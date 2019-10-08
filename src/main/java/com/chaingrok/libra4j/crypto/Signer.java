package com.chaingrok.libra4j.crypto;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;

import org.bouncycastle.jcajce.provider.digest.SHA3;

import com.chaingrok.libra4j.misc.Libra4jException;
import com.chaingrok.libra4j.types.Signature;
import com.chaingrok.libra4j.types.Transaction;

public class Signer {
	
	public static final String DEFAULT_ALGORITHM = KeyPair.ED25519;
	public static final String LIBRA_HASH_SUFFIX = "RawTransaction@@$$LIBRA$$@@"; // cf crypto/legacy_crypto/src/hash.rs
	
	private String cryptoAlgorithm = null;
	private PrivateKey privateKey = null;
	private PublicKey publicKey = null;
	
	public Signer(PrivateKey privateKey) {
		this(DEFAULT_ALGORITHM,privateKey);
	}
	
	public Signer(String cryptoAlgorithm,PrivateKey privateKey) {
		this.cryptoAlgorithm = cryptoAlgorithm;
		this.privateKey = privateKey;
	}
	
	public Signer(byte[] publicKeyBytes) {
		this(DEFAULT_ALGORITHM,KeyPair.publicKeyFromByteArray(publicKeyBytes));
	}
	
	public Signer(PublicKey publicKey) {
		this(DEFAULT_ALGORITHM,publicKey);
	}
	
	public Signer(String cryptoAlgorithm,PublicKey publicKey) {
		this.cryptoAlgorithm = cryptoAlgorithm;
		this.publicKey = publicKey;
	}
	
	public Signer(PrivateKey privateKey,PublicKey publicKey) {
		this(DEFAULT_ALGORITHM,privateKey,publicKey);
	}
	
	public Signer(String cryptoAlgorithm,PrivateKey privateKey,PublicKey publicKey) {
		this.cryptoAlgorithm = cryptoAlgorithm;
		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}
	
	public String getCryptoAlgorithm() {
		return cryptoAlgorithm;
	}
	
	public byte[] signRawTransaction(Transaction rawTransaction, PrivateKey privateKey) {
        SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest256();
        byte[] hashSuffix = digestSHA3.digest(LIBRA_HASH_SUFFIX.getBytes());
        /*
        byte[] transactionBytes = rawTransaction.toByteArray();
        byte[] saltDigestAndTransaction = new byte[hashSuffix.length + transactionBytes.length];
        System.arraycopy(hashSuffix, 0, saltDigestAndTransaction, 0, hashSuffix.length);
        System.arraycopy(transactionBytes, 0, saltDigestAndTransaction, hashSuffix.length, transactionBytes.length);
        return sign(transactionBytes);
        */
        return null;
    }
	
	
	public byte[] sign(byte[] contentToSign) {
		byte[] result = null;
        java.security.Signature signature = null;
		try {
			signature = java.security.Signature.getInstance(this.cryptoAlgorithm,"BC");
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			throw new Libra4jException(e);
		}
        try {
			signature.initSign(privateKey);
		} catch (InvalidKeyException e) {
			throw new Libra4jException(e);
		}
        try {
			signature.update(contentToSign);
		} catch (SignatureException e) {
			throw new Libra4jException(e);
		}
        try {
			result = signature.sign();
		} catch (SignatureException e) {
			throw new Libra4jException(e);
		}
        return result;
	}
	
	public boolean verify(Signature signature, byte[] content) {
		return verify(signature.getBytes(),content);
	}
	
	public boolean verify(byte[] signed, byte[] content) {
		boolean result = false;
		java.security.Signature signature = null;
		try {
			signature = java.security.Signature.getInstance(this.cryptoAlgorithm,"BC");
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			throw new Libra4jException(e);
		}
		try {
			signature.initVerify(publicKey);
		} catch (InvalidKeyException e) {
			throw new Libra4jException(e);
		}
		try {
			signature.update(content);
		} catch (SignatureException e) {
			throw new Libra4jException(e);
		}
		try {
			result = signature.verify(signed);
		} catch (SignatureException e) {
			throw new Libra4jException(e);
		}
		return result;
	}

}
