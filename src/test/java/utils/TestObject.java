package utils;

import config.TestConfig;
import models.UserModel;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import util.SeleniumUtils;

import java.util.HashMap;
import java.util.Map;

import static util.TestConstants.*;

public class TestObject {
    public static final java.time.Duration WAIT_DURATION_MIN = util.TestConstants.WAIT_DURATION_MIN;
    public static final java.time.Duration WAIT_DURATION_MID = util.TestConstants.WAIT_DURATION_MID;
    public static final java.time.Duration WAIT_DURATION_MAX = util.TestConstants.WAIT_DURATION_MAX;

    public WebDriver driver;
    public WebDriver appDriver;       // second browser window (used by multi-window tests)
    public SeleniumUtils seleniumUtils;
    public UserModel userModel;
    public TestConfig testConfig;

    private static final Logger logger = LogManager.getLogger(TestObject.class);

    public TestObject() {
        testConfig = ConfigFactory.create(TestConfig.class);
        userModel = new UserModel(testConfig.loginEmail(), testConfig.loginPassword(), testConfig.loginType());

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 1);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        applyHeadless(options);

        driver = new ChromeDriver(options);
        sizeWindow(driver);

        seleniumUtils = new SeleniumUtils();
        seleniumUtils.setDriver(driver);

        logger.info("WebDriver initialized for user: {}", userModel.email);
    }

    /** Enables headless Chrome when run with -Dheadless=true. */
    public static void applyHeadless(ChromeOptions options) {
        if (Boolean.getBoolean("headless")) {
            options.addArguments("--headless=new", "--window-size=1920,1080");
        }
    }

    /** maximize() is a no-op in headless Chrome and leaves a tiny viewport, so set the size explicitly. */
    public static void sizeWindow(WebDriver driver) {
        if (Boolean.getBoolean("headless")) {
            driver.manage().window().setSize(new org.openqa.selenium.Dimension(1920, 1080));
        } else {
            driver.manage().window().maximize();
        }
    }

    public void quitBrowser() {
        if (appDriver != null) {
            logger.info("Quitting secondary browser window");
            appDriver.quit();
            appDriver = null;
        }
        if (driver != null) {
            logger.info("Quitting browser for user: {}", userModel.email);
            driver.quit();
        }
    }
}
