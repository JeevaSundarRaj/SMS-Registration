package objectsFile;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public class CampaignRegistrationStepsObjects {
    WebDriver driver;
    WebDriverWait wait;

    public CampaignRegistrationStepsObjects(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
    }
    public void navigating_to_campaignPage(){
        driver.get("https://dialpadbeta.com/campaignregistration");
    }
    public void campaignRegistration() throws InterruptedException, IOException {

        String companyNameStr = "";
        String brandNameStr = "";
        String taxIDStr = "";
        String emailIDStr = "";
        String phoneNoStr = "";
        String webSiteStr = "";
        String streetStr = "";
        String cityStr = "";
        String zipCodeStr = "";
        String commPurposeStr = "";
        String customerSupportStr = "";
        String cSupportReasonStr = "";
        String supportWebStr = "";
        String commPurpose2Str = "";
        String commPurpose3Str = "";
        String commPurpose4Str = "";
        String commPurpose5Str = "";
        String commPurpose6Str = "";
        String webSite2Str = "";


        String resourceName = "test.properties"; // could also be a constant
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        Properties properties = new Properties();

        try (InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
            properties.load(resourceStream);
            companyNameStr = properties.getProperty("companyName");
            brandNameStr = properties.getProperty("brandName");
            taxIDStr = properties.getProperty("taxID");
            emailIDStr = properties.getProperty("emailID");
            phoneNoStr = properties.getProperty("phoneNo");
            webSiteStr = properties.getProperty("webSite");
            streetStr = properties.getProperty("street");
            cityStr = properties.getProperty("city");
            zipCodeStr = properties.getProperty("zipCode");
            commPurposeStr = properties.getProperty("commPurpose");
            customerSupportStr = properties.getProperty("customerSupport");
            cSupportReasonStr = properties.getProperty("cSupportReason");
            supportWebStr = properties.getProperty("supportWeb");
            commPurpose2Str = properties.getProperty("commPurpose2");
            commPurpose3Str = properties.getProperty("commPurpose3");
            commPurpose4Str = properties.getProperty("commPurpose4");
            commPurpose5Str = properties.getProperty("commPurpose5");
            commPurpose6Str = properties.getProperty("commPurpose6");
            webSite2Str = properties.getProperty("webSite2");
        } catch (IOException e) {
            e.printStackTrace();
        }


        driver.get("https://dialpadbeta.com/campaignregistration");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement registerBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Register my brand and campaign']"))
        );
        registerBtn.click();

        WebElement privateProfitBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='PRIVATE_PROFIT']"))
        );
        privateProfitBtn.click();

        WebElement companyName = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//body[1]/div[1]/div[3]/div[2]/div[2]/div[1]/main[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[2]/label[1]"))
        );
        companyName.sendKeys(companyNameStr);

        WebElement brandName = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//body[1]/div[1]/div[3]/div[2]/div[2]/div[1]/main[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[3]/label[1]"))
        );
        brandName.sendKeys(brandNameStr);

        WebElement dropdownElement1 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//select[@name='brand-company-tax-id-country']"))
        );

        Select dropdown = new Select(dropdownElement1);
        dropdown.selectByValue("us");

        WebElement taxID = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//body[1]/div[1]/div[3]/div[2]/div[2]/div[1]/main[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[5]/label[1]"))
        );
        taxID.sendKeys(taxIDStr);

        WebElement emailID = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//body[1]/div[1]/div[3]/div[2]/div[2]/div[1]/main[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[6]/label[1]"))
        );
        emailID.sendKeys(emailIDStr);

        WebElement phoneNo = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//body[1]/div[1]/div[3]/div[2]/div[2]/div[1]/main[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[7]/label[1]"))
        );
        phoneNo.sendKeys(phoneNoStr);

        WebElement dropdownElement2 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//select[@name='brand-company-industry']"))
        );

        Select dropdown2 = new Select(dropdownElement2);
        dropdown2.selectByValue("TECHNOLOGY");

        WebElement webSite = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//body[1]/div[1]/div[3]/div[2]/div[2]/div[1]/main[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[9]/label[1]"))
        );
        webSite.sendKeys(webSiteStr);

        WebElement dropdownElement3 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//select[@name='brand-company-country']"))
        );

        Select dropdown3 = new Select(dropdownElement3);
        dropdown3.selectByValue("us");

        WebElement street = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("(//label[@class='d-input__label'])[8]"))
        );
        street.sendKeys(streetStr);

        WebElement city = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//body[1]/div[1]/div[3]/div[2]/div[2]/div[1]/main[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[3]/div[3]/div[1]/label[1]"))
        );
        city.sendKeys(cityStr);

        WebElement dropdownElement4 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//select[@name='brand-company-region']"))
        );

        Select dropdown4 = new Select(dropdownElement4);
        dropdown4.selectByValue("CA");

        WebElement zipCode = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//body[1]/div[1]/div[3]/div[2]/div[2]/div[1]/main[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[3]/div[3]/div[3]/label[1]"))
        );
        zipCode.sendKeys(zipCodeStr);

        WebElement nextBtn1 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Next']"))
        );
        nextBtn1.click();


        //campaign description page

        WebElement commPurpose = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='d-mt16 d-input__root']//label[@class='d-input__label']"))
        );
        commPurpose.sendKeys(commPurposeStr);

        WebElement radio1 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//body[1]/div[1]/div[3]/div[2]/div[2]/div[1]/main[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[4]/div[1]/fieldset[1]/div[1]/label[1]/div[1]/input[1]"))
        );
        radio1.click();

        WebElement radio2 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//body[1]/div[1]/div[3]/div[2]/div[2]/div[1]/main[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[4]/div[2]/fieldset[1]/div[1]/label[1]/div[1]/input[1]"))
        );
        radio2.click();

        WebElement checkBox1 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='verbal']"))
        );
        checkBox1.click();

        WebElement radio3 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='in person']"))
        );
        radio3.click();

        WebElement customerSupport = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Ex: Customer support agent']"))
        );
        customerSupport.sendKeys(customerSupportStr);

        WebElement cSupportReason = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Ex: Marketing, Product updates, etc.']"))
        );
        cSupportReason.sendKeys(cSupportReasonStr);

        WebElement radio4 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='in a customer database']"))
        );
        radio4.click();

        WebElement checkBox2 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='verbal-consent']"))
        );
        checkBox2.click();

