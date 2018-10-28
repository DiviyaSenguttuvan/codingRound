import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Properties;

public class FlightBookingTest {

	WebDriver driver;
	BaseTestConfiguration base;
	Properties resource;
	
	@BeforeTest
	public void beforeTest() {
		base = BaseTestConfiguration.getBaseConfiguration();
		driver = BaseTestConfiguration.getDriver();
		resource = BaseTestConfiguration.getProperties();
	}

    @Test
    public void testThatResultsAppearForAOneWayJourney() {
    	try {
    		driver.get(resource.getProperty("flightbooking_weburl"));
            waitFor(2000);
            driver.findElement(By.id(resource.getProperty("flightbooking_oneway"))).click();

            driver.findElement(By.id(resource.getProperty("flightbooking_fromTag_id"))).clear();
            driver.findElement(By.id(resource.getProperty("flightbooking_fromTag_id"))).sendKeys(resource.getProperty("flightbooking_fromTag_value"));

            //wait for the auto complete options to appear for the origin
            waitFor(5000);
            List<WebElement> originOptions = driver.findElement(By.id(resource.getProperty("flightbooking_originoptions_id"))).findElements(By.tagName(resource.getProperty("flightbooking_originoptions_li_id")));
            originOptions.get(Integer.valueOf(resource.getProperty("flightbooking_origin_index"))).click();

            waitFor(2000);
            driver.findElement(By.id(resource.getProperty("flightbooking_toTag_id"))).clear();
            driver.findElement(By.id(resource.getProperty("flightbooking_toTag_id"))).sendKeys(resource.getProperty("flightbooking_toTag_value"));

            //wait for the auto complete options to appear for the destination

            waitFor(2000);
            //select the first item from the destination auto complete list
            List<WebElement> destinationOptions = driver.findElement(By.id(resource.getProperty("flightbooking_destoptions_id"))).findElements(By.tagName(resource.getProperty("flightbooking_destoptions_li_id")));
            destinationOptions.get(Integer.valueOf(resource.getProperty("flightbooking_dest_index"))).click();

            driver.findElement(By.xpath(resource.getProperty("flightbooking_datepicker_xpath"))).click();

            //all fields filled in. Now click on search
            driver.findElement(By.id(resource.getProperty("flightbooking_searchbtn_id"))).click();

            waitFor(5000);
            //verify that result appears for the provided journey search
            Assert.assertTrue(isElementPresent(By.className(resource.getProperty("flightbooking_searchSummary_class"))));
    	} catch (Exception e) {
    		Assert.fail("Exception / Error while running FlightBookingTest : "+e);
    	}
    }

    @AfterTest
    public void quitDriver(){
    	driver.quit();
    }
    
    private void waitFor(int durationInMilliSeconds) {
        try {
            Thread.sleep(durationInMilliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }    
}
