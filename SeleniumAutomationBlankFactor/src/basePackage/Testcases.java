package basePackage;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class Testcases {
	private static WebDriver driver;
	WebElement element;

	@BeforeClass
	public static void openBrowser(){
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\jars\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Before
	public void openApplication()
	{
		driver.get("https://blankfactor.com/");	
		driver.manage().window().maximize();
	}
	
	@Test
	public void validatePresenceOfBlogSection() throws InterruptedException{
		String postTitle, postAuthor,postLink,emailId;
		int totalArtDisplayed,i;
		System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id='hs-eu-confirmation-button']")).click();		
		driver.findElement(By.xpath("//ul[@id='menu-main-menu']/li[4]")).click();
		driver.findElement(By.xpath("//div[@class='page-item__info']/div[2]/a")).click();
		
		//Scroll down to - “Why Fintech in Latin America Is Having a Boom” and click the post by Sofia Gonzalez
		totalArtDisplayed=driver.findElements(By.xpath("//div[@class='posts-list']/article")).size();	
		i=1;

		while (i<=totalArtDisplayed){
			postTitle=driver.findElement(By.xpath("//div[@class='posts-list']/article["+i+"]/div/h2")).getText();
			postAuthor=driver.findElement(By.xpath("//div[@class='posts-list']/article["+i+"]/div/div[2]/div[1]/a")).getText();
			if(postTitle.contains("Why Fintech in Latin America Is Having a Boom") && postAuthor.contains("Sofia Gonzalez")) {
				driver.findElement(By.xpath("//div[@class='posts-list']/article["+i+"]/div/h2")).click();
				break;
			}
			else {
				if(i==totalArtDisplayed) {
					driver.findElement(By.xpath("//button[contains(@class,'btn-load-more-posts')]")).click();
					Thread.sleep(2000);
					totalArtDisplayed=driver.findElements(By.xpath("//div[@class='posts-list']/article")).size();
				}		
				i++;
			}		
		}	
		
		//Make validation (that the script is on the correct page, by verifying url, some of the text on the page)
		Assert.assertEquals("https://blankfactor.com/insights/blog/fintech-in-latin-america/", driver.getCurrentUrl());
		Assert.assertEquals("Why Fintech in Latin America Is Having a Boom", driver.findElement(By.xpath("//h1[contains(@class,'post-title')]")).getText().trim());
		Assert.assertEquals("Sofia Gonzalez", driver.findElement(By.xpath("//div[@class='author-name']/a")).getText().trim());
		
		//Subscribe to the newsletter using the subscribe form
		WebElement email=driver.findElement(By.xpath("//div[contains(@class,'widget-newsletter')]/form/div/label[1]/input"));
		emailId="abc@gmail.com";
		email.sendKeys(emailId);
		WebElement subscribeBtn=driver.findElement(By.xpath("//div[contains(@class,'widget-newsletter')]/form/div[1]/div/button"));
		subscribeBtn.click();
		Thread.sleep(2000);
		String subscribeNot=driver.findElement(By.xpath("//div[contains(@class,'widget-newsletter')]/form/div[2]")).getText().trim();
		Assert.assertEquals("Thank you for subscribing! Stay tuned.", subscribeNot);
		
		//Go back to the Blog section and print a list of all posts titles with related links.
		driver.navigate().back();
		i=1;
		totalArtDisplayed=driver.findElements(By.xpath("//div[@class='posts-list']/article")).size();	
		while (i<=totalArtDisplayed){
			postTitle=driver.findElement(By.xpath("//div[@class='posts-list']/article["+i+"]/div/h2")).getText();
			postLink=driver.findElement(By.xpath("//div[@class='posts-list']/article["+i+"]/a")).getAttribute("href");
			System.out.println("Title of Blog "+i+"-"+postTitle+" and related link is "+postLink);
			if(i==totalArtDisplayed) {	
				WebElement Element=driver.findElement(By.xpath("//button[contains(@class,'btn-load-more-posts')]"));
				if(Element.isDisplayed()) {
					// Actions class with moveToElement()
					Actions a = new Actions(driver);
					a.moveToElement(Element);
					a.perform();
					Thread.sleep(500);
					driver.findElement(By.xpath("//button[contains(@class,'btn-load-more-posts')]")).click();
					Thread.sleep(2000);
					totalArtDisplayed=driver.findElements(By.xpath("//div[@class='posts-list']/article")).size();
				}
				else {
					break;
				}

			}		
			i++;
		}
		System.out.println("Ending test " + new Object(){}.getClass().getEnclosingMethod().getName());
	}

	@AfterClass
	public static void closeBrowser(){
		driver.quit();
	}
}
