import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class ConnectionStringWithOption {

    AppiumDriver driver;

    @BeforeTest
    public void initializer() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("emulator-5554")
                .setAutomationName("UiAutomator2")
                .setApp(System.getProperty("user.dir")+"/app/ApiDemos-debug.apk")
                .setNewCommandTimeout(Duration.ofSeconds(120))
                .setAvd("Pixel_3a")
                .setAvdLaunchTimeout(Duration.ofSeconds(3000000))
                .setNoReset(false);

        //Assignment from Session Two > Added capabilities in Options method, and code is working....

        URL url = new URL("http://localhost:4723");

        driver = new AndroidDriver(url, options);

        System.out.println("Opening Appium server using Option Class");
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
