package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.TimeoutException;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import context.Constants;

public class CSVUtil {

	private static String GetCellValue(String fullFilePath, Integer rowNumber, String columnName)
			throws CsvValidationException, IOException {
		String cellValue = null;
		CSVReader reader = null;

		try {
			reader = new CSVReader(new FileReader(fullFilePath));
			String[] cell;
			int grabColumNum = 0;
			int countRows = 0;

			while ((cell = reader.readNext()) != null) {
				int numOfColumns = cell.length - 1;

				for (int i = 0; i <= numOfColumns; i++) {
					if (cell[i].equals(columnName)) {
						grabColumNum = i;
					}
					if (rowNumber == countRows) {
						cellValue = cell[grabColumNum];
					}

				}
				countRows++;
			}

			if (cellValue.length() == 0) {
				System.out.println("The requested cell (" + columnName + " Row " + rowNumber + ") in the CSV file "
						+ fullFilePath + " is empty...");
				if (grabColumNum == 0) {
					System.out.println(
							"The column " + columnName + " has not been found in the CSV file " + fullFilePath);
				}
			}
		} catch (TimeoutException | NoSuchElementException | ElementClickInterceptedException e) {
			// log with snapshot
			String failDetails = e.getLocalizedMessage();
			e.printStackTrace();
		}

		return cellValue;
	}

	/**
	 * Get the number of rows in a data source
	 * 
	 * @param CSV_file The file to open
	 * @return the number of rows in the file
	 * @throws IOException
	 */
	public static int GetDatapoolRowCount(String CSV_file) throws IOException {
		return GetNumberOfRows(String.format("%s/%s.csv", Constants.TEST_DATA_PATH, CSV_file));
	}


	/**
	 * Get the number of rows in a file of data
	 * 
	 * @param CSV_file The filename to search
	 * @return the number of rows
	 * @throws IOException read exception
	 */
	private static int GetNumberOfRows(String fullFilePath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fullFilePath));

		String line;
		int rowIdx = 0;
		List<String[]> allLines = new ArrayList<>();
		while ((line = br.readLine()) != null) {
			rowIdx++;
		}

		return rowIdx - 1;
	}

	/**
	 * Retrieve the value of a cell from a csv
	 * 
	 * @param CSV_file   The file to open
	 * @param rowNumber  The row number to look in
	 * @param columnName The column name to look for
	 * @return The value in the found cell
	 * @throws CsvValidationException
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws CsvException
	 */
	public static String retrieveDataCellValue(String CSV_file, Integer rowNumber, String columnName)
			throws CsvValidationException, IOException {
		String fullFilePath = Constants.TEST_DATA_PATH + CSV_file + Constants.DATAFILE_EXT;

		return GetCellValue(fullFilePath, rowNumber, columnName);

	}



}
