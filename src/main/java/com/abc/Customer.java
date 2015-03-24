package com.abc;

import com.abc.account.Account;
import java.util.Map;
import java.util.TreeMap;

import static com.abc.Money.amountOf;

public class Customer {

    private String name;

    private Map<Long, Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new TreeMap<Long, Account>();
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {
        accounts.put(account.getAccountId(), account);
        return this;
    }

    public void transfer(Long fromAccountId, Long toAccountId, Money amount) {

        if (fromAccountId.equals(toAccountId)) {
            throw new RuntimeException("Invalid transaction - identical FROM and TO accounts");
        }

        Account source = accounts.get(fromAccountId);
        Account target = accounts.get(toAccountId);
        source.transferTo(target, amount);
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double interestEarned = 0;
        for (Account a : accounts.values())
            interestEarned += a.interestEarned();
        return interestEarned;
    }

    public String getStatement() {
        String statement = "Statement for " + name + "\n";
        Money total = amountOf(0.0);
        for (Account a : accounts.values()) {
            statement += "\n" + a.getStatement() + "\n";
            total.add(a.getCurrentBalance());
        }
        statement += "\nTotal In All Accounts " + total.printValue();
        return statement;
    }


}