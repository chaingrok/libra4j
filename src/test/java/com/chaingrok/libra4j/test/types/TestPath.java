package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.misc.Libra4jException;
import com.chaingrok.libra4j.misc.Utils;
import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.Path;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestPath extends TestClass {
	
	@Test
	public void test001CheckPathType() {
		assertEquals(Path.Type.UNKNOWN,Path.Type.get("foo"));
		assertEquals(3,Path.Type.values().length);
		assertEquals(Path.Type.SENT_EVENTS_COUNT,Path.Type.get(Path.Type.SENT_EVENTS_COUNT.get()));
		assertEquals(Path.Type.RECEIVED_EVENTS_COUNT,Path.Type.get(Path.Type.RECEIVED_EVENTS_COUNT.get()));
	}
	
	@Test
	public void test002CheckFromHex() {
		Path path = new Path(Utils.hexStringToByteArray("2f72656365697665645f6576656e74735f636f756e742f"));
		assertEquals(Path.Type.RECEIVED_EVENTS_COUNT,path.getType());
		path = new Path(Utils.hexStringToByteArray("2f73656e745f6576656e74735f636f756e742f"));
		assertEquals(Path.Type.SENT_EVENTS_COUNT,path.getType());
	}
	
	@Test
	public void test003InvalidCharInPath() {
		byte[] path = {0x00};
		try {
			new Path(path);
			fail("should fail with invalid chars");
		} catch (Libra4jException e) {
			assertTrue(e.getMessage().startsWith("path contains invalid chars:"));
		}
		path = "!".getBytes();
		try {
			new Path(path);
			fail("should fail with invalid chars");
		} catch (Libra4jException e) {
			assertTrue(e.getMessage().startsWith("path contains invalid chars:"));
		}
		path = "-".getBytes();
		try {
			new Path(path);
			fail("should fail with invalid chars");
		} catch (Libra4jException e) {
			assertTrue(e.getMessage().startsWith("path contains invalid chars:"));
		}
		
	}
	
	@Test
	public void test004PathStart() {
		byte[] path = "foo".getBytes();
		try {
			new Path(path);
			fail("should fail with invalid chars");
		} catch (Libra4jException e) {
			assertTrue(e.getMessage().startsWith("path does not start with proper separator:"));
		}
	}
}
