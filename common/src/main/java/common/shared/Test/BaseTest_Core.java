package common.shared.Test;

import common.core.Library.Constant;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

public class BaseTest_Core extends Constant
{
    public static Properties prop ;

    public static Logger log = LogManager.getLogger(BaseTest_Core.class);

    public static WebDriver driver;

    public static String browserName;

    public static String accountUrl;

    public static String vehicleUrl;

    public static String mcOrderUrl;

    public static String accountUser;

    public static String password;
    public static String filePathForScreenshot = "C:\\Core Automation\\testing-automation" +
            "\\src\\test\\resources\\screenshot";

    public void readProperty()
    {
        prop = new Properties();
        try {
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
                    + "/src/test/resources/property/config.properties");
            prop.load(fis);
            browserName = prop.getProperty("browser");
            accountUrl = prop.getProperty("AccountUrl");
            vehicleUrl = prop.getProperty("VehicleUrl");
            mcOrderUrl = prop.getProperty("MCOrderUrl");
            accountUser = prop.getProperty("accntUsername");
            password = prop.getProperty("password");
        }catch (Exception e){}
    }

    public String GetValidUrl(String url)
    {
        if(url.equalsIgnoreCase("Account"))
        {
            url = accountUrl;
            log.info("Account Url.."+url);
        }
        else if(url.equalsIgnoreCase("Vehicle"))
        {
            url = vehicleUrl;
            log.info("Vehicle Url.."+url);
        }
        else if(url.equalsIgnoreCase("MCOrder"))
        {
            url = mcOrderUrl;
            log.info("MCOrder Url.."+url);
        }
        return url;
    }

    public void lunchApplication(String url)
    {
        readProperty();
        String appUrl = GetValidUrl(url);
        if (browserName.contains("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();

        } else if (browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(appUrl);
    }

    public void hitUrl(String url)
    {
        String appUrl1 = GetValidUrl(url);
        driver.get(appUrl1);
    }
}
