package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.misc.LCSProcessor;
import com.chaingrok.libra4j.misc.Libra4jException;
import com.chaingrok.libra4j.misc.Utils;
import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.Path;
import com.chaingrok.libra4j.types.UInt32;


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
		assertEquals(Path.Type.RECEIVED_EVENTS_COUNT,path.getPathType());
		path = new Path(Utils.hexStringToByteArray("2f73656e745f6576656e74735f636f756e742f"));
		assertEquals(Path.Type.SENT_EVENTS_COUNT,path.getPathType());
	}
	
	@Test
	public void test003InvalidCharInPath() {
		byte[] path = {0x00};
		try {
			new Path(path,false);
			fail("should fail with invalid chars");
		} catch (Libra4jException e) {
			assertTrue(e.getMessage().startsWith("path contains invalid chars:"));
		}
		path = "!".getBytes();
		try {
			new Path(path,false);
			fail("should fail with invalid chars");
		} catch (Libra4jException e) {
			assertTrue(e.getMessage().startsWith("path contains invalid chars:"));
		}
		path = "-".getBytes();
		try {
			new Path(path,false);
			fail("should fail with invalid chars");
		} catch (Libra4jException e) {
			assertTrue(e.getMessage().startsWith("path contains invalid chars:"));
		}
		
	}
	
	@Test
	public void test004PathStart() {
		byte[] path = "foo".getBytes();
		try {
			new Path(path,false);
			fail("should fail with invalid chars");
		} catch (Libra4jException e) {
			assertTrue(e.getMessage().startsWith("path does not start with proper separator:"));
		}
	}
	
	@Test
	public void test004PathLCSEncodingDecoding() { //based on https://github.com/libra/libra/tree/master/common/canonical_serialization
		String testVector = "2100000001217DA6C6B3E19F1825CFB2676DAECCE3BF3DE03CF26647C78DF00B371B25CC97";
		String pathHex = "01217da6c6b3e19f1825cfb2676daecce3bf3de03cf26647c78df00b371b25cc97";
		byte[] bytes = Utils.hexStringToByteArray(testVector);
		assertEquals(UInt32.BYTE_LENGTH + pathHex.getBytes().length/2,bytes.length);
		LCSProcessor decoder = LCSProcessor.buildDecoder(bytes);
		Path result = decoder.decodePath();
		assertEquals(new Path(Utils.hexStringToByteArray(pathHex)),result);
		//
		bytes = LCSProcessor.buildEncoder()
				.encode(new Path(Utils.hexStringToByteArray(pathHex)))
				.build();
		assertEquals(UInt32.BYTE_LENGTH + pathHex.getBytes().length/2,bytes.length);
		assertArrayEquals(Utils.hexStringToByteArray(testVector),bytes);
	}
}
