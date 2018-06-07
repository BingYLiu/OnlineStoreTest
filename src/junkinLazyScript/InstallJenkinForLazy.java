package junkinLazyScript;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

public class InstallJenkinForLazy {
	private WebDriver driver;
	private WebDriverWait wait;
	
	@DataProvider(name = "JenkinData")
	public static Object[][] userInfo() {
		return new Object[][] { { "http://localhost:8080", "141730f797f84037b78b3f1ef7a13deb","Admin","Admin","DemoAdmin","something@email.com","C:\\Program Files\\Java\\jdk1.8.0_92"} };
	}

	@Test(dataProvider = "JenkinData")
	public void installJenkins(String url,String initAdminPass, String userName, String password, String name, String email,String jdkPath) {
		//open url
		driver.get(url);
		driver.findElement(By.id("security-token")).sendKeys(initAdminPass);
		driver.findElement(By.cssSelector("[type ='submit']")).click();
		driver.findElement(By.xpath(".//b[text() = 'Install suggested plugins']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[contains(text() , 'Continue as admin')]")));
		driver.findElement(By.xpath(".//*[contains(text() , 'Continue as admin')]")).click();
		driver.findElement(By.xpath(".//button[contains(text() , 'Start using Jenkins')]")).click();
		/*
		driver.findElement(By.id("username")).sendKeys(userName);
		driver.findElement(By.xpath(".//*[@name = 'password1']")).sendKeys(password);
		driver.findElement(By.xpath(".//*[@name = 'password2']")).sendKeys(password);
		driver.findElement(By.xpath(".//*[@name = 'fullname']")).sendKeys(name);
		driver.findElement(By.xpath(".//*[@name = 'email']")).sendKeys(email);
		driver.findElement(By.xpath(".//button[contains(@text , 'Save and Finish')]")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//button[contains(text() , 'Start using Jenkins')]")));
		*/

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//a[contains(text() , 'Manage Jenkins')]")));
		if(driver.findElements(By.xpath(".//*[text()='DISABLE AUTO REFRESH']")).size()>0)
		{
			driver.findElement(By.xpath(".//*[text()='DISABLE AUTO REFRESH']")).click();
		}
		driver.findElement(By.xpath(".//a[contains(text() , 'Manage Jenkins')]")).click();
		driver.findElement(By.xpath(".//dt[contains(text() , 'Global Tool Configuration')]")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[contains(text() , 'JDK installations'")));
		driver.findElement(By.xpath(".//*[contains(text() , 'JDK installations')]")).click();
		
		driver.findElement(By.xpath(".//*[@checkurl='/descriptorByName/hudson.model.JDK/checkName']")).sendKeys("JDK");
		driver.findElement(By.xpath(".//*[@checkurl='/descriptorByName/hudson.model.JDK/checkName']/ancestor::table[1]//input[@name='hudson-tools-InstallSourceProperty']")).click();
		driver.findElement(By.xpath(".//*[@checkurl='/descriptorByName/hudson.model.JDK/checkHome']")).sendKeys(jdkPath);
		driver.findElement(By.xpath(".//*[contains(text() , 'Add Ant')]")).click();
		driver.findElement(By.xpath(".//*[@checkurl='/descriptorByName/hudson.tasks.Ant$AntInstallation/checkName']")).sendKeys("ANT");
		driver.findElement(By.xpath(".//button[contains(text() , 'Save')]")).click();
		
		driver.findElement(By.xpath(".//*[contains(text() , 'Manage Plugins')]")).click();
		driver.findElement(By.xpath(".//*[contains(text() , 'Available')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[contains(text() , '.NET')]")));
		driver.findElement(By.id("filter-box")).sendKeys("testNG");
		driver.findElement(By.xpath(".//*[contains(@name , 'TestNG Results')]//*[@type = 'checkbox']")).click();
		driver.findElement(By.xpath(".//*[contains(text() , 'Install without restart')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[contains(text() , 'TestNG Results')]/..//*[contains(text(), 'Success')]")));
		driver.findElement(By.xpath(".//*[contains(text() , 'Back to Dashboard')]")).click();
		
		driver.findElement(By.xpath(".//*[contains(text() , 'New Item')]")).click();
		driver.findElement(By.id("name")).sendKeys("Build and Test Demo");
		driver.findElement(By.xpath(".//*[contains(text() , 'Freestyle project')]")).click();
		driver.findElement(By.xpath(".//button[contains(text() , 'OK')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//label[contains(text() , 'Git')]")));
		driver.findElement(By.xpath(".//label[contains(text() , 'Git')]/input[@name = 'scm']")).click();
		driver.findElement(By.xpath(".//*[@checkurl='/job/Build%20and%20Test%20Demo/descriptorByName/hudson.plugins.git.UserRemoteConfig/checkUrl']")).sendKeys("https://github.com/BingYLiu/OnlineStoreTest.git");
		driver.findElement(By.xpath(".//*[contains(text() , 'Delete workspace before build starts')]")).click();
		driver.findElement(By.xpath(".//*[contains(text() , 'With Ant')]")).click();
		driver.findElement(By.xpath(".//*[contains(text() , 'Add build step')]")).click();
		driver.findElement(By.xpath(".//*[contains(text() , 'Invoke Ant')]")).click();
		
		driver.findElement(By.xpath(".//*[contains(text() , 'Add post-build action')]")).click();
		driver.findElement(By.xpath(".//*[contains(text() , 'Publish TestNG Results')]")).click();
		
		driver.findElement(By.xpath(".//*[contains(text() , 'Save')]")).click();
	}
	
	@BeforeTest
	public void beforeSuiteSetup() {
		System.out.println("Configure Suite");
		String os = System.getProperty("os.name").toLowerCase();
		if (os.indexOf("win") >= 0) {
			System.setProperty("webdriver.gecko.driver", "Tools\\geckodriver\\geckodriver.exe");
		}else if(os.indexOf("mac") >= 0)
		{
			System.setProperty("webdriver.gecko.driver", "Tools/geckodriver/geckodriver");
		}
		this.driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		this.wait = new WebDriverWait(driver,3000000);
	}
}
