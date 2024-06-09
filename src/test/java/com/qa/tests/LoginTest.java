package com.qa.tests;

import com.qa.base.AppFactory;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductPage;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Objects;


public class LoginTest extends AppFactory {
    LoginPage loginPage;
    ProductPage productPage;
    InputStream inputStream;
    JSONObject loginUser;

    @BeforeClass
    public void setupDataStream() throws IOException {
        try {
            String dataFileName = "data/loginUsers.json";
            inputStream = getClass().getClassLoader().getResourceAsStream(dataFileName);
            JSONTokener jsonTokener = new JSONTokener(Objects.requireNonNull(inputStream));
            loginUser = new JSONObject(jsonTokener);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null)
                inputStream.close();
        }
    }

    @BeforeMethod
    public void setup(Method method) {
        loginPage = new LoginPage();
        utilities.log().info("****** Starting Test: " + method.getName() + " *******");
    }

    @Test
    public void verifyInvalidUsername() {
        utilities.log().info("This test is used to verify that user will get error message on entering invalid username");
        loginPage.enterUsername(loginUser.getJSONObject("invalidUsername").getString("username"));
        loginPage.enterPassword(loginUser.getJSONObject("invalidUsername").getString("password"));
        loginPage.clickLoginButton();

        String expectedErrorMessage = stringHashMap.get("error_invalid_username_and_password");
        String actualErrorMessage = loginPage.getErrorMessage();
        utilities.log().info("Actual Error Message: " + actualErrorMessage + "\nExpected Error Message: " + expectedErrorMessage);
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @Test
    public void verifyInvalidPassword() {
        utilities.log().info("This test is used to verify that user will get error message on entering invalid password");
        loginPage.enterUsername(loginUser.getJSONObject("invalidPassword").getString("username"));
        loginPage.enterPassword(loginUser.getJSONObject("invalidPassword").getString("password"));
        loginPage.clickLoginButton();

        String expectedErrorMessage = stringHashMap.get("error_invalid_username_and_password");
        String actualErrorMessage = loginPage.getErrorMessage();
        utilities.log().info("Actual Error Message: " + actualErrorMessage + "\nExpected Error Message: " + expectedErrorMessage);
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @Test
    public void verifyValidLogin() {
        utilities.log().info("This test is used to verify that user will see Product page on successful login on entering valid username & password");
        loginPage.enterUsername(loginUser.getJSONObject("validUsernameAndPassword").getString("username"));
        loginPage.enterPassword(loginUser.getJSONObject("validUsernameAndPassword").getString("password"));
        productPage = loginPage.clickLoginButton();

        String actualTitle = productPage.getTitle();
        String expectedTitle = stringHashMap.get("product_title");
        utilities.log().info("Actual Product title: " + actualTitle + "\nExpected Product Title: " + expectedTitle);
        Assert.assertEquals(actualTitle, expectedTitle);
    }

    @AfterTest
    public void tearDown() {
        AppFactory.quitDriver();
    }
}
