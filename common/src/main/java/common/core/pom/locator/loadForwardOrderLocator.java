package common.core.pom.locator;

import org.openqa.selenium.By;

public class loadForwardOrderLocator
{
    protected By loadForwardOrderTitle;

    protected By allLabels;

    protected By forwardOrderIdTxt;

    protected String buttonByName;

    protected By openDateTxt;

    protected By cutOffDateTxt;

    protected By todatBtn;

    protected By importBtn;

    protected By clickOutside;

    protected By SOPCloseBtn;

    protected By forwardOrderGroupCount;

    protected By forwardOrderGroupValue;

    protected By forwardOrderGroupQuntity;

    protected By SOPPopUp;

    protected By totalForwardOrders;


    public void loadForwardOrderLocators()
    {
        loadForwardOrderTitle = By.xpath("//span[text()='Forward Order Allocation Download']");
        allLabels = By.xpath("//div[@class='form-rows-wrapper']//label");
        forwardOrderIdTxt = By.id("forwardOrderId");
        buttonByName = "//button[@label='buttonName']";
        openDateTxt = By.xpath("//mz-date-field[@id='openDate']//input");
        cutOffDateTxt = By.xpath("//mz-date-field[@id='cutOffDate']//input");
        todatBtn = By.xpath("//span[text()='Today']");
        importBtn = By.xpath("//mz-file-import//p-fileupload");
        clickOutside = By.className("ag-header-viewport");
        SOPCloseBtn = By.xpath("//a[contains(@class,'ui-dialog-titlebar-close')]");
        forwardOrderGroupCount = By.xpath("//span[@class='ag-icon ag-icon-tree-closed']");
        forwardOrderGroupValue = By.cssSelector("span .ag-group-value");
        forwardOrderGroupQuntity = By.xpath("//div[@col-id='monthAllocationQuantity' " +
                "and contains(@class,'ag-cell-value')]");
        SOPPopUp = By.xpath("//p-dynamicdialog//div[@role='dialog']");
        totalForwardOrders = By.xpath("//label[text()='Total Forward Orders']/following-sibling::input");
    }



}
