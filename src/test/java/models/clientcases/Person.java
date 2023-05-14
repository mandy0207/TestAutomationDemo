package models.clientcases;

import java.io.IOException;

import com.opencsv.exceptions.CsvValidationException;

import models.datafileinfoobject.DataFileInfoObject;
import util.CSVUtil;

public class Person {

	
	
	public String firstName, jobTitle, organisation, tags, fullName, title, lastName;

	public Person(DataFileInfoObject dfo) throws CsvValidationException, IOException {
		String CSV_File = dfo.CSV_file;
		int row = dfo.rowNumber;
		String appendix = dfo.appendix;
		this.title = CSVUtil.retrieveDataCellValue(CSV_File, row, "Title");
		this.firstName = CSVUtil.retrieveDataCellValue(CSV_File, row, "FirstName") + appendix;
		this.jobTitle = CSVUtil.retrieveDataCellValue(CSV_File, row, "JobTitle");
		this.organisation = CSVUtil.retrieveDataCellValue(CSV_File, row, "Organisation");
		this.tags = CSVUtil.retrieveDataCellValue(CSV_File, row, "Tags");
		
       this.fullName = String.format("%s", this.firstName);
		
		System.out.println(fullName);
	}
	
}
