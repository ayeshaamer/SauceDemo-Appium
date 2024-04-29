package testCases;

import base.AppFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductPage;


public class LoginTest extends AppFactory {
  LoginPage loginPage;
  ProductPage productPage;

  @BeforeMethod
  public void setup(){
      loginPage = new LoginPage();
      productPage = new ProductPage();
  }

  @Test
  public void verifyValidUserLogin() throws InterruptedException {
      System.out.println("Test Case 1: Valid Username and Password");
      loginPage.enterValidUsername("standard_user");
      loginPage.enterValidPassword("secret_sauce");
      loginPage.clickLoginButton();
      System.out.println("Login is successful");
      Assert.assertTrue(productPage.isProductHeaderDisplayed());
  }

  @AfterMethod
  public void tearDown(){
      AppFactory.quitDriver();
  }
}
