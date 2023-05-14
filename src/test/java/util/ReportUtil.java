package util;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.relevantcodes.extentreports.LogStatus;

import context.WebDriverContext;
import report.ExtentReportManager;

public class ReportUtil {
	/**
	 * Adds the screen shot.
	 *
	 * @param status  the status
	 * @param message the message
	 */
	public static void addScreenShot(LogStatus status, String message) {
		String base64Image = "data:image/png;base64,"
				+ ((TakesScreenshot) WebDriverContext.getDriver()).getScreenshotAs(OutputType.BASE64);
		ExtentReportManager.getCurrentTest().log(status, message,
				ExtentReportManager.getCurrentTest().addBase64ScreenShot(base64Image));
	}

	/**
	 * Adds the screen shot.
	 *
	 * @param message the message
	 */
	public static void addScreenShot(String message) {
		String base64Image = "data:image/png;base64,"
				+ ((TakesScreenshot) WebDriverContext.getDriver()).getScreenshotAs(OutputType.BASE64);
		ExtentReportManager.getCurrentTest().log(LogStatus.INFO, message,
				ExtentReportManager.getCurrentTest().addBase64ScreenShot(base64Image));
	}

	/**
	 * log as message with status to the report
	 * 
	 * @param status  the pass/fail/info status
	 * @param message the message for the log
	 * @param details any details for the log entry
	 */
	public static void logMessage(LogStatus status, String message, String details) {
		ExtentReportManager.getCurrentTest().log(status, message, details);

		LoggerUtil.log(String.format("ReportUtil.logMessage - %s - %s, %s", status, message, details));
	}

	/**
	 * log as message to the report
	 * 
	 * @param message the message for the log
	 * @param details any details for the log entry
	 */
	public static void logMessage(String message, String details) {
		ExtentReportManager.getCurrentTest().log(LogStatus.INFO, message, details);

		LoggerUtil.log(String.format("ReportUtil.logMessage - %s - %s, %s", LogStatus.INFO, message, details));
	}
}
