package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.misc.Libra4jException;
import com.chaingrok.libra4j.misc.Libra4jLog;
import com.chaingrok.libra4j.misc.Libra4jLog.Type;
import com.chaingrok.libra4j.misc.Utils;
import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.UInt16;
import com.google.protobuf.ByteString;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUInt16 extends TestClass {
	
	@Test
	public void test001WrongFormat() {
		//null with byte[]
		new UInt16((byte[])null);
		assertEquals(1,Libra4jLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,Libra4jLog.getLogs().get(0).getType());
		Libra4jLog.purgeLogs();
		//null with ByteString
		new UInt16((ByteString)null);
		assertEquals(1,Libra4jLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,Libra4jLog.getLogs().get(0).getType());
		Libra4jLog.purgeLogs();
		//no bytes
		byte[] bytes = {};
		new UInt16(bytes);
		assertEquals(1,Libra4jLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,Libra4jLog.getLogs().get(0).getType());
		Libra4jLog.purgeLogs();
		//not enough bytes
		byte[] bytes2 = {0x00};
		new UInt16(bytes2);
		assertEquals(1,Libra4jLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,Libra4jLog.getLogs().get(0).getType());
		Libra4jLog.purgeLogs();
		//too many bytes
		byte[] bytes3 = {0x00,0x01,0x02};
		new UInt16(bytes3);
		assertEquals(1,Libra4jLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,Libra4jLog.getLogs().get(0).getType());
		Libra4jLog.purgeLogs();
	}
	
	@Test
	public void test002LongValues() {
		long value = 0L;
		byte[] bytes = Utils.longToByteArray(value,UInt16.BYTE_LENGTH);
		ByteString byteString = ByteString.copyFrom(bytes);
		assertEquals(value,new UInt16(byteString).getAsLong());
		//
		value = 1L;
		bytes = Utils.longToByteArray(value,UInt16.BYTE_LENGTH);
		byteString = ByteString.copyFrom(bytes);
		assertEquals(value,new UInt16(byteString).getAsLong());
		//
		value = Short.MAX_VALUE;
		bytes = Utils.longToByteArray(value,UInt16.BYTE_LENGTH);
		byteString = ByteString.copyFrom(bytes);
		assertEquals(value,new UInt16(byteString).getAsLong());
	}
	
	@Test
	public void test003BiggerThanIntegerValues() {
		long value = -1L;
		byte[] bytes = Utils.longToByteArray(value,Short.BYTES);
		ByteString byteString = ByteString.copyFrom(bytes);
		assertEquals(UInt16.MAX_VALUE.longValue(),new UInt16(byteString).getAsLong());
		//
		value = new Long(Short.MAX_VALUE) + 1L;
		assertTrue(value > 0);
		UInt16 uint32 = new UInt16(value);
		assertEquals(value,uint32.getAsLong());
	}
	
	@Test
	public void test004ContructFromLong() {
		long value = 0L;
		UInt16 u16 = new UInt16(value);
		assertEquals(value,u16.getAsLong());
		//
		value = 1L;
		u16 = new UInt16(value);
		assertEquals(value,u16.getAsLong());
		//
		value = 100L;
		u16 = new UInt16(value);
		assertEquals(value,u16.getAsLong());
		//
		value = 12345L;
		u16 = new UInt16(value);
		assertEquals(value,u16.getAsLong());
		//
		value = Short.MAX_VALUE;
		u16 = new UInt16(value);
		assertEquals(value,u16.getAsLong());
		//
		value = UInt16.MAX_VALUE.longValue();
		u16 = new UInt16(value);
		assertEquals(value,u16.getAsLong());
		//
		value = -1L;
		try {
			new UInt16(value);
			fail("should fail with negative value");
		} catch (Libra4jException e) {
			assertEquals("UInt cannot be constructed from negative long value: " + value,e.getMessage());
		}
	}
	
	@Test
	public void test005GetBytes() {
		long value = 0L;
		UInt16 u16 = new UInt16(value);
		byte[] bytes = u16.getBytes();
		assertEquals(UInt16.BYTE_LENGTH,bytes.length);
		byte[] expected =  {0x00,0x00};
		assertArrayEquals(expected,bytes);
		//
		value = 1L;
		u16 = new UInt16(value);
		bytes = u16.getBytes();
		assertEquals(UInt16.BYTE_LENGTH,bytes.length);
		byte[]  expected2 =  {0x00,0x01};
		assertArrayEquals(expected2,bytes);
		//
		value = 100L;
		u16 = new UInt16(value);
		bytes = u16.getBytes();
		assertEquals(UInt16.BYTE_LENGTH,bytes.length);
		byte[]  expected3 =  {0x00,0x64};
		assertArrayEquals(expected3,bytes);
	}
	
	@Test
	public void test005BytesVsLongValues() {
		Long number = 1234L;
		UInt16 uint16 = new UInt16(number);
		assertEquals(number,(Long)uint16.getAsLong());
		byte[] bytes = uint16.getBytes();
		uint16 = new UInt16(bytes);
		assertEquals(number,(Long)uint16.getAsLong());
	}
}
