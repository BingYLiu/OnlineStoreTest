package SeleniumFixture;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage  extends LoadableComponent<ProductPage>{

	private final WebDriver driver;
	private final WebDriverWait wait;
	private WebElement content;
	
	public ProductPage(WebDriver driver)
	{
		this.driver = driver;
		this.wait = new WebDriverWait(driver,10);
		PageFactory.initElements(driver, this);
	}
	
	@Override
	protected void load() {
		//driver.get("http://store.demoqa.com/products-page/product-category/");
	}
	
	@Override
	protected void isLoaded() throws Error
	{
		wait.until(ExpectedConditions.visibilityOf(content));
		assertTrue(content.isDisplayed());
	}
	
	public String getProductCategory()
	{
		return content.findElement(By.cssSelector(".entry-title")).getText();
	}
	
	public void clickAddToCart(String itemName)
	{
		WebElement itemBody = driver.findElement(By.xpath(".//text()[.= '"+itemName+"']/ancestor::div[@class = 'productcol']"));
		itemBody.findElement(By.className("wpsc_buy_button")).click();
		
		//TODO refactor inefficient
		wait.until(ExpectedConditions.visibilityOf(itemBody.findElement(By.className("addtocart"))));
		wait.until(ExpectedConditions.invisibilityOf(itemBody.findElement(By.className("addtocart"))));
	}
	

}
