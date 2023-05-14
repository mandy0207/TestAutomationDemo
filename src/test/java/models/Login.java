package models;

import java.io.IOException;

import com.opencsv.exceptions.CsvException;

import models.datafileinfoobject.DataFileInfoObject;
import util.CSVUtil;

public class Login {

	public String Username, Password;

	/**
	 * The object to represent Login data
	 * 
	 * @param dfo the object representing the data file
	 * @throws IOException          ioexception
	 * @throws InterruptedException interruptedexception
	 * @throws CsvException         csvexception
	 */
	public Login(DataFileInfoObject dfo) throws IOException, InterruptedException, CsvException {
		String CSV_File = dfo.CSV_file;
		int row = dfo.rowNumber;

		this.Username = CSVUtil.retrieveDataCellValue(CSV_File, row, "SecondUser");
		this.Password = CSVUtil.retrieveDataCellValue(CSV_File, row, "SecondPass");
	}

	public Login(String username, String password) {
		this.Username = username;
		this.Password = password;
	}

}
