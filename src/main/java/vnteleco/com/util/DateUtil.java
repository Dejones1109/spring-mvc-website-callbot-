package vnteleco.com.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class DateUtil {
	public static final String DATETIME_PATTERN = "dd/MM/yyyy HH:mm:ss";
	public static final String DATE_FORMAT_NORMAL = "dd/MM/yyyy";
	public static final String DATE_FORMAT_PATTERN = "yyyyMMddhhmmss";

	public DateUtil() {

	}

	public static String datetoString(Date date) {
		if (date == null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(date);
	}

	public static String timetoString(Date date) {
		if (date == null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:MM");
		return sdf.format(date);
	}

	public static String datetoString(Date date, String format) {
		if (date == null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static Date stringToDate(String str) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			date = sdf.parse(str);
		} catch (ParseException ex) {
			return null;
		}

		return date;
	}
	
	public static String timeStampToString(Timestamp timestamp) {
		if (timestamp == null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NORMAL);
		return sdf.format(timestamp);
	}

	public static Timestamp stringToTimeStamp(String str) {
		Timestamp timestamp = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
			Date parsedDate = dateFormat.parse(str);
			System.out.println("###################"+parsedDate);
			timestamp = new java.sql.Timestamp(parsedDate.getTime());
			System.out.println("---------------"+timestamp);
		} catch (ParseException ex) {
			return null;
		}

		return timestamp;
	}

	public static Date stringToTime(String str) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:MM");
			date = sdf.parse(str);
		} catch (ParseException ex) {
			return null;
		}

		return date;
	}

	public static long subDate(Date d1, Date d2) {
		long diff = Math.abs(d1.getTime() - d2.getTime());
		long diffDays = diff / 86400000L;

		return diffDays;
	}
	
	public static Date getStartDateOfMonth() {
		
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    	    
	    return calendar.getTime();
	}

	public static Date getEndDateOfMonth() {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		
	    calendar.set(Calendar.HOUR_OF_DAY, 23);
	    calendar.set(Calendar.MINUTE, 59);
	    calendar.set(Calendar.SECOND, 59);
	    calendar.set(Calendar.MILLISECOND, 999);
	    
	    return calendar.getTime();
	}
	
	public static Date getWeekStartDate() {
	    Calendar calendar = Calendar.getInstance();
	    while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
	        calendar.add(Calendar.DATE, -1);
	    }
	    return calendar.getTime();
	}
	
	public static Date getWeekEndDate() {
	    Calendar calendar = Calendar.getInstance();
	    while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
	        calendar.add(Calendar.DATE, 1);
	    }
	    calendar.add(Calendar.DATE, -1);
	    return calendar.getTime();
	}

	public static void main(String[] args) {
		System.out.println(datetoString(new Date(), DATE_FORMAT_PATTERN));
	}
	
	public static String convertMilisecondToDate(long miliseconds) {
		Date date = new Date(miliseconds);
		return DateUtil.datetoString(date,"dd/MM/yyyy HH:mm:ss");
	}

	public static String convertMiliSecondsToHMS(long miliseconds) {
		String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(miliseconds),
	            TimeUnit.MILLISECONDS.toMinutes(miliseconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(miliseconds)),
	            TimeUnit.MILLISECONDS.toSeconds(miliseconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(miliseconds)));
		
		return hms;
	}
}
