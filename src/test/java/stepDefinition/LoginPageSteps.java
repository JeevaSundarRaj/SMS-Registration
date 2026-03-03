package stepDefinition;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import objectsFile.LoginPageStepsObjects;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.TestObject;

public class LoginPageSteps {
    public WebDriver driver;
    TestObject testObject;
    WebDriverWait wait;

    public LoginPageSteps(TestObject testObject)
    {

        this.testObject = testObject;
    }

    @Given("User is in Login page")
    public void user_in_login_page(){
        LoginPageStepsObjects loginPageStepsObjects = new LoginPageStepsObjects(testObject.driver, testObject.wait);
        loginPageStepsObjects.navigate_to_loginpage();

    }
    @When("User is logging in via Microsoft with valid credentials")
    public void user_login_to_microsoft_with_valid_credentials() throws InterruptedException {
        LoginPageStepsObjects loginPageStepsObjects = new LoginPageStepsObjects(testObject.driver, testObject.wait);
        loginPageStepsObjects.pass_vaild_credentials();

    }
    @Then("User redirected to Homepage")
    public void user_redirected_to_homepage(){
        LoginPageStepsObjects loginPageStepsObjects = new LoginPageStepsObjects(testObject.driver, testObject.wait);
        //loginPageStepsObjects.check_the_homepage();

    }


}
