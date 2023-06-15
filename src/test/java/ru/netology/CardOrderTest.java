package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardOrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUP() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void happyPathTest1() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Долгов Давид");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79998887766");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        assertEquals(expected, actual);

    }

    @Test
    void negativePathTestForInputFieldName() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Dolgov David");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79998887766");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText().trim();
        assertEquals(expected, actual);

    }

    @Test
    void negativePathTestForInputFieldPhone() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Долгов Давид");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7999888776611111111");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText().trim();
        assertEquals(expected, actual);

    }

    @Test
    void negativePathTestForCheckbox() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Долгов Давид");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79998887766");
        driver.findElement(By.tagName("button")).click();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        String actual = driver.findElement(By.cssSelector("[data-test-id=agreement] .checkbox__text")).getText().trim();
        assertEquals(expected, actual);

    }

}
