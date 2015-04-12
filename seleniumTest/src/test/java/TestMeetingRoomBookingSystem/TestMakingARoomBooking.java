package TestMeetingRoomBookingSystem;

import TestMeetingRoomBookingSystem.Rules.LogInRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.*;

public class TestMakingARoomBooking {
    @Rule public LogInRule session = new LogInRule();


    @Test
    public void standardFlowTest(){

        final String date = generateRandomDate();
        session.driver.findElement(By.className("dateselector")).findElement(By.id("datepicker")).sendKeys(Keys.chord(Keys.CONTROL, "A"), date, Keys.ENTER);

        List<WebElement> columns = session.driver.findElement(By.xpath("/html/body/div[2]/table[1]/tbody")).findElements(By.tagName("tr"));
        int selectedColumn = randInt(0, columns.size() - 1);
        List<WebElement> rows = columns.get(selectedColumn).findElements(By.tagName("td"));
        int selectedRow = randInt(1, rows.size()-1);
        rows.get(selectedRow).findElement(By.tagName("a")).click();

        UUID nameID = UUID.randomUUID();
        UUID descriptionID = UUID.randomUUID();

        session.driver.findElement(By.id("name")).sendKeys(nameID.toString());
        session.driver.findElement(By.id("description")).sendKeys(descriptionID.toString());
        session.driver.findElement(By.name("save_button")).click();

        List<WebElement> rooms = session.driver.findElements(By.xpath("//a[@title='"+descriptionID+"']"));
        for (WebElement e : rooms){
            if (e.isDisplayed())
                e.click();
        }

        List<WebElement> details = session.driver.findElement(By.xpath("/html/body/div[2]/table/tbody")).findElements(By.tagName("tr"));
        Assert.assertEquals(details.get(0).findElements(By.tagName("td")).get(1).getText(),descriptionID.toString());
        Assert.assertEquals(session.driver.findElement(By.xpath("//h3")).getText(),nameID.toString());
        session.driver.findElement(By.id("view_entry_nav")).findElements(By.tagName("div")).get(1).findElement(By.tagName("a")).click();
        session.driver.switchTo().alert().accept();

    }

    /*
    Please note we cannot test if the error message is present because it's a html5 feature that is not apparent on every version of browser. It's a feature of the browser and therefore not in the dom of the webpage.
     */
    @Test
    public void alternateFlowOmitBriefDescriptionTest(){
        final String date = generateRandomDate();
        session.driver.findElement(By.className("dateselector")).findElement(By.id("datepicker")).sendKeys(Keys.chord(Keys.CONTROL, "A"), date, Keys.ENTER);

        List<WebElement> columns = session.driver.findElement(By.xpath("/html/body/div[2]/table[1]/tbody")).findElements(By.tagName("tr"));
        int selectedColumn = randInt(0, columns.size() - 1);
        List<WebElement> rows = columns.get(selectedColumn).findElements(By.tagName("td"));
        int selectedRow = randInt(1, rows.size() - 1);
        rows.get(selectedRow).findElement(By.tagName("a")).click();
        String before_url = session.driver.getCurrentUrl();
        session.driver.findElement(By.name("save_button")).click();
        Assert.assertEquals(before_url, session.driver.getCurrentUrl());
    }

    @Test
    public void alternateFlowInvalidDateTest() throws InterruptedException {
        final String date = generateRandomDate();
        session.driver.findElement(By.className("dateselector")).findElement(By.id("datepicker")).sendKeys(Keys.chord(Keys.CONTROL, "A"), date, Keys.ENTER);

        List<WebElement> columns = session.driver.findElement(By.xpath("/html/body/div[2]/table[1]/tbody")).findElements(By.tagName("tr"));
        int selectedColumn = randInt(0, columns.size() - 1);
        List<WebElement> rows = columns.get(selectedColumn).findElements(By.tagName("td"));
        int selectedRow = randInt(1, rows.size() - 1);
        rows.get(selectedRow).findElement(By.tagName("a")).click();

        UUID nameID = UUID.randomUUID();

        session.driver.findElement(By.id("name")).sendKeys(nameID.toString());
        session.driver.findElement(By.id("end_datepicker")).sendKeys(Keys.chord(Keys.CONTROL, "A"), "01/01/2010");
        session.driver.findElement(By.name("save_button")).click();
        String alert_text = session.driver.switchTo().alert().getText();
        session.driver.switchTo().alert().accept();
        System.out.print(alert_text);
        Assert.assertEquals(alert_text, "Error: the start day cannot be after the end day.");

    }

    /*
    The date for the room booking system is from jan 1 2010 - Dec 31 2020
     */
    public String generateRandomDate(){
        GregorianCalendar gc = new GregorianCalendar();

        int year = randInt(2010, 2020);
        gc.set(Calendar.YEAR, year);
        int dayOfYear = randInt(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));
        gc.set(Calendar.DAY_OF_YEAR, dayOfYear);
        String date =  (gc.get(Calendar.MONTH)+1) + "/" + gc.get(Calendar.DAY_OF_MONTH) + "/" + gc.get(Calendar.YEAR);
        System.out.print(date);
        return date;
    }

    public int randInt(int min, int max) {
        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        return rand.nextInt((max - min) + 1) + min;
    }

}
