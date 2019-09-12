package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertArrayEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.Module;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestModule extends TestClass {
	
	@Test
	public void test001NewInstance() {
		byte[] code = {0x00,0x01};
		Module module = new Module(code);
		assertArrayEquals(code,module.getBytes());
		
	}

}
