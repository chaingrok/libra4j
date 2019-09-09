package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigInteger;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.misc.Libra4jException;
import com.chaingrok.libra4j.misc.Utils;
import com.chaingrok.libra4j.types.UInt64;
import com.google.protobuf.ByteString;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUInt64 {
	
	@Test
	public void test001_testWrongFormat() {
		byte[] bytes = {};
		try {
			new UInt64(bytes);
			fail("should fail on array size");
		} catch (Libra4jException e){
			assertEquals("UInt64 length is invalid: " + 0 + " <> " + UInt64.BYTE_LENGTH,e.getMessage());
		}
		//
		byte[] bytes2 = {0x00,0x01};
		try {
			new UInt64(bytes2);
			fail("should fail on array size");
		} catch (Libra4jException e){
			assertEquals("UInt64 length is invalid: " + 2 + " <> " + UInt64.BYTE_LENGTH,e.getMessage());
		}
	}
	
	@Test
	public void test002_testLongValues() {
		long value = 0L;
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
	public void test003_testBiggerThanLongValues() {
		long value = -1L;
		byte[] bytes = Utils.longToByteArray(value);
		ByteString byteString = ByteString.copyFrom(bytes);
		try {
			new UInt64(byteString).getAsLong();
			fail("should fail wit arithmetic exception");
		} catch (ArithmeticException e)  {
			assertTrue(e.getMessage().startsWith("BigInteger out of long range"));
		}
		assertEquals("18446744073709551615",new UInt64(byteString).getAsBigInt().toString()); //-1L -> positive due to 1 compl
		//
		value = Long.MAX_VALUE;
		BigInteger bigInt = BigInteger.valueOf(value = Long.MAX_VALUE);
		bigInt = bigInt.add(BigInteger.ONE);
		bytes = new byte[8];
		System.arraycopy(bigInt.toByteArray(), 1, bytes,0,8);
		byteString = ByteString.copyFrom(bytes);
		try {
			new UInt64(byteString).getAsLong();
			fail("should fail wit arithmetic exception");
		} catch (ArithmeticException e)  {
			assertTrue(e.getMessage().startsWith("BigInteger out of long range"));
		}
		assertEquals("9223372036854775808",new UInt64(byteString).getAsBigInt().toString()); // 9 223 372 036 854 775 808 = Long.MAX_INT + 1
	}
	
	@Test
	public void test003_testContructFromLong() {
		long value = 0L;
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
		value = Long.MAX_VALUE;
		u64 = new UInt64(value);
		assertEquals(value,u64.getAsLong());
		//
		value = -1L;
		try {
			new UInt64(value);
			fail("should fail with negative value");
		} catch (Libra4jException e) {
			assertEquals("UInt64 cannot be constructed from negative long value: " + value,e.getMessage());
		}
	}
}
