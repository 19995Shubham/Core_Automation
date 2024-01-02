package common.core.pom.locator;

import org.openqa.selenium.By;

public class accountLocator
{
    protected  By traderMaintenancePageTitle;

    protected By dealerCodeDD;

    protected By dealerCodeDDTxt;

    protected By dealCodeDDSearchedTxt;

    protected By searchBtn;

    protected By vehicleTab;

    protected By editBtn;

    protected By toastSummary;

    public static By toastDetails;

    protected By toastMsgCloseBtn;

    protected By receiverEmailTxt;

    protected  By saveBtn;

    public void accountLocators()
    {
        traderMaintenancePageTitle = By.xpath("//span[text()='Trader Maintenance']");
        dealerCodeDD = By.xpath("//p-dropdown[@formcontrolname='dealerCode']/div[contains(@class,'ui-dropdown')]");
        dealerCodeDDTxt = By.xpath("//input[contains(@class,'ui-dropdown-filter')]");
        dealCodeDDSearchedTxt = By.xpath("//div[contains(@class,'dealer-combobox-li-code')]");
        searchBtn = By.xpath("//button/span[text()='Search']");
        vehicleTab = By.xpath("//span[text()='Vehicles']");
        editBtn = By.xpath("//accounts-trader-vehicles-tab-panel//button/span[text()='Edit']");
        toastSummary = By.xpath("//div[contains(@class,'ui-toast-summary')]");
        toastDetails = By.xpath("//div[contains(@class,'ui-toast-detail')]");
        toastMsgCloseBtn = By.xpath("//div[contains(@class,'ui-toast-message-content')]/a");
        receiverEmailTxt = By.xpath("//input[@formcontrolname='receiverEmail']");
        saveBtn = By.xpath("//button/span[text()='Save']");
    }



}
