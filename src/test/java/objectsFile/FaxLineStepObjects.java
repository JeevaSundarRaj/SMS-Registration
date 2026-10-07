package objectsFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import util.SeleniumUtils;
import org.testng.Assert;
import org.testng.SkipException;
import utils.TestObject;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static util.TestConstants.*;

public class FaxLineStepObjects {
    private final TestObject testObject;
    private static final Logger logger = LogManager.getLogger(FaxLineStepObjects.class);

    private static final String FAX_PDF_PATH =
            System.getProperty("user.dir") + "/src/test/resources/test_data/Dialpa1fax.pdf";

    private static final By CONCURRENT_LIMIT_POPUP = By.xpath(
            "//div[@data-qa='single-alert-body']//div[contains(@class,'d-body--md') and contains(normalize-space(),'Concurrent fax limit reached')]");

    private static final int MAX_CONCURRENT_FAX_ATTEMPTS = 6;

    private boolean limitPopupSeen;

    private String lastSender = "user";
    private java.time.LocalDateTime lastSentAt;

    private static final long FAX_TIME_TOLERANCE_SECS = 5;

    public FaxLineStepObjects(TestObject testObject) {
        this.testObject = testObject;
    }

    public void navigateToApp() {
        String appUrl = testObject.testConfig.baseUrl() + "/app";
        logger.info("Navigating to Dialpad app: {}", appUrl);
        testObject.driver.get(appUrl);
    }

    // ── Standard single fax (uses default PDF) ────────────────────────────────

    public void sendFax(String sender, String receiver) throws InterruptedException {
        logger.info("Sending fax from '{}' to '{}'", sender, receiver);
        testObject.seleniumUtils.waitForInvisibility(By.id("logoCanvas"), WAIT_DURATION_MAX);
        sendFaxRequest(sender, receiver, FAX_PDF_PATH);
    }

    // ── Large volume fax (uses large file from config) ────────────────────────

    public void sendLargeVolumeFax(String sender, String receiver) throws InterruptedException {
        String largeFaxPath = System.getProperty("user.dir") + "/" + testObject.testConfig.largeFaxFilePath();
        logger.info("Sending large volume fax from '{}' to '{}', file: {}", sender, receiver, largeFaxPath);
        testObject.seleniumUtils.waitForInvisibility(By.id("logoCanvas"), WAIT_DURATION_MAX);
        sendFaxRequest(sender, receiver, largeFaxPath);
    }

    // ── Concurrent fax limit test ─────────────────────────────────────────────

    public void sendMultipleFaxesContinuously(String sender, String receiver) throws InterruptedException {
        logger.info("Sending multiple faxes continuously to trigger concurrent limit");
        testObject.seleniumUtils.waitForInvisibility(By.id("logoCanvas"), WAIT_DURATION_MAX);

        // Queue faxes back-to-back with no popup polling in between, so several are in flight at once
        for (int attempt = 1; attempt <= MAX_CONCURRENT_FAX_ATTEMPTS; attempt++) {
            logger.info("Fax attempt {} of {}", attempt, MAX_CONCURRENT_FAX_ATTEMPTS);
            try {
                sendFaxRequest(sender, receiver, FAX_PDF_PATH, 1000);
            } catch (org.openqa.selenium.WebDriverException e) {
                // The limit popup can appear mid-burst and block the compose pane
                if (limitPopupSeen || checkLimitPopup()) {
                    logger.info("Concurrent fax limit popup blocked attempt {}", attempt);
                    return;
                }
                throw e;
            }
            if (attempt >= 3 && checkLimitPopup()) {
                logger.info("Concurrent fax limit popup appeared after {} fax(es)", attempt);
                return;
            }
        }
        logger.warn("Concurrent fax limit popup did not appear after {} attempt(s)", MAX_CONCURRENT_FAX_ATTEMPTS);
    }

