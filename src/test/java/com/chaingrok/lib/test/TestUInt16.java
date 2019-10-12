package com.chaingrok.lib.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.lib.ChaingrokLog;
import com.chaingrok.lib.UInt16;
import com.chaingrok.lib.Utils;
import com.chaingrok.lib.ChaingrokLog.Type;
import com.chaingrok.libra4j.test.TestClass;
import com.google.protobuf.ByteString;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUInt16 extends TestClass {
	
	@Test
	public void test001WrongFormat() {
		//null with byte[]
		new UInt16((byte[])null);
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,ChaingrokLog.getLogs().get(0).getType());
		ChaingrokLog.purgeLogs();
		//null with ByteString
		new UInt16((ByteString)null);
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,ChaingrokLog.getLogs().get(0).getType());
		ChaingrokLog.purgeLogs();
		//no bytes
		byte[] bytes = {};
		new UInt16(bytes);
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,ChaingrokLog.getLogs().get(0).getType());
		ChaingrokLog.purgeLogs();
		//not enough bytes
		byte[] bytes2 = {0x00};
		new UInt16(bytes2);
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,ChaingrokLog.getLogs().get(0).getType());
		ChaingrokLog.purgeLogs();
		//too many bytes
		byte[] bytes3 = {0x00,0x01,0x02};
		new UInt16(bytes3);
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,ChaingrokLog.getLogs().get(0).getType());
		ChaingrokLog.purgeLogs();
	}
	
	@Test
	public void test002LongValues() {
		Long value = 0L;
		byte[] bytes = Utils.longToByteArray(value,UInt16.BYTE_LENGTH);
		ByteString byteString = ByteString.copyFrom(bytes);
		assertEquals(value,new UInt16(byteString).getAsLong());
		//
		value = 1L;
		bytes = Utils.longToByteArray(value,UInt16.BYTE_LENGTH);
		byteString = ByteString.copyFrom(bytes);
		assertEquals(value,new UInt16(byteString).getAsLong());
		//
		value = (long)Short.MAX_VALUE;
		bytes = Utils.longToByteArray(value,UInt16.BYTE_LENGTH);
		byteString = ByteString.copyFrom(bytes);
		assertEquals(value,new UInt16(byteString).getAsLong());
	}
	
	@Test
	public void test003BiggerThanShortValues() {
		Long value = -1L;
		byte[] bytes = Utils.longToByteArray(value,UInt16.BYTE_LENGTH);
		ByteString byteString = ByteString.copyFrom(bytes);
		assertEquals((Long)UInt16.MAX_VALUE.longValue(),new UInt16(byteString).getAsLong());
		//
		value = new Long(Short.MAX_VALUE) + 1L;
		assertTrue(value > 0);
		UInt16 uint16 = new UInt16(value);
		assertEquals(value,uint16.getAsLong());
	}
	
	@Test
	public void test004ContructFromLong() {
		Long value = 0L;
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
		value = (long)Short.MAX_VALUE;
		u16 = new UInt16(value);
		assertEquals(value,u16.getAsLong());
		//
		value = UInt16.MAX_VALUE.longValue();
		u16 = new UInt16(value);
		assertEquals(value,u16.getAsLong());
		//
		value = -1L;
		assertFalse(ChaingrokLog.hasLogs());
		new UInt16(value);
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(Type.INVALID_VALUE,ChaingrokLog.getLogs().get(0).getType());
		ChaingrokLog.purgeLogs();
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
		//
		value = UInt16.MAX_VALUE.longValue();
		u16 = new UInt16(value);
		bytes = u16.getBytes();
		assertEquals(UInt16.BYTE_LENGTH,bytes.length);
		byte[]  expected4 =  {(byte)0xff,(byte)0xff};
		assertArrayEquals(expected4,bytes);
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
	
	@Test
	public void test006Equals() {
		long value = 999L;
		UInt16 uint_1 = new UInt16(value);
		UInt16 uint_2 = new UInt16(value);
		assertNotSame(uint_1,uint_2);
		assertEquals(uint_1,uint_2);
		//
		uint_2 = new UInt16(value + 1L);
		assertNotEquals(uint_1,uint_2);
		//
		Object object = null;
		assertFalse(uint_1.equals(object)); //test the overriden equals against null value.
		//
		uint_1.equals(new Object());
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(Type.INVALID_CLASS,ChaingrokLog.getLogs().get(0).getType());
		assertTrue(((String)ChaingrokLog.getLogs().get(0).getObject()).contains("cannot compare objects of different classes"));
		ChaingrokLog.purgeLogs();
	}
	
	@Test
	public void test007hashCode() {
		UInt16 uint16 = new UInt16(0L);
		assertEquals(0,uint16.hashCode());
		uint16 = new UInt16(1L);
		assertEquals(65536,uint16.hashCode());
		uint16 = new UInt16(UInt16.MAX_VALUE.longValue());
		assertEquals(-65536,uint16.hashCode());
	}
}
