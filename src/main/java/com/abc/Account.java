package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {
	
	private final AccountType accountType;
	public List<Transaction> transactions;

	public Account(AccountType accountType) {
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
		} else {
			transactions.add(new Transaction(-amount));
		}
	}
	/**
	 * @deprecated Does not compute interest correctly
	 * <p>Use interestEarned(Date startDate, Date endDate)
	 * @return
	 */
	public double interestEarned() {
		double amount = sumTransactions();
		switch (accountType) {
		case SAVINGS:
			if (amount <= 1000)
				return amount * 0.001;
			else {
				return 1 + (amount - 1000) * 0.002;
			}
			// case SUPER_SAVINGS:
			// if (amount <= 4000)
			// return 20;
				
		case MAXI_SAVINGS:
			if (amount <= 1000)
				return amount * 0.02;
			if (amount <= 2000)
				return 20 + (amount - 1000) * 0.05;
			return 70 + (amount - 2000) * 0.1;
		default:
			return amount * 0.001;
		}
	}
	
	/**
	 * Computes interest over a specified period
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public double interestEarned(Date startDate, Date endDate) {
		startDate = DateProvider.clearTimeFields(startDate);
		endDate = DateProvider.clearTimeFields(endDate);
		double totalInterest = 0;
		double balance = 0;
		
		Date lastComputedDate = startDate;
		Date currentDate = DateProvider.addADay(startDate);
		Date lastWithdrawalDate = new Date(0);	//Epoch
		for (Transaction transaction : transactions) {
			
			if (currentDate.after(endDate)) {
				//Past the period we are interested in. Done with the computation
				break;
			}
			
			if (!transaction.transactionDate.before(currentDate)) {
				//This transaction occurred after midnight of current day
				//We have the total balance for the day
				double todaysInterest = interestDaily(balance, lastComputedDate, currentDate, lastWithdrawalDate);
				totalInterest += todaysInterest;
				balance += todaysInterest;
				
				//Prepare for next interest day
				lastComputedDate = DateProvider.clearTimeFields(transaction.transactionDate);
				currentDate = DateProvider.addADay(lastComputedDate);
			}
			if (transaction.amount < 0) {
				lastWithdrawalDate = DateProvider.clearTimeFields(transaction.transactionDate);
			}
			balance += transaction.amount;
		}
		if (!currentDate.after(endDate)) {
			//We must have run out of transactions rather than breaking out of the loop
			totalInterest += interestDaily(balance, lastComputedDate, endDate, lastWithdrawalDate);
		}
		return totalInterest;
	}

	public double sumTransactions() {
		return checkIfTransactionsExist(true);
	}
	
	/**
	 * Computes interest with daily compounding
	 * interest = P * (1 + r* 1/K)^T - P
	 * where P-Principal, r - annual interest rate, K - compounding periods in a year, T - number of periods  
	 * @param balance
	 * @param lastComputeDate
	 * @param currentDate
	 * @param lastWithdrawalDate
	 * @return
	 */
	private double interestDaily(double balance, Date lastComputeDate, Date currentDate, Date lastWithdrawalDate) {
		
		if (balance <= 0) {
			return 0;	//No interest for zero or negative balances
		}
		int days = DateProvider.diffInDays(lastComputeDate, currentDate);
		
		switch (accountType) {
		case CHECKING:
			return balance * (Math.pow(1 + 0.001/365, days) - 1);
		
		case SAVINGS:
			if (balance <= 1000) {
				return balance * (Math.pow(1 + 0.001/365, days) - 1);
			}
			return 1000 * (Math.pow(1 + 0.001/365, days) - 1) + (balance  - 1000) * (Math.pow(1 + 0.002/365, days) - 1);
		
		case MAXI_SAVINGS:
			double highRate = 5.0/100;
			double lowRate = 0.1/100;
			int withdrawalCutoffDays = 10;
			int daysSinceLastWithdrawal = DateProvider.diffInDays(lastWithdrawalDate, lastComputeDate);
			if (daysSinceLastWithdrawal > withdrawalCutoffDays) {
				//10 days since the last withdrawal apply high rate to the entire period
				return balance * (Math.pow(1 + highRate/365, days) - 1);
			}
			if (days <= withdrawalCutoffDays) {
				//Apply the low rate to all days in period
				return balance * (Math.pow(1 + lowRate/365, days) - 1);
			}
			//Apply the low rate for 10 days
			double interest = balance * (Math.pow(1 + lowRate/365, withdrawalCutoffDays) - 1);
			balance += interest;
			//And the high rate of the remaining days
			interest += balance * (Math.pow(1 + highRate/365, (days - withdrawalCutoffDays)) - 1);
			return interest;
		default:
			return 0;
		}
	}
	
	
	private double checkIfTransactionsExist(boolean checkAll) {
		double amount = 0.0;
		for (Transaction t : transactions)
			amount += t.amount;
		return amount;
	}

	public AccountType getAccountType() {
		return accountType;
	}
	
	
	public enum AccountType {
		CHECKING	 (0), 
		SAVINGS 	 (1),
		MAXI_SAVINGS (2); 
		
		private int code;
		private AccountType(int code){
			this.code = code;
		}
		
		public int getCode() {
			return code;
		}

		public String prettyName() {
			switch (code) {
			case 0 :
				return "Checking Account";
			case 1:
				return "Savings Account";
			case 2:
				return "Maxi Savings Account";
			default:
				return super.toString();
			}
		}
		
		
	};

}