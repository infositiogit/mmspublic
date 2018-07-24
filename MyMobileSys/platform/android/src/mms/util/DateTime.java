package mms.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTime {

	private java.util.Date dateNative;
	private Calendar cal;
	
	public DateTime() {
		dateNative = new java.util.Date();
		cal = Calendar.getInstance();
		cal.setTime(dateNative);
	}

	public String toStringFormat(String format) {
		SimpleDateFormat sdfDate = new SimpleDateFormat(format);
		String strDate = sdfDate.format(dateNative);
		return strDate;
	}

	public void setDate(int year, int month, int day) {
		cal.set(year, month - 1, day, 0, 0, 0);
		dateNative = cal.getTime();
	}

	public void setDate(int year, int month, int day, int hour, int minutes, int seconds) {
		cal.set(year, month - 1, day, hour, minutes, seconds);
		dateNative = cal.getTime();
	}
	
	public int getMonth() {
		return(cal.get(Calendar.MONTH));
	}

	public int getYear() {
		return(cal.get(Calendar.YEAR));
	}

	public int getDay() {
		return(cal.get(Calendar.DATE));
	}
	
	public void addMonth(int add) {
		cal.add(Calendar.MONTH, add);
		dateNative = cal.getTime();
	}
	
	public void addDays(int add) {
		cal.add(Calendar.DATE, add);
		dateNative = cal.getTime();
	}
	
	public java.util.Date getDate() {
		return dateNative;
	}
	
}