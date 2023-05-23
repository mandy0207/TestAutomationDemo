package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import models.clientcases.Person;
import models.clientcases.Project;

public class ProjectPage extends BasePage {

	
	
	@FindBy(css= "[class*='ember-text-field ember-view form-input']")
	private WebElement projectName;

	@FindBy(css = "[placeholder='Find a person or organisation']")
	private WebElement projectRelatesTo;
	
	@FindBy(css = ".search-select__option-party")
	private WebElement projectGuy;
	
	@FindBy(css = ".ember-text-area.ember-view.form-input-text")
	private WebElement description;
	
	@FindBy(css = ".filter-select__input-container .select-box__input")
	private WebElement tags;
	
	@FindBy(css = "button[type='submit']")
	private WebElement saveBtn;
	
	
	@FindBy(css = ".entity-details__title")
	private WebElement UiprojectText;
	
	@FindBy(css = ".kase-summary__status-blob")
	private WebElement fileStatus;
	
	
	
	
	
	
	
	public ProjectPage(WebDriver driver) {
		super(driver);
		
	}
	
	
	public void SetProject(Project project, Person person) {
		System.out.println(project.projectName);
		projectName.sendKeys(project.projectName);
		projectRelatesTo.sendKeys(person.fullName);
		
		
		try {
			ClickElement(projectGuy);
		}
		catch(Exception e) {
			projectRelatesTo.clear();
			projectRelatesTo.sendKeys(person.fullName);
			ClickElement(projectGuy);
		}
		
		description.sendKeys(project.description);
		tags.sendKeys(person.tags);
		ClickElement(saveBtn);
		
		
		VerifyElementText(UiprojectText.getText(), project.projectName);
		
		VerifyElementText(fileStatus.getText(), "Open");
		
			
			
	}
	
	
	
}
