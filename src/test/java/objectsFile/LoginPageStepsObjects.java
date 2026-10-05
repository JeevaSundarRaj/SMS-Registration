package objectsFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import utils.TestObject;

import static util.TestConstants.WAIT_DURATION_MID;

public class LoginPageStepsObjects {
    private final TestObject testObject;
    private static final Logger logger = LogManager.getLogger(LoginPageStepsObjects.class);

    public LoginPageStepsObjects(TestObject testObject) {
        this.testObject = testObject;
    }

    public void navigateToLoginPage() {
        String loginUrl = testObject.testConfig.baseUrl() + "/login";
        logger.info("Navigating to login page: {}", loginUrl);
        testObject.driver.get(loginUrl);
    }

    public void login() throws InterruptedException {
        logger.info("Logging in as: {}", testObject.userModel.email);

        testObject.seleniumUtils.waitForVisibleElement(
                By.xpath("//input[@name='email']"), WAIT_DURATION_MID)
                .sendKeys(testObject.userModel.email + Keys.RETURN);

        testObject.seleniumUtils.waitForVisibleElement(
                By.xpath("//input[@name='password']"), WAIT_DURATION_MID)
                .sendKeys(testObject.userModel.password + Keys.RETURN);

        Thread.sleep(1000);
        testObject.seleniumUtils.waitTillPageLoads(testObject.driver);
        logger.info("Login completed for: {}", testObject.userModel.email);
    }

    public void verifyHomepageLoaded() {
        logger.info("Verifying homepage is loaded");
    }
}
