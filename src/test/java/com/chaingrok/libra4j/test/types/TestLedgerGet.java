package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.lib.ChaingrokError;
import com.chaingrok.lib.ChaingrokLog;
import com.chaingrok.lib.test.TestClass;
import com.chaingrok.libra4j.types.AccountAddress;
import com.chaingrok.libra4j.types.AccountState;
import com.chaingrok.libra4j.types.Event;
import com.chaingrok.libra4j.types.Events;
import com.chaingrok.libra4j.types.LedgerInfo;
import com.chaingrok.libra4j.types.Transaction;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestLedgerGet extends TestClass {
	
	private Long expectedEventSequenceNumber = null;
	
	@Test
	public void test001GetLedgerInfo() {
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
		Transaction transaction = ledger.getTransaction(version,withEvents);
		assertEquals(1L,ledger.getRequestCount());
		assertFalse(ChaingrokError.hasLogs());
		assertNotNull(transaction);
		validateTransaction(transaction,version,withEvents);
	}
	
	@Test
	public void test004GetTransactionKo() {
		long version = -1L; //invalid version
		boolean withEvents = true;
		assertNull(ledger.getTransaction(version,withEvents));
	}
	
	@Test
	public void test005GetTransactionsWithoutEvents() {
		long version = 1L;
		long count = 10;
		boolean withEvents = false;
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
	public void test006GetTransactionsWithEvents() {
		long version = 123L;
		long count = 10;
		boolean withEvents = true;
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
	public void test007GetTransactionsKo() {
		long version = -1L; //invalid version
		long count = 10;
		boolean withEvents = true;
		assertNull(ledger.getTransactions(version,count,withEvents));
	}
	
	@Test
	public void test008GetAccountState() {
		AccountAddress accountAddress = AccountAddress.ACCOUNT_ZERO;
		AccountState accountState = ledger.getAccountState(accountAddress);
		System.out.println("account state " + accountAddress + ":" + "\n");
		System.out.println(accountState.toString());
		assertEquals(1L,ledger.getRequestCount());
		assertFalse(ChaingrokError.hasLogs());
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
		Transaction transaction2 = ledger.getTransaction(version);
		assertNotNull(transaction2);
		byte[] signedTransactionBytes = transaction2.getSignedTransactionBytes();
		assertNotNull(signedTransactionBytes);
		assertTrue(signedTransactionBytes.length > 0);
		//Hash hash = Hash.hash(signedTransactionBytes);
		//assertEquals(transaction.getSignedTransactionHash(),hash);
		//System.out.println("to string:" + new String(accountState.getBlob()));
	}
	
	//@Test
	public void test009GetEventsbyEventAccessPath() {
		AccountAddress accountAddress = AccountAddress.ACCOUNT_ZERO;
		accountAddress = new AccountAddress("19ec9d6b9c90d4283260e125d69682bc1551e15f8466b1aff0b9d417a3a4fb75");
		byte[] path = "/received_events_count/".getBytes();
		long count = 10L;
		ledger.getEventsbyEventAccessPath(accountAddress,path,count);
		assertEquals(1L,ledger.getRequestCount());
		assertFalse(ChaingrokError.hasLogs());
	}
	
	@Test
	public void test010GetAccountTransactionBySequenceNumber() {
		AccountAddress accountAddress = AccountAddress.ACCOUNT_ZERO;
		accountAddress = new AccountAddress("19ec9d6b9c90d4283260e125d69682bc1551e15f8466b1aff0b9d417a3a4fb75");
		long sequence = 1L;
		boolean withEvents = true;
		Transaction transaction = ledger.getAccountTransactionBySequenceNumber(accountAddress,sequence,withEvents);
		assertEquals(1L,ledger.getRequestCount());
		ChaingrokLog.purgeLogs();
		assertFalse(ChaingrokLog.hasLogs());
		assertNotNull(transaction);
		//System.out.println(transaction.toString());
		//assertNotNull(transaction.getVersion());
		/*
		assertEquals(0L,(long)transaction.getMajorStatus());
		assertTrue((long)transaction.getGasUsed() == 0);
		assertNotNull(transaction.getSignedTransactionHash());
		assertNotNull(transaction.getStateRootHash());
		assertNotNull(transaction.getEventRootHash());
		*/
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
