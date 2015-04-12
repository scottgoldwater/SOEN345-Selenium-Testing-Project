package TestMeetingRoomBookingSystem;

/**
 * Created by Patrick on 4/12/2015.
 *
 */

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class TestAddANewRoom {

    private WebDriver driver;
    private final String URL = "http://donatepls.com/mrbs-1.4.11/web/admin.php";
    private final String USERNAME = "admin";
    private final String PASSWORD = "admin";
    private final String ROOMNAME = "Love Shack";
    private final String DESCRIPTION = "Rock hard walls";
    private final String CAPACITY = "2";

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.navigate().to(URL);
        driver.findElement(By.id("NewUserName")).sendKeys(USERNAME);
        driver.findElement(By.id("NewUserPassword")).sendKeys(PASSWORD);
        driver.findElement(By.id("NewUserPassword")).sendKeys(Keys.ENTER);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void testAddANewRoomStandardFlow() throws Exception{
        driver.navigate().to(URL);
        driver.findElement(By.linkText("Rooms")).click();

        // 1a.1 The administrator enters the room name
        driver.findElement(By.id("room_name")).clear();
        driver.findElement(By.id("room_name")).sendKeys(ROOMNAME);

        // 1a.2 The administrator enters room information
        driver.findElement(By.id("room_description")).clear();
        driver.findElement(By.id("room_description")).sendKeys(DESCRIPTION);
        driver.findElement(By.id("room_capacity")).clear();
        driver.findElement(By.id("room_capacity")).sendKeys(CAPACITY);

        // 1a.3 The administrator requests to create the room
        driver.findElement(By.xpath("//input[@value='Add Room']")).click();

        // 1a.4 The system adds the new room to the database
        Assert.assertEquals(ROOMNAME, driver.findElement(By.linkText(ROOMNAME)).getText());
        Assert.assertTrue(isElementPresent(By.linkText(ROOMNAME)));

        // 1a.5 Use case ends successfully
    }

    @Test
    public void testAddNewRoomAlternateFlow() throws Exception {
        driver.navigate().to(URL);
        driver.findElement(By.linkText("Rooms")).click();

        // 1b.1 The administrator enters an empty room name
        driver.findElement(By.id("room_name")).clear();
        
        // 1b.3 The administrator requests to create the room
        driver.findElement(By.xpath("//input[@value='Add Room']")).click();

        // 1b.4 The system displays a message to the user that they did not successfully add a room
        Assert.assertEquals("You have not entered a name!", driver.findElement(By.cssSelector("p.error")).getText());

        // 1b.5 Use case ends unsuccessfully
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
