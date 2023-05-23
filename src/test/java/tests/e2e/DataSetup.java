package tests.e2e;

import java.io.IOException;

import org.testng.annotations.Test;

import com.opencsv.exceptions.CsvException;
import com.relevantcodes.extentreports.LogStatus;

import models.clientcases.CaseFiles;
import models.clientcases.Client;
import tests.BaseTest;
import util.ReportUtil;

@Test
public class DataSetup extends BaseTest {

	

	@Test
	public void TestDataSetup() throws IOException, InterruptedException, CsvException {
		
		String[] subTestNumber= {"001"};
	

		LaunchApp();

	
		loginPage.Login(Username, Password);

		for (String subTest : subTestNumber) {
			ReportUtil.logMessage("Test Description", String.format("Test Data Creation - SubTest%s", subTest));
			String clientCaseFileData = String.format("e2e/SubTest%s", subTest);
            System.out.println(clientCaseFileData);
			
			CaseFiles allClientCaseFiles = new CaseFiles(clientCaseFileData);

		
			allClientCaseFiles.FilesToCreate().forEach((clientCase) -> {

				ReportUtil.logMessage("Test Data for testcase ID="+clientCase.testId, "");
				
					try {
						
						CreateClientApplication(clientCase);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			});

			
		}
		
		projectPage.GetAssertFailures();
	

	}
}
