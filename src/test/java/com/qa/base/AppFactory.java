package com.qa.base;

import com.qa.utils.ConfigReader;
import com.qa.utils.Utilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;


public class AppFactory {
    public static AppiumDriver driver;
    public static ConfigReader configReader;
    protected static HashMap<String, String> stringHashMap = new HashMap<>();
    InputStream stringIs;
    Utilities utilities;
    static Logger log = LogManager.getLogger(AppFactory.class.getName());

    @BeforeTest
    @Parameters({"platformName", "platformVersion", "deviceName"})
    public void initializer(String platformName, String platformVersion, String deviceName) throws Exception {
        try {
            log.debug("This is debug message");
            log.info("This is info message");
            log.warn("This is warn messgae");
            log.error("This is error Messages");
            log.fatal("This is fatal message");

            configReader = new ConfigReader();
            utilities = new Utilities();
            String xmlStringFileName = "strings/strings.xml";
            stringIs = getClass().getClassLoader().getResourceAsStream(xmlStringFileName);
            stringHashMap = utilities.parseStringXML(stringIs);


            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("platformName", platformName);
            capabilities.setCapability("platformVersion", platformVersion);
            capabilities.setCapability("deviceName", deviceName);
            capabilities.setCapability("appPackage",  configReader.getAppPackage());
            capabilities.setCapability("appActivity", configReader.getAppActivity());
            capabilities.setCapability("newCommandTimeout", configReader.getCommandTimeout());
            capabilities.setCapability("automationName", configReader.getAutomationName());
            capabilities.setCapability("noReset", configReader.getNoReset());
            capabilities.setCapability("app", System.getProperty("user.dir") + configReader.getApkPath());
            driver = new AndroidDriver(new URL(configReader.getAppiumServerAndroidURL()), capabilities);
            AppDriver.setDriver(driver);
            System.out.println("Android Driver is Set");
        }catch (Exception exception){
            exception.printStackTrace();
            throw exception;
        }finally {
            if(stringIs != null)
                stringIs.close();
        }
    }

    public void waitForVisibility(WebElement element){
        new WebDriverWait(driver, Duration.ofSeconds(Utilities.WAIT)).until(ExpectedConditions.visibilityOf(element));
    }

    public void clickElement(WebElement element){
        waitForVisibility(element);
        element.click();
    }

    public void sendKeys(WebElement element, String text){
        waitForVisibility(element);
        element.sendKeys(text);
    }


    public String getAttribute(WebElement element, String attribute){
        waitForVisibility(element);
        return element.getAttribute(attribute);
    }

    @AfterTest
    public static void quitDriver(){
        if(driver!=null)
            driver.quit();
    }
}
