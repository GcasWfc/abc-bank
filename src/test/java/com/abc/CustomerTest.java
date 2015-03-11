package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.abc.Account.AccountType;

public class CustomerTest {
	
	private static final double DOUBLE_DELTA = 1e-9;
	
	@Test
	// Test customer statement generation
	public void testStatementChkngSvngs() {
		Account checkingAccount = new Account(AccountType.CHECKING);
		Account savingsAccount = new Account(AccountType.SAVINGS);
		Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);
		checkingAccount.deposit(100.0);
		savingsAccount.deposit(4000.0);
		savingsAccount.withdraw(200.0);
		String expectedStatement = "Statement for Henry\n" 
									+ "\n" 
									+ "Checking Account\n" 
									+ " deposit $100.00\n" 
									+ "Total $100.00\n" 
									+ "\n" 
									+ "Savings Account\n" 
									+ " deposit $4,000.00\n" 
									+ " withdrawal $200.00\n" 
									+ "Total $3,800.00\n" 
									+ "\n"
									+ "Total In All Accounts $3,900.00";
		
		assertEquals(expectedStatement, henry.getStatement());
	}

	@Test
	public void testOneAccount() {
		Customer oscar = new Customer("Oscar").openAccount(new Account(AccountType.SAVINGS));
		assertEquals(1, oscar.getNumberOfAccounts());
	}

	@Test
	public void testTwoAccount() {
		Customer oscar = new Customer("Oscar").openAccount(new Account(AccountType.SAVINGS));
		oscar.openAccount(new Account(AccountType.CHECKING));
		assertEquals(2, oscar.getNumberOfAccounts());
	}

	@Test
	public void testThreeAcounts() {
		Customer oscar = new Customer("Oscar").openAccount(new Account(AccountType.SAVINGS));
		oscar.openAccount(new Account(AccountType.CHECKING));
		oscar.openAccount(new Account(AccountType.MAXI_SAVINGS));
		assertEquals(3, oscar.getNumberOfAccounts());
	}
	
	@Test
	public void transfer(){
		Customer mrGrouch = new Customer("Mr Grouch");
		Account checkingAccount = new Account(AccountType.CHECKING); 
		mrGrouch.openAccount(checkingAccount);
		checkingAccount.deposit(100);
		Account maxiSavAccount = new Account(AccountType.MAXI_SAVINGS);
		mrGrouch.openAccount(maxiSavAccount);
		
		assertEquals("100 in checking account", 100, checkingAccount.sumTransactions(), DOUBLE_DELTA);
		assertEquals("0 in maxi savings account", 0, maxiSavAccount.sumTransactions(), DOUBLE_DELTA);
		
		mrGrouch.transfer(checkingAccount, maxiSavAccount, 25.75);
		
		assertEquals("74.25 in checking account", 74.25, checkingAccount.sumTransactions(), DOUBLE_DELTA);
		assertEquals("25.75 in maxi savings account", 25.75, maxiSavAccount.sumTransactions(), DOUBLE_DELTA);
	}
}