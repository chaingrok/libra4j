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

import com.chaingrok.libra4j.misc.LCSProcessor;
import com.chaingrok.libra4j.misc.Libra4jLog;
import com.chaingrok.libra4j.misc.Libra4jLog.Type;
import com.chaingrok.libra4j.misc.Utils;
import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.AccountAddress;
import com.chaingrok.libra4j.types.UInt16;
import com.chaingrok.libra4j.types.UInt32;
import com.chaingrok.libra4j.types.UInt64;
import com.chaingrok.libra4j.types.UInt8;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestLCSProcessor extends TestClass {
	
	@Test
	public void tes001BooleanLCSEncodingDecoding() {
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
		assertFalse(Libra4jLog.hasLogs());
		decoder = LCSProcessor.buildDecoder(bytes3);
		assertNull(decoder.decodeBoolean());
		assertTrue(Libra4jLog.hasLogs());
		Libra4jLog.purgeLogs();
	}
	
	@Test
	public void tes002StringLCSEncodingDecoding() {
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
	public void tes003UIntLCSEncodingDecodingEdgeCases() {
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
	    assertEquals(0,Libra4jLog.getLogs().size());
		decoder.decodeUInt8();
		assertEquals(1,Libra4jLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,Libra4jLog.getLogs().get(0).getType());
		Libra4jLog.purgeLogs();
		//
		decoder = LCSProcessor.buildDecoder(Utils.hexStringToByteArray("00"));
		assertEquals(0,Libra4jLog.getLogs().size());
		decoder.decodeUInt16();
		assertEquals(1,Libra4jLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,Libra4jLog.getLogs().get(0).getType());
		Libra4jLog.purgeLogs();
		//
		decoder = LCSProcessor.buildDecoder(Utils.hexStringToByteArray("001122"));
		assertEquals(0,Libra4jLog.getLogs().size());
		decoder.decodeUInt32();
		assertEquals(1,Libra4jLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,Libra4jLog.getLogs().get(0).getType());
		Libra4jLog.purgeLogs();
		//
		decoder = LCSProcessor.buildDecoder(Utils.hexStringToByteArray("00112233445566"));
		assertEquals(0,Libra4jLog.getLogs().size());
		decoder.decodeUInt64();
		assertEquals(1,Libra4jLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,Libra4jLog.getLogs().get(0).getType());
		Libra4jLog.purgeLogs();
	}
	
	@Test
	public void tes004UInt8LCSEncodingDecoding() {
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
	public void tes005UInt16LCSEncodingDecoding() {
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
	public void tes006UInt32LCSEncodingDecoding() {
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
	public void tes007UInt64LCSEncodingDecoding() {
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
	public void tes00AccountAddressLCSEncodingDecoding() {
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
				.encode(accountAddress.getBytes())
				.build();
		assertEquals(UInt32.BYTE_LENGTH + AccountAddress.BYTE_LENGTH,bytes.length);
		decoder = LCSProcessor.buildDecoder(bytes);
		assertFalse(Libra4jLog.hasLogs());
		result = decoder.decodeAccountAddress();
		assertEquals(1,Libra4jLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,Libra4jLog.getLogs().get(0).getType());
		Libra4jLog.purgeLogs();
	}
	

	public void testVectors() {
		
		String s5 = "{\n" + 
				"  code: \"move\",\n" + 
				"  args: [{STRING: CAFE D00D}, {STRING: cafe d00d}],\n" + 
				"  modules: [[CA][FED0][0D]],\n" + 
				"}" 
				+ "[040000006D6F766502000000020000000900000043414645204430304402000000090000006361666520643030640300000001000000CA02000000FED0010000000D]";
		String s6 = "{\n" + 
				"  address: 9a1ad09742d1ffc62e659e9a7797808b206f956f131d07509449c01ad8220ad4,\n" + 
				"  path: 01217da6c6b3e19f1825cfb2676daecce3bf3de03cf26647c78df00b371b25cc97\n" + 
				"}" 
				+ "" + "";

		
	}
	
}
