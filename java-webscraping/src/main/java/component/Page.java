package component;

import java.util.ArrayList;
import java.util.List;
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
	@FindBy(xpath = "//a[@class='urunadi']")
	public List<WebElement> productNameElements;
	
	@FindBy(xpath = "//span[@class='degergetir']")
	public List<WebElement> resulationElements;
	
	@FindBy(xpath = "//li[@class='fiyat cell']/a")
	public List<WebElement> priceElements;
	
	@FindBy(xpath = "//li[@class='ozellik ozellik3968 cell']")
	public List<WebElement> inchElements;
	
	@FindBy(xpath = "//li[@class='ozellik ozellik3974 cell']")
	public List<WebElement> hertzElements;
	
//	@FindBy(xpath = "//li[@class='ozellik ozellik3973 cell']")
//	public List<WebElement> screenTecnologhyElements;
	
	@FindBy(xpath = "//li[@class='puan cell']/div/span[1]")
	public List<WebElement> pointElements;
	
	public List<String> transferToStringList(List<WebElement> elementsList) {
		List<String> liste = new ArrayList<>();
		for(WebElement element : elementsList) {
			liste.add(element.getText());
		}
		return liste;
	}	
	
}
