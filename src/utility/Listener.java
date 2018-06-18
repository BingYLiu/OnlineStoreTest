package utility;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.*;

public class Listener implements ITestListener, ISuiteListener, IInvokedMethodListener {

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ISuite suite) {
		// TODO Auto-generated method stub
		//setup firefox profile
	    FirefoxBinary firefoxBinary = new FirefoxBinary();
	    //firefoxBinary.addCommandLineOptions("--headless");

		//setup Logs
		String outputFolder = "test-output"+File.separator;
		String folderPath = suite.getName()+File.separator+"logs"+File.separator;
		File ffLog = new File(outputFolder+folderPath+"firefoxLog.txt");
		ffLog.getParentFile().mkdirs();
		try {
			ffLog.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File selLog = new File(outputFolder+folderPath+"seleniumLog.txt");
		try {
			selLog.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		
		
		suite.setAttribute("FirefoxOption", firefoxOption);
		
	}

	@Override
	public void onFinish(ISuite suite) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String outputFolder = "test-output"+File.separator;
		File ssName;
		WebDriver driver = (WebDriver)result.getTestContext().getAttribute("WebDriver");
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String folderPath = result.getTestContext().getSuite().getName()+File.separator+"screenshots"+File.separator;
		ssName = new File(outputFolder+folderPath+srcFile.getName());

		try {
			ssName.getParentFile().mkdirs();
			ssName.createNewFile();
			FileUtils.copyFile(srcFile, ssName);
		}catch (Exception e)
		{
			Reporter.log("Unable to take screenshot due to "+ e.getMessage()+"; File Path: "+ ssName.getAbsolutePath());
			return;
		}
		String htmlPath = "http://localhost:8080/job/Build%20and%20Test%20Demo/ws/test-output/OnlineStoreTestSuite/screenshots/";//hacks for screenshot for now
		String path = "<img src=\""+htmlPath+ssName.getName()+"\" alt=\""+result.getMethod().getMethodName()+"\">";
		Reporter.log(result.getThrowable().getMessage().toString());
		Reporter.log(path);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	
	
}
