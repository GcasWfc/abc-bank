package com.abc.account;

/**
 * Created by Vidushi Bhandari on 3/23/2015.
 */
public class CheckingAccount extends Account {

    public CheckingAccount(long accountId) {
        super(accountId);
        this.accountType = AccountType.CHECKING;
    }

    public double interestEarned() {
        return (currentBalance.getDoubleValue() * 0.001)/365.0;
    }

}