package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.misc.LCSProcessor;
import com.chaingrok.libra4j.misc.Libra4jLog;
import com.chaingrok.libra4j.misc.Utils;
import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.AccessPath;
import com.chaingrok.libra4j.types.AccountAddress;
import com.chaingrok.libra4j.types.Argument;
import com.chaingrok.libra4j.types.Code;
import com.chaingrok.libra4j.types.Event;
import com.chaingrok.libra4j.types.EventData;
import com.chaingrok.libra4j.types.Events;
import com.chaingrok.libra4j.types.Hash;
import com.chaingrok.libra4j.types.Module;
import com.chaingrok.libra4j.types.Modules;
import com.chaingrok.libra4j.types.Path;
import com.chaingrok.libra4j.types.Program;
import com.chaingrok.libra4j.types.Signature;
import com.chaingrok.libra4j.types.Transaction;
import com.chaingrok.libra4j.types.UInt64;
import com.chaingrok.libra4j.types.WriteOp;
import com.chaingrok.libra4j.types.WriteSet;
import com.chaingrok.libra4j.types.WriteSetTuple;
import com.chaingrok.libra4j.types.Argument.Type;
import com.chaingrok.libra4j.types.Arguments;
import com.google.protobuf.ByteString;

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
		UInt64 sequenceNumber = new UInt64(0L);
		transaction.setSequenceNumber(sequenceNumber);
		assertSame(sequenceNumber,transaction.getSequenceNumber());
		//
		assertNull(transaction.getSenderPublicKey()); //TODO
		//
		assertNull(transaction.getMaxGasAmount());
		UInt64 maxGasAmount = new UInt64(0L);
		transaction.setMaxGasAmount(maxGasAmount);
		assertSame(maxGasAmount,transaction.getMaxGasAmount());
		//
		assertNull(transaction.getExpirationTime());
		UInt64 expirationTime = new UInt64(0L);
		transaction.setExpirationTime(expirationTime);
		assertSame(expirationTime,transaction.getExpirationTime());
		//
		assertNull(transaction.getGasUnitPrice());
		UInt64 gasUnitPrice = new UInt64(0L);
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
		Events eventsList = new Events();
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
		argument.setBytes(argBytes);
		Arguments arguments = new Arguments();
		arguments.add(argument);
		program.setArguments(arguments);
		byte[] code = {0x00,0x01,0x0a,0x0b};
		Module module = new Module(code);
		Modules modules = new Modules();
		modules.add(module);
		program.setModules(modules);
		byte[] signatureBytes = Utils.getByteArray(Signature.BYTE_LENGTH,0x55);
		Signature signature = new Signature(signatureBytes);
		transaction.setSignature(signature);
		byte[] accountAddressBytes = Utils.getByteArray(AccountAddress.BYTE_LENGTH,0x66);
		AccountAddress accountAddress = new AccountAddress(accountAddressBytes);
		transaction.setSenderAccountAddress(accountAddress);
		//
		Event event = new Event();
		long seqNum = 12345678999L;
		Long sequenceNumber = new Long(seqNum);
		event.setSequenceNumber(sequenceNumber);
		byte[] eventAccountAddressBytes = Utils.getByteArray(AccountAddress.BYTE_LENGTH,0x39);
		AccountAddress eventAccountAddress = new AccountAddress(eventAccountAddressBytes);
		event.setAddress(eventAccountAddress);
		byte[] eventDataBytes = Utils.getByteArray(20,0xab);
		ByteString byteString  = ByteString.copyFrom(eventDataBytes);
		EventData eventData = new EventData(byteString);
		event.setData(eventData);
		String testPath = "/test_path";
		AccessPath accessPath = AccessPath.create((byte)0x00,new AccountAddress(AccountAddress.ADDRESS_ZERO),new Path(testPath));
		event.setAccessPath(accessPath);
		Events events = new Events();
		events.add(event);
		//
		String string = transaction.toString();
		assertTrue(string.contains(version + ""));
		assertTrue(string.contains(Transaction.Type.MINT + ""));
		assertTrue(string.contains(argValue));
		assertTrue(string.contains(Utils.byteArrayToHexString(argBytes)));
		assertTrue(string.contains(Utils.byteArrayToHexString(signatureBytes)));
		assertTrue(string.contains(Utils.byteArrayToHexString(accountAddressBytes)));
	}
	
	@Test
	public void test004TransactionLCSEncodingDecoding() throws IOException { //as per https://github.com/libra/libra/tree/master/common/canonical_serialization
		String testVectorHex = "20000000C3398A599A6F3B9F30B635AF29F2BA046D3A752C26E9D0647B9647D1F4C04AD42000000000000000010000000200000020000000"
									+ "A71D76FAA2D2D5C3224EC3D41DEB293973564A791E55C6782BA76C2BF0495F9A2100000001217DA6C6B3E19F1825CFB2676DAECCE3BF3DE03CF2664"
									+ "7C78DF00B371B25CC970000000020000000C4C63F80C74B11263E421EBF8486A4E398D0DBC09FA7D4F62CCDB309F3AEA81F0900000001217DA6C6B3E"
									+ "19F180100000004000000CAFED00D00000000000000000000000000000000FFFFFFFFFFFFFFFF";
		byte[] testVectorBytes = Utils.hexStringToByteArray(testVectorHex);
		LCSProcessor decoder = LCSProcessor.buildDecoder(testVectorBytes);
		Transaction transaction = decoder.decodeTransaction();
		assertNotNull(transaction);
		assertEquals(new AccountAddress("c3398a599a6f3b9f30b635af29f2ba046d3a752c26e9d0647b9647d1f4c04ad4"),transaction.getSenderAccountAddress());
		assertEquals(new UInt64(32L),transaction.getSequenceNumber());
		//WriteSet
		WriteSet writeSet = transaction.getWriteSet();
		assertNotNull(writeSet);
		assertEquals(2,writeSet.size());
		//WriteOp 1
		WriteSetTuple writeSetTuple = writeSet.get(0);
		assertNotNull(writeSetTuple);
		WriteOp writeOp = writeSetTuple.getY();
		assertEquals(WriteOp.Type.DELETE,writeOp.getOpType());
		assertNull(writeOp.getBytes());
		AccessPath accessPath = writeSetTuple.getX();
		assertNotNull(accessPath);
		assertEquals(new AccountAddress("a71d76faa2d2d5c3224ec3d41deb293973564a791e55c6782ba76c2bf0495f9a"),accessPath.getAccountAddress());
		assertEquals(new Path(Utils.hexStringToByteArray("01217da6c6b3e19f1825cfb2676daecce3bf3de03cf26647c78df00b371b25cc97")),accessPath.getPath());
		//WriteOp 2
		writeSetTuple = writeSet.get(1);
		assertNotNull(writeSetTuple);
		writeOp = writeSetTuple.getY();
		assertEquals(WriteOp.Type.WRITE,writeOp.getOpType());
		assertArrayEquals(Utils.hexStringToByteArray("cafed00d"),writeOp.getBytes());
		accessPath = writeSetTuple.getX();
		assertNotNull(accessPath);
		assertEquals(new AccountAddress("c4c63f80c74b11263e421ebf8486a4e398d0dbc09fa7d4f62ccdb309f3aea81f"),accessPath.getAccountAddress());
		assertEquals(new Path(Utils.hexStringToByteArray("01217da6c6b3e19f18")),accessPath.getPath());
		//
		assertEquals(new UInt64(0L),transaction.getMaxGasAmount());
		assertEquals(new UInt64(0L),transaction.getGasUnitPrice());
		assertEquals(new UInt64(UInt64.MAX_VALUE),transaction.getExpirationTime());
		assertEquals(0,(int)decoder.getUndecodedDataSize());
	}

}
