package com.abc;

import java.util.Date;

public class Transaction {
    public final double amount;
    public final Date transactionDate;

    public Transaction(double amount, Date date) {
        this.amount = amount;
        this.transactionDate = date;
    }
}
