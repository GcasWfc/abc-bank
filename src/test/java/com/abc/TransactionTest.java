package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.Date;

import org.junit.Test;

public class TransactionTest {
	private static final double DOUBLE_DELTA = 1e-15;
	
	@Test
	public void transaction() {
		Transaction t = new Transaction(5);
		assertEquals(t.amount, 5, DOUBLE_DELTA);
		assertTrue("Transaction date before or equal to now", (DateProvider.getInstance().now().getTime() - t.transactionDate.getTime()) >= 0);
	}
	
	/**
	 * FOR Testing purposes only
	 * Allows overriding of the transaction date
	 * @param transaction
	 * @param date
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	static void __overrideTransactionDate(Transaction transaction, Date date)
		throws NoSuchFieldException, IllegalAccessException {
		Field field = Transaction.class.getDeclaredField("transactionDate");
		field.setAccessible(true);
		field.set(transaction, date);
	}

}