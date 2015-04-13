package TestMeetingRoomBookingSystem;


import TestMeetingRoomBookingSystem.Rules.LogInRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;

public class TestLoginRule {
    @Rule public LogInRule login = new LogInRule("http://donatepls.com/mrbs-1.4.11/web/admin.php","rob","123");


    @Test
    public void sanityTest(){
        Assert.assertEquals(login.driver.findElement(By.xpath("/html/body/div[1]/table/tbody/tr/td[7]/div/a[1]")).getText(),"You are rob");
        Assert.assertEquals(login.driver.findElement(By.id("logo")).getText(), "Your Company");
        login.logout();
        Assert.assertEquals(login.driver.findElement(By.xpath("/html/body/div[1]/table/tbody/tr/td[7]/div/a[1]")).getText(),"Unknown user");
    }
}
