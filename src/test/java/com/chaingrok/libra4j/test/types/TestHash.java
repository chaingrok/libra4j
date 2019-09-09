package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertArrayEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.misc.Utils;
import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.Hash;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestHash extends TestClass {
	
	@Test
	public void test001_hashStandardValuess() {
		// 0 address hash
		String zero32Bytes = "0000000000000000000000000000000000000000000000000000000000000000";
		byte[] hashZero32 = Utils.hexStringToByteArray("9e6291970cb44dd94008c79bcaf9d86f18b4b49ba5b2a04781db7199ed3b9e4e");
		Hash hash = Hash.hash(Utils.hexStringToByteArray(zero32Bytes));
		//System.out.println(Utils.byteArrayToHexString(hash.getBytes()));
		assertArrayEquals(hashZero32,hash.getBytes());	
	}
	
	@Test
	public void test002_hashSamples() {
		//source: crypto/legacy_crypto/src/unit_tests/hash_test.rs
		byte[] hashHello = Utils.hexStringToByteArray("3338be694f50c5f338814986cdf0686453a888b84f424d792af4b9202398f392");
		Hash hash = Hash.hash("hello");
		assertArrayEquals(hashHello,hash.getBytes());
		//
		byte[] hashWorld = Utils.hexStringToByteArray("420baf620e3fcd9b3715b42b92506e9304d56e02d3a103499a3a292560cb66b2");
		hash = Hash.hash("world");
		assertArrayEquals(hashWorld,hash.getBytes());
	}
}