//        WebElement commPurpose2 = wait.until(
//                ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='d-my24 d-input__root']//label[@class='d-input__label']"))
//        );
//        commPurpose2.sendKeys("Contact support_help1@dialpad.com to stop receiving notifications from the organiser. Text STOP to unsubscribe from all Dialpad Dialer notifications including reminders or text HELP to get more details.");

        WebElement radio5 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='d-mr12 d-mt8']//input[@name='recurrence-pattern']"))
        );
        radio5.click();

        WebElement checkBox3 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='d-d-flex d-fd-row d-ai-center d-mt8']//div//input[@type='checkbox']"))
        );
        checkBox3.click();

        WebElement checkBox4 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='employees']"))
        );
        checkBox4.click();

        WebElement checkBox5 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='customers']"))
        );
        checkBox5.click();

        WebElement supportWeb = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='d-mt24 d-input__root']//label[@class='d-input__label']"))
        );
        supportWeb.sendKeys(supportWebStr);

        WebElement nxtBtn2 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='d-ml12 base-button__button d-btn d-btn--primary']"))
        );
        nxtBtn2.click();

        //Messaging Types Page


        WebElement checkBox6 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='AUTOMATED_DRIVEN_COMMUNICATION']"))
        );
        checkBox6.click();

        WebElement checkBox7 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='CONVERSATIONAL_MESSAGES']"))
        );
        checkBox7.click();

        WebElement nxtBtn3 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='d-ml12 base-button__button d-btn d-btn--primary']"))
        );
        nxtBtn3.click();


        // Messaging Example Page

        WebElement dropdownElement5 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//select[@name='label-use-case']"))
        );

        Select dropdown5 = new Select(dropdownElement5);
        dropdown5.selectByValue("CUSTOMER_CARE");

        WebElement commPurpose2 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//body[1]/div[1]/div[3]/div[2]/div[2]/div[1]/main[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/label[1]"))
        );
        commPurpose2.sendKeys(commPurpose2Str);

        WebElement commPurpose3 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//body[1]/div[1]/div[3]/div[2]/div[2]/div[1]/main[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[3]/div[1]/div[1]/div[2]/div[2]/div[2]/div[1]/label[1]"))
        );
        commPurpose3.sendKeys(commPurpose3Str);

        WebElement nxtBtn4 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='d-ml12 base-button__button d-btn d-btn--primary']"))
        );
        nxtBtn4.click();

        //Content Message

        WebElement commPurpose4 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//body[1]/div[1]/div[3]/div[2]/div[2]/div[1]/main[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/label[1]"))
        );
        commPurpose4.sendKeys(commPurpose4Str);

        WebElement checkBox8 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("(//input[@value='use-custom-message'])[1]"))
        );
        checkBox8.click();

        WebElement commPurpose5 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//body[1]/div[1]/div[3]/div[2]/div[2]/div[1]/main[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[3]/div[1]/div[1]/label[1]"))
        );
        commPurpose5.sendKeys(commPurpose5Str);

        WebElement checkBox9 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("(//input[@value='use-custom-message'])[2]"))
        );
        checkBox9.click();

        WebElement commPurpose6 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//body[1]/div[1]/div[3]/div[2]/div[2]/div[1]/main[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[4]/div[1]/div[1]/label[1]"))
        );
        commPurpose6.sendKeys(commPurpose6Str);

        WebElement checkBox10 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("(//input[@value='use-custom-message'])[3]"))
        );
        checkBox10.click();

        WebElement nxtBtn5 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='d-ml12 base-button__button d-btn d-btn--primary']"))
        );
        nxtBtn5.click();

        //privacy statement

        WebElement webSite2 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='d-mt16 d-mb16 d-input__root']//label[@class='d-input__label']"))
        );
        webSite2.sendKeys(webSite2Str);

        WebElement checkBox11 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='I understand that if my website accepts phone numbers that the submission form must include a link to the Privacy Policy.']"))
        );
        checkBox11.click();

        WebElement checkBox12 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='My Privacy Policy indicates what information is being collected, how it is used, and how users can opt-out.']"))
        );
        checkBox12.click();

        WebElement checkBox13 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='My Privacy Policy clearly states that mobile phone contact information will not be shared or sold to third parties for marketing purposes.']"))
        );
        checkBox13.click();

        WebElement nxtBtn6 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='d-ml12 base-button__button d-btn d-btn--primary']"))
        );
        nxtBtn6.click();


        //Summary page

        WebElement checkBox14 = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='checkbox']"))
        );
        checkBox14.click();

        WebElement submitBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Submit registration']"))
        );
        submitBtn.click();
        }


        public void registeredSuccessfully(){

        WebElement successMsg = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//h1[contains(text(),'You’ve submitted your registration!')]"))
        );

        System.out.println(successMsg.getText());

        Assert.assertEquals(successMsg.getText(), "You’ve submitted your registration!");

        // testing the sms on dialpad app

