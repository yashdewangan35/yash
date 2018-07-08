package frame_work;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Example2 {
	public static void main(String[] args) throws Exception 
	{
		// get data from keyboard
				Scanner sc=new Scanner(System.in);
				System.out.println("enter valid user id");
				String u=sc.nextLine();
				System.out.println("enter valid password");
				String p=sc.nextLine();
        System.setProperty("webdriver.chrome.driver","E:\\batch237\\chromedriver\\chromedriver_win32\\chromedriver.exe");
		
		// create object to acess that openend browser
		ChromeDriver driver=new ChromeDriver();
		
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
		
		
		// get all mails count before deletion 
		
		String y= driver.findElement(By.xpath("(//*[@class='ts'])[3]")).getText();
		try
		{
		int z=Integer.parseInt(y);
		System.out.println("all mail count before deletion"+"...."+z);
		}
		
		catch(NumberFormatException ex)
		{
			
		}
		// pagination to delete mails 
		//int dmc=0;
	try
	{
		while (1<2)
			
		{
			    int mc = driver.findElements(By.xpath("(//*[@role='tabpanel'])[1]/descendant::tbody/tr")).size();
			    for(int i=1; i<=mc; i++ )
			    
			 {
				String t= driver.findElement(By.xpath("(//*[@role='tabpanel'])[1]/descendant::tbody/tr["+i+"]/td[8]/span")).getAttribute("title");
				System.out.println(t);
				//String t1= driver.findElement(By.xpath("(//*[@name='Twitter'])[1]")).getText();
				//System.out.println(t1);

				if(t.contains("Twitter"))
				{
					driver.findElement(By.xpath("(//*[@role='tabpanel'])[1]/descendant::tbody/tr["+i+"]/td[2]/div")).click();
					Thread.sleep(5000);
					//driver.findElement(By.xpath("(//*[@class='G-Ni J-J5-Ji'])[2]/div[3]")).click();
					//Thread.sleep(5000);
					//dmc=dmc+1;
					//i=i-1;   // to check next mail which occupied place of deleted mail place
				}
			 
			   
			 }
		
		}
	}
		catch (Exception e)
		{
			e.getMessage();
			System.out.println(e);
		}
			
	}
}
	


