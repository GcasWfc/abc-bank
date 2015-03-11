package com.abc;

import java.util.ArrayList;
import java.util.Date;
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

	/**
	 * @deprecated 
	 * <p> Use totalInterestEarned(Date start, Date end)
	 * @return
	 */
	public double totalInterestEarned() {
		double total = 0;
		for (Account a : accounts)
			total += a.interestEarned();
		return total;
	}
	
	/**
	 * Computes interest earned over a specific period
	 * @param start
	 * @param end
	 * @return
	 */
	public double totalInterestEarned(Date start, Date end) {
		double total = 0;
		for (Account a : accounts)
			total += a.interestEarned(start, end);
		return total;
	}

	public String getStatement() {
		StringBuilder statement = new StringBuilder("Statement for " + name + "\n");
		double total = 0.0;
		for (Account a : accounts) {
			statement.append("\n" + statementForAccount(a) + "\n");
			total += a.sumTransactions();
		}
		statement.append("\nTotal In All Accounts " + toDollars(total));
		return statement.toString();
	}
	
	public double transfer(Account fromAccount, Account toAccount, double amount) {
		double amountWithdrawn = 0; 
		try {
			fromAccount.withdraw(amount);
			amountWithdrawn = amount;
			toAccount.deposit(amountWithdrawn);
		} catch (Exception ex) {
			//If an exception was thrown credit back any withdrawn amount
			if (amountWithdrawn > 0) {
				fromAccount.deposit(amountWithdrawn);
			}
		}
		return amount;
	}

	private String statementForAccount(Account a) {
		StringBuilder statement = new StringBuilder();
		statement.append(a.getAccountType().prettyName()).append("\n");
		// Now total up all the transactions
		double total = 0.0;
		for (Transaction t : a.transactions) {
			statement.append(" " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n");
			total += t.amount;
		}
		statement.append("Total " + toDollars(total));
		return statement.toString();
	}

	private String toDollars(double d) {
		return String.format("$%,.2f", abs(d));
	}
	
}