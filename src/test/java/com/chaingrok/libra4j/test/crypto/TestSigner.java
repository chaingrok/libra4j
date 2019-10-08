package com.chaingrok.libra4j.test.crypto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.crypto.KeyPair;
import com.chaingrok.libra4j.crypto.Signer;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestSigner {
	
	
    @Test
    public void test001CheckSignerAlgo() {
    	KeyPair keyPair = KeyPair.random();
    	Signer signer = new Signer(keyPair.getPrivateKey());
    	assertEquals(Signer.DEFAULT_ALGORITHM,signer.getCryptoAlgorithm());
    }
	
	@Test
    public void test002ValidateSignatureOneSigner() {
    	KeyPair keyPair = KeyPair.random();
    	Signer signer = new Signer(keyPair.getPrivateKey(),keyPair.getPublicKey());
    	//
    	String content = "foo";
    	byte[] signature = signer.sign(content.getBytes());
    	assertNotNull(signature);
    	assertEquals(64,signature.length);
    	//
    	assertTrue(signer.verify(signature, content.getBytes()));
    }
    
    
    @Test
    public void test003ValidateSignatureTwoSigners() {
    	KeyPair keyPair = KeyPair.random();
    	Signer signer = new Signer(keyPair.getPrivateKey());
    	//
    	String content = "foo";
    	String content2 = "bar";
    	byte[] signature = signer.sign(content.getBytes());
    	assertNotNull(signature);
    	assertEquals(64,signature.length);
    	//
    	Signer signer2 = new Signer(keyPair.getPublicKey());
    	assertFalse(signer2.verify(signature, content2.getBytes()));
    }
    
    @Test
    public void test004RejectSignatureOneSigner() {
    	KeyPair keyPair = KeyPair.random();
    	Signer signer = new Signer(keyPair.getPrivateKey(),keyPair.getPublicKey());
    	//
    	String content = "foo";
    	String content2 = "bar";
    	byte[] signature = signer.sign(content.getBytes());
    	assertNotNull(signature);
    	assertEquals(64,signature.length);
    	//
    	assertFalse(signer.verify(signature, content2.getBytes()));
    }
    
    @Test
    public void test005RejectSignatureTwoSigners() {
    	KeyPair keyPair = KeyPair.random();
    	Signer signer = new Signer(keyPair.getPrivateKey(),keyPair.getPublicKey());
    	//
    	String content = "foo";
    	String content2 = "bar";
    	byte[] signature = signer.sign(content.getBytes());
    	assertNotNull(signature);
    	assertEquals(64,signature.length);
    	//
    	Signer signer2 = new Signer(keyPair.getPublicKey());
    	assertFalse(signer2.verify(signature, content2.getBytes()));
    }
    
}
