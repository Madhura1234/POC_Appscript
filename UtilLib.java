package com.ims.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.base.Predicate;


public class UtilLib {
	public static WebDriver driver;
	public static ObjectDefinitionLibrary element = new ObjectDefinitionLibrary();
	public FileInputStream fis = null;
	public XSSFWorkbook workbook = null;
	public XSSFSheet sheet = null;
	public XSSFRow row = null;
	public XSSFCell cell = null;
	FormulaEvaluator evaluator =null;

	/******************************************************************************************** 
	 * @throws IOException 
	 * @Function_Name : getDriver
	 * * * @author: Madhura
	 * @Description : Creates Driver object to launch scripts in different browser
	 ********************************************************************************************/
	public static  WebDriver getDriver () throws IOException{
		String browserType=getPropertiesValue("Browser");
		switch (browserType) {
		case "Chrome":
			System.out.println("=========="+browserType);
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/chromedriver_Latest.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			break;
		case "Firefox":
			System.out.println("^^^^^^^^^^^^^^^^^^"+browserType);
			File pathToBinary = new File("C:\\Users\\Madhura.G\\AppData\\Local\\Mozilla Firefox\\firefox.exe");
			FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
			FirefoxProfile firefoxProfile = new FirefoxProfile();
			driver = new FirefoxDriver(ffBinary,firefoxProfile);
			driver.manage().window().maximize();
			break;
		case "IE":
			System.out.println("*********"+browserType);
			System.setProperty("webdriver.IEDriverServer.driver",System.getProperty("user.dir")+"/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			driver.manage().window().maximize();
			break;
		default:
			System.out.println("browser : " + browserType+ " is invalid..");
		}
		return driver;
	}

	/******************************************************************************************** 
	 * @Function_Name : waitForPageLoad
	 * * * @author: Madhura
	 * @Description : wait untill the page has been loaded
	 ********************************************************************************************/
	public static boolean waitForPageLoad(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver,10000);

		Predicate<WebDriver> pageLoaded = new Predicate<WebDriver>() {

			@Override
			public boolean apply(WebDriver input) {
				return ((JavascriptExecutor) input).executeScript("return document.readyState").equals("complete");
			}
		};
		wait.until(pageLoaded);
		return false;
	}
	
