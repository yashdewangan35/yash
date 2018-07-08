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

public class Linear_frame_work_2 {

	public static void main(String[] args) throws Exception {
		// get data from keyboard
		Scanner sc = new Scanner(System.in);
		System.out.println("enter user id");
		String u = sc.nextLine();
		System.out.println("enter user id criteria as valid or invalid");
		String uc = sc.nextLine();
		String p = "";
		String pc = "";
		if (uc.equalsIgnoreCase("valid")) {
			System.out.println("Enter password");
			p = sc.nextLine();
			System.out.println("enter password criteria as valid or invalid");
			pc = sc.nextLine();
		}
		// create results file(.html)
		ExtentReports er = new ExtentReports("gmalitestresult.html", false);
		ExtentTest et = er.startTest("gmail login test");
		WebDriver driver = null;
		try {
			// run browser driver to get browser
			System.setProperty("webdriver.chrome.driver",
					"E:\\batch237\\chromedriver\\chromedriver_win32\\chromedriver.exe");

			// create object to acess that openend browser
			driver = new ChromeDriver();

			// launch site
			driver.manage().window().maximize();
			driver.get("http://www.gmail.com");
			WebDriverWait w = new WebDriverWait(driver, 100);
			w.until(ExpectedConditions.visibilityOfElementLocated(By
					.name("identifier")));
			driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);

			// Enter user id and perform validations
			driver.findElement(By.name("identifier")).sendKeys(u);
			Thread.sleep(5000);
			driver.findElement(By.xpath("//*[text()='Next']")).click();
			Thread.sleep(5000);
			if (uc.equalsIgnoreCase("valid")
					&& driver.findElement(By.name("password")).isDisplayed()) 
			{
				et.log(LogStatus.PASS, "valid user id test passed");
				Thread.sleep(5000);

				// enter password and perform validations
				driver.findElement(By.name("password")).sendKeys(p);
				driver.findElement(By.xpath("//*[text()='Next']")).click();
				Thread.sleep(10000);
				if (pc.equalsIgnoreCase("valid")
						&& driver
								.findElement(By.xpath("//*[text()='COMPOSE']"))
								.isDisplayed()) {
					et.log(LogStatus.PASS, "valid password test passed");

				} 
				else if (pc.equalsIgnoreCase("invalid")
						&& driver
								.findElement(
										By.xpath("//*[text()='Enter your password'or contains(text(),'Wrong password')]"))
								.isDisplayed()) {
					et.log(LogStatus.PASS, "Invalid password test passed");

				} 
				else {
					File src = ((TakesScreenshot) driver)
							.getScreenshotAs(OutputType.FILE);
					File dest = new File("ss3.png");
					FileUtils.copyFile(src, dest);
					et.log(LogStatus.FAIL,
							"Test failed" + et.addScreenCapture("ss3.png"));
				}

			} 
			
			else if (uc.equalsIgnoreCase("invalid")
					&& driver
							.findElement(
									By.xpath("(//*[@aria-live='assertive'])[1]|(//*[text()='Forgot email?'])"))
							.isDisplayed())

			{
				et.log(LogStatus.PASS, "Invalid user id test passed");

			} 
			else 
			{
				File src = ((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.FILE);
				File dest = new File("ss4.png");
				FileUtils.copyFile(src, dest);
				et.log(LogStatus.FAIL,
						"Test failed" + et.addScreenCapture("ss4.png"));
			}
			// close site
			driver.close();
		} 
		catch (Exception e) 
		{
			File src = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			File dest = new File("ss5.png");
			FileUtils.copyFile(src, dest);
			et.log(LogStatus.ERROR,
					e.getMessage() + et.addScreenCapture("ss5.png"));
			driver.close();
		}
		// save result
		er.endTest(et);
		er.flush();

	}

}
