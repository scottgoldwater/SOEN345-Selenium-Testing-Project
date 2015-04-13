package TestMeetingRoomBookingSystem;

import TestMeetingRoomBookingSystem.Rules.LogInRule;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.*;

public class TestModifyRoomBooking {

    @Rule public LogInRule session = new LogInRule("http://donatepls.com/mrbs-1.4.11/web/admin.php","rob","123");

    private String date = "04/12/2010";
    private String xPath = "/html/body/div[2]/table[1]/tbody/tr[1]/td[2]/div/a";
    private String name;
    private String description;


    @Before
    public void createRoom(){
        name = "Homer";
        description = "Simpson";
        session.driver.findElement(By.className("dateselector")).findElement(By.id("datepicker")).sendKeys(Keys.chord(Keys.CONTROL, "A"), date, Keys.ENTER);
        session.driver.findElement(By.xpath(xPath)).click();
        session.driver.findElement(By.id("name")).sendKeys(name);
        session.driver.findElement(By.id("description")).sendKeys(description);

        session.driver.findElement(By.name("save_button")).click();

    }

    @After
    public void deleteRoom(){

        session.driver.findElement(By.className("dateselector")).findElement(By.id("datepicker")).sendKeys(Keys.chord(Keys.CONTROL, "A"), date, Keys.ENTER);
        List<WebElement> rooms = session.driver.findElements(By.xpath("//a[@title='"+description+"']"));
        for (WebElement e : rooms){
            if (e.isDisplayed())
                e.click();
        }
        session.driver.findElement(By.id("view_entry_nav")).findElements(By.tagName("div")).get(1).findElement(By.tagName("a")).click();
        session.driver.switchTo().alert().accept();


    }

    @Test
    public void standardFlowTest(){
        List<WebElement> rooms = session.driver.findElements(By.xpath("//a[@title='"+description+"']"));
        for (WebElement e : rooms){
            if (e.isDisplayed())
                e.click();
        }

        session.driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/a")).click();
        name = "Gandalf";
        description = "The Grey";

        session.driver.findElement(By.id("name")).sendKeys(Keys.chord(Keys.CONTROL,"A"),name);
        session.driver.findElement(By.id("description")).sendKeys(Keys.chord(Keys.CONTROL, "A"),Keys.chord(Keys.CONTROL, "A") , description);
        session.driver.findElement(By.xpath("/html/body/div[2]/form/fieldset/fieldset/div[2]/input")).click();

        List<WebElement> changedRooms = session.driver.findElements(By.xpath("//a[@title='"+description+"']"));
        for (WebElement e : changedRooms){
            if (e.isDisplayed())
                e.click();
        }

        Assert.assertEquals(session.driver.findElement(By.xpath("/html/body/div[2]/h3")).getText(),name);
        Assert.assertEquals(session.driver.findElement(By.xpath("/html/body/div[2]/table/tbody/tr[1]/td[2]")).getText(),description);
    }

}
