package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertEquals;


import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.types.AccessPath;
import com.chaingrok.libra4j.types.AccountAddress;
import com.chaingrok.libra4j.types.Path;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAccessPath {
	
	@Test
	public void test001_parseAccessPath1() {
		String hex = "01217da6c6b3e19f1825cfb2676daecce3bf3de03cf26647c78df00b371b25cc972f73656e745f6576656e74735f636f756e742f";
		AccessPath accessPath = new AccessPath(hex);
		assertEquals(AccessPath.RESOURCE_TAG,accessPath.getTag());
		assertEquals(new AccountAddress("217da6c6b3e19f1825cfb2676daecce3bf3de03cf26647c78df00b371b25cc97"),accessPath.getAddress());
		assertEquals(Path.Type.SENT_EVENTS_COUNT,accessPath.getPath().getType());
	}
	
	@Test
	public void test002_parseAccessPath2() {
		String hex = "01217da6c6b3e19f1825cfb2676daecce3bf3de03cf26647c78df00b371b25cc972f72656365697665645f6576656e74735f636f756e742f";
		AccessPath accessPath = new AccessPath(hex);
		assertEquals(AccessPath.RESOURCE_TAG,accessPath.getTag());
		assertEquals(new AccountAddress("217da6c6b3e19f1825cfb2676daecce3bf3de03cf26647c78df00b371b25cc97"),accessPath.getAddress());
		assertEquals(Path.Type.RECEIVED_EVENTS_COUNT,accessPath.getPath().getType());
	}

}
