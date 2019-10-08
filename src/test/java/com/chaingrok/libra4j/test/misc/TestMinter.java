package com.chaingrok.libra4j.test.misc;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.crypto.KeyPair;
import com.chaingrok.libra4j.misc.Minter;
import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.AccountAddress;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestMinter extends TestClass {
	
	
	@Test
	public void test001HelperFunctions() {
		KeyPair keyPair = KeyPair.random();
		byte[] bytes = KeyPair.toLibraAddressByteArray(keyPair.getPublicKey());
		AccountAddress accountAddress = new AccountAddress(bytes);
		long microLibras = 10_000_000L;
		Minter minter = new Minter();
		assertEquals(200,minter.mint(accountAddress, microLibras));
		//TODO: check account amount
	}
}
