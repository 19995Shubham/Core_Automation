package common.core.pom.locator;

import org.openqa.selenium.By;

public class forwardOrderEntryLocator
{
    protected By forwardOrderEntryTitle;

    protected By componentName;

    protected By productionForwardId;

    protected By groupNamewithQuantity;

    protected String plusIconGrpName;

    protected By allGroupModels;

    protected By popupTitle;

    protected By minOrderQnty;

    protected By totalOrderQnty;

    protected By allOrderQntyCol;

    protected By allColourDesc;

    protected By groupDetails;

    protected By colorDetails;

    protected String groupDetailsXpath;

    protected By quantityDetails;

    protected String minusIconByGroupName;

    protected String forwardOrderIdName;

    protected String forwardOrderIdName2;

    protected By SOPSummary;

    protected String totalGroupsInSopSummary;

    protected By sopSummaryPopupTitle;

    protected By forwardOrderIdDD;

    protected By forwardOrderIdDDValues;

    protected String placeOrderRows;

    protected By filledBackOrder;

    protected By confirmDialouge;

    protected String yesNoBtn;

    protected By logoutBtn;

    protected By yesBtn;


    public void forwardOrderEntryLocators()
    {
        forwardOrderEntryTitle = By.xpath("//span[text()='Vehicle Forward Orders']");
        componentName = By.cssSelector("mz-titled-hrule span");
        productionForwardId = By.xpath("//span[contains(text(),'A23-11-1 - Cutoff')]");
        groupNamewithQuantity = By.xpath("//span[@class='ag-group-contracted']" +
                "/following-sibling::span[@class='ag-group-value']");
        forwardOrderIdName = "//span[contains(text(),'forwardOrderId - Cutoff')]" +
                "/../../../following-sibling::div";
        forwardOrderIdName2 = "//span[contains(text(),'forwardOrderId - Cutoff')]" +
                "/../../../following-sibling::div//span[@class='ag-group-value']";
        plusIconGrpName = "//span[text()='groupName']" +
                "//preceding-sibling::span//span[contains(@class, 'ag-icon-tree-close')]";
        allGroupModels = By.xpath("//span[contains(@class,'ag-row-group-leaf-indent')]");
        popupTitle = By.xpath("//p-dynamicdialog//span[contains(@class, 'ui-dialog-title')]");
        minOrderQnty = By.xpath("//label[text()='Min Order Required to Meet Irrevoc. Qty']" +
                "/following-sibling::input");
        allOrderQntyCol = By.xpath("//mz-blockable-div//div[@col-id='orderedQuantity' and " +
                "contains(@class,'ag-cell-value')]");
        totalOrderQnty = By.xpath("//label[text()='Qty Ordered']" +
                "/following-sibling::input");
        allColourDesc = By.xpath("//mz-blockable-div//div[@col-id='colourDescription' and " +
                "contains(@class,'ag-cell-value')]");
        groupDetailsXpath = "//div[@class='ag-center-cols-container']" +
                "//div[contains(@class,'ag-row-position-absolute ag-row-last ag-row-focus')]";
        groupDetails = By.xpath(groupDetailsXpath);
        colorDetails =By.xpath(groupDetailsXpath + "//span[@class='ag-group-value']");
        quantityDetails = By.xpath(groupDetailsXpath +"//div[@col-id='orderedQuantity']");
//        minusIconByGroupName = "//span[contains(text(),'forwardOrderId - Cutoff')]/../../../" +
//                "following-sibling::div//span[contains(text(),'groupName')]" +
//                "//preceding-sibling::span[contains(@class,'ag-group-expanded') " +
//                "and not(contains(@class, 'ag-hidden'))]/span";
        minusIconByGroupName = "//span[text()='groupName']" +
                          "//preceding-sibling::span//span[contains(@class, 'ag-icon-tree-open')]";
        SOPSummary = By.xpath("//span[text()='SOP Summary']");
        totalGroupsInSopSummary = "//div[text()='forwardOrderId']/" +
                "following-sibling::div[@col-id='minimumOrderRequired']";
        sopSummaryPopupTitle = By.xpath("//p-dynamicdialog//span[contains(@class, 'ui-panel-title')]");
        forwardOrderIdDD = By.xpath("//div[contains(@class,'ui-dropdown-label-container')]");
        forwardOrderIdDDValues = By.xpath("//li[@role='option']");
        placeOrderRows = "//div[@col-id='modelCode' and text()='modelName']";
        filledBackOrder = By.xpath("//span[text()='Fill Selected BackOrders']");
        confirmDialouge = By.xpath("//p-confirmdialog//div[contains(@class,'ui-confirmdialog')]");
        yesNoBtn = "//span[text()='btnName']";
        logoutBtn = By.xpath("//button/span[contains(@class,'lock-break-icon')]");
        yesBtn = By.xpath("//span[text()='Yes']");

    }



}
