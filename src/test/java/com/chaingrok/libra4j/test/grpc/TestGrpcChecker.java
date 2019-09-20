package com.chaingrok.libra4j.test.grpc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.grpc.GrpcChecker;
import com.chaingrok.libra4j.grpc.GrpcField;
import com.chaingrok.libra4j.misc.Libra4jError;
import com.chaingrok.libra4j.misc.Libra4jLog;
import com.chaingrok.libra4j.misc.Libra4jLog.Type;
import com.chaingrok.libra4j.misc.Utils;
import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.AccountAddress;
import com.chaingrok.libra4j.types.Hash;
import com.chaingrok.libra4j.types.Signature;
import com.google.protobuf.ByteString;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.UInt32Value;
import com.google.protobuf.UInt32ValueOrBuilder;
import com.google.protobuf.UInt64Value;
import com.google.protobuf.UInt64ValueOrBuilder;
import com.google.protobuf.UnknownFieldSet;
import com.google.protobuf.UnknownFieldSet.Field;

import org.libra.grpc.types.GetWithProof.ResponseItem;
import org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerResponse;
import org.libra.grpc.types.LedgerInfoOuterClass.LedgerInfo;
import org.libra.grpc.types.LedgerInfoOuterClass.LedgerInfoWithSignatures;
import org.libra.grpc.types.LedgerInfoOuterClass.ValidatorSignature;
import org.libra.grpc.types.Transaction.TransactionListWithProof;
import org.libra.grpc.types.TransactionInfoOuterClass.TransactionInfo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestGrpcChecker extends TestClass {
	
	
	@Test
	public void test001DumpFields() {
		GrpcChecker grpcChecker = new GrpcChecker();
		//
		assertEquals("",grpcChecker.dumpFields(null,null));
		//
		LedgerInfo ledgerInfo = LedgerInfo.newBuilder()
									.build();
		assertTrue(grpcChecker.dumpFields(ledgerInfo, null).contains(ledgerInfo.getClass().getCanonicalName()));
		//
		long version = 123L;
		long timestampUsecs = 456789L;
		ledgerInfo = LedgerInfo.newBuilder()
									.setVersion(version)
									.setTimestampUsecs(timestampUsecs)
									.build();
		String string = grpcChecker.dumpFields(ledgerInfo, ledgerInfo.getAllFields());
		System.out.println((string));
		assertTrue(string.contains(ledgerInfo.getClass().getCanonicalName()));
		assertTrue(string.contains(GrpcField.LEDGER_INFO_VERSION.getFullName() + ""));
		assertTrue(string.contains(GrpcField.LEDGER_TIMESTAMP_USECS.getFullName() + ""));
	}
	
	@Test
	public void test002IsFieldSet() {
		GrpcChecker grpcChecker = new GrpcChecker();
		//
		assertFalse(grpcChecker.isFieldSet(null,null, null));
		assertFalse(Libra4jLog.hasLogs());
		assertFalse(grpcChecker.isFieldSet(GrpcField.ACCOUNT_BLOB,null, null));
		assertFalse(Libra4jLog.hasLogs());
		assertFalse(grpcChecker.isFieldSet(GrpcField.ACCOUNT_BLOB,new Object(), null));
		assertEquals(1,Libra4jLog.getLogs().size());
		Libra4jLog.purgeLogs();
		//
		LedgerInfo ledgerInfo = LedgerInfo.newBuilder()
				.build();
		assertEquals(0,ledgerInfo.getAllFields().size());
		assertFalse(grpcChecker.isFieldSet(GrpcField.LEDGER_INFO_VERSION,ledgerInfo,ledgerInfo.getAllFields()));
		long version = 123L;
		ledgerInfo = LedgerInfo.newBuilder()
				.setVersion(version)
				.build();
		assertTrue(grpcChecker.isFieldSet(GrpcField.LEDGER_INFO_VERSION,ledgerInfo,ledgerInfo.getAllFields()));
		assertFalse(grpcChecker.isFieldSet(GrpcField.LEDGER_TIMESTAMP_USECS,ledgerInfo,ledgerInfo.getAllFields()));
	}
	
	@Test
	public void test003CheckMandatoryFields() {
		GrpcChecker grpcChecker = new GrpcChecker();
		//
		assertTrue(grpcChecker.MANDATORY_OBJECT_FIELDS_MAP.size() == 1);
		assertNotNull(grpcChecker.MANDATORY_OBJECT_FIELDS_MAP.get(UpdateToLatestLedgerResponse.class));
		UpdateToLatestLedgerResponse updateToLatestLedgerResponse = UpdateToLatestLedgerResponse.newBuilder()
													.build();
		assertFalse(grpcChecker.checkMandatoryFields(updateToLatestLedgerResponse));
		//System.out.println("logs: " + Libra4jLog.dumpLogs());
		assertEquals(2,Libra4jLog.getLogs().size());
		Libra4jLog.purgeLogs();
		//
		ResponseItem responseItem = ResponseItem.newBuilder()
				.build();
		LedgerInfoWithSignatures ledgerInfoWithSignatures = LedgerInfoWithSignatures.newBuilder()
																.build();
		updateToLatestLedgerResponse = UpdateToLatestLedgerResponse.newBuilder()
											.addResponseItems(responseItem)
											.setLedgerInfoWithSigs(ledgerInfoWithSignatures)
											.build();
		assertTrue(grpcChecker.checkMandatoryFields(updateToLatestLedgerResponse));
	}
	
	
	@Test
	public void test004CheckFieldErrorsOk() {
		GrpcChecker grpcChecker = new GrpcChecker();
		//
		assertTrue(grpcChecker.checkFieldErrors(null,null));
		//
		UnknownFieldSet unknownFieldSet = UnknownFieldSet.newBuilder()
									.build();
		assertEquals(0,unknownFieldSet.asMap().size());
		assertTrue(grpcChecker.checkFieldErrors(null,unknownFieldSet));
		ArrayList<String> initializationErrors = new ArrayList<String>();
		assertTrue(grpcChecker.checkFieldErrors(initializationErrors,unknownFieldSet));
		assertTrue(grpcChecker.checkFieldErrors(initializationErrors,null));
	}
	
	@Test
	public void test005CheckFieldErrorsKo() {
		GrpcChecker grpcChecker = new GrpcChecker();
		//
		assertTrue(grpcChecker.checkFieldErrors(null,null));
		//
		Field field  = Field.newBuilder()
					.build();
		UnknownFieldSet unknownFieldSet = UnknownFieldSet.newBuilder()
									.addField(1,field)
									.build();
		assertEquals(1,unknownFieldSet.asMap().size());
		assertFalse(grpcChecker.checkFieldErrors(null,unknownFieldSet));
		assertTrue(Libra4jLog.hasLogs());
		assertEquals(1,Libra4jLog.getLogs().size());
		Libra4jLog.purgeLogs();
		//
		ArrayList<String> initializationErrors = new ArrayList<String>();
		initializationErrors.add("error");
		assertFalse(grpcChecker.checkFieldErrors(initializationErrors,null));
		assertTrue(Libra4jLog.hasLogs());
		assertEquals(1,Libra4jLog.getLogs().size());
		Libra4jLog.purgeLogs();
	}
	
	@Test
	public void test006checkInt32FieldDescriptor() {
		GrpcChecker grpcChecker = new GrpcChecker();
		int value = 12345;
		//
		//with Int32ValueOrBuilder
		com.google.protobuf.UInt32Value.Builder builder = UInt32Value.newBuilder()
									.setValue(value);
		assertEquals(1, builder.getAllFields().size());
		assertFalse(grpcChecker.checkInt32FieldDescriptor(GrpcField.UPDATE_TO_LATEST_LEDGER_RESPONSE,(Object)builder));
		assertEquals(1,Libra4jLog.getLogs().size());
		assertEquals(Libra4jLog.Type.INVALID_CLASS,Libra4jLog.getLogs().get(0).getType());
		Libra4jLog.purgeLogs();
		//with Int32ValueOrBuilder
		assertTrue(UInt32ValueOrBuilder.class.isAssignableFrom(UInt32Value.class));
		UInt32Value uint32Value = builder.build();
		assertEquals(1, uint32Value.getAllFields().size());
		assertEquals(value,uint32Value.getValue());
		assertFalse(grpcChecker.checkInt32FieldDescriptor(GrpcField.UPDATE_TO_LATEST_LEDGER_RESPONSE,(Object)uint32Value));
		assertEquals(1,Libra4jLog.getLogs().size());
		assertEquals(Libra4jLog.Type.INVALID_CLASS,Libra4jLog.getLogs().get(0).getType());
		Libra4jLog.purgeLogs();
	}
	
	@Test
	public void test007checkInt64FieldDescriptor() {
		GrpcChecker grpcChecker = new GrpcChecker();
		long value = 12345L;
		//
		//with Int64ValueOrBuilder
		 com.google.protobuf.UInt64Value.Builder builder = UInt64Value.newBuilder()
									.setValue(value);
		assertEquals(1, builder.getAllFields().size());
		assertFalse(grpcChecker.checkInt64FieldDescriptor(GrpcField.UPDATE_TO_LATEST_LEDGER_RESPONSE,(Object)builder));
		assertEquals(1,Libra4jLog.getLogs().size());
		assertEquals(Libra4jLog.Type.INVALID_CLASS,Libra4jLog.getLogs().get(0).getType());
		Libra4jLog.purgeLogs();
		//with Int32ValueOrBuilder
		assertTrue(UInt64ValueOrBuilder.class.isAssignableFrom(UInt64Value.class));
		UInt64Value uint64Value = builder.build();
		assertEquals(1, uint64Value.getAllFields().size());
		assertEquals(value,uint64Value.getValue());
		assertFalse(grpcChecker.checkInt64FieldDescriptor(GrpcField.UPDATE_TO_LATEST_LEDGER_RESPONSE,(Object)uint64Value));
		assertEquals(1,Libra4jLog.getLogs().size());
		assertEquals(Libra4jLog.Type.INVALID_CLASS,Libra4jLog.getLogs().get(0).getType());
		Libra4jLog.purgeLogs();
	}
	
	@Test
	public void test008checkRepeatedFieldDescriptor() {
		GrpcChecker grpcChecker = new GrpcChecker();
		//
		TransactionInfo transactionInfo1 = TransactionInfo.newBuilder()
				.build();
		TransactionInfo transactionInfo2 = TransactionInfo.newBuilder()
				.build();
		assertTrue(transactionInfo2 instanceof MessageOrBuilder);
		TransactionListWithProof transactionListWithProof = TransactionListWithProof.newBuilder()
									.addInfos(0,transactionInfo1)
									.addInfos(0,transactionInfo2)
									.build();
		assertEquals(1,transactionListWithProof.getAllFields().size());
		Object object = transactionListWithProof.getAllFields().values().toArray()[0];
		//ok case 1: empty list of any kind
		assertTrue(grpcChecker.checkRepeatedFieldDescriptor(null,new ArrayList<Object>()));
		//ok case 2: normal case
		assertTrue(grpcChecker.checkRepeatedFieldDescriptor(GrpcField.TRANSACTION_INFO,object));
		//ko case1
		assertFalse(grpcChecker.checkRepeatedFieldDescriptor(GrpcField.UPDATE_TO_LATEST_LEDGER_RESPONSE,object));
		assertEquals(1,Libra4jLog.getLogs().size());
		assertEquals(Libra4jLog.Type.INVALID_CLASS,Libra4jLog.getLogs().get(0).getType());
		assertTrue(((String)Libra4jLog.getLogs().get(0).getObject()).contains("returned field class is invalid"));
		Libra4jLog.purgeLogs();
		//ko case2
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(new Object());
		assertFalse(grpcChecker.checkRepeatedFieldDescriptor(GrpcField.UPDATE_TO_LATEST_LEDGER_RESPONSE,list));
		assertEquals(1,Libra4jLog.getLogs().size());
		assertEquals(Libra4jLog.Type.INVALID_CLASS,Libra4jLog.getLogs().get(0).getType());
		assertTrue(((String)Libra4jLog.getLogs().get(0).getObject()).contains("listObject is not instanceOf MessageOrBuilder"));
		Libra4jLog.purgeLogs();
		
		
	}
	
	@Test
	public void test009MessageOrBuilderFieldDescriptor() {
		GrpcChecker grpcChecker = new GrpcChecker();
		//
		LedgerInfo ledgerInfo = LedgerInfo.newBuilder()
				.build();
		LedgerInfoWithSignatures ledgerInfoWithSignatures = LedgerInfoWithSignatures.newBuilder()
				.setLedgerInfo(ledgerInfo)
				.build();
		assertEquals(1,ledgerInfoWithSignatures.getAllFields().size());
		//test ok
		assertTrue(grpcChecker.checkMessageOrBuilderFieldDescriptor(GrpcField.LEDGER_INFO_WITH_SIGS,ledgerInfoWithSignatures));
		//test ko
		assertFalse(grpcChecker.checkMessageOrBuilderFieldDescriptor(GrpcField.RESPONSE_ITEMS,ledgerInfoWithSignatures));
		assertEquals(1,Libra4jLog.getLogs().size());
		assertEquals(Libra4jLog.Type.INVALID_CLASS,Libra4jLog.getLogs().get(0).getType());
		Libra4jLog.purgeLogs();
	}
	
	@Test
	public void test010CheckExpectedFieldsOkEdgeCases() {
		GrpcChecker grpcChecker = new GrpcChecker();
		assertFalse(grpcChecker.checkExpectedFields(null,0));
		assertEquals(1,Libra4jLog.getLogs().size());
		Libra4jLog.purgeLogs();
		//
		assertFalse(Libra4jError.hasLogs());
		assertFalse(grpcChecker.checkExpectedFields(new Object(),0)); //non MessageOrBuilderObject
		assertEquals(1,Libra4jLog.getLogs().size());
		Libra4jLog.purgeLogs();
		
	}
	
	@Test
	public void test011CheckFieldDescrpitorOkForUIntValues() {
		//GrpcChecker grpcChecker = new GrpcChecker();
		long value = 12345L;
		//
		assertTrue(MessageOrBuilder.class.isAssignableFrom(UInt32Value.class));
		UInt32Value uint32Value = UInt32Value.newBuilder()
									.setValue((int)value)
									.build();
		assertEquals(1, uint32Value.getAllFields().size());
		assertEquals(value,uint32Value.getValue());
		//assertTrue(grpcChecker.checkExpectedFields(uint64Value,1));
		//
		assertTrue(MessageOrBuilder.class.isAssignableFrom(UInt32ValueOrBuilder.class));
		UInt32ValueOrBuilder uint32ValueOrBuilder = UInt32Value.newBuilder()
				.setValue((int)value)
				.build();
		assertEquals(1, uint32ValueOrBuilder.getAllFields().size());
		assertEquals(value,uint32ValueOrBuilder.getValue());
		//assertTrue(grpcChecker.checkExpectedFields(uint64Value,1));
		//
		//
		assertTrue(MessageOrBuilder.class.isAssignableFrom(UInt64Value.class));
		UInt64Value uint64Value = UInt64Value.newBuilder()
									.setValue(value)
									.build();
		assertEquals(1, uint64Value.getAllFields().size());
		assertEquals(value,uint64Value.getValue());
		//assertTrue(grpcChecker.checkExpectedFields(uint64Value,1));
		//
		assertTrue(MessageOrBuilder.class.isAssignableFrom(UInt64ValueOrBuilder.class));
		UInt64ValueOrBuilder uint64ValueOrBuilder = UInt64Value.newBuilder()
				.setValue(value)
				.build();
		assertEquals(1, uint64ValueOrBuilder.getAllFields().size());
		assertEquals(value,uint64ValueOrBuilder.getValue());
		//assertTrue(grpcChecker.checkExpectedFields(uint64Value,1));
	}
	
	@Test
	public void test012CheckExpectedFieldsOkForMessageOrBuilder() {
		GrpcChecker grpcChecker = new GrpcChecker();
		//
		int expectedSize = 5;
		long version = 123L;
		long timestampUsecs = 456789L;
		ByteString consensusBlockId = ByteString.copyFrom(Utils.getByteArray(Hash.BYTE_LENGTH,0x11));
		ByteString consensusDataHash = ByteString.copyFrom(Utils.getByteArray(Hash.BYTE_LENGTH,0x22));
		ByteString transactionAccumulatorHash = ByteString.copyFrom(Utils.getByteArray(Hash.BYTE_LENGTH,0x33));
		LedgerInfo ledgerInfo = LedgerInfo.newBuilder()
									.setVersion(version)
									.setTimestampUsecs(timestampUsecs)
									.setConsensusBlockId(consensusBlockId)
									.setConsensusDataHash(consensusDataHash)
									.setTransactionAccumulatorHash(transactionAccumulatorHash)
									.build();
		assertTrue(ledgerInfo instanceof MessageOrBuilder);
		Map<FieldDescriptor, Object> fields = ledgerInfo.getAllFields();
		assertEquals(expectedSize,fields.size());
		assertTrue(grpcChecker.checkExpectedFields(ledgerInfo,expectedSize));
	}
	
	@Test
	public void test013CheckExpectedFieldsOkForRepeatedField() {
		GrpcChecker grpcChecker = new GrpcChecker();
		//
		int transactionExpectedFields = 1;
		int listExpectedFields = 2;
		long gasUsed = 123;
		TransactionInfo transactionInfo1 = TransactionInfo.newBuilder()
											.setGasUsed(gasUsed)
											.build();
		assertTrue(transactionInfo1 instanceof MessageOrBuilder);
		assertTrue(grpcChecker.checkExpectedFields(transactionInfo1,transactionExpectedFields));
		TransactionInfo transactionInfo2 = TransactionInfo.newBuilder()
				.setGasUsed(gasUsed)
				.build();
		assertTrue(transactionInfo2 instanceof MessageOrBuilder);
		assertTrue(grpcChecker.checkExpectedFields(transactionInfo2,transactionExpectedFields));
		UInt64Value version = UInt64Value.newBuilder()
				.setValue(12345L)
				.build();
		assertEquals(1,version.getAllFields().size());
		TransactionListWithProof transactionListWithProof = TransactionListWithProof.newBuilder()
									.setFirstTransactionVersion(version)
									.addInfos(0,transactionInfo1)
									.addInfos(0,transactionInfo2)
									.build();
		assertTrue(transactionListWithProof instanceof MessageOrBuilder);
		assertEquals(listExpectedFields,transactionListWithProof.getAllFields().size());
		//assertTrue(grpcChecker.checkExpectedFields(transactionListWithProof,listExpectedFields));
	}
	
	@Test
	public void test0014CheckInt64FieldDescriptor() {
		assertTrue(MessageOrBuilder.class.isAssignableFrom(UInt64Value.class));
		assertTrue(MessageOrBuilder.class.isAssignableFrom(UInt32Value.class));
		assertTrue(MessageOrBuilder.class.isAssignableFrom(UInt64ValueOrBuilder.class));
		assertTrue(MessageOrBuilder.class.isAssignableFrom(UInt32ValueOrBuilder.class));
		//
		assertFalse(MessageOrBuilder.class.isAssignableFrom(ByteString.class));
				
		
		
	}
	
	@Test
	public void test004CheckLedgerInfoEmpty() {
		GrpcChecker grpcChecker = new GrpcChecker();
		//
		LedgerInfo ledgerInfo = LedgerInfo.newBuilder()
									.build();
		assertFalse(grpcChecker.checkLedgerInfo(ledgerInfo));
		assertTrue(Libra4jLog.hasLogs());
		assertEquals(5,Libra4jLog.getLogs().size());
		Libra4jLog.purgeLogs();
	}
	
	
	@Test
	public void test005CheckLedgerInfoOk() {
		GrpcChecker grpcChecker = new GrpcChecker();
		//
		long version = 123L;
		long timestampUsecs = 456789L;
		ByteString consensusBlockId = ByteString.copyFrom(Utils.getByteArray(Hash.BYTE_LENGTH,0x11));
		ByteString consensusDataHash = ByteString.copyFrom(Utils.getByteArray(Hash.BYTE_LENGTH,0x22));
		ByteString transactionAccumulatorHash = ByteString.copyFrom(Utils.getByteArray(Hash.BYTE_LENGTH,0x33));
		LedgerInfo ledgerInfo = LedgerInfo.newBuilder()
									.setVersion(version)
									.setTimestampUsecs(timestampUsecs)
									.setConsensusBlockId(consensusBlockId)
									.setConsensusDataHash(consensusDataHash)
									.setTransactionAccumulatorHash(transactionAccumulatorHash)
									.build();
		assertTrue(grpcChecker.checkLedgerInfo(ledgerInfo));
		assertFalse(Libra4jLog.hasLogs());
	}
	
	@Test
	public void test006CheckValidatorSignatureKo() {
		GrpcChecker grpcChecker = new GrpcChecker();
		//
		ValidatorSignature validatorSignature = ValidatorSignature.newBuilder()
									.build();
		assertFalse(grpcChecker.checkValidatorSignature(validatorSignature));
		assertTrue(Libra4jLog.hasLogs());
		//System.out.println("logs: " + Libra4jLog.dumpLogs());
		assertEquals(2,Libra4jLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,Libra4jLog.getLogs().get(0).getType());
		assertEquals(Type.MISSING_DATA,Libra4jLog.getLogs().get(1).getType());
		Libra4jLog.purgeLogs();
	}
	
	@Test
	public void test007CheckValidatorSignatureOk() {
		GrpcChecker grpcChecker = new GrpcChecker();
		//
		ByteString accountAddress =ByteString.copyFrom(Utils.getByteArray(AccountAddress.BYTE_LENGTH,0x33));
		ByteString signature = ByteString.copyFrom(Utils.getByteArray(Signature.BYTE_LENGTH,0x33));
		ValidatorSignature validatorSignature = ValidatorSignature.newBuilder()
									.setValidatorId(accountAddress)
									.setSignature(signature)
									.build();
		assertTrue(grpcChecker.checkValidatorSignature(validatorSignature));
	}
}
