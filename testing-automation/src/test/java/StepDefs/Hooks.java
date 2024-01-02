package StepDefs;

import common.shared.Test.BaseTest_Core;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks extends BaseTest_Core
{
    @Before
    public void setUp(Scenario scenario)
    {
        log.info(scenario.getName());
        if(scenario.getName().contains("Scenario 1"))
        {
            lunchApplication(accountURL);
        }
        else if(scenario.getName().contains("Scenario 2"))
        {
            lunchApplication(mcOrderURL);
        }
        else if(scenario.getName().contains("Scenario 3"))
        {
            lunchApplication(vehicleURL);
        }
        else if(scenario.getName().contains("Scenario 4"))
        {
            lunchApplication(vehicleURL);
        }
        else if(scenario.getName().contains("Scenario 5"))
        {
            lunchApplication(mcOrderURL);
        }
    }
    @After(order = 0)
    public void quitBrowser() {
        driver.quit();
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
