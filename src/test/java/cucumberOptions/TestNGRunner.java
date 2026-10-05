package cucumberOptions;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "classpath:featureFile/faxLine.feature",
        glue = {"stepDefinition"},
        monochrome = true,
        plugin = {
                "html:reports/html/cucumber-report.html",
                "json:reports/json/cucumber-report.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        }
)
public class TestNGRunner extends AbstractTestNGCucumberTests {


}