package common.core.pom.pages;

import common.core.pom.locator.loginLocator;
import common.shared.Utility.UtlActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class loginPage extends loginLocator
{
    private WebDriver driver;
    public static Logger log = LogManager.getLogger(loginPage.class);
    UtlActions actions;
    By menuElement ;
    By subMenuElement;
    String filepath;

    public loginPage(WebDriver driver,String filepath) {
        this.driver = driver;
        this.filepath = filepath;
        actions = new UtlActions(driver);
        loginLocators();
    }

    public boolean validatePageLoad() {
        boolean chkUserTxtDisplayed = actions.waitForElementToBeDisplayed(txtUserName, Duration.ofSeconds(10));
        return chkUserTxtDisplayed;
    }

    public void validateSignInButtonIsDisabled()
    {
        validatePageLoad();
        String loginBtnDisableStatus = actions.getTextByAttributeName(btnLogin, "disabled");
        Assert.assertEquals(loginBtnDisableStatus, "true");
    }

    public boolean setUsername(String userName)
    {
        validateSignInButtonIsDisabled();
        log.info("Logged in User Name is: "+userName);
        return actions.setText(txtUserName, userName);
    }

    public boolean setPassword(String userPass)
    {
        return actions.setText(txtPass, userPass);
    }

    public void validateSignInButtonIsEnabled()
    {
        boolean loginBtnEnableStatus = actions.isEnabled(btnLogin);
        Assert.assertTrue(loginBtnEnableStatus);
    }

    public boolean clickLogin()
    {
        validateSignInButtonIsEnabled();
        return actions.click(btnLogin);
    }
    public boolean clickMenu()
    {
        actions.waitForElementToBeDisplayed(menuElement, Duration.ofSeconds(20));
        return actions.click(menuElement);
    }
    public boolean clickSubMenu() {
        actions.waitForElement(subMenuElement, Duration.ofSeconds(15));
        return actions.click(subMenuElement);
    }

    public void refreshPage()
    {
        boolean changePasswordSec = actions.waitForElementToBeDisplayed(changPass, Duration.ofSeconds(10));
        if(changePasswordSec)
        driver.navigate().refresh();
    }

    public void setLogInCredentials(String userName, String password)
    {
        try {
            validatePageLoad();
            setUsername(userName);
            setPassword(password);
            actions.screenshot(filepath, "login" );
            clickLogin();
            refreshPage();
        }catch(Exception e)
        {
            log.error("Unable to logged in: "+e.getMessage());
        }
    }
    public void clickOnSubmenuItem(String menuName, String subMenuName)
    {
        try {
            Assert.assertTrue(validateLogOutAndSettingElementDisplayed());
            String menu = menuItem.replace("menuItemText", menuName);
            String subMenu = subMenuItem.replace("subMenuItemText", subMenuName);
            menuElement = By.xpath(menu);
            subMenuElement = By.xpath(subMenu);
            log.info("Required Menu: " + menuName);
            log.info("Required Sub menu: " + subMenuName);
            clickMenu();
            clickSubMenu();
        } catch(Exception e)
        {
            log.error("Unable to click on menu/subMenu item: "+e.getMessage());
        }
    }

    public boolean validateLogOutAndSettingElementDisplayed()
    {
        boolean logout = actions.isDisplayed(logOut,5);
        boolean setting = actions.isDisplayed(settingIcon, 5);

        return logout && setting;
    }

}
