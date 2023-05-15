package models.clientcases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.exceptions.CsvException;

import util.CSVUtil;

public class CaseFiles {

	private List<CaseFile> clientFiles;
	private String CSV_File, appendix;

	public CaseFiles(String CSV_File) throws IOException, InterruptedException, CsvException {
		this.CSV_File = CSV_File;
		this.clientFiles = new ArrayList<CaseFile>();

		loadClientFilesToCreate(CSV_File);
	}

	/**
	 * The list of all casefile objects to create from the csv file
	 * 
	 * @return the list of casefile objects
	 */
	public List<CaseFile> FilesToCreate() {
		return this.clientFiles;
	}


	private void loadClientFilesToCreate(String CSV_File) throws IOException, InterruptedException, CsvException {
		int rowCount = CSVUtil.GetDatapoolRowCount(CSV_File);
		System.out.println("Total Rows="+rowCount);
		for (int i = 1; i <= rowCount; i++) {
			clientFiles.add(new CaseFile(CSV_File, i));
		}
	}

}
