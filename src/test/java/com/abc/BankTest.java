package com.abc;
import com.abc.account.*;
import org.junit.Test;

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
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new CheckingAccount(123);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);
        checkingAccount.deposit(amountOf(100.0));
        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new SavingsAccount(123);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
        checkingAccount.deposit(amountOf(1500.0));
        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new MaxiSavingsAccount(123);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
        checkingAccount.deposit(amountOf(3000.0));
        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
}