package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.lib.Utils;
import com.chaingrok.lib.test.TestClass;
import com.chaingrok.libra4j.types.AccessPath;
import com.chaingrok.libra4j.types.AccountAddress;
import com.chaingrok.libra4j.types.Event;
import com.chaingrok.libra4j.types.EventData;
import com.chaingrok.libra4j.types.Path;
import com.google.protobuf.ByteString;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestEvent extends TestClass {
	
	@Test
	public void test001NewInstance() {
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
	
	@Test
	public void test002EventToString() {
		Event event = new Event();
		//
		long seqNum = 123456789L;
		Long sequenceNumber = new Long(seqNum);
		event.setSequenceNumber(sequenceNumber);
		//
		byte[] accountAddress = Utils.getByteArray(AccountAddress.BYTE_LENGTH,0x01);
		AccountAddress address = new AccountAddress(accountAddress);
		event.setAddress(address);
		//
		byte[] eventDataBytes = Utils.getByteArray(20,0xab);
		ByteString byteString  = ByteString.copyFrom(eventDataBytes);
		EventData eventData = new EventData(byteString);
		event.setData(eventData);
		//
		String testPath = "/test_path";
		AccessPath accessPath = AccessPath.create((byte)0x00,new AccountAddress(AccountAddress.ADDRESS_ZERO),new Path(testPath));
		event.setAccessPath(accessPath);
		//
		String string = event.toString();
		System.out.println("event:" + string);
		assertTrue(string.contains(seqNum + ""));
		assertTrue(string.contains(Utils.byteArrayToHexString(accountAddress)));
		assertTrue(string.contains(Utils.byteArrayToHexString(eventDataBytes)));
		assertTrue(string.contains(testPath));
	}

}
