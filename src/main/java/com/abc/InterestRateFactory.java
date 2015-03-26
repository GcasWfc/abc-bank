package com.abc;

/**
 * Created by devesh on 3/25/15.
 */
public class InterestRateFactory {
    private static CheckingInterestRateCalculator checkingInterestRateCalculator = new CheckingInterestRateCalculator();
    private static MaxiSavingsInterestRateCalculator maxiSavingsInterestRateCalculator = new MaxiSavingsInterestRateCalculator();
    private static SavingsInterestRateCalculator savingsInterestRateCalculator = new SavingsInterestRateCalculator();
    public static InterestRateCalculator getInterestRateCalculator(int accountType){
        switch (accountType){
            case Account.SAVINGS:
                return savingsInterestRateCalculator;
            case Account.MAXI_SAVINGS:
                return maxiSavingsInterestRateCalculator;
            default:
                return checkingInterestRateCalculator;
        }
    }
}
