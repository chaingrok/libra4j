package com.chaingrok.libra4j.test.misc;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.misc.LCSEncoder;
import com.chaingrok.libra4j.misc.Libra4jLog;
import com.chaingrok.libra4j.misc.Libra4jLog.Type;
import com.chaingrok.libra4j.misc.Utils;
import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.UInt16;
import com.chaingrok.libra4j.types.UInt32;
import com.chaingrok.libra4j.types.UInt64;
import com.chaingrok.libra4j.types.UInt8;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestLCSEncoder extends TestClass {
	
	@Test
	public void tes001BooleanLCSEncondingDecoding() {
		boolean b = false;
		ByteArrayOutputStream bos = LCSEncoder.encodeBoolean(b);
		byte[] bytes = bos.toByteArray();
		assertEquals(1,bytes.length);
		byte[] value = {0x00};
		assertArrayEquals(value,bytes);
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		assertEquals(b,LCSEncoder.decodeBoolean(bis));
		//
		boolean b2 = true;
		ByteArrayOutputStream bos2 = LCSEncoder.encodeBoolean(b2);
		byte[] bytes2 = bos2.toByteArray();
		assertEquals(1,bytes.length);
		byte[] value2 = {0x01};
		assertArrayEquals(value2,bytes2);
		ByteArrayInputStream bis2 = new ByteArrayInputStream(bytes2);
		assertEquals(b2,LCSEncoder.decodeBoolean(bis2));
		//
		byte[] bytes3 = {0x02};
		ByteArrayInputStream bis3 = new ByteArrayInputStream(bytes3);
		assertFalse(Libra4jLog.hasLogs());
		assertNull(LCSEncoder.decodeBoolean(bis3));
		assertTrue(Libra4jLog.hasLogs());
		Libra4jLog.purgeLogs();
	}
	
	@Test
	public void tes002StringLCSEncondingDecoding() {
		String string = null;
		ByteArrayOutputStream bos = LCSEncoder.encodeString(string);
		assertNotNull(bos);
		assertEquals(0,bos.size());
		//string only
		string = "Hello, world";
		bos = LCSEncoder.encodeString(string);
		assertArrayEquals(string.getBytes(),bos.toByteArray());
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		String result = LCSEncoder.decodeString(bis,string.length());
		assertEquals(string,result);
		// string + length
		string = "Hello, world";
		UInt32 length = new UInt32(string.getBytes().length);
		bos = LCSEncoder.encodeUInt32(length);
		LCSEncoder.encodeString(string,bos);
		byte[] bytes = bos.toByteArray();
		assertEquals(string.getBytes().length + UInt32.BYTE_LENGTH,bytes.length);
		bis = new ByteArrayInputStream(bos.toByteArray());
		UInt32 length2 = LCSEncoder.decodeUint32(bis);
		assertEquals(length,length2);
		result = LCSEncoder.decodeString(bis,length2);
		assertEquals(string,result);
		//UTF string
		string = "ሰማይ አይታረስ ንጉሥ አይከሰስ።";
		assertEquals(20,string.length());
		assertEquals(54,string.getBytes().length);
		bos = LCSEncoder.encodeString(string);
		assertEquals(54,bos.size());
		assertArrayEquals(string.getBytes(),bos.toByteArray());
		assertEquals(string,new String(bos.toByteArray()));
		bis = new ByteArrayInputStream(bos.toByteArray());
		result = LCSEncoder.decodeString(bis,string.getBytes().length);
		assertEquals(string,result);
		// string UTF-8 + length
		string = "ሰማይ አይታረስ ንጉሥ አይከሰስ።";
		length = new UInt32(string.getBytes().length);
		bos = LCSEncoder.encodeUInt32(length);
		LCSEncoder.encodeString(string,bos);
		bytes = bos.toByteArray();
		assertEquals(string.getBytes().length + UInt32.BYTE_LENGTH,bytes.length);
		byte[] testVector = Utils.hexStringToByteArray("36000000E188B0E1889BE18BAD20E18AA0E18BADE189B3E188A8E188B520E18A95E18C89E188A520E18AA0E18BADE18AA8E188B0E188B5E18DA2");
		assertArrayEquals(testVector,bytes);
		bis = new ByteArrayInputStream(bos.toByteArray());
		length2 = LCSEncoder.decodeUint32(bis);
		assertEquals(length,length2);
		result = LCSEncoder.decodeString(bis,length2);
		assertEquals(string,result);
	}
	
	@Test
	public void tes003UIntLCSEncondingDecodingEdgeCases() {
		ByteArrayOutputStream bos = LCSEncoder.encodeUInt8(null);
		assertNotNull(bos);
		assertEquals(0,bos.size());
		//
		bos = LCSEncoder.encodeUInt16(null);
		assertNotNull(bos);
		assertEquals(0,bos.size());
		//
		bos = LCSEncoder.encodeUInt32(null);
		assertNotNull(bos);
		assertEquals(0,bos.size());
		//
		bos = LCSEncoder.encodeUInt64(null);
		assertNotNull(bos);
		assertEquals(0,bos.size());
		//
		assertNull(LCSEncoder.decodeUint8(null));
		assertNull(LCSEncoder.decodeUint16(null));
		assertNull(LCSEncoder.decodeUint32(null));
		assertNull(LCSEncoder.decodeUint64(null));
		//
	    ByteArrayInputStream bis = new ByteArrayInputStream(Utils.hexStringToByteArray("00"));
	    assertNotEquals(-1,bis.read());
	    assertEquals(0,bis.available());
		LCSEncoder.decodeUint8(bis);
		assertEquals(1,Libra4jLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,Libra4jLog.getLogs().get(0).getType());
		Libra4jLog.purgeLogs();
		//
		bis = new ByteArrayInputStream(Utils.hexStringToByteArray("00"));
		LCSEncoder.decodeUint16(bis);
		assertEquals(1,Libra4jLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,Libra4jLog.getLogs().get(0).getType());
		Libra4jLog.purgeLogs();
		//
		bis = new ByteArrayInputStream(Utils.hexStringToByteArray("001122"));
		LCSEncoder.decodeUint32(bis);
		assertEquals(1,Libra4jLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,Libra4jLog.getLogs().get(0).getType());
		Libra4jLog.purgeLogs();
		//
		bis = new ByteArrayInputStream(Utils.hexStringToByteArray("00112233445566"));
		LCSEncoder.decodeUint64(bis);
		assertEquals(1,Libra4jLog.getLogs().size());
		assertEquals(Type.INVALID_LENGTH,Libra4jLog.getLogs().get(0).getType());
		Libra4jLog.purgeLogs();
	}
	
	@Test
	public void tes004UInt8LCSEncondingDecoding() {
		Long longValue = 0L;
		UInt8 uint8 = new UInt8(longValue);
		ByteArrayOutputStream bos = LCSEncoder.encodeUInt8(uint8);
		byte[] bytes = bos.toByteArray();
		assertEquals(UInt8.BYTE_LENGTH,bytes.length);
		byte[] byteValue = {0x00};
		assertArrayEquals(byteValue,bytes);
		ByteArrayInputStream bis = new ByteArrayInputStream(byteValue);
		assertEquals(uint8,LCSEncoder.decodeUint8(bis));
		assertEquals(longValue,uint8.getAsLong());
		//
		longValue =1L;
		uint8 = new UInt8(longValue);
		bos = LCSEncoder.encodeUInt8(uint8);
		bytes = bos.toByteArray();
		assertEquals(UInt8.BYTE_LENGTH,bytes.length);
		byte[] byteValue2 = {0x01};
		assertArrayEquals(byteValue2,bytes);
		bis = new ByteArrayInputStream(byteValue2);
		assertEquals(uint8,LCSEncoder.decodeUint8(bis));
		assertEquals(longValue,uint8.getAsLong());
		//
		longValue =32L;
		uint8 = new UInt8(longValue);
		bos = LCSEncoder.encodeUInt8(uint8);
		bytes = bos.toByteArray();
		assertEquals(UInt8.BYTE_LENGTH,bytes.length);
		byte[] byteValue3 = {0x20};
		assertArrayEquals(byteValue3,bytes);
		bis = new ByteArrayInputStream(byteValue3);
		assertEquals(uint8,LCSEncoder.decodeUint8(bis));
		assertEquals(longValue,uint8.getAsLong());
		//
		longValue =UInt8.MAX_VALUE.longValue();
		uint8 = new UInt8(longValue);
		bos = LCSEncoder.encodeUInt8(uint8);
		bytes = bos.toByteArray();
		assertEquals(UInt8.BYTE_LENGTH,bytes.length);
		byte[] byteValue4 = {(byte)0xff};
		assertArrayEquals(byteValue4,bytes);
		bis = new ByteArrayInputStream(byteValue4);
		assertEquals(uint8,LCSEncoder.decodeUint8(bis));
		assertEquals(longValue,uint8.getAsLong());
	}
	
	@Test
	public void tes005UInt16LCSEncondingDecoding() {
		Long longValue = 0L;
		UInt16 uint16 = new UInt16(longValue);
		ByteArrayOutputStream bos = LCSEncoder.encodeUInt16(uint16);
		byte[] bytes = bos.toByteArray();
		assertEquals(UInt16.BYTE_LENGTH,bytes.length);
		byte[] byteValue = {0x00,0x00};
		assertArrayEquals(byteValue,bytes);
		ByteArrayInputStream bis = new ByteArrayInputStream(byteValue);
		assertEquals(uint16,LCSEncoder.decodeUint16(bis));
		assertEquals(longValue,uint16.getAsLong());
		//
		longValue =1L;
		uint16 = new UInt16(longValue);
		bos = LCSEncoder.encodeUInt16(uint16);
		bytes = bos.toByteArray();
		assertEquals(UInt16.BYTE_LENGTH,bytes.length);
		byte[] byteValue2 = {0x01,0x00};
		assertArrayEquals(byteValue2,bytes);
		bis = new ByteArrayInputStream(byteValue2);
		assertEquals(uint16,LCSEncoder.decodeUint16(bis));
		assertEquals(longValue,uint16.getAsLong());
		//
		longValue =32L;
		uint16 = new UInt16(longValue);
		bos = LCSEncoder.encodeUInt16(uint16);
		bytes = bos.toByteArray();
		assertEquals(UInt16.BYTE_LENGTH,bytes.length);
		byte[] byteValue3 = {0x20,0x00};
		assertArrayEquals(byteValue3,bytes);
		bis = new ByteArrayInputStream(byteValue3);
		assertEquals(uint16,LCSEncoder.decodeUint16(bis));
		assertEquals(longValue,uint16.getAsLong());
		//
		longValue =UInt8.MAX_VALUE.longValue();
		uint16 = new UInt16(longValue);
		bos = LCSEncoder.encodeUInt16(uint16);
		bytes = bos.toByteArray();
		assertEquals(UInt16.BYTE_LENGTH,bytes.length);
		byte[] byteValue4 = {(byte)0xff,0x00};
		assertArrayEquals(byteValue4,bytes);
		bis = new ByteArrayInputStream(byteValue4);
		assertEquals(uint16,LCSEncoder.decodeUint16(bis));
		assertEquals(longValue,uint16.getAsLong());
		//
		longValue =4660L;
		uint16 = new UInt16(longValue);
		bos = LCSEncoder.encodeUInt16(uint16);
		bytes = bos.toByteArray();
		assertEquals(UInt16.BYTE_LENGTH,bytes.length);
		byte[] byteValue5 = {(byte)0x34,(byte)0x12};
		assertArrayEquals(byteValue5,bytes);
		bis = new ByteArrayInputStream(byteValue5);
		assertEquals(uint16,LCSEncoder.decodeUint16(bis));
		assertEquals(longValue,uint16.getAsLong());
		//
		longValue =UInt16.MAX_VALUE.longValue();
		uint16 = new UInt16(longValue);
		bos = LCSEncoder.encodeUInt16(uint16);
		bytes = bos.toByteArray();
		assertEquals(UInt16.BYTE_LENGTH,bytes.length);
		byte[] byteValue6 = {(byte)0xff,(byte)0xff};
		assertArrayEquals(byteValue6,bytes);
		bis = new ByteArrayInputStream(byteValue6);
		assertEquals(uint16,LCSEncoder.decodeUint16(bis));
		assertEquals(longValue,uint16.getAsLong());
	}
	
	@Test
	public void tes006UInt32LCSEncondingDecoding() {
		Long longValue = 0L;
		UInt32 uint32 = new UInt32(longValue);
		ByteArrayOutputStream bos = LCSEncoder.encodeUInt32(uint32);
		byte[] bytes = bos.toByteArray();
		assertEquals(UInt32.BYTE_LENGTH,bytes.length);
		byte[] byteValue = {0x00,0x00,0x00,0x00};
		assertArrayEquals(byteValue,bytes);
		ByteArrayInputStream bis = new ByteArrayInputStream(byteValue);
		assertEquals(uint32,LCSEncoder.decodeUint32(bis));
		assertEquals(longValue,uint32.getAsLong());
		//
		longValue =1L;
		uint32 = new UInt32(longValue);
		bos = LCSEncoder.encodeUInt32(uint32);
		bytes = bos.toByteArray();
		assertEquals(UInt32.BYTE_LENGTH,bytes.length);
		byte[] byteValue2 = {0x01,0x00,0x00,0x00};
		assertArrayEquals(byteValue2,bytes);
		bis = new ByteArrayInputStream(byteValue2);
		assertEquals(uint32,LCSEncoder.decodeUint32(bis));
		assertEquals(longValue,uint32.getAsLong());
		//
		longValue =32L;
		uint32 = new UInt32(longValue);
		bos = LCSEncoder.encodeUInt32(uint32);
		bytes = bos.toByteArray();
		assertEquals(UInt32.BYTE_LENGTH,bytes.length);
		byte[] byteValue3 = {0x20,0x00,0x00,0x00};
		assertArrayEquals(byteValue3,bytes);
		bis = new ByteArrayInputStream(byteValue3);
		assertEquals(uint32,LCSEncoder.decodeUint32(bis));
		assertEquals(longValue,uint32.getAsLong());
		//
		longValue =UInt8.MAX_VALUE.longValue();
		uint32 = new UInt32(longValue);
		bos = LCSEncoder.encodeUInt32(uint32);
		bytes = bos.toByteArray();
		assertEquals(UInt32.BYTE_LENGTH,bytes.length);
		byte[] byteValue4 = {(byte)0xff,0x00,0x00,0x00};
		assertArrayEquals(byteValue4,bytes);
		bis = new ByteArrayInputStream(byteValue4);
		assertEquals(uint32,LCSEncoder.decodeUint32(bis));
		assertEquals(longValue,uint32.getAsLong());
		//
		longValue =4660L;
		uint32 = new UInt32(longValue);
		bos = LCSEncoder.encodeUInt32(uint32);
		bytes = bos.toByteArray();
		assertEquals(UInt32.BYTE_LENGTH,bytes.length);
		byte[] byteValue5 = {(byte)0x34,(byte)0x12,0x00,0x00};
		assertArrayEquals(byteValue5,bytes);
		bis = new ByteArrayInputStream(byteValue5);
		assertEquals(uint32,LCSEncoder.decodeUint32(bis));
		assertEquals(longValue,uint32.getAsLong());
		//
		longValue =UInt16.MAX_VALUE.longValue();
		uint32 = new UInt32(longValue);
		bos = LCSEncoder.encodeUInt32(uint32);
		bytes = bos.toByteArray();
		assertEquals(UInt32.BYTE_LENGTH,bytes.length);
		byte[] byteValue6 = {(byte)0xff,(byte)0xff,0x00,0x00};
		assertArrayEquals(byteValue6,bytes);
		bis = new ByteArrayInputStream(byteValue6);
		assertEquals(uint32,LCSEncoder.decodeUint32(bis));
		assertEquals(longValue,uint32.getAsLong());
		//
		longValue =231800522L;
		uint32 = new UInt32(longValue);
		bos = LCSEncoder.encodeUInt32(uint32);
		bytes = bos.toByteArray();
		assertEquals(UInt32.BYTE_LENGTH,bytes.length);
		byte[] byteValue7 = {(byte)0xca,(byte)0xfe,(byte)0xd0,(byte)0x0d};
		assertArrayEquals(byteValue7,bytes);
		bis = new ByteArrayInputStream(byteValue7);
		assertEquals(uint32,LCSEncoder.decodeUint32(bis));
		assertEquals(longValue,uint32.getAsLong());
		//
		longValue =305419896L;
		uint32 = new UInt32(longValue);
		bos = LCSEncoder.encodeUInt32(uint32);
		bytes = bos.toByteArray();
		assertEquals(UInt32.BYTE_LENGTH,bytes.length);
		byte[] byteValue8 = {(byte)0x78,(byte)0x56,(byte)0x34,(byte)0x12};
		assertArrayEquals(byteValue8,bytes);
		bis = new ByteArrayInputStream(byteValue8);
		assertEquals(uint32,LCSEncoder.decodeUint32(bis));
		assertEquals(longValue,uint32.getAsLong());
		//
		longValue = UInt32.MAX_VALUE.longValue();
		uint32 = new UInt32(longValue);
		bos = LCSEncoder.encodeUInt32(uint32);
		bytes = bos.toByteArray();
		assertEquals(UInt32.BYTE_LENGTH,bytes.length);
		byte[] byteValue9 = {(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff};
		assertArrayEquals(byteValue9,bytes);
		bis = new ByteArrayInputStream(byteValue9);
		assertEquals(uint32,LCSEncoder.decodeUint32(bis));
		assertEquals(longValue,uint32.getAsLong());
	}
	
	@Test
	public void tes007UInt64LCSEncondingDecoding() {
		Long longValue = 0L;
		UInt64 uint64 = new UInt64(longValue);
		ByteArrayOutputStream bos = LCSEncoder.encodeUInt64(uint64);
		byte[] bytes = bos.toByteArray();
		assertEquals(UInt64.BYTE_LENGTH,bytes.length);
		byte[] byteValue = {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
		assertArrayEquals(byteValue,bytes);
		ByteArrayInputStream bis = new ByteArrayInputStream(byteValue);
		assertEquals(uint64,LCSEncoder.decodeUint64(bis));
		assertEquals(longValue,uint64.getAsLong());
		//
		longValue =1L;
		uint64 = new UInt64(longValue);
		bos = LCSEncoder.encodeUInt64(uint64);
		bytes = bos.toByteArray();
		assertEquals(UInt64.BYTE_LENGTH,bytes.length);
		byte[] byteValue2 = {0x01,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
		assertArrayEquals(byteValue2,bytes);
		bis = new ByteArrayInputStream(byteValue2);
		assertEquals(uint64,LCSEncoder.decodeUint64(bis));
		assertEquals(longValue,uint64.getAsLong());
		//
		longValue =32L;
		uint64 = new UInt64(longValue);
		bos = LCSEncoder.encodeUInt64(uint64);
		bytes = bos.toByteArray();
		assertEquals(UInt64.BYTE_LENGTH,bytes.length);
		byte[] byteValue3 = {0x20,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
		assertArrayEquals(byteValue3,bytes);
		bis = new ByteArrayInputStream(byteValue3);
		assertEquals(uint64,LCSEncoder.decodeUint64(bis));
		assertEquals(longValue,uint64.getAsLong());
		//
		longValue =UInt8.MAX_VALUE.longValue();
		uint64 = new UInt64(longValue);
		bos = LCSEncoder.encodeUInt64(uint64);
		bytes = bos.toByteArray();
		assertEquals(UInt64.BYTE_LENGTH,bytes.length);
		byte[] byteValue4 = {(byte)0xff,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
		assertArrayEquals(byteValue4,bytes);
		bis = new ByteArrayInputStream(byteValue4);
		assertEquals(uint64,LCSEncoder.decodeUint64(bis));
		assertEquals(longValue,uint64.getAsLong());
		//
		longValue =4660L;
		uint64 = new UInt64(longValue);
		bos = LCSEncoder.encodeUInt64(uint64);
		bytes = bos.toByteArray();
		assertEquals(UInt64.BYTE_LENGTH,bytes.length);
		byte[] byteValue5 = {(byte)0x34,(byte)0x12,0x00,0x00,0x00,0x00,0x00,0x00};
		assertArrayEquals(byteValue5,bytes);
		bis = new ByteArrayInputStream(byteValue5);
		assertEquals(uint64,LCSEncoder.decodeUint64(bis));
		assertEquals(longValue,uint64.getAsLong());
		//
		longValue =UInt16.MAX_VALUE.longValue();
		uint64 = new UInt64(longValue);
		bos = LCSEncoder.encodeUInt64(uint64);
		bytes = bos.toByteArray();
		assertEquals(UInt64.BYTE_LENGTH,bytes.length);
		byte[] byteValue6 = {(byte)0xff,(byte)0xff,0x00,0x00,0x00,0x00,0x00,0x00};
		assertArrayEquals(byteValue6,bytes);
		bis = new ByteArrayInputStream(byteValue6);
		assertEquals(uint64,LCSEncoder.decodeUint64(bis));
		assertEquals(longValue,uint64.getAsLong());
		//
		longValue =231800522L;
		uint64 = new UInt64(longValue);
		bos = LCSEncoder.encodeUInt64(uint64);
		bytes = bos.toByteArray();
		assertEquals(UInt64.BYTE_LENGTH,bytes.length);
		byte[] byteValue7 = {(byte)0xca,(byte)0xfe,(byte)0xd0,(byte)0x0d,0x00,0x00,0x00,0x00};
		assertArrayEquals(byteValue7,bytes);
		bis = new ByteArrayInputStream(byteValue7);
		assertEquals(uint64,LCSEncoder.decodeUint64(bis));
		assertEquals(longValue,uint64.getAsLong());
		//
		longValue =305419896L;
		uint64 = new UInt64(longValue);
		bos = LCSEncoder.encodeUInt64(uint64);
		bytes = bos.toByteArray();
		assertEquals(UInt64.BYTE_LENGTH,bytes.length);
		byte[] byteValue8 = {(byte)0x78,(byte)0x56,(byte)0x34,(byte)0x12,0x00,0x00,0x00,0x00};
		assertArrayEquals(byteValue8,bytes);
		bis = new ByteArrayInputStream(byteValue8);
		assertEquals(uint64,LCSEncoder.decodeUint64(bis));
		assertEquals(longValue,uint64.getAsLong());
		//
		longValue = UInt32.MAX_VALUE.longValue();
		uint64 = new UInt64(longValue);
		bos = LCSEncoder.encodeUInt64(uint64);
		bytes = bos.toByteArray();
		assertEquals(UInt64.BYTE_LENGTH,bytes.length);
		byte[] byteValue9 = {(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,0x00,0x00,0x00,0x00};
		assertArrayEquals(byteValue9,bytes);
		bis = new ByteArrayInputStream(byteValue9);
		assertEquals(uint64,LCSEncoder.decodeUint64(bis));
		assertEquals(longValue,uint64.getAsLong());
		//
		longValue = 1311768467750121216L;
		uint64 = new UInt64(longValue);
		bos = LCSEncoder.encodeUInt64(uint64);
		bytes = bos.toByteArray();
		assertEquals(UInt64.BYTE_LENGTH,bytes.length);
		byte[] byteValue10 = {(byte)0x00,(byte)0xef,(byte)0xcd,(byte)0xab,(byte)0x78,(byte)0x56,(byte)0x34,(byte)0x12};
		assertArrayEquals(byteValue10,bytes);
		bis = new ByteArrayInputStream(byteValue10);
		assertEquals(uint64,LCSEncoder.decodeUint64(bis));
		assertEquals(longValue,uint64.getAsLong());
		//special case with UInt64.MAX_VALUE
		uint64 = new UInt64(UInt64.MAX_VALUE);
		bos = LCSEncoder.encodeUInt64(uint64);
		bytes = bos.toByteArray();
		assertEquals(UInt64.BYTE_LENGTH,bytes.length);
		byte[] byteValue11 = {(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff};
		assertArrayEquals(byteValue11,bytes);
		bis = new ByteArrayInputStream(byteValue11);
		assertEquals(uint64,LCSEncoder.decodeUint64(bis));
		assertEquals(UInt64.MAX_VALUE,uint64.getAsBigInt());
		//special case with UInt64.MAX_VALUE
		uint64 = new UInt64(UInt64.MAX_VALUE);
		bos = LCSEncoder.encodeUInt64(uint64);
		bytes = bos.toByteArray();
		assertEquals(UInt64.BYTE_LENGTH,bytes.length);
		byte[] byteValue13 = {(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff};
		assertArrayEquals(byteValue13,bytes);
		bis = new ByteArrayInputStream(byteValue13);
		assertEquals(uint64,LCSEncoder.decodeUint64(bis));
		assertEquals(UInt64.MAX_VALUE,uint64.getAsBigInt());
	}
	
	
	
	@Test
	public void testVectors() {
		String s1 ="20000000CA820BF9305EB97D0D784F71B3955457FBF6911F5300CEAA5D7E8621529EAE19"; //address + length
		String s3 = "{ADDRESS: 2c25991785343b23ae073a50e5fd809a2cd867526b3c1db2b0bf5d1924c693ed}" + "[01000000200000002C25991785343B23AE073A50E5FD809A2CD867526B3C1DB2B0BF5D1924C693ED]";
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
