package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.lib.ChaingrokLog;
import com.chaingrok.lib.ChaingrokLog.Type;
import com.chaingrok.lib.test.TestClass;
import com.chaingrok.libra4j.types.MoveIR;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestMoveIR extends TestClass {
	
	@Test
	public void test001MoveIRFromJsonKo() {
		String string = "";
		MoveIR moveIR = MoveIR.fromJson(string);
		assertNull(moveIR);
		//
		string = "{}";
		assertFalse(ChaingrokLog.hasLogs());
		moveIR = MoveIR.fromJson("{}");
		assertNull(moveIR.getCode());
		assertNull(moveIR.getArgs());
		assertEquals(2,ChaingrokLog.getLogs().size());
		assertEquals(Type.NULL_DATA,ChaingrokLog.getLogs().get(0).getType());
		assertTrue(((String)ChaingrokLog.getLogs().get(0).getObject()).contains("code array"));
		assertEquals(Type.NULL_DATA,ChaingrokLog.getLogs().get(1).getType());
		assertTrue(((String)ChaingrokLog.getLogs().get(1).getObject()).contains("args array"));
		ChaingrokLog.purgeLogs();
		//
		string = "{\"code\":[]}";
		moveIR = MoveIR.fromJson(string);
		assertEquals(0,moveIR.getCode().length);
		assertEquals(2,ChaingrokLog.getLogs().size());
		assertEquals(Type.INVALID_VALUE,ChaingrokLog.getLogs().get(0).getType());
		assertTrue(((String)ChaingrokLog.getLogs().get(0).getObject()).contains("code array"));
		assertEquals(Type.NULL_DATA,ChaingrokLog.getLogs().get(1).getType());
		assertTrue(((String)ChaingrokLog.getLogs().get(1).getObject()).contains("args array"));
		ChaingrokLog.purgeLogs();
		//
		string = "{\"args\":[1]}";
		moveIR = MoveIR.fromJson(string);
		assertEquals(1,moveIR.getArgs().length);
		assertEquals(2,ChaingrokLog.getLogs().size());
		assertEquals(Type.NULL_DATA,ChaingrokLog.getLogs().get(0).getType());
		assertTrue(((String)ChaingrokLog.getLogs().get(0).getObject()).contains("code array"));
		assertEquals(Type.INVALID_VALUE,ChaingrokLog.getLogs().get(1).getType());
		assertTrue(((String)ChaingrokLog.getLogs().get(1).getObject()).contains("args array"));
		ChaingrokLog.purgeLogs();
	}
	
	@Test
	public void test002MoveIRFromJsonOk() {
		String string = "{\"code\":[0,1,2],\"args\":[]}";
		MoveIR moveIR = MoveIR.fromJson(string);
		assertNotNull(moveIR);
		assertNotNull(moveIR.getCode());
		assertEquals(3,moveIR.getCode().length);
		assertNotNull(moveIR.getArgs());
		assertEquals(0,moveIR.getArgs().length);
		byte[] result = moveIR.toByteArray();
		byte[] bytes = {0x00,0x01,0x02};
		assertArrayEquals(bytes,result);
	}
	
	@Test
	public void test003MoveIRFromFiles() {
		//key_rotate
		String string = "{\"code\":[76,73,66,82,65,86,77,10,1,0,7,1,74,0,0,0,4,0,0,0,3,78,0,0,0,6,0,0,0,13,84,0,0,0,5,0,0,0,14,89,0,0,0,5,0,0,0,5,94,0,0,0,51,0,0,0,4,145,0,0,0,32,0,0,0,8,177,0,0,0,13,0,0,0,0,0,0,1,0,2,0,1,3,0,2,0,1,8,0,3,1,8,3,0,6,60,83,69,76,70,62,12,76,105,98,114,97,65,99,99,111,117,110,116,4,109,97,105,110,25,114,111,116,97,116,101,95,97,117,116,104,101,110,116,105,99,97,116,105,111,110,95,107,101,121,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,3,0,12,0,19,1,1,2],\"args\":[]}";
		MoveIR moveIR = MoveIR.fromJson(string);
		assertNotNull(moveIR);
		assertNotNull(moveIR.getArgs());
		assertEquals(0,moveIR.getArgs().length);
		assertNotNull(moveIR.getCode());
		assertEquals(190,moveIR.getCode().length);
		byte[] result = moveIR.toByteArray();
		assertEquals(76,result[0]);
		assertEquals(7,result[10]);
		assertEquals(2,result[189]);
	}
	
	
}
