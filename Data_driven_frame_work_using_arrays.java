package frame_work;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
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

public class Data_driven_frame_work_using_arrays 
{    
	
	// gmail login test automation by using arrays.data
	
	public static void main(String[] args) throws Exception
	{
        // get data from keyboard
		
		Scanner sc=new Scanner(System.in);
		
		//Create dynamic arrays
		
		ArrayList<String> uids=new ArrayList<String>();
		ArrayList<String> uidcs=new ArrayList<String>();
		ArrayList<String> pwds=new ArrayList<String>();
		ArrayList<String> pwdcs=new ArrayList<String>();
		
		//  get number of iterartions in testing
		 
		System.out.println("Enter number of iterations");
		int n=Integer.parseInt(sc.nextLine());
		
		// get data into arrays 
		
		for(int j=0; j<n; j++)
		{
			System.out.println("enter user id");
			uids.add(sc.nextLine());
			System.out.println("enter userid as valid or invalid");
			uidcs.add(sc.nextLine());
			if(uidcs.get(j).equalsIgnoreCase("valid"))
			{
				System.out.println("enter password");
				pwds.add(sc.nextLine());
				System.out.println("enter password criteria as valid or invalid");
				pwdcs.add(sc.nextLine());
			}
			else
			{
				pwds.add("n/a");
				pwdcs.add("n/a");
			}
		}
		
		// crate results file (.html)
		ExtentReports er=new ExtentReports("gmail-login-ddt.html",false);
		ExtentTest et=er.startTest("gmail login test");
		WebDriver driver=null;
		
		// data driven test
		for(int i=0; i<n; i++)
		{
			try
			{ 

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
				driver.findElement(By.name("identifier")).sendKeys(uids.get(i));
				w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Next']")));
				driver.findElement(By.xpath("//*[text()='Next']")).click();
                
				// to give time to browser to activate pwd or next
				
				Thread.sleep(5000);
				if(uidcs.get(i).equalsIgnoreCase("valid") && driver.findElement(By.name("password")).isDisplayed())
				{
					et.log(LogStatus.PASS,"Valid userid test passed");
					
					// enter passwor and perform validations
					driver.findElement(By.name("password")).sendKeys(pwds.get(i));
					w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Next']")));
					driver.findElement(By.xpath("//*[text()='Next']")).click();
					Thread.sleep(5000);
					if(pwdcs.get(i).equalsIgnoreCase("valid") && driver.findElement(By.xpath("//*[text()='COMPOSE']")).isDisplayed())
					{
						et.log(LogStatus.PASS,"valid user id & pwd test passed");
					}
					
					else if(pwdcs.get(i).equalsIgnoreCase("invalid") && driver.findElement(By.xpath("//*[text()='Enter your password'or contains(text(),'Wrong password')]")).isDisplayed())
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
				
				else if(uidcs.get(i).equalsIgnoreCase("invalid") && driver.findElement(By.xpath("(//*[@aria-live='assertive'])[1]|(//*[text()='Forgot email?'])")).isDisplayed())
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
