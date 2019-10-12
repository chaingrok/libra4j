package com.chaingrok.libra4j.test.misc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Modifier;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.lib.ChaingrokError;
import com.chaingrok.lib.ChaingrokInfo;
import com.chaingrok.lib.Libra4jLog;
import com.chaingrok.lib.Libra4jWarning;
import com.chaingrok.lib.Libra4jLog.Type;
import com.chaingrok.libra4j.test.TestClass;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestLibra4jLogs extends TestClass {
	
	@Test
	public void test001LogIsAbstract() {
		assertTrue(Modifier.isAbstract(Libra4jLog.class.getModifiers()));
	}
	
	@Test
	public void test002CheckInheritance() {
		assertTrue(Libra4jLog.class.isAssignableFrom(ChaingrokError.class));
		assertTrue(Libra4jLog.class.isAssignableFrom(Libra4jWarning.class));
		assertTrue(Libra4jLog.class.isAssignableFrom(ChaingrokInfo.class));
	}
	
	@Test
	public void test003CreateErrors() {
		assertFalse(Libra4jLog.hasLogs());
		assertNotNull(Libra4jLog.getLogs());
		assertEquals(0,Libra4jLog.getLogs().size());
		//
		assertFalse(Libra4jLog.hasLogs());
		new ChaingrokError(Type.INIT_ERROR); // log code only 
		assertEquals(1,Libra4jLog.getLogs().size());
		assertTrue(Libra4jLog.hasLogs());
		//
		new ChaingrokError(Type.INVALID_CLASS,1L); //log code + transaction version
		assertEquals(2,Libra4jLog.getLogs().size());
		//
		new ChaingrokError(Type.MISSING_DATA,2L,new Object()); //log code + transaction version + error object
		assertEquals(3,Libra4jLog.getLogs().size());
		//
		assertTrue(Libra4jLog.hasLogs());
		//
		String content = Libra4jLog.dumpLogs();
		assertNotNull(content);
		assertTrue(content.length() > 0);
		assertTrue(content.contains(ChaingrokError.class.getSimpleName() + " #"));
		assertFalse(content.contains(Libra4jWarning.class.getSimpleName() + " #"));
		assertFalse(content.contains(ChaingrokInfo.class.getSimpleName() + " #"));
		//
		Libra4jLog.purgeLogs();
		assertEquals(0,Libra4jLog.getLogs().size());
		assertFalse(Libra4jLog.hasLogs());
	}
	
	@Test
	public void test004CreateWarnings() {
		assertFalse(Libra4jLog.hasLogs());
		assertNotNull(Libra4jLog.getLogs());
		assertEquals(0,Libra4jLog.getLogs().size());
		//
		assertFalse(Libra4jLog.hasLogs());
		new Libra4jWarning(Type.INIT_ERROR); // log code only 
		assertEquals(1,Libra4jLog.getLogs().size());
		assertTrue(Libra4jLog.hasLogs());
		//
		new Libra4jWarning(Type.INVALID_CLASS,1L); //log code + transaction version
		assertEquals(2,Libra4jLog.getLogs().size());
		//
		new Libra4jWarning(Type.MISSING_DATA,2L,new Object()); //log code + transaction version + error object
		assertEquals(3,Libra4jLog.getLogs().size());
		//
		assertTrue(Libra4jLog.hasLogs());
		//
		String content = Libra4jLog.dumpLogs();
		assertNotNull(content);
		assertTrue(content.length() > 0);
		assertFalse(content.contains(ChaingrokError.class.getSimpleName() + " #"));
		assertTrue(content.contains(Libra4jWarning.class.getSimpleName() + " #"));
		assertFalse(content.contains(ChaingrokInfo.class.getSimpleName() + " #"));
		//
		Libra4jLog.purgeLogs();
		assertEquals(0,Libra4jLog.getLogs().size());
		assertFalse(Libra4jLog.hasLogs());
	}
	
	@Test
	public void test005CreateInfos() {
		assertFalse(Libra4jLog.hasLogs());
		assertNotNull(Libra4jLog.getLogs());
		assertEquals(0,Libra4jLog.getLogs().size());
		//
		assertFalse(Libra4jLog.hasLogs());
		new ChaingrokInfo(Type.INIT_ERROR); // log code only 
		assertEquals(1,Libra4jLog.getLogs().size());
		assertTrue(Libra4jLog.hasLogs());
		//
		new ChaingrokInfo(Type.INVALID_CLASS,1L); //log code + transaction version
		assertEquals(2,Libra4jLog.getLogs().size());
		//
		new ChaingrokInfo(Type.INVALID_CLASS,new Object()); //log code + error object
		assertEquals(3,Libra4jLog.getLogs().size());
		//
		new ChaingrokInfo(Type.MISSING_DATA,2L,new Object()); //log code + transaction version + error object
		assertEquals(4,Libra4jLog.getLogs().size());
		//
		assertTrue(Libra4jLog.hasLogs());
		//
		String content = Libra4jLog.dumpLogs();
		assertNotNull(content);
		assertTrue(content.length() > 0);
		assertFalse(content.contains(ChaingrokError.class.getSimpleName() + " #"));
		assertFalse(content.contains(Libra4jWarning.class.getSimpleName() + " #"));
		assertTrue(content.contains(ChaingrokInfo.class.getSimpleName() + " #"));
		//
		Libra4jLog.purgeLogs();
		assertEquals(0,Libra4jLog.getLogs().size());
		assertFalse(Libra4jLog.hasLogs());
	}

}
