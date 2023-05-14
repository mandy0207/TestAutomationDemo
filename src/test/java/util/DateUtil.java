package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class DateUtil {
  
	public static String LatestDate;
	
	public static String FormatDateForBC(String miCaseDate) throws ParseException {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		Date date = simpleDateFormat.parse(miCaseDate);
		return new SimpleDateFormat("M/d/yyyy").format(date);
	}

	public static String FormatDateForXML(String bcDate) throws ParseException {
		String pattern = "M/d/yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		Date date = simpleDateFormat.parse(bcDate);
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	/**
	 * Get the calculation start date to use based on the number of invoices to
	 * generate
	 * 
	 * @param invoicesToGenerate the number of invoices to generate
	 * @return the formatted calculation start date
	 */
	public static String GetCalculationStartDate(int invoicesToGenerate) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		int invoiceDays = (invoicesToGenerate - 1) * 30;

		Date now = new Date(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();

		cal.setTime(now);
		cal.add(Calendar.DATE, -(invoiceDays));

		return dateFormat.format(cal.getTime()).toString();
	}

	/**
	 * Get the current date
	 * 
	 * @return the formatted current date
	 */
	public static String GetCurrentDate() {
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int monthOffset = calendar.get(Calendar.MONTH);
		int month = monthOffset + 1;
		int year = calendar.get(Calendar.YEAR);

		String dayStr = String.valueOf(day);
		String monthStr = String.valueOf(month);
		String yearStr = String.valueOf(year);

		String currentDay = (dayStr + "-" + monthStr + "-" + yearStr);
    
		
		LatestDate = (yearStr + "-" + monthStr + "-" + dayStr);;
		// Random instance
		Random r = new Random();
		int n = r.nextInt();
		// n stores the random integer in decimal form
		String Hexadecimal = Integer.toHexString(n);

		return (Hexadecimal + "-" + currentDay);
	}
	
	public static String GetLatestCurrentDate() {
		return LatestDate;
	}
	
	public static String GetThreeMonthAheadDate() {
		
		 Calendar now = Calendar.getInstance();
		 now.add(Calendar.MONTH,3);
		 
		String three_month_aheadDate=  (now.get(Calendar.YEAR))
                 + "-"
                 + (now.get(Calendar.MONTH) + 1)
                 + "-"
                 + now.get(Calendar.DATE);
		return three_month_aheadDate;
	}
	
	public static String getNextDate(String curDate) {
        String nextDate = "";
        try {
            Calendar today = Calendar.getInstance();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(curDate);
            today.setTime(date);
            today.add(Calendar.DAY_OF_YEAR, 1);
            nextDate = format.format(today.getTime());
        } catch (Exception e) {
            return nextDate;
        }
        return nextDate;
    }
}

