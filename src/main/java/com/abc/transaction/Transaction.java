package com.abc.transaction;
import com.abc.DateProvider;
import com.abc.Money;
import java.util.Date;

public class Transaction {
    private final TransactionType type;
    private final Money amount;
    private final Date transactionDate;

    public Transaction(TransactionType type, Money amount) {
        this.type = type;
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().today();
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