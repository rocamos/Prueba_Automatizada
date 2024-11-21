package tasks;

import org.openqa.selenium.WebDriver;
import ui.LoginPage;

public class LoginTask {
    private WebDriver driver;

    public LoginTask(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String username, String password) {
        driver.findElement(LoginPage.USERNAME_INPUT).sendKeys(username);
        driver.findElement(LoginPage.PASSWORD_INPUT).sendKeys(password);
        driver.findElement(LoginPage.LOGIN_BUTTON).click();
    }
}


