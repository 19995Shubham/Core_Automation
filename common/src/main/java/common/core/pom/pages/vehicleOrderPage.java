package common.core.pom.pages;

import common.core.pom.locator.vehicleOrderLocator;
import common.shared.Utility.UtlActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;


public class vehicleOrderPage extends vehicleOrderLocator {
    private WebDriver driver;
    UtlActions actions;
    accountPage apage;
    String filepath;
    public static Logger log = LogManager.getLogger(vehicleOrderPage.class);

    public vehicleOrderPage(WebDriver driver,String filepath) {
        this.driver = driver;
        this.filepath = filepath;
        actions = new UtlActions(driver);
        apage = new accountPage(driver);
        vehicleOrderLocators();
    }

    // Validate page is loaded or not
    public boolean validateDealerOrderPageLoad() {
        System.out.println("Page Title.." + actions.getText(vehicleOrderTitle));
        boolean element =  actions.waitForElementToBeDisplayed(vehicleOrderTitle, Duration.ofSeconds(5));
        return element;
    }

    // Click Model drop down and select required model
    public void selectModelValueFromDropDown(String model)
    {
        actions.waitForElementToBeDisplayed(modelTxt, Duration.ofSeconds(15));
        actions.setText(modelTxt, model);
        System.out.println("Required vehicle model is.." + model);
        for(WebElement modelID:getModelList())
        {
            System.out.println(modelID.getText());
            if(modelID.getText().equalsIgnoreCase(model))
            {
                modelID.click();
            }
        }
    }

    public List<WebElement> getModelList() {
        return driver.findElements(modelDDValues);
    }

    // Click Colour drop down and select required colour
    public void selectColourValueFromDropDown(String colour)
    {
        actions.waitForElementToBeDisplayed(colourTxt, Duration.ofSeconds(15));
        actions.setText(colourTxt, colour);
        System.out.println("Required vehicle colour is.." + colour);
        for(WebElement colourCode:getColourList())
        {
            if(colourCode.getText().contains(colour))
            {
                colourCode.click();
            }
        }
    }

    public List<WebElement> getColourList() {
        return driver.findElements(colouDDValues);
    }

    // Click Trim drop down and select required trim
    public void selectTrimValueFromDropDown(String trim)
    {
        actions.waitForElementToBeDisplayed(trimTxt, Duration.ofSeconds(15));
        actions.setText(trimTxt, trim);
        System.out.println("Required vehicle colour is.." + trim);
        for(WebElement trimCode:getTrimList())
        {
            if(trimCode.getText().contains(trim))
            {
                trimCode.click();
            }
        }
    }

    public List<WebElement> getTrimList() {
        return driver.findElements(trimDDValues);
    }

    public void clickOnSearch()
    {
        actions.waitForElementToBeDisplayed(searchBtn, Duration.ofSeconds(10));
        actions.click(searchBtn);
    }

    public void validateToastMsg(String expectedVehicleTstSummary, String expectedVehicletstDetails)
    {
        String actualTstSummary = actions.getText(toastSummary) ;
        System.out.println("Toast Summary Message.."+actualTstSummary);
        String actualTstDetail = actions.getText(toastDetails) ;
        System.out.println("Toast Details Message.."+actualTstDetail);
        Assert.assertEquals(actualTstSummary,expectedVehicleTstSummary);
        Assert.assertEquals(actualTstDetail,expectedVehicletstDetails);
        actions.click(toastMsgCloseBtn);
    }

    public void clickSubmitBackOrder()
    {
        actions.screenshot(filepath,"OrderQuantityPopup");
        actions.waitForElementToBeDisplayed(submitBackOrderBtn, Duration.ofSeconds(5));
        actions.click(submitBackOrderBtn);
    }
    public String getRequestNumber()
    {
        String toastMsg = actions.getText(toastDetails);
        System.out.println("Back Order toast msg.."+toastMsg);
        String[] reqNum = toastMsg.split(" ");
        System.out.println("request number in toast msg.."+reqNum[reqNum.length-1]);
        return reqNum[reqNum.length-1];
    }

    public void validateWithoutStockNumber()
    {
        String stockNum = actions.getText(stockNumberTxt);
        System.out.println("Stock Number:"+stockNum);
        Assert.assertEquals(stockNum, "0");
    }

    public void TestBrokenLink() {

        String url = null;
        try {
            List<WebElement> anchorTagsList = driver.findElements
                    (By.tagName("a"));
            for (WebElement link : anchorTagsList) {
                 url = link.getAttribute("href");

                URL links = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) links.openConnection();
                httpURLConnection.setConnectTimeout(3000); // Set connection timeout to 3 seconds
                httpURLConnection.connect();

                if (httpURLConnection.getResponseCode() == 400 || httpURLConnection.getResponseCode() == 404) {
                    System.out.println(url + " - " + httpURLConnection.getResponseMessage()+ " - " + "is a broken link");
                } else {
                    System.out.println(url + " - " + httpURLConnection.getResponseMessage());
                }
            }
        }catch(Exception e){
                System.out.println(url + " - " + "is a broken link");
            }
        }


}
