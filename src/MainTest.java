import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class MainTest {
    private AndroidDriver<AndroidElement> driver;
    private final long defaultTimeoutInSeconds = 5;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "G:\\github\\MobileAppAutomator\\MobileAppAutomator-Ex02\\apks\\org.wikipedia.apk");

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void searchInputHasExpectedText() {
        final String searchXpath = "//*[@text = 'Search Wikipedia']";
        final String searchInputId = "org.wikipedia:id/search_src_text";
        final String errorMessage = "Search input element has not expected text:";
        final String expectedText = "Search…";

        // Тапаем по элементу поиска для открытия формы поиска
        waitForElementAndClick(By.xpath(searchXpath));

        // Проверяем текст в поле ввода поиска
        assertElementHasText(By.id(searchInputId), errorMessage, expectedText);
    }

    private void assertElementHasText(By by, String errorMessage, String expectedText) {
        WebElement element = waitForElementPresent(by);

        Assert.assertEquals(errorMessage, expectedText, element.getAttribute("text"));
    }

    private WebElement waitForElementAndClick(By by) {
        return waitForElementAndClick(by, defaultTimeoutInSeconds);
    }

    private WebElement waitForElementAndClick(By by, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementPresent(By by) {
        return waitForElementPresent(by, defaultTimeoutInSeconds);
    }

    private WebElement waitForElementPresent(By by, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage("Element \"" + by + "\" not found!\n");

        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }
}
