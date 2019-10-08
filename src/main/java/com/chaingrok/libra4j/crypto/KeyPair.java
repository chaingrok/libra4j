package com.chaingrok.libra4j.crypto;

import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.bouncycastle.jcajce.provider.digest.SHA3;

import com.chaingrok.libra4j.misc.Libra4jException;
import com.chaingrok.libra4j.misc.Utils;
import com.google.protobuf.ByteString;

public class KeyPair {
	
	public static final String ED25519 = "Ed25519";
	
	public static final int UNCOMPRESSED_KEY_LENGTH = 83; //in bytes
	public static final int PUBLIC_KEY_PREFIX_LENGTH = 12; //in bytes
	public static final int PUBLIC_KEY_STRIPPED_LENGTH = 32; //in bytes
	public static final int COMPRESSED_KEY_LENGTH = PUBLIC_KEY_PREFIX_LENGTH  + PUBLIC_KEY_STRIPPED_LENGTH;
	
	static {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}
	
	private PrivateKey privateKey;
	private PublicKey publicKey;
	
	public KeyPair(PrivateKey privateKey,PublicKey publicKey) {
		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}
	
	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}
	
	public String getAlgortihhm() {
		String result = null;
		if (publicKey != null) {
			result = publicKey.getAlgorithm();
		}
		return result;
		
	}
	
	public static String PublicKeyHexToLibraAddress(String pkHex) {
		String result = null;
        PublicKey pk = publicKeyFromHexString(pkHex);
        result = toLibraAddress(pk);
        return result;
    }
	
	public static PublicKey publicKeyFromHexString(String publicKeyHexString) {
        return publicKeyFromByteArray(Utils.hexStringToByteArray(publicKeyHexString));
    }
	
	public static PublicKey publicKeyFromByteString(ByteString pkByteString) {
		return publicKeyFromByteArray(pkByteString.toByteArray());
	}
	
	public static PublicKey publicKeyFromByteArray(byte[] pkBytes) {
    	PublicKey result = null;
        try {
            result = getKeyFactory().generatePublic(new X509EncodedKeySpec(pkBytes));
        } catch (InvalidKeySpecException e) {
        	throw new Libra4jException(e);
        }
        return result;
    }
	
	public static byte[] toLibraAddressBytes(PublicKey publicKey) {
		byte[] result = null;
        SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest256();
        byte[] strippedPublicKey = stripPrefix(publicKey.getEncoded());
        result = digestSHA3.digest(strippedPublicKey);
        return result;
    }
	
	public static byte[] stripPrefix(byte[] publicKeyUnstrippedBytes) {
        byte[] publicKeyStripped = new byte[PUBLIC_KEY_STRIPPED_LENGTH];
        System.arraycopy(publicKeyUnstrippedBytes, PUBLIC_KEY_PREFIX_LENGTH, publicKeyStripped, 0, PUBLIC_KEY_STRIPPED_LENGTH);
        return publicKeyStripped;
    }
	
	public static String toLibraAddress(PublicKey publicKey) {
		String result = null;
        byte[] bytes = toLibraAddressBytes(publicKey);
        result = Utils.byteArrayToHexString(bytes);
        return result;
    }
    
    public static PrivateKey privateKeyFromHexString(String privateKeyHexString) {
		PrivateKey result = null;
        byte[] privateKeyBytes = Utils.hexStringToByteArray(privateKeyHexString);
        try {
            result = getKeyFactory().generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        } catch (InvalidKeySpecException e) {
            throw new Libra4jException(e);
        }
        return result;
    }
    
    public static KeyPair random() {
		// Fpr x509 encoding , see https://stackoverflow.com/questions/22008337/generating-keypair-using-bouncy-castle
		KeyPair result = null;
		KeyPairGenerator keyGen;
		try {
			keyGen = KeyPairGenerator.getInstance(ED25519);
		} catch (NoSuchAlgorithmException e) {
			throw new Libra4jException(e);
		}
        java.security.KeyPair keyPair = keyGen.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        result = new KeyPair(privateKey,publicKey);
		return result;
	}
    
    public static KeyFactory getKeyFactory() {
		KeyFactory result = null;
		try {
            result = KeyFactory.getInstance(ED25519);
        } catch (NoSuchAlgorithmException e) {
            throw new Libra4jException(e);
        }
		return result;
	}
    
    public static boolean validatePair(PrivateKey privateKey, PublicKey publicKey) {
    	boolean result = false;
    	byte[] content = "foo-bar".getBytes();
    	Signer signer = new Signer(ED25519,privateKey,publicKey);
    	byte[] signature = signer.sign(content);
    	result = signer.verify(signature, content);
    	return result;
    }
}
