package com.chaingrok.lib.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.lib.ChaingrokException;
import com.chaingrok.libra4j.test.TestClass;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestLibra4jException extends TestClass {
	
	@Test
	public void tes001ThrowException() {
		String message = "message";
		try {
			throw new ChaingrokException(message);
		} catch (ChaingrokException e) {
			assertEquals(message,e.getMessage());
			assertNull(e.getThrowable());
		}
	}
	
	@Test
	public void tes002ThrowExceptionWithThrowable() {
		String message = "message";
		ChaingrokException e0 = new ChaingrokException(message);
		try {
			throw new ChaingrokException(e0);
		} catch (ChaingrokException e) {
			assertEquals(message,e.getMessage());
			assertSame(e0,e.getThrowable());
		}
	}

}
