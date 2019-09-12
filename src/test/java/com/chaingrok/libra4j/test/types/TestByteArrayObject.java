package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Modifier;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.ByteArrayObject;
import com.chaingrok.libra4j.types.Code;
import com.chaingrok.libra4j.types.EventData;
import com.chaingrok.libra4j.types.Module;
import com.chaingrok.libra4j.types.ValidatorId;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestByteArrayObject extends TestClass {
	
	@Test
	public void test001IsAbstract() {
		assertTrue(Modifier.isAbstract(ByteArrayObject.class.getModifiers()));
	}
	
	@Test 
	public void test002ValidateHeritingClasses() {
		assertTrue(ByteArrayObject.class.isAssignableFrom(Code.class));
		assertTrue(ByteArrayObject.class.isAssignableFrom(Module.class));
		assertTrue(ByteArrayObject.class.isAssignableFrom(EventData.class));
		assertTrue(ByteArrayObject.class.isAssignableFrom(ValidatorId.class));
	}
	
	@Test
	public void test003TestEquals() {
		ByteArrayObjectTest byteArray1 = new ByteArrayObjectTest("0101");
		ByteArrayObjectTest byteArray2 = new ByteArrayObjectTest("0101");
		assertEquals(byteArray1,byteArray1);
		assertNotSame(byteArray1,byteArray2);
		assertEquals(byteArray1,byteArray2);
		//
		ByteArrayObjectTest byteArray3 = new ByteArrayObjectTest("01");
		assertNotEquals(byteArray1,byteArray3);
		ByteArrayObjectTest byteArray4 = new ByteArrayObjectTest("0100");
		assertNotEquals(byteArray1,byteArray4);
	}
	
	private class ByteArrayObjectTest extends ByteArrayObject {

		public ByteArrayObjectTest(byte[] bytes) {
			super(bytes);
		}

		public ByteArrayObjectTest(String hex) {
			super(hex);
		}
		
	}

}
