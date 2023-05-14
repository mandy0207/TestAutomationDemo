package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

	
	@FindBy(id = "login:usernameDecorate:username")
	private WebElement username;

	@FindBy(css = "input[type='password']")
	private WebElement password;
	
	@FindBy(css = "input[id='login:login']")
	private WebElement loginBtn;
	
	public LoginPage(WebDriver driver) {
		super(driver);
		
	}

	public void Login(String Username, String Password) {
		System.out.println("Username & Password="+Username+ Password);
		SetTextBox(username, Username);
		SetTextBox(password, Password);
		ClickElement(loginBtn);
		
	}
	
	
}
