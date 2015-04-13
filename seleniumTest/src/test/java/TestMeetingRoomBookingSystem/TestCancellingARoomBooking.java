package TestMeetingRoomBookingSystem;

import TestMeetingRoomBookingSystem.Rules.LogInRule;
import static org.junit.Assert.*;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.UUID;

public class TestCancellingARoomBooking {
    private final String baseURL = "http://donatepls.com/mrbs-1.4.11/web/";
    private final String dayURL = baseURL + "day.php?year=2015&month=4&day=10&area=1&room=4";
    private String date = "04/10/2010";
    private String xPath = "/html/body/div[2]/table[1]/tbody/tr[1]/td[4]/div/a";
    private String name = "toDelete";

    @Rule
    public LogInRule session = new LogInRule("http://donatepls.com/mrbs-1.4.11/web/admin.php","rob","123");

    @Before
    public void createRoom(){
        session.driver.navigate().to(dayURL);
        session.driver.findElement(By.xpath(xPath)).click();
        session.driver.findElement(By.id("name")).sendKeys(name);
        session.driver.findElement(By.id("description")).sendKeys(name);

        session.driver.findElement(By.name("save_button")).click();

    }

    @Test
    public void standardFlowTest(){
        // Find booking with the title given when creating the room
        List<WebElement> rooms = session.driver.findElements(By.xpath("//a[@title='" + name + "']"));
        for (WebElement e : rooms){
            if (e.isDisplayed())
                e.click();
        }
        session.driver.findElement(By.id("view_entry_nav")).findElements(By.tagName("div")).get(1).findElement(By.tagName("a")).click();
        session.driver.switchTo().alert().accept();
        WebElement bookingSlot = session.driver.findElement(By.xpath("/html/body/div[2]/table[1]/tbody/tr[1]/td[4]/div/a"));
        // Assert that the slot the booking was added to now links to the expected page when empty.
        assertEquals(baseURL + "edit_entry.php?area=1&room=3&hour=7&minute=0&year=2015&month=4&day=10", bookingSlot.getAttribute("href"));
    }
}
