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
import com.chaingrok.libra4j.types.AccountAddress;
import com.chaingrok.libra4j.types.Argument;
import com.chaingrok.libra4j.types.Code;
import com.chaingrok.libra4j.types.Event;
import com.chaingrok.libra4j.types.Hash;
import com.chaingrok.libra4j.types.Module;
import com.chaingrok.libra4j.types.Program;
import com.chaingrok.libra4j.types.Signature;
import com.chaingrok.libra4j.types.Transaction;
import com.chaingrok.libra4j.types.Argument.Type;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestTransaction extends TestClass {
	
	@Test
	public void test001NewInstance() {
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
		//
		assertNull(transaction.getSignature());
		byte[] signatureBytes = Utils.getByteArray(Signature.BYTE_LENGTH,0x55);
		Signature signature = new Signature(signatureBytes);
		transaction.setSignature(signature);
		assertSame(signature,transaction.getSignature());
		//
		assertNull(transaction.getSenderAccountAddress());
		byte[] accountAddressBytes = Utils.getByteArray(AccountAddress.BYTE_LENGTH,0x66);
		AccountAddress accountAddress = new AccountAddress(accountAddressBytes);
		transaction.setSenderAccountAddress(accountAddress);
		assertSame(accountAddress,transaction.getSenderAccountAddress());
	}
	
	@Test
	public void test002CheckType() {
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
	
	@Test
	public void test003ToString() {
		Transaction transaction = new Transaction();
		//
		Long version = new Long(12345678L);
		transaction.setVersion(version);
		//
		Program program = new Program();
		program.setCode(Code.MINT);
		transaction.setProgram(program);
		Argument argument = new Argument();
		argument.setType(Type.STRING);
		String argValue = "foo_bar";
		byte[] argBytes = argValue.getBytes();
		argument.setData(argBytes);
		ArrayList<Argument> arguments = new ArrayList<Argument>();
		arguments.add(argument);
		program.setArguments(arguments);
		byte[] code = {0x00,0x01,0x0a,0x0b};
		Module module = new Module(code);
		ArrayList<Module> modules = new ArrayList<Module>();
		modules.add(module);
		program.setModules(modules);
		byte[] signatureBytes = Utils.getByteArray(Signature.BYTE_LENGTH,0x55);
		Signature signature = new Signature(signatureBytes);
		transaction.setSignature(signature);
		byte[] accountAddressBytes = Utils.getByteArray(AccountAddress.BYTE_LENGTH,0x66);
		AccountAddress accountAddress = new AccountAddress(accountAddressBytes);
		transaction.setSenderAccountAddress(accountAddress);
		//
		String string = transaction.toString();
		assertTrue(string.contains(version + ""));
		assertTrue(string.contains(Transaction.Type.MINT + ""));
		assertTrue(string.contains(argValue));
		assertTrue(string.contains(Utils.byteArrayToHexString(argBytes)));
		assertTrue(string.contains(Utils.byteArrayToHexString(signatureBytes)));
		assertTrue(string.contains(Utils.byteArrayToHexString(accountAddressBytes)));
	}

}
