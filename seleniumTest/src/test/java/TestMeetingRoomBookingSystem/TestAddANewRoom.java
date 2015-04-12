package TestMeetingRoomBookingSystem;

/**
 * Created by Patrick on 4/12/2015.
 *
 */

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestAddANewRoom {

    WebDriver driver = new FirefoxDriver();


    @Before
    public void setUpWebDriver(){
        driver.navigate().to("http://scottgoldwater.com");
    }

    @After
    public void closeWebDriver(){
        driver.close();
    }


    @Test
    public void TitleTest(){
        Assert.assertTrue("Title should start with", driver.getTitle().startsWith("Scott Goldwater"));
    }

    @Test
    public void pictureTest(){
        Assert.assertEquals(driver.findElement(By.className("title")).getText(),"Hello World");


    }
}
