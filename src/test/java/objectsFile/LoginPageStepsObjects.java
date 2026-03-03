package objectsFile;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.lang.Thread;


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

//        WebElement login = wait.until(
//                ExpectedConditions.elementToBeClickable(By.xpath("//span[contains( text() , 'Microsoft')]/ancestor::button"))
//        );
//        login.click();
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
                ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='email']")));
            user.sendKeys("automationuser1.jeeva@gmail.com");
            user.sendKeys(Keys.RETURN);

            WebElement pwd = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='password']")));
            pwd.sendKeys("Dialpad@12345");
            pwd.sendKeys(Keys.RETURN);

            Thread.sleep(3000);

//            WebElement button = wait.until(
//                    ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']" )));
//            button.click();
    }
//    public void check_the_homepage(){
//        Assert.assertEquals(driver.getCurrentUrl(), "https://dialpadbeta.com/");
//    }
}
