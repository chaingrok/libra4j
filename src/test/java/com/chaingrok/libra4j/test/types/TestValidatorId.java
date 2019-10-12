package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.lib.ChaingrokError;
import com.chaingrok.lib.ChaingrokLog;
import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.ValidatorId;
import com.google.protobuf.ByteString;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestValidatorId extends TestClass {
	
	@Test
	public void test001NewInstanceOk() {
		byte[] id = {0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07};
		ValidatorId validatorId  = new ValidatorId(ByteString.copyFrom(id));
		assertArrayEquals(id,validatorId.getBytes());
	}
	
	@Test
	public void test002NewInstanceKo() {
		assertFalse(ChaingrokLog.hasLogs());
		byte[] id = {0x00};
		ValidatorId validatorId  = new ValidatorId(ByteString.copyFrom(id));
		assertArrayEquals(id,validatorId.getBytes());
		assertTrue(ChaingrokLog.hasLogs());
		ArrayList<ChaingrokLog> logs = ChaingrokLog.getLogs();
		assertEquals(1,logs.size());
		ChaingrokLog log = logs.get(0);
		assertTrue(log instanceof ChaingrokError);
		ChaingrokLog.purgeLogs();
	}

}
