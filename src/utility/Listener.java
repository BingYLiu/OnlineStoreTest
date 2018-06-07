package utility;

import java.io.File;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
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
		String timeStamp;
		File ssName;
		WebDriver driver = (WebDriver)result.getTestContext().getAttribute("WebDriver");
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// TODO Auto-generated method stub
		timeStamp = Calendar.getInstance().getTime().toString();
		ssName = new File("test-output\\Screenshots\\"+timeStamp+".pgn");
		try {
			FileUtils.copyFile(srcFile, ssName);
		}catch (Exception e)
		{
			Reporter.log("Unable to take screenshot due to "+ e.getMessage());
			return;
		}
		String filePath = ssName.toString();
		String path = "<img src=\""+filePath+"\" alt=\""+timeStamp+".pgn\">";
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
