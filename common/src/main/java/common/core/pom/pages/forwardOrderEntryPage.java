package common.core.pom.pages;

import common.core.Utility.TextFileReadWrite;
import common.core.pom.locator.forwardOrderEntryLocator;
import common.shared.Utility.UtlActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


public class forwardOrderEntryPage extends forwardOrderEntryLocator {
    private WebDriver driver;
    public static Logger log = LogManager.getLogger(forwardOrderEntryPage.class);
    UtlActions actions;
    TextFileReadWrite txt = new TextFileReadWrite();
    List<String> expectedGroupNameWithQuantity;
    Map<String,String> colorWithQuantity;
    List<String> listOfColor = new ArrayList<>();
    List<String> quantityList = new ArrayList<>();

    public forwardOrderEntryPage(WebDriver driver) {
        this.driver = driver;
        actions = new UtlActions(driver);
        forwardOrderEntryLocators();
    }

    // Validate page is loaded or not
    public boolean validateLoadForwardOrderPageLoad(String filepath) {
        log.info("Page Title.." + actions.getText(forwardOrderEntryTitle));
        boolean element =  actions.waitForElementToBeDisplayed(forwardOrderEntryTitle, Duration.ofSeconds(5));
        actions.screenshot(filepath,"LoadForwardOrder");
        return element;
    }

    public void validateComponetName(String[] actualCompName, String filePath)
    {
        List<String> expectedComponentName = new ArrayList<String>();
        for(WebElement compName:getComponentNames())
        {
            expectedComponentName.add(compName.getText());
        }
        actions.screenshot(filePath,"ForwardOrderEntry");
        Assert.assertEquals(expectedComponentName, Arrays.asList(actualCompName));
    }

    public List<WebElement> getComponentNames() {
        return driver.findElements(componentName);
    }

    public void validateProductionCutOffDate(String expectedCutDate)
    {
        try
        {
            List<WebElement> cutOffDates = driver.findElements(productionForwardId);
            String actualCutDate = cutOffDates.get(0).getText();
            String[] cutoffDate = actualCutDate.split(" ");
            log.info("Original cutoff date: "+expectedCutDate);
            String expectedCutoffDate = actions
                    .changeMonthDateFormatFromNumberToWord(expectedCutDate);
            log.info("cutoff date after formatting: "+expectedCutDate);
            Assert.assertEquals(cutoffDate[cutoffDate.length-1], expectedCutoffDate);
        }catch(Exception e){}
    }

    public void resetPageSize()
    {
        try {
            //            actions.resizePageContent(driver,0.1);
            actions.performZoomAction();
        }catch(Exception e){}
    }

    public List<String> getGroupNameWithQuantity(String forwardId)
    {
        try {
            String forwardid = forwardOrderIdName2.replace("forwardOrderId", forwardId);
            System.out.println("Forward Id:"+forwardid);
            List<WebElement> getGroupNames = driver.findElements(By.xpath(forwardid));
            expectedGroupNameWithQuantity = new ArrayList<>();
            for (WebElement groupName : getGroupNames)
            {
                System.out.println("element:"+groupName);
                System.out.println("element Text:"+groupName.getText());
                expectedGroupNameWithQuantity.add(groupName.getText());
            }
        }catch(Exception e){}
        return expectedGroupNameWithQuantity;
    }
    public void validateGroupNameWithQuantity(String filePath, String DealerId, String forwardId)
    {
        Map<String, Integer> aggregatedData =
                txt.getGroupNameWithQuantityForDealer(filePath,DealerId);
        List<String> groupName = new ArrayList<String>();
        List<Integer> groupCunt = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : aggregatedData.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
            if ("C30".equals(entry.getKey())) {
                groupName.add("CX-30");
            } else if ("C60".equals(entry.getKey())) {
                groupName.add("CX-60");
            } else {
                groupName.add(entry.getKey());
            }
            groupCunt.add(entry.getValue());
        }
        List<String> grpNameWithQnty = getGroupNameWithQuantity(forwardId);
        List<String> groupName1 = new ArrayList<String>();
        List<Integer> groupCunt1 = new ArrayList<>();
        for(String value : grpNameWithQnty)
        {
            String[] grpName = value.split(" - ");
            groupName1.add(grpName[0]);
            String grpCnt = grpName[1].replace("(", "").replace(")", "").trim();
            groupCunt1.add(Integer.parseInt(grpCnt));
        }

