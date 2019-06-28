package com.majesco.itaf.main;

import io.restassured.response.Response;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.App;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import com.jayway.jsonpath.JsonPath;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.majesco.itaf.rest.service.RestService;
import com.majesco.itaf.rest.utils.CommonUtils;
import com.majesco.itaf.rest.utils.JsonUtility;
import com.majesco.itaf.util.ArchiveFiles;
import com.majesco.itaf.util.BillingProduct;
import com.majesco.itaf.util.CalendarSnippet;
import com.majesco.itaf.util.CommonExpectedConditions;
import com.majesco.itaf.util.Constants;
import com.majesco.itaf.util.ExcelUtility;
import com.majesco.itaf.util.GenerateOutputXML;
import com.majesco.itaf.util.InboundFileTransfer;
import com.majesco.itaf.util.JDBCConnection;
import com.majesco.itaf.util.Jacob;
import com.majesco.itaf.util.OutboundTransferFiles;
import com.majesco.itaf.util.PDFComparisonUtil;
import com.majesco.itaf.util.WaitTool;
import com.majesco.itaf.util.XmlComparisonUtil;
import com.majesco.itaf.verification.WebVerification;
import com.majesco.itaf.webservice.WebService;

public class WebHelper {

	final static Logger log = Logger.getLogger(WebHelper.class.getName());

	public static String description = null;
	public static Boolean faultstring = false;
	public static Boolean success = false;
	public static Date frmDate = null;
	public static File file;
	public static File dashboardfile;
	public static Wait<WebDriver> wait;
	public static String TIFilePath = "";
	public static WebDriver currentdriver;
	public static Boolean isDynamicNumFound = false;
	public static DataFormat format = null;
	public static String Config_endComparison = null;
	public static HashMap<String, Integer> structureHeader = new HashMap<>();
	public static HashMap<String, Integer> valuesHeader = new HashMap<>();
	public static String columnName;
	public static int loopRow = 1;// Added for Action Loop
	public static int loopvalueendIndex = 0;
	public static Row loopexpectedRow = null;
	public static int loginCnt = 0;// Meghana
	public static String inputValue = null;
	// Meghna--For UI Validation--01/12/2017
	public static Cell transactionType = null;
	public static Boolean webserviceFailed = false;
	public static String ErrDescription = null;
	public static int fieldVerFailCount = 0;
	public static PrintStream print = null;
	public static int screenshotnum = 1;
	// Mrinmayee 14-05-2019
	public static String cfileName = "";
	public static String control;
	public static Cell testcaseID = null;
	public static String sikscreen = null;
	public static Boolean isIntialized = false;
	public static Robot robot;
	public static BufferedImage image;
	public static String ActualValue = null;
	public static String ExpectedValue = null;
	public static String month;
	public static String responseXml = null;
	public static String request_xml;
	public static Boolean findtablefound = false;
	public static String OutPutFormCode = null;
	public static String BatchNo = null;
	public static String AccountNo = null;
	public static String BrokerNo = null;
	public static String PolicyNo = null;
	public static String OutPutForm_XML = null;
	public static String WebServiceResponse = null;
	public static boolean objfound = false;
	public static String FailedResponseTagValue = null;
	public static Boolean nullvalue = false;
	public static Boolean failed = false;
	public static String column_Name;
	public static String pathtoNode;
	public static String wsdl_url;
	public static String request_url;
	static int i = 0;
	public static WebElement webElementForROBOT;// Meghana
	public static String group_no;
	public static String job_status;
	public static String account_no;
	// ***Mandar-- for Freeze functionality***
	public static String expected_status;
	public static Cell cycleDate_Values2 = null;
	public static String xfl_filename; // Meghna-FlatFile--04/12/2017
	public static String file_cycledate;
	public static String local_path;
	public static String remote_path_in;
	public static String remote_path_out;
	public static String file_to_be_converted;
	public static String extension;
	public static String archive = "";// Meghna-FlatFile--04/12/2017
	public static String uniqueFFNo = "";// for StateFarm 10/05/19
	public static String reportFilePath = "";// for StateFarm 10/05/19

	public static String validateTag = null;
	public static String validationMsg = null;
	public static Cell ctrlValue1Cell = null;
	public static Cell ctrlValue2Cell = null;
	public static String file_names; // ***Mandar-- for Copy Flat File***
	public static String destination_folder;
	public static String file_name;
	public static String restErrorResDesc = "";
	public static String oasAuthType = "", queryParam = "", pathParam = "", readFromResponse = "", updateReqBody = "";

	static List<String> columns = new ArrayList<String>();
	static List<List<String>> columnsData = new ArrayList<List<String>>();

	public static String User_ID, User_ID1, User_ID2, User_ID3, User_ID4, User_ID5, User_ID6, User_ID7, User_ID8, User_ID9, User_ID10;
	// ***Mandar-- For Update_Query 13/02/2018
	public static int Module_No, User_No, Allocated_Count = 0;
	public static List<String> UserList = new ArrayList<String>();
	public static String LocationNo;// Varsha For Apex_Archive

	private static String restUrl, requestMethod, contentType, requestJson;// Restful
	private static boolean validateJson = false;// Restful
	public static String restResponse = null;// Amol G -- Restful

	static ITAFWebDriver webDriver = ITAFWebDriver.getInstance();
	static MainController controller = ObjectFactory.getMainController();
	static {
		if (Config.verificationResultPath != null) {
			file = new File(Config.verificationResultPath);
		}
		if (Config.dashboardResultPath != null) {
			dashboardfile = new File(Config.dashboardResultPath);
		}
	}

	public static String doAction(String FilePath, Row rowValues, String testCase, String imageType, String controlType, String controlId,
			String controlName, String ctrlValue, String ctrlValue1, String ctrlValue2, String wscycledate, String logicalName, String action,
			WebElement webElement, Boolean Results, Sheet strucSheet, Sheet valSheet, int rowIndex, int rowcount, String rowNo, String colNo,
			String operationType, String cycleDate, String TransactionType) throws WebDriverException, IOException, Exception {

		// Meghna- Case Screenshot - Declared the variables here to use them for
		// NC and Screenshot

		String cdate, clocation;
		List<WebElement> WebElementList = null;
		String currentValue = null;
		String uniqueNumber = "";
		WebVerification.isFromVerification = false;
		Constants.ControlTypeEnum controlTypeEnum = Constants.ControlTypeEnum.valueOf(controlType);
		Constants.ControlTypeEnum actionName = Constants.ControlTypeEnum.valueOf(action.toString());
		String DestinationFlatFile = null;// Mandar for Flatfile
		String SourceFlatFile = null;// Mandar for Flatfile

		currentdriver = Automation.driver;
		sikscreen = Config.SikuliScr;
		if (controlType.contains("Robot") && !isIntialized) {
			log.info("In method doaction debug1");
			robot = new Robot();
			isIntialized = true;
		}
		switch (controlTypeEnum)

		{

			case JSScript :
				((JavascriptExecutor) currentdriver).executeScript(controlName, ctrlValue);
				break;

			case WaitFor :

				Thread.sleep(Integer.parseInt(controlName) * 1000);
				log.info("Static Wait applied");
				break;

			case Radio :
				switch (actionName) {
					case I :

						if (ITAFWebDriver.isClaimsApplication()) {
							// Mayur_Claims for release bulk
							if (ctrlValue.equalsIgnoreCase("Y") || !ctrlValue.equalsIgnoreCase("")) {
								uniqueNumber = WebHelperClaims.ReadFromExcel(ctrlValue);
								if (ctrlValue.equalsIgnoreCase("Y") && (logicalName.contains("ReleaseClaimInputCheckBox"))) {
									try {
										WebDriverWait WaitForPageLoad = new WebDriverWait(Automation.driver, 7);
										WebElement webElement2 = WaitForPageLoad
												.until(ExpectedConditions.elementToBeClickable(By
														.xpath(controlName
																+ "[contains(text(),'"
																+ uniqueNumber
																+ "')]//ancestor::div[contains(@id,'OpenBatchData')]//following::td[@data-colid]//input[@type='checkbox']")));
										System.out.println(webElement2);
										Thread.sleep(1000);
										((JavascriptExecutor) WebHelper.currentdriver).executeScript("arguments[0].scrollIntoView();", webElement2);
										Thread.sleep(1000);
										webElement2.click();
									} catch (Exception e) {
										log.error(e.getMessage(), e);
										WebElement webElement1 = WebHelper.wait
												.until(ExpectedConditions.elementToBeClickable(By
														.xpath("//div[@id='mainRegion']/div[@class='page-region']//div[@data-name='tabExpPayDetl_tab']//li[@class='last']/a")));
										((JavascriptExecutor) WebHelper.currentdriver).executeScript("arguments[0].scrollIntoView();", webElement1);
										webElement1.click();
										WebElement webElement2 = WebHelper.wait
												.until(ExpectedConditions.elementToBeClickable(By
														.xpath(controlName
																+ "[contains(text(),'"
																+ uniqueNumber
																+ "')]//ancestor::div[contains(@id,'OpenBatchData')]//following::td[@data-colid]//input[@type='checkbox']")));
										System.out.println(webElement2);
										Thread.sleep(1000);
										((JavascriptExecutor) WebHelper.currentdriver).executeScript("arguments[0].scrollIntoView();", webElement2);
										Thread.sleep(1000);
										webElement2.click();
									}
								} else if (ctrlValue.equalsIgnoreCase("Y") && (logicalName.contains("DeleteClaimBulkPayment"))) {
									try {
										WebDriverWait WaitForPageLoad = new WebDriverWait(Automation.driver, 7);
										WebElement webElement2 = WaitForPageLoad
												.until(ExpectedConditions.elementToBeClickable(By
														.xpath(controlName
																+ "[contains(text(),'"
																+ uniqueNumber
																+ "')]//ancestor::div[contains(@id,'OpenBatchData')]//following::td[@data-colid]//img[contains(@src,'delete')]")));
										System.out.println(webElement2);
										Thread.sleep(1000);
										((JavascriptExecutor) WebHelper.currentdriver).executeScript("arguments[0].scrollIntoView();", webElement2);
										Thread.sleep(1000);
										webElement2.click();
									} catch (Exception e) {
										log.error(e.getMessage(), e);
										WebElement webElement1 = WebHelper.wait
												.until(ExpectedConditions.elementToBeClickable(By
														.xpath("//div[@id='mainRegion']/div[@class='page-region']//div[@data-name='tabExpPayDetl_tab']//li[@class='last']/a")));
										((JavascriptExecutor) WebHelper.currentdriver).executeScript("arguments[0].scrollIntoView();", webElement1);
										webElement1.click();
										WebElement webElement2 = WebHelper.wait
												.until(ExpectedConditions.elementToBeClickable(By
														.xpath(controlName
																+ "[contains(text(),'"
																+ uniqueNumber
																+ "')]//ancestor::div[contains(@id,'OpenBatchData')]//following::td[@data-colid]//img[contains(@src,'delete')]")));
										System.out.println(webElement2);
										Thread.sleep(1000);
										((JavascriptExecutor) WebHelper.currentdriver).executeScript("arguments[0].scrollIntoView();", webElement2);
										Thread.sleep(1000);
										webElement2.click();
									}
								}
							}

						} else {
							controlName = controlName.replace("]", "");
							controlName = controlName + " and @value='" + ctrlValue + "']";
							log.info("controlName is:" + controlName);
							webElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(controlName)));
							if (!webElement.isSelected()) {
								Thread.sleep(1000);
								((JavascriptExecutor) currentdriver).executeScript("arguments[0].click();", webElement);
								Thread.sleep(1000);
							}
						}

						break;

					case NC :
						if (!webElement.isSelected()) {
							webElement.click();
						}
						break;
					case V :
						if (webElement.isSelected()) {
							currentValue = webElement.getAttribute(controlName.toString());
						}
						break;
					case F :
						if (webElement != null) {
							currentValue = "Y";
						}
						break;
					default :
						break;
				}
				break;

