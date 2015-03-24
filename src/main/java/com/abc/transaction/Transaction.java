package com.abc.transaction;
import com.abc.Money;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    private final TransactionType type;
    private final Money amount;
    private final Date transactionDate;

    public Transaction(TransactionType type, Money amount) {
        this.type = type;
        this.amount = amount;
        this.transactionDate = Calendar.getInstance().getTime();
    }

    public Money getAmount() {
        return amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public TransactionType getType() {
        return type;
    }
}