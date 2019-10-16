package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Modifier;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.lib.Utils;
import com.chaingrok.lib.test.TestClass;
import com.chaingrok.libra4j.types.AccountAddress;
import com.chaingrok.libra4j.types.Argument;
import com.chaingrok.libra4j.types.ByteArray;
import com.chaingrok.libra4j.types.Code;
import com.chaingrok.libra4j.types.EventData;
import com.chaingrok.libra4j.types.EventKey;
import com.chaingrok.libra4j.types.Hash;
import com.chaingrok.libra4j.types.Module;
import com.chaingrok.libra4j.types.Path;
import com.chaingrok.libra4j.types.PubKey;
import com.chaingrok.libra4j.types.ValidatorId;
import com.chaingrok.libra4j.types.WriteOp;
import com.google.protobuf.ByteString;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestByteArray extends TestClass {
	
	@Test
	public void test001IsAbstract() {
		assertTrue(Modifier.isAbstract(ByteArray.class.getModifiers()));
	}
	
	@Test 
	public void test002ValidateHeritingClasses() {
		assertTrue(ByteArray.class.isAssignableFrom(AccountAddress.class));
		assertTrue(ByteArray.class.isAssignableFrom(Argument.class));
		assertTrue(ByteArray.class.isAssignableFrom(Code.class));
		assertTrue(ByteArray.class.isAssignableFrom(EventData.class));
		assertTrue(ByteArray.class.isAssignableFrom(EventKey.class));
		assertTrue(ByteArray.class.isAssignableFrom(Hash.class));
		assertTrue(ByteArray.class.isAssignableFrom(Module.class));
		assertTrue(ByteArray.class.isAssignableFrom(Path.class));
		assertTrue(ByteArray.class.isAssignableFrom(PubKey.class));
		assertTrue(ByteArray.class.isAssignableFrom(ValidatorId.class));
		assertTrue(ByteArray.class.isAssignableFrom(WriteOp.class));
	}
	
	@Test
	public void test003TestEquals() {
		ByteArrayObjectTest byteArray1 = new ByteArrayObjectTest("0101");
		ByteArrayObjectTest byteArray2 = new ByteArrayObjectTest("0101");
		assertEquals(byteArray1,byteArray1);
		assertEquals(byteArray1.hashCode(),byteArray2.hashCode());
		assertNotSame(byteArray1,byteArray2);
		//
		ByteArrayObjectTest byteArray3 = new ByteArrayObjectTest("01");
		assertNotEquals(byteArray1,byteArray3);
		ByteArrayObjectTest byteArray4 = new ByteArrayObjectTest("0100");
		assertNotEquals(byteArray1,byteArray4);
	}
	
	@Test 
	public void test004TestGet() {
		String hex = "0101";
		ByteArrayObjectTest byteArray = new ByteArrayObjectTest(hex);
		ByteString byteString = byteArray.getByteString();
		assertNotNull(byteString);
		assertArrayEquals(Utils.hexStringToByteArray(hex),byteString.toByteArray());
	}
	
	
	
	private class ByteArrayObjectTest extends ByteArray {

		public ByteArrayObjectTest(byte[] bytes) {
			super(bytes);
		}

		public ByteArrayObjectTest(String hex) {
			super(hex);
		}
		
	}

}
