package com.chaingrok.libra4j.test.misc;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.misc.LCSEncoder;
import com.chaingrok.libra4j.misc.Libra4jLog;
import com.chaingrok.libra4j.test.TestClass;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestLCSEncoder extends TestClass {
	
	@Test
	public void tes001BooleanLCSEncondingDecoding() {
		boolean b = true;
		ByteArrayOutputStream bos = LCSEncoder.encodeBoolean(b);
		byte[] bytes = bos.toByteArray();
		assertEquals(1,bytes.length);
		byte[] value = {0x01};
		assertArrayEquals(value,bytes);
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		assertEquals(b,LCSEncoder.decodeBoolean(bis));
		//
		boolean b2 = true;
		ByteArrayOutputStream bos2 = LCSEncoder.encodeBoolean(b2);
		byte[] bytes2 = bos2.toByteArray();
		assertEquals(1,bytes.length);
		byte[] value2 = {0x01};
		assertArrayEquals(value2,bytes2);
		ByteArrayInputStream bis2 = new ByteArrayInputStream(bytes2);
		assertEquals(b,LCSEncoder.decodeBoolean(bis2));
		//
		byte[] bytes3 = {0x02};
		ByteArrayInputStream bis3 = new ByteArrayInputStream(bytes3);
		assertFalse(Libra4jLog.hasLogs());
		assertNull(LCSEncoder.decodeBoolean(bis3));
		assertTrue(Libra4jLog.hasLogs());
		Libra4jLog.purgeLogs();
	}
	
}
