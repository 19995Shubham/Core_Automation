package common.core.pom.locator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class vehicleOrderLocator
{
    protected By vehicleOrderTitle;

    protected By modelTxt;

    protected By modelDDValues;

    protected By colourTxt;

    protected By colouDDValues;

    protected By trimTxt;

    protected By trimDDValues;

    protected By searchBtn;

    protected By toastSummary;

    protected By toastDetails;

    protected By toastMsgCloseBtn;

    protected  By submitBackOrderBtn;

    protected By stockNumberTxt;




    public void vehicleOrderLocators()
    {
        vehicleOrderTitle = By.xpath("//span[text()='Dealer Orders']");
        modelTxt = By.xpath("//mz-vehicle-model-combo-box//input");
        modelDDValues = By.xpath("//ul//li[@role='option']");
        colourTxt = By.xpath("//mz-vehicle-colour-combo-box//input");
        colouDDValues = By.xpath("//ul//li//div//span[@class='colourCode']");
        trimTxt = By.xpath("//mz-vehicle-trim-combo-box//input");
        trimDDValues = By.xpath("//ul//li//div[@class='trim-combobox-li-code']");
        searchBtn = By.xpath("//button/span[text()='Search']");
        toastSummary = By.xpath("//div[contains(@class,'ui-toast-summary')]");
        toastDetails = By.xpath("//div[contains(@class,'ui-toast-detail')]");
        toastMsgCloseBtn = By.xpath("//div[contains(@class,'ui-toast-message-content')]/a");
        submitBackOrderBtn = By.xpath("//button/span[text()='Submit Back Order']");
        stockNumberTxt = By.xpath("//inpu[@formcontrolname='stockNumber']");
    }



}
