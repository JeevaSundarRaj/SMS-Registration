package objectsFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import utils.TestObject;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import static util.TestConstants.*;

public class AutoReplyStepsObjects {
    private final TestObject testObject;
    private static final Logger logger = LogManager.getLogger(AutoReplyStepsObjects.class);

    // Both windows share the same driver — switch using handles
    private String settingsWindowHandle;
    private String appWindowHandle;

    private static final String TEST_MESSAGE = "Hello, this is an automated test message.";

    // ── Window 1 (Settings) XPaths ───────────────────────────────────────────

    private static final By SETTINGS_NAV_ICON         = By.xpath("//a[@href='/officesettings']");
    private static final By MAINLINE_SETTINGS_LINK    = By.xpath("//li[@title='Main Line']");
    private static final By CALL_CENTER_SETTINGS_LINK = By.xpath("//a[@data-header-url='callcenters']");
    private static final By DEPT_SETTINGS_LINK        = By.xpath("//a[@data-header-url='groups']");
    private static final By BUSINESS_HOURS_ROUTING    = By.xpath("//div[@id='hours_summary']");
    private static final By OPTION_NEVER              = By.xpath("//div[normalize-space()='Never']");
    private static final By OPTION_ALWAYS             = By.xpath("//div[normalize-space()='Always (24/7)']");
    private static final By AUTO_REPLY_TEXT_INPUT     = By.xpath("//textarea[@data-qa='group-sms-auto-response-text']");

    // Clicking this SVG opens the Dialpad app in a new window
    private static final By LAUNCH_DIALPAD_BTN        = By.xpath("//*[local-name()='svg' and @aria-label='Launch Dialpad']");

    // ── Window 2 (App) XPaths ────────────────────────────────────────────────

    private static final By NEW_MESSAGE_BTN           = By.xpath("//button[@data-qa='header-cta-message']");
    private static final By RECIPIENT_INPUT           = By.xpath("//div[contains(@class,'recipients-search-input')]");
    private static final By MESSAGE_TEXT_INPUT        = By.xpath("//p[@data-placeholder='New message']");
    private static final By SEND_MESSAGE_BTN          = By.xpath("//button[@data-qa='dt-split-button-alpha']");
    private static final By MESSAGE_BUBBLES           = By.xpath("(//section[@class='feed-item-text feed-item-text--all'])[last()]");

    // ─────────────────────────────────────────────────────────────────────────

    public AutoReplyStepsObjects(TestObject testObject) {
        this.testObject = testObject;
    }

    // ── Setup ─────────────────────────────────────────────────────────────────

    public void openSettingsAndAppWindows(String mainlineType) throws InterruptedException {
        logger.info("Opening settings + app windows for '{}'", mainlineType);

        // Window 1 — navigate to the mainline settings page
        settingsWindowHandle = testObject.driver.getWindowHandle();
        testObject.seleniumUtils.waitTillPageLoads(testObject.driver);
        testObject.seleniumUtils.click(SETTINGS_NAV_ICON, WAIT_DURATION_MID);
        testObject.seleniumUtils.click(getMainlineSettingsLink(mainlineType), WAIT_DURATION_MID);
        testObject.seleniumUtils.waitTillPageLoads(testObject.driver);
        Thread.sleep(1500); // allow page content to render fully
        logger.info("Window 1 (settings) ready for '{}' page", mainlineType);

        // Click Launch Dialpad — this opens the app in a new window
        testObject.seleniumUtils.click(LAUNCH_DIALPAD_BTN, WAIT_DURATION_MID);
        Thread.sleep(2000); // allow new window to fully open

        // Capture the new window handle
        Set<String> allHandles = new HashSet<>(testObject.driver.getWindowHandles());
        allHandles.remove(settingsWindowHandle);
        appWindowHandle = allHandles.iterator().next();

        // Switch to app window and wait for it to load
        testObject.driver.switchTo().window(appWindowHandle);
        testObject.seleniumUtils.waitTillPageLoads(testObject.driver);
        logger.info("Window 2 (app) opened and loaded");

        // Return focus to settings window — ready for configuration
        testObject.driver.switchTo().window(settingsWindowHandle);
        logger.info("Both windows ready. Focus on settings window.");
    }

