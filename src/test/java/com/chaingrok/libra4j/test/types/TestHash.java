package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.lib.Utils;
import com.chaingrok.lib.test.TestClass;
import com.chaingrok.libra4j.types.Hash;
import com.chaingrok.libra4j.types.Hash.HashSalt;
import com.google.protobuf.ByteString;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestHash extends TestClass {
	
	@Test
	public void test001HashStandardValuess() {
		// 0 address hash
		String zero32Bytes = "0000000000000000000000000000000000000000000000000000000000000000";
		byte[] hashZero32 = Utils.hexStringToByteArray("9e6291970cb44dd94008c79bcaf9d86f18b4b49ba5b2a04781db7199ed3b9e4e");
		Hash hash = Hash.hash(Utils.hexStringToByteArray(zero32Bytes));
		//System.out.println(Utils.byteArrayToHexString(hash.getBytes()));
		assertArrayEquals(hashZero32,hash.getBytes());	
	}
	
	@Test
	public void test002HashSamples() {
		//source: https://www.browserling.com/tools/sha3-hash
		byte[] hashHello = Utils.hexStringToByteArray("3338be694f50c5f338814986cdf0686453a888b84f424d792af4b9202398f392");
		Hash hash = Hash.hash("hello");
		assertArrayEquals(hashHello,hash.getBytes());
		ByteString byteString = ByteString.copyFrom(hashHello);
		hash = new Hash(byteString);
		assertArrayEquals(hashHello,hash.getBytes());
		//
		byte[] hashWorld = Utils.hexStringToByteArray("420baf620e3fcd9b3715b42b92506e9304d56e02d3a103499a3a292560cb66b2");
		hash = Hash.hash("world");
		assertArrayEquals(hashWorld,hash.getBytes());
		byteString = ByteString.copyFrom(hashWorld);
		hash = new Hash(byteString);
		assertArrayEquals(hashWorld,hash.getBytes());
	}
	
	@Test
	public void test003CheckSalt() {
		//source: https://www.browserling.com/tools/sha3-hash
		assertNotNull(HashSalt.ACCOUNT_ADDRESS.getSalt());
		assertTrue(HashSalt.ACCOUNT_ADDRESS.getSalt().length() > 0);
		assertTrue(HashSalt.ACCOUNT_ADDRESS.getSalt().endsWith(HashSalt.LIBRA_HASH_SUFFIX));
		String hashBytes = "f7a9417dc080c401c939a10bf342ec6ccb822bd4b12a4ae0fab0354b253bcf3d";
		assertArrayEquals(Utils.hexStringToByteArray(hashBytes),Hash.hash(HashSalt.ACCOUNT_ADDRESS.getSalt()).getBytes());
		//
		assertNotNull(HashSalt.RAW_TRANSACTION.getSalt());
		assertTrue(HashSalt.RAW_TRANSACTION.getSalt().length() > 0);
		assertTrue(HashSalt.RAW_TRANSACTION.getSalt().endsWith(HashSalt.LIBRA_HASH_SUFFIX));
		hashBytes = "46f174df6ca8de5ad29745f91584bb913e7df8dd162e3e921a5c1d8637c88d16";
		assertArrayEquals(Utils.hexStringToByteArray(hashBytes),Hash.hash(HashSalt.RAW_TRANSACTION.getSalt()).getBytes());
		//
		assertNotNull(HashSalt.SIGNED_TRANSACTION.getSalt());
		assertTrue(HashSalt.SIGNED_TRANSACTION.getSalt().length() > 0);
		assertTrue(HashSalt.SIGNED_TRANSACTION.getSalt().endsWith(HashSalt.LIBRA_HASH_SUFFIX));
		hashBytes = "58e3064a401105a1e79e87e9664045a9f0b8b480711995794ffe05eda7e7bae0";
		assertArrayEquals(Utils.hexStringToByteArray(hashBytes),Hash.hash(HashSalt.SIGNED_TRANSACTION.getSalt()).getBytes());
		//
		assertNotNull(HashSalt.TRANSACTION_INFO.getSalt());
		assertTrue(HashSalt.TRANSACTION_INFO.getSalt().length() > 0);
		assertTrue(HashSalt.TRANSACTION_INFO.getSalt().endsWith(HashSalt.LIBRA_HASH_SUFFIX));
		hashBytes = "52cd023af6354ac340a7412264ba534387f358427cebb1b1ce25a47783d20a4f";
		assertArrayEquals(Utils.hexStringToByteArray(hashBytes),Hash.hash(HashSalt.TRANSACTION_INFO.getSalt()).getBytes());
	}
	
	@Test
	public void test004HashSalt() {
		//source: https://www.browserling.com/tools/sha3-hash
		assertEquals("4bf619864f6f1875897159e5194cbb301020760bf35fbab9d541a6a1995be5b7",Utils.byteArrayToHexString(Hash.hashWithSalt("hello", HashSalt.ACCOUNT_ADDRESS).getBytes()));
	}
	
}
