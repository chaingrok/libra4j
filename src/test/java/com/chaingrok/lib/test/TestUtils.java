package com.chaingrok.lib.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.lib.ChaingrokException;
import com.chaingrok.lib.Utils;
import com.google.protobuf.ByteString;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUtils extends TestClass {
	
	@Test
    public void test001ByteToHexString() {
		byte[] bytes = {0x00};
    	assertEquals("00",Utils.byteArrayToHexString(bytes));
		byte[] bytes2 = {0x74,0x65,0x73,0x74};
    	assertEquals("74657374",Utils.byteArrayToHexString(bytes2));
    }
	
	@Test
    public void test002HexSgtringToByte() {
		assertNull(Utils.hexStringToByteArray(null));
		//
		assertNotNull(Utils.hexStringToByteArray(""));
		assertEquals(0,Utils.hexStringToByteArray("").length);
		//
		byte[] bytes = {0x00};
    	assertArrayEquals(bytes,Utils.hexStringToByteArray("00"));
		byte[] bytes2 = {0x74,0x65,0x73,0x74};
    	assertArrayEquals(bytes2,Utils.hexStringToByteArray("74657374"));
    }
	
	@Test
    public void test003ByteStringToHex() {
		byte[] bytes = {0x00};
		ByteString byteString = ByteString.copyFrom(bytes);
    	assertEquals("00",Utils.byteStringToHexString(byteString));
		byte[] bytes2 = {0x74,0x65,0x73,0x74};
		byteString = ByteString.copyFrom(bytes2);
    	assertEquals("74657374",Utils.byteStringToHexString(byteString));
    }
	
	@Test
    public void test004HexToByteString() {
		byte[] bytes = {0x00};
		ByteString byteString = ByteString.copyFrom(bytes);
    	assertEquals(byteString,Utils.hexStringToByteString("00"));
		byte[] bytes2 = {0x74,0x65,0x73,0x74};
		byteString = ByteString.copyFrom(bytes2);
    	assertEquals(byteString,Utils.hexStringToByteString("74657374"));
    }
	
	@Test
	public void test005IntArrayToByteArray() {
		int[] integers = {0,1,64,127,128,192,255};
		byte[] bytes = {0x00,0x01,0x40,0x7f,(byte)0x80,(byte)0xc0,(byte)0xff};
		assertArrayEquals(bytes,Utils.intArrayToByteArray(integers));
	}
	
	@Test
	public void test006ReverseByteOrder() {
		//even number of bytes
		byte[] bytes = {0x12,0x34,0x56,0x78};
		byte[] bytes2 = {0x78,0x56,0x34,0x12};
		assertArrayEquals(bytes2,Utils.reverseByteOrder(bytes));
		//odd number of bytes
		byte[] bytes3 = {0x12,0x34,0x56,0x78,0x00};
		byte[] bytes4 = {0x00,0x78,0x56,0x34,0x12};
		assertArrayEquals(bytes4,Utils.reverseByteOrder(bytes3));
		//
		ByteString byteString = ByteString.copyFrom(bytes);
		assertArrayEquals(bytes2,Utils.reverseByteOrder(byteString));
		ByteString byteString2 = ByteString.copyFrom(bytes3);
		assertArrayEquals(bytes4,Utils.reverseByteOrder(byteString2));
	}
	
	@Test
	public void test007DeleteDirectory() throws IOException {
		Path tempDirPath = Files.createTempDirectory("foo");
		Path tempFilePath = tempDirPath.resolve("tmp-file");
		assertTrue(tempDirPath.toFile().exists());
		assertFalse(tempFilePath.toFile().exists());
		assertTrue(tempFilePath.toFile().createNewFile());
		assertTrue(tempFilePath.toFile().exists());
		assertTrue(Utils.deleteDirectoryWithContent(tempDirPath.toString()));
		assertFalse(tempDirPath.toFile().exists());
	}
	
	@Test
	public void test008ReadWriteFile() throws IOException {
		File file = File.createTempFile("tmpFile", "tmp");
		assertTrue(file.delete());
		assertFalse(file.exists());
		String content = "foo-bar";
		Utils.writeFile(content,file);
		assertTrue(file.exists());
		byte[] bytes = Utils.readFile(file);
		assertEquals(content+System.lineSeparator() ,new String(bytes));
	}
	
	@Test
	public void test009ReadWritErrors() throws IOException {
		try {
			Utils.readFile("");
			fail("empty filepath should fail");
		} catch (ChaingrokException e) {
			assertNotNull(e.getThrowable());
			assertTrue(e.getThrowable() instanceof IOException);
		}
		try {
			Utils.writeFile(" ","");
			fail("empty filepath should fail");
		} catch (ChaingrokException e) {
			assertNotNull(e.getThrowable());
			assertTrue(e.getThrowable() instanceof IOException);
		}
	}
	
	@Test
	public void test010ByteArrayToLong() {
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
	public void test011ByteArrayToInt() {
		int value = 0;
		assertEquals(value,Utils.byteArrayToInt(Utils.intToByteArray(value)));
		value = 1;
		assertEquals(value,Utils.byteArrayToInt(Utils.intToByteArray(value)));
		value = -1;
		assertEquals(value,Utils.byteArrayToInt(Utils.intToByteArray(value)));
		value = Integer.MAX_VALUE;
		assertEquals(value,Utils.byteArrayToInt(Utils.intToByteArray(value)));
		value = Integer.MIN_VALUE;
		assertEquals(value,Utils.byteArrayToInt(Utils.intToByteArray(value)));
	}
	
	@Test
	public void test0012ByteArrayToBigInt() {
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
	
	@Test
	public void test013TimestampMillisTotString() {
		long millis = 1568003223154L;
		String dateString = Utils.timestampMillisToDateString(millis);
		assertEquals("2019-09-09 04:27:03",dateString);
	}
	
	@Test
	public void test014GetByteArray() {
		int l = 10;
		byte[] bytes = Utils.getByteArray(l);
		assertEquals(l,bytes.length);
		for(byte b : bytes) {
			assertEquals((byte)0x00,b);
		}
		//
		l = 15;
		byte pattern = 0x01;
		bytes = Utils.getByteArray(l,pattern);
		assertEquals(l,bytes.length);
		for(byte b : bytes) {
			assertEquals(pattern,b);
		}
	}
}
