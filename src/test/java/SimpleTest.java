import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.*;

import static org.junit.Assert.*;

public class SimpleTest {

    private  WebDriver driver ;

    @BeforeClass public static void setupAll(){

        String geckoDriverPath = new  File("src/resources/geckodriver").getAbsolutePath();
        System.setProperty("webdriver.gecko.driver", geckoDriverPath);
    }

    @Before
    public  void beforeTest() {
        this.driver = new FirefoxDriver();
    }

    @Test
    public void testAllegro() {

        this.driver.get("http://allegro.pl");

        WebElement searchField = (new WebDriverWait(this.driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[id='main-search-text']")));


        WebElement searchButton = driver.findElement(By.cssSelector(".search-btn"));
        assertTrue(searchButton.isDisplayed());

        searchField.sendKeys("rower");
        searchButton.click();

        List<WebElement> items = (new WebDriverWait(this.driver, 10))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.cssSelector("span[class*='price-main__price'] > span")));

        int itemsNum = items.size();

        assertNotEquals(items, new ArrayList<WebElement>());

        System.out.println("found items: " + itemsNum);

        WebElement firstItem =  items.get(0);

        String priceText = firstItem.getText().toString().trim().replace(",", ".").replace("zł", "");
        Double price =  Double.parseDouble(priceText);

        System.out.println("first item price: " + price.toString());

        assertTrue( price <  10000);

    }
    @After public void afterTest() {
        this.driver.quit();
    }

    @AfterClass public static void afterClass(){

    }

}
