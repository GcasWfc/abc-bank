package com.abc;

import java.util.Date;
import java.util.List;

/**
 * Created by devesh on 3/25/15.
 */
public class MaxiSavingsInterestRateCalculator implements InterestRateCalculator {
    @Override
    public double getInterestEarned(List<Transaction> transactionList, Date asOfDate) {
        double interest = 0;
        double runningAmount = 0;
        Date lastWithdrawalDate = null;
        DateProvider dateProvider = DateProvider.getInstance();
        Date prevTransactionDate = null;
        for(Transaction t: transactionList){
            if(prevTransactionDate == null){
                prevTransactionDate = t.transactionDate;
                runningAmount += t.amount;
                continue;
            }
            double txnAmount = t.amount;
            Date txnDate = t.transactionDate;
            if(t.amount < 0){
                lastWithdrawalDate = t.transactionDate;
            }
            int days = dateProvider.daysBetween(txnDate, prevTransactionDate);
            if(dateProvider.daysBetween(lastWithdrawalDate, t.transactionDate) <= 10){
                interest += runningAmount * (Math.pow((1+0.001), days/365) - 1 ) ;
            }else{
                interest += runningAmount * (Math.pow((1+0.05), days/365) - 1 )  ;
            }
            prevTransactionDate = t.transactionDate;
            runningAmount += txnAmount + interest;
        }
        int days = dateProvider.daysBetween(asOfDate, prevTransactionDate);
        if(dateProvider.daysBetween(lastWithdrawalDate, asOfDate) <= 10){
            interest += runningAmount * (Math.pow((1+0.001), days/365) - 1 ) ;
        }else{
            interest += runningAmount * (Math.pow((1+0.05), days/365) - 1 )  ;
        }
        return interest;
    }
}
