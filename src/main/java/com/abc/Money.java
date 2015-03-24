package com.abc;

import java.io.PrintStream;
import java.text.DecimalFormat;

/**
 * Created by Vidushi Bhandari on 3/23/2015.
 */
public class Money {

    private double amount;

    public Money(double amount) {
        confirmValueIsNotNegative(amount);
        this.amount = amount;
    }

    public static Money amountOf(double value) {
        return new Money(value);
    }

    public void add(Money amountToAdd) {
        amount += amountToAdd.amount;
    }

    public void deduct(Money amountToDeduct) {
        if(amountToDeduct.amount > amount){
            throw new RuntimeException("Insufficient funds");
        }
        amount -= amountToDeduct.amount;
    }

    public String printValue() {
        return String.format("$%,.2f", amount);
    }

    private void confirmValueIsNotNegative(double value) {
        if (value < 0) {
            throw new RuntimeException("Currency can not hold negative value");
        }
    }

    public double getDoubleValue() {
        return amount;
    }
}