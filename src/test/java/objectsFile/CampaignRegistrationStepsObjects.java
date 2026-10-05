package objectsFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.TestObject;

import static util.TestConstants.*;

public class CampaignRegistrationStepsObjects {
    private final TestObject testObject;
    private static final Logger logger = LogManager.getLogger(CampaignRegistrationStepsObjects.class);

    public CampaignRegistrationStepsObjects(TestObject testObject) {
        this.testObject = testObject;
    }

    public void navigateToCampaignPage() {
        String url = testObject.testConfig.baseUrl() + "/campaignregistration";
        logger.info("Navigating to campaign registration page: {}", url);
        testObject.driver.get(url);
    }

    public void campaignRegistration() throws InterruptedException {
        logger.info("Starting brand and campaign registration");

        // Brand information page
        testObject.seleniumUtils.click(
                By.xpath("//span[normalize-space()='New Brand']"), WAIT_DURATION_MID);
        testObject.seleniumUtils.click(
                By.xpath("//input[@value='PRIVATE_PROFIT']"), WAIT_DURATION_MID);

        testObject.seleniumUtils.enterText(
                By.xpath("//input[@name='brand-company-name']"), testObject.testConfig.companyName());
        testObject.seleniumUtils.enterText(
                By.xpath("//input[@name='brand-company-display-name']"), testObject.testConfig.brandName());

        testObject.seleniumUtils.selectByValue(
                By.xpath("//select[@name='brand-company-tax-id-country']"), "us");
        testObject.seleniumUtils.enterText(
                By.xpath("//input[@name='brand-company-tax-id']"), testObject.testConfig.taxID());
        testObject.seleniumUtils.enterText(
                By.xpath("//input[@name='brand-company-email']"), testObject.testConfig.emailID());
        testObject.seleniumUtils.enterText(
                By.xpath("//input[@name='brand-company-phone']"), testObject.testConfig.phoneNo());

        testObject.seleniumUtils.selectByValue(
                By.xpath("//select[@name='brand-company-industry']"), "TECHNOLOGY");
        testObject.seleniumUtils.enterText(
                By.xpath("//input[@name='brand-company-website']"), testObject.testConfig.webSite());

        testObject.seleniumUtils.selectByValue(
                By.xpath("//select[@name='brand-company-country']"), "us");

        testObject.seleniumUtils.waitForVisibleElement(
                By.xpath("//input[@aria-label='Street address']"), WAIT_DURATION_MID)
                .sendKeys(testObject.testConfig.street());
        testObject.seleniumUtils.enterText(
                By.xpath("//input[@name='brand-company-city']"), testObject.testConfig.city());

        testObject.seleniumUtils.selectByValue(
                By.xpath("//select[@name='brand-company-region']"), "CA");
        testObject.seleniumUtils.enterText(
                By.xpath("//input[@name='brand-company-postal-code']"), testObject.testConfig.zipCode());

        testObject.seleniumUtils.click(
                By.xpath("//span[normalize-space()='Submit brand registration']"), WAIT_DURATION_MID);
        logger.info("Brand information submitted, moving to campaign registration");

        //Thread.sleep(50000);

        testObject.seleniumUtils.click(
                By.xpath("//span[normalize-space()='Campaign Registration dashboard']"), WAIT_DURATION_MID);

        // New Campaign
        testObject.seleniumUtils.click(
                By.xpath("//span[normalize-space()='Register campaign']"), WAIT_DURATION_MID);

        WebElement dialerDropdown = testObject.seleniumUtils.waitForVisibleElement(
                By.className("d-select__input"), WAIT_DURATION_MID);

        Select selectDialer = new Select(dialerDropdown);

        selectDialer.selectByVisibleText("Dialer12");

        testObject.seleniumUtils.enterText(
                By.xpath("//textarea[@placeholder='Describe your communication purposes here']"),
                testObject.testConfig.commPurpose());
        testObject.seleniumUtils.click(
                By.xpath("(//input[@name='is_age_gated'])[1]"), WAIT_DURATION_MID);
        testObject.seleniumUtils.click(
                By.xpath("(//input[@name='is_direct_lending'])[1]"), WAIT_DURATION_MID);
        testObject.seleniumUtils.click(
                By.xpath("//input[@value='verbal']"), WAIT_DURATION_MID);

        Thread.sleep(2000);

        testObject.seleniumUtils.click(
                By.xpath("//input[@value='in person']"), WAIT_DURATION_MID);
        testObject.seleniumUtils.enterText(
                By.xpath("//input[@placeholder='Ex: Customer support agent']"), testObject.testConfig.customerSupport());
        testObject.seleniumUtils.enterText(
                By.xpath("//input[@placeholder='Ex: Marketing, Product updates, etc.']"), testObject.testConfig.cSupportReason());
        testObject.seleniumUtils.click(
                By.xpath("//input[@value='in a customer database']"), WAIT_DURATION_MID);
        testObject.seleniumUtils.click(
                By.xpath("(//input[@value='verbal-consent'])[1]"), WAIT_DURATION_MID);
        testObject.seleniumUtils.click(
                By.xpath("(//input[@name='recurrence-pattern'])[1]"), WAIT_DURATION_MID);
        testObject.seleniumUtils.click(
                By.xpath("//div[@class='d-d-flex d-fd-row d-ai-center d-mt-100']//input[@class='d-checkbox']"), WAIT_DURATION_MID);
        testObject.seleniumUtils.click(
                By.xpath("//input[@value='employees']"), WAIT_DURATION_MID);
        testObject.seleniumUtils.click(
                By.xpath("//input[@value='customers']"), WAIT_DURATION_MID);
        testObject.seleniumUtils.enterText(
                By.xpath("//input[@placeholder='URL, email address or phone number']"), testObject.testConfig.supportWeb());
        testObject.seleniumUtils.click(
                By.xpath("//span[normalize-space()='Next']"), WAIT_DURATION_MID);
        logger.info("Campaign description submitted, moving to messaging types");

        // Messaging types page
        testObject.seleniumUtils.click(
                By.xpath("//input[@value='AUTOMATED_DRIVEN_COMMUNICATION']"), WAIT_DURATION_MID);
        testObject.seleniumUtils.click(
                By.xpath("//input[@value='CONVERSATIONAL_MESSAGES']"), WAIT_DURATION_MID);
        testObject.seleniumUtils.click(
                By.xpath("//span[normalize-space()='Next']"), WAIT_DURATION_MID);
        logger.info("Messaging types submitted, moving to messaging examples");

        // Messaging examples page
        testObject.seleniumUtils.selectByValue(
                By.xpath("//select[@name='label-use-case']"), "CUSTOMER_CARE");
        testObject.seleniumUtils.enterText(
                By.xpath("(//textarea[@data-qa='dt-input-input'])[1]"), testObject.testConfig.commPurpose2());
        testObject.seleniumUtils.enterText(
                By.xpath("(//textarea[@data-qa='dt-input-input'])[2]"), testObject.testConfig.commPurpose3());
        testObject.seleniumUtils.click(
                By.xpath("//span[normalize-space()='Next']"), WAIT_DURATION_MID);
        logger.info("Messaging examples submitted, moving to content message");

        // Content message page
        testObject.seleniumUtils.enterText(
                By.xpath("(//textarea[@class='d-input__input d-textarea'])[1]"), testObject.testConfig.commPurpose4());
        testObject.seleniumUtils.click(
                By.xpath("(//input[@value='use-custom-message'])[1]"), WAIT_DURATION_MID);
        testObject.seleniumUtils.enterText(
                By.xpath("(//textarea[@class='d-input__input d-textarea'])[2]"), testObject.testConfig.commPurpose5());
        testObject.seleniumUtils.click(
                By.xpath("(//input[@value='use-custom-message'])[2]"), WAIT_DURATION_MID);
        testObject.seleniumUtils.enterText(
                By.xpath("(//textarea[@class='d-input__input d-textarea'])[3]"), testObject.testConfig.commPurpose6());
        testObject.seleniumUtils.click(
                By.xpath("(//input[@value='use-custom-message'])[3]"), WAIT_DURATION_MID);
        testObject.seleniumUtils.click(
                By.xpath("//span[normalize-space()='Next']"), WAIT_DURATION_MID);
        logger.info("Content message submitted, moving to privacy statement");

        // Privacy statement page
        testObject.seleniumUtils.enterText(
                By.xpath("//input[@name='brand-company-privacy-statement-link']"), testObject.testConfig.webSite2());
        testObject.seleniumUtils.click(
                By.xpath("//input[@value='I understand that if my website accepts phone numbers that the submission form must include a link to the Privacy Policy.']"),
                WAIT_DURATION_MID);
        testObject.seleniumUtils.click(
                By.xpath("//input[@value='My Privacy Policy indicates what information is being collected, how it is used, and how users can opt-out.']"),
                WAIT_DURATION_MID);
        testObject.seleniumUtils.click(
                By.xpath("//input[@value='My Privacy Policy clearly states that mobile phone contact information will not be shared or sold to third parties for marketing purposes.']"),
                WAIT_DURATION_MID);
        testObject.seleniumUtils.click(
                By.xpath("//span[normalize-space()='Next']"), WAIT_DURATION_MID);
        logger.info("Privacy statement submitted, moving to summary");

        // terms&condition page
        testObject.seleniumUtils.enterText(By.xpath("//input[@name='brand-company-terms-and-conditions-link']"),"https://www.support_help111@dialpad.com");
        testObject.seleniumUtils.click(By.xpath("//input[@type='checkbox']"), WAIT_DURATION_MID);
        testObject.seleniumUtils.click(
                By.xpath("//span[normalize-space()='Next']"), WAIT_DURATION_MID);

        //summary page
        testObject.seleniumUtils.click(By.xpath("//input[@type='checkbox']"), WAIT_DURATION_MID);
        testObject.seleniumUtils.click(
                By.xpath("//span[normalize-space()='Submit registration']"), WAIT_DURATION_MID);
        logger.info("Registration submitted");

        //back to dashboard
        testObject.seleniumUtils.click(
                By.xpath("//span[normalize-space()='Campaign Registration dashboard']"), WAIT_DURATION_MID);


    }

