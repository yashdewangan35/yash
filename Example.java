package frame_work;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Example 
{
	public static void main(String[] args)
	{
		// create a html file for results with appending
		
		ExtentReports er=new ExtentReports("googlesitetestresults.html",false);
		ExtentTest et=er.startTest("Google tittle test");
		try
		{
			//launch site
			System.setProperty("webdriver.chrome.driver","E:\\batch237\\chromedriver\\chromedriver_win32\\chromedriver.exe");
			WebDriver driver=new ChromeDriver();
			driver.get("https://www.google.co.in/");
			WebDriverWait w=new WebDriverWait(driver,100);
			w.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
			driver.manage().window().maximize();
			
			//get title of site
			
			String t=driver.getTitle();
			
			//validation
			if(t.equals("Google"))
			{
				et.log(LogStatus.PASS,"Tittle test passed");
			}
			else
			{
				et.log(LogStatus.FAIL,"Tittle test failed");
				File src=((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				File dest=new File("ss.png");
				FileUtils.copyFile(src, dest);
				et.log(LogStatus.FAIL,et.addScreenCapture("ss.png"));
			}
			// close site
			driver.close();
		    }
			catch(Exception e)
			{
				et.log(LogStatus.ERROR,e.getMessage());
		    }
		
		// save and close results
		   er.endTest(et);
		   er.flush();
	
	}

}
