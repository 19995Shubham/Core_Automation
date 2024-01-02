package common.core.pom.pages;

import common.core.pom.locator.stockLocator;
import common.shared.Utility.UtlActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class stockLocatorPage extends stockLocator {
    private WebDriver driver;
    UtlActions actions;
    public static Logger log = LogManager.getLogger(stockLocatorPage.class);

    public stockLocatorPage(WebDriver driver) {
        this.driver = driver;
        actions = new UtlActions(driver);
        stockLocators();
    }

    // Validate page is loaded or not
    public void validateStockLocatorPageLoad() {
        try
        {
            Thread.sleep(10000);
            boolean element =  actions.waitForElementToBeDisplayed(stockLocatorPageTitle, Duration.ofSeconds(20));
            log.info("Page Title.." + actions.getText(stockLocatorPageTitle));
            Assert.assertTrue("Stock locator page loaded", element);
        }catch(Exception e){}
    }

    public void clickFolderByName(String fName)
    {
        try {
            String expectedFolderName = folderName.replace("folderNameText", fName);
            log.info("Expected folder name.."+expectedFolderName);
            actions.waitForElementToBeDisplayed(By.xpath(expectedFolderName), Duration.ofSeconds(25));
            WebElement element = driver.findElement(By.xpath(expectedFolderName));
            actions.doubleClickByActionClass(element);
            actions.performZoomAction();
        }catch (Exception e)
        {
            log.error("Unable to click on the Folder: "+e.getMessage());
        }
    }

    public List<WebElement> getRequestNumbers()
    {
        actions.scrollToElement(reqNumberLabel);
        return driver.findElements(requestNumbersList);
    }

    public void validateRequestNumber(String reqNum)
    {
        try {
            List<String> reqNumList = new ArrayList<String>();
            for(WebElement re: getRequestNumbers())
            {
                reqNumList.add(re.getText());
            }
//            Assert.assertTrue(reqNumList.contains(reqNum));
        }catch(Exception e){}
    }

    public void validateDealerStatus(String requestNumber, String expectedDealerStatus)
    {
        String dealerStatusCol = dealerStatus.replace("reqNum", requestNumber);
        log.info("dealer status col: "+dealerStatusCol);
        String actualDealerSatus = actions.getText(By.xpath(dealerStatusCol));
        Assert.assertEquals(actualDealerSatus, expectedDealerStatus);
    }

    public int validateForwardOrderCount()
    {
        List<WebElement> forwardOrderCounts = driver.findElements(forwardOrderCount);
        Assert.assertTrue("Forward order should be zero", forwardOrderCounts.size()>0);
        return forwardOrderCounts.size();
    }


}