    public void verifyRegistrationSuccess() throws InterruptedException {
        logger.info("Verifying registration success message");

        By registeredLocator = By.xpath("//tr[contains(., 'Dialer12')]//td[div[contains(., 'Registered')]]");
        int maxRefreshAttempts = 2;
        WebElement confirmMsg = null;

        for (int attempt = 1; attempt <= maxRefreshAttempts + 1; attempt++) {
            try {
                confirmMsg = testObject.seleniumUtils.waitForVisibleElement(registeredLocator, WAIT_DURATION_MID);
                break;
            } catch (TimeoutException e) {
                if (attempt > maxRefreshAttempts) {
                    throw e;
                }
                logger.warn("Registration success message not visible yet, refreshing page (attempt {}/{})",
                        attempt, maxRefreshAttempts);
                testObject.driver.navigate().refresh();
                Thread.sleep(10000);
            }
        }

        Assert.assertEquals(confirmMsg.getText().split(" ")[0].trim(), "Registered");
    }

    public void deleteCampaign() throws InterruptedException {
        logger.info("Deleting campaign registration");

        waitForErrorStatus("Welcome to Dialpad,");
        testObject.seleniumUtils.waitTillElementClickableAndClick(
                By.xpath("//tr[contains(., 'Welcome to Dialpad,')]//button[@aria-label='show menu actions']"));
        testObject.seleniumUtils.waitTillElementClickableAndClick(
                By.xpath("//div[normalize-space()='Delete']"));
        testObject.seleniumUtils.waitTillElementClickableAndClick(
                By.xpath("//button[normalize-space()='Delete']"));


        testObject.seleniumUtils.waitTillElementClickableAndClick(
                By.xpath("//tr[contains(., 'Dialpad12')]//button[@aria-label='show menu actions']"));
        testObject.seleniumUtils.waitTillElementClickableAndClick(
                By.xpath("//div[normalize-space()='Delete']"));
        testObject.seleniumUtils.waitTillElementClickableAndClick(
                By.xpath("//button[normalize-space()='Delete']"));


        logger.info("Campaign registration deleted");
    }

    // The delete button stays disabled until the row's status flips to 'Error'; refresh until it shows up.
    private void waitForErrorStatus(String rowIdentifier) throws InterruptedException {
        By errorStatusLocator = By.xpath(
                "//tr[contains(., '" + rowIdentifier + "')]//td[div[contains(., 'Error')]]");
        int maxRefreshAttempts = 3;

        for (int attempt = 1; attempt <= maxRefreshAttempts + 1; attempt++) {
            try {
                testObject.seleniumUtils.waitForVisibleElement(errorStatusLocator, WAIT_DURATION_MID);
                return;
            } catch (TimeoutException e) {
                if (attempt > maxRefreshAttempts) {
                    throw e;
                }
                logger.warn("'Error' status not visible yet for row '{}', refreshing page (attempt {}/{})",
                        rowIdentifier, attempt, maxRefreshAttempts);
                testObject.driver.navigate().refresh();
                Thread.sleep(10000);
            }
        }
    }
}
