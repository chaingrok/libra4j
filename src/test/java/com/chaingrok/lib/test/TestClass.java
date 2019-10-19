package com.chaingrok.lib.test;

import org.junit.After;


import com.chaingrok.lib.ChaingrokException;
import com.chaingrok.lib.ChaingrokLog;
import com.chaingrok.libra4j.test.TestData;
import com.chaingrok.libra4j.types.Ledger;

public class TestClass {
	
	protected Ledger ledger = new Ledger(TestData.VALIDATOR_ENDPOINT);
	
	@After
	public void checkErrors() {
		if (ChaingrokLog.hasLogs()) {
			System.out.println(ChaingrokLog.dumpLogs());
			ChaingrokLog.purgeLogs();
			throw new ChaingrokException("errors in test");
		}
	}
}
