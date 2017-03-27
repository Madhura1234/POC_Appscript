package com.ims.lib;

public class ObjectDefinitionLibrary {
	public String UserName = "//*[@id='dnalogin']/div/div[1]/div[3]/input";
	public String Password = "//*[@id='password-field']";
	public String Login_Button = "//*[@id='dnalogin']/div/div[1]/div[7]/input";
	public String ClientLoadedData = "//*[@id='q2']/div/div/div[2]/div/div[3]/div[1]/h2/span[1]";
	public String SelectButton ="//div[1]/footer/div/div/button[2]";
	public String StateTable = "//div[@class='invoiceLeftPan']/table/tbody/tr";
	public String CompleteStatetable = "//div[@class='invoiceLeftPan']/table/tbody";
	public String ForReviewTab = "//*[@id='myTab']/li[1]/a";
	public String VerifyButton = "(//*[@id='pgm35']/div[1]/div[2]/div[9]/button)[2]";
	public String StateBackButton = "//nav/div/div[1]/a/p";
	public String RejectButtonStatePage = "//div/div/a/button";
	public String ApproveButtonStatePage = "//body/footer/div/div/button";
	public String Loading = "//body/span/span/div[3][text()='Loading...']";
	public String ValidateButton = "(//*[@id='pgm35']/div[1]/a/div/div[1]/div)[2]";
	public String QuantityTab = "//*[@id='navbar']/ul/li[2]/div/a/div/div[1]";
	public String TableRowAllValidation = "//*[@id='valid-all-grid']/div[1]/div/div[1]/div/table/tbody/tr[1]/td[1]";
	public String Tab340B = "//*[@id='navbar']/ul/li[3]/div/a/div/div[1]";
	public String RebillsTab = "//*[@id='navbar']/ul/li[4]/div/a/div/div[1]";
	public String DNACldTable = "//*[@id='dmgt']/tbody/tr[1]/td[1]";
	public String BackButtonValidationPage = "//*[@id='validationDiv']/nav/div/div[1]/span/a/span";
	public String CSUserDetail = "//*[@id='navbar']/ul[1]/li/a";
	public String LogoutButton = "//*[@id='navbar']/ul[1]/li/ul/li[3]/a/div[2]";
	public String QuaterValue = "//*[@id='quarterContent']/div[1]/div/div[2]/p[1]";
	
	/************************Home Page/Client Selection Page***************/
	public String DefaultClient = "//*[@id='speed-button']/span[2]";
	public String QuaterlyNewsletterContent = "//*[@id='q2']/div/div/div[2]/div/div[6]/div/div[6]/div/div";
	public String LoginUserName = "//*[@id='navbar']/ul[1]/li/a/p";
	public String LogOutOption = "//*[@id='navbar']/ul[1]/li/ul/li/a/div[2]";
	public String QuatersListed = "//div[1]/div/div/div[1]/div[4]/div[2]/ul";
	public String QuaterContent = "//*[@id='quarterContent']/div[2]";
	public String Q12015 = "//ul/li[@id='qtr20151']";
	public String Q32012 = "//ul/li[@id='qtr20123']";
	public String Q12010 = "//ul/li[@id='qtr20101']";
	public String DefaultQuatersDisplayed="//*[@id='20164']";
	public String HomePage_clientDropDown = "//*[@id='speed-button']/span[1]";
	public String HomePage_clientDropdownList = "//body/div[5]/div/ul";

	
	/******************************Quarters Page************************************/
	public String QuarterPage_SelectedQuarter = "//div[2]/div[1]/div/div[2]/b";
	public String QuarterPage_Quaterslink ="//div[2]/div[1]/a/p";
	public String QuarterPage_ClientSelected = "//body/nav/div[2]/div[1]/div[3]";
	public String QuartersPage_ValidateButton ="(//*[@id='pgm35']/div[1]/../div[1]/a/div[1]/div[1])[2]";
	public String QuartersPage_AllTab = "//*[@id='myTab']/li[2]/a";
	public String QuartersPage_ProgramContent = "//*[@id='myTabContent']/..//div/*[contains(@id,'pgm')]/div[1]";
	public String QuartersPage_StateTable = "//div/div[1]/div/div[1]/table/tbody/tr/td[1]";
	
	/*********************All Validation Pages*****************************/
	public String AllValidation_QunatityTab = "//*[@id='navbar']/ul/li[2]/div/a/div/div[1]";
	public String AllValidation_VisibilityBoxQuanity = "//*[@id='validationDiv']/div[1]/div[2]/div[3]/div/div[1]/div[4]/div/div[1]";
	public String AllValidation_Validation_AllTable = "//*[@id='valid-all-grid']/div[1]/div/div[1]/div/table/tbody";
	public String AllValidation_QtyTableCellUpdate = "//*[@id='validation-quantity']/div[1]/div/div[1]/div/table/tbody/tr[1]/td[11]";
	public String AllValidation_QtyFollowUpReminder = "//*[@id='validation-quantity']/div[1]/div/div[1]/div/table/tbody/tr[1]/td[12]";
	public String AllValidation_QtyFollowUpStatus = "//*[@id='validation-quantity']/div[1]/div/div[1]/div/table/tbody/tr[1]/td[13]";
	public String AllValidation_DNACld = "//*[@id='validationDiv']/div[1]/div/div[1]/ul/li[1]/a";
	public String AllValidation_MyCld = "//*[@id='validationDiv']/div[1]/div/div[1]/ul/li[2]/a";
	public String AllValidation_TotalRebatesValue="//*[@id='validationDiv']/div[1]/div/div[2]/div[1]/h4/span";
}
