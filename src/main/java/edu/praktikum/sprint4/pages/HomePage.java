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

public class HomePage {
    private final WebDriver driver;
    //локатор для кнопки Заказать вверху
    private final By orderButtonTop = By.className("Button_Button__ra12g");
    //локатор для кнопки Заказать внизу
    private final By orderButtonBottom = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");
    // локатор для элемента выпадающего списка
    private By itemQuestionsHeading;
    // локатор для текста, соответствующего элементу выпадающего списка
    private By itemAnswer;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    //сетер, выбирающий элемент выпадающего списка с заданным заголовком, а также текст ответа, соответствующий ему
    public void setItemQuestionsAndAnswer(String itemText) {
        String param = String.format(".//div[@class='accordion__button' and text() ='%1$s']", itemText);
        itemQuestionsHeading = By.xpath(param);
        List<WebElement> element = driver.findElements(itemQuestionsHeading);
        if (!element.isEmpty()) {
            String id = element.get(0).getAttribute("id");
            itemAnswer = By.id("accordion__panel-" + id.charAt(id.length() - 1));
        } else {
            itemAnswer = null;
        }
    }

    // метод ожидания загрузки элементов выпадающего списка
    public void waitForLoadQuestions() {
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(By.className("accordion__button")));
    }

    // метод кликает по элементу выпадающего списка с заданным текстом
    public void clickItemQuestionsHeading() {
        Assert.assertTrue("Элемент списка с заданным заголовком не найден на странице", driver.findElement(itemQuestionsHeading).isEnabled());
        //прокручиваем страницу до нужного элемента списка
        WebElement item = driver.findElement(itemQuestionsHeading);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", item);
        item.click();
    }

    // метод для получения текста ответа для элемента выпадающего списка (текст возвращается только если ответ отображается)
    public String getItemAnswerText() {
        Assert.assertTrue("Текст ответа не отображается на странице", driver.findElement(itemAnswer).isDisplayed());
        return driver.findElement(itemAnswer).getText();
    }

    //метод нажатия на кнопку Заказать вверху экрана
    public void clickOrderButtonTop() {
        Assert.assertTrue("Кнопка Заказать не доступна вверху страницы", driver.findElement(orderButtonTop).isEnabled());
        //прокручиваем страницу до нужного элемента списка
        WebElement item = driver.findElement(orderButtonTop);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", item);
        item.click();
    }

    //метод нажатия на кнопку Заказать внизу экрана
    public void clickOrderButtonBottom() {
        Assert.assertTrue("Кнопка Заказать не доступна внизу страницы", driver.findElement(orderButtonBottom).isEnabled());
        //прокручиваем страницу до нужного элемента списка
        WebElement item = driver.findElement(orderButtonBottom);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", item);
        item.click();
    }
}
