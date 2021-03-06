package com.chaingrok.lib.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigInteger;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.lib.ChaingrokLog;
import com.chaingrok.lib.UInt64;
import com.chaingrok.lib.Utils;
import com.chaingrok.lib.ChaingrokLog.Type;
import com.google.protobuf.ByteString;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUInt64 extends TestClass {
	
	@Test
	public void test001WrongFormat() {
		//null with byte[]
		new UInt64((byte[])null);
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,ChaingrokLog.getLogs().get(0).getType());
		ChaingrokLog.purgeLogs();
		//null with ByteString
		new UInt64((ByteString)null);
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,ChaingrokLog.getLogs().get(0).getType());
		ChaingrokLog.purgeLogs();
		//no bytes
		byte[] bytes = {};
		new UInt64(bytes);
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,ChaingrokLog.getLogs().get(0).getType());
		ChaingrokLog.purgeLogs();
		//not enough bytes
		byte[] bytes2 = {0x00,0x01};
		new UInt64(bytes2);
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,ChaingrokLog.getLogs().get(0).getType());
		ChaingrokLog.purgeLogs();
		//too many bytes
		byte[] bytes3 = {0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08};
		new UInt64(bytes3);
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,ChaingrokLog.getLogs().get(0).getType());
		ChaingrokLog.purgeLogs();
	}
	
	@Test
	public void test002LongValues() {
		Long value = 0L;
		byte[] bytes = Utils.longToByteArray(value);
		ByteString byteString = ByteString.copyFrom(bytes);
		assertEquals(value,new UInt64(byteString).getAsLong());
		//
		value = 1L;
		bytes = Utils.longToByteArray(value);
		byteString = ByteString.copyFrom(bytes);
		assertEquals(value,new UInt64(byteString).getAsLong());
		//
		value = Long.MAX_VALUE;
		bytes = Utils.longToByteArray(value);
		byteString = ByteString.copyFrom(bytes);
		assertEquals(value,new UInt64(byteString).getAsLong());
	}
	
	@Test
	public void test003BiggerThanLongValues() {
		long value = -1L;
		byte[] bytes = Utils.longToByteArray(value);
		ByteString byteString = ByteString.copyFrom(bytes);
		try {
			new UInt64(byteString).getAsLong();
			fail("should fail with arithmetic exception");
		} catch (ArithmeticException e)  {
			assertTrue(e.getMessage().startsWith("BigInteger out of long range"));
		}
		assertEquals("18446744073709551615",new UInt64(byteString).getAsBigInt().toString()); //-1L -> positive due to 1 compl
		//
		value = Long.MAX_VALUE;
		BigInteger bigInt = BigInteger.valueOf(Long.MAX_VALUE);
		bigInt = bigInt.add(BigInteger.ONE);
		bytes = new byte[8];
		System.arraycopy(bigInt.toByteArray(), 1, bytes,0,8);
		UInt64 uint64 = new UInt64(byteString); // must succeed
		byteString = ByteString.copyFrom(bytes);
		try {
			uint64.getAsLong();
			fail("should fail with arithmetic exception");
		} catch (ArithmeticException e)  {
			assertTrue(e.getMessage().startsWith("BigInteger out of long range"));
		}
		assertEquals("9223372036854775808",new UInt64(byteString).getAsBigInt().toString()); // 9 223 372 036 854 775 808 = Long.MAX_VALUE + 1
	}
	
	@Test
	public void test004ConstructFromLong() {
		Long value = 0L;
		UInt64 u64 = new UInt64(value);
		assertEquals(value,u64.getAsLong());
		//
		value = 1L;
		u64 = new UInt64(value);
		assertEquals(value,u64.getAsLong());
		//
		value = 100L;
		u64 = new UInt64(value);
		assertEquals(value,u64.getAsLong());
		//
		value = 123456L;
		u64 = new UInt64(value);
		assertEquals(value,u64.getAsLong());
		//
		value = Long.MAX_VALUE;
		u64 = new UInt64(value);
		assertEquals(value,u64.getAsLong());
		//
		value = -1L;
		assertFalse(ChaingrokLog.hasLogs());
		new UInt64(value);
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(Type.INVALID_VALUE,ChaingrokLog.getLogs().get(0).getType());
		ChaingrokLog.purgeLogs();
	}
	
	@Test
	public void test005GetBytes() {
		long value = 0L;
		UInt64 u64 = new UInt64(value);
		byte[] bytes = u64.getBytes();
		assertEquals(UInt64.BYTE_LENGTH,bytes.length);
		byte[] expected =  {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
		assertArrayEquals(expected,bytes);
		//
		value = 1L;
		u64 = new UInt64(value);
		bytes = u64.getBytes();
		assertEquals(UInt64.BYTE_LENGTH,bytes.length);
		byte[]  expected2 =  {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x01};
		assertArrayEquals(expected2,bytes);
		//
		value = 100L;
		u64 = new UInt64(value);
		bytes = u64.getBytes();
		assertEquals(UInt64.BYTE_LENGTH,bytes.length);
		byte[]  expected3 =  {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x64};
		assertArrayEquals(expected3,bytes);
		//
		BigInteger bigValue = UInt64.MAX_VALUE;
		u64 = new UInt64(bigValue);
		bytes = u64.getBytes();
		assertEquals(UInt64.BYTE_LENGTH,bytes.length);
		byte[]  expected4 =  {(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff};
		assertArrayEquals(expected4,bytes);
	}
	
	@Test
	public void test005BytesVsLongValues() {
		Long number = 123456L;
		UInt64 uint64 = new UInt64(number);
		assertEquals(number,(Long)uint64.getAsLong());
		byte[] bytes = uint64.getBytes();
		uint64 = new UInt64(bytes);
		assertEquals(number,(Long)uint64.getAsLong());
	}
	
	@Test
	public void test006Equals() {
		long value = 9999L;
		UInt64 uint_1 = new UInt64(value);
		UInt64 uint_2 = new UInt64(value);
		assertNotSame(uint_1,uint_2);
		assertEquals(uint_1,uint_2);
		//
		uint_2 = new UInt64(value + 1L);
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
		UInt64 uint64 = new UInt64(0L);
		assertEquals(0,uint64.hashCode());
		uint64 = new UInt64(1L);
		assertEquals(0,uint64.hashCode());
		uint64 = new UInt64(UInt64.MAX_VALUE);
		assertEquals(-1,uint64.hashCode());
	}
}
