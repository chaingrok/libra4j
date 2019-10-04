package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.misc.Libra4jLog;
import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.WriteOp;
import com.chaingrok.libra4j.types.WriteOp.Type;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestVWriteSet extends TestClass {
	
	@Test
	public void test001WriteOptype() {
		assertEquals(Type.WRITE,Type.get(Type.WRITE.getOpType()));
		assertEquals(Type.DELETE,Type.get(Type.DELETE.getOpType()));
		assertFalse(Libra4jLog.hasLogs());
		assertEquals(Type.UNRECOGNIZED,Type.get(Type.UNRECOGNIZED.getOpType()));
		assertTrue(Libra4jLog.hasLogs());
		Libra4jLog.purgeLogs();
	}
	
	@Test
	public void test002WriteOpInstanceOk() {
		byte[] bytes = {0x00,0x01};
		WriteOp writeOp = new WriteOp(bytes);
		assertSame(bytes,writeOp.getBytes());
		assertNull(writeOp.getIsValue());
		writeOp.setIsValue(true);
		assertTrue(writeOp.getIsValue());
		assertNull(writeOp.getOpType());
		writeOp.setOpType(Type.WRITE);
		assertEquals(Type.WRITE,writeOp.getOpType());
	}

}
