import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ConnectionString {
    AppiumDriver driver;
    @BeforeTest
    public void initializer() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "emulator-5554");
        capabilities.setCapability("automationName","UiAutomator2");
        capabilities.setCapability("app",System.getProperty("user.dir")+"/app/ApiDemos-debug.apk");
        capabilities.setCapability("noReset", false);
        capabilities.setCapability("newCommandTimeout",120);
        capabilities.setCapability("avd", "Pixel_3a");
        capabilities.setCapability("avdLaunchTimeout", 3000000);

        driver = new AndroidDriver( new URL("http://localhost:4723"), capabilities);
        System.out.println("Opening Appium Server");
    }
    @Test
    public void sampleTest(){
        System.out.println("First Sample Test");
    }

//    @AfterTest
//    public void tearDown(){
//        driver.quit();
//    }

    @AfterTest
    public void tearDown(){
        String cmdString = "abd -s emulator-5554 emu kill";
        if(null!=driver){
            try{
                Runtime.getRuntime().exec("CMD /C " + cmdString);
                driver.quit();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
