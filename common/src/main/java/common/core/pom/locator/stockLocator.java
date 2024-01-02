package common.core.pom.locator;

import org.openqa.selenium.By;

public class stockLocator
{
    protected By stockLocatorPageTitle;

    protected String folderName;

    protected By reqNumberLabel;

    protected By requestNumbersList;

    protected String dealerStatus;

    protected By forwardOrderCount;


    public void stockLocators()
    {
        stockLocatorPageTitle = By.xpath("//span[contains(@class,'ui-panel-title')]");
        folderName = "//li[@role='treeitem']//span[contains(text(),'folderNameText')]";
        requestNumbersList = By.xpath("//div[@col-id='requestNumber' and contains(@class,'ag-cell-value')]");
        reqNumberLabel = By.xpath("//div[@ref='eBodyHorizontalScrollViewport']");
        dealerStatus = "//div[@col-id='requestNumber' and text()='reqNum']" +
                "/preceding-sibling::div[@col-id='dealerOrderStatus']";
        forwardOrderCount = By.xpath("//div[@class='ag-center-cols-container']//div[@role='row']");

    }

}
