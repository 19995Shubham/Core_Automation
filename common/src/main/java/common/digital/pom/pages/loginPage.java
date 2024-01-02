//package pom.pages;
//
//import Utility.UtlActions;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.junit.Assert;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import pom.locator.login;
//
//import java.time.Duration;
//
//public class loginPage extends login
//{
//    private WebDriver driver;
////    UtlActions actions;
//
//    public loginPage(WebDriver driver, String filepath) {
//        this.driver = driver;
//        loginLocators();
//    }
//
//    public boolean validatePageLoad() {
//        boolean chkUserTxtDisplayed = actions.waitForElementToBeDisplayed(txtUserName, Duration.ofSeconds(10));
//        return chkUserTxtDisplayed;
//    }
//
//    public boolean setUsername(String userName)
//    {
//        log.info("Logged in User Name is: "+userName);
//        return actions.setText(txtUserName, userName);
//    }
//
//    public boolean setPassword(String userPass)
//    {
//        return actions.setText(txtPass, userPass);
//    }
//
//    public void validateSignInButtonIsEnabled()
//    {
//        boolean loginBtnEnableStatus = actions.isEnabled(btnLogin);
//        Assert.assertTrue(loginBtnEnableStatus);
//    }
//
//    public boolean clickLogin()
//    {
//        validateSignInButtonIsEnabled();
//        return actions.click(btnLogin);
//    }
//
//    public void setLogInCredentials(String userName, String password)
//    {
//        try {
//            validatePageLoad();
//            setUsername(userName);
//            setPassword(password);
//            clickLogin();
//            refreshPage();
//        }catch(Exception e)
//        {
//        }
//    }
//
//}
