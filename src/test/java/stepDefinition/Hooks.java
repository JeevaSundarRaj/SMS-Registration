package stepDefinition;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.TestObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Hooks {
    private final TestObject testObject;
    private static final Logger logger = LogManager.getLogger(Hooks.class);
    private static final String SCREENSHOT_DIR = "reports/screenshots";

    public Hooks(TestObject testObject) {
        this.testObject = testObject;
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            logger.warn("Scenario FAILED: '{}'", scenario.getName());
            captureScreenshot(scenario);
        }
        logger.info("Tearing down test - closing browser");
        testObject.quitBrowser();
    }

    private void captureScreenshot(Scenario scenario) {
        try {
            byte[] screenshot = ((TakesScreenshot) testObject.driver).getScreenshotAs(OutputType.BYTES);

            // Embed in ExtentReports Spark report + Cucumber HTML report
            scenario.attach(screenshot, "image/png", "Failed: " + scenario.getName());

            // Save a copy to disk under reports/screenshots/
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String safeName = scenario.getName().replaceAll("[^a-zA-Z0-9_-]", "_");
            File dir = new File(SCREENSHOT_DIR);
            dir.mkdirs();
            File file = new File(dir, safeName + "_" + timestamp + ".png");
            Files.write(file.toPath(), screenshot);
            logger.info("Screenshot saved to: {}", file.getPath());
        } catch (IOException e) {
            logger.error("Failed to save screenshot for scenario '{}': {}", scenario.getName(), e.getMessage());
        }
    }
}
