/**
 * 
 */
package com.abc;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import com.abc.Account.AccountType;

public class AccountTest {
	private static final double DOUBLE_DELTA = 1e-9;
	
	@Test
	public void checkingInterest4aDay() {
		Account account = new Account(AccountType.CHECKING);
		account.deposit(1000);
		double totalInterest = 1000 * Math.pow(1 + 0.1/100/365, 1) - 1000;
		Date tomorrow = DateProvider.addADay(new Date());
		assertEquals("Interest for 1 day", totalInterest, account.interestEarned(new Date(0), tomorrow), DOUBLE_DELTA);
	}
	
	@Test
	public void checkingInterest4aDayMulitpleTrans() throws Exception {
		Account account = new Account(AccountType.CHECKING);
		account.deposit(1000);
		account.deposit(1000);
		double totalInterest = 2000 * Math.pow(1 + 0.1/100/365, 1) - 2000;
		Date tomorrow = DateProvider.addADay(new Date());
		account.withdraw(500);
		TransactionTest.__overrideTransactionDate(account.transactions.get(2), tomorrow);
		double balance = 1500 + totalInterest;
		totalInterest += balance * Math.pow(1 + 0.1/100/365, 1) - balance;
		Date dayAfterTomorrow = DateProvider.addADay(tomorrow);
		assertEquals("Interest for 2000 1 day; 1500 1 day", totalInterest, account.interestEarned(new Date(0), dayAfterTomorrow), DOUBLE_DELTA);
	}
	
	@Test
	public void checkingInterest2Tran3Days() throws Exception{
		Account account = new Account(AccountType.CHECKING);
		account.deposit(1000);
		Date dayBeforeYesterday = DateProvider.addDays(new Date(), -2);
		TransactionTest.__overrideTransactionDate(account.transactions.get(0), dayBeforeYesterday);
		double interest = 1000 * Math.pow(1 + 0.1/100/365, 2) - 1000;
		
		account.deposit(500);
		TransactionTest.__overrideTransactionDate(account.transactions.get(1), DateProvider.addDays(new Date(), -1));
		interest += 500 * Math.pow(1 + 0.1/100/365, 1) - 500;
		assertEquals(interest, account.interestEarned(dayBeforeYesterday, new Date()), DOUBLE_DELTA);
	}

	@Test
	public void checkingInterest2Tran3Days1Excluded() throws Exception{
		Account account = new Account(AccountType.CHECKING);
		account.deposit(1000);
		double interest = 1000 * Math.pow(1 + 0.1/100/365, 2) - 1000;
		
		account.deposit(500);
		TransactionTest.__overrideTransactionDate(account.transactions.get(1), DateProvider.addDays(new Date(), 1));
		interest += 500 * Math.pow(1 + 0.1/100/365, 1) - 500;
		
		//This transaction should get excluded
		account.deposit(10000);
		TransactionTest.__overrideTransactionDate(account.transactions.get(2), DateProvider.addDays(new Date(), 2));
		
		Date dayAftertomorrow = DateProvider.addDays(new Date(), 2);
		assertEquals(interest, account.interestEarned(new Date(), dayAftertomorrow), DOUBLE_DELTA);
	}

	
	@Test
	public void savingsAccountInterest10Days(){
		Account account = new Account(AccountType.SAVINGS);
		account.deposit(5000);
		
		int days = 10;
		double interest = 1000 * Math.pow(1 + 0.1/100/365, days) - 1000;
		interest += 4000 * Math.pow(1 + 0.2/100/365, days) - 4000;
		
		Date todayPlus10 = DateProvider.addDays(new Date(), days);
		assertEquals("0.1% on the first 1K and 0.2% on the remainder", interest, account.interestEarned(new Date(), todayPlus10), DOUBLE_DELTA);
	}
	

	@Test
	public void maxSavingsNoWithdrawal(){
		Account account = new Account(AccountType.MAXI_SAVINGS);
		double depositAmount = 46323;
		account.deposit(depositAmount);
		
		int days = 50;
		double interest = depositAmount * Math.pow(1 + 5.0/100/365, days) - depositAmount;
		
		Date futureDate = DateProvider.addDays(new Date(), days);
		assertEquals("5% on the deposit", interest, account.interestEarned(new Date(), futureDate), DOUBLE_DELTA);
	}
	
	@Test
	public void maxSavingsWithdrawal5Days(){
		Account account = new Account(AccountType.MAXI_SAVINGS);
		account.deposit(7892);
		account.withdraw(487.75);
		
		double balance = 7892 - 487.75;
		int days = 5;
		double interest = balance * Math.pow(1 + 0.1/100/365, days) - balance;

		Date futureDate = DateProvider.addDays(new Date(), days);
		assertEquals("0.1% for 5 days", interest, account.interestEarned(new Date(), futureDate), DOUBLE_DELTA);
	}

	
	@Test
	public void maxSavingsWithdrawalAbove10Days(){
		Account account = new Account(AccountType.MAXI_SAVINGS);
		account.deposit(347832);
		account.withdraw(74.32);
		
		double balance = 347832 - 74.32;
		int days = 50;
		double interest = balance * Math.pow(1 + 0.1/100/365, 10) - balance;
		balance += interest;
		interest += balance * Math.pow(1 + 5.0/100/365, days-10) - balance;
		
		Date futureDate = DateProvider.addDays(new Date(), days);
		assertEquals("0.1% for 10 days and 5% for 40 days", interest, account.interestEarned(new Date(), futureDate), DOUBLE_DELTA);
	}

}
