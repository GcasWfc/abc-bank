package com.abc;
import com.abc.account.Account;
import com.abc.account.CheckingAccount;
import com.abc.account.SavingsAccount;
import org.junit.Ignore;
import org.junit.Test;

import static com.abc.Money.amountOf;
import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){
        Account checkingAccount = new CheckingAccount(123L);
        Account savingsAccount = new SavingsAccount(456L);
        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);
        checkingAccount.deposit(amountOf(100.0));
        savingsAccount.deposit(amountOf(4000.0));
        savingsAccount.withdraw(amountOf(200.0));
        String stmt = henry.getStatement();
        System.out.print(stmt);
        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "Deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "Deposit $4,000.00\n" +
                "Withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }


    @Test
    public void testTransfer(){
        Account checkingAccount = new CheckingAccount(123L);
        Account savingsAccount = new SavingsAccount(456L);
        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);
        checkingAccount.deposit(amountOf(100.0));
        savingsAccount.deposit(amountOf(4000.0));
        savingsAccount.withdraw(amountOf(200.0));
        henry.transfer(456L,123L,amountOf(100.0));
        String stmt = henry.getStatement();
        System.out.print(stmt);
        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "Deposit $100.00\n" +
                "Transfer In $100.00\n" +
                "Total $200.00\n" +
                "\n" +
                "Savings Account\n" +
                "Deposit $4,000.00\n" +
                "Withdrawal $200.00\n" +
                "Transfer Out $100.00\n" +
                "Total $3,700.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount(123));
        assertEquals(1, oscar.getNumberOfAccounts());
    }
    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount(123));
        oscar.openAccount(new CheckingAccount(456));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new SavingsAccount(123));
        oscar.openAccount(new CheckingAccount(456));
        oscar.openAccount(new SavingsAccount(666));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}