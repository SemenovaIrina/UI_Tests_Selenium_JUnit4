import edu.praktikum.sprint4.pages.HomePage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class QuestionsAboutImportantTests extends InfoForTests {
    static final String[] QUESTIONS = new String[]{
            "Сколько это стоит? И как оплатить?",
            "Хочу сразу несколько самокатов! Так можно?",
            "Как рассчитывается время аренды?",
            "Можно ли заказать самокат прямо на сегодня?",
            "Можно ли продлить заказ или вернуть самокат раньше?",
            "Вы привозите зарядку вместе с самокатом?",
            "Можно ли отменить заказ?",
            "Я жизу за МКАДом, привезёте?"
    };
    static final String[] ANSWERS = new String[]{
            "Сутки — 400 рублей. Оплата курьеру — наличными или картой.",
            "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.",
            "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.",
            "Только начиная с завтрашнего дня. Но скоро станем расторопнее.",
            "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.",
            "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.",
            "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.",
            "Да, обязательно. Всем самокатов! И Москве, и Московской области."
    };

    private final String itemQuestionsHeadingText;
    private final String itemAnswerText;

    public QuestionsAboutImportantTests(String itemQuestionsHeadingText, String itemAnswerText) {
        this.itemQuestionsHeadingText = itemQuestionsHeadingText;
        this.itemAnswerText = itemAnswerText;
    }

    // Тестовые данные
    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {QUESTIONS[0], ANSWERS[0]},
                {QUESTIONS[1], ANSWERS[1]},
                {QUESTIONS[2], ANSWERS[2]},
                {QUESTIONS[3], ANSWERS[3]},
                {QUESTIONS[4], ANSWERS[4]},
                {QUESTIONS[5], ANSWERS[5]},
                {QUESTIONS[6], ANSWERS[6]},
                {QUESTIONS[7], ANSWERS[7]},
        };
    }

    @Test
    public void checkAnswerForQuestionTest() {
        HomePage page = new HomePage(driver);
        //ждем когда прогрузятся элементы
        page.waitForLoadQuestions();
        //выбираем элемент выпадающего списка для проверки
        page.setItemQuestionsAndAnswer(itemQuestionsHeadingText);
        //если элемент списка с заданным текстом доступен, кликаем по нему
        page.clickItemQuestionsHeading();
        //проверяем соответствие отображаемого ответа ожидаемому
        Assert.assertEquals("Текст ответа на вопрос не совпадает с ожидаемым", itemAnswerText, page.getItemAnswerText());

    }
}
