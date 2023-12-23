package test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import component.Page;
import utilities.BrowserFactory;
import utilities.DriverFactory;
import utilities.WaitMethods;

public class ScrapTest {

	public static WebDriver driver;
	public static Page page;

	private List<String> productNameList;
	private List<String> resulationList;
	private List<String> priceList;
	private List<String> inchList;
	private List<String> hertzList;
//	private List<String> screenTecnologhyList;
	private List<String> pointList;

	private List<String> cleanedPriceList;
	private List<String> cleanedResulationList;
	private List<String> cleanedHertzList;
	private List<String> cleanedInchList;

	@Before
	public void setup() {
		if (driver == null) {
			BrowserFactory browserFactory = new BrowserFactory();
			driver = DriverFactory.createDriver();
			System.out.println("test driver baslatildi. \n" + driver);
//			DriverFactory.setimplicitlyWait(driver);
			System.out.println(driver.getTitle());
			resulationList = new ArrayList<>();
			priceList = new ArrayList<>();
			inchList = new ArrayList<>();
			hertzList = new ArrayList<>();
			pointList = new ArrayList<>();
			cleanedHertzList = new ArrayList<>();
			cleanedInchList = new ArrayList<>();
			cleanedPriceList = new ArrayList<>();
			cleanedResulationList = new ArrayList<>();
			productNameList = new ArrayList<>();


		}
	}

	@Test
	public void test01() {

		for (int j = 1; j < 33; j++) {
			driver.get(
					"https://www.epey.com/monitor/e/YToxOntzOjU6ImZpeWF0IjthOjI6e2k6MDtzOjM6IjEwMCI7aToxO3M6NjoiMjAxOTAwIjt9fV9OOw==/"
							+ j + "/");
			WaitMethods.waitForPageToLoad(driver, 15);
			page = new Page(driver);

			List<String> tempList = new ArrayList<String>();
			tempList = page.transferToStringList(page.priceElements);
			priceList.addAll(tempList);
			tempList.clear();
			tempList = page.transferToStringList(page.resulationElements);
			resulationList.addAll(tempList);
			tempList.clear();
			tempList = page.transferToStringList(page.hertzElements);
			hertzList.addAll(tempList);
			tempList.clear();
			tempList = page.transferToStringList(page.inchElements);
			inchList.addAll(tempList);
			tempList.clear();
			tempList = page.transferToStringList(page.pointElements);
			pointList.addAll(tempList);
			tempList.clear();
			tempList = page.transferToStringList(page.productNameElements);
			productNameList.addAll(tempList);
			tempList.clear();

			
			 System.out.println(pointList);
			 cleanedPriceList = cleanPrices(priceList);
			 System.out.println(cleanedPriceList);
			 cleanedResulationList = cleanResolutions(resulationList);
			 System.out.println(cleanedResulationList);
			 cleanedHertzList = cleanHertz(hertzList);
			 System.out.println(cleanedHertzList);
			 cleanedInchList = cleanInches(inchList);
			 System.out.println(cleanedInchList);

		}

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Data Sheet");
		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 4000);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 4000);


		int rowNum = 0;
		while (rowNum < priceList.size()) {
			
			Row row = sheet.createRow(rowNum);
			
			Cell nameCell = row.createCell(0);
			nameCell.setCellValue(productNameList.get(rowNum));

			Cell resCell = row.createCell(1);
			resCell.setCellValue(cleanedResulationList.get(rowNum));

			Cell inchCell = row.createCell(2);
			inchCell.setCellValue(cleanedInchList.get(rowNum));

			Cell hertzCell = row.createCell(3);
			hertzCell.setCellValue(cleanedHertzList.get(rowNum));

			Cell pointCell = row.createCell(4);
			pointCell.setCellValue(pointList.get(rowNum));

			Cell priceCell = row.createCell(5);
			priceCell.setCellValue(cleanedPriceList.get(rowNum));

			rowNum++;
		}

		// Save the workbook to a file
		try (FileOutputStream outputStream = new FileOutputStream("collected_data.xlsx")) {
			workbook.write(outputStream);
			System.out.println("Data written to Excel file successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private List<String> cleanInches(List<String> screenSizeList) {
		List<String> cleanedScreenSizes = new ArrayList<>();

		for (String input : screenSizeList) {
			String cleanedScreenSize = input.replaceAll("\\s*İnç", "");
			cleanedScreenSizes.add(cleanedScreenSize);
		}

		return cleanedScreenSizes;
	}

	private List<String> cleanHertz(List<String> frequencyList) {
		List<String> cleanedFrequencies = new ArrayList<>();

		for (String input : frequencyList) {
			String cleanedFrequency = input.replaceAll("\\s*Hz", "");
			cleanedFrequencies.add(cleanedFrequency);
		}

		return cleanedFrequencies;
	}

	private List<String> cleanResolutions(List<String> inputList) {
		List<String> result = new ArrayList<>();
		for (String str : inputList) {
			if (str.length() >= 9) {
				result.add(str.substring(0, 9));
			} else {
				// String 9 karakterden kısa ise tamamını ekleyelim
				result.add(str);
			}
		}
		return result;
	}

	private List<String> cleanPrices(List<String> stringList) {
		List<String> cleanedPrices = new ArrayList<>();
		Pattern pattern = Pattern.compile("\\d{1,3}(\\.\\d{3})*(,\\d{2})?");

		for (String input : stringList) {
			Matcher matcher = pattern.matcher(input);
			if (matcher.find()) {
				cleanedPrices.add(matcher.group());
			} else {
				cleanedPrices.add(""); // veya başka bir değer eklenebilir
			}
		}

		return cleanedPrices;
	}

	@After
	public void tearDown() {
		DriverFactory.closeDriver(driver);
		System.out.println("test driver durduruldu.");
	}

}
