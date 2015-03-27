package com.abc;

/**
 * Created by devesh on 3/25/15.
 */
public class InterestRateFactory {
    private static CheckingInterestRateCalculator checkingInterestRateCalculator = new CheckingInterestRateCalculator(RateUtil.getInstance());
    private static MaxiSavingsInterestRateCalculator maxiSavingsInterestRateCalculator = new MaxiSavingsInterestRateCalculator(RateUtil.getInstance());
    private static SavingsInterestRateCalculator savingsInterestRateCalculator = new SavingsInterestRateCalculator(RateUtil.getInstance());
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
