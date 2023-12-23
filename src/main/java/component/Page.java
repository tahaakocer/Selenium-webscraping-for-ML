package component;

import java.util.List;

import javax.xml.xpath.XPath;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Page {
	
	private WebDriver driver;
	
	public Page(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(xpath = "//span[@class='degergetir']")
	List<WebElement> resulationElements;
	
	@FindBy(xpath = "//li[@class='fiyat cell']/a")
	List<WebElement> priceElements;
}
