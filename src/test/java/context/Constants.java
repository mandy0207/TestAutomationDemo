package context;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Constants {
	/** The Constant WORKING_DIRECTORY. */
	public static final String WORKING_DIRECTORY = System.getProperty("user.dir");

	/** The Constant REPORT_DIRECTORY. */
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

	/** The directory to store extent reports */
	public final static String REPORT_DIRECTORY = String.format("%s/ExtentReports/AutomationResult.%s.html",
			WORKING_DIRECTORY, getTimestamp());

	/** The extension used by data files */
	public final static String DATAFILE_EXT = ".csv";

	/** The Constant PROJECT_NAME. */
	public final static String PROJECT_NAME = "Demo Automation Framework";

	/** The Constant EXTENT_CONFIG_PATH. */
	public final static String EXTENT_CONFIG_PATH = String.format("%s/src/test/resources/config/extent-config.xml",
			WORKING_DIRECTORY);

	/** The Constant PROPERTY_FILE_PATH. */
	public final static String PROPERTY_FILE_PATH = String.format("%s/src/test/resources/config/config.properties",
			WORKING_DIRECTORY);

	/** The directory to hold test data */
	public final static String TEST_DATA_PATH = String.format("%s/src/test/resources/data/", WORKING_DIRECTORY);

	public final static String Home = System.getProperty("user.home");

	public final static String Downloads = "/Downloads/";

	/**
	 * Get the current timestamp
	 * 
	 * @return the current timestamp
	 */
	public static String getTimestamp() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		return sdf.format(timestamp);
	}

}
