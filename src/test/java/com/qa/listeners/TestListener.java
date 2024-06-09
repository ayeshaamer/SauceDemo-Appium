package com.qa.listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.qa.base.AppDriver;
import com.qa.base.AppFactory;
import com.qa.reports.ExtentReport;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class TestListener implements ITestListener {
    private Process adbScreenRecordProcess;

    public void onTestFailure(ITestResult result) {
        if (result.getThrowable() != null) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            result.getThrowable().printStackTrace(printWriter);
            System.out.println(stringWriter.toString());
        }

        File file = ((TakesScreenshot) AppDriver.getDriver()).getScreenshotAs(OutputType.FILE);
        byte[] encoded = null;
        try {
            encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, String> params = new HashMap<>();
        params = result.getTestContext().getCurrentXmlTest().getAllParameters();

        String imagePath = "screenshots" + File.separator + params.get("platformName") + "_" + params.get("platformVersion") + "_" + params.get("deviceName")
                + File.separator + AppFactory.getDateTime() + File.separator + result.getTestClass().getRealClass().getSimpleName() + File.separator
                + result.getName() + ".png";

        String completeImagePath = System.getProperty("user.dir") + File.separator + imagePath;

        try {
            FileUtils.copyFile(file, new File(imagePath));
            Reporter.log("This is the sample Screenshot");
            Reporter.log("<a href='" + completeImagePath + "'> <img src='" + completeImagePath + "' height='100' width='100'/> </a>");
        } catch (IOException e) {
            e.printStackTrace();
        }

        ExtentReport.getTest().fail("Test Fail", MediaEntityBuilder.createScreenCaptureFromBase64String(new String(encoded, StandardCharsets.US_ASCII)).build());
        stopRecording(result,"fail");
        ExtentReport.getTest().fail(result.getThrowable());
    }

    @Override
    public void onTestStart(ITestResult result) {
        startRecording(result);
        ExtentReport.startTest(result.getName(), result.getMethod().getDescription())
                .assignCategory(AppDriver.getPlatformName() + "-" + AppDriver.getDeviceName())
                .assignAuthor("Ayesha Amer");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        stopRecording(result,"pass");
        ExtentReport.getTest().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReport.getTest().log(Status.SKIP, "Test Skipped");
    }


    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReport.getExtentReports().flush();
    }

    private void startRecording(ITestResult result) {
        Map<String, String> params = new HashMap<>();
        params = result.getTestContext().getCurrentXmlTest().getAllParameters();

        String videoPath = "videos" + File.separator + params.get("platformName") + "_" + params.get("platformVersion") + "_" + params.get("deviceName")
                + File.separator + AppFactory.getDateTime() + File.separator + result.getTestClass().getRealClass().getSimpleName() + File.separator
                + result.getName() + ".mp4";

        String videoDir = System.getProperty("user.dir") + File.separator + videoPath;
        File file = new File(videoDir);

        if (!file.exists()) {
            file.mkdirs();
        }

        try {
            adbScreenRecordProcess = new ProcessBuilder("adb", "shell", "screenrecord", "/sdcard/testvideo.mp4").start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording(ITestResult result, String status) {
        if (adbScreenRecordProcess != null) {
            adbScreenRecordProcess.destroy();
            Map<String, String> params = new HashMap<>();
            params = result.getTestContext().getCurrentXmlTest().getAllParameters();

            String videoPath = "videos" + File.separator + params.get("platformName") + "_" + params.get("platformVersion") + "_" + params.get("deviceName")
                    + File.separator + AppFactory.getDateTime() + File.separator + result.getTestClass().getRealClass().getSimpleName() + File.separator
                    + result.getName() + ".mp4";

            String videoDir = System.getProperty("user.dir") + File.separator + videoPath;
            String videoFilePathWithStatus = videoDir + File.separator + result.getTestClass().getRealClass().getSimpleName() + "_" + result.getName() + ".mp4"; // Unique file name based on test class name, method name, and status
            try {
                // To make videos ready
                Thread.sleep(2000);

                // Pull video file from emulator to local machine
                Process pullProcess = new ProcessBuilder("adb", "pull", "/sdcard/testvideo.mp4", videoFilePathWithStatus).start();
                pullProcess.waitFor();

                // Convert the video file to a Base64 string
                byte[] encoded = null;
                try {
                    encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(new File(videoFilePathWithStatus)));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Convert the video data to a data URI
                String videoDataURI = "data:video/mp4;base64," + new String(encoded, StandardCharsets.US_ASCII);

                // Create a video tag with the data URI as source
                String videoEmbed = "<video width='320' height='240' controls><source src='" + videoDataURI + "' type='video/mp4'></video>";

                // Log the video in the Extent Report as raw text
                if (status == "pass")
                    ExtentReport.getTest().pass("<b>Test Video:</b><br> " + videoEmbed);
                else
                    ExtentReport.getTest().fail("<b>Test Video:</b><br> " + videoEmbed);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