        Assert.assertEquals(groupName.stream().sorted().collect(Collectors.toList()),
                groupName1.stream().sorted().collect(Collectors.toList()));
        Assert.assertEquals(groupCunt.stream().sorted().collect(Collectors.toList()),
                groupCunt1.stream().sorted().collect(Collectors.toList()));

    }

        public void ExpandGroupPlusIcon(String filepath,String groupName,String forwardOrderId)
        {
            String firstGroupPlusIncon = plusIconGrpName
                    .replace("groupName",groupName);
//            String groupNamePlusIcon = forwardOrderIdName
//                    .replace("forwardOrderId",forwardOrderId)
//                    + firstGroupPlusIncon;
            actions.screenshot(filepath,"ForwardGroup");
            System.out.println("Group plus icon xpath:"+firstGroupPlusIncon);
            List<WebElement> plusIcon = driver.findElements(By.xpath(firstGroupPlusIncon));
            if (!plusIcon.isEmpty()) {
                WebElement firstPlusIcon = plusIcon.get(0);
                firstPlusIcon.click();
            } else {
                System.out.println("The list of plus icons is empty.");
            }
        }

        public void clickModelFromGroup(int index)
        {
            List<WebElement> grpModel = driver.findElements(allGroupModels);
            System.out.println("Total model group: "+grpModel.size());
            System.out.println("Index value is: "+index);
            if (!grpModel.isEmpty()) {
                System.out.println("model group name: "+grpModel.get(index).getText());
                actions.doubleClickByActionClass(grpModel.get(index));
            } else {
                System.out.println("The list of plus icons is empty.");
            }
        }

        public void validatePopUpTitle(String expectedTitle)
        {
            try {
                String actualTitle = actions.getText(popupTitle);
                Assert.assertEquals(actualTitle,expectedTitle);
            }catch(Exception e){}

        }

        public Integer getMinOrderQuantity()
        {
            String minOrder = actions.getTextByValueAttribute(minOrderQnty);
            int order = Integer.parseInt(minOrder);
            return order;
        }

        public void enterOrderQuantity(String filepath,int index) throws InterruptedException {
            colorWithQuantity = new HashMap<>();
            actions.screenshot(filepath,"OrderQuantityPopup");
            List<WebElement> orderQntyCol = driver.findElements(allOrderQntyCol);
            List<WebElement> colourDesc = driver.findElements(allColourDesc);
            String firstColor = colourDesc.get(0).getText();
            int col1Value = 0;

            if(index == 0)
            {
                col1Value = (int) (getMinOrderQuantity() * 0.6)/2;
            }
            if(index == 1)
            {
                    col1Value = getMinOrderQuantity();
            }
            System.out.println("First Value to enter: "+col1Value);

            // Convert the result to a string for setText
            actions.sendKeysByActionClass(driver,orderQntyCol.get(0), String.valueOf(col1Value));
            log.info("Entered number of Order: "+col1Value
                    +" for colour: "+firstColor);
            colorWithQuantity.put(firstColor,String.valueOf(col1Value));
        }

        public String totalOrderQuantity()
        {
            String totalOrdered = actions.getTextByValueAttribute(totalOrderQnty);
            return totalOrdered;
        }

        public void validateEnterOrderQuantityWithColorForFirstModel()
        {
            List<WebElement> color = driver.findElements(colorDetails);
            List<WebElement> quantity = driver.findElements(quantityDetails);
            for(WebElement c:color)
            {
                listOfColor.add(c.getText());
            }
            for(WebElement q:quantity)
            {
                quantityList.add(q.getText());
            }
            Map<String, String> combinedMap = actions.combineListsToMap(listOfColor, quantityList);
            Assert.assertEquals(colorWithQuantity,combinedMap);

        }
        public void compressFirstGroup(String forwardId, String groupName)
        {
            String firstGroupMinusIncon = minusIconByGroupName
                    .replace("forwardOrderId",forwardId)
                    .replace("groupName",groupName);
            driver.findElements(By.xpath(firstGroupMinusIncon)).get(0).click();
//            actions.click(By.xpath(firstGroupMinusIncon));
        }

        public void validateSOPSummaryTitle(String expectedTitle)
        {
            try {
                    String actualTitle = actions.getText(sopSummaryPopupTitle);
                    Assert.assertEquals(actualTitle,expectedTitle);
            }catch(Exception e){}
        }

        public void clickSopSummary()
        {
            actions.click(SOPSummary);
            log.info("Clicked on SOP Summary");
        }

        public void getTotalGroupsInSOPSummary(String forwardOrderId)
        {
            String forwardOrderIdName = totalGroupsInSopSummary
                    .replace("forwardOrderId",forwardOrderId);
            List<WebElement> totalGroupCountInSOPSummary = driver
                    .findElements(By.xpath(forwardOrderIdName));
            for(WebElement element:totalGroupCountInSOPSummary)
            {
                System.out.println(element.getText());
                Assert.assertEquals(element.getText(),String.valueOf(0));
            }
        }

        public void validateConfirmDialouge(String buttonname)
        {
            String buttonName = yesNoBtn.replace("btnName",buttonname);
            if(actions.isDisplayed(confirmDialouge, 10))
                driver.findElement(By.xpath(buttonName)).click();
        }

        public void selectForwardIdFromDD(String forwardId)
        {
            try {
                actions.click(forwardOrderIdDD);
                List<WebElement> fwValues = driver.findElements(forwardOrderIdDDValues);
                for(WebElement idName:fwValues)
                {
                    if(idName.getText().equalsIgnoreCase(forwardId))
                    {
                        idName.click();
                    }
                }
            }catch(Exception e){}
        }

        public void multiSelectRows(String modelName)
        {
            Actions actions = new Actions(driver);
          String rowsXpath = placeOrderRows.replace("modelName",modelName);
          List<WebElement> rows = driver.findElements(By.xpath(rowsXpath));
          for(WebElement rowLine:rows)
          {
              actions.keyDown(Keys.CONTROL)
                      .click(rowLine)
                      .keyUp(Keys.CONTROL)
                      .perform();
          }
        }

        public void clickFillSelectedBackOrders()
        {
            actions.click(filledBackOrder);
        }

        public void logout()
        {
            try {
                actions.waitForElement(logoutBtn, Duration.ofSeconds(15));
                actions.click(logoutBtn);
                actions.waitForElement(yesBtn, Duration.ofSeconds(15));
                actions.click(yesBtn);
            }catch(Exception e){}

        }


    }
