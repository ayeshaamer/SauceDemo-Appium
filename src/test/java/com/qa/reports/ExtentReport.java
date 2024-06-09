package com.qa.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.util.HashMap;
import java.util.Map;

public class ExtentReport {
    static ExtentReports extentReports;
    final static String filepath = "target/Spark.html";
    static Map<Integer, ExtentTest> extentTestMap = new HashMap<>();

    public synchronized static ExtentReports getExtentReports() {
        if (extentReports == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter(filepath);
            spark.config().setDocumentTitle("Sauce Demo App Automation using Appium");
            spark.config().setReportName("Test Execution Report");
            spark.config().setTheme(Theme.DARK);

            extentReports = new ExtentReports();
            extentReports.attachReporter(spark);
        }
        return extentReports;
    }

    public static synchronized ExtentTest getTest() {
        return extentTestMap.get((int) (long) (Thread.currentThread().getId()));
    }

    public static synchronized ExtentTest startTest(String testName, String testDescription) {
        ExtentTest test = getExtentReports().createTest(testName, testDescription);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
        return test;
    }

}
