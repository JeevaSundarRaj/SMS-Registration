package stepDefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import objectsFile.CampaignRegistrationStepsObjects;
import utils.TestObject;

public class CampaignRegistrationSteps {
    private final TestObject testObject;
    private final CampaignRegistrationStepsObjects campaignPage;

    public CampaignRegistrationSteps(TestObject testObject) {
        this.testObject = testObject;
        this.campaignPage = new CampaignRegistrationStepsObjects(testObject);
    }

    @Given("User is in campaign registration page")
    public void user_is_in_campaign_registration_page() {
        campaignPage.navigateToCampaignPage();
    }

    @When("User registers my brand and campaign")
    public void user_registers_my_brand() throws InterruptedException {
        campaignPage.campaignRegistration();
    }

    @And("Brand and Campaign registered successfully")
    public void brand_and_campaign_registered_successfully() throws InterruptedException {
        campaignPage.verifyRegistrationSuccess();
    }

    @Then("User Deletes the registration")
    public void user_deletes_registration() throws InterruptedException {
        campaignPage.deleteCampaign();
    }
}
