package test;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.omg.DynamicAny.NameDynAnyPairHelper;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import component.Page;
import utilities.BrowserFactory;
import utilities.DriverFactory;
import utilities.WaitMethods;

public class ScrapTest {

	public static WebDriver driver;
	public static Page page;
	private JavascriptExecutor js;
	
	private List<String> resulationList;
	private List<String> priceList;
	private List<String> inchList;
	private List<String> hertzList;
	private List<String> screenTecnologhyList;
	private List<String> pointList;
	
	
	@Before
	public void setup() {
		if(driver == null) {
			BrowserFactory browserFactory = new BrowserFactory();
			driver = DriverFactory.createDriver();
			System.out.println("test driver baslatildi. \n" + driver);
//			DriverFactory.setimplicitlyWait(driver);
			System.out.println(driver.getTitle());
			js = (JavascriptExecutor) driver;
		
		}	
	}
	
	@Test
	public void test01() {
		//driver.get("https://www.epey.com/monitor/e/YToxOntzOjU6ImZpeWF0IjthOjI6e2k6MDtzOjM6IjEwMCI7aToxO3M6NjoiMjAxOTAwIjt9fV9OOw==/1/");
		WaitMethods.waitForPageToLoad(driver, 15);
		page = new Page(driver);
		
		priceList = page.transferToStringList(page.priceElements);
		
	}
	
	@After
	public void tearDown() {
		DriverFactory.closeDriver(driver);
		System.out.println("test driver durduruldu.");
	}
		

}
