package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;



    private final int accountType;
    private final InterestRateCalculator calculator;
    public List<Transaction> transactions;

    public Account(int accountType, InterestRateCalculator calculator) {
        this.accountType = accountType;
        this.calculator = calculator;
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
        return calculator.getInterestEarned(transactions, asOfDate);
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
