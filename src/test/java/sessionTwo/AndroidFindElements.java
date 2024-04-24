package sessionTwo;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

public class AndroidFindElements {
    public static void main(String[] args) throws Exception {
        AppiumDriver driver = CreateDriverSession.initializerDriver("Android");
        WebElement webElement = driver.findElement(AppiumBy.accessibilityId("Accessibility"));
        System.out.println(webElement.getText());

        webElement = driver.findElements(AppiumBy.className("android.widget.TextView")).get(2);
        System.out.println(webElement.getText());

        webElement = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc=\"Accessibility\"]"));
        System.out.println(webElement.getText());

        webElement = driver.findElement(AppiumBy.xpath("//*[@text=\"Accessibility\"]"));
        System.out.println(webElement.getText());

        webElement = driver.findElements(AppiumBy.id("android:id/text1")).get(1);
        System.out.println(webElement.getText());
    }
}
