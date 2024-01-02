package common.core.pom.pages;

import common.core.pom.locator.accountLocator;
import common.shared.Utility.UtlActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import java.time.Duration;


public class accountPage extends accountLocator {
    private WebDriver driver;
    UtlActions actions;
    public static Logger log = LogManager.getLogger(accountPage.class);

    public accountPage(WebDriver driver) {
        this.driver = driver;
        actions = new UtlActions(driver);
        accountLocators();
    }

    // Validate page is loaded or not
    public void validatePageLoad() {
        System.out.println("Page Title.." + actions.getText(traderMaintenancePageTitle));
        boolean element =  actions.waitForElementToBeDisplayed(traderMaintenancePageTitle, Duration.ofSeconds(5));
        Assert.assertTrue("Trader details page loaded", element);
    }

    // Click Trader drop down and select one dealer
    public void clickAndSelectTraderDropDown(String dealerCode)
    {
        validatePageLoad();
        actions.click(dealerCodeDD);
        actions.setText(dealerCodeDDTxt, dealerCode);
        System.out.println("Searched Dealer Code is.." + dealerCode);
        actions.waitForElementToBeDisplayed(dealCodeDDSearchedTxt, Duration.ofSeconds(5));
        actions.click(dealCodeDDSearchedTxt);
        actions.click(searchBtn);
    }

    public void clickSaveBtn()
    {
        actions.click(saveBtn);
    }

    // Append new email with existing email
    public String emailAddress(String email) {
        String existingEmail = actions.getTextByValueAttribute(receiverEmailTxt);
        return existingEmail + "," + email;
    }

    public void updateDealerEmailAddress(String emailId,String filepath) {
        actions.click(vehicleTab);
        actions.waitForElementToBeDisplayed(editBtn, Duration.ofSeconds(15));
        actions.click(editBtn);
        String email = emailAddress(emailId);
        actions.clearText(receiverEmailTxt);
        actions.setText(receiverEmailTxt, email);
        actions.screenshot(filepath,"OrderQuantityPopup");
        clickSaveBtn();
        Assert.assertTrue(validateUpdatedEmail().contains(emailId));
    }

    public String validateUpdatedEmail()
    {
        return actions.getTextByValueAttribute(receiverEmailTxt);
    }

    public void validateToastMsg(String expectedTstSummary, String expectedtstDetails)
    {
        String actualTstSummary = actions.getText(toastSummary) ;
        System.out.println("Toast Summary Message.."+actualTstSummary);
        String actualTstDetail = actions.getText(toastDetails) ;
        System.out.println("Toast Details Message.."+actualTstDetail);
//        Assert.assertEquals(actualTstSummary,expectedTstSummary);
        Assert.assertTrue(actualTstDetail.contains(expectedtstDetails));
        actions.click(toastMsgCloseBtn);
    }

    public void validateToastSummary(String expectedTstSummary)
    {
        String actualTstSummary = actions.getText(toastSummary) ;
        Assert.assertEquals(actualTstSummary,expectedTstSummary);
    }

}
