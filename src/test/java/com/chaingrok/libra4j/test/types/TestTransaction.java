package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.misc.Libra4jLog;
import com.chaingrok.libra4j.misc.Utils;
import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.Code;
import com.chaingrok.libra4j.types.Event;
import com.chaingrok.libra4j.types.Hash;
import com.chaingrok.libra4j.types.Program;
import com.chaingrok.libra4j.types.Transaction;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestTransaction extends TestClass {
	
	@Test
	public void test001_newInstance() {
		Transaction transaction = new Transaction();
		assertNull(transaction.getVersion());
		Long version = new Long(0L);
		transaction.setVersion(version);
		assertSame(version,transaction.getVersion());
		//
		assertNull(transaction.getSequenceNumber());
		Long sequenceNumber = new Long(0L);
		transaction.setSequenceNumber(sequenceNumber);
		assertSame(sequenceNumber,transaction.getSequenceNumber());
		//
		assertNull(transaction.getSenderPublicKey()); //TODO
		//
		assertNull(transaction.getSignature());//TODO
		//
		assertNull(transaction.getMaxGasAmount());
		Long maxGasAmount = new Long(0L);
		transaction.setMaxGasAmount(maxGasAmount);
		assertSame(maxGasAmount,transaction.getMaxGasAmount());
		//
		assertNull(transaction.getExpirationTime());
		Long expirationTime = new Long(0L);
		transaction.setExpirationTime(expirationTime);
		assertSame(expirationTime,transaction.getExpirationTime());
		//
		assertNull(transaction.getGasUnitPrice());
		Long gasUnitPrice = new Long(0L);
		transaction.setGasUnitPrice(gasUnitPrice);
		assertSame(gasUnitPrice,transaction.getGasUnitPrice());
		//
		assertNull(transaction.getGasUsed());
		Long gasUsed = new Long(0L);
		transaction.setGasUsed(gasUsed);
		assertSame(gasUsed,transaction.getGasUsed());
		//
		assertNull(transaction.getProgram());
		Program program  =new Program();
		transaction.setProgram(program);
		assertSame(program, transaction.getProgram());
		//
		assertNull(transaction.getTxnInfoSerializedSize());
		Integer txnInfoSerializedSize = new Integer(0);
		transaction.setTxnInfoSerializedSize(txnInfoSerializedSize);
		assertSame(txnInfoSerializedSize,transaction.getTxnInfoSerializedSize());
		//
		assertNull(transaction.getSignedTxnSerializedSize());
		Integer signedTxnSerializedSize = new Integer(0);
		transaction.setSignedTxnSerializedSize(signedTxnSerializedSize);
		assertSame(signedTxnSerializedSize,transaction.getSignedTxnSerializedSize());
		//
		assertNull(transaction.getSignedTransactionHash());
		byte[] hashBytes = Utils.getByteArray(Hash.BYTE_LENGTH);
		Hash signedTransactionHash = new Hash(hashBytes);
		transaction.setSignedTransactionHash(signedTransactionHash);
		assertSame(signedTransactionHash,transaction.getSignedTransactionHash());
		//
		assertNull(transaction.getEventRootHash());
		byte[] hashBytes2 = Utils.getByteArray(Hash.BYTE_LENGTH);
		Hash eventRootHash = new Hash(hashBytes2);
		transaction.setEventRootHash(eventRootHash);
		assertSame(eventRootHash,transaction.getEventRootHash());
		//
		assertNull(transaction.getStateRootHash());
		byte[] hashBytes3 = Utils.getByteArray(Hash.BYTE_LENGTH);
		Hash stateRootHash = new Hash(hashBytes3);
		transaction.setStateRootHash(stateRootHash);
		assertSame(stateRootHash,transaction.getStateRootHash());
		//
		assertNull(transaction.getFullTxnBytes());
		byte[] fullTxnBytes = Utils.getByteArray(50);
		transaction.setFullTxnBytes(fullTxnBytes);
		assertSame(fullTxnBytes,transaction.getFullTxnBytes());
		//
		assertNull(transaction.getRawTxnBytes());
		byte[] rawTxnBytes = Utils.getByteArray(30);
		transaction.setRawTxnBytes(rawTxnBytes);
		assertSame(rawTxnBytes,transaction.getRawTxnBytes());
		//
		assertNull(transaction.getEventsList());
		ArrayList<Event> eventsList = new ArrayList<Event>();
		transaction.setEventsList(eventsList);
		assertSame(eventsList,transaction.getEventsList());
	}
	
	@Test
	public void test002_checkType() {
		Transaction transaction = new Transaction();
		Program program= new Program();
		program.setCode(Code.MINT);
		transaction.setProgram(program);
		assertEquals(Transaction.Type.MINT,Transaction.Type.get(transaction));
		//
		byte[] bytes = {0x00};
		Code code = new Code(bytes);
		program.setCode(code);
		assertEquals(Transaction.Type.UNKNOWN,Transaction.Type.get(transaction));
		assertTrue(Libra4jLog.hasLogs());
		Libra4jLog.purgeLogs();
	}

}
