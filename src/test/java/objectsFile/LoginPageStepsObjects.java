package objectsFile;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class LoginPageStepsObjects {
    WebDriver driver;
    WebDriverWait wait;

    public LoginPageStepsObjects(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
    }


    public void navigate_to_loginpage(){
        driver.get("https://dialpadbeta.com/login");
      //  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement login = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//span[contains( text() , 'Microsoft')]/ancestor::button"))
        );
        login.click();
    }


        public void pass_vaild_credentials () throws InterruptedException {

//                 wait.until(ExpectedConditions.elementToBeClickable(
//             By.xpath("//span[contains( text() , 'Microsoft')]/ancestor::button"))).click();
//
//            wait.until(ExpectedConditions.elementToBeClickable(By.id("i0116"))).sendKeys("test_1231985@outlook.com" + Keys.RETURN);
//            wait.until(ExpectedConditions.elementToBeClickable(By.id("i0118"))).sendKeys("P@ssw0rd1234" + Keys.RETURN);
//
//            Thread.sleep(3000);
//           //String loginYesId = envExpected.equalsIgnoreCase("staging") ? "acceptButton" : "idSIButton9";
//            wait.until(ExpectedConditions.elementToBeClickable(By.id("acceptButton"))).click();


            WebElement user = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("i0116")));
            user.sendKeys("test_1231985@outlook.com");
            user.sendKeys(Keys.RETURN);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("passwd")));

            WebElement pwd = wait.until(
                    ExpectedConditions.elementToBeClickable(By.name("passwd")));
            pwd.sendKeys("P@ssw0rd1234");
            pwd.sendKeys(Keys.RETURN);
            WebElement button = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space(text())='Yes']" )));
            button.click();
    }
    public void check_the_homepage(){
        Assert.assertEquals(driver.getCurrentUrl(), "https://dialpadbeta.com/");
    }
}
