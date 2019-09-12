package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.misc.Utils;
import com.chaingrok.libra4j.types.AccessPath;
import com.chaingrok.libra4j.types.AccountAddress;
import com.chaingrok.libra4j.types.Path;
import com.google.protobuf.ByteString;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAccessPath {
	
	
	@Test
	public void test001_newInstance() {
		String path = "/test_path";
		AccessPath accessPath = new AccessPath((byte)0x00,new AccountAddress(AccountAddress.ADDRESS_ZERO),new Path(path));
		String string = accessPath.toString();
		assertTrue(string.contains(path));
		assertTrue(string.contains(AccountAddress.ADDRESS_ZERO));
	}
	
	@Test
	public void test002_parseAccessPath1() {
		String hex = "01217da6c6b3e19f1825cfb2676daecce3bf3de03cf26647c78df00b371b25cc972f73656e745f6576656e74735f636f756e742f";
		AccessPath accessPath = new AccessPath(hex);
		assertEquals(AccessPath.RESOURCE_TAG,accessPath.getTag());
		assertEquals(new AccountAddress("217da6c6b3e19f1825cfb2676daecce3bf3de03cf26647c78df00b371b25cc97"),accessPath.getAddress());
		assertEquals(Path.Type.SENT_EVENTS_COUNT,accessPath.getPath().getType());
		//
		AccessPath accessPath2 = new AccessPath(ByteString.copyFrom(Utils.hexStringToByteArray(hex)));
		assertEquals(AccessPath.RESOURCE_TAG,accessPath2.getTag());
		assertEquals(new AccountAddress("217da6c6b3e19f1825cfb2676daecce3bf3de03cf26647c78df00b371b25cc97"),accessPath2.getAddress());
		assertEquals(Path.Type.SENT_EVENTS_COUNT,accessPath2.getPath().getType());
	}
	
	@Test
	public void test003_parseAccessPath2() {
		String hex = "01217da6c6b3e19f1825cfb2676daecce3bf3de03cf26647c78df00b371b25cc972f72656365697665645f6576656e74735f636f756e742f";
		AccessPath accessPath = new AccessPath(hex);
		assertEquals(AccessPath.RESOURCE_TAG,accessPath.getTag());
		assertEquals(new AccountAddress("217da6c6b3e19f1825cfb2676daecce3bf3de03cf26647c78df00b371b25cc97"),accessPath.getAddress());
		assertEquals(Path.Type.RECEIVED_EVENTS_COUNT,accessPath.getPath().getType());
		//
		//
		AccessPath accessPath2 = new AccessPath(ByteString.copyFrom(Utils.hexStringToByteArray(hex)));
		assertEquals(AccessPath.RESOURCE_TAG,accessPath2.getTag());
		assertEquals(new AccountAddress("217da6c6b3e19f1825cfb2676daecce3bf3de03cf26647c78df00b371b25cc97"),accessPath2.getAddress());
		assertEquals(Path.Type.RECEIVED_EVENTS_COUNT,accessPath2.getPath().getType());
	}

}
