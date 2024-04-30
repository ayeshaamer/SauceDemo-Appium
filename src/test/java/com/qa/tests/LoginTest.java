package com.qa.tests;

import com.qa.base.AppFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductPage;

import java.lang.reflect.Method;


public class LoginTest extends AppFactory {
  LoginPage loginPage;
  ProductPage productPage;

  @BeforeMethod
  public void setup(Method method){
      loginPage = new LoginPage();
      System.out.println("****** Starting Test: "+method.getName()+" *******");
  }

  @Test
  public void verifyInvalidUsername(){
      System.out.println("This test is used to verify that user will get error message on entering invalid username");
      loginPage.enterUsername("invalidUsername");
      loginPage.enterPassword("secret_sauce");
      loginPage.clickLoginButton();

      String expectedErrorMessage="Username and password do not match any user in this service.";
      String actualErrorMessage = loginPage.getErrorMessage();
      System.out.println("Actual Error Message: "+actualErrorMessage+"\nExpected Error Message: "+expectedErrorMessage);
      Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
  }

  @Test
  public void verifyInvalidPassword(){
      System.out.println("This test is used to verify that user will get error message on entering invalid password");
      loginPage.enterUsername("standard_user");
      loginPage.enterPassword("invalidPassword");
      loginPage.clickLoginButton();

      String expectedErrorMessage="Username and password do not match any user in this service.";
      String actualErrorMessage = loginPage.getErrorMessage();
      System.out.println("Actual Error Message: "+actualErrorMessage+"\nExpected Error Message: "+expectedErrorMessage);
      Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
  }
  @Test
  public void verifyValidLogin(){
      System.out.println("This test is used to verify that user will see Product page on successful login on entering valid username & password");
      loginPage.enterUsername("standard_user");
      loginPage.enterPassword("secret_sauce");
      productPage = loginPage.clickLoginButton();

      String actualTitle = productPage.getTitle();
      String expectedTitle = "PRODUCTS";
      System.out.println("Actual Product title: "+actualTitle+"\nExpected Product Title: "+expectedTitle);
      Assert.assertEquals(actualTitle, expectedTitle);
  }

  @AfterTest
  public void tearDown(){
      AppFactory.quitDriver();
  }
}
