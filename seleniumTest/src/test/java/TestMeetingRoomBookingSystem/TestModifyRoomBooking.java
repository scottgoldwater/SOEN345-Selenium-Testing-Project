package TestMeetingRoomBookingSystem;

import TestMeetingRoomBookingSystem.Rules.LogInRule;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.*;

public class TestModifyRoomBooking {

    @Rule public LogInRule session = new LogInRule();

    private String date = "04/12/2010";
    private String xPath = "/html/body/div[2]/table[1]/tbody/tr[1]/td[2]/div/a";
    private String name = "1337";

    @Before
    public void createRoom(){
        session.driver.findElement(By.className("dateselector")).findElement(By.id("datepicker")).sendKeys(Keys.chord(Keys.CONTROL, "A"), date, Keys.ENTER);
        session.driver.findElement(By.xpath(xPath)).click();
        session.driver.findElement(By.id("name")).sendKeys(name);
        session.driver.findElement(By.id("description")).sendKeys(name);

        session.driver.findElement(By.name("save_button")).click();
        List<WebElement> rooms = session.driver.findElements(By.xpath("//a[@title='"+name+"']"));
        for (WebElement e : rooms){
            if (e.isDisplayed())
                e.click();
        }
    }

    @After
    public void deleteRoom(){
        session.driver.findElement(By.id("view_entry_nav")).findElements(By.tagName("div")).get(1).findElement(By.tagName("a")).click();
        session.driver.switchTo().alert().accept();
    }

    @Test
    public void standardFlowTest(){


    }

}
