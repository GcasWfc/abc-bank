package com.abc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.abc.Money.amountOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Vidushi Bhandari on 3/23/2015.
 */
public class MoneyTest {
    private Money moneyOneHundred;
    private Money moneyTwoHundred;

    @Before
    public void initialise() {
        moneyTwoHundred = amountOf(200);
        moneyOneHundred = amountOf(100);
    }


    @SuppressWarnings("UnusedDeclaration")
    @Test(expected = RuntimeException.class)
    public void throw_an_exception_when_handling_a_negative_value() {
        Money negativeMoney = new Money(-100);
    }

    @Test
    public void print_its_value() {
        String print = moneyOneHundred.printValue();
        assertThat(print, is("$100.00"));
    }

    @Test
    public void perform_an_addition() {
        moneyOneHundred.add(moneyTwoHundred);
        assertThat(moneyOneHundred.printValue(), is("$300.00"));
    }

    @Test
    public void perform_a_subtraction() {
        moneyTwoHundred.deduct(moneyOneHundred);
        assertThat(moneyTwoHundred.printValue(), is("$100.00"));
    }

    @Test(expected = RuntimeException.class)
    public void perform_an_invalid_subtraction() {
        moneyOneHundred.deduct(moneyTwoHundred);
    }

}
