package com.abc;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by devesh on 3/24/15.
 */
public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-4;
    private Date asOfDate;

    @Before
    public void setUp() throws Exception{
        DateProvider dateProvider = DateProvider.getInstance();
        asOfDate = dateProvider.addDays(dateProvider.now(), 365);
    }


    @Test
    public void testDepositToAccount() throws Exception{
        Account checkingAccount = new Account(Account.CHECKING, InterestRateFactory.getInterestRateCalculator(Account.CHECKING));
        checkingAccount.deposit(300.5);
        assertEquals(300.5, checkingAccount.sumTransactions(), DOUBLE_DELTA);
    }

    @Test
    public void testWithdrawFromAccount() throws Exception{
        Account checkingAccount = new Account(Account.CHECKING, InterestRateFactory.getInterestRateCalculator(Account.CHECKING));
        checkingAccount.deposit(300.5);
        checkingAccount.withdraw(300.5);
        assertEquals(0, checkingAccount.sumTransactions(), DOUBLE_DELTA);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testFailedDepositToAccount() throws Exception{
        Account checkingAccount = new Account(Account.CHECKING, InterestRateFactory.getInterestRateCalculator(Account.CHECKING));
        checkingAccount.deposit(-200);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testFailedWithdrawFromAccount() throws Exception{
        Account checkingAccount = new Account(Account.CHECKING,InterestRateFactory.getInterestRateCalculator(Account.CHECKING));
        checkingAccount.deposit(300.5);
        checkingAccount.withdraw(-200);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testFailedOverDraftFromAccount() throws Exception{
        Account checkingAccount = new Account(Account.CHECKING,InterestRateFactory.getInterestRateCalculator(Account.CHECKING));
        checkingAccount.deposit(300.5);
        checkingAccount.withdraw(400);
    }

    @Test
    public void testCheckingInterestRate() throws Exception{
        Account checkingAccount = new Account(Account.CHECKING,InterestRateFactory.getInterestRateCalculator(Account.CHECKING));
        checkingAccount.deposit(100);
        assertEquals(0.1, checkingAccount.interestEarned(asOfDate), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {

        Account maxSavingsAccount = new Account(Account.MAXI_SAVINGS,InterestRateFactory.getInterestRateCalculator(Account.MAXI_SAVINGS));


        maxSavingsAccount.deposit(3000.0);

        assertEquals(150.0, maxSavingsAccount.interestEarned(asOfDate), DOUBLE_DELTA);
    }
}
