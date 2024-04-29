package base;

import configurationFileReader.Config.ConfigReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;


public class AppFactory {
    public static AppiumDriver driver;
    public static ConfigReader configReader;

    @BeforeTest
    @Parameters({"platformName", "platformVersion", "deviceName"})
    public void initializer(String platformName, String platformVersion, String deviceName) throws MalformedURLException {
        try {
            configReader = new ConfigReader();
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
        }
    }

    @AfterTest
    public static void quitDriver(){
        if(driver!=null)
            driver.quit();
    }
}
