package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {
    private static DateProvider instance = null;

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public Date now() {
        return Calendar.getInstance().getTime();
    }

    public int daysBetween(Date d1, Date d2){
        if(d1 == null || d2 == null){
            return 0;
        }
        return Math.abs((int)( (d1.getTime() - d2.getTime()) / (1000 * 60 * 60 * 24))) + 1;
    }

    public Date addDays(Date date, int days){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
}
