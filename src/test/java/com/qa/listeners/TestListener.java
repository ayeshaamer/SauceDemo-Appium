package com.qa.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.PrintWriter;
import java.io.StringWriter;

public class TestListener implements ITestListener {
    public void onTestFailure(ITestResult result){
        if(result.getThrowable() != null){
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            result.getThrowable().printStackTrace(printWriter);
            System.out.println(stringWriter.toString());
        }
    }
    @Override
    public void onTestStart(ITestResult result){

    }

    @Override
    public void onTestSuccess(ITestResult result){

    }

    @Override
    public void onTestSkipped(ITestResult result){

    }


    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result){

    }

    @Override
    public void onStart(ITestContext context){

    }

    @Override
    public void onFinish(ITestContext context){

    }


}