    // ── Window 1: Settings ───────────────────────────────────────────────────

    public void disableAutoReply(String mainlineType) {
        logger.info("Setting auto-reply to Never for '{}'", mainlineType);
        testObject.driver.switchTo().window(settingsWindowHandle);

        if (hasBusinessHoursSection()) {
            // Mainline: routing button must be clicked first to expand Never/Always options
            WebElement routingBtn = testObject.seleniumUtils.waitForVisibleElement(BUSINESS_HOURS_ROUTING, WAIT_DURATION_MID);
            testObject.seleniumUtils.scrollToElement(routingBtn);
            String currentSetting = routingBtn.getText();
            if (!currentSetting.contains("Never")) {
                testObject.seleniumUtils.click(BUSINESS_HOURS_ROUTING, WAIT_DURATION_MID);
                WebElement neverOption = testObject.seleniumUtils.waitForVisibleElement(OPTION_NEVER, WAIT_DURATION_MID);
                testObject.seleniumUtils.scrollToElement(neverOption);
                testObject.seleniumUtils.click(OPTION_NEVER, WAIT_DURATION_MID);
                logger.info("Auto-reply changed to Never (was: '{}')", currentSetting);
            } else {
                logger.info("Auto-reply already set to Never — no change needed");
            }
        } else {
            // CallCenter / Department: Never option is directly visible, no routing button needed
            WebElement neverOption = testObject.seleniumUtils.waitForVisibleElement(OPTION_NEVER, WAIT_DURATION_MID);
            testObject.seleniumUtils.scrollToElement(neverOption);
            testObject.seleniumUtils.click(OPTION_NEVER, WAIT_DURATION_MID);
            logger.info("Auto-reply set to Never for '{}'", mainlineType);
        }
    }

    public void enableAutoReply(String mainlineType) throws InterruptedException {
        logger.info("Setting auto-reply to Always (24/7) for '{}'", mainlineType);
        testObject.driver.switchTo().window(settingsWindowHandle);

        if (hasBusinessHoursSection()) {
            // Mainline: expand routing section first
            WebElement routingBtn = testObject.seleniumUtils.waitForVisibleElement(BUSINESS_HOURS_ROUTING, WAIT_DURATION_MID);
            testObject.seleniumUtils.scrollToElement(routingBtn);
            testObject.seleniumUtils.click(BUSINESS_HOURS_ROUTING, WAIT_DURATION_MID);
            WebElement alwaysOption = testObject.seleniumUtils.waitForVisibleElement(OPTION_ALWAYS, WAIT_DURATION_MID);
            testObject.seleniumUtils.scrollToElement(alwaysOption);
            testObject.seleniumUtils.click(OPTION_ALWAYS, WAIT_DURATION_MID);
            logger.info("Always (24/7) option selected for '{}'", mainlineType);
        } else {
            // CallCenter / Department: Always option is directly visible
            WebElement alwaysOption = testObject.seleniumUtils.waitForVisibleElement(OPTION_ALWAYS, WAIT_DURATION_MID);
            testObject.seleniumUtils.scrollToElement(alwaysOption);
            testObject.seleniumUtils.click(OPTION_ALWAYS, WAIT_DURATION_MID);
            logger.info("Auto-reply set to Always (24/7) for '{}'", mainlineType);
        }

        // Enter the auto-reply message (same for all types)
        testObject.seleniumUtils.waitForVisibleElement(AUTO_REPLY_TEXT_INPUT, WAIT_DURATION_MID);
        testObject.seleniumUtils.enterText(AUTO_REPLY_TEXT_INPUT, testObject.testConfig.autoReplyMessage());
        logger.info("Auto-reply message set: '{}'", testObject.testConfig.autoReplyMessage());
    }

    // ── Window 2: App ─────────────────────────────────────────────────────────

