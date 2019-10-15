package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.lib.ChaingrokLog;
import com.chaingrok.lib.ChaingrokLog.Type;
import com.chaingrok.lib.Utils;
import com.chaingrok.lib.test.TestClass;
import com.chaingrok.libra4j.types.EventKey;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestEventKey extends TestClass {
	
	@Test
	public void test001NewInstance() {
		String hex = "19ec9d6b9c90d4283260e125d69682bc1551e15f8466b1aff0b9d417a3a4fb75";
		byte[] bytes = Utils.hexStringToByteArray(hex);
		EventKey eventKey = new EventKey(bytes);
		assertSame(bytes,eventKey.getBytes());
		//
		hex = "00";
		bytes = Utils.hexStringToByteArray(hex);
		assertFalse(ChaingrokLog.hasLogs());
		eventKey = new EventKey(bytes);
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,ChaingrokLog.getLogs().get(0).getType());
		ChaingrokLog.purgeLogs();
		//
		assertFalse(ChaingrokLog.hasLogs());
		eventKey = new EventKey(null);
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(Type.NULL_DATA,ChaingrokLog.getLogs().get(0).getType());
		ChaingrokLog.purgeLogs();
	}
	
	

}
