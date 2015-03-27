package com.abc;

import java.util.Date;
import java.util.List;

/**
 * Created by devesh on 3/25/15.
 */
public class SavingsInterestRateCalculator implements InterestRateCalculator {
    private final RateUtil rateUtil;
    private double rateLessThan1000 = 0.001/365;
    private double rateGreaterThan1000 = 0.002/365;

    public SavingsInterestRateCalculator(RateUtil rateUtil) {

        this.rateUtil = rateUtil;
    }

    @Override
    public double getInterestEarned(List<Transaction> transactionList, Date asOfDate) {
        double interest = 0;
        double runningAmount = 0;
        DateProvider dateProvider = DateProvider.getInstance();
        Date prevTransactionDate = null;
        for(Transaction t: transactionList){
            if(prevTransactionDate == null){
                prevTransactionDate = t.transactionDate;
                runningAmount += t.amount;
                continue;
            }

            Date txnDate = t.transactionDate;
            int duration = dateProvider.daysBetween(txnDate, prevTransactionDate);
            interest += calculateInterest(runningAmount, duration);
            prevTransactionDate = t.transactionDate;
            runningAmount += t.amount + interest;
        }
        int duration =  dateProvider.daysBetween(asOfDate, prevTransactionDate);
        interest += calculateInterest(runningAmount, duration);
        return interest;
    }

    private double calculateInterest(double amount, int duration) {
        if(amount <= 1000 ){
            return rateUtil.getDailyCompoundInterest(amount, rateLessThan1000, duration) ;
        }else{
            return rateUtil.getDailyCompoundInterest(1000, rateLessThan1000, duration) + rateUtil.getDailyCompoundInterest((amount - 1000), rateGreaterThan1000, duration) ;
        }
    }
}
