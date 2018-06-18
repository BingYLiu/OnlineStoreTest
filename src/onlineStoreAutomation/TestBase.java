package onlineStoreAutomation;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestContext;
import org.testng.annotations.*;


@Listeners(utility.Listener.class)

public class TestBase {
	private WebDriver driver;
	
	@BeforeTest
	public void beforeTest(ITestContext context) {
		FirefoxOptions ffOptions = (FirefoxOptions)context.getSuite().getAttribute("FirefoxOption");
		this.driver = new FirefoxDriver(ffOptions);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		context.setAttribute("WebDriver", driver);
		driver.get("http://store.demoqa.com/");
	}


	@AfterTest
	public void afterTest() {
		this.driver.quit();
	}
	
	public WebDriver getWebDriver()
	{
		return driver;
	}

}
