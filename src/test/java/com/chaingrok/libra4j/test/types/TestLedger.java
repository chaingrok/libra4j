package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.lib.ChaingrokError;
import com.chaingrok.lib.ChaingrokLog;
import com.chaingrok.lib.test.TestClass;
import com.chaingrok.libra4j.test.TestData;
import com.chaingrok.libra4j.types.AccountAddress;
import com.chaingrok.libra4j.types.AccountState;
import com.chaingrok.libra4j.types.Event;
import com.chaingrok.libra4j.types.Events;
import com.chaingrok.libra4j.types.Ledger;
import com.chaingrok.libra4j.types.LedgerInfo;
import com.chaingrok.libra4j.types.Transaction;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestLedger extends TestClass {
	
	private Long expectedEventSequenceNumber = null;
	
	
	@Test
	public void test001GetLedgerInfo() {
		Ledger ledger = new Ledger(TestData.VALIDATOR_ENDPOINT);
		LedgerInfo ledgerInfo = ledger.getLedgerInfo();
		assertEquals(1L,ledger.getRequestCount());
		assertFalse(ChaingrokError.hasLogs());
		assertNotNull(ledgerInfo);
		System.out.println("ledger info:\n" + ledgerInfo.toString());
		assertTrue(ledgerInfo.getValidators().size() >= 5);
		assertTrue(ledgerInfo.getTimestampUsecs() > (System.currentTimeMillis() -15000)*1000);
		assertNotNull(ledgerInfo.getConsensusBlockId());
		assertNotNull(ledgerInfo.getConsensusDataHash());
		assertNotNull(ledgerInfo.getTransactionAccumulatorHash());
	}
		
	
	@Test
	public void test002GetTransactionWithoutEvents() {
		long version = 1L;
		boolean withEvents = false;
		Ledger ledger = new Ledger(TestData.VALIDATOR_ENDPOINT);
		Transaction transaction = ledger.getTransaction(version,withEvents);
		assertEquals(1L,ledger.getRequestCount());
		assertFalse(ChaingrokError.hasLogs());
		assertNotNull(transaction);
		validateTransaction(transaction,version,withEvents);
	}
	
	@Test
	public void test003GetTransactionWithEvents() {
		long version = 1L;
		boolean withEvents = true;
		Ledger ledger = new Ledger(TestData.VALIDATOR_ENDPOINT);
		Transaction transaction = ledger.getTransaction(version,withEvents);
		assertEquals(1L,ledger.getRequestCount());
		assertFalse(ChaingrokError.hasLogs());
		assertNotNull(transaction);
		validateTransaction(transaction,version,withEvents);
	}
	
	@Test
	public void test004GetTransactionsWithoutEvents() {
		long version = 1L;
		long count = 10;
		boolean withEvents = false;
		Ledger ledger = new Ledger(TestData.VALIDATOR_ENDPOINT);
		ArrayList<Transaction> transactions = ledger.getTransactions(version,count,withEvents);
		assertEquals(1L,ledger.getRequestCount());
		assertFalse(ChaingrokError.hasLogs());
		assertNotNull(transactions);
		assertEquals(transactions.size(),count);
		for (Transaction transaction : transactions) {
			validateTransaction(transaction,version++,withEvents);
		}
	}
	
	@Test
	public void test005GetTransactionsWithEvents() {
		long version = 123L;
		long count = 10;
		boolean withEvents = true;
		Ledger ledger = new Ledger(TestData.VALIDATOR_ENDPOINT);
		ArrayList<Transaction> transactions = ledger.getTransactions(version,count,withEvents);
		assertEquals(1L,ledger.getRequestCount());
		assertFalse(ChaingrokError.hasLogs());
		assertNotNull(transactions);
		assertEquals(transactions.size(),count);
		for (Transaction transaction : transactions) {
			validateTransaction(transaction,version++,withEvents);
		}
	}
	
	@Test
	public void test006GetAccountState() {
		Ledger ledger = new Ledger(TestData.VALIDATOR_ENDPOINT);
		AccountAddress accountAddress = AccountAddress.ACCOUNT_ZERO;
		AccountState accountState = ledger.getAccountState(accountAddress);
		assertEquals(1L,ledger.getRequestCount());
		assertFalse(ChaingrokError.hasLogs());
		assertNotNull(accountState);
		assertTrue(accountState.getVersion() > 0);
		assertNotNull(accountState.getTransaction());
		assertNotNull(accountState.getBlob());
		assertNotNull(accountState.getBitmap());
		assertNotNull(accountState.getNonDefaultSiblingsLedgerInfoToTransactionInfoProof());
		assertNotNull(accountState.getNonDefaultSiblingsTransactionInfoToAccountProof());
	}
	
	//@Test
	public void test007GetEventsbyEventAccessPath() {
		Ledger ledger = new Ledger(TestData.VALIDATOR_ENDPOINT);
		AccountAddress accountAddress = AccountAddress.ACCOUNT_ZERO;
		accountAddress = new AccountAddress("19ec9d6b9c90d4283260e125d69682bc1551e15f8466b1aff0b9d417a3a4fb75");
		byte[] path = "/received_events_count/".getBytes();
		long count = 10L;
		ledger.getEventsbyEventAccessPath(accountAddress,path,count);
		assertEquals(1L,ledger.getRequestCount());
		assertFalse(ChaingrokError.hasLogs());
	}
	
	@Test
	public void test008GetAccountTransactionBySequenceNumber() {
		Ledger ledger = new Ledger(TestData.VALIDATOR_ENDPOINT);
		AccountAddress accountAddress = AccountAddress.ACCOUNT_ZERO;
		accountAddress = new AccountAddress("19ec9d6b9c90d4283260e125d69682bc1551e15f8466b1aff0b9d417a3a4fb75");
		long sequence = 0L;
		boolean withEvents = true;
		Transaction transaction = ledger.getAccountTransactionBySequenceNumber(accountAddress,sequence,withEvents);
		assertEquals(1L,ledger.getRequestCount());
		ChaingrokLog.purgeLogs();
		assertFalse(ChaingrokLog.hasLogs());
		assertNotNull(transaction);
		/*
		assertNotNull(transaction.getVersion());
		assertEquals(0L,(long)transaction.getMajorStatus());
		assertTrue((long)transaction.getGasUsed() == 0);
		assertNotNull(transaction.getSignedTransactionHash());
		assertNotNull(transaction.getStateRootHash());
		assertNotNull(transaction.getEventRootHash());
		*/
	}
	
	@Test
	public void test009SubmitTransaction() {
		Ledger ledger = new Ledger(TestData.VALIDATOR_ENDPOINT);
		ledger.submitTransaction();
		ChaingrokLog.purgeLogs();
	}
	
	public void validateTransaction(Transaction transaction, long version, boolean withEvents) {
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
			
	}
	
	public void validateEvent(Event event,Long sequenceNumber) {
		System.out.println(event.toString());
		if (expectedEventSequenceNumber == null) {
			expectedEventSequenceNumber = event.getSequenceNumber();
		} else {
			expectedEventSequenceNumber +=  1;
		}
		//assertEquals(expectedEventSequenceNumber,event.getSequenceNumber());
		assertNotNull(event.getEventKey());
		assertNotNull(event.getData());
	}
	

}
