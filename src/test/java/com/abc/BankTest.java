package com.abc;

import java.util.Date;

import org.junit.Test;

import com.abc.Account.AccountType;

import static org.junit.Assert.assertEquals;

public class BankTest {
	private static final double DOUBLE_DELTA = 1e-15;

	@Test
	public void customerSummary() {
		Bank bank = new Bank();
		Customer john = new Customer("John");
		john.openAccount(new Account(AccountType.CHECKING));
		bank.addCustomer(john);
		assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
	}

	@Test
	public void checkingAccount() {
		Bank bank = new Bank();
		Account checkingAccount = new Account(AccountType.CHECKING);
		Customer bill = new Customer("Bill").openAccount(checkingAccount);
		bank.addCustomer(bill);
		checkingAccount.deposit(100.0);
		int days = 365;
		assertEquals(interestDailyCompounding(100, 0.001, 365, days), bank.totalInterestPaid(new Date(), DateProvider.addDays(new Date(), days)), DOUBLE_DELTA);
	}
	
	@Test
	public void savingsAccountLowInterest() {
		Bank bank = new Bank();
		Account checkingAccount = new Account(AccountType.SAVINGS);
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
		checkingAccount.deposit(500.0);
		int days = 365;
		assertEquals(interestDailyCompounding(500, .001, 365, days), bank.totalInterestPaid(new Date(), DateProvider.addDays(new Date(), days)), DOUBLE_DELTA);
	}


	@Test
	public void savingsAccountHighInterest() {
		Bank bank = new Bank();
		Account checkingAccount = new Account(AccountType.SAVINGS);
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
		checkingAccount.deposit(1500.0);
		assertEquals(1000*.001 + 500*.002, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void maxi_savings_account() {
		Bank bank = new Bank();
		Account checkingAccount = new Account(AccountType.MAXI_SAVINGS);
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
		checkingAccount.deposit(3000.0);
		assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}
	
	private static double interestDailyCompounding(double balance, double interestRate, int compoundingFreq, int periods) {
		return balance * (Math.pow(1 + interestRate/compoundingFreq, periods) - 1);
	}
}