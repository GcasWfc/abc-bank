package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {
	private static DateProvider instance = new DateProvider();

	private DateProvider() {
	}
	
	public static DateProvider getInstance() {
		return instance;
	}

	public Date now() {
		return Calendar.getInstance().getTime();
	}
	
	/**
	 * Returns a new date with the time fields zeroed out
	 * @see clearTimeFields(Calendar calendar)
	 * @param date
	 * @return
	 */
	public static Date clearTimeFields(Date date) {
		if (date == null) {
			return null;
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);		
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}
	
	/**
	 * Adds one day to the date
	 * @param date
	 * @return new date with the day added
	 */
	public static Date addADay(Date date) {
		
		return addDays(date, 1);
	}
	
	/**
	 * Adds days to the date
	 * @param date
	 * @return new date with the day added
	 */
	public static Date addDays(Date date, int days) {
		Calendar nextDay = Calendar.getInstance();
		nextDay.setTime(date);
		nextDay.add(Calendar.DATE, days);
		
		return nextDay.getTime();
	}
	
	/**
	 * Returns difference in days between two dates
	 * @param before
	 * @param after
	 * @return
	 */
	public static int diffInDays(Date before, Date after){
		int diffInDays = (int) ((after.getTime() - before.getTime()) / (24 * 60 * 60 * 1000));

		//Adjust for daylight savings time crossing
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		start.setTime(before);
		end.setTime(after);
		
		start.add(Calendar.DAY_OF_MONTH, diffInDays);
		while (start.before(end)) {
			start.add(Calendar.DAY_OF_MONTH, 1);
			diffInDays++;
		}
		
		while (start.after(end)) {
			start.add(Calendar.DAY_OF_MONTH, -1);
			diffInDays--;
		}
		
		return diffInDays;
	}

}