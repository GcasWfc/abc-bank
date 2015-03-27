package com.abc;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    private Date asOfDate;

    @Before
    public void setUp() throws Exception{
        DateProvider dateProvider = DateProvider.getInstance();
        asOfDate = dateProvider.addDays(dateProvider.now(), 365);
    }

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING,InterestRateFactory.getInterestRateCalculator(Account.CHECKING)));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING,InterestRateFactory.getInterestRateCalculator(Account.CHECKING));
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.10004987954705946, bank.totalInterestPaid(asOfDate), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS,InterestRateFactory.getInterestRateCalculator(Account.SAVINGS));
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);


        assertEquals(2.0014967172630183, bank.totalInterestPaid(asOfDate), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS,InterestRateFactory.getInterestRateCalculator(Account.MAXI_SAVINGS));
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);

        assertEquals(153.802489402342, bank.totalInterestPaid(asOfDate), DOUBLE_DELTA);
    }

}
