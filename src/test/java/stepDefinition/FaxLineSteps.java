package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import objectsFile.FaxLineStepObjects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.TestObject;

public class FaxLineSteps {
    private final TestObject testObject;
    private final FaxLineStepObjects faxPage;
    private static final Logger logger = LogManager.getLogger(FaxLineSteps.class);

    public FaxLineSteps(TestObject testObject) {
        this.testObject = testObject;
        this.faxPage = new FaxLineStepObjects(testObject);
    }

    @Given("User is using the Dialpad app")
    public void user_is_using_the_dialpad_app() {
        faxPage.navigateToApp();
    }

    @When("User sends a fax from {string} to {string}")
    public void user_sends_a_fax_from_to(String sender, String receiver) throws InterruptedException {
        faxPage.sendFax(sender, receiver);
    }

    @Then("Fax should be received successfully")
    public void fax_should_be_received_successfully() throws InterruptedException {
        faxPage.validateSentFax();
    }

    @Then("Receiver should see the fax as {string} and download opens in a new tab")
    public void receiver_should_see_fax(String expectedTime) {
        faxPage.verifyFaxReceivedByReceiver(expectedTime);
    }

    @Then("{string} should see the fax as {string} and download opens in a new tab")
    public void entity_should_see_fax(String receiver, String expectedTime) {
        faxPage.verifyFaxReceivedByEntity(receiver, expectedTime);
    }

    @When("User sends multiple faxes continuously from {string} to {string}")
    public void user_sends_multiple_faxes_continuously(String sender, String receiver) throws InterruptedException {
        faxPage.sendMultipleFaxesContinuously(sender, receiver);
    }

    @Then("Concurrent fax limit popup should be displayed")
    public void concurrent_fax_limit_popup_should_be_displayed() {
        faxPage.verifyConcurrentFaxLimitPopup();
    }

    @When("User sends a large volume fax from {string} to {string}")
    public void user_sends_large_volume_fax(String sender, String receiver) throws InterruptedException {
        faxPage.sendLargeVolumeFax(sender, receiver);
    }

    @Then("Large fax should be received successfully")
    public void large_fax_should_be_received_successfully() throws InterruptedException {
        faxPage.validateLargeFaxDelivery();
    }
}
