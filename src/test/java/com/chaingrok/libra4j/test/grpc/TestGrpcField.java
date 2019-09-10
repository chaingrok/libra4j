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

import com.chaingrok.libra4j.grpc.GrpcField;
import com.chaingrok.libra4j.misc.Libra4jError;
import com.chaingrok.libra4j.misc.Libra4jException;
import com.chaingrok.libra4j.misc.Libra4jLog;
import com.chaingrok.libra4j.misc.Libra4jWarning;
import com.chaingrok.libra4j.test.TestClass;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestGrpcField extends TestClass {
	
	@Test
	public void test000_FieldSize() {
		assertEquals(68,GrpcField.values().length);
	}
	
	@Test
	public void test001_getFieldOk() {
		assertEquals(GrpcField.LEDGER_INFO,GrpcField.get(GrpcField.LEDGER_INFO.getFullName()));
		assertEquals(GrpcField.SIGNATURES,GrpcField.get(GrpcField.SIGNATURES.getFullName()));
	}
	
	@Test
	public void test002_FieldKo() {
		assertFalse(Libra4jLog.hasLogs());
		assertNull(GrpcField.get("foo"));
		assertTrue(Libra4jLog.hasLogs());
		ArrayList<Libra4jLog> logs = Libra4jLog.getLogs();
		assertEquals(1,logs.size());
		Libra4jLog log = logs.get(0);
		assertTrue(log instanceof Libra4jError);
		Libra4jLog.purgeLogs();
	}
	
	@Test
	public void test003_testFullDuplicates() {
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
	public void test003_testSuffixDuplicates() {
		int result = 0;
		HashMap<String,GrpcField> map = new HashMap<String,GrpcField>();
		for(GrpcField grpcField : GrpcField.values()) {
			String fullName = grpcField.getFullName();
			String[] chunks = fullName.split("\\.");
			if (chunks.length < 2) {
				throw new Libra4jException("at least 2 chunks expected: " + fullName);
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
		assertEquals(10,result);
	}
	
	@Test
	public void test004_getOwningClass() {
		assertEquals(LedgerInfo.class,GrpcField.LEDGER_INFO_VERSION.getOwningClass());
		assertEquals(LedgerInfo.class,GrpcField.LEDGER_INFO_VERSION.getFieldClass());
	}
}
