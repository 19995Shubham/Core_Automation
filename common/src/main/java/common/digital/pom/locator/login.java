package pom.locator;

import org.openqa.selenium.By;

public class login
{
    protected By userNameTxt;
    protected By passwordTxt;
    protected By loginBtn;

    public void loginLocators()
    {
        userNameTxt = By.id("LoginControl_UserName");
        passwordTxt = By.id("LoginControl_Password");
        loginBtn = By.xpath("//input[@value='Log in']");
    }
}