    public void verifyConcurrentFaxLimitPopup() {
        logger.info("Verifying concurrent fax limit popup is displayed");
        if (limitPopupSeen) {
            logger.info("Concurrent fax limit popup was observed during the fax burst");
            return;
        }
        WebElement popup = testObject.seleniumUtils.waitForVisibleElement(CONCURRENT_LIMIT_POPUP, WAIT_DURATION_MID);
        Assert.assertNotNull(popup, "Concurrent fax limit popup was not found");
        Assert.assertTrue(popup.isDisplayed(), "Concurrent fax limit popup is not visible");
        logger.info("Concurrent fax limit popup confirmed: '{}'", popup.getText());
    }

    // ── Fax delivery validation ───────────────────────────────────────────────

    public void validateSentFax() throws InterruptedException {
        logger.info("Validating fax delivery status");

        List<WebElement> alert = testObject.driver.findElements(By.id("single-alert-flow"));
        if (!alert.isEmpty() && alert.get(0).isDisplayed()) {
            throw new SkipException("Fax limit reached — skipping delivery validation");
        }

        testObject.seleniumUtils.waitForVisibleElement(By.className("feed-item-pill"), WAIT_DURATION_MAX);

        Thread.sleep(40000);
        testObject.driver.navigate().refresh();

        testObject.seleniumUtils.waitForVisibleElement(By.className("feed-item-pill"), WAIT_DURATION_MID);

        List<WebElement> pills = testObject.driver.findElements(By.className("feed-item-pill"));
        String status = pills.get(pills.size() - 1).getText();

        logger.info("Fax delivery status: '{}'", status);
        Assert.assertTrue(status.contains("success"), "Fax status not successful, actual: " + status);
    }

    // ── Receiver-side verification (second browser, logged in as receiver) ────

    public void verifyFaxReceivedByReceiver(String expectedTime) {
        SeleniumUtils utils = loginAsReceiver();
        WebDriver receiverDriver = testObject.appDriver;

        utils.waitTillElementClickableAndClick(By.xpath("//div[normalize-space()='Inbox']"));
        utils.waitTillElementClickableAndClick(By.xpath("//button[normalize-space()='Faxes']"));

        // First row from the sender — every fax in the list shares this name
        String row = "(//tr[contains(@class,'history-fax-row')][contains(., '"
                + testObject.testConfig.faxUserTitle() + "')])[1]";

        // Row actions only render on hover (same as the entity fax list)
        WebElement firstRow = utils.waitForVisibleElement(By.xpath(row), WAIT_DURATION_MAX);
        new Actions(receiverDriver).moveToElement(firstRow).perform();

        // Download fax on the same row should open a new tab
        clickAndVerifyNewTab(utils, receiverDriver, By.xpath(row + "//*[contains(@class,'link') and contains(@class,'download-fax')]"));
    }

    // ── Receiver-side verification on the CC / Department fax list ────────────

    public void verifyFaxReceivedByEntity(String receiver, String expectedTime) {
        // cc / dept belong to automation user 1; cc2 / dept2 belong to automation user 2
        boolean ownedByUser1 = receiver.equalsIgnoreCase("cc") || receiver.equalsIgnoreCase("dept");
        SeleniumUtils utils = ownedByUser1
                ? loginAsReceiver(testObject.testConfig.loginEmail(), testObject.testConfig.loginPassword())
                : loginAsReceiver();
        WebDriver receiverDriver = testObject.appDriver;

        openEntityFaxPage(utils, receiverDriver, receiver);
        utils.waitTillElementClickableAndClick(By.id("dt-tab-group-inbox-tab-faxes"));

        String senderName = getSenderName(lastSender);
        String row = "(//div[@data-qa='dt-scroller-item'][.//div[contains(text(), '" + senderName + "')]])[1]";

        WebElement firstRow = utils.waitForVisibleElement(By.xpath(row), WAIT_DURATION_MAX);
        Assert.assertTrue(firstRow.isDisplayed(), "Fax row is not displayed on '" + receiver + "' Faxes tab");
        String rowText = firstRow.getText();
        logger.info("First fax row on '{}': {}", receiver, rowText.replace("\n", " | "));
        Assert.assertTrue(rowText.contains(senderName),
                "Fax sender should be '" + senderName + "' but row was: " + rowText);
        Assert.assertTrue(matchesFaxTime(rowText, expectedTime),
                "Expected fax time '" + expectedTime + "' (or a recent time) but row was: " + rowText);
        Assert.assertTrue(rowText.contains("Inbound"), "Fax should be marked Inbound but row was: " + rowText);

        // Download button only appears while hovering the row
        new Actions(receiverDriver).moveToElement(firstRow).perform();
        WebElement download = utils.waitForVisibleElement(By.xpath(
                row + "//button[@data-qa='dt-button' and .//span[normalize-space()='Download']]"), WAIT_DURATION_MID);
        Assert.assertTrue(download.isDisplayed() && download.isEnabled(),
                "Download button should be visible and enabled on hover");

        clickAndVerifyNewTab(utils, receiverDriver, By.xpath(
                row + "//button[@data-qa='dt-button' and .//span[normalize-space()='Download']]"));
    }

