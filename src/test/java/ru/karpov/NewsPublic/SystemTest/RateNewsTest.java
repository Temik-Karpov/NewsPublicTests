package ru.karpov.NewsPublic.SystemTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;


import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class RateNewsTest extends BaseTest {
    WebDriver driver;

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions ops = new ChromeOptions();
        ops.addArguments("--disable-dev-shm-usage");
        ops.addArguments("--headless");
        ops.addArguments("--disable-gpu");
        ops.addArguments("--no-sandbox");
        ops.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(ops);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));
        driver.manage().timeouts().setScriptTimeout(5000, TimeUnit.MILLISECONDS);
        driver.manage().timeouts().pageLoadTimeout(10000, TimeUnit.MILLISECONDS);
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    public void userCanLoginByUsername() throws InterruptedException {
        Utils.createNewNews(userRepo, newsRepo, "Test Sport", "Sport", "Test");
        driver.get("http://localhost:8081/");
        driver.manage().window().setSize(new Dimension(1900, 1020));
        driver.findElement(By.linkText("LogIn")).click();
        driver.findElement(By.id("username")).sendKeys("janedoe");
        driver.findElement(By.id("password")).sendKeys("s3cr3t");
        Thread.sleep(100);
        driver.findElement(By.id("kc-login")).click();
        Thread.sleep(100);
        driver.findElement(By.linkText("Show")).click();
        driver.findElement(By.id("mark")).click();

        WebElement dropdown = driver.findElement(By.id("mark"));
        dropdown.findElement(By.xpath("//option[. = '4']")).click();
        Assertions.assertEquals(0, markRepo.findAll().size());
        driver.findElement(By.cssSelector("button")).click();
        Assertions.assertEquals(1, markRepo.findAll().size());
        driver.findElement(By.cssSelector("h1.display-4"));
        driver.findElement(By.linkText("Show"));
    }
}
