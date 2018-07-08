package frame_work;

import java.io.File;
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

public class Linear_frame_work_4 
{
	public static void main(String[] args)
	{
		      // get data from keyboard
				Scanner sc=new Scanner(System.in);
				System.out.println("enter valid user id");
				String u=sc.nextLine();
				System.out.println("enter valid password");
				String p=sc.nextLine();
				
				// create results file (.html)
				ExtentReports er=new ExtentReports("gmalitestresult.html",false);
				ExtentTest et=er.startTest("gmail mailbox count test");
				WebDriver driver=null;
				try
				{
					

					// run browser driver to get browser
		            System.setProperty("webdriver.chrome.driver","E:\\batch237\\chromedriver\\chromedriver_win32\\chromedriver.exe");
					
					// create object to acess that openend browser
					driver=new ChromeDriver();
					
					//launch site
					driver.manage().window().maximize();
					driver.get("http://www.gmail.com");
					
					//to give time to browser to show page 
					WebDriverWait w=new WebDriverWait(driver,100);
					w.until(ExpectedConditions.visibilityOfElementLocated(By.name("identifier")));
					driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
					
					// enter user id and perform validations 
					driver.findElement(By.name("identifier")).sendKeys(u);
					driver.findElement(By.xpath("//*[text()='Next']")).click();
					w.until(ExpectedConditions.elementToBeClickable(By.name("password")));
					driver.findElement(By.name("password")).sendKeys(p);
					w.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='Next']")));
					driver.findElement(By.xpath("//*[text()='Next']")).click();
					w.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='COMPOSE']")));

					// pagination to count all mails
					
					int eamc=0;  // email all mail count
					while(2>1)
					{
						int mc = driver.findElements(By.xpath("(//*[@role='tabpanel'])[1]/div[2]/div/table/tbody/tr")).size();
						eamc = eamc+mc;
						
						//go to next page 
						try
						{
							if (driver.findElement(By.xpath("//*[@data-tooltip='Older']")).getAttribute("aria-disabled").equals("true"))
							{
								break;
							}
						}
						
						catch (Exception e)
						{
							driver.findElement(By.xpath("//*[@data-tooltip='Older']")).click();
							Thread.sleep(5000);
						}
					}
					System.out.println(eamc);
					//get actual mails count
					String temp= driver.findElement(By.xpath("(//*[@aria-expanded='false'])[11]/descendant::span[5]")).getText();
					int aamc = Integer.parseInt(temp);
					System.out.println(aamc);
					
					//validations 
					if(eamc==aamc)
					{
						et.log(LogStatus.PASS,"Mails count test passed");
						
					}
                    
					else
					{
						File src=((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
						File dest=new File("ss3.png");
						FileUtils.copyFile(src,dest);
						et.log(LogStatus.FAIL,"Mails count test failed"+ et.addScreenCapture("ss3.png"));
					}
					
					// do logout
					 driver.findElement(By.xpath("//*[starts-with(@title,'Google Account')]/child::*")).click();
					 driver.findElement(By.linkText("Sign out")).click();
					 
					 // close site 
					 driver.close();
					 
				}
				
				catch (Exception ex)
				{
					et.log(LogStatus.ERROR,ex.getMessage());
				}
				
				// save results
				er.endTest(et);
				er.flush();

					

				}
				

	}


