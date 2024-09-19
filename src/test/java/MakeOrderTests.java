import edu.praktikum.sprint4.pages.AboutRentPage;
import edu.praktikum.sprint4.pages.HomePage;
import edu.praktikum.sprint4.pages.OrderPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@RunWith(Parameterized.class)
public class MakeOrderTests {
    private static final String URL = "https://qa-scooter.praktikum-services.ru/";

    private final String name;
    private final String surname;
    private final String address;
    private final int metroStationItem;
    private final String telephone;
    private final String date;
    private final int periodItem;
    private final String color;
    private final String comments;
    private WebDriver driver;

    public MakeOrderTests(String name, String surname, String address, int metroStationItem, String telephone, String date, int periodItem, String color, String comments) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metroStationItem = metroStationItem;
        this.telephone = telephone;
        this.date = date;
        this.periodItem = periodItem;
        this.color = color;
        this.comments = comments;
    }

    @Before
    public void prepare() {
        // Запускаем браузер, переходим на сайт
        driver = new ChromeDriver();
        driver.get(URL);
    }

    // Тестовые данные
    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        return new Object[][]{
                //обязательный формат даты доставки dd.mm.yyyy
                //парамтеры:
                //имя & фамилия & адрес & номер_станции_метро_в_списке & телефон & дата_доставки & номер_периода_в_списке & цвет & комментарий
                {"Иван", "Петров", "Самара, ул.Гагарина, 9", 1, "89271112233", "07.10.2024", 1, "black", ""},
                {"Ирина", "Семенова", "ул.Мира, 8889", 2, "89271114455", "20.12.2024", 2, "", "В первой половине дня"},
        };
    }

    @Test
    public void makeOrderPositiveTestTopButton() {
        HomePage page = new HomePage(driver);
        //ждем когда прогрузятся элементы
        page.waitForLoadQuestions();
        //клик по кнопке Заказать вверху страницы
        page.clickOrderButtonTop();
        //все действия, которые должны произойти после нажатия на кнопку для оформления заказа
        afterClickOrderButton();
    }

    @Test
    public void makeOrderPositiveTestButtomButton() {
        HomePage page = new HomePage(driver);
        //ждем когда прогрузятся элементы
        page.waitForLoadQuestions();
        //клик по кнопке Заказать внизу страницы
        page.clickOrderButtonBottom();
        //все действия, которые должны произойти после нажатия на кнопку для оформления заказа
        afterClickOrderButton();
    }

    public void afterClickOrderButton() {
        //1-й этап оформления заказа
        OrderPage orderPage = new OrderPage(driver);
        //ждем пока загрузится страница
        orderPage.waitForLoadPage();
        //заполяем поля формы
        orderPage.makeOrder(name, surname, address, metroStationItem, telephone);
        //нажимаем кнопку Далее
        orderPage.clickThenButton();
        //2-й этап оформления заказа
        AboutRentPage nextOrderPage = new AboutRentPage(driver);
        nextOrderPage.waitForLoadPage();
        //заполнение полей формы
        nextOrderPage.finishOrder(date, periodItem, color, comments);
        //нажатие на кнопку Заказать
        nextOrderPage.clickOrderButton();
        //подтверждаем оформление заказа нажатием кнопки Да
        nextOrderPage.clickYesButton();
        Assert.assertTrue("Окно не доступно", nextOrderPage.checkIsVisibleModalOrderPlaced());
    }

    @After
    public void pageDown() {
        // Закрываем браузер
        driver.quit();
    }
}
