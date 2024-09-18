package edu.praktikum.sprint4.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AboutRentPage {
    private final WebDriver driver;
    //локатор для поля Дата
    private final By date = By.xpath(".//div[@class='react-datepicker__input-container']");
    //локатор для поля Срок аренды
    private final By period = By.className("Dropdown-control");
    //локатор для цвета Черный жемчуг
    private final By colorBlack = By.id("black");
    //локатор для цвета Серая безысходность
    private final By colorGrey = By.id("grey");
    // локатор для поля Комментарий для курьера
    private final By comment = By.xpath(".//input[@placeholder = 'Комментарий для курьера']");
    //локатор для кнопки Заказать
    private final By orderButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Заказать']");
    //локатор для всплывающего окна Хотите оформить заказ
    private final By modalWantRegister = By.className("Order_ModalHeader__3FDaJ");
    //локатор для кнопки Да
    private final By yesButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Да']");
    //локатор для всплывающего окна Заказ оформлен (если видна кнопка, то видно и само окно)
    private final By modalOrderPlaced = By.xpath(".//div[@class='Order_NextButton__1_rCA']");

    public AboutRentPage(WebDriver driver) {
        this.driver = driver;
    }

    // метод ожидания загрузки страницы (если загрузилась кнопка Заказать, то большая часть страницы также должна прогрузиться)
    public void waitForLoadPage() {
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(orderButton));
    }

    //получение по названию месяца его номера
    public int numberMonth(String month) {
        int curMonth = 1;
        switch (month) {
            case ("январь"):
                curMonth = 1;
                break;
            case ("февраль"):
                curMonth = 2;
                break;
            case ("март"):
                curMonth = 3;
                break;
            case ("апрель"):
                curMonth = 4;
                break;
            case ("май"):
                curMonth = 5;
                break;
            case ("июнь"):
                curMonth = 6;
                break;
            case ("июль"):
                curMonth = 7;
                break;
            case ("август"):
                curMonth = 8;
                break;
            case ("сентябрь"):
                curMonth = 9;
                break;
            case ("октябрь"):
                curMonth = 10;
                break;
            case ("ноябрь"):
                curMonth = 11;
                break;
            case ("декабрь"):
                curMonth = 12;
                break;
        }
        return curMonth;
    }

    // отталкиваемся от того, что доставка не может быть раньше текущей даты, так как рассматриваем позитивные тест-кейсы
    public int findDifferenceMonth(int year, int month) {
        String currentMonthYear = driver.findElement(By.xpath(".//div[@class='react-datepicker__current-month']")).getText();
        int currentYear = Integer.parseInt(currentMonthYear.substring(currentMonthYear.length() - 4));
        int currentMonth = numberMonth(currentMonthYear.substring(0, currentMonthYear.length() - 5));
        int differenceMonth;
        if ((year - currentYear) == 0) {
            differenceMonth = month - currentMonth;
        } else {
            differenceMonth = 12 - currentMonth + month;
        }
        return differenceMonth;
    }

    // метод выбора даты доставки
    public void setDate(String newDate) {
        Assert.assertTrue("Поле выбора даты не доступно", driver.findElement(date).isEnabled());
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(date));
        driver.findElement(date).click();
        //перелистаем календарь на нужный месяц
        int newMonth = Integer.parseInt(newDate.substring(3, 5));
        int newYear = Integer.parseInt(newDate.substring(newDate.length() - 4));
        //разница между текущим и нужным месяцем (в месяцах)
        int delta = findDifferenceMonth(newYear, newMonth);
        WebElement buttonNextMonth = driver.findElement(By.xpath(".//button[@aria-label='Next Month']"));
        for (int i = 0; i < delta; i++) {
            buttonNextMonth.click();
        }
        String d = String.format("react-datepicker__day--0%1$s",newDate.substring(0, 2));
        String param = String.format(".//div[contains(@class,'%1$s') and not(contains(@class,'react-datepicker__day--outside-month'))]",d);
        WebElement day = driver.findElement(By.xpath(param));
        day.click();
    }

    //метод выбора периода аренды
    public void setPeriod(int periodItem) {
        Assert.assertTrue("Поле для выбора периода аренды не доступно", driver.findElement(period).isEnabled());
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(period));
        driver.findElement(period).click();
        List<WebElement> list = driver.findElements(By.xpath(".//div[@class='Dropdown-option']"));
        if ((periodItem > 0) && (periodItem < list.size())) {
            //прокручиваем страницу до нужного элемента списка
            WebElement item = list.get(periodItem - 1);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", item);
            item.click();
        } else {
            list.get(0).click();
        }
    }

    //метод выбора цвета самоката
    public void setColor(String color) {
        Assert.assertTrue("Поле для выбора цвета не доступно", driver.findElement(colorBlack).isEnabled());
        Assert.assertTrue("Поле для выбора цвета не доступно", driver.findElement(colorGrey).isEnabled());
        if ("black".equals(color)) {
            driver.findElement(colorBlack).click();
        } else if (("grey".equals(color))) {
            driver.findElement(colorGrey).click();
        }
    }

    //метод вводит значение в поле Комментарий
    public void setComment(String newComment) {
        Assert.assertTrue("Поле для ввода комментария не доступно", driver.findElement(comment).isEnabled());
        driver.findElement(comment).sendKeys(newComment);
    }

    //метод кликает на кнопку Заказать
    public void clickOrderButton() {
        Assert.assertTrue("Кнопка Заказать не доступна", driver.findElement(orderButton).isEnabled());
        driver.findElement(orderButton).click();
    }

    public void finishOrder(String newDate, int periodItem, String color, String newComment) {
        this.setDate(newDate);
        this.setPeriod(periodItem);
        this.setColor(color);
        this.setComment(newComment);
    }

    //"Хотите оформить заказ?" метод кликает на кнопку Да
    public void clickYesButton() {
        Assert.assertTrue("Область 'Хотите оформить заказ' не доступна", driver.findElement(modalWantRegister).isEnabled());
        Assert.assertTrue("Кнопка Да не доступна", driver.findElement(yesButton).isEnabled());
        driver.findElement(yesButton).click();
    }

    //Проверка доступности всплывающего окна с сообщением об оформлении заказа
    public boolean checkIsVisibleModalOrderPlaced() {
        List<WebElement> list = driver.findElements(modalOrderPlaced);
        return !list.isEmpty();
    }
}



