package frame_work;

import jxl.LabelCell;
import java.io.File;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.Label;
import jxl.write.WriteException;
import java.util.concurrent.TimeUnit;

import jxl.Sheet;
import jxl.write.Number;
import jxl.write.WritableCell;
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

public class Datadriven_frame_work_with_excel_file 
{
	public static void main(String[] args) throws Exception
	{
		// connect to excel file 
		File f=new File ("Book2.xls");
				
		// open that excel file for reading
	     Workbook rwb = Workbook.getWorkbook(f);   // Workbook,Sheet,Writable import to jxl.file
         Sheet rsh=rwb.getSheet(0);                 // 0 means sheet 1
	     int nour=rsh.getRows();
		        
		//open excel file for writing
		 WritableWorkbook wwb=Workbook.createWorkbook(f,rwb);
		 WritableSheet wsh=wwb.getSheet(0);         // 0 means sheet1
		 

		 // data driven test
		
		WebDriver driver=null;
		int i;
		try
			{
				for(i=1; i<nour; i++)
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
				driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
				driver.findElement(By.name("identifier")).sendKeys(rsh.getCell(0,i).getContents());
				Thread.sleep(2000);
				w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Next']")));
				driver.findElement(By.xpath("//*[text()='Next']")).click();
				Thread.sleep(5000);
                
                // to give time to browser to activate pwd or next
				
				
				if(rsh.getCell(1,i).getContents().equalsIgnoreCase("valid") && driver.findElement(By.name("password")).isDisplayed())
				{
					
					// enter passwor and perform validations
					Thread.sleep(5000);
					driver.findElement(By.name("password")).sendKeys(rsh.getCell(2,i).getContents());
					w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Next']")));
					driver.findElement(By.xpath("//*[text()='Next']")).click();
					
					if(rsh.getCell(3,i).getContents().equalsIgnoreCase("valid") && driver.findElement(By.xpath("//*[text()='COMPOSE']")).isDisplayed())
					{
						//Label label= new Label(3,i,null);
						//label.setString("valid user id and pswd");
					}
					
					else if(rsh.getCell(4,i).getContents().equalsIgnoreCase("invalid") && driver.findElement(By.xpath("//*[text()='Enter your password'or contains(text(),'Wrong password')]")).isDisplayed())
					{
						//Label label= new Label(3,i,null);

						//et.log(LogStatus.PASS,"Valid mail id but Invalid pwd test passed");
					}
					
					else
					{
		            }
				}
				
				else if(rsh.getCell(5,i).getContents().equalsIgnoreCase("invalid") && driver.findElement(By.xpath("(//*[@aria-live='assertive'])[1]|(//*[text()='Forgot email?'])")).isDisplayed())
				{
					//Label label= new Label(3,i,null);

					//et.log(LogStatus.PASS,"Invalid user id test passed");

				}
		        
				else
				{
					
				}
				
				// close site 
				driver.close();
				
			  
		        }
			}
			  catch(Exception e)
			  {
			  }
		
		     
		     // save results
		   
	}
}

