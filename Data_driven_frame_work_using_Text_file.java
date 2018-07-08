package frame_work;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;

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

public class Data_driven_frame_work_using_Text_file 
{
	public static void main(String[] args) throws Exception 
	{
		// open text file for data reading
		
		File f=new File ("gmailtestdata.txt");       // File=java.io
		FileReader fr=new FileReader(f);             // FileReader=java.io
		BufferedReader br=new BufferedReader(fr);    // BufferedReader=java.io
		
		// create results file(.html)
		
		ExtentReports er=new ExtentReports("gmail-login-ddt2.html",false);
		ExtentTest et=er.startTest("Gmail login test");
		
		// data driven test
		
		WebDriver driver=null;
		String l="";
		while((l=br.readLine()) != null)
		{
			try
			{
				// split into pieces
				String p[]=l.split(",");
				

				// run browser driver to get browser
		        System.setProperty("webdriver.chrome.driver","E:\\batch237\\chromedriver\\chromedriver_win32\\chromedriver.exe");
				
				// create object to acess that openend browser
				driver=new ChromeDriver();
				
				//launch site
				driver.manage().window().maximize();
				driver.get("http://www.gmail.com");	
				
				// to give time to browser to show page 
				WebDriverWait w=new WebDriverWait(driver,100);
				w.until(ExpectedConditions.visibilityOfElementLocated(By.name("identifier")));
				
				// Enter userid and performs validations
				driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
				driver.findElement(By.name("identifier")).sendKeys(p[0]);
				w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Next']")));
				driver.findElement(By.xpath("//*[text()='Next']")).click();
                
                // to give time to browser to activate pwd or next
				
				Thread.sleep(5000);
				if(p[1].equalsIgnoreCase("valid") && driver.findElement(By.name("password")).isDisplayed())
				{
					et.log(LogStatus.PASS,"Valid userid test passed");
					
					// enter passwor and perform validations
					driver.findElement(By.name("password")).sendKeys(p[2]);
					w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Next']")));
					driver.findElement(By.xpath("//*[text()='Next']")).click();
					Thread.sleep(5000);
					
					if(p[3].equalsIgnoreCase("valid") && driver.findElement(By.xpath("//*[text()='COMPOSE']")).isDisplayed())
					{
						et.log(LogStatus.PASS,"valid user id & pwd test passed");
					}
					
					else if(p[3].equalsIgnoreCase("invalid") && driver.findElement(By.xpath("//*[text()='Enter your password'or contains(text(),'Wrong password')]")).isDisplayed())
					{
						et.log(LogStatus.PASS,"Valid mail id but Invalid pwd test passed");
					}
					
					else
					{
						File src=((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
						File dest=new File("ss3.png");
						FileUtils.copyFile(src,dest);
						et.log(LogStatus.FAIL,"pwd test failed"+ et.addScreenCapture("ss1.png"));
					}
				}
				
				else if(p[1].equalsIgnoreCase("invalid") && driver.findElement(By.xpath("(//*[@aria-live='assertive'])[1]|(//*[text()='Forgot email?'])")).isDisplayed())
				{
					et.log(LogStatus.PASS,"Invalid user id test passed");

				}
				
				else
				{
					File src=((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
					File dest=new File("ss3.png");
					FileUtils.copyFile(src,dest);
					et.log(LogStatus.FAIL,"User id test failed"+ et.addScreenCapture("ss2.png"));
				}
				
				// close site 
				driver.close();
			  }
			  
			  catch(Exception e)
			  {
				  File src=((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			      File dest=new File("ss3.png");
			      FileUtils.copyFile(src,dest);
				  et.log(LogStatus.ERROR,e.getMessage()+ et.addScreenCapture("ss3.png"));
				  driver.close();
			}
		}
		     
		     // save results
		    er.endTest(et);
		    er.flush();

			
		
		

	}

}
