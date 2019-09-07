package com.chaingrok.libra4j.test.misc;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.misc.Utils;
import com.google.protobuf.ByteString;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUtils {
	
	@Test
    public void test001_byteToHex() {
		byte[] bytes = {0x00};
    	assertEquals("00",Utils.byteArrayToHexString(bytes));
		byte[] bytes2 = {0x74,0x65,0x73,0x74};
    	assertEquals("74657374",Utils.byteArrayToHexString(bytes2));
    }
	
	@Test
    public void test002_hexToByte() {
		byte[] bytes = {0x00};
    	assertArrayEquals(bytes,Utils.hexStringToByteArray("00"));
		byte[] bytes2 = {0x74,0x65,0x73,0x74};
    	assertArrayEquals(bytes2,Utils.hexStringToByteArray("74657374"));
    }
	
	@Test
    public void test003_byteStringToHex() {
		byte[] bytes = {0x00};
		ByteString byteString = ByteString.copyFrom(bytes);
    	assertEquals("00",Utils.byteStringToHexString(byteString));
		byte[] bytes2 = {0x74,0x65,0x73,0x74};
		byteString = ByteString.copyFrom(bytes2);
    	assertEquals("74657374",Utils.byteStringToHexString(byteString));
    }
	
	@Test
    public void test004_hexToByteString() {
		byte[] bytes = {0x00};
		ByteString byteString = ByteString.copyFrom(bytes);
    	assertEquals(byteString,Utils.hexStringToByteString("00"));
		byte[] bytes2 = {0x74,0x65,0x73,0x74};
		byteString = ByteString.copyFrom(bytes2);
    	assertEquals(byteString,Utils.hexStringToByteString("74657374"));
    }
	
	@Test
	public void test005_reverseByteOrder() {
		//even number of bytes
		byte[] bytes = {0x12,0x34,0x56,0x78};
		byte[] bytes2 = {0x78,0x56,0x34,0x12};
		assertArrayEquals(bytes2,Utils.reverseByteOrder(bytes));
		//odd number of bytes
		byte[] bytes3 = {0x12,0x34,0x56,0x78,0x00};
		byte[] bytes4 = {0x00,0x78,0x56,0x34,0x12};
		assertArrayEquals(bytes4,Utils.reverseByteOrder(bytes3));
	}
	
	@Test
	public void test006_readWriteFile() throws IOException {
		File file = File.createTempFile("tmpFile", "tmp");
		assertTrue(file.delete());
		assertFalse(file.exists());
		String content = "foo-bar";
		Utils.writeFile(content,file);
		assertTrue(file.exists());
		byte[] bytes = Utils.readFile(file);
		assertEquals(content+"\n",new String(bytes));
	}
	
	@Test
	public void test007_byteArrayToLong() {
		long value = 0L;
		assertEquals(value,Utils.byteArrayToLong(Utils.longToByteArray(value)));
		value = 1L;
		assertEquals(value,Utils.byteArrayToLong(Utils.longToByteArray(value)));
		value = -1L;
		assertEquals(value,Utils.byteArrayToLong(Utils.longToByteArray(value)));
		value = Long.MAX_VALUE;
		assertEquals(value,Utils.byteArrayToLong(Utils.longToByteArray(value)));
		value = Long.MIN_VALUE;
		assertEquals(value,Utils.byteArrayToLong(Utils.longToByteArray(value)));
	}
	
	@Test
	public void test008_byteArrayToBigInt() {
		byte[] bytes={0x00};
		assertEquals(BigInteger.valueOf(0),Utils.byteArraytoBigInt(bytes));
		byte[] bytes2={0x00,0x01};
		assertEquals(BigInteger.valueOf(1),Utils.byteArraytoBigInt(bytes2));
		byte[] bytes3={0x11};
		assertEquals(BigInteger.valueOf(17),Utils.byteArraytoBigInt(bytes3));
		byte[] bytes4={0x01,0x01};
		assertEquals(BigInteger.valueOf(257),Utils.byteArraytoBigInt(bytes4));
		Long value = Long.MAX_VALUE;
		byte[] bytes5 = Utils.longToByteArray(value);
		assertEquals(BigInteger.valueOf(Long.MAX_VALUE),Utils.byteArraytoBigInt(bytes5));
		value = Long.MIN_VALUE;
		byte[] bytes6 = Utils.longToByteArray(value);
		assertEquals(new BigInteger("9223372036854775808"),Utils.byteArraytoBigInt(bytes6)); //9 223 372 036 854 775 808 = Long.MAX_VALUE + 1 due to 2-compl logic
		value = -1L;
		byte[] bytes7 = Utils.longToByteArray(value);
		assertEquals(new BigInteger("18446744073709551615"),Utils.byteArraytoBigInt(bytes7)); //18 446 744 073 709 551 615 = Long.MAX_VALUE + 1 due to 2-compl logic
	}
 
}
