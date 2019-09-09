package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.misc.Libra4jError;
import com.chaingrok.libra4j.misc.Libra4jException;
import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.AccountAddress;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAccountAddress extends TestClass {
	
	@Test
	public void test001_testLength() {
		try {
			new AccountAddress("00");
			fail("should fail on incorrect size");
		} catch (Libra4jException e) {
			assertTrue(e.getMessage().startsWith("invalid account address size"));
		}
	}
	
	@Test
	public void test002_testNotEquals() {
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
	public void test003_testEquals() {
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
