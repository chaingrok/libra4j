package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.misc.Libra4jError;
import com.chaingrok.libra4j.misc.Libra4jLog;
import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.AccountAddress;
import com.google.protobuf.ByteString;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAccountAddress extends TestClass {
	
	
	@Test
	public void test001_testLength() {
		assertFalse(Libra4jLog.hasLogs());
		new AccountAddress((byte[])null);
		assertTrue(Libra4jLog.hasLogs());
		ArrayList<Libra4jLog> logs = Libra4jLog.getLogs();
		assertEquals(1,logs.size());
		Libra4jLog log = logs.get(0);
		assertTrue(log instanceof Libra4jError);
		Libra4jLog.purgeLogs();
		//
		assertFalse(Libra4jLog.hasLogs());
		ByteString byteString = null;
		try {
			new AccountAddress(byteString);
			fail("should throw null Exception");
		} catch (NullPointerException e) {
			assertTrue(e instanceof NullPointerException);
		}
	}
	
	@Test
	public void test002_testLength() {
		assertFalse(Libra4jLog.hasLogs());
		new AccountAddress("00");
		assertTrue(Libra4jLog.hasLogs());
		ArrayList<Libra4jLog> logs = Libra4jLog.getLogs();
		assertEquals(1,logs.size());
		Libra4jLog log = logs.get(0);
		assertTrue(log instanceof Libra4jError);
		Libra4jLog.purgeLogs();
	}
	
	@Test
	public void test03_testNotEquals() {
		AccountAddress address = new AccountAddress("045d3e63dba85f759d66f9bed4a0e4c262d17f9713f25e846fdae63891837a98");
		assertFalse(address.equals(null));
		//
		assertFalse(Libra4jError.hasLogs());
		assertFalse(address.equals(new Object()));
		assertEquals(1,Libra4jError.getLogs().size());
		String msg = (String)Libra4jError.getLogs().get(0).getObject();
		assertTrue(msg.startsWith("cannot compare objects of different classes"));
		Libra4jError.purgeLogs();
	}
	
	@Test
	public void test004_testEquals() {
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
