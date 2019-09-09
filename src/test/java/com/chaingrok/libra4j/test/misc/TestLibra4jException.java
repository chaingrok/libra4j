package com.chaingrok.libra4j.test.misc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.misc.Libra4jException;
import com.chaingrok.libra4j.test.TestClass;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestLibra4jException extends TestClass {
	
	@Test
	public void tes001_throwException() {
		String message = "message";
		try {
			throw new Libra4jException(message);
		} catch (Libra4jException e) {
			assertEquals(message,e.getMessage());
			assertNull(e.getThrowable());
		}
	}
	
	@Test
	public void tes002_throwException() {
		String message = "message";
		Libra4jException e0 = new Libra4jException(message);
		try {
			throw new Libra4jException(e0);
		} catch (Libra4jException e) {
			assertEquals(message,e.getMessage());
			assertSame(e0,e.getThrowable());
		}
	}

}
