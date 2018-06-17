package onlineStoreAutomation;

import org.testng.annotations.Test;
import seleniumFixture.*;
import static org.testng.Assert.*;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.ITestContext;
import org.testng.annotations.*;

@Listeners(utility.Listener.class)

public class PurchaseMagicMouseTest {
	private WebDriver driver;

	@DataProvider(name = "DummyData")
	public static Object[][] userInfo() {
		return new Object[][] { { "Dummy.Deadbeef@Dummy.com", "DUMMY", "DEADBEEF", "4 Privet Drive", "Toronto",
				"Ontario", "Canada", "M3M 1C1", "3735928559" } };
	}

	@BeforeSuite
	public void beforeSuiteSetup(ITestContext context) throws Exception {
		//setup firefox profile
	    FirefoxBinary firefoxBinary = new FirefoxBinary();
	    //firefoxBinary.addCommandLineOptions("--headless");

		//setup Logs
		String outputFolder = "test-output"+File.separator;
		String folderPath = context.getSuite().getName()+File.separator+"logs"+File.separator;
		File ffLog = new File(outputFolder+folderPath+"firefoxLog.txt");
		ffLog.getParentFile().mkdirs();
		ffLog.createNewFile();
		File selLog = new File(outputFolder+folderPath+"seleniumLog.txt");
		selLog.createNewFile();
		System.setProperty("webdriver.log.file", selLog.getPath());
		System.setProperty("webdriver.firefox.logfile", ffLog.getPath());
		LoggingPreferences logs = new LoggingPreferences();
		logs.enable(LogType.DRIVER, Level.ALL);
		logs.enable(LogType.BROWSER, Level.ALL);
		
		FirefoxOptions firefoxOption = new FirefoxOptions();
		firefoxOption.setCapability(CapabilityType.LOGGING_PREFS, logs);
		firefoxOption.setBinary(firefoxBinary);
		
		//OS base geckodriver
		String os = System.getProperty("os.name").toLowerCase();
		if (os.indexOf("win") >= 0) {
			System.setProperty("webdriver.gecko.driver", "Tools\\geckodriver\\geckodriver.exe");
		}else if(os.indexOf("mac") >= 0)
		{
			System.setProperty("webdriver.gecko.driver", "Tools/geckodriver/geckodriver");
		}
		
		
		this.driver = new FirefoxDriver(firefoxOption);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		context.setAttribute("WebDriver", driver);
		
	}

	@BeforeTest
	public void beforeTest() {
		driver.get("http://store.demoqa.com/");
	}

	@AfterTest
	public void afterTest() {
		this.driver.quit();
	}

	@Test(description = "Varify I can Purchase a magic mouse with valid user infos", dataProvider = "DummyData")
	public void ContinueCheckout(String email, String firstName, String lastName, String address, String city,
			String province, String country, String postCode, String phone) {

		// Ensure Home Page is displayed
		new HomePage(driver).get();
		assertTrue(driver.getTitle().contains("ONLINE STORE"), "Unable to Load Home Page");

		// Ensure Header and Footer is displayed and I can Navigate to Accessories Page
		// Via Menu
		HeaderFooterPage hf = new HeaderFooterPage(driver).get();
		hf.selectDropdownMenuItem("Product Category", "Accessories");
		assertTrue(driver.getTitle().contains("Accessories"), "Unable to Navigate to Accessories");

		// Ensure I can add magic mouse to cart
		ProductPage ap = new ProductPage(driver).get();
		ap.clickAddToCart("Magic Mouse");
		assertTrue(hf.getCartText().contains("1 item"), "Item in cart is incorrect");

		// Ensure I can move to checkout page
		hf.clickCheckout();
		assertTrue(driver.getTitle().contains("Checkout"), "Unable to Navigate to Checkout");

		// Ensure Checkout Page is correct
		CheckoutPage cp = new CheckoutPage(driver).get();
		assertEquals(cp.getQuantity("Magic Mouse"),2,"Item quantity is incorrect in Checkout");


		// Ensure I can continue to billing info page
		cp.clickContinueButton();
		assertTrue(cp.isCheckOutFormsDisplayed(), "user form is not displayed");

		// Input user info to checkout form
		cp.enterValueToTextBox("email", email, "Billing");
		cp.enterValueToTextBox("firstName", firstName, "Billing");
		cp.enterValueToTextBox("lastName", lastName, "Billing");
		cp.enterValueToTextBox("address", address, "Billing");
		cp.enterValueToTextBox("city", city, "Billing");
		cp.enterValueToTextBox("postcode", postCode, "Billing");
		cp.enterValueToTextBox("phone", phone, "Billing");
		cp.selectDropDownText("country", country, "Billing");
		// cp.selectDropDownText("region", province,"Billing");

		// Ensure I can finish purchase with correct items
		cp.clickPurchaseButton();
		TransactionResultsPage trp = new TransactionResultsPage(driver);
		assertTrue(trp.getColumnValueForItem("Quantity", "Magic Mouse").equals("1"),
				"Transaction Result is not correct");
	}

}