			case WebLink :
			case CloseWindow :// added this Case to bypass page loading after
				// clicking the event
				switch (actionName) {
					case Read :
						log.info("for read transaction is: " + TransactionType);
						uniqueNumber = WebHelperBilling.ReadFromExcel(ctrlValue);
						WebElementList = WebHelperUtil.getElementsByType(controlId, controlName, controlType, imageType, uniqueNumber);
						webElement = WebHelperUtil.GetControlByIndex("", WebElementList, controlId, controlName, controlType, uniqueNumber);
						webElement.click();
						break;
					case Write :
						log.info("for write transaction is: " + TransactionType);
						WebHelperUtil.writeToExcel(ctrlValue, webElement, controlId, controlType, controlName, rowNo, colNo);
						break;
					case I :
						if (controlId.equalsIgnoreCase("LinkValue")) {
							webElement.click();
						} else {
							if (ctrlValue.equalsIgnoreCase("Y") || ctrlValue.equalsIgnoreCase("Yes") || !(ctrlValue.trim().equalsIgnoreCase(""))) {
								if (Automation.browserType.toString().toUpperCase().contains("INTERNETEXPLORER")) {
									((JavascriptExecutor) currentdriver).executeScript("arguments[0].click();", webElement);
								} else {
									webElement.click();
								}
							} else if (ctrlValue.equalsIgnoreCase("") || StringUtils.isEmpty(ctrlValue)) {
								break;
							}
						}

						break;
					case NC :
					case NoException :
						try {
							((JavascriptExecutor) currentdriver).executeScript("arguments[0].click();", webElement);
						} catch (Exception ex) {
							log.error("Error before sleeping for 30 seconds");
							Thread.sleep(30000);
							((JavascriptExecutor) currentdriver).executeScript("arguments[0].click();", webElement);
						}

						break;
					default :
						break;
				}
				break;

			case WaitForJS :
				WebHelperUtil.waitForCondition();
				break;

			case ListBox :
			case WebList :
				switch (actionName) {
					case Read :
						uniqueNumber = WebHelperBilling.ReadFromExcel(ctrlValue);
						new Select(webElement).selectByVisibleText(uniqueNumber);
						break;
					case Write :
						WebHelperUtil.writeToExcel(ctrlValue, webElement, controlId, controlType, controlName, rowNo, colNo);
						break;
					case I :
						Thread.sleep(3000);
						if (ctrlValue.startsWith("#") || ctrlValue.endsWith("#"))
						// Added By Dharmendra to handle the dropdown value
						// having space
						// at the START or at the END.
						{
							ctrlValue = ctrlValue.replace("#", " ");
							log.info("ctrlValue is : " + ctrlValue);
						}
						ExpectedCondition<Boolean> isTextPresent = CommonExpectedConditions.textToBePresentInElement(webElement, ctrlValue);
						if (isTextPresent != null) {
							if (webElement != null) {
								if (ctrlValue.startsWith(" ") || ctrlValue.endsWith(" ")) {
									ctrlValue = ctrlValue.replace(" ", "");
									new Select(webElement).selectByVisibleText(ctrlValue);
								} else {
									Select dropdown = new Select(webElement);
									Thread.sleep(1000);
									// log.info("DropDown selected");
									log.info("DropDown selected : CtrlValue is :" + ctrlValue);// Mandar
									if (logicalName.equalsIgnoreCase("PolicyStatus")) // devishree
									{
										dropdown.selectByValue(ctrlValue);
									} else {
										dropdown.selectByVisibleText(ctrlValue);
									}
									Thread.sleep(1000);

								}
							}
						}

						break;
					case PT :// Varsha
						if (ctrlValue.startsWith("#") || ctrlValue.endsWith("#")) {
							ctrlValue = ctrlValue.replace("#", " ");
							log.info("ctrlValue is : " + ctrlValue);
						}
						String partialText = ctrlValue;
						List<WebElement> list = currentdriver.findElements(By.tagName("option"));
						Iterator<WebElement> i = list.iterator();
						while (i.hasNext()) {
							WebElement wel = i.next();
							if (wel.getText().contains(partialText)) {
								wel.click();
							}
						}

						Thread.sleep(1000);

						break;
					case V :
						if (!ctrlValue.contains(",")) {
							currentValue = new Select(webElement).getFirstSelectedOption().getText();
							if (StringUtils.isEmpty(currentValue)) {
								currentValue = new Select(webElement).getFirstSelectedOption().getAttribute("value");
							}

							break;
						} else {
							currentValue = new String();
							List<WebElement> currentValues = new ArrayList<WebElement>();
							currentValues = new Select(webElement).getOptions();

							for (int j = 0; j < currentValues.size(); j++) {
								if (j + 1 == currentValues.size())
									currentValue = currentValue.concat(currentValues.get(j).getText());
								else {
									currentValue = currentValue.concat(currentValues.get(j).getText() + ",");
								}
							}
							break;
						}
					default :
						break;
				}
				break;

			// New code for AJAX Dropdown with dojo
			case AjaxWebList :
				switch (actionName) {
					case I :
						webElement.click();
						break;
					case VA :
						Thread.sleep(20000);
						currentValue = new String();
						List<WebElement> currentValues = new ArrayList<WebElement>();
						currentValues = currentdriver.findElements(By.xpath(controlName));

						for (int j = 0; j < currentValues.size(); j++) {
							if (j + 1 == currentValues.size())
								currentValue = currentValue.concat(currentValues.get(j).getText());
							else {
								currentValue = currentValue.concat(currentValues.get(j).getText() + ",");
							}
						}
						break;
					default :
						break;

				}
				break;
			// Meghna : R10.9 - Case Refresh to refresh login page in case of
			// Blank
			// screen//
			case Refresh :
				log.info("Refreshing login page");
				Automation.refreshPage(controlName);

			case Browser :
				Set<String> handlers = null;
				handlers = currentdriver.getWindowHandles();
				for (String handler : handlers) {
					currentdriver = currentdriver.switchTo().window(handler);

					// TM-19/01/2015: Changed following comparison from
					// equalsIgnoreCase to contains
					if (currentdriver.getTitle().contains(controlName)) {
						log.info("Focus on window with title: " + currentdriver.getTitle());
						break;
					}
				}
				break;

			case URL :
				switch (actionName) {
					case I :
						currentdriver.navigate().to(ctrlValue);
						break;
					case NC :
						currentdriver.navigate().to(controlName);
						break;
					default :
						break;
				}
				break;

			case Menu :
				webElement.click();
				break;

			case Alert :
				switch (actionName) {
					case V :
						Alert alert = currentdriver.switchTo().alert();
						if (alert != null) {
							currentValue = alert.getText();
							log.info("Alert found on the web page");
							log.info(currentValue);
							alert.accept();
						}
						break;
					case NC :
						Alert alert1 = currentdriver.switchTo().alert();
						if (alert1 != null) {
							alert1.accept();
							Thread.sleep(2000);
						}
						break;
					default :
						break;
				}
				break;

			case WebImage :
				webElement.sendKeys(Keys.TAB);
				webElement.click();
				Thread.sleep(5000);
				for (int Seconds = 0; Seconds <= Integer.parseInt(Config.timeOut); Seconds++) {
					if (!((currentdriver.getWindowHandles().size()) > 1)) {
						webElement.click();
						Thread.sleep(5000);
					} else {
						break;
					}
				}
				break;

			case ActionClick :
				Actions builderClick = new Actions(currentdriver);
				Action clickAction = builderClick.moveToElement(webElement).clickAndHold().release().build();
				clickAction.perform();
				break;

			case ActionDoubleClick :
				Actions builderdoubleClick = new Actions(currentdriver);
				builderdoubleClick.doubleClick(webElement).build().perform();
				// TM-27/01/2015 :- commented following code and used this code
				// for
				// simultaneous clicks
				break;

			case ActionClickandEsc :
				Actions clickandEsc = new Actions(currentdriver);
				Action clickEscAction = clickandEsc.moveToElement(webElement).click().sendKeys(Keys.ENTER, Keys.ESCAPE).build();
				clickEscAction.perform();
				break;

			case ActionMouseOver :
				Actions builderMouserOver = new Actions(currentdriver);
				builderMouserOver.moveToElement(webElement).perform();
				break;

			case Calendar :
				// Thread.sleep(5000);
				Boolean isCalendarDisplayed = currentdriver.switchTo().activeElement().isDisplayed();
				log.info(isCalendarDisplayed);
				if (isCalendarDisplayed == true) {
					String[] dtMthYr = ctrlValue.split("/");
					WebElement Year = WaitTool.waitForElement(currentdriver, By.name("year"), Integer.parseInt(Config.timeOut));// currentdriver.findElement(By.name("year"));
					while (!Year.getAttribute("value").equalsIgnoreCase(dtMthYr[2])) {
						if (Integer.parseInt(Year.getAttribute("value")) > Integer.parseInt(dtMthYr[2])) {
							WebElement yearButton = WaitTool.waitForElement(currentdriver, By.id("button1"), Integer.parseInt(Config.timeOut));// currentdriver.findElement(By.id("button1"));
							yearButton.click();
						} else if (Integer.parseInt(Year.getAttribute("value")) < Integer.parseInt(dtMthYr[2])) {
							WebElement yearButton = WaitTool.waitForElement(currentdriver, By.id("Button5"), Integer.parseInt(Config.timeOut));// currentdriver.findElement(By.id("Button5"));
							yearButton.click();
						}
					}
					Select date = new Select(WaitTool.waitForElement(currentdriver, By.name("month"), Integer.parseInt(Config.timeOut)));
					month = CalendarSnippet.getMonthForInt(Integer.parseInt(dtMthYr[1]));
					date.selectByVisibleText(month);
					WebElement Day = WaitTool.waitForElement(currentdriver, By.id("Button6"), Integer.parseInt(Config.timeOut));// currentdriver.findElement(By.id("Button6"));
					int day = 6;
					while (Day.getAttribute("value") != null) {
						Day = WaitTool.waitForElement(currentdriver, By.id("Button" + day), Integer.parseInt(Config.timeOut));// currentdriver.findElement(By.id("Button"+day));
						if (Day.getAttribute("value").toString().equalsIgnoreCase(dtMthYr[0])) {
							Day.click();
							break;
						}
						day++;
					}
				} else {
					log.info("Calendar not Diplayed");
				}
				break;

			case CalendarNew :
				isCalendarDisplayed = currentdriver.switchTo().activeElement().isDisplayed();
				log.info(isCalendarDisplayed);
				if (isCalendarDisplayed == true) {

					String[] dtMthYr = ctrlValue.split("/");
					Thread.sleep(2000);
					// String[] CurrentDate =
					// dtFormat.format(frmDate).split("/");
					WebElement Monthyear = currentdriver.findElement(By.xpath("//table/thead/tr/td[2]"));
					String Monthyear1 = Monthyear.getText();
					String[] Monthyear2 = Monthyear1.split(",");
					Monthyear2[1] = Monthyear2[1].trim();

					month = CalendarSnippet.getMonthForString(Monthyear2[0]);

					while (!Monthyear2[1].equalsIgnoreCase(dtMthYr[2])) {
						if (Integer.parseInt(Monthyear2[1]) > Integer.parseInt(dtMthYr[2])) {
							WebElement yearButton = currentdriver.findElement(By.cssSelector("td:contains('ï¿½')"));
							yearButton.click();
							Monthyear2[1] = Integer.toString(Integer.parseInt(Monthyear2[1]) - 1);
						} else if (Integer.parseInt(Monthyear2[1]) < Integer.parseInt(dtMthYr[2])) {
							WebElement yearButton = currentdriver.findElement(By.cssSelector("td:contains('ï¿½')"));
							yearButton.click();
							Monthyear2[1] = Integer.toString(Integer.parseInt(Monthyear2[1]) + 1);
						}
					}

					while (!month.equalsIgnoreCase(dtMthYr[1])) {
						if (Integer.parseInt(month) > Integer.parseInt(dtMthYr[1])) {
							WebElement monthButton = currentdriver.findElement(By.cssSelector("td:contains('ï¿½')"));
							monthButton.click();
							if (Integer.parseInt(month) < 11) {
								month = "0" + Integer.toString(Integer.parseInt(month) - 1);
							} else {
								month = Integer.toString(Integer.parseInt(month) - 1);
							}

						} else if (Integer.parseInt(month) < Integer.parseInt(dtMthYr[1])) {
							WebElement monthButton = currentdriver.findElement(By.cssSelector("td:contains('ï¿½')"));
							monthButton.click();
							if (Integer.parseInt(month) < 9) {
								month = "0" + Integer.toString(Integer.parseInt(month) + 1);
							} else {
								month = Integer.toString(Integer.parseInt(month) + 1);
							}
						}
					}

					WebElement dateButton = currentdriver.findElement(By.cssSelector("td.day:contains('" + dtMthYr[0] + "')"));
					log.info(dateButton);
					dateButton.click();

				} else {
					log.info("Calendar not Diplayed");
				}
				break;

