package utility;

import java.io.File;
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
		String path = "<img src=\""+folderPath+srcFile.getName()+"\" alt=\""+result.getMethod().getMethodName()+"\">";
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
