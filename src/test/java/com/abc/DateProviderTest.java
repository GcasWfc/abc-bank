package com.abc;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Vidushi Bhandari on 3/23/2015.
 */
public class DateProviderTest {

    @Test
    public void test_days_between_zero() {
        DateProvider dateProvider = DateProvider.getInstance();

        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();

        cal1.set(2014, 8, 1);
        cal2.set(2014, 8, 1);

        assertThat(dateProvider.daysBetween(cal1.getTime(), cal2.getTime()), is(0));
    }


    @Test
    public void test_days_between_five() {
        DateProvider dateProvider = DateProvider.getInstance();

        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();

        cal1.set(2014, 8, 1);
        cal2.set(2014, 8, 6);

        assertThat(dateProvider.daysBetween(cal1.getTime(),cal2.getTime()), is(5));
    }

    @Test
    public void test_days_between_ten() {
        DateProvider dateProvider = DateProvider.getInstance();

        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();

        cal1.set(2014, 8, 1);
        cal2.set(2014, 8, 11);

        assertThat(dateProvider.daysBetween(cal1.getTime(),cal2.getTime()), is(10));
    }

}
