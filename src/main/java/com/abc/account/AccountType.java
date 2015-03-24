package com.abc.account;

/**
 * Created by Vidushi Bhandari on 3/23/2015.
 */
public enum AccountType {

    CHECKING("Checking Account"),
    SAVINGS("Savings Account"),
    MAXI_SAVINGS("Maxi Savings Account");

    private String value;

    AccountType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
