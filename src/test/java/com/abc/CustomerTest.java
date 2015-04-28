package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void testTransferSuccessCase()
    {
    	Account checkingAccount = new Account(Account.CHECKING);
    	Account savingAccount = new Account(Account.SAVINGS);
    	
    	Customer oscar = new Customer("Oscar")
        	.openAccount(savingAccount);
    	oscar.openAccount(checkingAccount);
    	
    	checkingAccount.deposit(10000);
    	oscar.transfer(checkingAccount.getAccountNumber(), savingAccount.getAccountNumber(), 300);
    	
    	assertTrue(9700 == checkingAccount.sumTransactions());
    	assertTrue(300 == savingAccount.sumTransactions());
    }
    @Test (expected = IllegalArgumentException.class)
    public void testTransferAccountNotFound()
    {
    	Account checkingAccount = new Account(Account.CHECKING);
    	Account savingAccount = new Account(Account.SAVINGS);
    	
    	Customer oscar = new Customer("Oscar")
        	.openAccount(savingAccount);
    	oscar.openAccount(checkingAccount);
    	
    	checkingAccount.deposit(10000);
    	oscar.transfer(123, savingAccount.getAccountNumber(), 300);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testTransferInvalidAmount()
    {
    	Account checkingAccount = new Account(Account.CHECKING);
    	Account savingAccount = new Account(Account.SAVINGS);
    	
    	Customer oscar = new Customer("Oscar")
        	.openAccount(savingAccount);
    	oscar.openAccount(checkingAccount);
    	
    	checkingAccount.deposit(10000);
    	oscar.transfer(checkingAccount.getAccountNumber(), savingAccount.getAccountNumber(), 10001);
    }
}
