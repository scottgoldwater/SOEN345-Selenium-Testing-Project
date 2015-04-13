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
    private String name;
    private String description;

    @Before
    public void createRoom(){
        name = "Homer";
        description = "Simpson";
        gotoDate();
        session.driver.findElement(By.xpath("/html/body/div[2]/table[1]/tbody/tr[1]/td[2]/div/a")).click();
        session.driver.findElement(By.id("name")).sendKeys(name);
        session.driver.findElement(By.id("description")).sendKeys(description);

        session.driver.findElement(By.name("save_button")).click();
    }

    @After
    public void deleteRoom(){

        gotoDate();
        selectBooking();

        session.driver.findElement(By.id("view_entry_nav")).findElements(By.tagName("div")).get(1).findElement(By.tagName("a")).click();
        session.driver.switchTo().alert().accept();
    }

    @Test
    public void standardFlowTest(){
        selectBooking();

        session.driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/a")).click();
        name = "Gandalf";
        description = "The Grey";

        session.driver.findElement(By.id("name")).sendKeys(Keys.chord(Keys.CONTROL,"A"),name);
        session.driver.findElement(By.id("description")).sendKeys(Keys.chord(Keys.CONTROL, "A"),Keys.chord(Keys.CONTROL, "A") , description);
        session.driver.findElement(By.xpath("/html/body/div[2]/form/fieldset/fieldset/div[2]/input")).click();

        selectBooking();

        Assert.assertEquals(session.driver.findElement(By.xpath("/html/body/div[2]/h3")).getText(), name);
        Assert.assertEquals(session.driver.findElement(By.xpath("/html/body/div[2]/table/tbody/tr[1]/td[2]")).getText(), description);
    }

    @Test
    public void altFlowOtherUserTest(){
        session.setCredentials("test", "test123");
        session.logout();
        session.login();

        gotoDate();
        selectBooking();
        session.driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/a")).click();
        Assert.assertEquals(session.driver.findElement(By.xpath("/html/body/div[2]/h1")).getText(), "Access Denied");
        session.logout();
        session.setCredentials("rob", "123");
        session.login();
    }

    @Test
    public void altFlowOtherAdminTest(){
        session.setCredentials("admin", "admin");
        session.logout();
        session.login();

        gotoDate();
        selectBooking();

        session.driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/a")).click();
        name = "Gandalf";
        description = "The Grey";

        session.driver.findElement(By.id("name")).sendKeys(Keys.chord(Keys.CONTROL, "A"), name);
        session.driver.findElement(By.id("description")).sendKeys(Keys.chord(Keys.CONTROL, "A"), Keys.chord(Keys.CONTROL, "A"), description);
        session.driver.findElement(By.xpath("/html/body/div[2]/form/fieldset/fieldset/div[2]/input")).click();

        selectBooking();

        Assert.assertEquals(session.driver.findElement(By.xpath("/html/body/div[2]/h3")).getText(), name);
        Assert.assertEquals(session.driver.findElement(By.xpath("/html/body/div[2]/table/tbody/tr[1]/td[2]")).getText(), description);
    }

    public void gotoDate(){
        session.driver.findElement(By.className("dateselector")).findElement(By.id("datepicker")).sendKeys(Keys.chord(Keys.CONTROL, "A"), date, Keys.ENTER);
    }

    public void selectBooking(){
        List<WebElement> rooms = session.driver.findElements(By.xpath("//a[@title='"+description+"']"));
        for (WebElement e : rooms){
            if (e.isDisplayed())
                e.click();
        }
    }


    @Test
    public void altFlowRemovedDescriptionTest(){
        selectBooking();

        session.driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/a")).click();
        String before_url = session.driver.getCurrentUrl();
        session.driver.findElement(By.id("name")).sendKeys(Keys.chord(Keys.CONTROL,"A"),Keys.DELETE);
        session.driver.findElement(By.id("description")).sendKeys(Keys.chord(Keys.CONTROL, "A"), Keys.chord(Keys.CONTROL, "A"), description);

        session.driver.findElement(By.xpath("/html/body/div[2]/form/fieldset/fieldset/div[2]/input")).click();
        Assert.assertEquals(before_url, session.driver.getCurrentUrl());
    }

    @Test
    public void altFlowInvalidDateTest(){
        selectBooking();

        session.driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/a")).click();


        session.driver.findElement(By.id("name")).sendKeys(Keys.chord(Keys.CONTROL, "A"), name);
        session.driver.findElement(By.id("description")).sendKeys(Keys.chord(Keys.CONTROL, "A"), Keys.chord(Keys.CONTROL, "A"), description);


        session.driver.findElement(By.id("end_datepicker")).sendKeys(Keys.chord(Keys.CONTROL, "A"), "01/01/2010");
        session.driver.findElement(By.xpath("/html/body/div[2]/form/fieldset/fieldset/div[2]/input")).click();
        String alert_text = session.driver.switchTo().alert().getText();
        session.driver.switchTo().alert().accept();
        Assert.assertEquals(alert_text, "Error: the start day cannot be after the end day.");
    }

}
