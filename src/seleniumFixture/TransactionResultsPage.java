package seleniumFixture;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TransactionResultsPage  extends LoadableComponent<TransactionResultsPage> {
	private final WebDriver driver;
	private final WebDriverWait wait;
	private WebElement content;
	
	@FindBy (className = "wpsc-purchase-log-transaction-results") private WebElement transactionLog;
	
	enum Column
	{
		Name,Price,Quantity,ItemTotal
	}
	
	public TransactionResultsPage(WebDriver driver)
	{
		this.driver = driver;
		this.wait = new WebDriverWait(driver,10);
		PageFactory.initElements(driver, this);
	}
	
	@Override
	protected void load() {
	}
	
	@Override
	protected void isLoaded() throws Error
	{
		wait.until(ExpectedConditions.visibilityOf(content));
		assertTrue(driver.getTitle().contains("Transaction Results"));
	}
	
	public String getColumnValueForItem(String columnName, String itemName)
	{
		return transactionLog.findElements(By.xpath(".//tbody//td[text()='"+itemName+"']/../td")).get(Column.valueOf(columnName).ordinal()).getText();
	}

}
