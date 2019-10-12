package com.chaingrok.libra4j.test;

import org.junit.After;

import com.chaingrok.lib.ChaingrokException;
import com.chaingrok.lib.Libra4jLog;

public class TestClass {
	
	@After
	public void checkErrors() {
		if (Libra4jLog.hasLogs()) {
			System.out.println(Libra4jLog.dumpLogs());
			Libra4jLog.purgeLogs();
			throw new ChaingrokException("errors in test");
		}
	}

}
