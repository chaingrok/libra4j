package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;


import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.lib.ChaingrokLog;
import com.chaingrok.lib.Utils;
import com.chaingrok.lib.test.TestClass;
import com.chaingrok.libra4j.types.Signature;
import com.google.protobuf.ByteString;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestSignature extends TestClass {
	
	@Test
	public void test001NewInstanceOk() {
		byte[] bytes = Utils.getByteArray(Signature.BYTE_LENGTH,0x01);
		Signature signature = new Signature(bytes);
		assertTrue(signature.isValid());
		assertSame(bytes,signature.getBytes());
		//
		ByteString byteString = ByteString.copyFrom(bytes);
		signature = new Signature(byteString);
		assertTrue(signature.isValid());
		assertArrayEquals(bytes,signature.getBytes());
	}
	
	@Test
	public void test002NewInstanceKo() {
		byte[] bytes = Utils.getByteArray(Signature.BYTE_LENGTH-1,0x00);
		Signature signature = new Signature(bytes);
		assertFalse(signature.isValid());
		assertTrue(ChaingrokLog.hasLogs());
		assertEquals(1,ChaingrokLog.getLogs().size());
		ChaingrokLog.purgeLogs();
	}
	
	@Test
	public void test002SignatureToString() {
		byte[] bytes = Utils.getByteArray(Signature.BYTE_LENGTH,0x01);
		Signature signature = new Signature(bytes);
		assertTrue(signature.toString().contains(Utils.byteArrayToHexString(bytes)));
	}

}
