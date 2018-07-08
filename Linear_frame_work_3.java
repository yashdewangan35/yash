package frame_work;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Linear_frame_work_3
{
	public static void main(String[] args) 
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("enter a word");
		String x=sc.nextLine();
		WebDriver driver=null;
		
		// run browser driver to get browser
        System.setProperty("webdriver.chrome.driver","E:\\batch237\\chromedriver\\chromedriver_win32\\chromedriver.exe");
		
		// create object to acess that openend browser
		driver=new ChromeDriver();
		
		// launch site 
		driver.manage().window().maximize();
		driver.get("http://www.google.co.in");
		WebDriverWait w=new WebDriverWait(driver,100);
		w.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		driver.findElement(By.name("q")).sendKeys(x,Keys.ENTER);
		
		//results pages are ready to navigate (pagination) //pagination means moving from 1 page to next page until last page 
	   
        int flag=0;
        while(2>1) //infinte loop
        {
        	String t=driver.getTitle();
        	if(! t.contains(x))
        	{
        		flag = 1;
        		break;
        	}
        	
        	// go to next page of result
        	
        	try
        	{
        		if(driver.findElement(By.xpath("//*[text()='Next']")).isDisplayed())
        	{
        			driver.findElement(By.xpath("//*[text()='Next']")).click();
        			Thread.sleep(2000);
        	}
        	}
        	catch(Exception e)
        	{
        		break;
        	}
        }
        // validation 
        if (flag==0)
        {
        	System.out.println("test passed");
        }
        
        else
        {
        	System.out.println("test failed");
        }
        
        // close site 
        
        driver.close();
        
	}   

}
