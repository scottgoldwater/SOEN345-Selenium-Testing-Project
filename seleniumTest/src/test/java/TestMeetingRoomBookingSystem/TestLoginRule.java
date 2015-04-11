package TestMeetingRoomBookingSystem;


import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;

public class TestLoginRule {
    @Rule public LogInRule login = new LogInRule();


    @Test
    public void sanityTest(){
        Assert.assertEquals(login.driver.findElement(By.id("logo")).getText(), "Your Company");
    }
}
