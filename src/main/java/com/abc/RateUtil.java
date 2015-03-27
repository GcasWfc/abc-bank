package com.abc;

/**
 * Created by devesh on 3/26/15.
 */
public class RateUtil {
    private static RateUtil rateUtil = new RateUtil();
    public static RateUtil getInstance() {
        return rateUtil;
    }

    /**
     *  amount * (Math.pow((1+0.001/365), days) - 1 )
     * @param amount
     * @param rate
     * @param duration
     * @return
     */
    public double getDailyCompoundInterest(double amount, double rate, double duration) {
        return amount * (Math.pow(1+rate, duration)  -1) ;
    }
}
