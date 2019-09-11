package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.misc.Utils;
import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.AccessPath;
import com.chaingrok.libra4j.types.AccountAddress;
import com.chaingrok.libra4j.types.Event;
import com.chaingrok.libra4j.types.EventData;
import com.chaingrok.libra4j.types.Path;
import com.google.protobuf.ByteString;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestEvent extends TestClass {
	
	@Test
	public void test001_newInstance() {
		Event event = new Event();
		//
		Long sequenceNumber = new Long(0);
		event.setSequenceNumber(sequenceNumber);
		assertSame(sequenceNumber,event.getSequenceNumber());
		//
		AccountAddress address = new AccountAddress(Utils.getByteArray(AccountAddress.BYTE_LENGTH));
		event.setAddress(address);
		assertEquals(address,event.getAddress());
		//
		ByteString byteString  = ByteString.copyFrom(Utils.getByteArray(20));
		EventData eventData = new EventData(byteString);
		event.setData(eventData);
		assertSame(eventData,event.getData());
		//
		AccessPath accessPath = AccessPath.create((byte)0x00,new AccountAddress(AccountAddress.ADDRESS_ZERO),new Path("/test_path"));
		event.setAccessPath(accessPath);
		assertSame(accessPath,event.getAccessPath());
	}

}
