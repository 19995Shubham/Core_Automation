package common.core.pom.pages;

import common.core.Utility.TextFileReadWrite;
import common.core.pom.locator.loadForwardOrderLocator;
import common.shared.Utility.UtlActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class loadForwardOrderPage extends loadForwardOrderLocator {
    private WebDriver driver;
    public static Logger log = LogManager.getLogger(loadForwardOrderPage.class);
    UtlActions actions;
    TextFileReadWrite txt = new TextFileReadWrite();
    int totalSum=0;

    public loadForwardOrderPage(WebDriver driver) {
        this.driver = driver;
        actions = new UtlActions(driver);
        loadForwardOrderLocators();
    }

    // Validate page is loaded or not
    public boolean validateLoadForwardOrderPageLoad() {
        log.info("Page Title.." + actions.getText(loadForwardOrderTitle));
        boolean element =  actions.waitForElementToBeDisplayed(loadForwardOrderTitle, Duration.ofSeconds(5));
        return element;
    }

    // Validate all label names in Load forward screen
    public void validateLabelName(String[] actualLabelName)
    {
        List<String> expectedLabelName = new ArrayList<String>();
        for(WebElement labelName:getLabelNames())
        {
            expectedLabelName.add(labelName.getText());
        }
        Assert.assertEquals(expectedLabelName, Arrays.asList(actualLabelName));
    }

    public List<WebElement> getLabelNames() {
        return driver.findElements(allLabels);
    }

    public void enterForwardOrderId(String forwardId)
    {
        actions.setText(forwardOrderIdTxt,forwardId);
        log.info("Entered forward ID is: "+forwardId);
    }

    public void validateButtonIsDisabled(String btnName)
    {
        String buttonBYName = buttonByName.replace("buttonName", btnName);
        String btnDisableStatus = actions.getTextByAttributeName(
                By.xpath(buttonBYName), "disabled");
        Assert.assertEquals(btnDisableStatus, "true");
    }

    public void validateButtonIsEnabled(String btnName)
    {
        String buttonBYName = buttonByName.replace("buttonName", btnName);
        boolean loginBtnEnableStatus = actions.isEnabled(By.xpath(buttonBYName));
        Assert.assertTrue(loginBtnEnableStatus);
    }

    public void clickButtonByName(String btnName)
    {
        String buttonBYName = buttonByName.replace("buttonName", btnName);
        WebElement folderName = driver.findElement(By.xpath(buttonBYName));
        actions.clickElementWithActions(driver,folderName);
    }

    public void enterOpenDate()
    {
        String currDate = actions.getCurrentDate();
        actions.setText(openDateTxt, currDate);
        actions.click(clickOutside);
    }

    public String enterCutOffDate(int days)
    {
        String nextDate = null;
        try {
            nextDate = actions.getNextDaysDate(days);
            System.out.println("Next date in string: "+nextDate);
            actions.setText(cutOffDateTxt, nextDate);
            actions.click(forwardOrderIdTxt);
        }catch(Exception e){}
        return nextDate;
    }

    public void importForwardOrder(String filePath)
    {
        actions.sendKeysByRobotClass(importBtn, filePath);
        System.out.println("Imported Successfully");
    }
    public void closePopup()
    {
        actions.click(SOPCloseBtn);
    }
    public void validateSOPPopUp()
    {
        if(actions.isDisplayed(SOPPopUp, 10))
            closePopup();
    }

    public void ValidateForwardOrders(String filePath)
    {
        Map<String, Integer> aggregatedData =  txt.FileReadAndWrite(filePath);
        List<String> groupName = new ArrayList<String>();
        List<Integer> groupCunt = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : aggregatedData.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
            groupName.add(entry.getKey());
            groupCunt.add(entry.getValue());
            totalSum = totalSum +entry.getValue();
        }
        System.out.println("Total Forward Order: "+totalSum);
        log.info("Total Forward Order: "+totalSum);
        Assert.assertEquals(groupName.stream().sorted().collect(Collectors.toList()),
                getGroupNameList().stream().sorted().collect(Collectors.toList()));
        Assert.assertEquals(groupCunt.stream().sorted().collect(Collectors.toList()),
                getGroupQuntityList().stream().sorted().map(Integer::parseInt).collect(Collectors.toList()));
    }
    public List<String> getGroupNameList()
    {
        List<String> expectedGroupName = new ArrayList<String>();
        for(WebElement groupName:getGroupNames())
        {
            expectedGroupName.add(groupName.getText());
        }
        return expectedGroupName;
    }

    public List<WebElement> getGroupNames() {
        return driver.findElements(forwardOrderGroupValue);
    }

    public List<String> getGroupQuntityList()
    {
        List<String> expectedGroupQuntity = new ArrayList<String>();
        for(WebElement groupQuntity:getGroupQuntity())
        {
            expectedGroupQuntity.add(groupQuntity.getText());
        }
        return expectedGroupQuntity;
    }

    public List<WebElement> getGroupQuntity() {
        return driver.findElements(forwardOrderGroupQuntity);
    }

    public void clickFirstGroup()
    {
        List<WebElement> groupCount = driver.findElements(forwardOrderGroupCount);
        groupCount.get(0).click();
    }

    public void validateTotalForwardOrders()
    {
        String expectedForwardOrders = actions.getTextByValueAttribute(totalForwardOrders);
        log.info("Total Expected Forward Order: "+expectedForwardOrders);
        Assert.assertEquals(expectedForwardOrders, Integer.toString(totalSum));
    }

    }
