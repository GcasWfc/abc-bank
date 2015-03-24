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
        assertEquals(2.7397260273972606E-4, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account savingsAccount = new SavingsAccount(123);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));
        savingsAccount.deposit(amountOf(1500.0));
        assertEquals(0.005479452054794521, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new MaxiSavingsAccount(123);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));
        maxiSavingsAccount.deposit(amountOf(3000.0));
        assertEquals(0.410958904109589, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
}