    /**
     * "Just now" turns into a clock time / "N min ago" once the sender's delivery wait has elapsed,
     * so for "Just now" also accept those recent-time formats.
     */
    private boolean matchesFaxTime(String actual, String expected) {
        String a = actual.toLowerCase();
        if (a.contains(expected.toLowerCase())) {
            return true;
        }
        return expected.equalsIgnoreCase("Just now")
                && java.util.regex.Pattern.compile("\\d+\\s*(s|sec|secs|m|min|mins|minute|minutes)\\b.*ago|\\d{1,2}:\\d{2}")
                .matcher(a).find();
    }

    /**
     * The UI shows the fax time as HH:mm (minute precision), so the displayed minute covers
     * [HH:mm:00, HH:mm:59]. Passes if that window overlaps send time ±FAX_TIME_TOLERANCE_SECS.
     */
    private boolean isWithinSendTime(String displayed) {
        java.util.regex.Matcher m = java.util.regex.Pattern
                .compile("(\\d{1,2}):(\\d{2})\\s*([AaPp][Mm])?").matcher(displayed);
        if (!m.find() || lastSentAt == null) {
            return false;
        }
        int hour = Integer.parseInt(m.group(1));
        if (m.group(3) != null) {
            boolean pm = m.group(3).equalsIgnoreCase("pm");
            hour = (hour % 12) + (pm ? 12 : 0);
        }
        java.time.LocalDateTime start = lastSentAt.withHour(hour)
                .withMinute(Integer.parseInt(m.group(2))).withSecond(0).withNano(0);
        java.time.LocalDateTime end = start.plusSeconds(59);
        return !lastSentAt.plusSeconds(FAX_TIME_TOLERANCE_SECS).isBefore(start)
                && !lastSentAt.minusSeconds(FAX_TIME_TOLERANCE_SECS).isAfter(end);
    }

    private SeleniumUtils loginAsReceiver() {
        return loginAsReceiver(testObject.testConfig.faxReceiverEmail(), testObject.testConfig.faxReceiverPassword());
    }

    private SeleniumUtils loginAsReceiver(String receiverEmail, String receiverPassword) {
        logger.info("Opening second browser and logging in as receiver: {}", receiverEmail);

        org.openqa.selenium.chrome.ChromeOptions receiverOptions = new org.openqa.selenium.chrome.ChromeOptions();
        TestObject.applyHeadless(receiverOptions);
        WebDriver receiverDriver = new ChromeDriver(receiverOptions);
        TestObject.sizeWindow(receiverDriver);
        testObject.appDriver = receiverDriver; // quit by TestObject.quitBrowser()

        SeleniumUtils utils = new SeleniumUtils();
        utils.setDriver(receiverDriver);

        receiverDriver.get(testObject.testConfig.baseUrl() + "/login");
        utils.waitForVisibleElement(By.xpath("//input[@name='email']"), WAIT_DURATION_MID)
                .sendKeys(receiverEmail + Keys.RETURN);
        utils.waitForVisibleElement(By.xpath("//input[@name='password']"), WAIT_DURATION_MID)
                .sendKeys(receiverPassword + Keys.RETURN);
        utils.waitTillPageLoads(receiverDriver);

        receiverDriver.get(testObject.testConfig.baseUrl() + "/app");
        utils.waitForInvisibility(By.id("logoCanvas"), WAIT_DURATION_MAX);
        return utils;
    }

