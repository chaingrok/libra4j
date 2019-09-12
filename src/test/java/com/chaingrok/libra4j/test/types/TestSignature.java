package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.misc.Libra4jException;
import com.chaingrok.libra4j.misc.Utils;
import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.Signature;
import com.google.protobuf.ByteString;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestSignature extends TestClass {
	
	@Test
	public void test001NewInstanceOk() {
		byte[] bytes = Utils.getByteArray(Signature.BYTE_LENGTH,0x01);
		Signature signature = new Signature(bytes);
		assertSame(bytes,signature.getBytes());
		//
		ByteString byteString = ByteString.copyFrom(bytes);
		signature = new Signature(byteString);
		assertArrayEquals(bytes,signature.getBytes());
	}
	
	@Test
	public void test002NewInstanceKo() {
		byte[] bytes = Utils.getByteArray(Signature.BYTE_LENGTH-1,0x00);
		try {
			new Signature(bytes);
			fail("invalid length shhould throw exception");
		} catch (Libra4jException e) {
			assertTrue(e.getMessage().startsWith("invalid length for signature"));
		}
	}
	
	@Test
	public void test002SignatureToString() {
		byte[] bytes = Utils.getByteArray(Signature.BYTE_LENGTH,0x01);
		Signature signature = new Signature(bytes);
		assertTrue(signature.toString().contains(Utils.byteArrayToHexString(bytes)));
	}

}
