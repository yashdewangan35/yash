package frame_work;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Example_1 {
	public static void main(String[] args) throws BiffException, IOException {

		// connect to excel file
		File f = new File("Book12.xls");

		// open that excel file for reading
		Workbook rwb = Workbook.getWorkbook(f); // Workbook,Sheet,Writable
												// import to jxl.file
		Sheet rsh = rwb.getSheet(0); // 0 means sheet 1
		int nour = rsh.getRows();

		// open excel file for writing
		WritableWorkbook wwb = Workbook.createWorkbook(f, rwb);
		WritableSheet wsh = wwb.getSheet(0); // 0 means sheet1

		WebDriver driver = null;
		int i;
		try {
			for (i = 1; i < nour; i++) {

				// run browser driver to get browser
				System.setProperty("webdriver.chrome.driver",
						"E:\\batch237\\driver\\chromedriver.exe");

				// create object to acess that openend browser
				driver = new ChromeDriver();

				// launch site
				driver.manage().window().maximize();
				driver.get("https://accounts.google.com/signup/v2/webcreateaccount?service=mail&continue=https%3A%2F%2Fmail.google.com%2Fmail%2F%3Fpc%3Dtopnav-about-en&flowName=GlifWebSignIn&flowEntry=SignUp");

				// to give time to browser to show page
				WebDriverWait w = new WebDriverWait(driver, 100);
				w.until(ExpectedConditions.visibilityOfElementLocated(By
						.xpath("//*[@id='firstName']")));

				driver.findElement(By.xpath("//*[@id='firstName']")).sendKeys(
						rsh.getCell(1, i).getContents());
				Thread.sleep(5000);
				driver.findElement(By.xpath("//*[@name='lastName']")).sendKeys(
						rsh.getCell(3, i).getContents());
				Thread.sleep(5000);
				driver.findElement(By.xpath("//*[@name='Username']")).sendKeys(
						rsh.getCell(4, i).getContents());
				Thread.sleep(5000);
				driver.findElement(By.xpath("//*[@name='Passwd']")).sendKeys(
						rsh.getCell(5, i).getContents());
				Thread.sleep(5000);
				driver.findElement(By.xpath("//*[@name='ConfirmPasswd']"))
						.sendKeys(rsh.getCell(6, i).getContents());
				Thread.sleep(5000);
				driver.close();

			}
		}

		catch (Exception e) {
		}

		driver.close();
	}

}
