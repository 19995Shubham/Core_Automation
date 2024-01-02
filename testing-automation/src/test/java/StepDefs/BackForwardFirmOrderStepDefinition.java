package StepDefs;

import common.core.pom.pages.*;
import common.shared.Test.BaseTest_Core;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.io.IOException;

public class BackForwardFirmOrderStepDefinition extends BaseTest_Core
{
    public static Logger log = LogManager.getLogger(BackForwardFirmOrderStepDefinition.class);
    loginPage lp = new loginPage(driver, filePathForScreenshot);
    accountPage ac = new accountPage(driver);
    vehicleOrderPage vop = new vehicleOrderPage(driver,filePathForScreenshot);
    stockLocatorPage slp = new stockLocatorPage(driver);
    loadForwardOrderPage lfp = new loadForwardOrderPage(driver);
    forwardOrderEntryPage fop = new forwardOrderEntryPage(driver);
    finalizeForwardOrderSOPPage ffop = new finalizeForwardOrderSOPPage(driver);
    String requestNum;
    String vehicleModel;
    String vehicleColor;
    String cutoffDate;
    String[] myArray;
    String forwardOrderId;

    @Given("^Set up dealer email address$")
    public void set_up_dealer_email_address() throws IOException {
        lp.setLogInCredentials(accountUser, password);
        lp.clickOnSubmenuItem("Accounts", "Trader Details");
        ac.clickAndSelectTraderDropDown(ringwoodDealerId);
        ac.updateDealerEmailAddress(dealerEmail,filePathForScreenshot);
        ac.validateToastMsg(accntToastMsgSummary, accntToastMsgDetail);
    }

    @Then("^Open MA Vehicle Url and login with (.+) dealer$")
    public void openMAVehicleUrlAndLoginWithRingwoodDealer(String dealerName) throws InterruptedException
    {
        String dealerLoginId = null;
        if(dealerName.equalsIgnoreCase("Ringwood"))
        {
            dealerLoginId = ringwoodDealerLoginId;
        } else if(dealerName.equalsIgnoreCase("Essendon"))
        {
            dealerLoginId = essendonDealerLoginId;
        }
        else if(dealerName.equalsIgnoreCase("Brighton"))
        {
            dealerLoginId = brightonDealerLoginId;
        }
        Thread.sleep(5000);
        log.info("Current Url is: "+driver.getCurrentUrl());
        if(!driver.getCurrentUrl().contains(vehicleURL)) {
            hitUrl(vehicleURL);
        }
        lp.setLogInCredentials(dealerLoginId, password);
    }

    @Then("Navigate to Vehicle Order Page")
    public void navigate_to_vehicle_order_page() throws InterruptedException {
        lp.clickOnSubmenuItem("Orders", "Vehicle Order");
        Assert.assertTrue("Dealer Order details page loaded", vop.validateDealerOrderPageLoad());
    }

    @Then("^Enter model (.+), colour (.+) and trim (.+)$")
    public void enter_model_colour_and_trim(String model,String color,String trim) throws InterruptedException {
        vehicleModel = model;
        vehicleColor = color;
        vop.selectModelValueFromDropDown(vehicleModel);
        vop.selectColourValueFromDropDown(vehicleColor);
        vop.selectTrimValueFromDropDown(trim);
        log.info("Searched Vehicle with model: "+vehicleModel+" and color: "
                +vehicleColor+" and trim: "+trim);
    }

    @Then("Click on Search")
    public void click_on_search() throws InterruptedException {
        vop.clickOnSearch();
        log.info("Clicked on Search Button after entering required details of vehicle");
    }

    @And("^Validate (.+) message$")
    public void validate_no_vehicle_info_found_message(String noVehicleToastmsg)
    {
        try
        {
            log.info("No Vehicle Toast Message should be.."+noVehicleToastMsgSummary
                    + " /n" + noVehicleToastmsg);
            vop.validateWithoutStockNumber();
        }catch(Exception e){}
    }

    @Then("^Click on Submit Back Order$")
    public void click_on_submit_back_order() throws InterruptedException
    {
        vop.clickSubmitBackOrder();
        requestNum = vop.getRequestNumber();
        log.info("Back Order Toast Message should be.."+backOrderToastMsgSummary
                        + " /n" + backOrderToastMsgDetail);
        System.out.println("Generated Request Number is: "+requestNum);
        Thread.sleep(5000);
        fop.validateConfirmDialouge("No");
    }

