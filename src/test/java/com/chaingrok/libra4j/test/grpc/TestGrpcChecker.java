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

import com.chaingrok.lib.ChaingrokError;
import com.chaingrok.lib.ChaingrokLog;
import com.chaingrok.lib.Utils;
import com.chaingrok.lib.ChaingrokLog.Type;
import com.chaingrok.lib.test.TestClass;
import com.chaingrok.libra4j.grpc.GrpcChecker;
import com.chaingrok.libra4j.grpc.GrpcField;
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

import org.libra.grpc.types.AccountStateBlobOuterClass.AccountStateBlob;
import org.libra.grpc.types.GetWithProof.ResponseItem;
import org.libra.grpc.types.GetWithProof.UpdateToLatestLedgerResponse;
import org.libra.grpc.types.LedgerInfoOuterClass.LedgerInfo;
import org.libra.grpc.types.LedgerInfoOuterClass.LedgerInfoWithSignatures;
import org.libra.grpc.types.LedgerInfoOuterClass.ValidatorSignature;
import org.libra.grpc.types.TransactionOuterClass.TransactionListWithProof;
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
		assertFalse(ChaingrokLog.hasLogs());
		assertFalse(grpcChecker.isFieldSet(GrpcField.ACCOUNT_BLOB,null, null));
		assertFalse(ChaingrokLog.hasLogs());
		assertFalse(grpcChecker.isFieldSet(GrpcField.ACCOUNT_BLOB,new Object(), null));
		assertEquals(1,ChaingrokLog.getLogs().size());
		ChaingrokLog.purgeLogs();
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
		assertEquals(2,ChaingrokLog.getLogs().size());
		ChaingrokLog.purgeLogs();
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
		assertTrue(ChaingrokLog.hasLogs());
		assertEquals(1,ChaingrokLog.getLogs().size());
		ChaingrokLog.purgeLogs();
		//
		ArrayList<String> initializationErrors = new ArrayList<String>();
		initializationErrors.add("error");
		assertFalse(grpcChecker.checkFieldErrors(initializationErrors,null));
		assertTrue(ChaingrokLog.hasLogs());
		assertEquals(1,ChaingrokLog.getLogs().size());
		ChaingrokLog.purgeLogs();
	}
	
	@Test
	public void test006checkInt32FieldDescriptor() {
		GrpcChecker grpcChecker = new GrpcChecker();
		int value = 12345;
		//
		//with Int32ValueOrBuilder
		UInt32Value.Builder builder = UInt32Value.newBuilder()
									.setValue(value);
		assertEquals(1, builder.getAllFields().size());
		assertEquals(value,builder.getValue());
		assertFalse(grpcChecker.checkInt32FieldDescriptor(GrpcField.UPDATE_TO_LATEST_LEDGER_RESPONSE,(Object)builder));
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(ChaingrokLog.Type.INVALID_CLASS,ChaingrokLog.getLogs().get(0).getType());
		assertTrue(((String)(ChaingrokLog.getLogs().get(0).getObject())).contains(GrpcField.UPDATE_TO_LATEST_LEDGER_RESPONSE.getFullName()));
		ChaingrokLog.purgeLogs();
		//with Int32ValueOrBuilder
		assertTrue(UInt32ValueOrBuilder.class.isAssignableFrom(UInt32Value.class));
		UInt32Value uint32Value = builder.build();
		assertEquals(value,uint32Value.getValue());
		assertEquals(1, uint32Value.getAllFields().size());
		assertEquals(value,builder.getValue());
		assertEquals(value,uint32Value.getValue());
		assertFalse(grpcChecker.checkInt32FieldDescriptor(GrpcField.UPDATE_TO_LATEST_LEDGER_RESPONSE,(Object)uint32Value));
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(ChaingrokLog.Type.INVALID_CLASS,ChaingrokLog.getLogs().get(0).getType());
		assertTrue(((String)(ChaingrokLog.getLogs().get(0).getObject())).contains(GrpcField.UPDATE_TO_LATEST_LEDGER_RESPONSE.getFullName()));
		ChaingrokLog.purgeLogs();
	}
	
	@Test
	public void test007checkInt64FieldDescriptor() {
		GrpcChecker grpcChecker = new GrpcChecker();
		long value = 12345L;
		//with Int64ValueOrBuilder
		 UInt64Value.Builder builder = UInt64Value.newBuilder()
									.setValue(value);
		assertEquals(1, builder.getAllFields().size());
		assertEquals(value,builder.getValue());
		assertFalse(grpcChecker.checkInt64FieldDescriptor(GrpcField.UPDATE_TO_LATEST_LEDGER_RESPONSE,(Object)builder));
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(ChaingrokLog.Type.INVALID_CLASS,ChaingrokLog.getLogs().get(0).getType());
		ChaingrokLog.purgeLogs();
		//with Int64ValueOrBuilder
		assertTrue(UInt64ValueOrBuilder.class.isAssignableFrom(UInt64Value.class));
		UInt64Value uint64Value = builder.build();
		assertEquals(1, uint64Value.getAllFields().size());
		assertEquals(value,uint64Value.getValue());
		assertFalse(grpcChecker.checkInt64FieldDescriptor(GrpcField.UPDATE_TO_LATEST_LEDGER_RESPONSE,(Object)uint64Value));
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(ChaingrokLog.Type.INVALID_CLASS,ChaingrokLog.getLogs().get(0).getType());
		ChaingrokLog.purgeLogs();
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
									//TODO: restore to .addInfos(0,transactionInfo1)
				                    //TODO: restore to.addInfos(1,transactionInfo2)
									.build();
		assertEquals(1,transactionListWithProof.getAllFields().size());
		Object object = transactionListWithProof.getAllFields().values().toArray()[0];
		//ok case 1: empty list of any kind
		assertTrue(grpcChecker.checkRepeatedFieldDescriptor(null,new ArrayList<Object>()));
		//ok case 2: normal case
		assertTrue(grpcChecker.checkRepeatedFieldDescriptor(GrpcField.TRANSACTION_INFO,object));
		//ko case1
		assertFalse(grpcChecker.checkRepeatedFieldDescriptor(GrpcField.UPDATE_TO_LATEST_LEDGER_RESPONSE,object));
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(ChaingrokLog.Type.INVALID_CLASS,ChaingrokLog.getLogs().get(0).getType());
		assertTrue(((String)ChaingrokLog.getLogs().get(0).getObject()).contains("returned field class is invalid"));
		ChaingrokLog.purgeLogs();
		//ko case2
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(new Object());
		assertFalse(grpcChecker.checkRepeatedFieldDescriptor(GrpcField.UPDATE_TO_LATEST_LEDGER_RESPONSE,list));
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(ChaingrokLog.Type.INVALID_CLASS,ChaingrokLog.getLogs().get(0).getType());
		assertTrue(((String)ChaingrokLog.getLogs().get(0).getObject()).contains("listObject is not instanceOf MessageOrBuilder"));
		ChaingrokLog.purgeLogs();
		
		
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
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(ChaingrokLog.Type.INVALID_CLASS,ChaingrokLog.getLogs().get(0).getType());
		ChaingrokLog.purgeLogs();
	}
	
	@Test
	public void test010CheckFieldDescriptorKo() {
		GrpcChecker grpcChecker = new GrpcChecker();
		//test with null rpcField
		assertFalse(grpcChecker.checkFieldDescriptor((Class<?>)null,null,(Boolean)null,null));
		assertEquals(2,ChaingrokLog.getLogs().size());
		assertEquals(ChaingrokLog.Type.UNKNOWN_VALUE,ChaingrokLog.getLogs().get(0).getType());
		assertEquals(ChaingrokLog.Type.UNKNOWN_VALUE,ChaingrokLog.getLogs().get(1).getType());
		ChaingrokLog.purgeLogs();
		assertFalse(ChaingrokLog.hasLogs());
		//
		boolean isRepeated = false;
		String fieldFullName = GrpcField.ACCOUNT_BLOB.getFullName();
		//test with invalid grpcClass
		assertFalse(grpcChecker.checkFieldDescriptor(Long.class,fieldFullName,isRepeated,null));
		assertEquals(2,ChaingrokLog.getLogs().size());
		assertEquals(ChaingrokLog.Type.INVALID_CLASS,ChaingrokLog.getLogs().get(0).getType());
		assertTrue(((String)ChaingrokLog.getLogs().get(0).getObject()).contains("field descriptor class is invalid"));
		assertEquals(ChaingrokLog.Type.MISSING_DATA,ChaingrokLog.getLogs().get(1).getType());
		assertTrue(((String)ChaingrokLog.getLogs().get(1).getObject()).contains("field object is null"));
		ChaingrokLog.purgeLogs();
		//test with invalid fieldObject
		assertFalse(grpcChecker.checkFieldDescriptor(AccountStateBlob.class,fieldFullName,isRepeated,new Object()));
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(ChaingrokLog.Type.INVALID_CLASS,ChaingrokLog.getLogs().get(0).getType());
		assertTrue(((String)ChaingrokLog.getLogs().get(0).getObject()).contains("field type checking is not implemented"));
		ChaingrokLog.purgeLogs();
	}
	
	@Test
	public void test011CheckFieldDescriptorOk() {
		GrpcChecker grpcChecker = new GrpcChecker();
		//test with directlyassignable ok
		LedgerInfo ledgerInfo = LedgerInfo.newBuilder()
				.build();
		LedgerInfoWithSignatures ledgerInfoWithSignatures = LedgerInfoWithSignatures.newBuilder()
				.setLedgerInfo(ledgerInfo)
				.build();
		assertEquals(1,ledgerInfoWithSignatures.getAllFields().size());
		Map<FieldDescriptor, Object> fieldDescriptors = ledgerInfoWithSignatures.getAllFields();
		assertEquals(1,fieldDescriptors.size());
		FieldDescriptor fieldDescriptor = (FieldDescriptor)fieldDescriptors.keySet().toArray()[0];
		assertTrue(grpcChecker.checkFieldDescriptor(GrpcField.LEDGER_INFO.getParentFieldClass(),fieldDescriptor,fieldDescriptors));
		//test with MessageOrBuilder
		LedgerInfo ledgerInfo2 = LedgerInfo.newBuilder()
				.build();
		LedgerInfoWithSignatures ledgerInfoWithSignatures2 = LedgerInfoWithSignatures.newBuilder()
				.setLedgerInfo(ledgerInfo2)
				.build();
		assertEquals(1,ledgerInfoWithSignatures.getAllFields().size());
		Map<FieldDescriptor, Object> fieldDescriptors2 = ledgerInfoWithSignatures2.getAllFields();
		assertEquals(1,fieldDescriptors2.keySet().size());
		FieldDescriptor fieldDescriptor2 = (FieldDescriptor)fieldDescriptors2.keySet().toArray()[0];
		assertTrue(grpcChecker.checkFieldDescriptor(GrpcField.LEDGER_INFO.getParentFieldClass(),fieldDescriptor2,fieldDescriptors2));
		//test with repeated field
		TransactionInfo transactionInfo1 = TransactionInfo.newBuilder()
				.build();
		TransactionInfo transactionInfo2 = TransactionInfo.newBuilder()
				.build();
		assertTrue(transactionInfo2 instanceof MessageOrBuilder);
		TransactionListWithProof transactionListWithProof = TransactionListWithProof.newBuilder()
				                    //TODO: restore to .addInfos(0,transactionInfo1)
                                    //TODO: restore to.addInfos(1,transactionInfo2)
									.build();
		assertEquals(1,transactionListWithProof.getAllFields().size());
		Map<FieldDescriptor, Object> fieldDescriptors3 = transactionListWithProof.getAllFields();
		assertEquals(1,fieldDescriptors3.keySet().size());
		FieldDescriptor fieldDescriptor3 = (FieldDescriptor)fieldDescriptors3.keySet().toArray()[0];
		assertTrue(fieldDescriptor3.isRepeated());
		assertTrue(grpcChecker.checkFieldDescriptor(GrpcField.TRANSACTIONS_LIST_INFOS.getParentFieldClass(),fieldDescriptor3,fieldDescriptors3));
	}
	
	@Test 
	public void test012CheckExpectedFieldsKoExpectedCount() {
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
		Map<FieldDescriptor, Object> fields = ledgerInfo.getAllFields();
		assertEquals(expectedSize,fields.size());
		assertFalse(grpcChecker.checkExpectedFields(ledgerInfo,expectedSize-1));
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertTrue(((String)ChaingrokLog.getLogs().get(0).getObject()).contains("count different from expected"));
		ChaingrokLog.purgeLogs();
	}
	
	@Test
	public void test013CheckExpectedFieldsOkEdgeCases() {
		GrpcChecker grpcChecker = new GrpcChecker();
		assertFalse(grpcChecker.checkExpectedFields(null,0));
		assertEquals(1,ChaingrokLog.getLogs().size());
		ChaingrokLog.purgeLogs();
		//
		assertFalse(ChaingrokError.hasLogs());
		assertFalse(grpcChecker.checkExpectedFields(new Object(),0)); //non MessageOrBuilderObject
		assertEquals(1,ChaingrokLog.getLogs().size());
		ChaingrokLog.purgeLogs();
		//Case withh 0 fields
		LedgerInfo ledgerInfo = LedgerInfo.newBuilder()
				.build();
		assertEquals(0,ledgerInfo.getAllFields().size());
		assertTrue(grpcChecker.checkExpectedFields(ledgerInfo,0));
	}
	
	
	@Test
	public void test014CheckExpectedFieldsOkForMessageOrBuilder() {
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
	public void test015CheckExpectedFieldsOkForRepeatedField() {
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
									//TODO: restore to .addInfos(0,transactionInfo1)
				                    //TODO: restore to.addInfos(1,transactionInfo2)
									.build();
		assertTrue(transactionListWithProof instanceof MessageOrBuilder);
		assertEquals(listExpectedFields,transactionListWithProof.getAllFields().size());
		assertTrue(grpcChecker.checkExpectedFields(transactionListWithProof,listExpectedFields));
	}
	
	
	@Test
	public void test016CheckLedgerInfoEmpty() {
		GrpcChecker grpcChecker = new GrpcChecker();
		//
		LedgerInfo ledgerInfo = LedgerInfo.newBuilder()
									.build();
		assertFalse(grpcChecker.checkLedgerInfo(ledgerInfo));
		assertTrue(ChaingrokLog.hasLogs());
		assertEquals(5,ChaingrokLog.getLogs().size());
		ChaingrokLog.purgeLogs();
	}
	
	
	@Test
	public void test017CheckLedgerInfoOk() {
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
		assertFalse(ChaingrokLog.hasLogs());
	}
	
	@Test
	public void test018CheckValidatorSignatureKo() {
		GrpcChecker grpcChecker = new GrpcChecker();
		//
		ValidatorSignature validatorSignature = ValidatorSignature.newBuilder()
									.build();
		assertFalse(grpcChecker.checkValidatorSignature(validatorSignature));
		assertTrue(ChaingrokLog.hasLogs());
		//System.out.println("logs: " + Libra4jLog.dumpLogs());
		assertEquals(2,ChaingrokLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,ChaingrokLog.getLogs().get(0).getType());
		assertEquals(Type.MISSING_DATA,ChaingrokLog.getLogs().get(1).getType());
		ChaingrokLog.purgeLogs();
	}
	
	@Test
	public void test019CheckValidatorSignatureOk() {
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
