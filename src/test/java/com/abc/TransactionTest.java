package com.abc;
import com.abc.transaction.Transaction;
import com.abc.transaction.TransactionType;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(TransactionType.DEPOSIT,Money.amountOf(5));
        assertTrue(t instanceof Transaction);
    }
}