package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class TestObject {
    public WebDriver driver;
    public WebDriverWait wait;

    public TestObject(){
        this.driver= new ChromeDriver();
        driver.manage().window().maximize();
        this.wait= new WebDriverWait(driver, Duration.ofSeconds(20));
    }
}
