package cucumberOptions;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/featureFile",
        glue = {"stepDefinition"},// Step definitions package
        monochrome = true
        // Improves console readability
)
public class TestNGRunner extends AbstractTestNGCucumberTests {


}