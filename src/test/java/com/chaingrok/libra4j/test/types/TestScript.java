package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.misc.LCSProcessor;
import com.chaingrok.libra4j.misc.Utils;
import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.Code;
import com.chaingrok.libra4j.types.Program;
import com.chaingrok.libra4j.types.Script;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestScript extends TestClass {
	
	//@Test
	public void test001SCriptLCSEncodingDecoding() { 
		String testVectorHex = "c40000004c49425241564d0a010007014a000000060000000350000000060000000d56000000060000000e5c0000000600000005620000003300000004950000002000000008b50000000f000000000000010002000300010400020002040200030204020300063c53454c463e0c4c696272614163636f756e74094c69627261436f696e046d61696e0f6d696e745f746f5f616464726573730000000000000000000000000000000000000000000000000000000000000000000100020004000c000c011301010202000000010000002000000019ec9d6b9c90d4283260e125d69682bc1551e15f8466b1aff0b9d417a3a4fb750000000000e1f50500000000e02202000000000000000000000000007f14955d0000000020000000664f6e8f36eacb1770fa879d86c2c1d0fafea145e84fa7d671ab7a011a54d509400000006f8f4268092f574591f5a6a2a9ea7e4c54d6fc1cb7368c0550f51203efa32e5316c626ce06e0998f51e35f139b55b8d8ef9618ec7d35c33b4ce01cd1e16f2701";
		int vecLength = 384;
		byte[] bytes = Utils.hexStringToByteArray(testVectorHex);
		assertEquals(vecLength,bytes.length);
		LCSProcessor decoder = LCSProcessor.buildDecoder(bytes);
		assertEquals(vecLength,(int)decoder.getUndecodedDataSize());
		Script script = decoder.decodeScript();
		assertNotNull(script);
		//
		Code code = script.getCode();
		assertNotNull(code);
		assertNotNull(code.getBytes());
		assertEquals(0,code.getBytes().length);
		//
		/*
		Arguments arguments = script.getArguments();
		assertNotNull(arguments);
		assertEquals(2,arguments.size());
		Argument argument0 = arguments.get(0);
		assertEquals("CAFE D00D",argument0.getString());
		Argument argument1 = arguments.get(1);
		assertEquals("cafe d00d",argument1.getString());
		*/
	}
}
