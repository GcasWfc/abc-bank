package com.abc;

import java.util.Date;
import java.util.List;

/**
 * Created by devesh on 3/25/15.
 */
public class CheckingInterestRateCalculator implements InterestRateCalculator {
    private final RateUtil rateUtil;

    public CheckingInterestRateCalculator(RateUtil rateUtil) {

        this.rateUtil = rateUtil;
    }

    @Override
    public double getInterestEarned(List<Transaction> transactionList, Date asOfDate) {
        double interest = 0;

        DateProvider dateProvider = DateProvider.getInstance();

        for(Transaction t: transactionList){
            double amount = t.amount;
            Date txnDate = t.transactionDate;
            int duration = dateProvider.daysBetween(txnDate, asOfDate);
            double rate = 0.001/365;
            interest +=  rateUtil.getDailyCompoundInterest(amount, rate, duration) ;
        }

        return interest;
    }
}
