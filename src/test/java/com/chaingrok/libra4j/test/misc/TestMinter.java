package com.chaingrok.libra4j.test.misc;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.lib.test.TestClass;
import com.chaingrok.libra4j.crypto.KeyPair;
import com.chaingrok.libra4j.misc.Minter;
import com.chaingrok.libra4j.types.AccountAddress;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestMinter extends TestClass {
	
	
	@Test
	public void test001Mint() {
		KeyPair keyPair = KeyPair.random();
		//
		AccountAddress accountAddress = new AccountAddress(keyPair.getLibraAddress());
		long microLibras = 10_000_000L;
		Minter minter = new Minter();
		assertEquals(Minter.HTTP_OK,minter.mint(accountAddress, microLibras).getHttpCode());
		//TODO: check account amount
	}
}
