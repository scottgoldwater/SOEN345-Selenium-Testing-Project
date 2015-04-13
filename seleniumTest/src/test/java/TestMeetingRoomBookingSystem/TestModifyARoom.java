package TestMeetingRoomBookingSystem;

import org.junit.*;
import java.util.List;
import static org.junit.Assert.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by User on 4/12/2015.
 */
public class TestModifyARoom {

    private WebDriver driver;
    private final String URL = "http://donatepls.com/mrbs-1.4.11/web/admin.php";
    private final String USERNAME = "admin";
    private final String PASSWORD = "admin";
    private final String ROOMNAME = "A Room To Modify";
    private final String DESCRIPTION = "This room will be modified";
    private final String CAPACITY = "5";
    private final String MOD_ROOMNAME = "A Modified Room";
    private final String MOD_DESCRIPTION = "The room was modified";
    private final String MOD_CAPACITY = "6";
    private final String MOD_EMAIL = "mod@hotmail.com";
    private final String DUPLICATE_ROOM_NAME_ERROR = "This room name has already been used in the area!";

    @Before
    public void setUp() throws Exception {
        // Log in as an Admin
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.navigate().to(URL);
        driver.findElement(By.id("NewUserName")).sendKeys(USERNAME);
        driver.findElement(By.id("NewUserPassword")).sendKeys(PASSWORD);
        driver.findElement(By.id("NewUserPassword")).sendKeys(Keys.ENTER);

        // Add the room to modify
        driver.findElement(By.linkText("Rooms")).click();
        createRoom(ROOMNAME);
    }

    @After
    public void tearDown(){
        // Delete second row where the added room is placed
        deleteRoom(1);
    }
    
    @Test
    public void standardFlowTest(){
        // Keep track of the number of enabled rooms before the modification
        int numOfCheckmarksBefore = driver.findElements(By.xpath("//img[@alt='check mark']")).size();

        driver.findElement(By.xpath("//a[@title='" + ROOMNAME + "']")).click();

        // Modify The room
        driver.findElement(By.id("room_name")).clear();
        driver.findElement(By.id("room_name")).sendKeys(MOD_ROOMNAME);
        driver.findElements(By.name("room_disabled")).get(1).click(); // Click second radio button option
        driver.findElement(By.id("description")).clear();
        driver.findElement(By.id("description")).sendKeys(MOD_DESCRIPTION);
        driver.findElement(By.id("capacity")).clear();
        driver.findElement(By.id("capacity")).sendKeys(MOD_CAPACITY);
        driver.findElement(By.id("room_admin_email")).clear();
        driver.findElement(By.id("room_admin_email")).sendKeys(MOD_EMAIL);
        driver.findElement(By.xpath("//input[@name='change_room']")).click();

        String act_mod_name = driver.findElement(By.xpath("//a[@title='" + MOD_ROOMNAME + "']")).getText();
        int numOfCheckmarksAfter = driver.findElements(By.xpath("//img[@alt='check mark']")).size();
        String act_mod_description = driver.findElement(By.xpath("//table[@id='rooms_table']/tbody/tr[2]/td[2]/div")).getText();
        String act_mod_capacity = driver.findElement(By.xpath("//table[@id='rooms_table']/tbody/tr[2]/td[3]/div")).getText();
        String act_mod_email = driver.findElement(By.xpath("//table[@id='rooms_table']/tbody/tr[2]/td[4]/div")).getText();

        assertEquals(MOD_ROOMNAME, act_mod_name);
        assertEquals(1, numOfCheckmarksBefore - numOfCheckmarksAfter); //should be one less enabled checkmark
        assertEquals(MOD_DESCRIPTION, act_mod_description);
        assertEquals(MOD_CAPACITY, act_mod_capacity);
        assertEquals(MOD_EMAIL, act_mod_email);
    }

    @Test
    public void testDuplicateName(){
        // Create an extra room with the name that the room to modify's name tries to change to.
        createRoom(MOD_ROOMNAME);
        driver.findElement(By.xpath("//a[@title='" + ROOMNAME + "']")).click();

        // Modify The room
        driver.findElement(By.id("room_name")).clear();
        driver.findElement(By.id("room_name")).sendKeys(MOD_ROOMNAME);
        driver.findElements(By.name("room_disabled")).get(1).click(); // Click second radio button option
        driver.findElement(By.id("description")).clear();
        driver.findElement(By.id("description")).sendKeys(MOD_DESCRIPTION);
        driver.findElement(By.id("capacity")).clear();
        driver.findElement(By.id("capacity")).sendKeys(MOD_CAPACITY);
        driver.findElement(By.id("room_admin_email")).clear();
        driver.findElement(By.id("room_admin_email")).sendKeys(MOD_EMAIL);
        driver.findElement(By.xpath("//input[@name='change_room']")).click();
        WebElement errorSpan = driver.findElement(By.xpath("//form[@id='edit_room']/fieldset/fieldset[1]/span"));
        assertEquals(DUPLICATE_ROOM_NAME_ERROR, errorSpan.getText());
        driver.findElement(By.linkText("Rooms")).click();
        deleteRoom(2);
    }

    private void createRoom(String roomName){
        driver.findElement(By.id("room_name")).clear();
        driver.findElement(By.id("room_name")).sendKeys(roomName);
        driver.findElement(By.id("room_description")).clear();
        driver.findElement(By.id("room_description")).sendKeys(DESCRIPTION);
        driver.findElement(By.id("room_capacity")).clear();
        driver.findElement(By.id("room_capacity")).sendKeys(CAPACITY);
        driver.findElement(By.xpath("//input[@value='Add Room']")).click();
    }

    private void deleteRoom(int row){
        // Delete second row where the added room is placed
        driver.findElements(By.xpath("//img[@alt='Delete']")).get(row).click();
        driver.findElement(By.id("del_yes")).click();
    }

}
