package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.misc.Utils;
import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.AccountAddress;
import com.chaingrok.libra4j.types.EventData;
import com.chaingrok.libra4j.types.Path.Type;
import com.google.protobuf.ByteString;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestEventData extends TestClass {
	
	@Test
	public void test001NewInstance() {
		byte[] bytes = Utils.getByteArray(AccountAddress.BYTE_LENGTH * 2,0x35); //length must be > Account address length to deserialize access path;
		ByteString byteString  = ByteString.copyFrom(bytes); 
		EventData eventData = new EventData(byteString);
		assertEquals(byteString,eventData.getByteString());
		String string = eventData.toString();
		assertNotNull(string);
		assertTrue(string.length() > 0);
		assertTrue(string.contains(Utils.byteArrayToHexString(bytes)));
		//with type
		string = eventData.toString(Type.UNKNOWN);
		assertNotNull(string);
		assertTrue(string.length() > 0);
		assertTrue(string.contains(Utils.byteArrayToHexString(bytes)));
	}

}
