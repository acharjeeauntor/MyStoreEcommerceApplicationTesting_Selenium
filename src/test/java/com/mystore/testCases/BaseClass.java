package com.mystore.testCases;

import com.mystore.utilities.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.OperatingSystem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BaseClass {
    public static WebDriver driver;
    public static Logger logger;
    public static Config config = new Config();
    public static String baseUrl = config.getBaseUrl();
    public static String loginEmail = config.getLoginEmail();
    public static String loginPassword = config.getLoginPassword();

    public static String randomeNum() {
        String generatedString2 = RandomStringUtils.randomNumeric(6);
        return (generatedString2);
    }

    @Parameters("browser")
    @BeforeClass
    public void setUp(String br) {
        logger = Logger.getLogger("Demo E-commerce");
        PropertyConfigurator.configure("log4j.properties");

        if (br.equals("chrome")) {
            WebDriverManager.chromedriver().operatingSystem(OperatingSystem.LINUX).setup();
            driver = new ChromeDriver();
        } else if (br.equals("firefox")) {
            WebDriverManager.firefoxdriver().operatingSystem(OperatingSystem.LINUX).setup();
            driver = new FirefoxDriver();
        } else if (br.equals("ie")) {
            WebDriverManager.iedriver().operatingSystem(OperatingSystem.LINUX).setup();
            driver = new InternetExplorerDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(baseUrl);
        //driver.manage().deleteAllCookies();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    public void captureScreen(WebDriver driver, String tname) throws IOException {

        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File target = new File(System.getProperty("user.dir") + "/Screenshots/" + tname + ".png");
        FileUtils.copyFile(source, target);
        System.out.println("Screenshot Taken");


    }

    public String randomestring() {
        String generatedstring = RandomStringUtils.randomAlphabetic(4);
        return (generatedstring);
    }

    public boolean isAlertPresent(){
        try{
            return true;
        }catch (NoAlertPresentException e){
            return false;
        }
    }


}