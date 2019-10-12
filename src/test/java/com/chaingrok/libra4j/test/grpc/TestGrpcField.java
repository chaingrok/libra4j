package com.chaingrok.libra4j.test.grpc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.libra.grpc.types.LedgerInfoOuterClass.LedgerInfo;

import com.chaingrok.lib.ChaingrokError;
import com.chaingrok.lib.ChaingrokException;
import com.chaingrok.lib.ChaingrokLog;
import com.chaingrok.lib.test.TestClass;
import com.chaingrok.libra4j.grpc.GrpcField;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestGrpcField extends TestClass {
	
	public static final int GRPC_FIELD_COUNT = 64;
	
	@Test
	public void test001FieldSize() {
		assertEquals(GRPC_FIELD_COUNT,GrpcField.values().length);
	}
	
	@Test
	public void test001GetFieldOk() {
		assertEquals(GrpcField.LEDGER_INFO,GrpcField.get(GrpcField.LEDGER_INFO.getFullName()));
		assertEquals(GrpcField.SIGNATURES,GrpcField.get(GrpcField.SIGNATURES.getFullName()));
	}
	
	@Test
	public void test002FieldKo() {
		assertFalse(ChaingrokLog.hasLogs());
		assertNull(GrpcField.get("foo"));
		assertTrue(ChaingrokLog.hasLogs());
		ArrayList<ChaingrokLog> logs = ChaingrokLog.getLogs();
		assertEquals(1,logs.size());
		ChaingrokLog log = logs.get(0);
		assertTrue(log instanceof ChaingrokError);
		ChaingrokLog.purgeLogs();
	}
	
	@Test
	public void test003FullDuplicates() {
		boolean result = true;
		HashMap<String,GrpcField> map = new HashMap<String,GrpcField>();
		for(GrpcField grpcField : GrpcField.values()) {
			String fullName = grpcField.getFullName();
			GrpcField value = map.get(fullName);
			if (value != null) {
				result = false;
			} else {
				map.put(fullName,grpcField);
			}
		}
		assertTrue(result);
	}
	
	@Test
	public void test004SuffixDuplicates() {
		int result = 0;
		HashMap<String,GrpcField> map = new HashMap<String,GrpcField>();
		for(GrpcField grpcField : GrpcField.values()) {
			String fullName = grpcField.getFullName();
			String[] chunks = fullName.split("\\.");
			if (chunks.length < 2) {
				throw new ChaingrokException("at least 2 chunks expected: " + fullName);
			}
			String suffix = chunks[chunks.length-1];
			GrpcField value = map.get(suffix);
			if (value != null) {
				++result;
				System.out.println("suffix duplicate: " + suffix + " -> " + fullName + " <> " + value.getFullName());
			} else {
				map.put(suffix,grpcField);
			}
		}
		Set<String> types = map.keySet();
		String[] reversedTypes = new String[types.size()];
		int count = 0;
		for (String type : types) {
			reversedTypes[count++] = new StringBuilder(type).reverse().toString();
		}
		Arrays.sort(reversedTypes);
		for (String type : reversedTypes) {
			System.out.println("type: " + new StringBuilder(type).reverse().toString());
		}
		assertEquals(9,result);
	}
	
	@Test
	public void test005GetParameters() {
		assertEquals(LedgerInfo.class,GrpcField.LEDGER_INFO_VERSION.getParentFieldClass());
		assertEquals(Long.class,GrpcField.LEDGER_INFO_VERSION.getFieldClass());
	}
	
	
	@Test
	public void test006CheckFieldHierarchy() {
		String string = GrpcField.checkFieldsHierarchy();
		System.out.println(string);
		//assertEquals("",string);
	}
	
	@Test
	public void test007AnalyzeFieldTree() {
		String string = GrpcField.grpcFieldFlatTreeToString(GrpcField.getGrpcFieldFlatTree());
		System.out.println(string);
		assertTrue(string.contains("ACCOUNT_STATE_PROOF: <LEDGER_INFO_TO_TRANSACTION_INFO_PROOF,TRANSACTION_INFO,TRANSACTION_INFO_TO_ACCOUNT_PROOF,>"));
		assertTrue(string.contains("EVENT_ACCESS_PATH: <ACCESS_PATH_ADDRESS,ACCESS_PATH_PATH,>"));
		assertEquals(GrpcField.values().length,string.split("\n").length);
		//
		string = GrpcField.grpcFieldsTreeToString();
		System.out.println(string);
		assertTrue(string.contains(GrpcField.SPARSE_MERKLE_PROOF_NON_DEFAULT_SIBLINGS.toString()));
		assertTrue(string.contains(GrpcField.GAS_USED.toString()));
		assertTrue(string.contains(GrpcField.PROOF_NON_DEFAULT_SIBLINGS.toString()));
		//assertEquals(GrpcField.values().length,string.split("\n").length);
	}
}
