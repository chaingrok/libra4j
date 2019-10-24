package com.chaingrok.libra4j.crypto;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;

import com.chaingrok.lib.ChaingrokException;
import com.chaingrok.libra4j.types.Signature;

public class Signer {
	
	public static final String DEFAULT_ALGORITHM = KeyPair.ED25519;
	
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
	
	public byte[] sign(byte[] contentToSign) {
		byte[] result = null;
        java.security.Signature signature = null;
		try {
			signature = java.security.Signature.getInstance(this.cryptoAlgorithm,"BC");
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			throw new ChaingrokException(e);
		}
        try {
			signature.initSign(privateKey);
		} catch (InvalidKeyException e) {
			throw new ChaingrokException(e);
		}
        try {
			signature.update(contentToSign);
		} catch (SignatureException e) {
			throw new ChaingrokException(e);
		}
        try {
			result = signature.sign();
		} catch (SignatureException e) {
			throw new ChaingrokException(e);
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
			throw new ChaingrokException(e);
		}
		try {
			signature.initVerify(publicKey);
		} catch (InvalidKeyException e) {
			throw new ChaingrokException(e);
		}
		try {
			signature.update(content);
		} catch (SignatureException e) {
			throw new ChaingrokException(e);
		}
		try {
			result = signature.verify(signed);
		} catch (SignatureException e) {
			throw new ChaingrokException(e);
		}
		return result;
	}

}
