package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.lib.UInt64;
import com.chaingrok.lib.Utils;
import com.chaingrok.lib.test.TestClass;
import com.chaingrok.libra4j.misc.LCSProcessor;
import com.chaingrok.libra4j.types.AccountAddress;
import com.chaingrok.libra4j.types.Argument;
import com.chaingrok.libra4j.types.Arguments;
import com.chaingrok.libra4j.types.Code;
import com.chaingrok.libra4j.types.Script;
import com.chaingrok.libra4j.types.Argument.Type;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestScript extends TestClass {
	
	
	@Test
	public void test001ScriptLCSDecoding() { //transaction 31167 as per October 11th 2019
		String testVectorHex = "b80000004c49425241564d0a010007014a00000004000000034e000000060000000d54000000060000000e5a0000000600000005600000002900000004890000002000000008a90000000f00000000000001000200010300020002040200030204020300063c53454c463e0c4c696272614163636f756e74046d61696e0f7061795f66726f6d5f73656e6465720000000000000000000000000000000000000000000000000000000000000000000100020004000c000c0113010102020000000100000020000000768175a4c6b30855a0983a99d17139cee8f9010623443fbf2cb7dd84b4d146d600000000001bb7000000000040420f00000000000100000000000000d8f59d5d00000000200000007746108532981e8183c37125d70e2b70e917121c8688dc2ffd32e89c03f006324000000016782895c9544233865df1da02addd685f193b127a315499aca1a682cc0b8a4b9445cc06ec471eb0b581169234e7f68a87d306fb8097275c4491ffd384fe630a";
		int vecLength = 372;
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
		assertEquals(184,code.getBytes().length);
		//
		Arguments arguments = script.getArguments();
		assertNotNull(arguments);
		assertEquals(2,arguments.size());
		Argument argument0 = arguments.get(0);
		assertEquals(Type.ADDRESS,argument0.getType());
		assertEquals(new AccountAddress("768175a4c6b30855a0983a99d17139cee8f9010623443fbf2cb7dd84b4d146d6"),argument0.getAccountAddress());
		Argument argument1 = arguments.get(1);
		assertEquals(Type.U64,argument1.getType());
		assertEquals(new UInt64(12000000L),argument1.getUInt64());
		//Transaction trail
		assertEquals(128,(int)decoder.getUndecodedDataSize());
		assertEquals(new UInt64(1000000L),decoder.decodeUInt64()); //max gas amount
		assertEquals(new UInt64(1L),decoder.decodeUInt64()); //gas sunit price
		assertEquals(new UInt64(1570633176L),decoder.decodeUInt64()); // expiration time
		decoder.decodePublicKey();
		decoder.decodeSignature();
		//
		assertEquals(0,(int)decoder.getUndecodedDataSize());
	}
	
}
