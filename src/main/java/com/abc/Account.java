package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;



    private final int accountType;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

public void withdraw(double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("amount must be greater than zero");
    } else if(sumTransactions() < amount){
        throw new IllegalArgumentException(String.format("insufficient funds to withdraw amount: %s", amount));
    }else {
        transactions.add(new Transaction(-amount));
    }
}
    public double interestEarned(Date asOfDate){
        double interest = 0;
        double runningAmount = 0;
        Date lastWithdrawalDate = null;
        DateProvider dateProvider = DateProvider.getInstance();
        Date prevTransactionDate = null;
        for(Transaction t : transactions){
            if(t.amount < 0){
                lastWithdrawalDate = t.transactionDate;
            }
            if(prevTransactionDate == null){
                prevTransactionDate = t.transactionDate;
                runningAmount += t.amount;
                continue;
            }
            double txnAmount = t.amount;
            Date txnDate = t.transactionDate;
            int days = dateProvider.daysBetween(txnDate, prevTransactionDate);
            interest = calculateInterestEarned(interest, runningAmount, days,dateProvider.daysBetween(lastWithdrawalDate, t.transactionDate));
            prevTransactionDate = t.transactionDate;
            runningAmount += txnAmount;
        }
        interest = calculateInterestEarned(interest, runningAmount, dateProvider.daysBetween(asOfDate, prevTransactionDate),dateProvider.daysBetween(asOfDate ,lastWithdrawalDate));
        return interest;
    }

    private double calculateInterestEarned(double interest, double runningAmount, int days, int lastWithdraw) {
        switch (accountType){
            case SAVINGS:
                if(runningAmount <= 1000 ){
                    interest += runningAmount*days*0.001/365;
                }else{
                    interest += days/365 + (runningAmount - 1000)*days*0.002/365;
                }
                break;
            case MAXI_SAVINGS:
                if(lastWithdraw <= 10){
                    interest += runningAmount *days* 0.05/365;
                }else{
                    interest += runningAmount *days* 0.001/365;
                }
                break;
            default:
                interest += runningAmount * days * 0.001/365;
                break;
        }
        return interest;
    }


    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }


    public int getAccountType() {
        return accountType;
    }

}
