import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SignInTest {

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
    public void shouldThrowAnErrorIfSignInDetailsAreMissing() {
    	try {
    		driver.get(resource.getProperty("signin_weburl"));
	        waitFor(2000);
	        driver.findElement(By.linkText(resource.getProperty("signin_linkText"))).click();
	        driver.findElement(By.id(resource.getProperty("signin_button_id"))).click();
	        waitFor(2000);
	        //Switch to frame window and click on SignIn Button
	        driver.switchTo().frame(resource.getProperty("signin_framename"));
	        driver.findElement(By.id(resource.getProperty("signin_frame_button"))).click();
	
	        String errors1 = driver.findElement(By.id(resource.getProperty("signin_frame_errors"))).getText();
	        Assert.assertTrue(errors1.contains(resource.getProperty("signin_frame_errors_message")));     
    	} catch(Exception e) {
    		Assert.fail("Exception / Error while running SignInTest : "+e);
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
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
