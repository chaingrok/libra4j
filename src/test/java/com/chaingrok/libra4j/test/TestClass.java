package com.chaingrok.libra4j.test;

import org.junit.After;

import com.chaingrok.libra4j.misc.Libra4jException;
import com.chaingrok.libra4j.misc.Libra4jLog;

public class TestClass {
	
	@After
	public void checkErrors() {
		if (Libra4jLog.hasLogs()) {
			System.out.println(Libra4jLog.dumpLogs());
			Libra4jLog.purgeLogs();
			throw new Libra4jException("errors in test");
		}
	}

}
