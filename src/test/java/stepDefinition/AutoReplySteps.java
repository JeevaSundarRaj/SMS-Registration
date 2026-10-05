package stepDefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import objectsFile.AutoReplyStepsObjects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.TestObject;

public class AutoReplySteps {
    private final TestObject testObject;
    private final AutoReplyStepsObjects autoReplyPage;
    private static final Logger logger = LogManager.getLogger(AutoReplySteps.class);

    public AutoReplySteps(TestObject testObject) {
        this.testObject = testObject;
        this.autoReplyPage = new AutoReplyStepsObjects(testObject);
    }

    @Given("User opens settings and app windows for {string}")
    public void user_opens_settings_and_app_windows(String mainlineType) throws InterruptedException {
        autoReplyPage.openSettingsAndAppWindows(mainlineType);
    }

    @When("User disables auto-reply for {string}")
    public void user_disables_auto_reply(String mainlineType) {
        autoReplyPage.disableAutoReply(mainlineType);
    }

    @When("User enables auto-reply for {string}")
    public void user_enables_auto_reply(String mainlineType) throws InterruptedException {
        autoReplyPage.enableAutoReply(mainlineType);
    }

    @And("User sends a test message to {string}")
    public void user_sends_test_message(String mainlineType) throws InterruptedException {
        autoReplyPage.sendTestMessage(mainlineType);
    }

    @Then("No auto-reply should be received")
    public void no_auto_reply_received() throws InterruptedException {
        autoReplyPage.verifyNoAutoReply();
    }

    @Then("Auto-reply message should be received")
    public void auto_reply_message_received() throws InterruptedException {
        autoReplyPage.verifyAutoReplyReceived();
    }
}
