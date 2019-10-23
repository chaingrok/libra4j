package com.chaingrok.libra4j.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import com.chaingrok.lib.test.TestClass;
import com.chaingrok.libra4j.types.AccountState;
import com.chaingrok.libra4j.types.Event;
import com.chaingrok.libra4j.types.Events;
import com.chaingrok.libra4j.types.Ledger;
import com.chaingrok.libra4j.types.Transaction;

public class TestClassLibra extends TestClass {
	
	protected Ledger ledger = new Ledger(TestData.VALIDATOR_ENDPOINT);
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
	
	public boolean validateAccountState(AccountState accountState) {
		assertNotNull(accountState);
		Long version = accountState.getVersion();
		assertTrue(version > 0);
		assertNotNull(accountState.getBlob());
		assertTrue(accountState.getBlob().length > 0);
		assertNotNull(accountState.getBitmap());
		assertTrue(accountState.getBitmap().length > 0);
		ArrayList<byte[]> siblings = accountState.getNonDefaultSiblingsLedgerInfoToTransactionInfoProof();
		assertNotNull(siblings);
		assertTrue(siblings.size() > 0);
		for (byte[] sibling : siblings) {
			assertTrue(sibling.length > 0);
		}
		siblings = accountState.getNonDefaultSiblingsTransactionInfoToAccountProof();
		assertNotNull(siblings);
		assertTrue(siblings.size() > 0);
		for (byte[] sibling : siblings) {
			assertTrue(sibling.length > 0);
		}
		Transaction transaction = accountState.getTransaction();
		assertNotNull(transaction);
		//check consistency between transaction version and account state version
		assertNull(transaction.getVersion()); //info not provided in TransactionInfo when analyzing account state
		assertNotNull(transaction.getSignedTransactionHash());
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
