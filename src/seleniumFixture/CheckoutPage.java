package seleniumFixture;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage extends LoadableComponent<CheckoutPage> {
	private final WebDriver driver;

	private final WebDriverWait wait;
	
	private WebElement content;
	
	@FindBy(className = "checkout_cart") private WebElement checkout_cart;
	
	@FindBy(className = "wpsc_checkout_forms") private WebElement checkout_forms;
	
	public CheckoutPage(WebDriver driver)
	{
		this.driver = driver;
		this.wait = new WebDriverWait(driver,10);
		PageFactory.initElements(driver, this);
	}
	
	@Override
	protected void load() {
		//driver.get("http://store.demoqa.com/products-page/checkout/");
	}
	
	@Override
	protected void isLoaded() throws Error
	{
		wait.until(ExpectedConditions.visibilityOf(content));
		assertTrue(driver.getTitle().contains("Checkout"));
		assertTrue(content.isDisplayed());
	}
	
	//step 1
	public Boolean isCheckoutCartDisplayed()
	{
		return checkout_cart.isDisplayed();
	}
	
	public int getQuantity(String itemName)
	{
		String quantity = checkout_cart.findElement(By.xpath(".//a[text() = '"+itemName+"']/ancestor::tr[contains(@class,'product_row')]//td[contains(@class,'wpsc_product_quantity')]//input[@name = 'quantity']")).getAttribute("value");
		return Integer.parseInt(quantity);
	}
	
	public void clickContinueButton()
	{
		content.findElement(By.xpath(".//span[text() = 'Continue']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".slide2[style*='block']:not([style*='opacity'])")));
		
	}
	
	//step 2
	public Boolean isCheckOutFormsDisplayed()
	{
		return checkout_forms.isDisplayed();
	}
	
	//
	public void enterValueToTextBox(String textBoxTitle, String value)
	{
		enterValueToTextBox(textBoxTitle, value, null);
	}
	
	public void enterValueToTextBox(String textBoxTitle, String value, String formName)
	{
		textBoxTitle = textBoxTitle.replaceAll("\\s", "").toLowerCase();
		WebElement textBox;
		if(formName.toLowerCase().contains("signin"))
		{
			return;
			//TODO implement sign in textboxes
		}else if(formName.toLowerCase().contains("billing"))
		{
			textBox = checkout_forms.findElement(By.xpath(".//*[@title = 'billing"+textBoxTitle+"']"));
		}else if(formName.toLowerCase().contains("shipping"))
		{
			textBox = checkout_forms.findElement(By.xpath(".//*[@title = 'shipping"+textBoxTitle+"']"));
		}else
		{
			return;
			//TODO throw exception
		}
		textBox.sendKeys(value);
	}
	
	public void selectDropDownText(String textBoxTitle, String value)
	{
		selectDropDownText(textBoxTitle, value, null);
	}
	
	public void selectDropDownText(String dropDownTitle, String value, String formName)
	{
		dropDownTitle = dropDownTitle.replaceAll("\\s", "").toLowerCase();
		Select dropDown;
		if(formName.toLowerCase().contains("billing"))
		{
			dropDown = new Select(content.findElement(By.xpath(".//select[@title = 'billing"+dropDownTitle+"']")));
		}else if(formName.toLowerCase().contains("shipping"))
		{
			dropDown = new Select(content.findElement(By.xpath(".//select[@title = 'shipping"+dropDownTitle+"']")));
		}else if(formName.toLowerCase().contains("changecountry"))
		{
			return;
			//TODO implement shipping calculate dropdowns
		}else
		{
			return;
			//TODO throw exception
		}
		dropDown.selectByVisibleText(value);
	}
	
	public void clickPurchaseButton()
	{
		content.findElement(By.className("wpsc_buy_button")).click();
	}
	
	
}
