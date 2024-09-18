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

public class OrderPage {
    private final WebDriver driver;
    // локатор для поля Имя
    private final By name = By.xpath(".//input[@placeholder = '* Имя']");
    // локатор для поля Фамилия
    private final By surname = By.xpath(".//input[@placeholder = '* Фамилия']");
    //локатор для поля Адрес
    private final By address = By.xpath(".//input[@class='Input_Input__1iN_Z Input_Responsible__1jDKN']");
    //локатор для поля Станция метро
    private final By metroStation = By.className("select-search__input");
    //локатор для поля Телефон
    private final By telephone = By.xpath(".//input[@placeholder = '* Телефон: на него позвонит курьер']");
    //локатор для кнопки Далее
    private final By thenButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    // метод ожидания загрузки страницы (если загрузилась кнопка Далее, то большая часть страницы также должна прогрузиться)
    public void waitForLoadPage() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[@class='Order_Header__BZXOb']")));
    }

    // метод вводит значение в поле Имя
    public void setName(String newName) {
        Assert.assertTrue("Поле для ввода имени не доступно", driver.findElement(name).isEnabled());
        driver.findElement(name).sendKeys(newName);
    }

    //метод вводит значение в поле Фамилия
    public void setSurname(String newSurname) {
        Assert.assertTrue("Поле для ввода фамилии не доступно", driver.findElement(surname).isEnabled());
        driver.findElement(surname).sendKeys(newSurname);
    }

    //метод вводит значение в поле Адрес
    public void setAddress(String newAddress) {
        Assert.assertTrue("Поле для ввода адреса не доступно", driver.findElement(address).isEnabled());
        driver.findElement(address).sendKeys(newAddress);
    }

    //метод выбора станции метро
    public void setMetroStation(int metroStationItem) {
        Assert.assertTrue("Поле для выбора станции метро не доступно", driver.findElement(metroStation).isEnabled());
        driver.findElement(metroStation).click();
        List<WebElement> list = driver.findElements(By.className("Order_Text__2broi"));
        if ((metroStationItem > 0) && (metroStationItem <= list.size())) {
            //прокручиваем страницу до нужного элемента списка
            WebElement item = list.get(metroStationItem - 1);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", item);
            item.click();
        } else {
            list.get(1).click();
        }
    }

    //метод вводит значение в поле Телефон
    public void setTelephone(String newTelephone) {
        Assert.assertTrue("Поле для ввода телефона не доступно", driver.findElement(telephone).isEnabled());
        driver.findElement(telephone).sendKeys(newTelephone);
    }

    //метод кликает на кнопку Далее
    public void clickThenButton() {
        Assert.assertTrue("Кнопка Далее не доступна", driver.findElement(thenButton).isEnabled());
        //скролл не помогает и сообщение о куки закрывает кнопку Далее, поэтому нажмем на кнопку "да, все привыкли", чтобы скрыть это сообщение
        driver.findElement(By.id("rcc-confirm-button")).click();
        driver.findElement(thenButton).click();
    }

    public void makeOrder(String newName, String newSurname, String newAddress, int metroStationItem, String newTelephone) {
        this.setName(newName);
        this.setSurname(newSurname);
        this.setAddress(newAddress);
        this.setMetroStation(metroStationItem);
        this.setTelephone(newTelephone);
    }
}