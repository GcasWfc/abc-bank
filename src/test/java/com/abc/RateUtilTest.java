package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by devesh on 3/26/15.
 */
public class RateUtilTest {
    private static final double DOUBLE_DELTA = 1e-15;
    private RateUtil rateUtil = RateUtil.getInstance();

//    runningAmount * (Math.pow((1+0.001/365), days) - 1 )

    @Test
    public void testDailyCompoundRate() throws Exception{
        double amount = 100;
        double rate = 0.001/365;
        double duration = 365;

        double interest = rateUtil.getDailyCompoundInterest(amount, rate, duration);
        assertEquals(0.10004987954705946, interest, DOUBLE_DELTA);
    }

    @Test
    public void testDailyCompoundRateNegative() throws Exception{
        double amount = -100;
        double rate = 0.001/365;
        double duration = 365;

        double interest = rateUtil.getDailyCompoundInterest(amount, rate, duration);
        assertEquals(-0.10004987954705946, interest, DOUBLE_DELTA);
    }

}
