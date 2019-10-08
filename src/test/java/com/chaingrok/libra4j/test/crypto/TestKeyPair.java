package com.chaingrok.libra4j.test.crypto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.crypto.KeyPair;
import com.chaingrok.libra4j.misc.Utils;
import com.chaingrok.libra4j.test.TestData;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestKeyPair {
	
	@Test
    public void test001ValidateTestData() {
    	assertEquals(KeyPair.UNCOMPRESSED_KEY_LENGTH,TestData.TEST_PRIVATE_KEY_HEX1.length()/2);
		assertEquals(KeyPair.COMPRESSED_KEY_LENGTH,TestData.TEST_PUBLIC_KEY_HEX1.length()/2); 
    }
    
    @Test
	public void test002HexToPrivateKeyAndBack() {
        PrivateKey privateKey = KeyPair.privateKeyFromHexString(TestData.TEST_PRIVATE_KEY_HEX1);
        assertNotNull(privateKey);
        assertEquals(KeyPair.UNCOMPRESSED_KEY_LENGTH,privateKey.getEncoded().length);
        assertEquals(TestData.TEST_PRIVATE_KEY_HEX1,Utils.byteArrayToHexString(privateKey.getEncoded()));
    }
    
    @Test
	public void test003HexToPublicKeyAndBack() {
        PublicKey publicKey = KeyPair.publicKeyFromHexString(TestData.TEST_PUBLIC_KEY_HEX1);
        assertNotNull(publicKey);
        assertEquals(KeyPair.COMPRESSED_KEY_LENGTH,publicKey.getEncoded().length);
        assertEquals(TestData.TEST_PUBLIC_KEY_HEX1,Utils.byteArrayToHexString(publicKey.getEncoded()));
    }
    
    @Test
	public void test004PublicKeyToAddressAndBack() {
        PublicKey publicKey = KeyPair.publicKeyFromHexString(TestData.TEST_PUBLIC_KEY_HEX1);
        assertNotNull(publicKey);
        assertEquals(TestData.TEST_ADDRESS1,KeyPair.toLibraAddress(publicKey));
        //
        assertEquals(TestData.TEST_ADDRESS1,KeyPair.PublicKeyHexToLibraAddress(TestData.TEST_PUBLIC_KEY_HEX1));
    }
    
    
    @Test
	public void test005GenerateRandomKeyPair() {
		KeyPair keyPair = KeyPair.random();
		assertEquals(KeyPair.ED25519, keyPair.getAlgortihhm());
		assertNotNull(keyPair.getPrivateKey());
		assertEquals(KeyPair.UNCOMPRESSED_KEY_LENGTH,keyPair.getPrivateKey().getEncoded().length);
		assertNotNull(keyPair.getPublicKey());
		assertEquals(KeyPair.COMPRESSED_KEY_LENGTH,keyPair.getPublicKey().getEncoded().length);
    }
    
    @Test
    public void test006ValidateKeyPairOk() {
    	KeyPair keyPair = KeyPair.random();
    	assertTrue(KeyPair.validatePair(keyPair.getPrivateKey(), keyPair.getPublicKey()));
    	//
    	KeyPair keyPair2 = KeyPair.random();
    	assertFalse(KeyPair.validatePair(keyPair.getPrivateKey(), keyPair2.getPublicKey()));
    }
 
}
