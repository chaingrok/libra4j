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
import com.chaingrok.lib.UInt32;
import com.chaingrok.lib.Utils;
import com.chaingrok.lib.ChaingrokLog.Type;
import com.google.protobuf.ByteString;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUInt32 extends TestClass {
	
	@Test
	public void test001WrongFormat() {
		//null with byte[]
		new UInt32((byte[])null);
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,ChaingrokLog.getLogs().get(0).getType());
		ChaingrokLog.purgeLogs();
		//null with ByteString
		new UInt32((ByteString)null);
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,ChaingrokLog.getLogs().get(0).getType());
		ChaingrokLog.purgeLogs();
		//no bytes
		byte[] bytes = {};
		new UInt32(bytes);
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,ChaingrokLog.getLogs().get(0).getType());
		ChaingrokLog.purgeLogs();
		//not enough bytes
		byte[] bytes2 = {0x00,0x01};
		new UInt32(bytes2);
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,ChaingrokLog.getLogs().get(0).getType());
		ChaingrokLog.purgeLogs();
		//too many bytes
		byte[] bytes3 = {0x00,0x01,0x02,0x03,0x04};
		new UInt32(bytes3);
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,ChaingrokLog.getLogs().get(0).getType());
		ChaingrokLog.purgeLogs();
	}
	
	@Test
	public void test002LongValues() {
		Long value = 0L;
		byte[] bytes = Utils.longToByteArray(value,UInt32.BYTE_LENGTH);
		ByteString byteString = ByteString.copyFrom(bytes);
		assertEquals(value,new UInt32(byteString).getAsLong());
		//
		value = 1L;
		bytes = Utils.longToByteArray(value,UInt32.BYTE_LENGTH);
		byteString = ByteString.copyFrom(bytes);
		assertEquals(value,new UInt32(byteString).getAsLong());
		//
		value = (long)Integer.MAX_VALUE;
		bytes = Utils.longToByteArray(value,UInt32.BYTE_LENGTH);
		byteString = ByteString.copyFrom(bytes);
		assertEquals(value,new UInt32(byteString).getAsLong());
	}
	
	@Test
	public void test003BiggerThanIntegerValues() {
		Long value = -1L;
		byte[] bytes = Utils.longToByteArray(value,UInt32.BYTE_LENGTH);
		ByteString byteString = ByteString.copyFrom(bytes);
		assertEquals((Long)UInt32.MAX_VALUE.longValue(),new UInt32(byteString).getAsLong());
		//
		value = new Long(Integer.MAX_VALUE) + 1L;
		assertTrue(value > 0);
		UInt32 uint32 = new UInt32(value);
		assertEquals(value,uint32.getAsLong());
	}
	
	@Test
	public void test004ContructFromLong() {
		Long value = 0L;
		UInt32 u32 = new UInt32(value);
		assertEquals(value,u32.getAsLong());
		//
		value = 1L;
		u32 = new UInt32(value);
		assertEquals(value,u32.getAsLong());
		//
		value = 100L;
		u32 = new UInt32(value);
		assertEquals(value,u32.getAsLong());
		//
		value = 123456L;
		u32 = new UInt32(value);
		assertEquals(value,u32.getAsLong());
		//
		value = (long)Integer.MAX_VALUE;
		u32 = new UInt32(value);
		assertEquals(value,u32.getAsLong());
		//
		value = UInt32.MAX_VALUE.longValue();
		u32 = new UInt32(value);
		assertEquals(value,u32.getAsLong());
		//
		value = -1L;
		assertFalse(ChaingrokLog.hasLogs());
		new UInt32(value);
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(Type.INVALID_VALUE,ChaingrokLog.getLogs().get(0).getType());
		ChaingrokLog.purgeLogs();
	}
	
	@Test
	public void test005GetBytes() {
		long value = 0L;
		UInt32 u32 = new UInt32(value);
		byte[] bytes = u32.getBytes();
		assertEquals(UInt32.BYTE_LENGTH,bytes.length);
		byte[] expected =  {0x00,0x00,0x00,0x00};
		assertArrayEquals(expected,bytes);
		//
		value = 1L;
		u32 = new UInt32(value);
		bytes = u32.getBytes();
		assertEquals(UInt32.BYTE_LENGTH,bytes.length);
		byte[]  expected2 =  {0x00,0x00,0x00,0x01};
		assertArrayEquals(expected2,bytes);
		//
		value = 100L;
		u32 = new UInt32(value);
		bytes = u32.getBytes();
		assertEquals(UInt32.BYTE_LENGTH,bytes.length);
		byte[]  expected3 =  {0x00,0x00,0x00,0x64};
		assertArrayEquals(expected3,bytes);
		//
		//
		value = UInt32.MAX_VALUE.longValue();
		u32 = new UInt32(value);
		bytes = u32.getBytes();
		assertEquals(UInt32.BYTE_LENGTH,bytes.length);
		byte[]  expected4 =  {(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff};
		assertArrayEquals(expected4,bytes);
	}
	
	@Test
	public void test005BytesVsLongValues() {
		Long number = 123456L;
		UInt32 uint32 = new UInt32(number);
		assertEquals(number,(Long)uint32.getAsLong());
		byte[] bytes = uint32.getBytes();
		uint32 = new UInt32(bytes);
		assertEquals(number,(Long)uint32.getAsLong());
		
	}
	
	@Test
	public void test006Equals() {
		long value = 9999L;
		UInt32 uint_1 = new UInt32(value);
		UInt32 uint_2 = new UInt32(value);
		assertNotSame(uint_1,uint_2);
		assertEquals(uint_1,uint_2);
		//
		uint_2 = new UInt32(value + 1L);
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
		UInt32 uint32 = new UInt32(0L);
		assertEquals(0,uint32.hashCode());
		uint32 = new UInt32(1L);
		assertEquals(1,uint32.hashCode());
		uint32 = new UInt32(UInt32.MAX_VALUE.longValue());
		assertEquals(-1,uint32.hashCode());
	}
}
