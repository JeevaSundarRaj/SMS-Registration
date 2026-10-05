package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import objectsFile.LoginPageStepsObjects;
import utils.TestObject;

public class LoginPageSteps {
    private final TestObject testObject;
    private final LoginPageStepsObjects loginPage;

    public LoginPageSteps(TestObject testObject) {
        this.testObject = testObject;
        this.loginPage = new LoginPageStepsObjects(testObject);
    }

    @Given("User is in Login page")
    public void user_in_login_page() {
        loginPage.navigateToLoginPage();
    }

    @When("User is logging in via username password")
    public void user_login_with_credentials() throws InterruptedException {
        loginPage.login();
    }

    @Then("User redirected to Homepage")
    public void user_redirected_to_homepage() {
        loginPage.verifyHomepageLoaded();
    }
}
