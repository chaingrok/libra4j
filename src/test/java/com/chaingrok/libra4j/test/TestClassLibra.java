package com.chaingrok.libra4j.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.chaingrok.lib.test.TestClass;
import com.chaingrok.libra4j.types.Event;
import com.chaingrok.libra4j.types.Events;
import com.chaingrok.libra4j.types.Transaction;

public class TestClassLibra extends TestClass {
	
	private Long expectedEventSequenceNumber = null;
	
	public boolean validateTransaction(Transaction transaction, long version, boolean withEvents) {
		System.out.println(transaction.toString());
		assertEquals(version,(long)transaction.getVersion());
		assertNotNull(transaction.getSignedTransactionBytes());
		assertNotNull(transaction.getSenderAccountAddress());
		assertNotNull(transaction.getSequenceNumber());
		assertNotNull(transaction.getTransactionPayloadType());
		assertTrue((transaction.getScript() != null)
				|| (transaction.getProgram() != null)
				|| (transaction.getWriteSet() != null)
					);
		assertNotNull(transaction.getMaxGasAmount());
		assertNotNull(transaction.getGasUnitPrice());
		assertNotNull(transaction.getExpirationTime());
		assertNotNull(transaction.getSenderPublicKey());
		assertNotNull(transaction.getSignature());
		assertNotNull(transaction.getVersion());
		assertNotNull(transaction.getMajorStatus());
		assertNotNull(transaction.getGasUsed());
		assertNotNull(transaction.getSignedTransactionHash());
		assertNotNull(transaction.getEventRootHash());
		assertNotNull(transaction.getStateRootHash());
		assertNotNull(transaction.getTxnInfoSerializedSize());
		assertNotNull(transaction.getSignedTxnSerializedSize());
		if (withEvents) {
			expectedEventSequenceNumber = null;
			Events events = transaction.getEventsList();
			if ((events != null)
					&& (events.size() > 0)) {
				long count = 0L;
				for(Event event : events) {
					validateEvent(event,count++);
				}
			}
		}
		return true;
	}
	
	public boolean validateEvent(Event event,Long sequenceNumber) {
		System.out.println(event.toString());
		if (expectedEventSequenceNumber == null) {
			expectedEventSequenceNumber = event.getSequenceNumber();
		} else {
			expectedEventSequenceNumber +=  1;
		}
		//assertEquals(expectedEventSequenceNumber,event.getSequenceNumber());
		assertNotNull(event.getEventKey());
		assertNotNull(event.getData());
		return true;
	}

}
