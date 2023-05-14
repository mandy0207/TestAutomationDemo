package models.clientcases;

import java.io.IOException;

import com.opencsv.exceptions.CsvValidationException;

import models.datafileinfoobject.DataFileInfoObject;
import util.CSVUtil;

public class Project {

	
	
	public String projectName, description;

	public Project(DataFileInfoObject dfo) throws CsvValidationException, IOException {
		String CSV_File = dfo.CSV_file;
		int row = dfo.rowNumber;
		String appendix = dfo.appendix;
		this.description = CSVUtil.retrieveDataCellValue(CSV_File, row, "Description");
		this.projectName = CSVUtil.retrieveDataCellValue(CSV_File, row, "ProjectName");
		
	}
	
}
