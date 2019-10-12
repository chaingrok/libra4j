package com.chaingrok.libra4j.test.misc;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.lib.ChaingrokLog;
import com.chaingrok.lib.UInt16;
import com.chaingrok.lib.UInt32;
import com.chaingrok.lib.UInt64;
import com.chaingrok.lib.UInt8;
import com.chaingrok.lib.Utils;
import com.chaingrok.lib.ChaingrokLog.Type;
import com.chaingrok.libra4j.misc.LCSProcessor;
import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.AccountAddress;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestLCSProcessor extends TestClass {
	
	
	@Test
	public void test001HelperFunctions() {
		LCSProcessor encoder = LCSProcessor.buildEncoder();
		assertNotNull(encoder);
		assertNotNull(encoder.getBos());
		assertEquals(0,(int)encoder.getEncodedDataSize());
		encoder.encode(true);
		assertNotNull(encoder.getBos());
		assertEquals(1,(int)encoder.getEncodedDataSize());
		encoder.encode(new UInt64(1L));
		assertEquals(9,(int)encoder.getEncodedDataSize());
		//
		byte[] bytes = {0x01,0x02};
		LCSProcessor decoder = LCSProcessor.buildDecoder(bytes);
		assertNotNull(decoder);
		assertNotNull(decoder.getBis());
		assertEquals(2,(int)decoder.getUndecodedDataSize());
		assertArrayEquals(bytes,decoder.getUndecodedBytes());
	}
	
	@Test
	public void test002BooleanLCSEncodingDecoding() {
		boolean b = false;
		byte[] bytes = LCSProcessor.buildEncoder()
				.encode(b)
				.build();
		assertEquals(1,bytes.length);
		byte[] value = {0x00};
		assertArrayEquals(value,bytes);
		LCSProcessor decoder = LCSProcessor.buildDecoder(bytes);
		assertEquals(b,decoder.decodeBoolean());
		//
		b = true;
		bytes = LCSProcessor.buildEncoder()
				.encode(b)
				.build();
		assertEquals(1,bytes.length);
		byte[] value2 = {0x01};
		assertArrayEquals(value2,bytes);
		decoder = LCSProcessor.buildDecoder(bytes);
		assertEquals(b,decoder.decodeBoolean());
		//
		byte[] bytes3 = {0x02};
		assertFalse(ChaingrokLog.hasLogs());
		decoder = LCSProcessor.buildDecoder(bytes3);
		assertNull(decoder.decodeBoolean());
		assertTrue(ChaingrokLog.hasLogs());
		ChaingrokLog.purgeLogs();
	}
	
	@Test
	public void test003StringLCSEncodingDecoding() {
		String string = null;
		byte[] bytes = LCSProcessor.buildEncoder()
				.encode(string)
				.build();
		assertNull(bytes);
		//string only
		string = "Hello, world";
		bytes = LCSProcessor.buildEncoder()
				.encode(string)
				.build();
		LCSProcessor decoder = LCSProcessor.buildDecoder(bytes);
		String result = decoder.decodeString();
		assertEquals(string,result);
		// string UTF-8 + length
		string = "ሰማይ አይታረስ ንጉሥ አይከሰስ።";
		assertEquals(20,string.length());
		assertEquals(54,string.getBytes().length);
		bytes = LCSProcessor.buildEncoder()
				.encode(string)
				.build();
		assertEquals(string.getBytes().length + UInt32.BYTE_LENGTH,bytes.length);
		byte[] testVector = Utils.hexStringToByteArray("36000000E188B0E1889BE18BAD20E18AA0E18BADE189B3E188A8E188B520E18A95E18C89E188A520E18AA0E18BADE18AA8E188B0E188B5E18DA2");
		assertArrayEquals(testVector,bytes);
		decoder = LCSProcessor.buildDecoder(bytes);
		result = decoder.decodeString();
		assertEquals(string,result);
	}
	
	@Test
	public void test004UIntLCSEncodingDecodingEdgeCases() {
		UInt8 uint8 = null;
		byte[] bytes = LCSProcessor.buildEncoder()
				.encode(uint8)
				.build();
		assertNull(bytes);
		//
		UInt16 uint16 = null;
		bytes = LCSProcessor.buildEncoder()
				.encode(uint16)
				.build();
		assertNull(bytes);
		//
		UInt32 uint32 = null;
		bytes = LCSProcessor.buildEncoder()
				.encode(uint32)
				.build();
		assertNull(bytes);
		//
		UInt64 uint64 = null;
		bytes = LCSProcessor.buildEncoder()
				.encode(uint64)
				.build();
		assertNull(bytes);
		//
		bytes = Utils.hexStringToByteArray("");
		assertNotNull(bytes);
		assertEquals(0,bytes.length);
	    LCSProcessor decoder = LCSProcessor.buildDecoder(bytes);
	    assertNotNull(decoder.getBis());
	    assertEquals(0,decoder.getBis().available());
	    assertEquals(0,ChaingrokLog.getLogs().size());
		decoder.decodeUInt8();
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,ChaingrokLog.getLogs().get(0).getType());
		ChaingrokLog.purgeLogs();
		//
		decoder = LCSProcessor.buildDecoder(Utils.hexStringToByteArray("00"));
		assertEquals(0,ChaingrokLog.getLogs().size());
		decoder.decodeUInt16();
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,ChaingrokLog.getLogs().get(0).getType());
		ChaingrokLog.purgeLogs();
		//
		decoder = LCSProcessor.buildDecoder(Utils.hexStringToByteArray("001122"));
		assertEquals(0,ChaingrokLog.getLogs().size());
		decoder.decodeUInt32();
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,ChaingrokLog.getLogs().get(0).getType());
		ChaingrokLog.purgeLogs();
		//
		decoder = LCSProcessor.buildDecoder(Utils.hexStringToByteArray("00112233445566"));
		assertEquals(0,ChaingrokLog.getLogs().size());
		decoder.decodeUInt64();
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,ChaingrokLog.getLogs().get(0).getType());
		ChaingrokLog.purgeLogs();
	}
	
	@Test
	public void test005UInt8LCSEncodingDecoding() {
		Long longValue = 0L;
		UInt8 uint8 = new UInt8(longValue);
		byte[] bytes = LCSProcessor.buildEncoder()
				.encode(uint8)
				.build();
		assertEquals(UInt8.BYTE_LENGTH,bytes.length);
		byte[] byteValue = {0x00};
		assertArrayEquals(byteValue,bytes);
		LCSProcessor decoder = LCSProcessor.buildDecoder(bytes);
		UInt8 result = decoder.decodeUInt8();
		assertEquals(uint8,result);
		assertEquals(longValue,result.getAsLong());
		//
		longValue =1L;
		uint8 = new UInt8(longValue);
		bytes = LCSProcessor.buildEncoder()
				.encode(uint8)
				.build();
		assertEquals(UInt8.BYTE_LENGTH,bytes.length);
		byte[] byteValue2 = {0x01};
		assertArrayEquals(byteValue2,bytes);
		decoder = LCSProcessor.buildDecoder(bytes);
		result = decoder.decodeUInt8();
		assertEquals(uint8,result);
		assertEquals(longValue,result.getAsLong());
		//
		longValue =32L;
		uint8 = new UInt8(longValue);
		bytes = LCSProcessor.buildEncoder()
				.encode(uint8)
				.build();
		assertEquals(UInt8.BYTE_LENGTH,bytes.length);
		byte[] byteValue3 = {0x20};
		assertArrayEquals(byteValue3,bytes);
		decoder = LCSProcessor.buildDecoder(bytes);
		result = decoder.decodeUInt8();
		assertEquals(uint8,result);
		assertEquals(longValue,result.getAsLong());
		//
		longValue =UInt8.MAX_VALUE.longValue();
		uint8 = new UInt8(longValue);
		bytes = LCSProcessor.buildEncoder()
				.encode(uint8)
				.build();
		assertEquals(UInt8.BYTE_LENGTH,bytes.length);
		byte[] byteValue4 = {(byte)0xff};
		assertArrayEquals(byteValue4,bytes);
		decoder = LCSProcessor.buildDecoder(bytes);
		result = decoder.decodeUInt8();
		assertEquals(uint8,result);
		assertEquals(longValue,result.getAsLong());
	}
	
	@Test
	public void test006UInt16LCSEncodingDecoding() {
		Long longValue = 0L;
		UInt16 uint16 = new UInt16(longValue);
		byte[] bytes = LCSProcessor.buildEncoder()
				.encode(uint16)
				.build();
		assertEquals(UInt16.BYTE_LENGTH,bytes.length);
		byte[] byteValue = {0x00,0x00};
		assertArrayEquals(byteValue,bytes);
		LCSProcessor decoder = LCSProcessor.buildDecoder(bytes);
		UInt16 result = decoder.decodeUInt16();
		assertEquals(uint16,result);
		assertEquals(longValue,result.getAsLong());
		//
		longValue =1L;
		uint16 = new UInt16(longValue);
		bytes = LCSProcessor.buildEncoder()
				.encode(uint16)
				.build();
		assertEquals(UInt16.BYTE_LENGTH,bytes.length);
		byte[] byteValue2 = {0x01,0x00};
		assertArrayEquals(byteValue2,bytes);
		decoder = LCSProcessor.buildDecoder(bytes);
		result = decoder.decodeUInt16();
		assertEquals(uint16,result);
		assertEquals(longValue,result.getAsLong());
		//
		longValue =32L;
		uint16 = new UInt16(longValue);
		bytes = LCSProcessor.buildEncoder()
				.encode(uint16)
				.build();
		assertEquals(UInt16.BYTE_LENGTH,bytes.length);
		byte[] byteValue3 = {0x20,0x00};
		assertArrayEquals(byteValue3,bytes);
		decoder = LCSProcessor.buildDecoder(bytes);
		result = decoder.decodeUInt16();
		assertEquals(uint16,result);
		assertEquals(longValue,result.getAsLong());
		//
		longValue =UInt8.MAX_VALUE.longValue();
		uint16 = new UInt16(longValue);
		bytes = LCSProcessor.buildEncoder()
				.encode(uint16)
				.build();
		assertEquals(UInt16.BYTE_LENGTH,bytes.length);
		byte[] byteValue4 = {(byte)0xff,0x00};
		assertArrayEquals(byteValue4,bytes);
		decoder = LCSProcessor.buildDecoder(bytes);
		result = decoder.decodeUInt16();
		assertEquals(uint16,result);
		assertEquals(longValue,result.getAsLong());
		//
		longValue =4660L;
		uint16 = new UInt16(longValue);
		bytes = LCSProcessor.buildEncoder()
				.encode(uint16)
				.build();
		assertEquals(UInt16.BYTE_LENGTH,bytes.length);
		byte[] byteValue5 = {(byte)0x34,(byte)0x12};
		assertArrayEquals(byteValue5,bytes);
		decoder = LCSProcessor.buildDecoder(bytes);
		result = decoder.decodeUInt16();
		assertEquals(uint16,result);
		assertEquals(longValue,result.getAsLong());
		//
		longValue =UInt16.MAX_VALUE.longValue();
		uint16 = new UInt16(longValue);
		bytes = LCSProcessor.buildEncoder()
				.encode(uint16)
				.build();
		assertEquals(UInt16.BYTE_LENGTH,bytes.length);
		byte[] byteValue6 = {(byte)0xff,(byte)0xff};
		assertArrayEquals(byteValue6,bytes);
		decoder = LCSProcessor.buildDecoder(bytes);
		result = decoder.decodeUInt16();
		assertEquals(uint16,result);
		assertEquals(longValue,result.getAsLong());
	}
	
	@Test
	public void test007UInt32LCSEncodingDecoding() {
		Long longValue = 0L;
		UInt32 uint32 = new UInt32(longValue);
		byte[] bytes = LCSProcessor.buildEncoder()
				.encode(uint32)
				.build();
		assertEquals(UInt32.BYTE_LENGTH,bytes.length);
		byte[] byteValue = {0x00,0x00,0x00,0x00};
		assertArrayEquals(byteValue,bytes);
		LCSProcessor decoder = LCSProcessor.buildDecoder(bytes);
		UInt32 result = decoder.decodeUInt32();
		assertEquals(uint32,result);
		assertEquals(longValue,result.getAsLong());
		//
		longValue =1L;
		uint32 = new UInt32(longValue);
		bytes = LCSProcessor.buildEncoder()
				.encode(uint32)
				.build();
		assertEquals(UInt32.BYTE_LENGTH,bytes.length);
		byte[] byteValue2 = {0x01,0x00,0x00,0x00};
		assertArrayEquals(byteValue2,bytes);
		decoder = LCSProcessor.buildDecoder(bytes);
		result = decoder.decodeUInt32();
		assertEquals(uint32,result);
		assertEquals(longValue,result.getAsLong());
		//
		longValue =32L;
		uint32 = new UInt32(longValue);
		bytes = LCSProcessor.buildEncoder()
				.encode(uint32)
				.build();
		assertEquals(UInt32.BYTE_LENGTH,bytes.length);
		byte[] byteValue3 = {0x20,0x00,0x00,0x00};
		assertArrayEquals(byteValue3,bytes);
		decoder = LCSProcessor.buildDecoder(bytes);
		result = decoder.decodeUInt32();
		assertEquals(uint32,result);
		assertEquals(longValue,result.getAsLong());
		//
		longValue =UInt8.MAX_VALUE.longValue();
		uint32 = new UInt32(longValue);
		bytes = LCSProcessor.buildEncoder()
				.encode(uint32)
				.build();
		assertEquals(UInt32.BYTE_LENGTH,bytes.length);
		byte[] byteValue4 = {(byte)0xff,0x00,0x00,0x00};
		assertArrayEquals(byteValue4,bytes);
		decoder = LCSProcessor.buildDecoder(bytes);
		result = decoder.decodeUInt32();
		assertEquals(uint32,result);
		assertEquals(longValue,result.getAsLong());
		//
		longValue =4660L;
		uint32 = new UInt32(longValue);
		bytes = LCSProcessor.buildEncoder()
				.encode(uint32)
				.build();
		assertEquals(UInt32.BYTE_LENGTH,bytes.length);
		byte[] byteValue5 = {(byte)0x34,(byte)0x12,0x00,0x00};
		assertArrayEquals(byteValue5,bytes);
		decoder = LCSProcessor.buildDecoder(bytes);
		result = decoder.decodeUInt32();
		assertEquals(uint32,result);
		assertEquals(longValue,result.getAsLong());
		//
		longValue =UInt16.MAX_VALUE.longValue();
		uint32 = new UInt32(longValue);
		bytes = LCSProcessor.buildEncoder()
				.encode(uint32)
				.build();
		assertEquals(UInt32.BYTE_LENGTH,bytes.length);
		byte[] byteValue6 = {(byte)0xff,(byte)0xff,0x00,0x00};
		assertArrayEquals(byteValue6,bytes);
		decoder = LCSProcessor.buildDecoder(bytes);
		result = decoder.decodeUInt32();
		assertEquals(uint32,result);
		assertEquals(longValue,result.getAsLong());
		//
		longValue =231800522L;
		uint32 = new UInt32(longValue);
		bytes = LCSProcessor.buildEncoder()
				.encode(uint32)
				.build();
		assertEquals(UInt32.BYTE_LENGTH,bytes.length);
		byte[] byteValue7 = {(byte)0xca,(byte)0xfe,(byte)0xd0,(byte)0x0d};
		assertArrayEquals(byteValue7,bytes);
		decoder = LCSProcessor.buildDecoder(bytes);
		result = decoder.decodeUInt32();
		assertEquals(uint32,result);
		assertEquals(longValue,result.getAsLong());
		//
		longValue =305419896L;
		uint32 = new UInt32(longValue);
		bytes = LCSProcessor.buildEncoder()
				.encode(uint32)
				.build();
		assertEquals(UInt32.BYTE_LENGTH,bytes.length);
		byte[] byteValue8 = {(byte)0x78,(byte)0x56,(byte)0x34,(byte)0x12};
		assertArrayEquals(byteValue8,bytes);
		decoder = LCSProcessor.buildDecoder(bytes);
		result = decoder.decodeUInt32();
		assertEquals(uint32,result);
		assertEquals(longValue,result.getAsLong());
		//
		longValue = UInt32.MAX_VALUE.longValue();
		uint32 = new UInt32(longValue);
		bytes = LCSProcessor.buildEncoder()
				.encode(uint32)
				.build();
		assertEquals(UInt32.BYTE_LENGTH,bytes.length);
		byte[] byteValue9 = {(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff};
		assertArrayEquals(byteValue9,bytes);
		decoder = LCSProcessor.buildDecoder(bytes);
		result = decoder.decodeUInt32();
		assertEquals(uint32,result);
		assertEquals(longValue,result.getAsLong());
	}
	
	@Test
	public void test008UInt64LCSEncodingDecoding() {
		Long longValue = 0L;
		UInt64 uint64 = new UInt64(longValue);
		byte[] bytes = LCSProcessor.buildEncoder()
				.encode(uint64)
				.build();
		assertEquals(UInt64.BYTE_LENGTH,bytes.length);
		byte[] byteValue = {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
		assertArrayEquals(byteValue,bytes);
		LCSProcessor decoder = LCSProcessor.buildDecoder(bytes);
		UInt64 result = decoder.decodeUInt64();
		assertEquals(uint64,result);
		assertEquals(longValue,result.getAsLong());
		//
		longValue =1L;
		uint64 = new UInt64(longValue);
		bytes = LCSProcessor.buildEncoder()
				.encode(uint64)
				.build();
		assertEquals(UInt64.BYTE_LENGTH,bytes.length);
		byte[] byteValue2 = {0x01,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
		assertArrayEquals(byteValue2,bytes);
		decoder = LCSProcessor.buildDecoder(bytes);
		result = decoder.decodeUInt64();
		assertEquals(uint64,result);
		assertEquals(longValue,result.getAsLong());
		//
		longValue =32L;
		uint64 = new UInt64(longValue);
		bytes = LCSProcessor.buildEncoder()
				.encode(uint64)
				.build();
		assertEquals(UInt64.BYTE_LENGTH,bytes.length);
		byte[] byteValue3 = {0x20,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
		assertArrayEquals(byteValue3,bytes);
		decoder = LCSProcessor.buildDecoder(bytes);
		result = decoder.decodeUInt64();
		assertEquals(uint64,result);
		assertEquals(longValue,result.getAsLong());
		//
		longValue =UInt8.MAX_VALUE.longValue();
		uint64 = new UInt64(longValue);
		bytes = LCSProcessor.buildEncoder()
				.encode(uint64)
				.build();
		assertEquals(UInt64.BYTE_LENGTH,bytes.length);
		byte[] byteValue4 = {(byte)0xff,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
		assertArrayEquals(byteValue4,bytes);
		decoder = LCSProcessor.buildDecoder(bytes);
		result = decoder.decodeUInt64();
		assertEquals(uint64,result);
		assertEquals(longValue,result.getAsLong());
		//
		longValue =4660L;
		uint64 = new UInt64(longValue);
		bytes = LCSProcessor.buildEncoder()
				.encode(uint64)
				.build();
		assertEquals(UInt64.BYTE_LENGTH,bytes.length);
		byte[] byteValue5 = {(byte)0x34,(byte)0x12,0x00,0x00,0x00,0x00,0x00,0x00};
		assertArrayEquals(byteValue5,bytes);
		decoder = LCSProcessor.buildDecoder(bytes);
		result = decoder.decodeUInt64();
		assertEquals(uint64,result);
		assertEquals(longValue,result.getAsLong());
		//
		longValue =UInt16.MAX_VALUE.longValue();
		uint64 = new UInt64(longValue);
		bytes = LCSProcessor.buildEncoder()
				.encode(uint64)
				.build();
		assertEquals(UInt64.BYTE_LENGTH,bytes.length);
		byte[] byteValue6 = {(byte)0xff,(byte)0xff,0x00,0x00,0x00,0x00,0x00,0x00};
		assertArrayEquals(byteValue6,bytes);
		decoder = LCSProcessor.buildDecoder(bytes);
		result = decoder.decodeUInt64();
		assertEquals(uint64,result);
		assertEquals(longValue,result.getAsLong());
		//
		longValue =231800522L;
		uint64 = new UInt64(longValue);
		bytes = LCSProcessor.buildEncoder()
				.encode(uint64)
				.build();
		assertEquals(UInt64.BYTE_LENGTH,bytes.length);
		byte[] byteValue7 = {(byte)0xca,(byte)0xfe,(byte)0xd0,(byte)0x0d,0x00,0x00,0x00,0x00};
		assertArrayEquals(byteValue7,bytes);
		decoder = LCSProcessor.buildDecoder(bytes);
		result = decoder.decodeUInt64();
		assertEquals(uint64,result);
		assertEquals(longValue,result.getAsLong());
		//
		longValue =305419896L;
		uint64 = new UInt64(longValue);
		bytes = LCSProcessor.buildEncoder()
				.encode(uint64)
				.build();
		assertEquals(UInt64.BYTE_LENGTH,bytes.length);
		byte[] byteValue8 = {(byte)0x78,(byte)0x56,(byte)0x34,(byte)0x12,0x00,0x00,0x00,0x00};
		assertArrayEquals(byteValue8,bytes);
		decoder = LCSProcessor.buildDecoder(bytes);
		result = decoder.decodeUInt64();
		assertEquals(uint64,result);
		assertEquals(longValue,result.getAsLong());
		//
		longValue = UInt32.MAX_VALUE.longValue();
		uint64 = new UInt64(longValue);
		bytes = LCSProcessor.buildEncoder()
				.encode(uint64)
				.build();
		assertEquals(UInt64.BYTE_LENGTH,bytes.length);
		byte[] byteValue9 = {(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,0x00,0x00,0x00,0x00};
		assertArrayEquals(byteValue9,bytes);
		decoder = LCSProcessor.buildDecoder(bytes);
		result = decoder.decodeUInt64();
		assertEquals(uint64,result);
		assertEquals(longValue,result.getAsLong());
		//
		longValue = 1311768467750121216L;
		uint64 = new UInt64(longValue);
		bytes = LCSProcessor.buildEncoder()
				.encode(uint64)
				.build();
		assertEquals(UInt64.BYTE_LENGTH,bytes.length);
		byte[] byteValue10 = {(byte)0x00,(byte)0xef,(byte)0xcd,(byte)0xab,(byte)0x78,(byte)0x56,(byte)0x34,(byte)0x12};
		assertArrayEquals(byteValue10,bytes);
		decoder = LCSProcessor.buildDecoder(bytes);
		result = decoder.decodeUInt64();
		assertEquals(uint64,result);
		assertEquals(longValue,result.getAsLong());
		//special case with UInt64.MAX_VALUE
		uint64 = new UInt64(UInt64.MAX_VALUE);
		bytes = LCSProcessor.buildEncoder()
				.encode(uint64)
				.build();
		assertEquals(UInt64.BYTE_LENGTH,bytes.length);
		byte[] byteValue11 = {(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff};
		assertArrayEquals(byteValue11,bytes);
		decoder = LCSProcessor.buildDecoder(bytes);
		result = decoder.decodeUInt64();
		assertEquals(uint64,result);
	}
	
	@Test
	public void test009AccountAddressLCSEncodingDecoding() {
		String hex = "CA820BF9305EB97D0D784F71B3955457FBF6911F5300CEAA5D7E8621529EAE19";
		AccountAddress accountAddress = new AccountAddress(hex);
		byte[] bytes = LCSProcessor.buildEncoder()
			.encode(accountAddress)
			.build();
		assertEquals(UInt32.BYTE_LENGTH + AccountAddress.BYTE_LENGTH,bytes.length);
		LCSProcessor decoder = LCSProcessor.buildDecoder(bytes);
		AccountAddress result = decoder.decodeAccountAddress();
		assertEquals(accountAddress,result);
		//
		hex = "2C25991785343B23AE073A50E5FD809A2CD867526B3C1DB2B0BF5D1924C693ED";
		accountAddress = new AccountAddress(hex);
		bytes = LCSProcessor.buildEncoder()
			.encode(accountAddress)
			.build();
		assertEquals(UInt32.BYTE_LENGTH + AccountAddress.BYTE_LENGTH,bytes.length);
		decoder = LCSProcessor.buildDecoder(bytes);
		result = decoder.decodeAccountAddress();
		assertEquals(accountAddress,result);
		//
		hex = "2C25991785343B23AE073A50E5FD809A2CD867526B3C1DB2B0BF5D1924C693ED";
		accountAddress = new AccountAddress(hex);
		bytes = LCSProcessor.buildEncoder()
				.encode(new UInt32(AccountAddress.BYTE_LENGTH + 10))
				.encode(accountAddress.getBytes(),false) //without length
				.build();
		assertEquals(UInt32.BYTE_LENGTH + AccountAddress.BYTE_LENGTH,bytes.length);
		decoder = LCSProcessor.buildDecoder(bytes);
		assertFalse(ChaingrokLog.hasLogs());
		result = decoder.decodeAccountAddress();
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,ChaingrokLog.getLogs().get(0).getType());
		ChaingrokLog.purgeLogs();
	}
	
}
