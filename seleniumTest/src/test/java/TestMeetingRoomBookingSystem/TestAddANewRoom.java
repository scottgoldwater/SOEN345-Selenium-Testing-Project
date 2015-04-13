package TestMeetingRoomBookingSystem;

/**
 * Created by Patrick on 4/12/2015.
 *
 */

import TestMeetingRoomBookingSystem.Rules.LogInRule;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class TestAddANewRoom {

    private final String ROOMNAME = "Love Shack";
    private final String DESCRIPTION = "Rock hard walls";
    private final String CAPACITY = "2";

    @Rule
    public LogInRule session = new LogInRule("http://donatepls.com/mrbs-1.4.11/web/admin.php","admin","admin");

    @Test
    public void testAddANewRoomStandardFlow() throws Exception{
        session.driver.findElement(By.linkText("Rooms")).click();

        // 1a.1 The administrator enters the room name
        session.driver.findElement(By.id("room_name")).clear();
        session.driver.findElement(By.id("room_name")).sendKeys(ROOMNAME);

        // 1a.2 The administrator enters room information
        session.driver.findElement(By.id("room_description")).clear();
        session.driver.findElement(By.id("room_description")).sendKeys(DESCRIPTION);
        session.driver.findElement(By.id("room_capacity")).clear();
        session.driver.findElement(By.id("room_capacity")).sendKeys(CAPACITY);

        // 1a.3 The administrator requests to create the room
        session.driver.findElement(By.xpath("//input[@value='Add Room']")).click();

        // 1a.4 The system adds the new room to the database
        Assert.assertEquals(ROOMNAME, session.driver.findElement(By.linkText(ROOMNAME)).getText());
        Assert.assertTrue(isElementPresent(By.linkText(ROOMNAME)));

        // 1a.5 Use case ends successfully
    }

    @Test
    public void testAddNewRoomAlternateFlow() throws Exception {
        session.driver.findElement(By.linkText("Rooms")).click();

        // 1b.1 The administrator enters an empty room name
        session.driver.findElement(By.id("room_name")).clear();

        // 1b.3 The administrator requests to create the room
        session.driver.findElement(By.xpath("//input[@value='Add Room']")).click();

        // 1b.4 The system displays a message to the user that they did not successfully add a room
        Assert.assertEquals("You have not entered a name!", session.driver.findElement(By.cssSelector("p.error")).getText());

        // 1b.5 Use case ends unsuccessfully
    }

    private boolean isElementPresent(By by) {
        try {
            session.driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