//        driver.get("https://dialpadbeta.com/app/history/new");
//
//        WebElement dialpBotMsg = wait.until(
//                ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='aff-name-text selectable body-copy-8'][normalize-space()='Dialbot']"))
//        );
//        dialpBotMsg.click();
//
//        WebElement dialpBotMsgContent = wait.until(
//                ExpectedConditions.elementToBeClickable(By.xpath("//li[@id='dt142']//div[@class='dt-item-layout']"))
//        );
//        Assert.assertEquals(dialpBotMsgContent.getText(),"Your organization’s SMS registration just received a status update. Please log into your  campaign registration page .");


        //driver.get("https://dialpadbeta.com/campaignregistration");


        int maxRetries = 8;
        int attempt = 0;
        boolean elementFound = false;

        while (attempt < maxRetries && !elementFound) {
            try {
                Thread.sleep(2000);
                driver.navigate().refresh();
                WebElement confirmMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@class='d-d-flex d-ai-center d-fc-green']")
                ));

                if (confirmMsg != null && confirmMsg.getText().split(" ")[0].trim().equals("Registered")) {
                    elementFound = true;
                    System.out.println("Element found with the expected text: " + confirmMsg.getText());
                } else {
                    System.out.println("Element found, but text is not as expected.");
                }
            } catch (Exception e) {
                System.out.println("Element not found on attempt " + (attempt + 1));
            }

            attempt++;
        }

        WebElement confirmMsg = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='d-d-flex d-ai-center d-fc-green']"))
        );
        Assert.assertEquals(confirmMsg.getText().split(" ")[0].trim(), "Registered");
    }

    

    public void deleteCampaign(){

        //deleting the registration

        WebElement menuIcon_brand = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("(//button[@aria-label='show menu actions'])[1]"))
        );
        menuIcon_brand.click();

        WebElement delete = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'Delete')]"))
        );
        delete.click();

        WebElement deleteBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Delete']"))
        );
        deleteBtn.click();

//        WebElement menuIcon_campaign = wait.until(
//                ExpectedConditions.elementToBeClickable(By.xpath("//tr[@class='d-body--md-compact d-fc-tertiary d-bb d-bbw1 d-bc-default']//td[@class='d-p0 d-ta-center']//div//div[@class='d-popover']"))
//        );
//
//       menuIcon_campaign.click();
//        WebElement delete1 = wait.until(
//                ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'Delete')]"))
//        );
//        delete1.click();
//
//        WebElement deleteBtn1 = wait.until(
//                ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Delete']"))
//        );
//        deleteBtn1.click();



    }


}
