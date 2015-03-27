package com.abc;

import java.util.Date;
import java.util.List;

/**
 * Created by devesh on 3/25/15.
 */
public class MaxiSavingsInterestRateCalculator implements InterestRateCalculator {
    private final RateUtil rateUtil;
    private double regularRate = 0.001/365;
    private double maxSavingsRate = 0.05/365;
    public MaxiSavingsInterestRateCalculator(RateUtil rateUtil) {

        this.rateUtil = rateUtil;
    }

    @Override
    public double getInterestEarned(List<Transaction> transactionList, Date asOfDate) {
        double interest = 0;
        double runningAmount = 0;
        DateProvider dateProvider = DateProvider.getInstance();
        Date lastWithdrawalDate = null;
        Date prevTransactionDate = null;
        for(Transaction t: transactionList){
            if(t.amount < 0){
                lastWithdrawalDate = t.transactionDate;
            }
            if(prevTransactionDate == null){
                prevTransactionDate = t.transactionDate;
                runningAmount += t.amount;
                continue;
            }

            Date txnDate = t.transactionDate;
            boolean lastWithdrawLessThan10Days =lastWithdrawalDate != null &&  dateProvider.daysBetween(lastWithdrawalDate, t.transactionDate) <= 10;
            int duration = dateProvider.daysBetween(txnDate, prevTransactionDate);
            interest += calculateInterest(runningAmount, duration, lastWithdrawLessThan10Days);
            prevTransactionDate = t.transactionDate;
            runningAmount += t.amount + interest;
        }
        boolean lastWithdrawLessThan10Days = lastWithdrawalDate != null && dateProvider.daysBetween(lastWithdrawalDate, asOfDate) <= 10;
        int duration =  dateProvider.daysBetween(asOfDate, prevTransactionDate);
        interest += calculateInterest(runningAmount, duration, lastWithdrawLessThan10Days);
        return interest;
    }

    private double calculateInterest(double amount, int duration, boolean lastWithdrawLessThan10Days) {
        if(lastWithdrawLessThan10Days ){
            return rateUtil.getDailyCompoundInterest(amount, regularRate, duration) ;
        }else{
            return rateUtil.getDailyCompoundInterest(amount, maxSavingsRate, duration) ;
        }
    }
}
