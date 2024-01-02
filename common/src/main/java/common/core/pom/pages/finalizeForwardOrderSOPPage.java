package common.core.pom.pages;

import common.core.pom.locator.finalizeForwardOrderSOPLocator;
import common.shared.Utility.UtlActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.*;


public class finalizeForwardOrderSOPPage extends finalizeForwardOrderSOPLocator {
    private WebDriver driver;
    public static Logger log = LogManager.getLogger(finalizeForwardOrderSOPPage.class);
    UtlActions actions;

    public finalizeForwardOrderSOPPage(WebDriver driver) {
        this.driver = driver;
        actions = new UtlActions(driver);
        finalizeForwardOrderSOPLocators();
    }

    // Validate page is loaded or not
    public boolean validateFinalizeForwardOrderSOPPageLoad(String filepath) {
        log.info("Page Title.." + actions.getText(finalizeForwardOrderSOPTitle));
        boolean element =  actions.waitForElementToBeDisplayed(finalizeForwardOrderSOPTitle, Duration.ofSeconds(5));
        actions.screenshot(filepath,"FinalizeSOP");
        return element;
    }

    public void selectForwardIdValueFromDropDown(String fId)
    {
        try {
            actions.waitForElement(forwardOrderIdTxt, Duration.ofSeconds(15));
            actions.setText(forwardOrderIdTxt, fId);
            List<WebElement> fIdname = driver.findElements(forwardOrderIdDDValues);
            System.out.println("Required Forward Id is.." + fId);
            for(WebElement forwardID:fIdname)
            {
                if(forwardID.getText().contains(fId))
                {
                    forwardID.click();
                }
            }
        }catch(Exception e)
        {
            log.error("Unable to select value from dropdown");
        }

    }
    public void enterForwardIdValue(String fId)
    {
        if (fId != null) {
            actions.setText(forwardOrderIdInput, fId);
        } else {
            log.info("Keys to send is null. Handle accordingly.");
        }

    }

    public void openSearchedForwardId()
    {
        try {
            WebElement forwardId = driver.findElement(searchedForwardOrderID);
            actions.waitForElementToBeDisplayed(searchedForwardOrderID, Duration.ofSeconds(20));
            actions.doubleClickByActionClass(forwardId);
        }catch(Exception e){}
    }

    public void enterCutoffDatePreviousTo2Days(int day)
    {
        actions.waitForElement(cutoffDateTxt, Duration.ofSeconds(10));
        String existingDate = actions.getTextByValueAttribute(cutoffDateTxt);
        log.info("Existing cutoff date is: "+existingDate);
         String previousDate = actions.getNextDaysDate(day);
            log.info("Cutoff date set to: "+previousDate);
         actions.click(cutoffDateTxt);
         String[] days = previousDate.split("/");
         String desiredDayFromCalender = desiredDay.replace("day",days[0]);
         actions.waitForElement(By.xpath(desiredDayFromCalender), Duration.ofSeconds(20));
         actions.click(By.xpath(desiredDayFromCalender));
    }

    public void ValidateMetNotMetStatus(String dealerCode, String gname, String status)
    {
        String mstatus = metNotMetStatus
                .replace("dealerName", dealerCode)
                .replace("groupName", gname);
        String statusName = actions.getText(By.xpath(mstatus));
        Assert.assertEquals(statusName,status);
    }

    public void clickFinalizeBtn()
    {
        try {
            actions.waitForElement(finalizeBtn,Duration.ofSeconds(15));
            actions.click(finalizeBtn);
        }catch(Exception e){}

    }

    }
