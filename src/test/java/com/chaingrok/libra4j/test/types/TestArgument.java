package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.libra.grpc.types.Transaction.TransactionArgument.ArgType;

import com.chaingrok.libra4j.misc.LCSProcessor;
import com.chaingrok.libra4j.misc.Libra4jError;
import com.chaingrok.libra4j.misc.Libra4jLog;
import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.AccountAddress;
import com.chaingrok.libra4j.types.Argument;
import com.chaingrok.libra4j.types.UInt32;
import com.chaingrok.libra4j.types.Argument.Type;
import com.chaingrok.libra4j.types.UInt64;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestArgument extends TestClass {
	
	@Test
	public void test001GetArgumentType() {
		assertEquals(Type.U64,Type.get(ArgType.U64));
		assertEquals(Type.ADDRESS,Type.get(ArgType.ADDRESS));
		assertEquals(Type.STRING,Type.get(ArgType.STRING));
		assertEquals(Type.BYTE_ARRAY,Type.get(ArgType.BYTEARRAY));
		assertEquals(0,Libra4jError.getLogs().size());
	}
	
	@Test
	public void test002FailArgumentType() {
		assertEquals(Type.UNRECOGNIZED,Type.get(ArgType.UNRECOGNIZED));
		assertEquals(1,Libra4jError.getLogs().size());
		Libra4jLog error = Libra4jLog.getLogs().get(0);
		assertEquals(Libra4jError.Type.UNKNOWN_VALUE,error.getType());
		Libra4jLog.purgeLogs();
	}
	
	@Test
	public void test003NewInstance() {
		Argument argument = new Argument();
		argument.setType(Type.STRING);
		assertEquals(Type.STRING,argument.getType());
		String string = "foo";
		byte[] bytes = string.getBytes();
		argument.setBytes(bytes);
		assertEquals(string,new String(argument.getBytes()));
		assertTrue(argument.toString().contains(string + ""));
		//
		argument = new Argument();
		argument.setType(Type.STRING);
		assertEquals(Type.STRING,argument.getType());
		string = "foo";
		argument.setString(string);
		assertEquals(string,argument.getString());
		assertTrue(argument.toString().contains(string + ""));
		//
		argument.setType(Type.U64);
		assertEquals(Type.U64,argument.getType());
		Long number = 123456L;
		UInt64 uint64 = new UInt64(number);
		argument.setBytes(uint64.getBytes());
		assertEquals(number,uint64.getAsLong());
		System.out.println(argument.toString());
		assertTrue(argument.toString().contains(number + ""));
		//
		argument.setType(Type.U64);
		assertEquals(Type.U64,argument.getType());
		number = 123456L;
		uint64 = new UInt64(number);
		assertEquals(number,uint64.getAsLong());
		argument.setUInt64(uint64);
		assertEquals(new UInt64(number),argument.getUInt64());
		assertTrue(argument.toString().contains(number + ""));
		//
		String hex="2c25991785343b23ae073a50e5fd809a2cd867526b3c1db2b0bf5d1924c693ed";
		argument.setType(Type.ADDRESS);
		assertEquals(Type.ADDRESS,argument.getType());
		AccountAddress accountAddress = new AccountAddress(hex);
		argument.setAccountAddress(accountAddress);
		assertEquals(accountAddress,argument.getAccountAddress());
		assertTrue(argument.toString().contains(accountAddress.toString() + ""));
	}
	
	@Test
	public void test004LCSEncodeDecodeArgumentType() {
		Type value = Type.get(ArgType.U64);
		byte[] bytes = LCSProcessor.buildEncoder()
			.encode(value)
			.build();
		assertEquals(bytes.length,UInt32.BYTE_LENGTH);
		LCSProcessor decoder = LCSProcessor.buildDecoder(bytes);
		Type result = decoder.decodeArgumentType();
		assertEquals(value,result);
		//
		value = Type.get(ArgType.STRING);
		bytes = LCSProcessor.buildEncoder()
			.encode(value)
			.build();
		assertEquals(bytes.length,UInt32.BYTE_LENGTH);
		decoder = LCSProcessor.buildDecoder(bytes);
		result = decoder.decodeArgumentType();
		assertEquals(value,result);
		//invalid type
		assertFalse(Libra4jLog.hasLogs());
		bytes = LCSProcessor.buildEncoder()
				.encode(new UInt32(-1))
				.build();
		assertNull(bytes);
		assertEquals(1,Libra4jLog.getLogs().size());
		assertEquals(Libra4jLog.Type.INVALID_VALUE,Libra4jLog.getLogs().get(0).getType());
		Libra4jLog.purgeLogs();
	}
	

}
