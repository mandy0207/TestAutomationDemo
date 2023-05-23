package tests.e2e;

import org.testng.annotations.Test;

import tests.BaseTest;
import util.ReportUtil;

public class DeleteData extends BaseTest  {

	@Test
	public void DestroyData() {
		
		LaunchApp();

		
		loginPage.Login(Username, Password);
		ReportUtil.logMessage("Test Description **********", String.format("Test Data deletion for test case 2"));
		personPage.DeleteData();
		
		
		
	}
}
