package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.lib.ChaingrokLog;
import com.chaingrok.lib.Utils;
import com.chaingrok.libra4j.misc.LCSProcessor;
import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.WriteOp;
import com.chaingrok.libra4j.types.WriteOp.Type;
import com.chaingrok.libra4j.types.WriteSet;
import com.chaingrok.libra4j.types.WriteSetTuple;
import com.chaingrok.libra4j.types.AccessPath;
import com.chaingrok.libra4j.types.AccountAddress;
import com.chaingrok.libra4j.types.Path;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestVWriteSet extends TestClass {
	
	@Test
	public void test001WriteOptype() {
		assertEquals(Type.WRITE,Type.get(Type.WRITE.getOpType()));
		assertEquals(Type.DELETE,Type.get(Type.DELETE.getOpType()));
		assertFalse(ChaingrokLog.hasLogs());
		assertEquals(Type.UNRECOGNIZED,Type.get(Type.UNRECOGNIZED.getOpType()));
		assertTrue(ChaingrokLog.hasLogs());
		ChaingrokLog.purgeLogs();
	}
	
	@Test
	public void test002WriteOpInstanceOk() {
		byte[] bytes = {0x00,0x01};
		WriteOp writeOp = new WriteOp(bytes);
		assertSame(bytes,writeOp.getBytes());
		assertNull(writeOp.getOpType());
		writeOp.setOpType(Type.WRITE);
		assertEquals(Type.WRITE,writeOp.getOpType());
		//
		String string = writeOp.toString();
		assertTrue(string.contains(Type.WRITE + ""));
		assertTrue(string.contains(Utils.byteArrayToHexString(bytes)));
	}
	
	@Test
	public void test003LCSEncoding() {
		LCSProcessor encoder = LCSProcessor.buildEncoder();
		Type writeOpType = Type.DELETE;
		writeOpType.encodeToLCS(encoder);
		byte[] bytes = encoder.build();
		LCSProcessor decoder = LCSProcessor.buildDecoder(bytes);
		Type result = decoder.decodeWriteOpType();
		assertEquals(writeOpType,result);
		//
		encoder = LCSProcessor.buildEncoder();
		writeOpType = Type.WRITE;
		byte[] value = {0x00,0x01,0x02};
		WriteOp writeOp = new WriteOp(value);
		writeOp.setOpType(writeOpType);
		writeOp.encodeToLCS(encoder);
		bytes = encoder.build();
		decoder = LCSProcessor.buildDecoder(bytes);
		WriteOp result2 = decoder.decodeWriteOp();
		assertNotNull(result2);
		assertEquals(Type.WRITE,result2.getOpType());
		assertArrayEquals(value,result2.getBytes());
	}
	
	@Test
	public void test004WriteSetLCSDecoding() { // as per https://github.com/libra/libra/tree/master/common/canonical_serialization
		String testVectorHex = "0200000020000000A71D76FAA2D2D5C3224EC3D41DEB293973564A791E55C6782BA76C2BF0495F9A2100000001217DA6C6B3E19F1825CFB2676DAECCE3BF3DE03CF26647C78DF00B371B25CC970000000020000000C4C63F80C74B11263E421EBF8486A4E398D0DBC09FA7D4F62CCDB309F3AEA81F0900000001217DA6C6B3E19F180100000004000000CAFED00D";
		byte[] testVectorBytes = Utils.hexStringToByteArray(testVectorHex);
		LCSProcessor decoder = LCSProcessor.buildDecoder(testVectorBytes);
		WriteSet writeSet = decoder.decodeWriteSet();
		assertNotNull(writeSet);
		assertEquals(2,writeSet.size());
		//
		WriteSetTuple writeSetTuple = writeSet.get(0);
		assertNotNull(writeSetTuple);
		WriteOp writeOp = writeSetTuple.getY();
		assertEquals(Type.DELETE,writeOp.getOpType());
		assertNull(writeOp.getBytes());
		AccessPath accessPath = writeSetTuple.getX();
		assertNotNull(accessPath);
		assertEquals(new AccountAddress("a71d76faa2d2d5c3224ec3d41deb293973564a791e55c6782ba76c2bf0495f9a"),accessPath.getAccountAddress());
		assertEquals(new Path(Utils.hexStringToByteArray("01217da6c6b3e19f1825cfb2676daecce3bf3de03cf26647c78df00b371b25cc97")),accessPath.getPath());
		//
		writeSetTuple = writeSet.get(1);
		assertNotNull(writeSetTuple);
		writeOp = writeSetTuple.getY();
		assertEquals(Type.WRITE,writeOp.getOpType());
		assertArrayEquals(Utils.hexStringToByteArray("cafed00d"),writeOp.getBytes());
		accessPath = writeSetTuple.getX();
		assertNotNull(accessPath);
		assertEquals(new AccountAddress("c4c63f80c74b11263e421ebf8486a4e398d0dbc09fa7d4f62ccdb309f3aea81f"),accessPath.getAccountAddress());
		assertEquals(new Path(Utils.hexStringToByteArray("01217da6c6b3e19f18")),accessPath.getPath());
	}

}
