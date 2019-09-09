package com.chaingrok.libra4j.test.misc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Modifier;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.misc.Libra4jError;
import com.chaingrok.libra4j.misc.Libra4jInfo;
import com.chaingrok.libra4j.misc.Libra4jLog;
import com.chaingrok.libra4j.misc.Libra4jLog.Type;
import com.chaingrok.libra4j.misc.Libra4jWarning;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestLibra4jLogs {
	
	@Test
	public void test001_LogIsAbstract() {
		assertTrue(Modifier.isAbstract(Libra4jLog.class.getModifiers()));
	}
	
	@Test
	public void test002_checkInheritance() {
		assertTrue(Libra4jLog.class.isAssignableFrom(Libra4jError.class));
		assertTrue(Libra4jLog.class.isAssignableFrom(Libra4jWarning.class));
		assertTrue(Libra4jLog.class.isAssignableFrom(Libra4jInfo.class));
	}
	
	@Test
	public void test003_createLogs() {
		assertFalse(Libra4jLog.hasLogs());
		assertNotNull(Libra4jLog.getLogs());
		assertEquals(0,Libra4jLog.getLogs().size());
		assertFalse(Libra4jLog.hasLogs());
		new Libra4jError(Type.INIT_ERROR);
		assertEquals(1,Libra4jLog.getLogs().size());
		assertTrue(Libra4jLog.hasLogs());
		new Libra4jWarning(Type.INVALID_CLASS);
		assertEquals(2,Libra4jLog.getLogs().size());
		new Libra4jInfo(Type.MISSING_DATA);
		assertEquals(3,Libra4jLog.getLogs().size());
		assertTrue(Libra4jLog.hasLogs());
		Libra4jLog.purgeLogs();
		assertEquals(0,Libra4jLog.getLogs().size());
		assertFalse(Libra4jLog.hasLogs());
	}

}
