# POC_Appscript
package ims.appscript;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Appscript_ProductValidationWithReport {
	static ExtentReports report;
	static ExtentTest logger;
	public static void main(String args[]){

		//Objects and declaration
		report = new ExtentReports(System.getProperty("user.dir") + "/Reports/Extentreport.html");
		logger = report.startTest("Validating a product in AppScript application");
		
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver,20);
		driver.get("https://qa.appscript.net/us/login");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		//Verifying the title of the page
		String actualTitle = driver.getTitle();
		System.out.println("The actual title is: "+actualTitle);
		
		String expectedTitle = "AppScript Prescriber | Consumer Store";
		System.out.println("The expected title is: "+expectedTitle);
		if (expectedTitle.contains(actualTitle)){
			System.out.println("Verification Successful - The correct title is displayed on the web page");
			logger.log(LogStatus.PASS, "The correct title is displayed on the web page: "+driver.getTitle() );
		}       else{
			System.out.println("Verification Failed - An incorrect title is displayed on the web page.");
			logger.log(LogStatus.FAIL, "An incorrect title is displayed on the web page: "+driver.getTitle());
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		
		// enter a valid credentials in text box and click on submit
		WebElement UserName = driver.findElement(By.id("username"));
		UserName.sendKeys("imsfacilityadmin@us.imshealth.com");

		WebElement Password = driver.findElement(By.id("password"));
		Password.sendKeys("P&$$word@123");
		
		String screenshot = screenshot(driver, "Login into Appscript application with valid credentials");
		logger.log(LogStatus.PASS, "Login into Appscript application" + logger.addScreenCapture(screenshot));
		
		WebElement SubmitButton = driver.findElement(By.id("submit"));
		SubmitButton.click();
		
		
		//Search Products in dashboard page
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("query")));
		WebElement SearchMedicine = driver.findElement(By.id("query"));
		SearchMedicine.sendKeys("Medisafe");
		screenshot = screenshot(driver, "Search a product in Appscript Application");
		logger.log(LogStatus.PASS, "Search a product in Appscript Application" + logger.addScreenCapture(screenshot));
		System.out.println(SearchMedicine.getAttribute("value"));
		logger.log(LogStatus.PASS, "The searched product in dashboard page of Appscript application is: "+SearchMedicine.getAttribute("value"));
		driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		SearchMedicine.sendKeys(Keys.ENTER);
		
		//	Verify the product name in the dashboard page
		String DisplayingRecords = driver.findElement(By.xpath("//*[@id='search']/div/div/div[2]/div/div/div/div/div[1]/div[2]/div/div[1]/div")).getText();
		System.out.println("Displayed record of product name is: "+DisplayingRecords);
		logger.log(LogStatus.PASS, "Displayed record of product name in dashboard page of Appscript application is: "+DisplayingRecords);
		
		String ScoreRecord = driver.findElement(By.xpath("//*[@id='search']/div/div/div[2]/div/div/div/div/div[1]/div[2]/div/div[2]/div/div[2]/div/span[2]")).getText();
		System.out.println("Displayed record of product score is: "+ScoreRecord);
		logger.log(LogStatus.PASS, "Displayed record of product score in dashboard page of Appscript application is: "+ScoreRecord);
		
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		
		screenshot = screenshot(driver, "Verify the product name in dashboard page of AppScript Application");
		logger.log(LogStatus.PASS, "Verify the product name in dashboard page of AppScript Application" + logger.addScreenCapture(screenshot));
		
		WebElement DisplayedRecords = driver.findElement(By.xpath("//*[@id='search']/div/div/div[2]/div/div/div/div/div[1]/div[2]/div/div[1]/div"));
		DisplayedRecords.click();
		
		//Verify the product and other details 
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='summary']/div/div/div/div[2]/div[3]/div/span")));
		String DisplayedProductScore = driver.findElement(By.xpath("//*[@id='summary']/div/div/div/div[2]/div[3]/div/span")).getText();
		System.out.println("Medisafe product score is :"+DisplayedProductScore);
		logger.log(LogStatus.PASS, "Displayed record of product score in dashboard page of Appscript application is: "+ScoreRecord);
		
		if(ScoreRecord.equals(DisplayedProductScore)){
			System.out.println("The product score is matching in dashboard and details page");
			logger.log(LogStatus.PASS, "The product score is: "+ScoreRecord+" in dashboard page and is matching to the product score in details page with value: "+DisplayedProductScore);
		}else{
			System.out.println("The product score is not matching in dashboard and details page");
			logger.log(LogStatus.PASS, "The product score is: "+ScoreRecord+" in dashboard page and is not matching to the product score in details page with value: "+DisplayedProductScore);
		}
		
		//verify the 4 tabs is present in the product details page
		WebElement DescriptionTab = driver.findElement(By.xpath("//*[@id='nav']/ul/li[1]/a"));
		if(DescriptionTab.isDisplayed()){
			logger.log(LogStatus.PASS, DescriptionTab.getText()+" Tab is found in details page of appscript");
		}else{
			logger.log(LogStatus.FAIL, DescriptionTab.getText()+" Tab is not found in details page of appscript" );
		}
		
		WebElement GalleryTab = driver.findElement(By.xpath("//*[@id='nav']/ul/li[2]/a"));
		if(GalleryTab.isDisplayed()){
			logger.log(LogStatus.PASS, GalleryTab.getText()+" Tab is found in details page of appscript");
		}else{
			logger.log(LogStatus.FAIL, GalleryTab.getText()+" Tab is not found in details page of appscript" );
		}
		
		WebElement RatingsTab = driver.findElement(By.xpath("//*[@id='nav']/ul/li[3]/a"));
		if(RatingsTab.isDisplayed()){
			logger.log(LogStatus.PASS, RatingsTab.getText()+" Tab is found in details page of appscript");
		}else{
			logger.log(LogStatus.FAIL, RatingsTab.getText()+" Tab is not found in details page of appscript" );
		}
		
		WebElement ScoreTab = driver.findElement(By.xpath("//*[@id='nav']/ul/li[4]/a"));
		if(ScoreTab.isDisplayed()){
			logger.log(LogStatus.PASS, ScoreTab.getText()+" Tab is found in details page of appscript");
		}else{
			logger.log(LogStatus.FAIL, ScoreTab.getText()+" Tab is not found in details page of appscript" );
		}
		
		screenshot = screenshot(driver, "Verify the tabs present in product details page of AppScript Application");
		logger.log(LogStatus.PASS, "Verify the tabs present in product details page of AppScript Application" + logger.addScreenCapture(screenshot));
		
		//click on ratings tab and give the review and ratings
		driver.findElement(By.xpath("//*[@id='nav']/ul/li[3]/a")).click();
		WebElement RateItButton = driver.findElement(By.xpath("//button[contains(@class,'rate-it')]"));
		RateItButton.click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='reviewForm']/div[1]/label")));
		int rowCount= driver.findElements(By.xpath("//*[@id='review-rating']/img")).size();
		System.out.println(rowCount+"row(s) found");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		for(int i=1; i<= rowCount;i++){
			boolean Ratings= driver.findElement(By.xpath("//*[@id='review-rating']/img["+i+"]")).isDisplayed();
				driver.findElement(By.xpath("//*[@id='review-rating']/img["+i+"]")).click();
		}
		
		WebElement Review = driver.findElement(By.xpath("//*[@id='review-text']"));
		Review.sendKeys("Good");
		
		screenshot = screenshot(driver, "Provide the ratings and review of the product selected in AppScript Application");
		logger.log(LogStatus.PASS, "Provide the ratings and review of the product selected in AppScript Application" + logger.addScreenCapture(screenshot));
		
		WebElement submitReviewButton = driver.findElement(By.xpath("//*[@id='reviewForm']/div[3]/button[2]"));
		submitReviewButton.click();
		
		report.endTest(logger);
		report.flush();
		
		driver.close();
	
	}
	
	public static String screenshot(WebDriver driver, String screenshotname) {

		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);

			String dest = System.getProperty("user.dir") + "/Screenshots/" + screenshotname + ".png";
			File destination = new File(dest);
			org.apache.commons.io.FileUtils.copyFile(source, destination);
			System.out.println("Screenshot Captured");
			return dest;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}

}