    public void sendTestMessage(String mainlineType) throws InterruptedException {
        String number = getRecipientNumber(mainlineType);
        logger.info("Sending '{}' to '{}' ({})", TEST_MESSAGE, mainlineType, number);
        testObject.driver.switchTo().window(appWindowHandle);
        testObject.seleniumUtils.waitTillPageLoads(testObject.driver);

        testObject.seleniumUtils.click(NEW_MESSAGE_BTN, WAIT_DURATION_MID);
        Thread.sleep(500);
        // Click the recipients container to focus it, then type via Actions (div is not directly interactable)
        testObject.seleniumUtils.waitForClickableElement(RECIPIENT_INPUT).click();
        Thread.sleep(500);
        new Actions(testObject.driver).sendKeys(number).sendKeys(Keys.ENTER).perform();
        Thread.sleep(1000);

        testObject.seleniumUtils.waitForClickableElement(MESSAGE_TEXT_INPUT).click();
        new Actions(testObject.driver).sendKeys(TEST_MESSAGE).sendKeys(Keys.ENTER).perform();
        Thread.sleep(500);
        testObject.seleniumUtils.click(SEND_MESSAGE_BTN, WAIT_DURATION_MID);
        logger.info("Message sent to '{}' ({})", mainlineType, number);
    }

    public void verifyNoAutoReply() throws InterruptedException {
        logger.info("Verifying no auto-reply — last bubble should be the sent message");
        testObject.driver.switchTo().window(appWindowHandle);
        Thread.sleep(5000);

        testObject.seleniumUtils.waitForVisibleElement(MESSAGE_BUBBLES, WAIT_DURATION_MID);
        String lastBubble = testObject.driver.findElement(MESSAGE_BUBBLES).getText();
        Assert.assertEquals(lastBubble,TEST_MESSAGE);
        logger.info("Last message bubble (no auto-reply expected): '{}'", lastBubble);
    }

    public void verifyAutoReplyReceived() throws InterruptedException {
        // Read the configured auto-reply text directly from the settings window textarea
        testObject.driver.switchTo().window(settingsWindowHandle);
        WebElement textArea = testObject.seleniumUtils.waitForVisibleElement(AUTO_REPLY_TEXT_INPUT, WAIT_DURATION_MID);
        String configuredReplyText = textArea.getAttribute("value");
        logger.info("Configured auto-reply text: '{}'", configuredReplyText);

        // Switch to app window and wait for the reply to arrive
        testObject.driver.switchTo().window(appWindowHandle);
        Thread.sleep(8000);

        testObject.seleniumUtils.waitForVisibleElement(MESSAGE_BUBBLES, WAIT_DURATION_MID);
        String lastBubble = testObject.driver.findElement(MESSAGE_BUBBLES).getText();
        logger.info("Configured auto-reply : '{}'", configuredReplyText);
        logger.info("Actual last bubble    : '{}'", lastBubble);
        Assert.assertTrue(lastBubble.contains(configuredReplyText),
                "Auto-reply not received. Expected: '" + configuredReplyText + "', actual: '" + lastBubble + "'");
        logger.info("Auto-reply verified successfully");
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    // Returns true if the Mainline-specific Business Hours Routing section exists on the page
    private boolean hasBusinessHoursSection() {
        return testObject.seleniumUtils.isDisplayed(BUSINESS_HOURS_ROUTING, Duration.ofSeconds(4));
    }

    private By getMainlineSettingsLink(String mainlineType) {
        switch (mainlineType.toLowerCase()) {
            case "mainline":    return MAINLINE_SETTINGS_LINK;
            case "callcenter":  return CALL_CENTER_SETTINGS_LINK;
            case "department":  return DEPT_SETTINGS_LINK;
            default: throw new IllegalArgumentException("Unknown mainline type: " + mainlineType);
        }
    }

    private String getRecipientNumber(String mainlineType) {
        switch (mainlineType.toLowerCase()) {
            case "mainline":    return testObject.testConfig.mainlineNumber();
            case "callcenter":  return testObject.testConfig.callCenterNumber();
            case "department":  return testObject.testConfig.mainlineDeptNumber();
            default: throw new IllegalArgumentException("Unknown mainline type: " + mainlineType);
        }
    }
}