			case CalendarIPF :
				String[] dtMthYr = ctrlValue.split("/");
				Thread.sleep(2000);
				String year = dtMthYr[2];
				String monthNum = dtMthYr[1];
				String day = dtMthYr[0];

				// Xpath for Year, mMnth & Days
				String xpathYear = "//div[@class='datepicker datepicker-dropdown dropdown-menu datepicker-orient-left datepicker-orient-bottom']/div[@class='datepicker-years']";
				String xpathMonth = "//div[@class='datepicker datepicker-dropdown dropdown-menu datepicker-orient-left datepicker-orient-bottom']/div[@class='datepicker-months']";
				String xpathDay = "//div[@class='datepicker datepicker-dropdown dropdown-menu datepicker-orient-left datepicker-orient-bottom']/div[@class='datepicker-days']";

				// Selecting year in 3 steps
				currentdriver.findElement(By.xpath(xpathDay + "/table/thead/tr[1]/th[2]")).click();
				currentdriver.findElement(By.xpath(xpathMonth + "/table/thead/tr/th[2]")).click();
				currentdriver.findElement(By.xpath(xpathYear + "/table/tbody/tr/td/span[@class='year'][contains(text()," + year + ")]")).click();

				// Selecting month in 1 step
				currentdriver.findElement(By.xpath(xpathMonth + "/table/tbody/tr/td/span[" + monthNum + "]")).click();

				// Selecting day in 1 step
				currentdriver.findElement(By.xpath(xpathDay + "/table/tbody/tr/td[@class='day'][contains(text()," + day + ")]")).click();

			case CalendarEBP :
				String[] dtMthYrEBP = ctrlValue.split("/");
				Thread.sleep(2000);
				String yearEBP = dtMthYrEBP[2];
				String monthNumEBP = CalendarSnippet.getMonthForInt(Integer.parseInt(dtMthYrEBP[1])).substring(0, 3);
				String dayEBP = dtMthYrEBP[0];

				// common path used for most of the elements
				String pathToVisibleCalendar = "//div[@class='ajax__calendar'][contains(@style, 'visibility: visible;')]/div";

