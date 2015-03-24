package com.abc.transaction;

/**
 * Created by Vidushi Bhandari on 3/23/2015.
 */
public enum TransactionType {

    DEPOSIT("Deposit"),
    WITHDRAWAL("Withdrawal"),
    TRANSFER_OUT("Transfer Out"),
    TRANSFER_IN("Transfer In");

    private String value;

    TransactionType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}