package com.ims.lib;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.collections.CollectionUtils;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;
public class ApplicationUtility {
	public static UtilLib util = new UtilLib();
	public static ObjectDefinitionLibrary element = new ObjectDefinitionLibrary();
	private static WebDriver driver;
	static String screenshot;


	public ApplicationUtility(WebDriver driver) {
		super();
		this.driver = driver;
	}

	/******************************************************************************************** 
	 * @throws InterruptedException 
	 * * @author: Madhura
	 * @Function_Name : DNA_Login
	 * @Description : Initiate Browser and navigate to the URL with valid credentials
	 ********************************************************************************************/
	public static  void DNA_Login(ExtentReports report,ExtentTest logger) throws IOException, InterruptedException{
		try {
			driver = UtilLib.getDriver();
			driver.manage().deleteAllCookies();

			String UrlLaunch = util.getPropertiesValue("URL");
			String UN = util.getPropertiesValue("User_Name");
			String Password_property = util.getPropertiesValue("Password");

			driver.get(UrlLaunch);
			System.out.println(UrlLaunch);

			driver.findElement(By.xpath(element.UserName)).clear();
			driver.findElement(By.xpath(element.UserName)).sendKeys(util.getPropertiesValue("User_Name"));

			byte[] decodedBytes = Base64.decodeBase64(Password_property);
			driver.findElement(By.xpath(element.Password)).sendKeys(new String(decodedBytes));

			//			screenshot = UtilLib.screenshot(driver, "Login to DNA Application");
			//			logger.log(LogStatus.PASS, "Login to UAT DNA Application: "+UrlLaunch +" for CS users"+ logger.addScreenCapture(screenshot));

			driver.findElement(By.xpath(element.Login_Button)).click();

		} 

		catch (Error e) {
			e.printStackTrace();
			e.getMessage();

		}
	}
	/********************************************************************************************
	/********************************************************************************************
	 * @Function_Name : AppNavigation_CSUsers
	 * @Description :  To Check the application navigation for CS Users
	 ********************************************************************************************/
	public static boolean  Validation_CSUser(ExtentReports report,ExtentTest logger){
		WebDriverWait wait = new WebDriverWait(driver,150);
		try
		{
			/*Long LoginPageTime = (Long)((JavascriptExecutor)driver).executeScript(
		            "return performance.timing.loadEventEnd - performance.timing.navigationStart;");
			System.out.println("Time taken to load after logging into the application is: " + LoginPageTime+" milliseconds");
			logger.log(LogStatus.INFO,"Time taken to load after logging into the application is : "+LoginPageTime +" milliseconds");*/

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.ClientLoadedData)));

			DateFormat df = new SimpleDateFormat("dd.MMM.yyyy-HH.mm.ss");
			Date today = Calendar.getInstance().getTime();
			String runTime = df.format(today);
			System.out.println("Logged in after :"+runTime);

			//Click on 'Select' button
			String ClientSelectionPagescreenshot = util.screenshot(driver, "Navigate to the Client selection page");
			logger.log(LogStatus.PASS, "Login is successfull and CS user is navigated to the Client selection page" + logger.addScreenCapture(ClientSelectionPagescreenshot));

			driver.findElement(By.xpath(element.SelectButton)).click();

			//click on the state button for which the Dollar value is present
			Thread.sleep(1);
			try{
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.StateTable)));
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(element.Loading)));
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element.StateTable)));

				/*	Long loadtimeInvoicePage = (Long)((JavascriptExecutor)driver).executeScript(
			            "return performance.timing.loadEventEnd - performance.timing.navigationStart;");
				System.out.println("Time taken to load the Invoice page is: " + loadtimeInvoicePage+" milliseconds");
				logger.log(LogStatus.INFO,"Time taken to load the Invoice page is : "+loadtimeInvoicePage+" milliseconds");*/

				String InvoicePagescreenshot = util.screenshot(driver, "Navigate to the invoice page after clicking on select button fron Client selection page ");
				logger.log(LogStatus.PASS, "Navigated to invoice page after clicking on select button fron Client selection page" + logger.addScreenCapture(InvoicePagescreenshot));

				WebElement Statetable = driver.findElement(By.xpath(element.CompleteStatetable));
				List<WebElement> allrows = Statetable.findElements(By.tagName("tr"));
				List<WebElement> allcols = Statetable.findElements(By.tagName("td"));
				System.out.println("Number of rows in the table "+allrows.size());
				System.out.println("Number of columns in the table "+allcols.size());

				boolean flag = false;
				for(WebElement row: allrows){
					List<WebElement> Cells = row.findElements(By.tagName("td"));
					for(WebElement Cell:Cells){
						System.out.println(Cell.getText());
						if (Cell.getText().contains("California")){
							Cell.click();
							flag = true;
						}
					}
					if(flag == true){
						System.out.println(flag);
						break;
					}
				}

			}catch(Exception ex){
				ex.printStackTrace();
				ex.getMessage();
				String InvoicePageFailed = util.screenshot(driver, "Unable to Navigate to Invoice page");
				logger.log(LogStatus.FAIL, " Unable to Navigate to Invoice page " + logger.addScreenCapture(InvoicePageFailed));
				report.endTest(logger);
				driver.close();
				driver.quit();
			}

			//Click on the Verify Button of any Program for which Data is available	
			Thread.sleep(1);
			/*Long loadtimeDNACLDPage = (Long)((JavascriptExecutor)driver).executeScript(
		            "return performance.timing.loadEventEnd - performance.timing.navigationStart;"); 
			System.out.println("Time taken to load the DNA CLD page is: " + loadtimeDNACLDPage+" milliseconds");
			logger.log(LogStatus.INFO,"Time taken to load the DNA CLD page is :" +loadtimeDNACLDPage+" milliseconds");*/

			try{
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.VerifyButton)));
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(element.Loading)));
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element.VerifyButton)));

				String StateDescriptionScreenshot = util.screenshot(driver, "Navigated to Program description invoice page after clicking on the state <b> California </b> for which the Dollar value is present");
				logger.log(LogStatus.PASS, "Navigated to Program description invoice page after clicking on the state <b> California </b> for which the Dollar value is present" + logger.addScreenCapture(StateDescriptionScreenshot));

				driver.findElement(By.xpath(element.VerifyButton)).click();
			}catch(Exception exception){
				exception.printStackTrace();
				exception.getMessage();
				String VerifyButtonsFailed = util.screenshot(driver, "Unable to click on Verify button ");
				logger.log(LogStatus.FAIL, " Unable to click on Verify button " + logger.addScreenCapture(VerifyButtonsFailed));
				report.endTest(logger);
				driver.close();
				driver.quit();
			}

			Thread.sleep(1);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.DNACldTable)));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(element.Loading)));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element.DNACldTable)));

			String DNACLDNaviagtion = util.screenshot(driver, "Navigated to DNA CLD page after clicking on verify button");
			logger.log(LogStatus.PASS, "Navigated to DNA CLD page after clicking on verify button" + logger.addScreenCapture(DNACLDNaviagtion));

			boolean RejectButton = driver.findElement(By.xpath(element.RejectButtonStatePage)).isDisplayed();
			String RejectButtonValue = driver.findElement(By.xpath(element.RejectButtonStatePage)).getText();
			if(RejectButton==true){	
				logger.log(LogStatus.PASS, "Button present in DNA CLD page is: <b>"+RejectButtonValue+"</b>");
				System.out.println("Reject button is present in the page");
			}else{
				logger.log(LogStatus.FAIL, RejectButtonValue+"is not present in the DNA CLD page");
				System.out.println("Reject button is not present in the page");
			}

			boolean ApproveButton = driver.findElement(By.xpath(element.ApproveButtonStatePage)).isDisplayed();
			String ApproveButtonValue = driver.findElement(By.xpath(element.ApproveButtonStatePage)).getText();
			if(ApproveButton==true){
				logger.log(LogStatus.PASS, "Button present in DNA CLD page is: <b>"+ApproveButtonValue+"</b>");
				System.out.println("Approve button is present in the page");
			}else{
				logger.log(LogStatus.FAIL, ApproveButtonValue+"is not present in the DNA CLD page");
				System.out.println("Approve button is not present in the page");
			}
			driver.findElement(By.xpath(element.StateBackButton)).click();

			//Click on the Validation Button of any Program for which Data is available
			Thread.sleep(1);
			try{
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(element.Loading)));
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.ValidateButton)));
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element.ValidateButton)));
				driver.findElement(By.xpath(element.ValidateButton)).click();
			}catch(Exception UnableClickValidateButton){
				UnableClickValidateButton.printStackTrace();
				UnableClickValidateButton.getMessage();
				String VerifyButtonsFailed = util.screenshot(driver, "Unable to click on Validate button ");
				logger.log(LogStatus.FAIL, " Unable to click on Validate button " + logger.addScreenCapture(VerifyButtonsFailed));
				report.endTest(logger);
				driver.close();
				driver.quit();
			}

			//Verify the tabs present in validation page
			/*Long loadtimeValidationPage = (Long)((JavascriptExecutor)driver).executeScript(
		            "return performance.timing.loadEventEnd - performance.timing.navigationStart;"); 
			System.out.println("Time taken to load the All Validation page is: " + loadtimeValidationPage+" milliseconds");
			logger.log(LogStatus.INFO,"Time taken to load the All Validation page is " +loadtimeValidationPage+" milliseconds");*/

			Thread.sleep(1);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element.TableRowAllValidation)));

			String AllValidationPageScreenshot = util.screenshot(driver, "Navigated to All Validation after clicking on validate button");
			logger.log(LogStatus.PASS, "Navigated to All Validation after clicking on validate button" + logger.addScreenCapture(AllValidationPageScreenshot));

			boolean quantityTab = driver.findElement(By.xpath(element.QuantityTab)).isDisplayed();
			String QuantityTabValue = driver.findElement(By.xpath(element.QuantityTab)).getText();
			if(quantityTab==true){
				logger.log(LogStatus.PASS, "Tab present in All Validation page is: <b>"+QuantityTabValue+"</b>");
				System.out.println("The tab that is present in validation page is:"+QuantityTabValue);
			}else{
				logger.log(LogStatus.FAIL, QuantityTabValue+" is not present in All Validation page");
				System.out.println("The tab that is present in validation page is:"+QuantityTabValue+"======");
			}

			boolean Tab340BValidation = driver.findElement(By.xpath(element.Tab340B)).isDisplayed();
			String Tab340BValue = driver.findElement(By.xpath(element.Tab340B)).getText();
			if(Tab340BValidation==true){
				logger.log(LogStatus.PASS, "Tab present in All Validation page is: "+Tab340BValue);
				System.out.println("The tab that is present in validation page is:"+Tab340BValue);
			}else{
				logger.log(LogStatus.FAIL, "\""+Tab340BValue+ "\""+"is not present in All Validation page");
				System.out.println("The tab that is present in validation page is:"+Tab340BValue+"======");
			}

			boolean RebillsTab = driver.findElement(By.xpath(element.RebillsTab)).isDisplayed();
			String RebillsTabValue = driver.findElement(By.xpath(element.RebillsTab)).getText();
			if(RebillsTab==true){
				logger.log(LogStatus.PASS, "Tab present in All Validation page is: "+RebillsTabValue);
				System.out.println("The tab that is present in validation page is:"+RebillsTabValue);
			}else{
				logger.log(LogStatus.FAIL, "\""+RebillsTabValue+ "\""+"is not present in All Validation page");
				System.out.println("The tab that is present in validation page is:"+RebillsTabValue+"======");
			}

			Thread.sleep(1);
			driver.findElement(By.xpath(element.BackButtonValidationPage)).click();

			//Navigate back to Invoice page to logout from the DNA application
			Thread.sleep(1);
			try{
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.CSUserDetail)));
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(element.Loading)));
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element.CSUserDetail)));
				driver.findElement(By.xpath(element.CSUserDetail)).click();
				driver.findElement(By.xpath(element.LogoutButton)).click();
			}
			catch(Exception BackToInvoicePage){
				BackToInvoicePage.printStackTrace();
				BackToInvoicePage.getMessage();
				String NavigationInvoicePageFailed = util.screenshot(driver, "Failed to navigate back to invoice page from All validation page");
				logger.log(LogStatus.FAIL, "Failed to navigate back to invoice page from All validation page" + logger.addScreenCapture(NavigationInvoicePageFailed));
				report.endTest(logger);
				driver.close();
				driver.quit();
			}

			//Back to login page
			Thread.sleep(1);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.UserName)));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(element.Loading)));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element.UserName)));
			driver.findElement(By.xpath(element.UserName)).click();

			String  LoginPageSceenshot= util.screenshot(driver, "Navigate back to DNA login page");
			logger.log(LogStatus.PASS, "Navigate back to DNA login page" + logger.addScreenCapture(LoginPageSceenshot));

			report.endTest(logger);
			driver.close();
			driver.quit();

		}
		catch (NoSuchElementException e) {
			String Logscreenshot = util.screenshot(driver, "Unable to find Element Exception");
			logger.log(LogStatus.FAIL, "Unable to find Element Exception" + e.getMessage() + logger.addScreenCapture(screenshot));
			return false;
		}catch(Exception e ){
			e.printStackTrace();
			e.getMessage();
		}
		return false;
	}
	/******************************************************************************************** 
	 * * @author: Madhura
	 * @Function_Name : AdminUserValidation
	 * @Description : Verify Go to Admin and Username in the pages
	 ********************************************************************************************/
	public static boolean AdminUserValidation (ExtentReports report,ExtentTest logger){
		try
		{
			//Admin link should be displayed 
			String GoToAdminLink = driver.findElement(By.partialLinkText("Go To Admin")).getText();
			System.out.println("Link displayed in top right corner of DNA application is : "+GoToAdminLink);
			//			logger.log(LogStatus.PASS,"Link displayed in top right corner: <b>"+GoToAdminLink+"</b>");

			//Login User name should be displayed with only Logout option
			boolean Loginusername = driver.findElement(By.xpath(element.LoginUserName)).isDisplayed();
			String Login_UserName = driver.findElement(By.xpath(element.LoginUserName)).getText();
			if(Loginusername == true){
				//				logger.log(LogStatus.PASS,"LoggedIn User in DNA application is : '"+Login_UserName+"'");
				System.out.println("LoggedIn User in DNA application is: '"+Login_UserName);
			}else{
				//				logger.log(LogStatus.FAIL,"LoggedIn User in DNA application is: "+Login_UserName+"'");
				System.out.println("LoggedIn User in DNA application is: '"+Login_UserName +"'");
			}

			Actions actions = new Actions(driver);
			WebElement LoggedUser = driver.findElement(By.xpath(element.LoginUserName));
			System.out.println(LoggedUser.getText());
			actions.moveToElement(LoggedUser).perform();
			WebElement LogoutOption = driver.findElement(By.xpath(element.LogOutOption));
			String LogoutOptions = LogoutOption.getText();
			System.out.println(LogoutOptions);
			//		    logger.log(LogStatus.PASS,"Option present on mouse hover of the logged In user of DNA application is: "+LogoutOptions+"'");

			//		    String MouseHoverLoggedInUser = UtilLib.screenshot(driver, "Logged in User name should be displayed below the Go to Admin link with only Logout option");
			//			logger.log(LogStatus.PASS,"Logged in User name should be displayed below the Go to Admin link with only Logout option"+logger.addScreenCapture(MouseHoverLoggedInUser));
		}
		catch(Exception e){

		}
		return false;
	}
	/******************************************************************************************** 
	 * * @author: Madhura
	 * @throws IOException 
	 * @Function_Name : HomepageValidation
	 * @Description : Perform Home page validation 
	 ********************************************************************************************/
	public static boolean  HomepageValidation(ExtentReports report,ExtentTest logger) throws IOException{
		WebDriverWait wait = new WebDriverWait(driver,150);
		String xlFilePath=System.getProperty("user.dir")+"/DNA_TestData/TestData_DNA.xlsx";
		String sheet="DNA_UAT";
		String ClientNames = util.getCellData(xlFilePath, sheet,"ClientName",1);
		String StateNames = util.getCellData(xlFilePath, sheet, "StateName",1);
		String PrgmName = util.getCellData(xlFilePath, sheet,"Program Name", 1);
		String FollowUpDate = util.getCellData(xlFilePath, sheet,"Follow Up Date", 1);
		String FollowupValidValue = util.getCellData(xlFilePath, sheet,"Followup_ValidValue", 1);
		String FollowupStatus = util.getCellData(xlFilePath, sheet,"Followupstatus", 1);
		System.out.println("ClientNames======"+ClientNames);
		System.out.println("StateNames======="+StateNames);
		System.out.println("Program Name====="+PrgmName);
		System.out.println("Follow Up date====="+FollowUpDate);
		System.out.println("FollowupValidValue===="+FollowupValidValue);
		System.out.println("Follow up status====="+FollowupStatus);
		try
		{
			//Default client should be DNA in the client dropdown
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element.QuaterlyNewsletterContent)));

			boolean DefaultClientDropdown = driver.findElement(By.xpath(element.DefaultClient)).isEnabled();
			String DefaultClientDrpdown = driver.findElement(By.xpath(element.DefaultClient)).getText();
			if(DefaultClientDropdown == true){
				//				logger.log(LogStatus.PASS,"Default Client in client drop down is: <b>"+DefaultClientDrpdown+"</b>");
				System.out.println("Default Client in client drop down is: "+DefaultClientDrpdown);
			}else{
				//				logger.log(LogStatus.FAIL,"Default Client in client drop down is: <b>"+DefaultClientDrpdown+"</b>");
				System.out.println("Default Client in client drop down is: "+DefaultClientDrpdown);
			}

			boolean AdminStatus = ApplicationUtility.AdminUserValidation(report, logger);
			if(AdminStatus==true){
				System.out.println("Admin and User Name is found in the page");
			}else{
				System.out.println("Admin and User Name is not found in the page");
			}

			driver.findElement(By.xpath(element.QuaterContent)).click();

			//Check that the QUARTERS listed should be from 2010Q1 to 2016Q4

			//Default first Quarter should be selected and displayed above the newsletter
			boolean defaultQuater = driver.findElement(By.xpath(element.DefaultQuatersDisplayed)).isSelected();
			String DefaultQuaterSelected = driver.findElement(By.xpath(element.DefaultQuatersDisplayed)).getText();
			if(defaultQuater==true){
				//				logger.log(LogStatus.PASS,"Default Quarters selected in Client selection Page of DNA application is: "+DefaultQuaterSelected);
				System.out.println("The Default Quarter Selected is :"+DefaultQuaterSelected);
			}else{
				//				logger.log(LogStatus.FAIL,"Default Quarters selected in Client selection Page of DNA application is: "+DefaultQuaterSelected);
				System.out.println("The Default Quarter Selected is :"+DefaultQuaterSelected);
			}

			WebElement quaterList = driver.findElement(By.xpath(element.QuatersListed));
			List<WebElement> options = quaterList.findElements(By.tagName("li"));
			System.out.println(options.size());
			WebElement Quaterselements = driver.findElement(By.xpath(element.Q12015));

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true);",Quaterselements);
			for (int i = 0; i < options.size(); i++) {
				System.out.println(options.get(i).getText());
				//logger.log(LogStatus.PASS,"QUARTERS listed in client selection page of DNA application is: "+options.get(i).getText());
			}
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.Q12015)));

			WebElement element1 = driver.findElement(By.xpath(element.Q32012));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);",element1);
			for (int k = 0; k < options.size(); k++) { 
				System.out.println("Second list of visible elements: "+options.get(k).getText());
				//				logger.log(LogStatus.PASS,"QUARTERS listed in client selection page of DNA application is: "+options.get(k).getText());
			}
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.Q32012)));

			WebElement element2 = driver.findElement(By.xpath(element.Q12010));
			JavascriptExecutor j = (JavascriptExecutor) driver;
			j.executeScript("arguments[0].scrollIntoView(true);",element2);
			for (int m = 0; m < options.size(); m++) {
				System.out.println("Third list of visible elements: "+options.get(m).getText());
				//				logger.log(LogStatus.PASS,"QUARTERS listed in client selection page of DNA application is: "+options.get(m).getText());
			}
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.Q12010)));

			//Select button should be displayed and be enabled to click
			driver.findElement(By.xpath(element.SelectButton)).click();

			//Quarters page should be displayed with Quarters link, State list, selected Quarter, Go to Admin and Username

			//			 String QuatersPageNavigation = UtilLib.screenshot(driver, "CS users is successfully navigated to Quarters page");
			//			 logger.log(LogStatus.PASS,"CS users is successfully navigated to Quarters page"+logger.addScreenCapture(QuatersPageNavigation));

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.StateTable)));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(element.Loading)));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element.StateTable)));

			boolean AdminUserStatus = ApplicationUtility.AdminUserValidation(report, logger);

			boolean selectedQuaters = driver.findElement(By.xpath(element.QuarterPage_SelectedQuarter)).isDisplayed();
			String selectedQuater = driver.findElement(By.xpath(element.QuarterPage_SelectedQuarter)).getText();
			if(selectedQuaters==true){
				//				logger.log(LogStatus.PASS,"The Selected Quarter in Quarters page of DNA application is :" +selectedQuater);
				System.out.println("The Selected Quarter in Quaters page of DNA application is :" +selectedQuater);
			}else{
				//				logger.log(LogStatus.FAIL,"The Selected Quarter in Quarters page of DNA application is :" +selectedQuater);
				System.out.println("The Selected Quarter in Quaters page of DNA application is :" +selectedQuater);
			}

			boolean QuatersLink = driver.findElement(By.xpath(element.QuarterPage_Quaterslink)).isDisplayed();
			String Quaterslink = driver.findElement(By.xpath(element.QuarterPage_Quaterslink)).getText();
			if(QuatersLink==true){
				//				logger.log(LogStatus.PASS,"Quarters link is present in Quarters page of DNA application :" +selectedQuater);
				System.out.println("Quarters link is present in Quarters page of DNA application : "+Quaterslink);
			}else{
				//				logger.log(LogStatus.FAIL,"Quarters link is present in Quarters page of DNA application :" +selectedQuater);
				System.out.println("Quarters link is present in Quaters page of DNA application : "+Quaterslink);
			}


			driver.findElement(By.xpath(element.QuarterPage_Quaterslink)).click();

			//Select a client from client drop down
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element.QuaterlyNewsletterContent)));
			driver.findElement(By.xpath(element.HomePage_clientDropDown)).click();

			WebElement clientDropdown = driver.findElement(By.xpath(element.HomePage_clientDropdownList));
			List<WebElement> clientDropdownList = clientDropdown.findElements(By.tagName("li"));
			System.out.println(clientDropdownList.size());
			WebElement clientSelected = driver.findElement(By.xpath("//ul/li[text()='"+ClientNames+"']"));

			JavascriptExecutor clientdrpdwn = (JavascriptExecutor) driver;
			clientdrpdwn.executeScript("arguments[0].scrollIntoView(true);",clientSelected);
			for (int i = 0; i < clientDropdownList.size(); i++) {
				System.out.println(clientDropdownList.get(i).getText());
				System.out.println("--");
			}
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul/li[text()='"+ClientNames+"']")));
			driver.findElement(By.xpath("//ul/li[text()='"+ClientNames+"']")).click();       

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.ClientLoadedData)));
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='q2']/div/div/div[2]/div/div[6]/div/div[6]/div/div/span/p[3]")));
			//Thread.sleep(100);
			driver.findElement(By.xpath(element.SelectButton)).click();

			//Navigate to my invoice page after clicking select button from client selection page
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.StateTable)));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(element.Loading)));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element.StateTable)));

			// Select the state - California
			driver.findElement(By.cssSelector("#"+StateNames+">td")).click();
			String StateNameSelected = driver.findElement(By.cssSelector("#"+StateNames+">td")).getText();
			System.out.println("State Name selected is:"+StateNameSelected);

			//Client displayed in my invoice page 
			String Quarters_clientSelected=driver.findElement(By.xpath(element.QuarterPage_ClientSelected)).getText();
			System.out.println("Quaters client selected: "+Quarters_clientSelected);
			//			logger.log(LogStatus.PASS,"State Name selected in My invoice page for the Client :"+Quarters_clientSelected+"is:" +StateNameSelected);
			//			
			//			String InvoicePagescreenshot = util.screenshot(driver, "Page displayed after selecting the State in My Invoice page");
			//			logger.log(LogStatus.PASS, "Page displayed after selecting the State in My Invoice page" + logger.addScreenCapture(InvoicePagescreenshot));

			Thread.sleep(10000);
			try{
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element.Quarters_AllTab)));
				driver.findElement(By.partialLinkText("AL")).click();
				List<WebElement> prgm = driver.findElements(By.xpath(element.Quarters_ProgramContent));
				System.out.println("prgm size:" +prgm.size());
				Thread.sleep(1000);
				for(int t=1; t<=prgm.size(); t++){
					String a = driver.findElement(By.xpath("(//*[@id='myTabContent']/..//div/*[contains(@id,'pgm')]/div[1])["+t+"]/div[1]/div[1]/h4")).getText();
					System.out.println("==========="+a);
					if(a.equalsIgnoreCase(PrgmName)){
						driver.findElement(By.xpath("(//*[@id='myTabContent']/..//div/*[contains(@id,'pgm')]/div[1])["+t+"]/a/div")).click();
						System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
						break;
					}
				}
			}catch(Exception errorpage){
				errorpage.printStackTrace();
				errorpage.getMessage();
			}

			/***************************All validation page****************************/
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(element.Loading)));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.AllValidation_Validation_AllTable)));

			//click on Qunatity tab
			driver.findElement(By.xpath(element.AllValidation_QunatityTab)).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.AllValidation_VisibilityBoxQuanity)));

			Thread.sleep(1000);
			String Allvalidation_QtyTab = driver.findElement(By.xpath(element.AllValidation_QunatityTab)).getText();
			System.out.println("All Quantity tab value is:"+Allvalidation_QtyTab);
			

			//previous cell in webtable
			/*WebElement previouscell = driver.findElement(By.xpath("//*[@id='validation-quantity']/div[1]/div/div[1]/div/table/tbody/tr[1]/td[14]"));
			JavascriptExecutor WebtableJSE = (JavascriptExecutor) driver;
			WebtableJSE.executeScript("arguments[0].scrollIntoView(true);",previouscell);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='validation-quantity']/div[1]/div/div[1]/div/table/tbody/tr[1]/td[14]")));
			String previoscell = driver.findElement(By.xpath("//*[@id='validation-quantity']/div[1]/div/div[1]/div/table/tbody/tr[1]/td[14]")).getText();
			System.out.println("Previous cell ===="+previoscell);*/

			// enter values in Follow up dispute with valid values
			WebElement currentCell = driver.findElement(By.xpath("//*[@id='validation-quantity']/div[1]/div/div[1]/div/table/tbody/tr[2]/td[11]"));
			JavascriptExecutor WebtableJSe = (JavascriptExecutor) driver;
			WebtableJSe.executeScript("arguments[0].scrollIntoView(true);",currentCell);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='validation-quantity']/div[1]/div/div[1]/div/table/tbody/tr[2]/td[11]")));
			String currentcell = driver.findElement(By.xpath("//*[@id='validation-quantity']/div[1]/div/div[1]/div/table/tbody/tr[2]/td[11]")).getText();
			System.out.println("Current cell ===="+currentcell);
			
			Actions ele=new Actions(driver);
			WebElement cellvalue = driver.findElement(By.xpath("//*[@id='validation-quantity']/div[1]/div/div[1]/div/table/tbody/tr[2]/td[11]"));
			ele.doubleClick(cellvalue).sendKeys(FollowupValidValue).perform();
			
			// enter values in Follow up reminder 
			Thread.sleep(100);
			Actions eleNext=new Actions(driver);
			WebElement cellNext = driver.findElement(By.xpath("//*[@id='validation-quantity']/div[1]/div/div[1]/div/table/tbody/tr[2]/td[12]"));
			eleNext.doubleClick(cellNext).sendKeys(FollowUpDate).perform();
			
			Thread.sleep(100);
			WebElement FollowUpStatus = driver.findElement(By.xpath("//*[@id='validation-quantity']/div[1]/div/div[1]/div/table/tbody/tr[2]/td[13]"));
			FollowUpStatus.click();
			String followupstatus = driver.findElement(By.xpath("//*[@id='validation-quantity']/div[1]/div/div[1]/div/table/tbody/tr[2]/td[13]")).getText();
			System.out.println(" The Follow Up status is:" +followupstatus);
			if(followupstatus.equalsIgnoreCase(FollowupStatus)){
				System.out.println("The follow UP status from cell is:" +followupstatus+"and the follow up status from the test data is:"+FollowupStatus);
			}else{
				System.out.println("The follow UP status from cell is:"+followupstatus+"and the follow up status from the test data is:"+FollowupStatus);
			}
			
			//enter values in Follow up dispute with Invalid values
			Actions elementInvalid=new Actions(driver);
			WebElement CellInvalidValue = driver.findElement(By.xpath("//*[@id='validation-quantity']/div[1]/div/div[1]/div/table/tbody/tr[2]/td[11]"));
			elementInvalid.doubleClick(CellInvalidValue).sendKeys("@").perform();
			
			WebElement followReminder = driver.findElement(By.xpath("//*[@id='validation-quantity']/div[1]/div/div[1]/div/table/tbody/tr[2]/td[12]"));
			followReminder.click();
			boolean FollowUpReminder = driver.findElement(By.xpath("//*[@id='validation-quantity']/div[1]/div/div[1]/div/table/tbody/tr[2]/td[12]")).isEnabled();
			if(FollowUpReminder==false){
				System.out.println("Follow up reminder is disbaled");
			}else{
				System.out.println(" Follow up reminder is not disabled");
			}
			
		}
		catch(Exception e){

		}
		return false;
	}
	
}
