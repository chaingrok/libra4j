package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertArrayEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.Code;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestCode extends TestClass {
	
	@Test
	public void test001NewInstances() {
		Code code = new Code(Code.MINT.getBytes());
		assertArrayEquals(Code.MINT.getBytes(),code.getBytes());
		code = new Code(Code.PEER_TO_PEER_TRANSFER.getBytes());
		assertArrayEquals(Code.PEER_TO_PEER_TRANSFER.getBytes(),code.getBytes());
	}

}