				// following is to click the title once to reach the year page
				wait.until(
						ExpectedConditions.elementToBeClickable(By.xpath(pathToVisibleCalendar + "/div[@class='ajax__calendar_header']/div[3]/div")))
						.click();
				// check if 'Dec' is visibly clickable after refreshing
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(pathToVisibleCalendar
						+ "/div/div/table/tbody/tr/td/div[contains(text(), 'Dec')]")));
				// following is to click the title once again to reach the year
				// page
				currentdriver.findElement(By.xpath(pathToVisibleCalendar + "/div[@class='ajax__calendar_header']/div[3]/div")).click();

				// common path used for most of the elements while selection of
				// year, month and date
				pathToVisibleCalendar = "//div[@class='ajax__calendar'][contains(@style, 'visibility: visible;')]/div/div/div/table/tbody/tr/td";

				// each of the following line selects the year, month and date
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(pathToVisibleCalendar + "/div[contains(text()," + yearEBP + ")]")))
						.click();
				wait.until(
						ExpectedConditions.elementToBeClickable(By.xpath(pathToVisibleCalendar
								+ "/div[@class='ajax__calendar_month'][contains(text(),'" + monthNumEBP + "')]"))).click();
				wait.until(
						ExpectedConditions.elementToBeClickable(By.xpath(pathToVisibleCalendar + "/div[@class='ajax__calendar_day'][contains(text(),"
								+ dayEBP + ")]"))).click();

				break;

			/** Code for window popups **/
			case Window :
				switch (actionName) {
					case O :
						String parentHandle = currentdriver.getWindowHandle();
						for (String winHandle : currentdriver.getWindowHandles()) {
							currentdriver.switchTo().window(winHandle);
							if (currentdriver.getTitle().equalsIgnoreCase(controlName)) {
								currentdriver.close();
							}
						}
						currentdriver.switchTo().window(parentHandle);
						break;
					default :
						break;
				}
				break;

			case WebTable :
				switch (actionName) {
					case Read :
						WebHelperBilling.ReadFromExcel(ctrlValue);
						break;
					case Write :
						WebHelperUtil.writeToExcel(ctrlValue, webElement, controlId, controlType, controlName, rowNo, colNo);
						break;
					case NC :
						WebElement table = webElement;
						List<WebElement> tableRows = table.findElements(By.tagName("tr"));
						int tableRowIndex = 0;
						// int tableColumnIndex = 0;
						boolean matchFound = false;
						for (WebElement tableRow : tableRows) {
							tableRowIndex += 1;
							List<WebElement> tableColumns = tableRow.findElements(By.tagName("td"));
							if (tableColumns.size() > 0) {
								for (WebElement tableColumn : tableColumns)
									if (tableColumn.getText().equals(ctrlValue)) {
										matchFound = true;
										log.info(tableRowIndex);
										List<Object> elementProperties = WebHelperUtil.getPropertiesOfWebElement(
												tableColumns.get(Integer.parseInt(colNo)), imageType);
										controlName = elementProperties.get(0).toString();
										if (controlName.equals("")) {
											controlName = elementProperties.get(1).toString();
										}
										controlType = elementProperties.get(2).toString();
										webElement = (WebElement) elementProperties.get(3);
										doAction(FilePath, rowValues, testCase, imageType, controlType, controlId, controlName, ctrlValue, "", "",
												"", logicalName, action, webElement, Results, strucSheet, valSheet, tableRowIndex, rowcount, rowNo,
												colNo, operationType, cycleDate, TransactionType);
										break;
									}
								if (matchFound) {
									break;
								}
							}

						}
						break;
					case V :
						WebHelperUtil.WriteToDetailResults(ctrlValue, "", logicalName);
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							log.error(e.getMessage(), e);
							e.printStackTrace();
						}
						break;

					// Modified the code to handle performance issues//
					// ---Meghna--01/16/2018
					case TableInput :

						String tableV = WebHelperUtil.checkTable(logicalName, rowValues, ExcelUtility.TIvaluesheetrows);

						if (!(tableV.equals(""))) {
							// Meghna--to wait till element is visible as the
							// below line
							// does
							// not wait till the table is completely loaded
							wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(controlName)));
							findtablefound = currentdriver.findElements(By.xpath(controlName)).size() > 0;
							if (findtablefound == true) {
								// //Meghna--for performance issue//

								WebElement tableFound = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(controlName))); // Meghna--for
								// performance
								// issue//
								BillingProduct.TableInputAction(tableFound, controlName, logicalName, rowValues, valuesHeader,
										ExcelUtility.TIvaluesheetrows);
								Thread.sleep(1000);
							} else {
								log.info("Table not found. TABLE INPUT Functionality failed");
								break;
							}
						}
						break;

					// Modified the code to handle performance issues//
					case FIND :

						String findV = WebHelperUtil.CheckFind(logicalName, rowValues);

						if (!(findV.equals(""))) {
							Thread.sleep(5000);
							findtablefound = currentdriver.findElements(By.xpath(controlName)).size() > 0;

							if (findtablefound == true) {
								// Meghna - Added this to wait till the element
								// is
								// visible and enabled.
								WebElement tableFound = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(controlName)));

								BillingProduct.findAction(tableFound, controlName, logicalName, rowValues, valuesHeader);
								Thread.sleep(1000);
							} else {
								log.info("Table not found. FIND Functionality failed");
								break;
							}
						}

						break;

					case I :
						Thread.sleep(10000);
						findtablefound = currentdriver.findElements(By.xpath(controlName)).size() > 0;
						if (findtablefound == true) {
							WebElement tableFound = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(controlName)));
							List<WebElement> table_Rows = tableFound.findElements(By.tagName("tr"));
							List<WebElement> table_Columns = table_Rows.get(1).findElements(By.tagName("td"));
							// ApplicationtableRowsize = no of rows in the
							// WebTable
							int ApplicationtableRowsize = table_Rows.size();
							int Applicationtablecolumnsize = table_Columns.size();

							String ColumnName = ctrlValue.split(",")[0];
							String ColumnType = ctrlValue.split(",")[1];

							for (int i = 1; i <= Applicationtablecolumnsize; i++) {
								Thread.sleep(1000);
								String ApplicationColumnHeaderxapth = controlName + "/thead/tr/th[" + i + "]";
								log.info("ApplicationColumnHeader is:" + ApplicationColumnHeaderxapth);
								WebElement element = currentdriver.findElement(By.xpath(ApplicationColumnHeaderxapth));
								String ApplicationColumnHeader = element.getText();
								if ((ColumnName).equalsIgnoreCase(ApplicationColumnHeader)) {
									for (int r = 1; r <= ApplicationtableRowsize; r++) {
										if (ColumnType.equalsIgnoreCase("Webcheckbox")) {
											String XPath = controlName + "/tbody/tr[" + r + "]/td[" + i + "]/div/div/input";
											objfound = currentdriver.findElements(By.xpath(XPath)).size() > 0;
											if (objfound == true) {
												WebElement newelement = currentdriver.findElement(By.xpath(XPath));
												((JavascriptExecutor) currentdriver).executeScript("arguments[0].scrollIntoView();", newelement);// Meghana
												// --
												newelement.click();
												Thread.sleep(500);
												((JavascriptExecutor) currentdriver).executeScript("arguments[0].scrollIntoView();", newelement);
												Thread.sleep(500);
												objfound = false;
											}

										} else if (ColumnType.equalsIgnoreCase("WebLink")) {
											String XPath = controlName + "/tbody/tr[" + r + "]/td[" + i + "]/div/span";
											objfound = currentdriver.findElements(By.xpath(XPath)).size() > 0;
											if (objfound == true) {
												WebElement newelement = currentdriver.findElement(By.xpath(XPath));
												log.info("link xpath " + XPath);

												((JavascriptExecutor) currentdriver).executeScript("arguments[0].scrollIntoView();", newelement);// Meghana
												// --
												((JavascriptExecutor) currentdriver).executeScript("arguments[0].click();", newelement);// Meghana
												Thread.sleep(500);
												Thread.sleep(500);
												objfound = false;
											}
										} else if (ColumnType.equalsIgnoreCase("WebCheckBox")) {
											// not encountered
										}
									}
								}
							}

						}
						break;
					default :
						break;
				}
				break;

			// bhaskar capture screenshot START
			case Screenshot :
				switch (actionName) {
					case NC :
						cdate = null; // Mandar -17/11/2017 - Not Changing this
										// part
						if (StringUtils.isNotBlank(webDriver.getReport().getFromDate()))
							cdate = webDriver.getReport().getFromDate().replaceAll("[-/: ]", "");
						else
							webDriver.getReport().setFromDate(Config.dtFormat.format(new Date()));
						String transactionname = webDriver.getReport().getTrasactionType();
						String Screenshotcycledate = cycleDate.replace("/", "_");
						String cfileName = webDriver.getReport().getTestcaseId() + "_" + transactionname + "_" + Screenshotcycledate;
						clocation = Config.resultFilePath + "\\ScreenShots\\" + cfileName + "_" + screenshotnum + ".png";
						image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
						ImageIO.write(image, "png", new File(clocation));
						screenshotnum = screenshotnum + 1;

						break;

					// Case Screenshot from State Farm Project - Aniruddha K.
					case Screenshot :
						cdate = null;
						if (StringUtils.isNotBlank(webDriver.getReport().getFromDate()))
							cdate = webDriver.getReport().getFromDate().replaceAll("[-/: ]", "");
						else
							webDriver.getReport().setFromDate(Config.dtFormat.format(new Date()));

						String cycleDate_value3 = cycleDate_Values2.toString().replaceAll("/", "_");
						String strTestCaseID = controller.controllerTestCaseID.toString();
						String SC_NO[] = strTestCaseID.split("P");
						String strScNo = SC_NO[0].replace("_", "");
						// cycleDate_value3+"_"+cdate; //Meghna
						cfileName = strScNo + "_" + strTestCaseID + "_" + cycleDate_value3 + "_" + cdate;
						System.out.println("Screenshot path : " + cfileName);
						clocation = System.getProperty("user.dir") + "\\" + Config.projectName + "\\Resources\\Screenshots\\" + strScNo + "\\"
								+ cfileName + ".jpg";

						File screen_location = new File(clocation);

						while (screen_location.exists()) {
							i++;
							clocation = clocation.replace(".jpg", "");
							clocation = clocation + "_" + i;
							screen_location = new File(clocation);
							clocation = clocation + ".jpg";
						}
						screen_location = new File(clocation);

						File scrFile = ((TakesScreenshot) currentdriver).getScreenshotAs(OutputType.FILE);
						FileUtils.copyFile(scrFile, screen_location);
						break;

					default :
						break;
				}
				break;
			// bhaskar capture screenshot END

			case Robot :
				if (controlName.equalsIgnoreCase("SetFilePath")) {
					StringSelection stringSelection = new StringSelection(ctrlValue);
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
					robot.delay(1000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_V);
					robot.keyRelease(KeyEvent.VK_V);
					robot.keyRelease(KeyEvent.VK_CONTROL);

				} else if (controlName.equalsIgnoreCase("TAB")) {
					Thread.sleep(1000);
					// Belowgiven code changes done by Basebilling --- Meghana
					try {
						webElementForROBOT.sendKeys(Keys.TAB);
					} catch (Exception ex) {
						log.error(ex.getMessage(), ex);
						System.out.println("Object was not available");
					}
				} else if (controlName.equalsIgnoreCase("SPACE")) {
					robot.keyPress(KeyEvent.VK_SPACE);
					robot.keyRelease(KeyEvent.VK_SPACE);
				} else if (controlName.equalsIgnoreCase("ENTER")) {
					robot.keyPress(KeyEvent.VK_ENTER);
					robot.keyRelease(KeyEvent.VK_ENTER);
					Thread.sleep(3000);
				}
				break;

			case DB :
				switch (actionName) {
					case Write :
						String policyNo = currentdriver.findElement(By.xpath(controlName)).getText();
						ctrlValue = ctrlValue + "'" + policyNo + "'";
						ResultSet rs = null;
						Connection conn = JDBCConnection.establishDBConn();
						Statement st = conn.createStatement();
						rs = st.executeQuery(ctrlValue);
						rs.next();
						ctrlValue = String.valueOf(rs.getLong("COL_1"));
						rs.close();
						st.close();
						JDBCConnection.closeConnection(conn);
						WebHelperUtil.writeToExcel(ctrlValue, webElement, controlId, controlType, controlName, rowNo, colNo);
						break;
					default :
						break;
				}
				break;

			case WaitForEC :
				wait.until(CommonExpectedConditions.elementToBeClickable(webElement));
				break;
			case SikuliScreen :
				App.open(sikscreen);
				break;
			case SikuliType :
				log.info("in sikulitype");
				log.info("controlName is:" + controlName);
				break;

			case SikuliButton :
				log.info("in sikuliButton");
				log.info("controlName is:" + controlName);
				log.info("Done");
				break;
			case Slider :
				WebElement slider = currentdriver.findElement(By.xpath(controlName));
				Thread.sleep(3000);
				Actions moveSlider = new Actions(currentdriver);
				Action actionslider = moveSlider.dragAndDropBy(slider, 30, 0).build();
				actionslider.perform();
				break;
			case MaskedInputDate :
				if (!ctrlValue.equalsIgnoreCase("null")) {
					webElement.clear();
					webElement.click();
					// For handling Object level issue 01 June -- Meghana
					((JavascriptExecutor) currentdriver).executeScript("arguments[0].setAttribute('value', '" + ctrlValue + "')", webElement);
					webElement.clear(); // -- Meghana
					Thread.sleep(1000); // -- Meghana
					webElement.sendKeys(ctrlValue);
					webElement.sendKeys(Keys.TAB);// Mandar
				} else {
					webElement.clear();
				}
				break;
			// bhaskar
			case Date :
				Calendar cal = new GregorianCalendar();
				int i = cal.get(Calendar.DAY_OF_MONTH);
				if (i >= 31) {
					i = i - 10;
				}
				break;

			case FileUpload :
				((JavascriptExecutor) currentdriver).executeScript("arguments[0].setAttribute('value', '" + ctrlValue + "')", webElement);
				// For handling Object level issue 01 June webElement.clear();
				Thread.sleep(1000);
				webElement.sendKeys(ctrlValue);
				break;

			case ScrollTo :
				Locatable element = (Locatable) webElement;
				Point p = element.getCoordinates().onScreen();
				JavascriptExecutor js = (JavascriptExecutor) currentdriver;
				js.executeScript("window.scrollTo(" + p.getX() + "," + (p.getY() + 150) + ");");
				break;

			case Freeze :
				switch (actionName) {
					case I :
						if (logicalName.equalsIgnoreCase("GroupNo"))
							group_no = ctrlValue;
						else if (logicalName.equalsIgnoreCase("AccountNo"))
							account_no = ctrlValue;
						else if (logicalName.equalsIgnoreCase("JobStatus"))
							job_status = ctrlValue;
						else if (logicalName.equalsIgnoreCase("ExpectedStatus")) {
							expected_status = ctrlValue;

							try {
								Connection conn = JDBCConnection.establishHTML5BillingDBConn();
								Statement st = conn.createStatement();

								if (group_no != null) {
									st.execute("UPDATE job_schedule SET job_status = '"
											+ job_status
											+ "' WHERE job_status = '"
											+ expected_status
											+ "' AND group_system_code IN (select system_entity_code from entity_register where source_system_entity_code IN ("
											+ group_no + "))");
								}
								if (account_no != null) {
									st.execute("UPDATE job_schedule SET job_status = '"
											+ job_status
											+ "' WHERE job_status = '"
											+ expected_status
											+ "' AND Account_system_code IN (select system_entity_code from entity_register where source_system_entity_code IN ("
											+ account_no + "))");
								}
								log.info("Group No Freezed :" + group_no + account_no);
								webDriver.getReport().setMessage("Group No freezed : " + group_no + account_no);
								if (Config.databaseType.equalsIgnoreCase("ORACLE")) {
									st.execute("commit");
								}
								st.close();
								JDBCConnection.closeConnection(conn);
							}

							catch (Exception e) {
								log.error(e.getMessage(), e);
								throw new Exception("Error in RunDBQueries : " + e.getMessage());
							}
						}

						break;
					default :
						break;
				}
				// Case for Apex Archive ---Varsha 05/09/2018
			case Apex_Archive :
				switch (actionName)

				{
					case I :
						try {

							if (logicalName.trim().equalsIgnoreCase("ApexNo")) {
								LocationNo = ctrlValue;

								String query = "UPDATE entity_register SET" + " source_system_entity_code=concat(source_system_entity_code, 1)"
										+ "WHERE APEX_ENTITY_CODE  =" + "(SELECT APEX_ENTITY_CODE " + "FROM entity_register "
										+ "WHERE source_system_entity_code='" + LocationNo + "')";

								Connection conn = JDBCConnection.establishHTML5BillingDBConn();
								Statement st = conn.createStatement();
								st.execute(query);
								if (Config.databaseType.equalsIgnoreCase("ORACLE")) {
									st.execute("commit");
								}
								st.close();
								JDBCConnection.closeConnection(conn);

								// Updating Report
								webDriver.getReport().setMessage("Apex Archived : " + LocationNo);

							}
						} catch (Exception e) {
							log.info(e.getLocalizedMessage());
							webDriver.getReport().setStatus("FAIL");
							webDriver.getReport().setMessage(e.getLocalizedMessage());
						}

						break;
					default :
						break;
				}
				break;
			// Case for Task Load ---Varsha 05/09/2018
			case Task_Load :
				switch (actionName) {

					case I :
						if (logicalName.trim().equalsIgnoreCase("ModuleNO"))
							Module_No = Integer.parseInt(ctrlValue);

						else if (logicalName.trim().equalsIgnoreCase("UserNO"))
							User_No = Integer.parseInt(ctrlValue);

						else if (logicalName.equalsIgnoreCase("UserID1")) {
							User_ID1 = ctrlValue;
							UserList.add(User_ID1);
						} else if (logicalName.trim().equalsIgnoreCase("UserID2")) {
							User_ID2 = ctrlValue;
							UserList.add(User_ID2);
						} else if (logicalName.trim().equalsIgnoreCase("UserID3")) {
							User_ID3 = ctrlValue;
							UserList.add(User_ID3);
						} else if (logicalName.trim().equalsIgnoreCase("UserID4")) {
							User_ID4 = ctrlValue;
							UserList.add(User_ID4);
						} else if (logicalName.trim().equalsIgnoreCase("UserID5")) {
							User_ID5 = ctrlValue;
							UserList.add(User_ID5);
						} else if (logicalName.trim().equalsIgnoreCase("UserID6")) {
							User_ID6 = ctrlValue;
							UserList.add(User_ID6);
						} else if (logicalName.trim().equalsIgnoreCase("UserID7")) {
							User_ID7 = ctrlValue;
							UserList.add(User_ID7);
						} else if (logicalName.trim().equalsIgnoreCase("UserID8")) {
							User_ID8 = ctrlValue;
							UserList.add(User_ID8);
						} else if (logicalName.trim().equalsIgnoreCase("UserID9")) {
							User_ID9 = ctrlValue;
							UserList.add(User_ID9);
						} else if (logicalName.trim().equalsIgnoreCase("AllocatedCount")) {

							StringBuilder UserNameStr = new StringBuilder();

							try {

								Allocated_Count = Integer.parseInt(ctrlValue);

								int ac = 1, uniqueNo;
								String User_ID_Value = null;

								System.out.print(UserList.size());

								Connection conn = JDBCConnection.establishHTML5BillingDBConn();
								Statement st = conn.createStatement();

								// Iteration for all users to load with Task
								for (int ul = 0; ul < UserList.size(); ul++) {

									// ***Retrieve USER_ID from user_master ***
									// Varsha
									ResultSet UId = st.executeQuery("Select USER_ID from USER_MASTER where DISPLAY_USER_ID ='" + UserList.get(ul)
											+ "'");

									if (UId.next()) {
										User_ID_Value = UId.getString("USER_ID");
									}

									uniqueNo = ac;

									// //Iteration for No of allocation Count
									for (ac = uniqueNo; ac < uniqueNo + Allocated_Count; ac++) {

										st.execute("INSERT INTO [dbo].RS_WORKITEMCACHE (WIR_ID, [version], SPECID, SPECURI, SPECVERSION, TASKID, RESOURCESTATUS)"
												+ " VALUES (" + Module_No + User_No + ac + ", 1, 'UID', 'Task', 1, 'TID','Allocated')");

										st.execute("INSERT INTO [dbo].RS_WORKITEMPARTICIPANT(WIR_PART_ID, PARTICIPANT_ID, WIR_ID) " + "	VALUES("
												+ Module_No + User_No + ac + ", '" + User_ID_Value + "','" + Module_No + User_No + ac + "')");
									}

									// Converting User List values to string
									UserNameStr.append(UserList.get(ul));
									if (ul != UserList.size() - 1) {
										UserNameStr.append(", ");
									}
									UId.close();
								}
								if (Config.databaseType.equalsIgnoreCase("ORACLE")) {
									st.execute("commit");
								}
								st.close();
								JDBCConnection.closeConnection(conn);
								// Updating Report
								webDriver.getReport().setMessage("Users : " + UserNameStr + " Loaded With Allocation Count " + Allocated_Count);
								// Clear Userlist
								UserList.clear();

							}

							catch (Exception e) {

								webDriver.getReport().setStatus("FAIL");
								webDriver.getReport().setMessage(e.getLocalizedMessage());

							}

						}

						break;
					default :
						break;
				}

				break;

			// Case for FlatFile - Linux ----Meghna--04/12/2017
			case AP_Outbound :
			case FlatFileResponse :
			case FlatFile :
				switch (actionName) {
					case I :
						if (logicalName.equalsIgnoreCase("Local_Path")) {
							file_cycledate = ctrlValue;
							file_cycledate = file_cycledate.replace("/", "_");
							local_path = System.getProperty("user.dir") + "\\" + Config.projectName + "\\Resources\\Input\\FlatFile\\FlatFiles\\"
									+ ctrlValue;
							local_path = local_path.replace("/", "_");
						} else if (logicalName.equalsIgnoreCase("Remote_Path_Inbound")) {
							if (Config.flatFilePath.equals("") | Config.flatFilePath.equals(null))
								remote_path_in = ctrlValue;
							else
								remote_path_in = Config.flatFilePath + ctrlValue;
						} else if (logicalName.equalsIgnoreCase("File_To_Be_Converted"))
							file_to_be_converted = ctrlValue;
						else if (logicalName.equalsIgnoreCase("Extension"))
							// extension = file_cycledate + ctrlValue;
							extension = ctrlValue;
						else if (logicalName.equalsIgnoreCase("Remote_Path_Outbound")) {
							if (Config.flatFilePath.equals("") | Config.flatFilePath.equals(null))
								remote_path_out = ctrlValue;
							else
								remote_path_out = Config.flatFilePath + ctrlValue;
						} else if (logicalName.equalsIgnoreCase("ArchiveYN"))
							archive = ctrlValue;
						else if (logicalName.equalsIgnoreCase("XFL_FileName")) {
							xfl_filename = System.getProperty("user.dir") + "\\" + Config.projectName + "\\Resources\\Input\\FlatFile\\FlatFiles\\"
									+ ctrlValue;
							xfl_filename = xfl_filename.replace("/", "_");
						} else if (logicalName.equalsIgnoreCase("ReadFromDatabase") && !(ctrlValue.isEmpty())) {
							String requests_xml = local_path + "\\" + file_to_be_converted;
							requests_xml = requests_xml.replace("/", "_");
							Thread.sleep(1000);
							WebService.setXMLAPTagValue(requests_xml, "CheckStatusUpdate", "DisbursementDetailSeq", "PaymentId", "PaymentStatusDate",
									ctrlValue, 0);
						}
						// added by sonali to unique flat file name
						else if (logicalName.equalsIgnoreCase("UniqueNo")) {
							uniqueFFNo = ctrlValue;
						} else if (logicalName.equalsIgnoreCase("ReportFilePath")) {
							reportFilePath = ctrlValue;
						} else if (logicalName.equalsIgnoreCase("CopyReportsToLocal")) {
							if (ctrlValue.equalsIgnoreCase("Y")) {
								try {
									log.info("Ready to Download all Bill XML !!");
									GenerateOutputXML.main(null);
								} catch (Exception e) {
									log.error(e.getMessage(), e);
									e.printStackTrace();
								}
							}
						}
						break;

					case ArchiveFiles :

						if (ctrlValue.equalsIgnoreCase("Y")) {
							// Get data from Config
							String hostName = Config.flatFileHostName;
							String userName = Config.flatFileUserName;
							String password = Config.flatFilePassword;
							int port = Integer.parseInt(Config.flatFilePort);
							//

							JSch jsch = new JSch();

							Session session = null;
							System.out.println("Trying to connect.....");

							try {
								session = jsch.getSession(userName, hostName, port);
								session.setConfig("StrictHostKeyChecking", "no");
								session.setPassword(password);
								session.connect();

								Channel channel = session.openChannel("sftp");
								channel.connect();
								ChannelSftp sftpChannel = (ChannelSftp) channel;

								System.out.println("Done !!");

								ArchiveFiles af = new ArchiveFiles();
								af.archive(sftpChannel, remote_path_out, extension);
							}

							catch (JSchException e) {
								log.error(e.getMessage(), e);
								e.printStackTrace();
							} catch (SftpException e) {
								log.error(e.getMessage(), e);
								e.printStackTrace();
							}

						}

						break;
					case CopyFiles :

						if (ctrlValue.equalsIgnoreCase("Y")) {
							InboundFileTransfer cfr = new InboundFileTransfer();
							// cfr.copyFilesToRemote(local_path,
							// remote_path_in,file_to_be_converted, ("_" +
							// file_cycledate + extension),xfl_filename);
							if (uniqueFFNo.equals("") | uniqueFFNo.equals(null))
								cfr.copyFilesToRemote_Nav(local_path, remote_path_in, file_to_be_converted, ("_" + file_cycledate + extension),
										xfl_filename);
							else
								cfr.copyFilesToRemote_Nav(local_path, remote_path_in, file_to_be_converted, ("_" + uniqueFFNo + extension),
										xfl_filename);
						}

						break;

					case ConvertToXml :
						if (ctrlValue.equalsIgnoreCase("Y")) {
							validateTag = ctrlValue1Cell.toString();
							validationMsg = ctrlValue2Cell.toString();
							OutboundTransferFiles cf = new OutboundTransferFiles();
							cf.copyFiles_Nav(remote_path_out, local_path, file_to_be_converted, extension, xfl_filename, archive, validateTag,
									validationMsg, file_cycledate);
						}
						break;
					default :
						break;
				}
				break;

			// Case for FlatFile - Linux ----Meghna---04/12/2017

			case CopyFlatFile :
				switch (actionName)

				{
					case I :
						if (logicalName.equalsIgnoreCase("DestinationFolder"))
							destination_folder = ctrlValue;
						else if (logicalName.equalsIgnoreCase("FileName")) {
							file_names = ctrlValue;

							try {
								cycleDate = cycleDate.replace("/", "_");
								SourceFlatFile = (Config.inputDataFilePath + "CopyFlatFile\\FlatFiles\\");
								DestinationFlatFile = (Config.copyServerRemotePath + "\\" + destination_folder);
								String[] files = file_names.split("\\|");
								String filesnames = "";

								for (int k = 0; k < files.length; k++) {
									file_name = "";
									file_name = files[k];

									File src = new File(SourceFlatFile + "/" + cycleDate + "/" + file_name);
									File dest = new File(DestinationFlatFile + "/" + file_name);

									if (src.exists()) {
										FileUtils.copyFile(src, dest);
										filesnames = filesnames + " " + file_name;
									}

									else {

										// printing error messages into result
										// file
										webDriver.getReport().setMessage("Directory: " + SourceFlatFile + "/" + cycleDate + "/" + "not found");
										log.error("Directory: " + SourceFlatFile + "/" + cycleDate + "/" + "not found");
										throw new IOException("Directory: " + SourceFlatFile + "/" + cycleDate + "/" + "not found");

									}

								}

								log.info("Files Copied :" + filesnames);
								webDriver.getReport().setMessage("Files Copied :" + filesnames);

							} catch (IOException ff) {
								webDriver.getReport().setMessage(ff.getMessage());
								log.error("File not found in path: " + SourceFlatFile + "/" + cycleDate + "/" + file_name + " <-|-> LocalizeMessage "
										+ ff.getLocalizedMessage() + " <-|-> Message " + ff.getMessage() + " <-|-> Cause " + ff.getCause(), ff);
								throw new IOException("File not found in path: " + SourceFlatFile + "/" + cycleDate + "/" + file_name
										+ " <-|-> LocalizeMessage " + ff.getLocalizedMessage() + " <-|-> Message " + ff.getMessage()
										+ " <-|-> Cause " + ff.getCause());
							}
						}
						// FileChannel DestinationFlatFile = null;
						// FileChannel SourceFlatFile = null;
						break;
					default :
						break;
				}
			case WebServiceCSI : // Meghna--04/12/2017
			case WebService_CheckUpdate : // Mandar - For GP Billing
			case WebService_VoidRef : // Mandar - For GP Billing
			case WebService : // devishree
			case WebService1 :
			case WebService2 :
			case WebService3 :
			case WebServiceV :
			case WebServiceC :
			case WebServiceRP :
			case WebServiceVI :
			case WebServiceV1 :
			case WebServiceV2 :
			case WebServiceVAG :
			case WebServiceV3 :
				switch (actionName) {
					case I :
						if (logicalName.equalsIgnoreCase("WSDL_URL"))
							wsdl_url = ctrlValue;
						else if (logicalName.equalsIgnoreCase("REQUEST_URL"))
							request_url = ctrlValue;
						else if (logicalName.equalsIgnoreCase("REQUEST_XML")) {
							request_xml = ctrlValue;
							// if(TransactionType.toString().equalsIgnoreCase("WebServiceC"))//Meghana--
							if (!TransactionType.toString().contains("WebServiceV")) {
								// anurag
								String request_xml = System.getProperty("user.dir") + "\\" + Config.projectName
										+ "\\Resources\\Input\\WebService\\WebserviceFiles\\" + cycleDate + "\\" + ctrlValue + ".xml";
								request_xml = request_xml.replace("/", "_");
								Thread.sleep(1000);
								WebService.setXMLResponseTagValue(request_xml, "RequestHeader", "SourceSystemRequestNo", 0);
							}
						}
						// For Check Status Update -- 04/12/2017//
						else if (logicalName.equalsIgnoreCase("ReadFromDatabase") && !(ctrlValue.isEmpty())) {
							// request_xml = ctrlValue;
							String requests_xml = System.getProperty("user.dir") + "\\" + Config.projectName
									+ "\\Resources\\Input\\WebService\\WebserviceFiles\\" + cycleDate + "\\" + request_xml + ".xml";
							requests_xml = requests_xml.replace("/", "_");
							Thread.sleep(1000);
							WebService.setXMLAPTagValue(requests_xml, "CheckStatusUpdate", "DisbursementDetailSeq", "PaymentId", "PaymentStatusDate",
									ctrlValue, 0);
						}
						// For Check Status Update -- 04/12/2017//
						break;

					case T :
						// bhaskar save response data START
						// Reporter report =new Reporter();
						faultstring = false;
						// NoResponseFile = false;
						success = false;
						description = null;
						pathtoNode = ctrlValue1;
						column_Name = ctrlValue2;
						log.info("path to Node:" + pathtoNode);
						log.info("column names :" + column_Name);
						responseXml = WebService.callWebService(wscycledate, wsdl_url, request_xml, Config.user, Config.password);
						String InBoundInstrumentInformation = "InBoundInstrumentInformation";
						if (InBoundInstrumentInformation.contains(wsdl_url)
								&& ((!pathtoNode.equalsIgnoreCase("null") && !pathtoNode.equalsIgnoreCase("")) || (!column_Name
										.equalsIgnoreCase("null") && !column_Name.equalsIgnoreCase(""))))
							WebService.getXMLResponseData(ctrlValue1, ctrlValue2, testCase, wscycledate, responseXml);
						if (!controller.controllerTransactionType.toString().contains("WebServiceV")) {
							// String Tag_Name = "EntityResponse";
							String Tag_Name = "*";
							// String Node_Value1 = "SuccessFlag";
							String[] Node_Value = new String[2];
							if (ctrlValue1 != "" && ctrlValue2 != "") {
								Node_Value[0] = ctrlValue1;
								Node_Value[1] = ctrlValue2;
							} else {
								Node_Value[0] = "ProcessStatusFlag";
								Node_Value[1] = "SuccessFlag";
							}
							String Node_Value2 = "faultstring";
							String Node_Value3 = "Description";
							int index = 0;
							WebServiceResponse = WebService.getXMLResponseStatus(responseXml, Tag_Name, Node_Value, index);

							// if(successFlag == null)
							if (WebServiceResponse.equalsIgnoreCase("SUCCESS")) {
								webDriver.getReport().setMessage("SUCCESS : Matched -- " + " ' " + Node_Value[1] + " ' ");// Mandar
								webDriver.getReport().setStatus("PASS");
								success = true;
								description = "SUCCESS";
							}

							else if (WebServiceResponse.equalsIgnoreCase("FAILED") && success != true) {
								// successFlag =
								// WebService.getXMLResponseTagValue(responseXml,Tag_Name,Node_Value2,index);
								FailedResponseTagValue = WebService.getXMLResponseTagValue(responseXml, Tag_Name, Node_Value3, index);
								if (webDriver.getReport().getMessage() == null || webDriver.getReport().getMessage() == "") {
									// to do//Mandar --20/09/2017
								}
								failed = true;
							}
							// if(successFlag == null)
							else if (WebServiceResponse == null && failed != true) {
								faultstring = true;
								FailedResponseTagValue = WebService.getXMLResponseTagValue(responseXml, Tag_Name, Node_Value2, index);
								webDriver.getReport().setMessage("REQUEST FAILED : Error Msg displayed -- " + FailedResponseTagValue);// Mandar--
								webDriver.getReport().setStatus("FAIL");
								description = FailedResponseTagValue;
							}

							else if (WebServiceResponse.equalsIgnoreCase("BLANK") && nullvalue != true && WebService.isNoResponseFileTrue()) {

								webDriver.getReport().setMessage("BLANK WEBSERVICE RESPONSE");
								webDriver.getReport().setStatus("FAIL");

								description = "BLANK WEBSERVICE RESPONSE";
							}
							log.info("Tag value from Response file is:" + FailedResponseTagValue);

							if (WebServiceResponse == null || WebServiceResponse.equalsIgnoreCase("FAILED")
									|| WebServiceResponse.equalsIgnoreCase("BLANK")) {
								// Recovery Scenario (WebService) START
								controller.recoveryhandler();
								// Recovery Scenario (WebService) END
							}
							success = false;
							// Mandar--Uncommented as this was writing
							// SUCCESS instead of error message for
							// failed scenarios.---Meghna
						}
						// ***Below given code added by Meghana for
						// Basebilling***
						else if (controller.controllerTransactionType.toString().contains("WebServiceVI")) {
							String actual_XML = responseXml;
							String expected_XML = actual_XML.replaceAll("_Response.xml", "_expected.xml");
							String details = "TransactionID:" + webDriver.getReport().getTestcaseId() + "|" + "CycleDate:" + cycleDate + "|"
									+ "TransactionType:" + controller.controllerTransactionType.toString() + "|" + "ExpectedValue:" + expected_XML
									+ "|" + "ActualValue:" + actual_XML;
							String f_Details = System.getProperty("user.dir") + "\\" + Config.projectName
									+ "\\Resources\\XML_Comparison_File_Helper.txt";
							try (PrintWriter writeDetailsToFile = new PrintWriter(f_Details)) {
								writeDetailsToFile.write(details);
							} catch (Exception e) {
								log.error(e.getMessage(), e);
								log.info("Failed while writing file to " + f_Details);
							}

							try {
								Thread.sleep(5000);
							} catch (InterruptedException e1) {
								log.error(e1.getMessage(), e1);
							}

							Jacob.main(Config.webserviceComparisonUtilityPath, "WebserviceVerificationIntermideate");

							try {
								Thread.currentThread().wait(2000);
								CalendarSnippet.killProcess("EXCEL.EXE");
							} catch (Exception e) {
								log.error(e.getMessage(), e);
							}

							StringBuilder comparedResult = null;
							try (BufferedReader ReaderDetailsFromFile = new BufferedReader(new FileReader(f_Details))) {
								Boolean createStringBuilder = false;
								// String line =
								// ReaderDetailsFromFile.readLine();
								String line = null;
								while ((line = ReaderDetailsFromFile.readLine()) != null) {
									if (createStringBuilder == false) {
										comparedResult = new StringBuilder();
										createStringBuilder = true;
									}
									comparedResult.append(line);
									comparedResult.append(System.lineSeparator());
									System.out.println(line);
								}

							} catch (Exception e) {
								log.error(e.getMessage(), e);
								log.info("Failed while Reading file from " + f_Details);
							}

							if (comparedResult != null && !comparedResult.equals("")) {
								webDriver.getReport().setMessage(comparedResult.toString());
								webDriver.getReport().setStatus("FAIL");
							} else {
								webDriver.getReport().setStatus("PASS");
							}
							// ****
						} else {
							break;
						}
						break;
					case V :
						currentValue = WebService.getXMLTagValue(controlName);
						break;
					// Added for EFT Transaction // ----Meghna
					case Write :
						if (ctrlValue != "") {
							WebHelperUtil.writeToExcel(ctrlValue, webElement, controlId, controlType, controlName, rowNo, colNo);
						}

						break;

					// Added ***----Meghna
					case Read :

						if (ctrlValue != "") {

							uniqueNumber = WebHelperBilling.ReadFromExcel(ctrlValue);

							String[] tag_Names = controlName.split(";");

							String req_xml = System.getProperty("user.dir") + "\\" + Config.projectName
									+ "\\Resources\\Input\\WebService\\WebserviceFiles\\" + cycleDate + "\\" + request_xml + ".xml";
							req_xml = req_xml.replace("/", "_");
							WebService.setReadValueXML(req_xml, tag_Names[0], tag_Names[1], 0, uniqueNumber);

						}
						break;

					// Added for EFT Transaction // ----Meghna

					//
					default :
						break;
				}
				break;
			case Restful : {

				log.info("---------Rest call reached");
				switch (actionName) {
					case I :
						if (logicalName.equalsIgnoreCase("RestURL"))
							restUrl = ctrlValue;
						if (logicalName.equalsIgnoreCase("RequestMethod")) {
							/* log.info("select request method"); */requestMethod = ctrlValue;
						}
						if (logicalName.equalsIgnoreCase("ContentType")) {
							/* log.info("select content type"); */contentType = ctrlValue;
						}
						if (logicalName.equalsIgnoreCase("RequestJSON")) {
							/* log.info("select json file "); */requestJson = ctrlValue;
						}
						if (logicalName.equals("Validate")) {
							if ("Y".equals(ctrlValue))
								validateJson = true;
							else
								validateJson = false;
						}

						break;

					case T :
						String xmlResponse = null;

						if (TransactionType.toString().startsWith("RestServiceJSON")) {
							log.info("REST JSON POST request");
							String requestJsonFile = System.getProperty("user.dir") + "\\" + Config.projectName
									+ "\\Resources\\Input\\Restful\\RestfulFiles\\" + cycleDate + "\\" + requestJson + ".json";
							requestJsonFile = requestJsonFile.replace("/", "_");
							String inputJson = new String(Files.readAllBytes(Paths.get(requestJsonFile)));
							Response response = RestService.getRestResponse(MediaType.valueOf(contentType), HttpMethod.valueOf(requestMethod),
									restUrl, inputJson, "Auth2.0", "", "");
							restResponse = response.asString();
						}

						if (!StringUtils.isEmpty(restResponse)) {
							log.debug("Writing rest response to a file.");
							String responseFilePath = System.getProperty("user.dir") + "\\" + Config.projectName
									+ "\\Resources\\Input\\Restful\\RestfulFiles\\" + cycleDate + "\\" + requestJson + "_Response.json";
							responseFilePath = responseFilePath.replace("/", "_");
							File responseFile = new File(responseFilePath);
							/* try{ */
							restResponse = CommonUtils.toPrettyFormat(restResponse);
							CommonUtils.WriteToFile(responseFile, restResponse.getBytes());

							// Actual response xml
							JSONObject resJson = new JSONObject(restResponse);
							xmlResponse = XML.toString(resJson);
							String soapnode = "<soap:Envelope xmlns:soap= \"http://schemas.xmlsoap.org/soap/envelope/\"> \n" + "<soap:Body> \n"
									+ "<ns2:serviceResponse xmlns:ns2=\"http://com/majescomastek/stgicd/ws/meta/entityinterface\"> \n"
									+ " <return> \n ";
							soapnode = soapnode + xmlResponse;
							soapnode = soapnode + " \n </return> \n </ns2:serviceResponse> \n </soap:Body> \n </soap:Envelope>";

							File xmlFilePath = new File(responseFilePath.replace(".json", ".xml"));
							CommonUtils.WriteToFile(xmlFilePath, soapnode.getBytes());

							// Expected Respons xml
							String expectedJsonFilePath = System.getProperty("user.dir") + "\\" + Config.projectName
									+ "\\Resources\\Input\\Restful\\RestfulFiles\\" + cycleDate + "\\" + requestJson + "_Expected.json";
							expectedJsonFilePath = expectedJsonFilePath.replace("/", "_");

							File expectedJsonFile = new File(expectedJsonFilePath);

							String expectedJson = "";
							if (expectedJsonFile.exists()) {
								expectedJson = new String(Files.readAllBytes(Paths.get(expectedJsonFilePath)));
							}

							if (expectedJson.contains("{")) {
								JSONObject expJson = null;
								try {
									expJson = new JSONObject(expectedJson);
								} catch (Exception e) {
									log.info("error while parsing expected JSON: " + e.getMessage());
								}
								String expectedXML = XML.toString(expJson);

								String soapexpnode = "<soap:Envelope xmlns:soap= \"http://schemas.xmlsoap.org/soap/envelope/\"> \n"
										+ "<soap:Body> \n"
										+ "<ns2:serviceResponse xmlns:ns2=\"http://com/majescomastek/stgicd/ws/meta/entityinterface\"> \n"
										+ " <return> \n ";
								soapexpnode = soapexpnode + expectedXML;
								soapexpnode = soapexpnode + " \n </return> \n </ns2:serviceResponse> \n </soap:Body> \n </soap:Envelope>";

								File expectedFilePath = new File(expectedJsonFilePath.replace(".json", ".xml"));
								CommonUtils.WriteToFile(expectedFilePath, soapexpnode.getBytes());
							} else {
								log.info("Expected Json File does not exist or It is present but its Empty");
							}

						}

						boolean success = false;
						if (!StringUtils.isEmpty(restResponse)) {
							success = JsonUtility.validateRestJson(restResponse);
						} else {
							log.info("Rest response returned is null or empty");
						}

						if (success) {
							webDriver.report.setStatus("PASS");
							webDriver.report.setMessage("SuccessFlag : SUCCESS");
						} else {
							webDriver.report.setStatus("FAIL");
							if (restResponse == null) {
								webDriver.report.setMessage("Rest Response is null(Internal Server Error:500 OR BAD REQUEST Error:400)");
							} else {
								if (restErrorResDesc.equals(""))
									webDriver.report.setMessage("SuccessFlag : FAILED");
								else
									webDriver.report.setMessage(restErrorResDesc);
							}
						}
						restErrorResDesc = "";
						break;

					default :
						log.info("Rest action not configured.");
				}
				break;
			}

			case OpenAPI : {
				log.info("---------OpenAPI  service call reached");
				switch (actionName) {
					case I :
						if (logicalName.equalsIgnoreCase("OASURL"))
							restUrl = ctrlValue;
						if (logicalName.equalsIgnoreCase("RequestMethod")) {
							requestMethod = ctrlValue;
						}
						if (logicalName.equalsIgnoreCase("ContentType")) {
							contentType = ctrlValue;
						}
						if (logicalName.equalsIgnoreCase("RequestJSON")) {
							requestJson = ctrlValue;
						}
						if (logicalName.equalsIgnoreCase("QueryParam")) {
							queryParam = ctrlValue;
						}
						if (logicalName.equalsIgnoreCase("AuthType")) {
							oasAuthType = ctrlValue;
						}
						if (logicalName.equalsIgnoreCase("PathParam")) {
							pathParam = ctrlValue;
						}
						if (logicalName.equalsIgnoreCase("ReadFromResponse")) {
							readFromResponse = ctrlValue;
						}
						if (logicalName.equalsIgnoreCase("UpdateReqBody")) {
							updateReqBody = ctrlValue;
						}

						if (logicalName.equals("Validate")) {
							if ("Y".equals(ctrlValue))
								validateJson = true;
							else
								validateJson = false;
						}

						break;

					case T :
						String oASFolderName;
						if (Config.executionApproach.equalsIgnoreCase("Linear")) {
							oASFolderName = testcaseID.toString();
						} else {
							oASFolderName = cycleDate;
						}
						String xmlResponse = null;
						Response response = null;
						if (TransactionType.toString().startsWith("OASServiceJSON")) {
							log.info("Inside OAS Service");

							String inputJson = "";
							String requestJsonFile = System.getProperty("user.dir") + "\\" + Config.projectName
									+ "\\Resources\\Input\\OpenAPI\\OpenAPIFiles\\" + oASFolderName + "\\" + requestJson + ".json";
							requestJsonFile = requestJsonFile.replace("/", "_");

							if (!updateReqBody.equals("")) {
								JsonUtility.updateReqJsonFile(requestJsonFile, updateReqBody);
							}

							File reqJSONFile = new File(requestJsonFile);
							if (reqJSONFile.exists()) {
								inputJson = new String(Files.readAllBytes(Paths.get(requestJsonFile)));
							}
							response = RestService.getRestResponse(MediaType.valueOf(contentType), HttpMethod.valueOf(requestMethod), restUrl,
									inputJson, oasAuthType, queryParam, pathParam);
							restResponse = response.prettyPrint();
							pathParam = "";
							queryParam = "";
							oasAuthType = "";
						}

						if (!StringUtils.isEmpty(restResponse)) {
							log.debug("Writing rest response to a file.");
							String responseFilePath = System.getProperty("user.dir") + "\\" + Config.projectName
									+ "\\Resources\\Input\\OpenAPI\\OpenAPIFiles\\" + oASFolderName + "\\" + requestJson + "_Response.json";
							responseFilePath = responseFilePath.replace("/", "_");
							File responseFile = new File(responseFilePath);
							/* try{ */
							// restResponse =
							// CommonUtils.toPrettyFormat(restResponse);
							CommonUtils.WriteToFile(responseFile, restResponse.getBytes());

							if (restResponse.startsWith("{")) {
								JSONObject resJson = new JSONObject(restResponse);
								xmlResponse = XML.toString(resJson);
							} else if (restResponse.startsWith("[")) {
								JSONArray resJson = new JSONArray(restResponse);
								xmlResponse = XML.toString(resJson);
							} else {
								log.info("Json Response is Empty or Invalid!!");
							}

							// Actual response xml
							// JSONObject resJson = new
							// JSONObject(restResponse);
							// xmlResponse = XML.toString(resJson);
							String soapnode = "<soap:Envelope xmlns:soap= \"http://schemas.xmlsoap.org/soap/envelope/\"> \n" + "<soap:Body> \n"
									+ "<ns2:serviceResponse xmlns:ns2=\"http://com/majescomastek/stgicd/ws/meta/entityinterface\"> \n"
									+ " <return> \n ";
							soapnode = soapnode + xmlResponse;
							soapnode = soapnode + " \n </return> \n </ns2:serviceResponse> \n </soap:Body> \n </soap:Envelope>";

							File xmlFilePath = new File(responseFilePath.replace(".json", ".xml"));
							CommonUtils.WriteToFile(xmlFilePath, soapnode.getBytes());

							// Expected Respons xml
							String expectedJsonFilePath = System.getProperty("user.dir") + "\\" + Config.projectName
									+ "\\Resources\\Input\\OpenAPI\\OpenAPIFiles\\" + oASFolderName + "\\" + requestJson + "_Expected.json";
							expectedJsonFilePath = expectedJsonFilePath.replace("/", "_");

							File expectedJsonFile = new File(expectedJsonFilePath);

							// String expectedJson =
							// CommonUtils.readJsonFile3(expectedJsonFilePath);
							String expectedJson = "";
							if (expectedJsonFile.exists()) {
								expectedJson = new String(Files.readAllBytes(Paths.get(expectedJsonFilePath)));

								String expectedXML = "";
								if (expectedJson.startsWith("{")) {
									JSONObject expJson = new JSONObject(expectedJson);
									expectedXML = XML.toString(expJson);
								} else if (expectedJson.startsWith("[")) {
									JSONArray expJson = new JSONArray(expectedJson);
									expectedXML = XML.toString(expJson);
								} else {
									log.info("Expected XML provided is not valid or its empty!!");
								}
								String soapexpnode = "<soap:Envelope xmlns:soap= \"http://schemas.xmlsoap.org/soap/envelope/\"> \n"
										+ "<soap:Body> \n"
										+ "<ns2:serviceResponse xmlns:ns2=\"http://com/majescomastek/stgicd/ws/meta/entityinterface\"> \n"
										+ " <return> \n ";
								soapexpnode = soapexpnode + expectedXML;
								soapexpnode = soapexpnode + " \n </return> \n </ns2:serviceResponse> \n </soap:Body> \n </soap:Envelope>";

								File expectedFilePath = new File(expectedJsonFilePath.replace(".json", ".xml"));
								CommonUtils.WriteToFile(expectedFilePath, soapexpnode.getBytes());
							}
						}

						boolean success = false;
						if (!StringUtils.isEmpty(restResponse)) {
							success = JsonUtility.validateOASJson(response);
						} else {
							log.info("Rest response returned is null or empty");
						}

						if (success) {
							webDriver.report.setStatus("PASS");
							webDriver.report.setMessage(restErrorResDesc);

							// for read tags value from response and store it
							// into excel.(23-05-2019)
							if (!readFromResponse.equals("")) {
								JsonUtility.readFromResponseJson(restResponse, readFromResponse);
								readFromResponse = "";
								restResponse = "";
							}
						} else {
							webDriver.report.setStatus("FAIL");
							if (restResponse == null) {
								webDriver.report.setMessage("Rest Response is null(Internal Server Error:500)");
							} else {
								webDriver.report.setMessage(restErrorResDesc);
							}
						}
						restErrorResDesc = "";
						break;

					default :
						log.info("Rest action not configured.");
				}
				break;
			}

			case OutPutForm :
				// ---------OutPut Form verification start------------------
				switch (actionName) {
					case I :
						if (logicalName.equalsIgnoreCase("PolicyNo"))
							PolicyNo = ctrlValue;
						else if (logicalName.equalsIgnoreCase("AccountNo"))
							AccountNo = ctrlValue;
						else if (logicalName.equalsIgnoreCase("BrokerNo"))
							BrokerNo = ctrlValue;
						else if (logicalName.equalsIgnoreCase("OutPutFormCode"))
							OutPutFormCode = ctrlValue;
						else if (logicalName.equalsIgnoreCase("OutPutForm_XML")) {
							OutPutForm_XML = ctrlValue;
							String request_xml = System.getProperty("user.dir") + "\\" + Config.projectName
									+ "\\Resources\\Input\\OutPutForm\\OutPutFormXMLFile\\" + cycleDate + "\\" + ctrlValue + ".xml";
							request_xml = request_xml.replace("/", "_");
						}
						break;
					case T :
						String OutputFormName = null;
						ResultSet OutputRecords = null;
						Connection conn = null;
						Statement st = null;

						conn = JDBCConnection.establishHTML5BillingCoreDBConn();
						st = conn.createStatement();
						if (Config.databaseType.equalsIgnoreCase("MsSQL")) {

							OutputRecords = st
									.executeQuery("SELECT OPFS.OUTPUT_RESPONSE, OPFS.POLICY_TERM_ID FROM "
											+ Config.jbeamdatabaseusername
											+ ".dbo.LOG CORE, "
											+ Config.applicationdatabaseusername
											+ ".dbo.JOB_SCHEDULE BASE , "
											+ Config.applicationdatabaseusername
											+ ".dbo.OUTPUT_FORMS_SCHEDULE OPFS WHERE CORE.BE_SEQ_NO=BASE.JOB_SEQ AND OPFS.JOB_SEQ=BASE.JOB_SEQ AND OPFS.OUTPUT_FORM_CODE = '"
											+ OutPutFormCode
											+ "' AND CORE.TASK_NAME is not null AND CORE.BATCH_NO = (Select max(batch_no)-5 from "
											+ Config.jbeamdatabaseusername
											+ ".dbo.BATCH) AND (BASE.POLICY_NO is not null OR BASE.ACCOUNT_SYSTEM_CODE is not null OR BASE.BROKER_SYSTEM_CODE is not null) AND OPFS.OUTPUT_RESPONSE is not null AND (BASE.POLICY_NO ='"
											+ PolicyNo + "' OR BASE.BROKER_SYSTEM_CODE = (select source_system_entity_code from "
											+ Config.applicationdatabaseusername
											+ ".dbo.entity_register where entity_type= 'BROKER' and SOURCE_SYSTEM_ENTITY_CODE = '" + BrokerNo
											+ "') OR BASE.ACCOUNT_SYSTEM_CODE= (select source_system_entity_code from "
											+ Config.applicationdatabaseusername
											+ ".dbo.entity_register where entity_type= 'ACCOUNT' and SOURCE_SYSTEM_ENTITY_CODE = '" + AccountNo
											+ "'))");

						} else if (Config.databaseType.equalsIgnoreCase("Oracle")) {
							OutputRecords = st
									.executeQuery("SELECT OPFS.OUTPUT_RESPONSE, OPFS.POLICY_TERM_ID FROM "
											+ Config.jbeamdatabaseusername
											+ ".LOG CORE, "
											+ Config.applicationdatabaseusername
											+ ".JOB_SCHEDULE BASE , "
											+ Config.applicationdatabaseusername
											+ ".OUTPUT_FORMS_SCHEDULE OPFS WHERE CORE.BE_SEQ_NO=BASE.JOB_SEQ AND OPFS.JOB_SEQ=BASE.JOB_SEQ AND OPFS.OUTPUT_FORM_CODE = '"
											+ OutPutFormCode
											+ "' AND CORE.TASK_NAME is not null AND CORE.BATCH_NO = (Select max(batch_no) from "
											+ Config.jbeamdatabaseusername
											+ ".BATCH) AND (BASE.POLICY_NO is not null OR BASE.ACCOUNT_SYSTEM_CODE is not null OR BASE.BROKER_SYSTEM_CODE is not null) AND OPFS.OUTPUT_RESPONSE is not null AND (BASE.POLICY_NO ='"
											+ PolicyNo + "' OR BASE.BROKER_SYSTEM_CODE = (select source_system_entity_code from "
											+ Config.applicationdatabaseusername
											+ ".entity_register where entity_type= 'BROKER' and SOURCE_SYSTEM_ENTITY_CODE = '" + BrokerNo
											+ "') OR BASE.ACCOUNT_SYSTEM_CODE= (select source_system_entity_code from "
											+ Config.applicationdatabaseusername
											+ ".entity_register where entity_type= 'ACCOUNT' and SOURCE_SYSTEM_ENTITY_CODE = '" + AccountNo + "'))");
						} else {
							log.error("Databse is not selected ");
						}

						while (OutputRecords.next()) {
							try {
								OutputFormName = OutputRecords.getString(1).toString();
							} catch (NullPointerException e) {
								log.error(e.getMessage(), e);
								log.info("OutputFormName is null for output Object " + OutPutFormCode + "Batch # " + BatchNo);
							}
						}
						OutputRecords.close();
						st.close();
						JDBCConnection.closeConnection(conn);

						responseXml = request_xml.replace(".xml", "_Response" + ".xml");
						responseXml = responseXml.replace("/", "_");
						File chkDir = new File(System.getProperty("user.dir") + "\\" + Config.projectName
								+ "\\Resources\\Input\\OutPutForm\\OutPutFormXMLFile\\" + wscycledate + "\\");
						if (chkDir.mkdir()) {
							log.info("New directory is created : " + chkDir);
						} else {
							log.info("Directory is already Exists : " + chkDir);
						}
						String actual_XML = responseXml;
						String expected_XML = request_xml;
						File file = new File(responseXml);
						log.info("file object created");

						String content = OutputFormName.toString();
						log.info("content object created");
						FileOutputStream fop = new FileOutputStream(file);
						log.info("FileOutputStream object created");
						if (!file.exists()) {
							file.createNewFile();
							log.info("New response file created");
						}
						byte[] contentInBytes = content.getBytes();
						log.info("contentInBytes done");
						fop.write(contentInBytes);
						log.info("contentInBytes written");
						fop.flush();
						log.info("flushed");
						fop.close();

						String details = "TransactionID:" + webDriver.getReport().getTestcaseId() + "|" + "CycleDate:" + cycleDate + "|"
								+ "TransactionType:" + controller.controllerTransactionType.toString() + "|" + "ExpectedValue:" + expected_XML + "|"
								+ "ActualValue:" + actual_XML;
						String f_Details = System.getProperty("user.dir") + "\\" + Config.projectName + "\\Resources\\XML_Comparison_File_Helper.txt";
						try (PrintWriter writeDetailsToFile = new PrintWriter(f_Details)) {
							writeDetailsToFile.write(details);
						} catch (Exception e) {
							log.error(e.getMessage(), e);
							log.info("Failed while writing file to " + f_Details);
						}

						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							log.error(e.getMessage(), e);
						}

						Jacob.main(Config.webserviceComparisonUtilityPath, "WebserviceVerificationIntermideate");

						try {
							Thread.currentThread().wait(2000);
							CalendarSnippet.killProcess("EXCEL.EXE");
						} catch (Exception e) {
							log.error(e.getMessage(), e);
						}

						StringBuilder comparedResult = new StringBuilder();
						try (BufferedReader ReaderDetailsFromFile = new BufferedReader(new FileReader(f_Details))) {
							// String line = ReaderDetailsFromFile.readLine();
							String line = null;
							while ((line = ReaderDetailsFromFile.readLine()) != null) {
								comparedResult.append(line);
								comparedResult.append(System.lineSeparator());
								System.out.println(line);
							}

						} catch (Exception e) {
							log.error(e.getMessage(), e);
							log.info("Failed while Reading file from " + f_Details);
						}

						if (comparedResult != null && !comparedResult.equals("")) {
							webDriver.getReport().setMessage(comparedResult.toString());
							webDriver.getReport().setStatus("FAIL");
						} else {
							webDriver.getReport().setMessage("");
							webDriver.getReport().setStatus("PASS");
						}

						break;
					// ---------OutPut Form verification end------------------
					default :
						break;
				}
				break;
			// *** Meghna

			// Control types for PDF comparison - DownloadDocument,
			// MoveDocument,
			// RenameDocument, IgnoreString, PDFDocumentCompare
			case DownloadDocument :
				// deletes all zip and pdf files from runtime download folder
				String path = Config.runtimeFileDownloadFolder;
				File dir = new File(path);
				List<File> fileList;
				File[] files = dir.listFiles();
				for (File file : files) {
					if (file.getName().endsWith("zip") || file.getName().endsWith("pdf")) {
						file.delete();
					}
				}

				// Below click downloads the document
				Actions MousebuilderClick1 = new Actions(Automation.driver);
				// highlightElement(webElement);
				Action MouseclickAction1 = MousebuilderClick1.moveToElement(webElement).clickAndHold().release().build();
				MouseclickAction1.perform();
				break;

			case MoveDocument :
				// delete file with same name from destination folder
				String[] temp = ctrlValue.split(";");

				String existingSameFile = Config.actualPdfDownloadPath + "\\" + temp[0];
				File file1 = new File(existingSameFile);

				if (file1.exists()) {
					file1.delete();
					System.out.println("Old file " + temp[0] + " deleted successfully");
				}

				// move downloaded file to final PDF subfolder
				String downloadedFileName1 = Config.runtimeFileDownloadFolder + "\\" + temp[0];
				File moveFile = new File(downloadedFileName1);

				String distinationFileName = Config.actualPdfDownloadPath + "\\" + temp[0];

				if (moveFile.renameTo(new File(distinationFileName))) {
					// if file copied successfully then delete the original file
					if (moveFile.exists()) {
						moveFile.delete();
					}
					System.out.println("File " + downloadedFileName1 + " moved successfully to " + distinationFileName);
				} else {
					System.out.println("Failed to move the file " + downloadedFileName1);
				}

				break;

			case RenameDocument :

				String mainWindow = Automation.driver.getWindowHandle();

				String temp2[] = ctrlValue.split(";");

				String downloadedFileName = Config.actualPdfDownloadPath + "\\" + temp2[0];
				String renameFileNameTo = Config.actualPdfDownloadPath + "\\" + temp2[1];

				File oldFile = new File(downloadedFileName);
				File newFile = new File(renameFileNameTo);

				if (newFile.exists()) {
					newFile.delete();
					System.out.println("Old file " + temp2[1] + " deleted successfully");
				}

				oldFile.renameTo(newFile);

				String timeStampForFileBackup = new SimpleDateFormat("ddMMMMyyyy_HH_mm_ss").format(new Date());
				String backUpFileName = renameFileNameTo.replace(".pdf", "") + "_" + timeStampForFileBackup + ".pdf";

				File backUpFile = new File(backUpFileName);

				com.google.common.io.Files.copy(newFile, backUpFile);

				Set<String> handlers1 = null;
				handlers1 = Automation.driver.getWindowHandles();
				for (String handler2 : handlers1) {
					if (!mainWindow.equalsIgnoreCase(handler2)) {
						Automation.driver.switchTo().window(handler2);
						Automation.driver.close();
					}
				}
				Automation.driver.switchTo().window(mainWindow);
				break;
			case IgnoreString :
				PDFComparisonUtil.setStringToIgnore(ctrlValue);
				break;

			case PDFDocumentCompare :
				if (!ctrlValue.isEmpty()) {
					String[] result = PDFComparisonUtil.PDFCompare(logicalName, controlTypeEnum.toString(), ctrlValue);
					if (ITAFWebDriver.isPASApplication()) {
						webDriver.report = WebHelperPAS.WriteToDetailResults(result[1], result[2], logicalName);
					}
					// Write code to pass result to claim/billing reports
				}
				break;

			// Controltypes for xml comparison
			case MoveXMLDocument :
				// move downloaded file to final XML subfolder
				String downloadedFileName2 = Config.runtimeFileDownloadFolder;
				File moveFilePath = new File(downloadedFileName2);
				File[] filesList = moveFilePath.listFiles();
				File fileToMove = null;
				String fileName = null;
				for (File file : filesList) {
					if (file.getName().endsWith("zip")) {
						fileToMove = file;
						fileName = file.getName();
					}
				}
				// delete if file exists
				if (fileToMove.exists()) {
					String distinationFileName1 = Config.actualXMLDownloadPath + "\\" + fileName;
					// String downloadedXMLFilename = fileName;
					File downloadedFile = new File(distinationFileName1);

					if (downloadedFile.exists()) {
						downloadedFile.delete();
						System.out.println("Old file " + fileName + " deleted successfully");
					}

					if (fileToMove.renameTo(new File(distinationFileName1))) {
						// if file copied successfully then delete the original
						// file
						if (fileToMove.exists()) {
							fileToMove.delete();
						}
						System.out.println("File " + fileToMove.getAbsolutePath() + " moved successfully to " + distinationFileName1);
					} else {
						System.out.println("Failed to move the file " + fileToMove.getAbsolutePath());
					}
				}
				break;

			case UnzipFolderAndRename :

				String mainWindow1 = Automation.driver.getWindowHandle();
				String newFileName = ctrlValue;

				String renameXMLFileNameTo = Config.actualXMLDownloadPath + "\\" + newFileName;

				File newXMLFile = new File(renameXMLFileNameTo);
				// System.out.println(oldXMLFile.getParentFile());

				if (newXMLFile.exists()) {
					newXMLFile.delete();
					System.out.println("Old file " + newFileName + " deleted successfully");
				}

				// Unzip zipped file
				String downloadedFileName4 = Config.actualXMLDownloadPath;
				XmlComparisonUtil.unZipXmlFolder(downloadedFileName4);

				// get the xml path from unzipped folder
				File dirExport = new File(downloadedFileName4);
				File[] exportFiles = dirExport.listFiles();

				for (File exportFile : exportFiles) {
					if (!exportFile.getName().endsWith("zip")) {
						if (exportFile.isDirectory()) {
							XmlComparisonUtil.setActualXmlPath(downloadedFileName4 + "\\" + exportFile.getName());
						}
					}
				}

				// move the xml from unzipped folder to actual download path
				String downloadedXMLFileName = XmlComparisonUtil.ActualXmlPath;
				File oldXMLFile = new File(downloadedXMLFileName);

				System.out.println("Unzip Folder " + oldXMLFile.renameTo(newXMLFile));

				String timeStampForXMLFileBackup = new SimpleDateFormat("ddMMMMyyyy_HH_mm_ss").format(new Date());
				String backUpXMLFileName = renameXMLFileNameTo.replace(".xml", "") + "_" + timeStampForXMLFileBackup + ".xml";

				File backUpXMLFile = new File(backUpXMLFileName);

				com.google.common.io.Files.copy(newXMLFile, backUpXMLFile);

				// delete the zipped and unzipped folder
				String downloadedFileName3 = Config.actualXMLDownloadPath;
				File file2 = new File(downloadedFileName3 + "\\");
				File[] filesDeleteList = file2.listFiles();
				for (File file : filesDeleteList) {
					if (file.isDirectory()) {
						FileUtils.deleteDirectory(file);
					} else if (file.getName().endsWith("zip")) {
						file.delete();
					}
				}

				Set<String> handlers2 = null;
				handlers2 = Automation.driver.getWindowHandles();
				for (String handler3 : handlers2) {
					if (!mainWindow1.equalsIgnoreCase(handler3)) {
						Automation.driver.switchTo().window(handler3);
						Automation.driver.close();
					}
				}
				Automation.driver.switchTo().window(mainWindow1);

				break;

			case XMLIgnoreTags_AttributeLevel :

				XmlComparisonUtil.setAttributeLevelTagsToIgnore(ctrlValue);

				break;

			case XMLIgnoreTags_NodeLevel :

				XmlComparisonUtil.setNodeLevelTagsToIgnore(ctrlValue);

				break;

			case XMLCompare :

				if (!ctrlValue.isEmpty()) {
					String[] result = XmlComparisonUtil.XMLCompare(logicalName, ctrlValue);
					if (ITAFWebDriver.isPASApplication()) {
						webDriver.report = WebHelperPAS.WriteToDetailResults(result[1], result[2], logicalName);
						// Write code to pass result to claim/billing reports
					}
				}
				break;
			default :
				log.info("U r in Default");
				break;
		}

		return currentValue;

	}

}
