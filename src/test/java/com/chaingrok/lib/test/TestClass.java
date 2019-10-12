package com.chaingrok.lib.test;

import org.junit.After;

import com.chaingrok.lib.ChaingrokException;
import com.chaingrok.lib.ChaingrokLog;

public class TestClass {
	
	@After
	public void checkErrors() {
		if (ChaingrokLog.hasLogs()) {
			System.out.println(ChaingrokLog.dumpLogs());
			ChaingrokLog.purgeLogs();
			throw new ChaingrokException("errors in test");
		}
	}

}
