import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterSuite;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

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
	
	static java.util.Properties properties;
	static String browserName;
	public java.util.logging.Level levelRef;
	public java.util.concurrent.TimeUnit timeUnitRef;
	static BaseTestConfiguration BaseTestConfiguration;
	
	public BaseTestConfiguration() {
		if(properties==null){
			properties = new Properties();
			loadProperties(properties);			
		}
		initializeComponents();		
	}
	
	//Setup method for the TestSuite/TestRun
	public void initializeComponents() {
	  try {
		  devicePlatform = properties.getProperty("devicePlatform");	
		  if(devicePlatform != null && !devicePlatform.isEmpty())
			  browserName = devicePlatform.trim().toUpperCase();
		  else 
			  browserName = "CHROME";
		  
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
		  if(driver != null)
			  driver.quit();
		  e.printStackTrace();
	  }
	}
	
	public static BaseTestConfiguration getBaseConfiguration() {
	  if(BaseTestConfiguration == null)
		  BaseTestConfiguration = new BaseTestConfiguration();
	   return BaseTestConfiguration;
	}
			
	public static java.util.Properties getProperties() {
	  return properties; 
	}
	
	//Set Driver Path based on Browser Type
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

	public static java.lang.String getDevicePlatform() {
		return devicePlatform;
	}

	public static void setDevicePlatform(java.lang.String devicePlatform) {
		BaseTestConfiguration.devicePlatform = devicePlatform;
	}

	public static java.lang.String getEvidencePath() {
		return evidencePath;
	}

	public static void setEvidencePath(java.lang.String evidencePath) {
		BaseTestConfiguration.evidencePath = evidencePath;
	}

	public static org.openqa.selenium.WebDriver getDriver() {
		return driver;
	}

	public static void setDriver(org.openqa.selenium.WebDriver driver) {
		BaseTestConfiguration.driver = driver;
	}

	public static String getBrowserName() {
		return browserName;
	}

	public static void setBrowserName(String browserName) {
		BaseTestConfiguration.browserName = browserName;
	}
	
	//Load properties file for defining and configuring test related values
	private static void loadProperties(Properties properties){
		File file = new File("testresources.properties");		
		try {			
			properties.load(new FileInputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}