    @Then("^Navigate to Stock Locator Page$")
    public void navigate_to_stock_locator_page()
    {
        lp.clickOnSubmenuItem("Enquiries", "Stock Locator");
        slp.validateStockLocatorPageLoad();
        log.info("Landed on Stock Locator Page");
    }

    @And("Search for required Model")
    public void searchForRequiredModel() throws InterruptedException {
        vop.selectModelValueFromDropDown(vehicleModel);
        vop.selectColourValueFromDropDown(vehicleColor);
        vop.clickOnSearch();
    }

    @And("^Click on \"([^\"]*)\" folder$")
    public void click_on_back_order(String folderName) throws InterruptedException {
        slp.clickFolderByName(folderName.toUpperCase());
    }

    @Then("^Validate the generated request number$")
    public void validate_the_generated_request_number()
    {
        slp.validateRequestNumber(requestNum);
        log.info("Validated Requested number is:"+requestNum);
    }

    @Then("^Validate dealer status as \"([^\"]*)\"$")
    public void validate_dealer_status_as(String dealerStatus)
    {
        slp.validateDealerStatus(requestNum, dealerStatus);
        log.info("Validated Dealer status as:"+dealerStatus);
    }

    @Given("^Open MA MCOrder Url and login with user SBZHOU$")
    public void openMAMCOrderUrlAndLoginWithUserSBZHOU() throws InterruptedException {
        hitUrl(mcOrderURL);
        lp.setLogInCredentials(mcOrderUserLoginId, password);
    }

    @Then("Navigate to Load Forward Orders page")
    public void navigateToLoadForwardOrdersPage() throws IOException, InterruptedException {
        lp.clickOnSubmenuItem("Forward Orders", "Load Forward Orders");
        lfp.validateLoadForwardOrderPageLoad();
        lfp.validateLabelName(actualLabelName);
    }

    @And("^Enter Forward id as \"([^\"]*)\" and click on \"([^\"]*)\"$")
    public void enterForwardIdAsAndClickOn(String forwardId, String btnName)
    {
        forwardOrderId = forwardId;
        log.info("Entered forward order Id: "+forwardOrderId);
        lfp.validateButtonIsDisabled(btnName);
        lfp.validateButtonIsDisabled("Check Forward Order");
        lfp.enterForwardOrderId(forwardOrderId);
        lfp.validateButtonIsEnabled(btnName);
        lfp.validateButtonIsEnabled("Check Forward Order");
        lfp.clickButtonByName(btnName);
        log.info("Clicked on Button: "+btnName);
    }

    @And("^Click on \"([^\"]*)\" button$")
    public void clickOnCheckForwardOrder(String btnName)
    {
        try {
            lfp.clickButtonByName(btnName);
            log.info("Clicked on Button: " + btnName);
        }catch(Exception e)
        {
            log.error("Unable to click button: "+e.getMessage());
        }
    }

    @And("^Enter Open Date and Cut off date of (\\d+) day$")
    public void enterOpenDateAndCutOffDateOfDay(int days)
    {
        lfp.enterOpenDate();
        cutoffDate = lfp.enterCutOffDate(days);
        System.out.println("Entered cut off date: "+cutoffDate);
    }

    @Then("^Import the Text file$")
    public void importTheTextFile()
    {
        lfp.importForwardOrder(filePath);
        log.info("Text file imported successfully and File path is: "+filePath);
    }

    @And("^Validate \"([^\"]*)\" message if any$")
    public void validateMessageIfAny(String titleName)
    {
        lfp.validateSOPPopUp();
        log.info("Forward Order Share of Productions not configured pop-up Displayed");
    }

    @And("^Validate vehicle count for each group$")
    public void validateVehicleCountForEachGroup()
    {
        lfp.ValidateForwardOrders(filePath);
    }

    @And("Expand First Group")
    public void expandFirstGroup()
    {
        lfp.clickFirstGroup();
        log.info("Click on first forward group");
    }

    @And("Validate vehicle count for each dealer")
    public void validateVehicleCountForEachDealer()
    {
        lfp.validateTotalForwardOrders();
        log.info("Validated total forward group");
    }

    @Then("click on Save")
    public void clickOnSave()
    {
        ac.clickSaveBtn();
        log.info("Forward order saved successfully");
    }

