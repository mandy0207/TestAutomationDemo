package pages;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.relevantcodes.extentreports.LogStatus;

import models.clientcases.Person;
import util.LoggerUtil;
import util.ReportUtil;

public class PersonPage extends BasePage {

	
	@FindBy(css = "a[aria-label ='People & Organisations']")
	private WebElement people_Organisation_Btn;
	
	@FindBy(css = ".page-header__right :nth-child(3)")
	private WebElement addPerson_Btn;
	
	@FindBy(css = "[class*='form-first-name']")
	private WebElement firstName_Field;
	
	@FindBy(css = "[class*='form-last-name']")
	private WebElement lastName_Field;
	
	@FindBy(css = "[class*='form-job-title']")
	private WebElement jobTitle_Field;
	
	@FindBy(css = "[placeholder='Find an organisation']")
	private WebElement organisation_Field;
	
	@FindBy(css = ".select-box__options.search-select__dropdown [class*='search-select__'] [role='option']")
	private List<WebElement> organisation_list;
	
	@FindBy(xpath = "//div[@class='filter-select__input-container']/input")
	private WebElement tags_Field;
	
	@FindBy(xpath = "//*[@class='form-actions']/button[1]")
	private WebElement saveBtn;
	
	@FindBy(css = "[class='form-field '] .select-box:nth-child(2)")
	private WebElement titlebox;
	
	@FindBy(css = ".select-box.select.title-select .select__dropdown .select-box__options div")
	private List<WebElement> titleList;
	
	@FindBy(css = ".party-details__title")
	private WebElement UiName;
	
	@FindBy(css = "[aria-label='Projects']")
	private WebElement projectsBtn;
	
	@FindBy(css = "[class*='nav-bar-item-add-text']")
	private WebElement addBtn;
	
	@FindBy(css = ".select-box__options a:nth-child(4)")
	private WebElement organisationBtn;
	
	@FindBy(xpath = "//*[@class ='ember-view form-checkbox']")
	private List<WebElement> checkbox_list;
	
	@FindBy(xpath = "//*[@aria-label ='Delete contacts']")
	private WebElement deletecontactsBtn;
	
	@FindBy(css = "[class*='delete-modal__checkbox']")
	private WebElement checkUnderstandBtn;
	
	@FindBy(xpath = "//*[@class='form-actions']//*[@type='submit']")
	private WebElement deleteBtn;
	
	@FindBy(css = ".list-actions__notice")
	private WebElement numberofcontactsBtn;
	
	@FindBy(css = " .select-box__options:nth-child(2)")
	private WebElement orgoption;
	
	
	
	
	
	
	
	public PersonPage(WebDriver driver) {
		super(driver);
		
	}
	
	
	public void SetupPerson(Person person) throws InterruptedException {
		ClickElement(people_Organisation_Btn);
		ClickElement(addPerson_Btn);
		
		
		ClickElement(titlebox);
		for(WebElement element : titleList) {
			
			if(element.getText().contains(person.title)) {
				ClickElement(element);
				break;
			}
		}
		
		
		firstName_Field.sendKeys(person.firstName);
		try {
			JavaScriptSetTextBox(jobTitle_Field, person.jobTitle);
		}
		catch(Exception e ) {
			
		}
		
		JavaScriptSetTextBox(tags_Field, person.tags);
		organisation_Field.sendKeys(person.organisation);
        Thread.sleep(2000);
        ClickElement(orgoption);
//		if(organisation_list.size()>1) {
//			ClickElement(organisation_list.get(1));
//		}
//		else {
//			ClickElement(organisation_list.get(0));
//		}
		System.out.println("Tags="+person.tags);
	
	

		saveBtn.click();
		
		
	   
	
        VerifyElementText(GetName(UiName), person.fullName);
        ClickElement(projectsBtn);
        ClickElement(addBtn);
        ClickElement(organisationBtn);
	}
	
	public void DeleteData() {
		try {
			ClickElement(people_Organisation_Btn);
			System.out.println("List size="+checkbox_list.size());
			Thread.sleep(2000);
			for(WebElement element : checkbox_list) {
				element.click();
				
			}
			try {
	            ScrollToTop();
				
				String selectedContacts=(GetElementText(numberofcontactsBtn).split(" contacts"))[0];
				System.out.println(selectedContacts);
				VerifyEqual(selectedContacts,Integer.toString(checkbox_list.size()), "");
				
				
				
				ClickElement(deletecontactsBtn);
				ClickElement(checkUnderstandBtn);
				Thread.sleep(2000);
				ClickElement(deleteBtn);
				ReportUtil.addScreenShot(LogStatus.PASS, "All contacts deleted");
			}
			catch(NoSuchElementException p) {
				ReportUtil.addScreenShot(LogStatus.FAIL, "Element NOt found");
				
			}	
		}
		catch(Exception e) {
			LoggerUtil.log("No Confirmation Box Available");
			ReportUtil.addScreenShot(LogStatus.FAIL, "No Confirmation Box Available");
		
		}
	
	}
	
	public String GetName(WebElement element) {
		
		String name=element.getText();
		return (name.split(" "))[1];
		
	}
}
