package stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class RecruitmentStepDefinitions {

    private static WebDriver driver; // Cambiado a static para reutilizar en todos los pasos
    private static WebDriverWait wait;

    @Before
    public void setUp() {
        if (driver == null) { // Solo inicializa si no está inicializado
            WebDriverManager.chromedriver().driverVersion("131.0.6778.85").setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            wait = new WebDriverWait(driver, Duration.ofSeconds(60)); // Timeout extendido
        }
    }

    @After
    public void tearDown() {
        // Comentado para evitar cerrar el navegador después de cada prueba
        // Si quieres cerrar al final de todo el proceso, agrega un paso específico para ello
    }

    @Given("I navigate to the login page")
    public void i_navigate_to_the_login_page() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
    }

    @When("I enter valid credentials")
    public void i_enter_valid_credentials() {
        driver.findElement(By.name("username")).sendKeys("Admin");
        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Redirigir inmediatamente a la página de Recruitment
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/recruitment/addCandidate");
        wait.until(ExpectedConditions.urlContains("/recruitment/addCandidate"));
    }

    @And("I navigate to the recruitment page")
    public void navigateToRecruitmentPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("firstName")));
    }

    @When("I add a new candidate")
    public void addNewCandidate() {
        // Ingresar los datos básicos del candidato
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("firstName"))).sendKeys("Oscar");
        driver.findElement(By.name("middleName")).sendKeys("Andres");
        driver.findElement(By.name("lastName")).sendKeys("Roa");

        // Seleccionar el puesto
        driver.findElement(By.xpath("//div[contains(text(),'-- Select --')]")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Payroll Administrator']"))).click();

        // Ingresar el correo electrónico
        driver.findElement(By.xpath("//input[@placeholder='Type here']")).sendKeys("oscarandres@gmail.com");

        // Ingresar el Contact Number
        WebElement contactNumberField = driver.findElement(By.xpath("//input[@placeholder='Type here' and @class='oxd-input oxd-input--active']"));
        contactNumberField.clear();
        contactNumberField.sendKeys("453465464347");

        // Adjuntar el archivo
        //String filePath = "D:/resume.pdf"; // Asegúrate de que la ruta del archivo sea correcta
       // WebElement fileInput = driver.findElement(By.xpath("//input[@type='file']"));
       // fileInput.sendKeys(filePath);

        // Hacer scroll hasta el botón Save
        WebElement saveButton = driver.findElement(By.xpath("//button[text()='Save']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", saveButton);

        // Presionar botón Save
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();

        // Validar que los datos fueron guardados
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Successfully Saved')]")));

        // Validar que aún se mantiene en la página de Recruitment
        assert driver.getCurrentUrl().contains("/recruitment/addCandidate") : "El flujo no se mantuvo en Recruitment";
    }

    @Then("I should validate the candidate is hired")
    public void validateCandidateHired() {
        // Validación específica si es requerida
    }

    @And("I close the browser")
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
            driver = null; // Reinicia para evitar problemas en futuras ejecuciones
        }
    }
}
