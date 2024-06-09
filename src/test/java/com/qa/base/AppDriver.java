package com.qa.base;

import com.qa.utils.Utilities;
import org.openqa.selenium.WebDriver;

public class AppDriver {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static final Utilities utilities = new Utilities();
    protected static ThreadLocal<String> platform = new ThreadLocal<>();
    protected static ThreadLocal<String> deviceName = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(WebDriver Driver) {
        driver.set(Driver);
        utilities.log().info("Driver is set");
    }

    public static String getPlatformName() {
        return platform.get();
    }

    public static void setPlatformName(String platformName) {
        platform.set(platformName);
    }

    public static String getDeviceName() {
        return deviceName.get();
    }

    public static void setDeviceName(String deviceName1) {
        deviceName.set(deviceName1);
    }

}
