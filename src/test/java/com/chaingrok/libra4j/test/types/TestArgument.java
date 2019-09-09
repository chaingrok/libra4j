package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.libra.grpc.types.Transaction.TransactionArgument.ArgType;

import com.chaingrok.libra4j.misc.Libra4jError;
import com.chaingrok.libra4j.misc.Libra4jLog;
import com.chaingrok.libra4j.types.Argument.Type;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestArgument {
	
	@Test
	public void test001_getArgumentType() {
		assertEquals(Type.U64,Type.get(ArgType.U64));
		assertEquals(Type.ADDRESS,Type.get(ArgType.ADDRESS));
		assertEquals(Type.STRING,Type.get(ArgType.STRING));
		assertEquals(Type.BYTE_ARRAY,Type.get(ArgType.BYTEARRAY));
		assertEquals(0,Libra4jError.getLogs().size());
	}
	
	@Test
	public void test002_failArgumentType() {
		assertEquals(Type.UNRECOGNIZED,Type.get(ArgType.UNRECOGNIZED));
		assertEquals(1,Libra4jError.getLogs().size());
		Libra4jLog error = Libra4jLog.getLogs().get(0);
		assertEquals(Libra4jError.Type.UNKNOWN_VALUE,error.getType());
	}

}
