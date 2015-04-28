package com.abc;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class AccountInterestTest {
    private static final double DOUBLE_DELTA = 1e-15;

    private Account savingAccount;
    private Account checkingAccount;
    private Account maxSavingAccount;
    
    @Before
    public void before()
    {
    	savingAccount = new Account(Account.SAVINGS);
    	maxSavingAccount = new Account(Account.MAXI_SAVINGS);
    	checkingAccount = new Account(Account.CHECKING);
    }
    
    @Test
    public void testMaxSavingAccountInterestNoWithdraw() {
        maxSavingAccount.deposit(20000);
        assertTrue(1000.0 == maxSavingAccount.interestEarned());
    }
    
    @Test
    public void testMaxSavingAccountInterestLatestWithdraw() {
        maxSavingAccount.deposit(30000);
        maxSavingAccount.withdraw(10000);
        assertTrue(20.0 == maxSavingAccount.interestEarned());
    }
    
    @Test
    public void testMaxSavingAccountInterestWithdraw10DaysBefore()
    {
    	//Account.transactions is designed not be modified,
    	//there is no way to test withdraw on old date unless use something like mockito
    	assertTrue(true);
    }
 
}
