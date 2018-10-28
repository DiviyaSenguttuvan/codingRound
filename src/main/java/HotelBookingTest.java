import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class HotelBookingTest {

    WebDriver driver;
    BaseTestConfiguration base;
	Properties resource;
	
	@FindBy(linkText="Hotels")
    private WebElement hotelLink;

    @FindBy(id="Tags")
    private WebElement localityTextBox;

    @FindBy(id="SearchHotelsButton")
    private WebElement searchButton;

    @FindBy(id = "travellersOnhome")
    private WebElement travellerSelection;
	
	@BeforeTest
	public void beforeTest() {		
		base = BaseTestConfiguration.getBaseConfiguration();
		driver = BaseTestConfiguration.getDriver();
		resource = BaseTestConfiguration.getProperties();
		PageFactory.initElements(driver, this);
	}

	@Test
    public void shouldBeAbleToSearchForHotels() {
    	try{
    		driver.get(resource.getProperty("hotelbooking_weburl"));
            hotelLink.click();            
            waitFor(3000);
            localityTextBox.sendKeys(resource.getProperty("hotelbooking_locality_textbox"));
            new Select(travellerSelection).selectByVisibleText(resource.getProperty("hotelbooking_travellers_selection"));
            searchButton.click();
            waitFor(3000);
    	} catch(Exception e) {
    		Assert.fail("Exception / Error while running HotelBookingTest : "+e);
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
}
