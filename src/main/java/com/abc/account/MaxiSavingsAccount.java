package com.abc.account;

/**
 * Created by Vidushi Bhandari on 3/23/2015.
 */
public class MaxiSavingsAccount extends Account {

    public MaxiSavingsAccount(long accountId) {
        super(accountId);
        this.accountType = AccountType.MAXI_SAVINGS;
    }

    public double interestEarned() {
        double amount = currentBalance.getDoubleValue();

        if (amount <= 1000)
            return (amount * 0.02)/365.0;

        if (amount <= 2000)
            return (20 + (amount-1000) * 0.05)/365.0;

        return (70 + (amount-2000) * 0.1)/365.0;
    }

}
