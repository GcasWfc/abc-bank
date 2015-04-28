package com.abc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private final int accountNumber;
    private final List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        //prevent fast fail iterator  
        this.transactions = Collections.synchronizedList(new ArrayList<Transaction>());
        accountNumber = Math.abs((new Random()).nextInt());
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount, DateProvider.getInstance().now()));
        }
    }

    public void withdraw(double amount) {
	    if (amount <= 0 || amount > sumTransactions()) {
	        throw new IllegalArgumentException("amount must be greater than zero and less than balance");
	    } else {
	        transactions.add(new Transaction(-amount, DateProvider.getInstance().now()));
	    }
	}

    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                if (checkWithdrawWithinDays(10))
                	return amount * 0.001;
                else
                	return amount * 0.05;
            default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }
    
    private boolean checkWithdrawWithinDays(int days)
    {
    	if (days <= 0)
    		throw new IllegalArgumentException("number of days must greater than zero");
    	Date today = DateProvider.getInstance().now();
		//start loop from end to find if has withdraw within 10 days
		boolean hasWithdrawWithinDays = false;
		for(int j = transactions.size() - 1; j >= 0; j--)
		{
			Transaction tran = transactions.get(j);
			long daysInner = (today.getTime() - tran.transactionDate.getTime()) / (1000 * 60 * 60 * 24);
			if (daysInner > 10)
			{
				break;
			}
			else
			{
				if (tran.amount < 0)
				{
					hasWithdrawWithinDays = true;
					break;
				}
			}
		}
		return hasWithdrawWithinDays;
    }

    public int getAccountType() {
        return accountType;
    }
    public int getAccountNumber() {
        return accountNumber;
    }
    
    public ArrayList<Transaction> getTransactions()
    {
    	return new ArrayList<Transaction>(transactions);
    }
}
