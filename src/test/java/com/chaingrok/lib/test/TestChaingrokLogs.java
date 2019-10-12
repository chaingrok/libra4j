package com.chaingrok.lib.test;

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
import com.chaingrok.lib.ChaingrokLog;
import com.chaingrok.lib.ChaingrokWarning;
import com.chaingrok.lib.ChaingrokLog.Type;
import com.chaingrok.libra4j.test.TestClass;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestChaingrokLogs extends TestClass {
	
	@Test
	public void test001LogIsAbstract() {
		assertTrue(Modifier.isAbstract(ChaingrokLog.class.getModifiers()));
	}
	
	@Test
	public void test002CheckInheritance() {
		assertTrue(ChaingrokLog.class.isAssignableFrom(ChaingrokError.class));
		assertTrue(ChaingrokLog.class.isAssignableFrom(ChaingrokWarning.class));
		assertTrue(ChaingrokLog.class.isAssignableFrom(ChaingrokInfo.class));
	}
	
	@Test
	public void test003CreateErrors() {
		assertFalse(ChaingrokLog.hasLogs());
		assertNotNull(ChaingrokLog.getLogs());
		assertEquals(0,ChaingrokLog.getLogs().size());
		//
		assertFalse(ChaingrokLog.hasLogs());
		new ChaingrokError(Type.INIT_ERROR); // log code only 
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertTrue(ChaingrokLog.hasLogs());
		//
		new ChaingrokError(Type.INVALID_CLASS,1L); //log code + transaction version
		assertEquals(2,ChaingrokLog.getLogs().size());
		//
		new ChaingrokError(Type.MISSING_DATA,2L,new Object()); //log code + transaction version + error object
		assertEquals(3,ChaingrokLog.getLogs().size());
		//
		assertTrue(ChaingrokLog.hasLogs());
		//
		String content = ChaingrokLog.dumpLogs();
		assertNotNull(content);
		assertTrue(content.length() > 0);
		assertTrue(content.contains(ChaingrokError.class.getSimpleName() + " #"));
		assertFalse(content.contains(ChaingrokWarning.class.getSimpleName() + " #"));
		assertFalse(content.contains(ChaingrokInfo.class.getSimpleName() + " #"));
		//
		ChaingrokLog.purgeLogs();
		assertEquals(0,ChaingrokLog.getLogs().size());
		assertFalse(ChaingrokLog.hasLogs());
	}
	
	@Test
	public void test004CreateWarnings() {
		assertFalse(ChaingrokLog.hasLogs());
		assertNotNull(ChaingrokLog.getLogs());
		assertEquals(0,ChaingrokLog.getLogs().size());
		//
		assertFalse(ChaingrokLog.hasLogs());
		new ChaingrokWarning(Type.INIT_ERROR); // log code only 
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertTrue(ChaingrokLog.hasLogs());
		//
		new ChaingrokWarning(Type.INVALID_CLASS,1L); //log code + transaction version
		assertEquals(2,ChaingrokLog.getLogs().size());
		//
		new ChaingrokWarning(Type.MISSING_DATA,2L,new Object()); //log code + transaction version + error object
		assertEquals(3,ChaingrokLog.getLogs().size());
		//
		assertTrue(ChaingrokLog.hasLogs());
		//
		String content = ChaingrokLog.dumpLogs();
		assertNotNull(content);
		assertTrue(content.length() > 0);
		assertFalse(content.contains(ChaingrokError.class.getSimpleName() + " #"));
		assertTrue(content.contains(ChaingrokWarning.class.getSimpleName() + " #"));
		assertFalse(content.contains(ChaingrokInfo.class.getSimpleName() + " #"));
		//
		ChaingrokLog.purgeLogs();
		assertEquals(0,ChaingrokLog.getLogs().size());
		assertFalse(ChaingrokLog.hasLogs());
	}
	
	@Test
	public void test005CreateInfos() {
		assertFalse(ChaingrokLog.hasLogs());
		assertNotNull(ChaingrokLog.getLogs());
		assertEquals(0,ChaingrokLog.getLogs().size());
		//
		assertFalse(ChaingrokLog.hasLogs());
		new ChaingrokInfo(Type.INIT_ERROR); // log code only 
		assertEquals(1,ChaingrokLog.getLogs().size());
		assertTrue(ChaingrokLog.hasLogs());
		//
		new ChaingrokInfo(Type.INVALID_CLASS,1L); //log code + transaction version
		assertEquals(2,ChaingrokLog.getLogs().size());
		//
		new ChaingrokInfo(Type.INVALID_CLASS,new Object()); //log code + error object
		assertEquals(3,ChaingrokLog.getLogs().size());
		//
		new ChaingrokInfo(Type.MISSING_DATA,2L,new Object()); //log code + transaction version + error object
		assertEquals(4,ChaingrokLog.getLogs().size());
		//
		assertTrue(ChaingrokLog.hasLogs());
		//
		String content = ChaingrokLog.dumpLogs();
		assertNotNull(content);
		assertTrue(content.length() > 0);
		assertFalse(content.contains(ChaingrokError.class.getSimpleName() + " #"));
		assertFalse(content.contains(ChaingrokWarning.class.getSimpleName() + " #"));
		assertTrue(content.contains(ChaingrokInfo.class.getSimpleName() + " #"));
		//
		ChaingrokLog.purgeLogs();
		assertEquals(0,ChaingrokLog.getLogs().size());
		assertFalse(ChaingrokLog.hasLogs());
	}

}