    @Then("Navigate to Forward Orders Entry page")
    public void navigate_to_forward_orders_entry_page()
    {
        lp.clickOnSubmenuItem("Orders", "Forward Order Entry");
        Assert.assertTrue("Dealer Order details page loaded",
                fop.validateLoadForwardOrderPageLoad(filePathForScreenshot));
        fop.resetPageSize();
    }
    @Then("Validate Order and Back Order components")
    public void validate_order_and_back_order_components()
    {
        fop.validateComponetName(forwardEntryComponentName,filePathForScreenshot);
        log.info("Forward Entry page components: "+forwardEntryComponentName);
    }
    @Then("Validate all groups with count displayed for that dealer")
    public void validate_all_groups_with_count_displayed_for_that_dealer()
    {
        fop.validateGroupNameWithQuantity(filePath, ringwoodDealerId, forwardOrderId);
        validateFullyMetOrders();
        log.info("Vehicle order validated in dealer: "+ringwoodDealerId);
    }
    @Then("Validate Forward order Id with Cut Off Date")
    public void validate_forward_order_id_with_cut_off_date()
    {
        log.info("Expected cut off date is: "+cutoffDate);
        fop.validateProductionCutOffDate(cutoffDate);
    }
    @Then("Expand one group")
    public void expand_one_group()
    {
        log.info("Expand second group.Group name is: "+myArray[0]);
        fop.ExpandGroupPlusIcon(filePathForScreenshot, myArray[0],forwardOrderId);
    }
    @Then("Select first vehicle")
    public void select_first_vehicle()
    {
        log.info("Click first model from list of model");
        fop.clickModelFromGroup(0);
    }
    @Then("^Validate \"([^\"]*)\" pop-up$")
    public void validate_vehicle_forward_orders_colour_details_pop_up(String title)
    {
        log.info("Title of the pop-up: "+title);
        fop.validateSOPSummaryTitle(title);
    }
    @Then("Enter the quantity of vehicle of different colour")
    public void enter_the_quantity_of_vehicle_of_different_colour() throws InterruptedException {
        fop.enterOrderQuantity(filePathForScreenshot,1);
        log.info("Entered orderQuanity with different colour");
    }
    @Then("Validate SOP portion")
    public void validate_sop_portion()
    {
        log.info("Total Ordered Quantity is: "+fop.totalOrderQuantity());
        log.info("Min Order Required to Meet Irrevoc. Qty is: "+fop.getMinOrderQuantity());
    }
    @Then("Click on Save")
    public void click_on_save()
    {
        ac.clickSaveBtn();
    }
    @Then("Validate selected colour for specific model displaying in parent screen")
    public void validate_selected_colour_for_specific_model_displaying_in_parent_screen()
    {
        fop.validateEnterOrderQuantityWithColorForFirstModel();
    }
    @Then("Select Second Vehicle")
    public void select_second_vehicle() throws InterruptedException {
        log.info("Click Second model from list of model");
        fop.clickModelFromGroup(1);
        fop.enterOrderQuantity(filePathForScreenshot,2);
        ac.clickSaveBtn();
    }

    @Then("Compress opened group")
    public void compressFirstGroup()
    {
        fop.compressFirstGroup(forwardOrderId,myArray[0]);
        log.info("Number of order entered for first group is: "+myArray[0]);
    }

    @And("Expand Second Group")
    public void expandSecondGroup()
    {
        fop.ExpandGroupPlusIcon(filePathForScreenshot,myArray[1],forwardOrderId);
        log.info("Expand second group.Group name is: "+myArray[1]);
    }

    @And("Click on SOP Summary")
    public void clickOnSOPSummary()
    {
        fop.clickSopSummary();
    }

    @And("Validate minimum order quantity in SOP summary pop-up")
    public void validateMinimumOrderQuantityInSOPSummaryPopUp()
    {
        fop.getTotalGroupsInSOPSummary(forwardOrderId);
    }

    @And("Close SOP Summary Pop-up")
    public void closeSOPSummaryPopUp()
    {
        lfp.closePopup();
    }

    @And("Validate total forward count")
    public void validateTotalForwardCount()
    {
        int forwardCount = slp.validateForwardOrderCount();
        log.info("Total forward count is: "+forwardCount);
    }

    @And("Search For same Model in Place Order")
    public void searchForSameModelInPlaceOrder() throws InterruptedException {
        vop.selectModelValueFromDropDown(vehicleModel);
        vop.clickOnSearch();
    }

    @Then("Select forward Id from Dropdown")
    public void selectForwardIdFromDropdown()
    {
        fop.selectForwardIdFromDD(forwardOrderId);
        log.info("Forward Id selected from dropdown is: "+forwardOrderId);
    }

