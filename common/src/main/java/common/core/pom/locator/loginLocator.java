package common.core.pom.locator;

import org.openqa.selenium.By;

public class loginLocator
{
    protected By txtUserName;

    protected By txtPass;

    protected By btnLogin;

    protected By changPass;

    protected String menuItem;

    protected String subMenuItem;

    protected By settingIcon;

    protected By logOut;

    public void loginLocators()
    {
        txtUserName = By.id("userId");
        txtPass = By.id("password");
        btnLogin = By.xpath("//button[@type='submit']");
        changPass = By.xpath("//div/span[text()='Change Password']");
        menuItem = "//li[contains(@class,'ui-menuitem')]//span[text()='menuItemText']";
        subMenuItem = "//li[contains(@class,'ui-menuitem')]//span[text()='subMenuItemText']";
        settingIcon = By.xpath("//button//span[contains(@class,'settings-icon')]");
        logOut = By.xpath("//button//span[contains(@class,'lock-break-icon')]");
    }



}
