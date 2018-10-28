package testvagrant.codingground;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.sun.javafx.PlatformUtil;

import java.util.logging.Level;

public class BaseTestConfiguration {
	
	static java.lang.String devicePlatform;
	static java.lang.String evidencePath;
	
	static org.openqa.selenium.WebDriver driver;
	
	public org.openqa.selenium.ie.InternetExplorerDriver internetExplorerDriver;
	public org.openqa.selenium.firefox.FirefoxDriver firefoxDriver;
	public org.openqa.selenium.chrome.ChromeDriver chromeDriver;
	public org.openqa.selenium.safari.SafariDriver safariDriver;
	public org.openqa.selenium.logging.LogType logType;
	public org.openqa.selenium.logging.LoggingPreferences logPrefs;
	public org.openqa.selenium.remote.CapabilityType capabilityType;
	public org.openqa.selenium.remote.DesiredCapabilities caps;
	
	String chromePath;
	static String dependentFilesPath;
	static java.util.Properties prop;
	static String browserName;
	public java.util.logging.Level levelRef;
	public java.util.concurrent.TimeUnit timeUnitRef;
	
	//Setup method for the TestSuite/TestRun
	@BeforeSuite
	public void setUp() {
	  try {
		  devicePlatform = System.getProperty("devicePlatform");		
		  String platform[]=devicePlatform.split("-");
		  browserName = platform[1];
		  switch(browserName) {
				case "FIREFOX":
					caps = DesiredCapabilities.firefox();
					logPrefs = new LoggingPreferences();
					logPrefs.enable(LogType.BROWSER, Level.ALL);
					caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
					driver=new FirefoxDriver(caps);
					break;
				case "IE":
					setDriverPath(browserName);
					System.setProperty("webdriver.ie.driver.loglevel","DEBUG");
					caps = DesiredCapabilities.internetExplorer();
					logPrefs = new LoggingPreferences();
					logPrefs.enable(LogType.BROWSER, Level.ALL);
					caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
					driver=new InternetExplorerDriver(caps);
					break;
				case "CHROME":
					setDriverPath(browserName);
					caps = DesiredCapabilities.chrome();
					logPrefs = new LoggingPreferences();
					logPrefs.enable(LogType.BROWSER, Level.ALL);
					caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
					driver=new ChromeDriver(caps);
					break;
				case "SAFARI":
					caps = DesiredCapabilities.safari();
					logPrefs = new LoggingPreferences();
					logPrefs.enable(LogType.BROWSER, Level.ALL);
					caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
					driver=new SafariDriver(caps);
					break;	
				case "EDGE":
					setDriverPath(browserName);
					org.openqa.selenium.edge.EdgeOptions edgeOptions = new org.openqa.selenium.edge.EdgeOptions();
					edgeOptions.setPageLoadStrategy("normal");
					caps = DesiredCapabilities.edge();
					logPrefs = new LoggingPreferences();
					logPrefs.enable(LogType.BROWSER, Level.ALL);
					caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
					caps.setCapability(org.openqa.selenium.edge.EdgeOptions.CAPABILITY, edgeOptions);
					driver = new org.openqa.selenium.edge.EdgeDriver(caps);
					break;	
				case "FIREFOXGECKO":
					setDriverPath(browserName);
					driver = new FirefoxDriver();
					break;	
			}
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  } catch (Exception e) {
		  tearDown();
	  }
	}
	
	//Teardown method for the TestSuite/TestRun                         
	@AfterSuite
	public void tearDown() {
		try {
			driver.quit();
	   }  catch (Exception e) {
			System.out.println("Exception occured while quitting from driver during tear down .");
	   }
	}
	
	public static BaseTestConfiguration getMain()
	{
	  if(main==null)main=new Main();
	   return main;
	}
	
	public static java.lang.String getWebURL()
	{
	   return webURL;
	}
	
	public static org.openqa.selenium.WebDriver getDriver()
	{
	  return driver;
	}
	
	public static void setDriver(WebDriver driver){
		Main.driver = driver;
	}
	
	public static void setDriver()
	{
	   Main.driver = driver; 
	}
	
	public static java.lang.String getDevicePlatform()
	{
	  return devicePlatform;
	}
	
	public static void setDevicePlatform()
	{
	   Main.devicePlatform = devicePlatform;
	}
	
	public static java.lang.String getProjectPath()
	{
	  return projectPath;
	}
	
	public static void setProjectPath()
	{
	   Main.projectPath = projectPath;
	}
	public static java.lang.String getRunlistId()
	{
	  return runListId;
	}
	
	public static void setrunListId()
	{
	  Main.runListId = runListId;
	}
	public static java.lang.String getdependentFilesPath()
	{
	  return dependentFilesPath;
	}
	public static void setdependentFilesPath()
	{
	   Main.dependentFilesPath = dependentFilesPath;
	}
	
	public static java.lang.String getBrowserName()
	{
	  return browserName; 
	}
	
	public static String getEvidencePath()
	{
	 return evidencePath; 
	}
	public static void setEvidencePath()
	{
	  Main.evidencePath = evidencePath;
	}
	public static java.util.Properties getIdentifiersPropertyFile()
	{
	  return prop; 
	}
	
	private void setDriverPath(String browserName) {
        if (System.getProperty("os.name").contains("Mac")) {        	
			System.setProperty("webdriver.chrome.driver", "chromedriver");
        } 
        
        if (System.getProperty("os.name").contains("Win")) {
        	switch(browserName) {				
				case "IE":
					System.setProperty("webdriver.ie.driver","IEDriverServer.exe");
					break;
				case "CHROME":
					System.setProperty("webdriver.chrome.driver","chromedriver.exe");
					break;
				case "EDGE":
					System.setProperty("webdriver.edge.driver","MicrosoftWebDriver.exe");
					break;	
				case "FIREFOXGECKO":
					System.setProperty("webdriver.gecko.driver","geckodriver.exe");
					break;
				default:
					System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
					break;
        	}
        }
        
        if (System.getProperty("os.name").contains("Linux")) {
            System.setProperty("webdriver.chrome.driver", "chromedriver_linux");
        }
	}
}