    @Then("Select multi color by using Keyboard")
    public void selectMultiColorByUsingKeyboard()
    {
        fop.multiSelectRows(vehicleModel);
        log.info("Select multiple rows from back order for model: "+vehicleModel);
    }

    @And("Click on Fill Selected BackOrders")
    public void clickOnFillSelectedBackOrders()
    {
        try {
            fop.clickFillSelectedBackOrders();
            log.info("Clicked on Fill selected Back orders button");
        }catch(Exception e)
        {
            log.error("Unable to click Fill Selected BackOrders: "+e.getMessage());
        }
    }

    public void validateFullyMetOrders()
    {
        try {
            int expectedGroupNameWithQuantity = fop.getGroupNameWithQuantity(forwardOrderId).size();
            for (int i=0;i<expectedGroupNameWithQuantity;i++) {
                String groupName = fop.getGroupNameWithQuantity(forwardOrderId).get(i);
                fop.ExpandGroupPlusIcon(filePathForScreenshot,groupName, forwardOrderId);
                for (int j = 0; j < 2; j++) {
                    fop.clickModelFromGroup(j);
                    fop.validatePopUpTitle("Vehicle Forward Orders - Colour Details");
                    fop.enterOrderQuantity(filePathForScreenshot, j);
                    ac.clickSaveBtn();
                }
                fop.compressFirstGroup(forwardOrderId, groupName);
            }
        }catch(Exception e)
        {
            log.error("Unable to place forward order for group: "+e.getMessage());
        }
    }

    @Then("Navigate to Stock Locator Page from Forward Order Entry Page")
    public void navigateToStockLocatorPageFromForwardOrderEntryPage()
    {
        lp.clickOnSubmenuItem("Enquiries", "Stock Locator");
        fop.validateConfirmDialouge("Yes");
    }

    @And("Logout from the application")
    public void logoutFromTheApplication()
    {
        fop.logout();
        log.info("Log out from the vehicle url to login with another dealer");
    }

    @Then("Navigate to Finalize forward order SOP%")
    public void navigateToFinalizeForwardOrderSOP() throws InterruptedException {
        lp.clickOnSubmenuItem("Forward Orders", "Finalise Fwd Order SOP %");
        Assert.assertTrue("Finalise Forward Order SOP %",
                ffop.validateFinalizeForwardOrderSOPPageLoad(filePathForScreenshot));
    }

    @And("Enter forward order ID and click on Search")
    public void enterForwardOrderID()
    {
        ffop.selectForwardIdValueFromDropDown(forwardOrderId);
        vop.clickOnSearch();
    }

    @And("Validate all the placed order with status as MET & Not MET")
    public void validateAllThePlacedOrderWithStatusAsMETNotMET() {
        ffop.ValidateMetNotMetStatus("R390705","C30","MET");
        ffop.ValidateMetNotMetStatus("B390254","C60","NOT_MET");
    }

    @Then("click on Finalize")
    public void clickOnFinalize() {
        ffop.clickFinalizeBtn();
    }

    @And("Validate Only MET forward orders still be there")
    public void validateOnlyMETForwardOrdersStillBeThere()
    {
        ffop.ValidateMetNotMetStatus("R390705","C60","MET");
    }

    @Then("Navigate to Forward Order Dates")
    public void navigateToForwardOrderDates()
    {
        lp.clickOnSubmenuItem("Maintenance", "Forward Order Dates");
    }

    @And("Double Click on retrieved forwardId")
    public void doubleClickOnRetrievedForwardId()
    {
        ffop.openSearchedForwardId();
    }

    @Then("^Update cutoff date to (.+) days back date$")
    public void updateCutoffDateToDaysBackDate(int days) throws InterruptedException {
        ffop.enterCutoffDatePreviousTo2Days(days);
        ac.clickSaveBtn();
    }

    @And("Enter forward order ID and click on Search on forward order dates Page")
    public void enterForwardOrderIDAndClickOnSearchOnForwardOrderDatesPage() throws InterruptedException {
        System.out.println("Entered forward Id: "+forwardOrderId);
        ffop.enterForwardIdValue(forwardOrderId);
        vop.clickOnSearch();
    }

    @And("Validate changes in Dealer Login")
    public void validateChangesInDealerLogin()
    {
        fop.resetPageSize();
        fop.getGroupNameWithQuantity(forwardOrderId);
    }
}
