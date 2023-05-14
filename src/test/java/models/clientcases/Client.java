package models.clientcases;

import java.io.IOException;

import com.opencsv.exceptions.CsvException;

import models.datafileinfoobject.DataFileInfoObject;

public class Client {

	
	

	public String testId;

	/**
	 * The object representing the client information section of a datapool
	 * 
	 * @param CSV_File the csv file to read
	 * @param row      the row number to read
	 * @throws IOException          exception
	 * @throws InterruptedException exception
	 * @throws CsvException         exception
	 */
	public Client(String CSV_File, int row) throws IOException, InterruptedException, CsvException {
		DataFileInfoObject dfo = new DataFileInfoObject(CSV_File, row);

	}
}