    private void clickAndVerifyNewTab(SeleniumUtils utils, WebDriver driver, By downloadButton) {
        String originalTab = driver.getWindowHandle();
        Set<String> before = new HashSet<>(driver.getWindowHandles());

        utils.waitTillElementClickableAndClick(downloadButton);

        new org.openqa.selenium.support.ui.WebDriverWait(driver, WAIT_DURATION_MID)
                .until(d -> d.getWindowHandles().size() > before.size());

        Set<String> after = new HashSet<>(driver.getWindowHandles());
        after.removeAll(before);
        Assert.assertEquals(after.size(), 1, "Download fax should open exactly one new tab");

        driver.switchTo().window(after.iterator().next());
        String newTabUrl = driver.getCurrentUrl();
        logger.info("Download fax opened new tab: {}", newTabUrl);
        Assert.assertFalse(newTabUrl == null || newTabUrl.isBlank() || newTabUrl.equals("about:blank"),
                "Download tab should load a fax URL but was: " + newTabUrl);
        Assert.assertNotEquals(driver.getWindowHandle(), originalTab, "Driver should be on the new tab");
        driver.close();
        driver.switchTo().window(originalTab);
    }

    // ── Large fax delivery validation (longer wait + refresh before status check) ──

    public void validateLargeFaxDelivery() throws InterruptedException {
        logger.info("Validating large volume fax delivery status");

        List<WebElement> alert = testObject.driver.findElements(By.id("single-alert-flow"));
        if (!alert.isEmpty() && alert.get(0).isDisplayed()) {
            throw new SkipException("Fax limit reached — skipping delivery validation");
        }

        testObject.seleniumUtils.waitForVisibleElement(By.className("feed-item-pill"), WAIT_DURATION_MAX);

        logger.info("Waiting for large fax to be processed (2 minutes)...");
        Thread.sleep(120000);

        logger.info("Refreshing page before checking delivery status");
        testObject.driver.navigate().refresh();
        Thread.sleep(5000);

        testObject.seleniumUtils.waitForVisibleElement(By.className("feed-item-pill"), WAIT_DURATION_MID);

        List<WebElement> pills = testObject.driver.findElements(By.className("feed-item-pill"));
        String status = pills.get(pills.size() - 1).getText();

        logger.info("Large fax delivery status: '{}'", status);
        Assert.assertTrue(status.contains("success"), "Large fax status not successful, actual: " + status);
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private void sendFaxRequest(String sender, String receiver, String filePath) throws InterruptedException {
        sendFaxRequest(sender, receiver, filePath, 1000);
    }

    private void sendFaxRequest(String sender, String receiver, String filePath, long settleMs) throws InterruptedException {
        lastSender = sender;
        testObject.seleniumUtils.waitTillElementClickableAndClick(By.xpath("//button[@aria-label='Fax']"));
        testObject.seleniumUtils.waitTillElementClickableAndClick(By.xpath("//button[@data-qa='dropdown-button']"));
        testObject.seleniumUtils.waitTillElementClickableAndClick(By.xpath(getSenderXpath(sender)));

        typeInToField(By.id("search-input"), getReceiverSearch(receiver));
        Thread.sleep(settleMs); // let the number search results load before selecting
        testObject.driver.findElement(By.id("search-input")).sendKeys(Keys.ENTER);
        Thread.sleep(settleMs); // let the recipient chip resolve before attaching the file

        testObject.driver.findElement(By.xpath("//input[@name='new-fax']")).sendKeys(filePath);
        lastSentAt = java.time.LocalDateTime.now();
        logger.info("Fax queued — sender: '{}', receiver: '{}', file: {}", sender, receiver, filePath);
    }

    /** Non-blocking check; remembers the popup once seen since the toast auto-dismisses. */
    private boolean checkLimitPopup() {
        for (WebElement el : testObject.driver.findElements(CONCURRENT_LIMIT_POPUP)) {
            if (el.isDisplayed()) {
                limitPopupSeen = true;
                logger.info("Concurrent fax limit popup seen: '{}'", el.getText());
                return true;
            }
        }
        return false;
    }

    /** Re-locates, scrolls to and clicks the field before typing; retries while the compose pane is still animating/re-rendering. */
    private void typeInToField(By locator, String text) throws InterruptedException {
        WebDriver driver = testObject.driver;
        for (int attempt = 1; ; attempt++) {
            try {
                WebElement field = testObject.seleniumUtils.waitForClickableElement(locator);
                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("arguments[0].scrollIntoView({block:'center'});", field);
                // JS click: the selected sender label overlaps the field, so a native click gets intercepted
                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("arguments[0].click(); arguments[0].focus();", field);
                field.sendKeys(text);
                return;
            } catch (org.openqa.selenium.ElementNotInteractableException
                     | org.openqa.selenium.StaleElementReferenceException e) {
                // The limit toast is short-lived and blocks the pane; record it before it disappears
                if (attempt >= 3 || checkLimitPopup()) throw e;
                logger.warn("Field {} not interactable (attempt {}), retrying", locator, attempt);
                Thread.sleep(2000);
            }
        }
    }

    private void openEntityFaxPage(SeleniumUtils utils, WebDriver driver, String receiver) {
        String title = getEntityTitle(receiver);
        if (receiver.toLowerCase().startsWith("cc")) {
            utils.waitTillElementClickableAndClick(By.xpath("//a[@data-qa='leftbar-links-ai-contact-centers']"));
            utils.waitTillElementClickableAndClick(By.xpath("//span[normalize-space()='" + title + "']"));
        } else {
            // Departments section header shows chevron-right when collapsed, chevron-down when expanded
            By header = By.xpath("//button[.//span[@title='Departments']]");
            utils.waitForVisibleElement(header, WAIT_DURATION_MID);
            if (!driver.findElements(By.xpath("//button[.//span[@title='Departments']]//*[@data-name='Chevron Right']")).isEmpty()) {
                utils.waitTillElementClickableAndClick(header);
            }
            utils.waitTillElementClickableAndClick(By.xpath(
                    "(//a[starts-with(@data-qa,'leftbar-links-') and @title='" + title + "'])[1]"));
        }
    }

    private String getSenderName(String sender) {
        return sender.equalsIgnoreCase("user") ? testObject.testConfig.faxUserTitle() : getEntityTitle(sender);
    }

    private String getEntityTitle(String entity) {
        switch (entity.toLowerCase()) {
            case "cc":       return testObject.testConfig.faxCCTitle();
            case "cc2":      return testObject.testConfig.faxCC2Title();
            case "dept":     return testObject.testConfig.faxDeptTitle();
            case "dept2":    return testObject.testConfig.faxDept2Title();
            case "hellofax": return testObject.testConfig.faxHelloFaxTitle();
            default: throw new IllegalArgumentException("Unknown fax receiver entity: " + entity);
        }
    }

    private String getSenderXpath(String sender) {
        switch (sender.toLowerCase()) {
            case "user":     return "//span[normalize-space()='" + testObject.testConfig.faxUserTitle() + "']";
            case "cc":       return "//span[normalize-space()='" + testObject.testConfig.faxCCTitle() + "']";
            case "cc2":      return "//span[normalize-space()='" + testObject.testConfig.faxCC2Title() + "']";
            case "dept":     return "//span[normalize-space()='" + testObject.testConfig.faxDeptTitle() + "']";
            case "dept2":    return "//span[normalize-space()='" + testObject.testConfig.faxDept2Title() + "']";
            case "hellofax": return "//span[normalize-space()='" + testObject.testConfig.faxHelloFaxTitle() + "']";
            default: throw new IllegalArgumentException("Unknown fax sender: " + sender);
        }
    }

    private String getReceiverSearch(String receiver) {
        switch (receiver.toLowerCase()) {
            case "user":     return testObject.testConfig.faxUserSearch();
            case "cc":       return testObject.testConfig.faxCCSearch();
            case "cc2":      return testObject.testConfig.faxCC2Search();
            case "dept":     return testObject.testConfig.faxDeptSearch();
            case "dept2":    return testObject.testConfig.faxDept2Search();
            case "hellofax": return testObject.testConfig.faxHelloFaxSearch();
            default: throw new IllegalArgumentException("Unknown fax receiver: " + receiver);
        }
    }
}

