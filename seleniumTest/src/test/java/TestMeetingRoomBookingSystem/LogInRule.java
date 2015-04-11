package TestMeetingRoomBookingSystem;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class LogInRule implements TestRule{
    private final String URL = "http://donatepls.com/mrbs-1.4.11/web/admin.php";
    private final String USERNAME = "rob";
    private final String PASSWORD = "123";
    public WebDriver driver;


    public Statement apply(final Statement statement, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                driver = new FirefoxDriver();
                driver.navigate().to(URL);
                driver.findElement(By.id("NewUserName")).sendKeys(USERNAME);
                driver.findElement(By.id("NewUserPassword")).sendKeys(PASSWORD);
                driver.findElement(By.id("NewUserPassword")).sendKeys(Keys.ENTER);
                try {
                    statement.evaluate();
                } finally {
                    driver.close();
                }
            }
        };
    }
}
