package com.abc;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by devesh on 3/25/15.
 */
public class InterestRateCalculatorTest {
    private static final double DOUBLE_DELTA = 1e-15;
    private Date asOfDate;

    @Before
    public void setUp() throws Exception{
        DateProvider dateProvider = DateProvider.getInstance();
        asOfDate = dateProvider.addDays(dateProvider.now(), 365);
    }

    @Test
    public void testCheckingsInterestRate() throws Exception{
        InterestRateCalculator interestRateCalculator = InterestRateFactory.getInterestRateCalculator(Account.CHECKING);
        Transaction transaction1 = new Transaction(100);
        Transaction transaction2 = new Transaction(1000);
        List<Transaction> txnList = new ArrayList<Transaction>();
        txnList.add(transaction1);
        txnList.add(transaction2);
        double interest = interestRateCalculator.getInterestEarned(txnList, asOfDate);
        assertEquals(1.0999999999998789, interest, DOUBLE_DELTA);
    }
    @Test
    public void testSavingsInterestRate() throws Exception{
        InterestRateCalculator interestRateCalculator = InterestRateFactory.getInterestRateCalculator(Account.SAVINGS);
        Transaction transaction1 = new Transaction(100);
        Transaction transaction2 = new Transaction(1000);
        List<Transaction> txnList = new ArrayList<Transaction>();
        txnList.add(transaction1);
        txnList.add(transaction2);
        double interest = interestRateCalculator.getInterestEarned(txnList, asOfDate);
        assertEquals(1.19999999999989, interest, DOUBLE_DELTA);
    }

    @Test
    public void testMaxiSavingsInterestRate() throws Exception{
        InterestRateCalculator interestRateCalculator = InterestRateFactory.getInterestRateCalculator(Account.MAXI_SAVINGS);
        Transaction transaction1 = new Transaction(100);
        Transaction transaction2 = new Transaction(1000);
        List<Transaction> txnList = new ArrayList<Transaction>();
        txnList.add(transaction1);
        txnList.add(transaction2);
        double interest = interestRateCalculator.getInterestEarned(txnList, asOfDate);
        assertEquals(55.00000000000005, interest, DOUBLE_DELTA);
    }

    @Test
    public void testMaxiSavingsInterestRateWithWithdraw() throws Exception{
        InterestRateCalculator interestRateCalculator = InterestRateFactory.getInterestRateCalculator(Account.MAXI_SAVINGS);
        Transaction transaction1 = new Transaction(100);
        Transaction transaction2 = new Transaction(1000);
        Transaction transaction3 = new Transaction(-1000);
        List<Transaction> txnList = new ArrayList<Transaction>();
        txnList.add(transaction1);
        txnList.add(transaction2);
        txnList.add(transaction3);
        double interest = interestRateCalculator.getInterestEarned(txnList, asOfDate);
        assertEquals(5.000000000000004, interest, DOUBLE_DELTA);
    }
}
