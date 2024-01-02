package common.shared.Utility;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class UtlActions
{
    private final WebDriver driver;
    DateFormat dateFormat;
    WebDriverWait wait;

    public UtlActions(WebDriver driver) {
        this.driver = driver;
    }

    // Perform entering text for a text field using sendkeys
    public boolean setText(By selector, String text) {
        try {
            waitForElement(selector, Duration.ofSeconds(100)).sendKeys(text);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean setTextByWebElement(WebElement element, String text) {
        try {
            element.clear();
            element.sendKeys(text);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public WebElement waitForElement(By selector, Duration seconds) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        return wait.until(ExpectedConditions.presenceOfElementLocated((selector)));
    }

    // Perform clear text for a text field
    public boolean clearText(By selector) {
        try {
            driver.findElement(selector).clear();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // Perform getting value for a text field by Attribute type as Value
    public String getTextByValueAttribute(By selector) {
        try {
            return driver.findElement(selector).getAttribute("value");
        } catch (NoSuchElementException e) {
            return null;
        }
    }
    // Perform getting value for a text field by Attribute type as Value
    public String getTextByAttributeName(By selector, String attrName) {
        try {
            return driver.findElement(selector).getAttribute(attrName);
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    // Perform getting text for any element
    public String getText(By selector) {
        try {
            return waitForElement(selector, Duration.ofSeconds(10)).getText();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    // Perform button click
    public boolean click(By selector) {
        try {
            driver.findElement(selector).click();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // Perform button click by id
    public boolean clickById(String id) {
        try {
            driver.findElement(By.id(id)).click();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // Check if Object is displayed
    public boolean isDisplayed(By selector, long seconds) {
        try {
            waitForElement(selector, Duration.ofSeconds(seconds));
            return driver.findElement(selector).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // Check if Object is Enabled
    public boolean isEnabled(By selector) {
        try {
            return driver.findElement(selector).isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // Check if Object is clickable
    public boolean isClickable(By selector) {
        try {
            WebElement element = driver.findElement(selector);
            if (element.isDisplayed() && element.isEnabled()) {
                return true;
            } else {
                return false;
            }
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean waitForElementToBeDisplayed(By selector, Duration seconds) {
        WebDriverWait wait = new WebDriverWait(this.driver, seconds);
        boolean elemFound = false;

        try {
                   wait.until(ExpectedConditions.presenceOfElementLocated((selector)));
                    wait.until(ExpectedConditions.visibilityOfElementLocated((selector)));
            elemFound = true;
        } catch (Exception e) {
            System.out.println("Element " + selector.toString() + " is not displayed.");
        }
        return elemFound;
    }

    public boolean waitForElementToBeClicable(By selector, Duration seconds) {
        WebDriverWait wait = new WebDriverWait(this.driver, seconds);
        boolean elemFound = false;

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated((selector)));
            wait.until(ExpectedConditions.visibilityOfElementLocated((selector)));
            wait.until(ExpectedConditions.elementToBeClickable((selector)));
            elemFound = true;
        } catch (Exception e) {
            System.out.println("Element " + selector.toString() + " is not displayed.");
        }
        return elemFound;
    }


    public void screenshot(String path, String pageName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        try {
            // Generate a timestamp
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp = dateFormat.format(new Date());

            // Append the timestamp to the filename
            String destinationPath = path + File.separator + pageName + "_" + timestamp + ".png";
            FileUtils.copyFile(source, new File(destinationPath));

            System.out.println("Screenshot captured and saved at: " + destinationPath);
        } catch (IOException e) {
            System.out.println("Could not take screenshot. Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }



    public void scrollToElement(By selector)
    {
        try
        {
            WebElement element = driver.findElement(selector);
            horizontalScrollToElement(element);
            Thread.sleep(500);
        }catch(Exception e){}
    }

    public void horizontalScrollToElement(WebElement element) {
        int elementY = element.getLocation().getY();
        Actions actions = new Actions(driver);
        actions.moveToElement(element)
                .clickAndHold()
                .moveByOffset(0, 30)
                .release()
                .perform();
    }
    public String getCurrentDate()
    {
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        return dateFormat.format(date);
    }
    public String getNextDaysDate(int days)
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, days);
        System.out.println(dateFormat.format(c.getTime()));
        return dateFormat.format(c.getTime());
    }
    public void sendKeysByjse(WebElement selector,String text)
    {
        try {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            // Set text content
            jsExecutor.executeScript("arguments[0].textContent = arguments[1]", selector, text);

            // Trigger input event
            jsExecutor.executeScript("var event = new Event('div', { bubbles: true }); " +
                    "arguments[0].dispatchEvent(event);", selector);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doubleClickByActionClass(WebElement element) {
        Actions actions = new Actions(driver);
        actions.doubleClick(element).build().perform();
    }

    public void sendKeysByRobotClass(By selector,String filepath)
    {
        try
        {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(selector));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            if (element.isEnabled() && element.isDisplayed()) {
                element.click();
            } else {
                System.out.println("Element not clickable");
            }

            Robot rb = new Robot();
            rb.delay(2000);
            StringSelection stringSelection = new StringSelection(filepath);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

            rb.keyPress(KeyEvent.VK_SHIFT);
            rb.keyPress(KeyEvent.VK_INSERT);
            rb.keyRelease(KeyEvent.VK_INSERT);
            rb.keyRelease(KeyEvent.VK_SHIFT);

            rb.keyPress(KeyEvent.VK_ENTER);
            rb.keyRelease(KeyEvent.VK_ENTER);

        }catch(Exception e){}
    }

    public void resizePageContent(WebDriver driver, double scale)
    {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

        // Set the width and height properties of the body element
//        String script = "document.body.style.width = '" + (scale * 100) + "%';";
        String script = "document.body.style.height = '" + scale + "%';";
//        String script = "document.body.style.fontSize = '" + (scale * 100) + "%';";
//        String script = "document.body.style.setProperty('--zoom', '" + scale + "');"
//                + "document.body.style.fontSize = 'calc(var(--zoom))';";
//        String script = "document.documentElement.style.setProperty('--zoom', '" + scale + "');";
        jsExecutor.executeScript(script);
    }

    public void performZoomAction() throws AWTException {
        Robot robot = new Robot();

        for(int i=0;i<4;i++) {
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_SUBTRACT);
            robot.keyRelease(KeyEvent.VK_SUBTRACT);
            robot.keyRelease(KeyEvent.VK_CONTROL);
        }
    }

    public void sendKeysByActionClass(WebDriver driver, WebElement selector, String text) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(selector));

            Actions actions = new Actions(driver);

            actions.moveToElement(selector)
                    .click()
                    .keyDown(Keys.CONTROL)
                    .sendKeys("a")
                    .keyUp(Keys.CONTROL)
                    .sendKeys(Keys.DELETE)
                    .sendKeys(Keys.DELETE)
                    .sendKeys(Keys.BACK_SPACE)
                    .perform();
            Thread.sleep(500);
            actions.sendKeys(text).perform();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // This method will change date format from 11/12/2023 to 11/dec/2023
    public String changeMonthDateFormatFromNumberToWord(String inputDateString)
    {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MMM/yyyy");
        String outputDateString = null;

        try {
            // Parse the input string to obtain a Date object
            Date inputDate = inputFormat.parse(inputDateString);

            // Format the Date object to the desired output format
             outputDateString = outputFormat.format(inputDate);
            
        } catch (ParseException e) {
            // Handle parsing exception
            e.printStackTrace();
        }
        return outputDateString;
    }

    public <K, V> Map<K, V> combineListsToMap(java.util.List<K> keys, java.util.List<V> values) {
        Map<K, V> combinedMap = new HashMap<>();

        // Check if the sizes of the lists are the same
        if (keys.size() == values.size()) {
            for (int i = 0; i < keys.size(); i++) {
                combinedMap.put(keys.get(i), values.get(i));
            }
        } else {
            throw new IllegalArgumentException("Lists must have the same size.");
        }

        return combinedMap;
    }

   public <K, V> boolean containsKeyValuePair(Map<K, V> map, K key, V value) {
        return map.containsKey(key) && map.get(key).equals(value);
    }

    public void clickElementWithActions(WebDriver driver, WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            Actions actions = new Actions(driver);
            actions.moveToElement(element)
                    .click()
                    .perform();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int generateRandomNumber(int numRange)
    {
        Random random = new Random();
        return random.nextInt(numRange);
    }
}
