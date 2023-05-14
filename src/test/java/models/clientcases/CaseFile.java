package models.clientcases;

import java.io.IOException;

import com.opencsv.exceptions.CsvException;

import models.datafileinfoobject.DataFileInfoObject;
import util.CSVUtil;

public class CaseFile {

	public boolean toCreate;

	

	public Client client;
	public Person person;
	
   public Project project;
	public String testId;

	/**
	 * The object representation of a complete contact case in micase
	 * 
	 * @param CSV_File the file holding case data
	 * @param row      the row to read
	 * @throws IOException          exception
	 * @throws InterruptedException exception
	 * @throws CsvException         exception
	 */
	public CaseFile(String CSV_File, int row) throws IOException, InterruptedException, CsvException {
		DataFileInfoObject dfo = new DataFileInfoObject(CSV_File, row);

		this.testId = CSVUtil.retrieveDataCellValue(CSV_File, row, "ID");	
		this.person = new Person(dfo);
		this.project = new Project(dfo);
		
	}







}
