package SeleniumFixture;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HeaderFooterPage extends LoadableComponent<HeaderFooterPage> {

	private final WebDriver driver;
	private final WebDriverWait wait;
	
	private WebElement header;
	
	private WebElement footer;
	
	//Top Menu Bar
	@FindBy(id = "main-nav") private WebElement headerMenu;
	
	//checkout cart
	@FindBy(id = "header_cart") private WebElement checkOut;
	
	//BottomMenu
	@FindBy(id = "footer_nav") private WebElement footerMenu;
	
	public HeaderFooterPage(WebDriver driver)
	{
		this.driver = driver;
		this.wait = new WebDriverWait(driver,10);
		PageFactory.initElements(driver, this);
	}
	
	@Override
	protected void load() {
		//driver.get("http://store.demoqa.com/");
	}
	
	@Override
	protected void isLoaded() throws Error
	{

		wait.until(ExpectedConditions.visibilityOf(header));
		//Ensure header
		assertTrue(header.isDisplayed());
		
		wait.until(ExpectedConditions.visibilityOf(footer));
		//Ensure footer
		assertTrue(footer.isDisplayed());
	}
	
	public Boolean isHeaderDisplayed()
	{
		return header.isDisplayed();
	}
	
	public Boolean isFooterDisplayed()
	{
		return footer.isDisplayed();
	}
	
	public void selectDropdownMenuItem(String menuItem, String dropDownItem)
	{
		WebElement MenuLabel = headerMenu.findElement(By.xpath(".//text()[.= '"+menuItem+"']/.."));
		
		Actions act = new Actions(driver);
		act.moveToElement(MenuLabel).build().perform();
			
		WebElement DropDownLabel = headerMenu.findElement(By.xpath(".//ul[(@class = 'sub-menu') and not(contains(@style , 'display: none'))]//a[text()='"+dropDownItem+"']"));
		DropDownLabel.click();
	}
	
	public String getCartText()
	{
		return checkOut.getText();
	}
	
	public void clickCheckout()
	{
		checkOut.click();
	}
}
