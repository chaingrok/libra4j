package com.chaingrok.libra4j.misc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.bind.DatatypeConverter;

import com.google.protobuf.ByteString;

public class Utils {
	
	public static byte[] readFile(String path) {
		byte[] bytes = null;
		try {
			bytes = Files.readAllBytes(Paths.get(path));
		} catch (IOException e) {
			throw new Libra4jException(e);
		}
		return bytes;
	}
	
	public static byte[] readFile(File file) {
		return readFile(file.getAbsolutePath());
	}
	
	public static void writeFile(String string, String filepath) {
		try (PrintWriter out = new PrintWriter(filepath)) {
		    out.println(string);
		} catch (FileNotFoundException e) {
			throw new Libra4jException(e);
		}
	}
	
	public static void writeFile(String string, File file) {
		writeFile(string,file.getAbsolutePath());
	}
	
	public static String byteArrayToHexString(byte[] bytes) {
	   StringBuilder sb = new StringBuilder(bytes.length * 2);
	   for(byte b: bytes)
	      sb.append(String.format("%02x",b));
	   return sb.toString();
	}
		
	public static byte[] hexStringToByteArray(String hex) {
		return DatatypeConverter.parseHexBinary(hex);
	}
	
	public static byte[] toBigEndian(byte[] bytes) {
		byte[] result = null;
		if (bytes.length == 4) {
			result = ByteBuffer.allocate(4).putInt(Integer.reverseBytes(new BigInteger(bytes).intValue())).array();
			
		} else {
			throw new Libra4jException("not implemented!");
		}
		return result;
	}
	
	public static ByteString hexStringToByteString(String hex) {
		return ByteString.copyFrom(DatatypeConverter.parseHexBinary(hex));
	}
	
	public static String byteStringToHexString(ByteString byteString) {
		return byteArrayToHexString(byteString.toByteArray());
	}
	
	public static byte[] reverseByteOrder(ByteString byteString) {
		return reverseByteOrder(byteString.toByteArray());
	}
	
	public static byte[] reverseByteOrder(byte[] bytes) {
		byte[] result = new byte[bytes.length];
		for (int i=0 ; i < bytes.length ; ++i)
			result[i] = bytes[bytes.length-i-1];
		return result;
	}
	
	public static byte[] longToByteArray(long x) {
	    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
	    buffer.putLong(x);
	    return buffer.array();
	}

	public static long byteArrayToLong(byte[] bytes) {
	    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
	    buffer.put(bytes);
	    buffer.flip();
	    return buffer.getLong();
	}
	
	public static byte[] intToByteArray(int x) {
	    ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
	    buffer.putInt(x);
	    return buffer.array();
	}
	
	public static int byteArrayToInt(byte[] bytes) {
	    ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
	    buffer.put(bytes);
	    buffer.flip();
	    return buffer.getInt();
	}
	
	public static BigInteger byteArraytoBigInt(byte[] bytes) {
		BigInteger result;
		byte[] prefixedBytes = new byte[bytes.length+1];
		prefixedBytes[0] = (byte)0x00; // 0x00 as leading byte to avoid 2-complement logic
		System.arraycopy(bytes, 0,prefixedBytes, 1,bytes.length);
		result = new BigInteger(prefixedBytes);
		return result;
	}
	
	public static String timestampMillisToDateString(long millis) {
		String result = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		sdf.format(calendar.getTime());
		result = sdf.format(calendar.getTime());
		return result;
	}

}
