package StefDefs;

import common.shared.Test.BaseTest_Digital;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks extends BaseTest_Digital
{
    @Before
    public void setUp(Scenario scenario)
    {
        try
        {
            String url = "https://pre-production.mazda.com.au/Util/login.aspx?ReturnUrl=%2f";
            lunchApplication(url);
        }catch(Exception e){}

    }
    @After(order = 0)
    public void quitBrowser() {
//        driver.quit();
   }

    @After(order = 1)
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            // take screenshot:
            String screenshotName = scenario.getName().replaceAll(" ", "_");
            byte[] sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(sourcePath, "image/png", screenshotName);

        }
    }
}