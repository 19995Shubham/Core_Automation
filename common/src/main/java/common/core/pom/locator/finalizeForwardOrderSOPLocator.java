package common.core.pom.locator;

import org.openqa.selenium.By;

public class finalizeForwardOrderSOPLocator
{
    protected By finalizeForwardOrderSOPTitle;

    protected By forwardOrderIdTxt;

    protected By forwardOrderIdDDValues;

    protected By searchedForwardOrderID;

    protected By cutoffDateTxt;

    protected String desiredDay;

    protected By clickOutside;

    protected String metNotMetStatus;

    protected By finalizeBtn;

    protected By forwardOrderIdInput;


    public void finalizeForwardOrderSOPLocators()
    {
        finalizeForwardOrderSOPTitle = By.xpath("//span[text()='Finalise Forward Order SOP %']");
        forwardOrderIdTxt = By.xpath("//p-autocomplete[@id='forwardOrder']//input");
        forwardOrderIdDDValues = By.xpath("//ul[@role='listbox']//li");
        searchedForwardOrderID = By.xpath("//div[@col-id='forwardOrderId' and @role='gridcell']");
        cutoffDateTxt = By.xpath("//mz-date-field[@formcontrolname='cutOffDate']//input");
        clickOutside = By.xpath("//span[text()='Modify Forward Order Dates']");
        metNotMetStatus = "//div[@col-id='dealerCode' and text() ='dealerName']" +
                "/following-sibling::div[text()='groupName']/following-sibling::div[@col-id='sopStatus']";
        finalizeBtn = By.xpath("//span[text()='Finalise']");
        forwardOrderIdInput = By.xpath("//input[@id='forwardOrderId']");
        desiredDay = "//td/a[text()='day']";

    }



}
