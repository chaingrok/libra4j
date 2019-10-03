package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

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

}
