package com.abc;

import java.util.Date;
import java.util.List;

/**
 * Created by devesh on 3/25/15.
 */
public interface InterestRateCalculator {
    public double getInterestEarned(List<Transaction> transactionList, Date asOfDate);
}
