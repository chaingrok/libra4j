package com.chaingrok.libra4j.test.grpc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.grpc.GrpcField;
import com.chaingrok.libra4j.misc.Libra4jException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestGrpcField {
	
	@Test
	public void test000_FieldSize() {
		assertEquals(60,GrpcField.values().length);
	}
	
	@Test
	public void test001_FieldOk() {
		assertEquals(60,GrpcField.values().length);
		assertEquals(GrpcField.LEDGER_INFO,GrpcField.get(GrpcField.LEDGER_INFO.getFullName()));
		assertEquals(GrpcField.SIGNATURES,GrpcField.get(GrpcField.SIGNATURES.getFullName()));
	}
	
	@Test
	public void test002_FieldOk() {
		try {
			GrpcField.get("foo");
			fail("should throw exception with unknown fullName");
		} catch (Libra4jException e) {
			assertEquals("unknown " + GrpcField.class.getCanonicalName() + ":foo",e.getMessage());
		}
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
		assertEquals(8,result);
	}
}
