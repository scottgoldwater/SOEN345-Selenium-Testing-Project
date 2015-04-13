package TestMeetingRoomBookingSystem.Rules;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class LogInRule implements TestRule{
    private final String URL;
    private String USERNAME;
    private String PASSWORD;
    public WebDriver driver;

    public LogInRule(String url, String userName, String password){
        URL = url;
        USERNAME = userName;
        PASSWORD = password;

    }


    public Statement apply(final Statement statement, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                driver = new FirefoxDriver();
                driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);
                login();
                try {
                    statement.evaluate();
                } finally {
                    driver.close();
                }
            }
        };
    }

    public void setCredentials(String username, String password){
        USERNAME = username;
        PASSWORD = password;
    }

    public void login(){
        driver.navigate().to(URL);
        driver.findElement(By.id("NewUserName")).sendKeys(USERNAME);
        driver.findElement(By.id("NewUserPassword")).sendKeys(PASSWORD);
        driver.findElement(By.id("NewUserPassword")).sendKeys(Keys.ENTER);
    }

    public void logout(){
        driver.findElement(By.xpath("/html/body/div[1]/table/tbody/tr/td[7]/div/form/div/input[5]")).click();
    }
}
