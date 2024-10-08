import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public abstract class InfoForTests {
    private static final String URL = "https://qa-scooter.praktikum-services.ru/";

    WebDriver driver;

    @Before
    public void prepare() {
        // Запускаем браузер, переходим на сайт
        driver = new ChromeDriver();
        driver.get(URL);
    }

    @After
    public void pageDown() {
        // Закрываем браузер
        driver.quit();
    }
}
