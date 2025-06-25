package stepDefinition;

import io.cucumber.java.en.And;
import objectsFile.CampaignRegistrationStepsObjects;
import objectsFile.LoginPageStepsObjects;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.TestObject;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;

public class CampaignRegistrationSteps {

    public WebDriver driver;
    TestObject testObject;
    WebDriverWait wait;

    public CampaignRegistrationSteps(TestObject testObject) {

        this.testObject = testObject;
    }

    @Given("User is in campaign registration page")
    public void user_is_in_campaign_registration_page() {
        CampaignRegistrationStepsObjects campaignRegistrationStepsObject = new CampaignRegistrationStepsObjects(testObject.driver, testObject.wait);
        campaignRegistrationStepsObject.navigating_to_campaignPage();
    }

    @When("User registers my brand and campaign")
    public void user_registers_my_brand() throws IOException, InterruptedException {
        CampaignRegistrationStepsObjects campaignRegistrationStepsObject = new CampaignRegistrationStepsObjects(testObject.driver, testObject.wait);
        campaignRegistrationStepsObject.campaignRegistration();
    }

    @And("Brand and Campaign registered successfully")
    public void user_registers_my_campaign() {
        CampaignRegistrationStepsObjects campaignRegistrationStepsObject = new CampaignRegistrationStepsObjects(testObject.driver, testObject.wait);
        campaignRegistrationStepsObject.registeredSuccessfully();
    }

    @Then("User Deletes the registration")
    public void brand_and_campaign_registered_successfully() {
        CampaignRegistrationStepsObjects campaignRegistrationStepsObject = new CampaignRegistrationStepsObjects(testObject.driver, testObject.wait);
        campaignRegistrationStepsObject.deleteCampaign();

    }

}
