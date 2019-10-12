package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.lib.ChaingrokLog;
import com.chaingrok.lib.UInt32;
import com.chaingrok.lib.ChaingrokLog.Type;
import com.chaingrok.libra4j.misc.LCSProcessor;
import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.TransactionPayloadType;



@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestTransactionPayloadType extends TestClass {
	
	@Test
	public void test001GetType() {
		assertEquals(TransactionPayloadType.PROGRAM,TransactionPayloadType.get(TransactionPayloadType.PROGRAM.getType()));
		assertEquals(TransactionPayloadType.WRITESET,TransactionPayloadType.get(TransactionPayloadType.WRITESET.getType()));
		assertEquals(TransactionPayloadType.SCRIPT,TransactionPayloadType.get(TransactionPayloadType.SCRIPT.getType()));
		assertEquals(TransactionPayloadType.MODULE,TransactionPayloadType.get(TransactionPayloadType.MODULE.getType()));
	}
	
	@Test
	public void test002LCSEncodeDecode() {
		TransactionPayloadType value = TransactionPayloadType.PROGRAM;
		byte[] bytes = LCSProcessor.buildEncoder()
			.encode(value)
			.build();
		assertEquals(bytes.length,UInt32.BYTE_LENGTH);
		LCSProcessor decoder = LCSProcessor.buildDecoder(bytes);
		TransactionPayloadType  result= decoder.decodeTransactionPayloadType();
		assertEquals(value,result);
		//
		value = TransactionPayloadType.SCRIPT;
		bytes = LCSProcessor.buildEncoder()
			.encode(value)
			.build();
		assertEquals(bytes.length,UInt32.BYTE_LENGTH);
		decoder = LCSProcessor.buildDecoder(bytes);
		result= decoder.decodeTransactionPayloadType();
		assertEquals(value,result);
		//invalid type
		assertFalse(ChaingrokLog.hasLogs());
		bytes = LCSProcessor.buildEncoder()
				.encode(new UInt32(-1))
				.build();
		assertNull(bytes);
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertEquals(Type.INVALID_VALUE,ChaingrokLog.getLogs().get(0).getType());
		ChaingrokLog.purgeLogs();
	}
	
	

}
