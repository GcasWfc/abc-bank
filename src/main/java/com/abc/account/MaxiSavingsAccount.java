package com.abc.account;

import com.abc.DateProvider;
import com.abc.transaction.Transaction;
import com.abc.transaction.TransactionType;

import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Created by Vidushi Bhandari on 3/23/2015.
 */
public class MaxiSavingsAccount extends Account {

    public MaxiSavingsAccount(long accountId) {
        super(accountId);
        this.accountType = AccountType.MAXI_SAVINGS;
    }

    /*
    ** interest accrued daily - compounding is out of scope for this exercise
    ** Change Maxi-Savings accounts to have an
    **interest rate of 5% assuming no withdrawals in the past 10 days otherwise 0.1%
     */
    public double interestEarned() {
        double amount = currentBalance.getDoubleValue();

        /*
        if (amount <= 1000)
            return (amount * 0.02)/365.0;

        if (amount <= 2000)
            return (20 + (amount-1000) * 0.05)/365.0;

        return (70 + (amount-2000) * 0.1)/365.0;
    */
        if(checkIfWithdrawalDoneInLastTenDays())
            return (currentBalance.getDoubleValue() * 0.001)/365.0;

        return (currentBalance.getDoubleValue() * 0.05)/365.0;
    }

    private boolean checkIfWithdrawalDoneInLastTenDays() {
        Date today = DateProvider.getInstance().today();

        for(Transaction transaction : transactions){
            if(transaction.getType() == TransactionType.WITHDRAWAL || transaction.getType() == TransactionType.TRANSFER_OUT){
                if(DateProvider.getInstance().daysBetween(transaction.getTransactionDate(), today) < 10)
                    return true;
            }
        }
        return false;
    }
}
