package tests;

import java.net.MalformedURLException;
import java.time.Duration;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import context.WebDriverContext;
import factory.PageInstancesFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import listeners.LogListener;
import listeners.ReportListener;
import models.clientcases.CaseFile;
import models.clientcases.Person;
import models.clientcases.Project;
import util.LoggerUtil;
import util.ReportUtil;
import util.TestProperties;

@Listeners({ ReportListener.class, LogListener.class })
public class BaseTest {

	WebDriver driver;

	
	Person person;
	Project project;
	
	public void CreateClientApplication(CaseFile clientFile) throws InterruptedException {
		
	    person = clientFile.person;
	    project = clientFile.project;
		personPage.SetupPerson(person);
		projectPage.SetProject(project, person );
		
	}
	

	private WebDriver CreateDriver(String browserName) {
		switch (browserName.toLowerCase()) {
		case "chrome":
			return ChromeDriver();
		default:
			return EdgeDriver();
		}
	}
	
	private WebDriver ChromeDriver() {
		boolean headless = TestProperties.getProperty("headless").toLowerCase().equals("y") ? true : false;

		WebDriverManager.chromedriver().setup();
		ChromeOptions ops = new ChromeOptions();
		ops.addArguments("--remote-allow-origins=*");
		ops.addArguments("disable-infobars");
		ops.addArguments("disable-notifications");
		ops.addArguments("--remote-debugging-port=9222");

		if (headless)
			ops.addArguments("--headless");

		ops.setPageLoadStrategy(PageLoadStrategy.EAGER);
		ops.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS);

		return new ChromeDriver(ops);
	}


	private WebDriver EdgeDriver() {
		boolean headless = TestProperties.getProperty("headless").toLowerCase().equals("y") ? true : false;

		WebDriverManager.edgedriver().setup();
		EdgeOptions edgeOps = new EdgeOptions();

		if (headless)
			edgeOps.addArguments("--headless");

		edgeOps.setPageLoadStrategy(PageLoadStrategy.EAGER);
		edgeOps.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS);
		return new EdgeDriver(edgeOps);
	}

	public String Username;
	public String Password;
	
	/**
	 * Launch the browser
	 * 
	 */
	private void initBrowser() {
		String browser = TestProperties.getProperty("browser");
		int waitTimeout = Integer.parseInt(TestProperties.getProperty("waitTimeout"));

		LoggerUtil.log("Starting browser <" + browser + ">");

		driver = CreateDriver(browser);

		driver.manage().window().maximize();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitTimeout));
		WebDriverContext.setDriver(driver);

	}
	
	public pages.LoginPage loginPage ;
	public pages.PersonPage personPage;
	public pages.ProjectPage projectPage;

	public void initPages() {
		/*
		 * INITIALIZE PAGES
		 */
		LoggerUtil.log("Initialize Pages");
		loginPage = PageInstancesFactory.getInstance(pages.LoginPage.class);
		personPage = PageInstancesFactory.getInstance(pages.PersonPage.class);
		projectPage=   PageInstancesFactory.getInstance(pages.ProjectPage.class);

	}

	public void LaunchApp() {
		String activeUrl = TestProperties.getProperty("activeUrl");
		String url = TestProperties.getProperty(activeUrl);

		ReportUtil.logMessage("GoTo Application", url);

		driver.get(url);

		initPages();
	}

	@BeforeClass
	protected void setup() throws MalformedURLException {
		TestProperties.loadAllProperties();

		// TODO read values from sheet and store in properties object here
		 Username = TestProperties.getProperty("Username");
	     Password = TestProperties.getProperty("Password");

		initBrowser();
	}

	/*
	 * Steps to run after the suite runs
	 */
	@AfterSuite(alwaysRun = true)
	public void wrapAllUp(ITestContext context) {
		int total = context.getAllTestMethods().length;
		int passed = context.getPassedTests().size();
		int failed = context.getFailedTests().size();
		int skipped = context.getSkippedTests().size();

		LoggerUtil.log("Total number of testcases : " + total);
		LoggerUtil.log("Number of testcases Passed : " + passed);
		LoggerUtil.log("Number of testcases Failed : " + failed);
		LoggerUtil.log("Number of testcases Skipped  : " + skipped);
		LoggerUtil.log("************************** Test Execution Finished ************************************");
	}

	/*
	 * Steps to run after a test class finishes
	 */
	@AfterClass
	public void wrapUp() {
		if (driver != null) {
			driver.close();
			driver.quit();
			
		}
	}
}
