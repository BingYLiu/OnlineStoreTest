package onlineStoreAutomation;

import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;

public class TestBase {
	private WebDriver driver;
	
	@BeforeMethod
	public void beforeMethod(ITestContext context) {
		FirefoxOptions ffOptions = (FirefoxOptions)context.getSuite().getAttribute("FirefoxOption");
		this.driver = new FirefoxDriver(ffOptions);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://store.demoqa.com/");
	}


	@AfterMethod
	public void afterMethod() {
		this.driver.quit();
	}
	
	public WebDriver getWebDriver()
	{
		return driver;
	}

}
