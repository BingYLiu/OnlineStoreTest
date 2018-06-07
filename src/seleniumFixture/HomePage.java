package seleniumFixture;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

public class HomePage extends LoadableComponent<HomePage> {
	private final WebDriver driver;
	
	public HomePage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@Override
	protected void load() {
		//driver.get("http://store.demoqa.com/");
	}
	
	@Override
	protected void isLoaded() throws Error
	{
		assertTrue(driver.getTitle().contains("ONLINE STORE"));
	}
}