	public void waitForPageLoaded() {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver,100);
			wait.until(expectation);
		} catch (Throwable error) {
			Assert.fail("Timeout waiting for Page Load Request to complete.");
		}
	}
	
	public void waitForLoad(WebDriver driver) {
		ExpectedCondition<Boolean> pageLoadCondition = new
				ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 1000);
		wait.until(pageLoadCondition);
	}
	/******************************************************************************************** 
	 * @Function_Name : getPropertiesValue
	 * * * @author: Madhura
	 * @Description : Read values from property file for constant values
	 ********************************************************************************************/
	public static String getPropertiesValue(String key) throws IOException {
		String workingDir = System.getProperty("user.dir");
		String configpath = workingDir + "/DNA_TestData/config.properties";
		Properties props = new Properties();
		FileInputStream URLPath = new FileInputStream(configpath);
		props.load(URLPath);
		String url = props.getProperty(key);
		return url;
	}
	/******************************************************************************************** 
	 * @Function_Name : systemTimeStamp
	 * * * @author: Madhura
	 * @Description : Function to fetch the current system date 
	 ********************************************************************************************/
	public static String systemTimeStamp() {
		DateFormat df = new SimpleDateFormat("dd.MMM.yyyy-HH.mm.ss");
		Date today = Calendar.getInstance().getTime();
		String runTime = df.format(today);
		//System.out.println(df.format(today));
		return runTime;
	}

	/******************************************************************************************** 
	 * @throws InterruptedException 
	 * @Function_Name : DNA_Login
	 * * * @author: Madhura
	 * @Description : Initiate Browser and navigate to the URL with valid credentials
	 ********************************************************************************************/
	public static void DNA_Login() throws IOException, InterruptedException{
		try {

			driver = UtilLib.getDriver();
			driver.manage().deleteAllCookies();

			String UrlLaunch = getPropertiesValue("URL");
			String UN = getPropertiesValue("User_Name");
			String Password_property = getPropertiesValue("Password");

			driver.get(UrlLaunch);
			System.out.println(UrlLaunch);

			driver.findElement(By.xpath(element.UserName)).clear();
			driver.findElement(By.xpath(element.UserName)).sendKeys(getPropertiesValue("User_Name"));

			byte[] decodedBytes = Base64.decodeBase64(Password_property);
			driver.findElement(By.xpath(element.Password)).sendKeys(new String(decodedBytes));

			driver.findElement(By.xpath(element.Login_Button)).click();
			Thread.sleep(1000);
		} 
		catch (Error e) {
			e.printStackTrace();
			e.getMessage();

		}
	}
	/******************************************************************************************** 
	 * @throws InterruptedException 
	 * @Function_Name : isElementPresent
	 * * * @author: Madhura
	 * @Description : Checks if Element is present
	 ********************************************************************************************/
	public static boolean isElementPresent(String element , By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchFrameException e) {
			return false;
		}
	}
	/********************************************************************************************
	 * @Function_Name :  verifyElementPresent
	 * @Description : Verifies the Element is present in the page
	 *@param ElementName - Name of the Element
	 ** * @author: Madhura
	 *@param Xpath - XPATH of the Element 
	 ********************************************************************************************/
	public static boolean verifyElementPresent(String ElementName,String Xpath){
		int RerunFlag = 0;
		try {
			boolean Verify = UtilLib.isElementPresent(ElementName, By.xpath(Xpath));
			if (Verify  == true){
				boolean Verify_Displayed  = driver.findElement(By.xpath(Xpath)).isDisplayed();
				if (Verify_Displayed  == true){
					System.out.println(ElementName+" is present in the page");
				} else {
					RerunFlag = RerunFlag+1;
				}
			} else {
				RerunFlag = RerunFlag+1;
			}

			if(RerunFlag>0){
				return false;
			} else{
				return true;
			}
		} catch (Error e) {
			e.printStackTrace();
			return false;
		}
	}
	/********************************************************************************************
	 * @Function_Name :  screenshot
	 * * * @author: Madhura
	 * @Description : Function to capture the screenshot
	 ********************************************************************************************/
	public static String screenshot(WebDriver driver, String screenshotname) {

		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);

			DateFormat screenshotName1 = new SimpleDateFormat("dd-MMMM-yyyy_HH-mm-ss");
			Date screenshotDate = new Date();
			String picName = screenshotName1.format(screenshotDate);

//			String dest = System.getProperty("user.dir") + "/Screenshots/" + picName + ".png";
			String dest = System.getProperty("user.dir") + "/Screenshots/" + picName + screenshotname+ ".png";
			File destination = new File(dest);
			FileUtils.copyFile(source, destination);
			System.out.println("Screenshot Captured");

			return dest;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}

	/********************************************************************************************
	 * @Function_Name : getCellData
	 * * * @author: Madhura
	 * @Description :  Reusable function for retrieving the test data from excel sheet
	 ********************************************************************************************/
	public String getCellData(String xlFilePath,String sheetName, String colName, int rowNum) throws IOException
	{
		fis = new FileInputStream(xlFilePath);
		workbook = new XSSFWorkbook(fis);
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		try
		{
			int col_Num = -1;
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(0);
			for(int i = 0; i < row.getLastCellNum(); i++)
			{
				if(row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num = i;
			}

			row = sheet.getRow(rowNum);
			cell = row.getCell(col_Num);

			if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == XSSFCell.CELL_TYPE_FORMULA)
			 return cell.getCellFormula();
			else if(cell.getCellType() ==XSSFCell.CELL_TYPE_NUMERIC || cell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN)
			{
				String cellValue = String.valueOf(cell.getNumericCellValue());
				if(HSSFDateUtil.isCellDateFormatted(cell))
				{
					DateFormat df = new SimpleDateFormat("dd/MM/yy");
					Date date = cell.getDateCellValue();
					cellValue = df.format(date);
				}
				return cellValue;
			}else if(cell.getCellType() ==XSSFCell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "row "+rowNum+" or column "+colName +" does not exist  in Excel";
		}
		
	}
	/********************************************************************************************
	 * @Function_Name :  getRowCount
	 * * * @author: Madhura
	 * @Description : Function to retrieve the Row count from the excel sheet
	 ********************************************************************************************/
	public  int getRowCount(String xlpath,String sheetName)
	{
		int rc=0;
		try
		{
			FileInputStream fis=new FileInputStream(xlpath);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet(sheetName);
			rc=sheet.getLastRowNum();
		}
		catch(Exception e)
		{
		}
		return rc;
	}


}

