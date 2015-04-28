package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    private String statementForAccount(Account a) {
        String s = "";

       //Translate to pretty account type
        switch(a.getAccountType()){
            case Account.CHECKING:
                s += "Checking Account\n";
                break;
            case Account.SAVINGS:
                s += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        ArrayList<Transaction> transactionsCopied = a.getTransactions();
        for (Transaction t : transactionsCopied) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + toDollars(total);
        return s;
    }

    public void transfer(int fromAccountNumber, int toAccountNumber, double amount)
    {
    	Account accountFrom = null;
    	Account accountTo = null;
    	for (Account account:accounts)
    	{
    		if (accountFrom != null && accountTo != null)
    			break;
    		else if (account.getAccountNumber() == fromAccountNumber)
    			accountFrom = account;
    		else if (account.getAccountNumber() == toAccountNumber)
    			accountTo = account;
    	}
    	if (accountFrom == null || accountFrom == null)
    		throw new IllegalArgumentException("account number not found");
    	
    	double totalBalanceFrom = accountFrom.sumTransactions();
    	if (amount > totalBalanceFrom)
    		throw new IllegalArgumentException("amount must be less than balance in from account");
    	
    	synchronized(this)
    	{
    		accountFrom.withdraw(amount);
    		accountTo.deposit(amount);
    	}
    }
    
    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
