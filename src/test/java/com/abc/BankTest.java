package com.abc;
import com.abc.account.*;
import org.junit.Test;

import java.util.Date;

import static com.abc.Money.amountOf;
import static org.junit.Assert.assertEquals;

public class BankTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount(123));
        bank.addCustomer(john);
        System.out.print(bank.customerSummary());
        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void multipleCustomersSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount(123));
        bank.addCustomer(john);

        Customer galt = new Customer("Galt");
        galt.openAccount(new CheckingAccount(456)).openAccount( new SavingsAccount(789));
        bank.addCustomer(galt);

        System.out.print(bank.customerSummary());
        assertEquals("Customer Summary\n - John (1 account)\n" +
                      " - Galt (2 accounts)"  , bank.customerSummary());
    }

    @Test(expected = RuntimeException.class)
    public void queryBankForFirstCustomerWhenNoneExist() {
        Bank bank = new Bank();
        bank.getFirstCustomer();
    }

    @Test
    public void checkingAccountIntrest() {
        Bank bank = new Bank();
        Account checkingAccount = new CheckingAccount(123);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);
        checkingAccount.deposit(amountOf(100.0));
        assertEquals(2.7397260273972606E-4, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savingsAccountIntrest() {
        Bank bank = new Bank();
        Account savingsAccount = new SavingsAccount(123);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));
        savingsAccount.deposit(amountOf(1500.0));
        assertEquals(0.005479452054794521, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccountInterest() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new MaxiSavingsAccount(123);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));
        maxiSavingsAccount.deposit(amountOf(3000.0));
        assertEquals(0.410958904109589, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccountInterestWhenWithDrawalinLastTenDays() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new MaxiSavingsAccount(123);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        Date today = DateProvider.getInstance().today();
        Date tenDaysAgo = new Date(today.getTime() - 10*DAY_IN_MS);
        Date fiveDaysAgo = new Date(today.getTime() - 5*DAY_IN_MS);
        maxiSavingsAccount.deposit(amountOf(6000.0),tenDaysAgo);
        maxiSavingsAccount.withdraw(amountOf(3000.0), fiveDaysAgo);
        assertEquals(0.00821917808219178, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
}