package com.qa.base;

import com.aventstack.extentreports.Status;
import com.qa.reports.ExtentReport;
import com.qa.utils.ConfigReader;
import com.qa.utils.Utilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class AppFactory {
    public static AppiumDriver driver;
    public static ConfigReader configReader;
    protected static HashMap<String, String> stringHashMap = new HashMap<>();
    protected static String dateTime;
    private static AppiumDriverLocalService server;
    InputStream stringIs;
    public Utilities utilities = new Utilities();
    static Logger log = LogManager.getLogger(AppFactory.class.getName());

    @BeforeSuite
    public void upAndRunningAppiumServer() {
        server = getAppiumServerDefault();
        if (!utilities.checkIfAppiumServerisRunning(4723)) {
            server.start();
            server.clearOutPutStreams();
            utilities.log().info("Starting Appium Server");
        } else {
            utilities.log().info("Appium Server is already up and running");
        }
    }

    @AfterSuite
    public void shutDownServer() {
        server.stop();
        utilities.log().info("Shutting Down Appium Server");
    }

    public AppiumDriverLocalService getAppiumServerDefault() {
        return AppiumDriverLocalService.buildDefaultService();
    }

    @BeforeTest
    @Parameters({"platformName", "platformVersion", "deviceName"})
    public void initializer(String platformName, String platformVersion, String deviceName) throws Exception {
        try {
            configReader = new ConfigReader();
            String xmlStringFileName = "strings/strings.xml";
            stringIs = getClass().getClassLoader().getResourceAsStream(xmlStringFileName);
            stringHashMap = utilities.parseStringXML(stringIs);

            dateTime = utilities.getDateTime();

            AppDriver.setPlatformName(platformName);
            AppDriver.setDeviceName(deviceName);

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("platformName", platformName);
            capabilities.setCapability("platformVersion", platformVersion);
            capabilities.setCapability("deviceName", deviceName);
            capabilities.setCapability("appPackage", configReader.getAppPackage());
            capabilities.setCapability("appActivity", configReader.getAppActivity());
            capabilities.setCapability("newCommandTimeout", configReader.getCommandTimeout());
            capabilities.setCapability("automationName", configReader.getAutomationName());
            capabilities.setCapability("noReset", configReader.getNoReset());
            capabilities.setCapability("app", System.getProperty("user.dir") + configReader.getApkPath());

            driver = new AndroidDriver(new URL(configReader.getAppiumServerAndroidURL()), capabilities);
            utilities.log().info("appURL is {}", configReader.getAppiumServerAndroidURL());
            AppDriver.setDriver(driver);
            utilities.log().info("Android Driver is Set");
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        } finally {
            if (stringIs != null)
                stringIs.close();
        }
    }

    public void waitForVisibility(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(Utilities.WAIT)).until(ExpectedConditions.visibilityOf(element));
    }

    public void clickElement(WebElement element, String message) {
        waitForVisibility(element);
        utilities.log().info(message);
        ExtentReport.getTest().log(Status.INFO, message);
        element.click();
    }

    public void sendKeys(WebElement element, String text, String message) {
        waitForVisibility(element);
        utilities.log().info(message);
        ExtentReport.getTest().log(Status.INFO, message);
        element.sendKeys(text);
    }

    public String getAttribute(WebElement element, String attribute) {
        waitForVisibility(element);
        return element.getAttribute(attribute);
    }

    public String getText(WebElement element, String message) {
        String elementText = null;
        waitForVisibility(element);
        elementText = getAttribute(element, "text");
        utilities.log().info("{}{}", message, elementText);
        ExtentReport.getTest().log(Status.INFO, message + elementText);
        return elementText;
    }

    public static String getDateTime() {
        return dateTime;
    }

    @AfterTest
    public static void quitDriver() {
        if (driver != null)
            driver.quit();
    }
}
