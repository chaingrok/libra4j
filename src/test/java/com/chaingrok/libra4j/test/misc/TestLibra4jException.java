package com.chaingrok.libra4j.test.misc;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.misc.Libra4jException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestLibra4jException {
	
	@Test
	public void tes001_throwException() {
		String message = "message";
		try {
			throw new Libra4jException(message);
		} catch (Libra4jException e) {
			assertEquals(message,e.getMessage());
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
		}
	}

}
