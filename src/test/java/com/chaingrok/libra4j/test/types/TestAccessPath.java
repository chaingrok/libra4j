package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.misc.LCSProcessor;
import com.chaingrok.libra4j.misc.Utils;
import com.chaingrok.libra4j.types.AccessPath;
import com.chaingrok.libra4j.types.AccountAddress;
import com.chaingrok.libra4j.types.Path;
import com.chaingrok.libra4j.types.UInt32;
import com.google.protobuf.ByteString;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAccessPath {
	
	
	@Test
	public void test001NewInstance() {
		String path = "/test_path";
		AccessPath accessPath = new AccessPath((byte)0x00,new AccountAddress(AccountAddress.ADDRESS_ZERO),new Path(path));
		String string = accessPath.toString();
		assertTrue(string.contains(path));
		assertTrue(string.contains(AccountAddress.ADDRESS_ZERO));
	}
	
	@Test
	public void test002ParseAccessPath1() {
		String hex = "01217da6c6b3e19f1825cfb2676daecce3bf3de03cf26647c78df00b371b25cc972f73656e745f6576656e74735f636f756e742f";
		AccessPath accessPath = new AccessPath(hex);
		assertEquals(AccessPath.RESOURCE_TAG,accessPath.getTag());
		assertEquals(new AccountAddress("217da6c6b3e19f1825cfb2676daecce3bf3de03cf26647c78df00b371b25cc97"),accessPath.getAccountAddress());
		assertEquals(Path.Type.SENT_EVENTS_COUNT,accessPath.getPath().getPathType());
		//
		AccessPath accessPath2 = new AccessPath(ByteString.copyFrom(Utils.hexStringToByteArray(hex)));
		assertEquals(AccessPath.RESOURCE_TAG,accessPath2.getTag());
		assertEquals(new AccountAddress("217da6c6b3e19f1825cfb2676daecce3bf3de03cf26647c78df00b371b25cc97"),accessPath2.getAccountAddress());
		assertEquals(Path.Type.SENT_EVENTS_COUNT,accessPath2.getPath().getPathType());
	}
	
	@Test
	public void test003ParseAccessPath2() {
		String hex = "01217da6c6b3e19f1825cfb2676daecce3bf3de03cf26647c78df00b371b25cc972f72656365697665645f6576656e74735f636f756e742f";
		AccessPath accessPath = new AccessPath(hex);
		assertEquals(AccessPath.RESOURCE_TAG,accessPath.getTag());
		assertEquals(new AccountAddress("217da6c6b3e19f1825cfb2676daecce3bf3de03cf26647c78df00b371b25cc97"),accessPath.getAccountAddress());
		assertEquals(Path.Type.RECEIVED_EVENTS_COUNT,accessPath.getPath().getPathType());
		//
		//
		AccessPath accessPath2 = new AccessPath(ByteString.copyFrom(Utils.hexStringToByteArray(hex)));
		assertEquals(AccessPath.RESOURCE_TAG,accessPath2.getTag());
		assertEquals(new AccountAddress("217da6c6b3e19f1825cfb2676daecce3bf3de03cf26647c78df00b371b25cc97"),accessPath2.getAccountAddress());
		assertEquals(Path.Type.RECEIVED_EVENTS_COUNT,accessPath2.getPath().getPathType());
	}
	
	@Test
	public void test004AccessPathLCSEncodingDecoding() {  //based on https://github.com/libra/libra/tree/master/common/canonical_serialization
		String accountAddressHex = "9a1ad09742d1ffc62e659e9a7797808b206f956f131d07509449c01ad8220ad4";
		String pathHex = "01217da6c6b3e19f1825cfb2676daecce3bf3de03cf26647c78df00b371b25cc97";
		String testVectorHex = "200000009A1AD09742D1FFC62E659E9A7797808B206F956F131D07509449C01AD8220AD42100000001217DA6C6B3E19F1825CFB2676DAECCE3BF3DE03CF26647C78DF00B371B25CC97";
		byte[] bytes = Utils.hexStringToByteArray(testVectorHex);
		assertEquals(UInt32.BYTE_LENGTH + AccountAddress.BYTE_LENGTH + UInt32.BYTE_LENGTH + pathHex.getBytes().length/2,bytes.length);
		LCSProcessor decoder = LCSProcessor.buildDecoder(bytes);
		AccessPath result = decoder.decodeAccessPath();
		assertEquals(new AccountAddress(accountAddressHex),result.getAccountAddress());
		assertEquals(new Path(Utils.hexStringToByteArray(pathHex)),result.getPath());
		//
		AccountAddress accountAddress = new AccountAddress(accountAddressHex);
		Path path = new Path(Utils.hexStringToByteArray(pathHex));
		AccessPath accessPath = new AccessPath();
		accessPath.setAccountAddress(accountAddress);
		accessPath.setPath(path);
		bytes = LCSProcessor.buildEncoder()
				.encode(accessPath)
				.build();
		assertEquals(UInt32.BYTE_LENGTH + AccountAddress.BYTE_LENGTH + UInt32.BYTE_LENGTH + pathHex.getBytes().length/2,bytes.length);
		assertArrayEquals(Utils.hexStringToByteArray(testVectorHex),bytes);
	}

}
