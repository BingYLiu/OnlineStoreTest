package onlineStoreAutomation;

import org.testng.annotations.Test;

import seleniumFixture.*;

import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
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
	public void beforeSuiteSetup(ITestContext context) {
		System.out.println("Configure Suite");
		String os = System.getProperty("os.name").toLowerCase();
		if (os.indexOf("win") >= 0) {
			System.setProperty("webdriver.gecko.driver", "Tools\\geckodriver\\geckodriver.exe");
		}else if(os.indexOf("mac") >= 0)
		{
			System.setProperty("webdriver.gecko.driver", "Tools/geckodriver/geckodriver");
		}
		this.driver = new FirefoxDriver();
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
		assertTrue(cp.getQuantity("Magic Mouse") == 2, "Item quantity is incorrect in Checkout");

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
