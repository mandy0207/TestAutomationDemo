package models.datafileinfoobject;

import util.DateUtil;

public class DataFileInfoObject {

	public String CSV_file;
	public String appendix;

	public int rowNumber;

	/**
	 * The object to store information about a test data file
	 * 
	 * @param CSV_file  the filename
	 * @param rowNumber the row number to care about
	 */
	public DataFileInfoObject(String CSV_file, Integer rowNumber) {
		this.CSV_file = CSV_file;
		this.rowNumber = rowNumber;
		this.appendix = DateUtil.GetCurrentDate();
	}

}
