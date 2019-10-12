package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.lib.ChaingrokError;
import com.chaingrok.lib.ChaingrokLog;
import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.AccountAddress;
import com.google.protobuf.ByteString;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAccountAddress extends TestClass {
	
	
	@Test
	public void test001TestLength() {
		assertFalse(ChaingrokLog.hasLogs());
		new AccountAddress((byte[])null);
		assertTrue(ChaingrokLog.hasLogs());
		ArrayList<ChaingrokLog> logs = ChaingrokLog.getLogs();
		assertEquals(1,logs.size());
		ChaingrokLog log = logs.get(0);
		assertTrue(log instanceof ChaingrokError);
		ChaingrokLog.purgeLogs();
		//
		assertFalse(ChaingrokLog.hasLogs());
		ByteString byteString = null;
		try {
			new AccountAddress(byteString);
			fail("should throw NullPointerException");
		} catch (NullPointerException e) {
			assertNull(e.getMessage());
		}
	}
	
	@Test
	public void test002TestLength() {
		assertFalse(ChaingrokLog.hasLogs());
		new AccountAddress("00");
		assertTrue(ChaingrokLog.hasLogs());
		ArrayList<ChaingrokLog> logs = ChaingrokLog.getLogs();
		assertEquals(1,logs.size());
		ChaingrokLog log = logs.get(0);
		assertTrue(log instanceof ChaingrokError);
		ChaingrokLog.purgeLogs();
	}
	
	@Test
	public void test03TestNotEquals() {
		AccountAddress address = new AccountAddress("045d3e63dba85f759d66f9bed4a0e4c262d17f9713f25e846fdae63891837a98");
		assertFalse(address ==null);
		//
		assertFalse(ChaingrokError.hasLogs());
		assertFalse(address.equals(new Object()));
		assertEquals(1,ChaingrokError.getLogs().size());
		String msg = (String)ChaingrokError.getLogs().get(0).getObject();
		assertTrue(msg.startsWith("cannot compare objects of different classes"));
		ChaingrokError.purgeLogs();
	}
	
	@Test
	public void test004TestEquals() {
		AccountAddress address = new AccountAddress("045d3e63dba85f759d66f9bed4a0e4c262d17f9713f25e846fdae63891837a98");
		assertEquals(AccountAddress.BYTE_LENGTH,address.getBytes().length);
		assertEquals(address,address);
		AccountAddress address2 = new AccountAddress("045d3e63dba85f759d66f9bed4a0e4c262d17f9713f25e846fdae63891837a98");
		assertEquals(AccountAddress.BYTE_LENGTH,address2.getBytes().length);
		assertEquals(address,address2);
		AccountAddress address3 = new AccountAddress("1111111111111111111111111111111111111111111111111111111111111111");
		assertEquals(AccountAddress.BYTE_LENGTH,address3.getBytes().length);
		assertNotEquals(address,address3);
	}

}
