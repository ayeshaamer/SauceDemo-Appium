package com.qa.pages;

import com.qa.base.AppDriver;
import com.qa.base.AppFactory;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;


public class LoginPage extends AppFactory {
    public LoginPage() {
        PageFactory.initElements(new AppiumFieldDecorator(AppDriver.getDriver()), this);
    }

    @AndroidFindBy(accessibility = "test-Username")
    public WebElement userNameTextBox;

    @AndroidFindBy(accessibility = "test-Password")
    public WebElement passwordTextBox;

    @AndroidFindBy(accessibility = "test-LOGIN")
    public WebElement loginButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Username and password do not match any user in this service.\"]")
    public WebElement errorMsg;


    public void enterUsername(String username) {
        sendKeys(userNameTextBox, username, "Entering Username: " + username);
    }

    public void enterPassword(String password) {
        sendKeys(passwordTextBox, password, "Entering Password: " + password);
    }

    public ProductPage clickLoginButton() {
        clickElement(loginButton, "Clicking on Login Button");
        return new ProductPage();
    }

    public String getErrorMessage() {
        return getText(errorMsg, "Error Text is: ");
    }
}
