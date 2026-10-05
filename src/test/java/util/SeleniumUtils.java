package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

import static util.TestConstants.*;

public final class SeleniumUtils {
    private WebDriver driver;
    private static final Logger logger = LogManager.getLogger(SeleniumUtils.class);
    public static final int MAX_RETRY_COUNT = 3;

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement waitForClickableElement(By locator) {
        return new WebDriverWait(driver, WAIT_DURATION_MAX)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitForElement(By locator, Duration timeout) {
        try {
            return new WebDriverWait(driver, timeout)
                    .until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public WebElement waitForVisibleElement(By locator, Duration timeout) {
        return new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public List<WebElement> waitForListOfElements(By locator, Duration timeout) {
        try {
            return new WebDriverWait(driver, timeout)
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        } catch (TimeoutException e) {
            logger.info("Elements not found within specified wait time, returning empty list");
            return Collections.emptyList();
        }
    }

    public void click(WebElement element, Duration timeout) {
        new WebDriverWait(driver, timeout)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOf(element))
                .click();
    }

    public void click(By locator, Duration timeout) {
        new WebDriverWait(driver, timeout)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(locator))
                .click();
    }

    public void waitTillElementClickableAndClick(By locator) {
        int retries = MAX_RETRY_COUNT;
        while (retries > 0) {
            try {
                waitForClickableElement(locator).click();
                return;
            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                logger.info("Element not clickable, retrying: {}", e.getMessage());
                retries--;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public boolean isDisplayed(By locator, Duration timeout) {
        try {
            return new WebDriverWait(driver, timeout)
                    .until(ExpectedConditions.visibilityOfElementLocated(locator))
                    .isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public void selectByValue(By locator, String value) {
        new Select(waitForVisibleElement(locator, WAIT_DURATION_MID)).selectByValue(value);
    }


    public void enterText(By locator, String text) {
        waitForVisibleElement(locator, WAIT_DURATION_MID).sendKeys(text);
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void waitForInvisibility(By locator, Duration timeout) {
        new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void waitTillPageLoads(WebDriver driver) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new WebDriverWait(driver, WAIT_DURATION_MID).until(
                d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
    }
}
