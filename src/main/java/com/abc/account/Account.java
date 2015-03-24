package com.abc.account;

import com.abc.Money;
import com.abc.transaction.Transaction;
import com.abc.transaction.TransactionType;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {

    private long accountId;

    protected List<Transaction> transactions;

    protected AccountType accountType;

    protected Money currentBalance;

    public Account(long accountId) {
        this.accountId = accountId;
        this.currentBalance = new Money(0);
        this.transactions = new ArrayList<Transaction>();
    }

    public long getAccountId() {
        return accountId;
    }

    public Money getCurrentBalance() {
        return currentBalance;
    }

    public void deposit(Money amount) {
        currentBalance.add(amount);
        transactions.add(new Transaction(TransactionType.DEPOSIT, amount));
    }

    public void withdraw(Money amount) {
        currentBalance.deduct(amount);
        transactions.add(new Transaction(TransactionType.WITHDRAWAL, amount));
    }

    public void transfer(Money amount) {
        currentBalance.deduct(amount);
        transactions.add(new Transaction(TransactionType.TRANSFER_OUT, amount));
    }

    public void receipt(Money amount) {
        currentBalance.add(amount);
        transactions.add(new Transaction(TransactionType.TRANSFER_IN, amount));
    }

    public void transferTo(Account targetAccount, Money transferAmount) {
        transfer(transferAmount);
        targetAccount.receipt(transferAmount);
    }

    public String getStatement() {
        StringBuilder s = new StringBuilder(accountType.toString()).append("\n");

        //Now total up all the transactions
        for (Transaction t : transactions) {
            s.append(t.getType()).append(" ").append(t.getAmount().printValue()).append("\n");
        }
        s.append("Total ").append(currentBalance.printValue());
        return s.toString();
    }

    public abstract double interestEarned();
}