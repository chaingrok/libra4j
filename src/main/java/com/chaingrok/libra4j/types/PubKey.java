package com.chaingrok.libra4j.types;

import com.chaingrok.lib.ChaingrokError;
import com.chaingrok.lib.Libra4jLog.Type;
import com.chaingrok.libra4j.crypto.KeyPair;

public class PubKey extends ByteArray {
	
	public static final int BYTE_LENGTH = KeyPair.PUBLIC_KEY_STRIPPED_LENGTH;

	public PubKey(byte[] bytes) {
		super(bytes);
		if (bytes == null) {
			new ChaingrokError(Type.NULL_DATA,"input byte array cannot be null for piblic key");
		} else {
			if (bytes.length != BYTE_LENGTH) {
				new ChaingrokError(Type.INVALID_LENGTH,"invalid public key size: " + bytes.length +  " <> " + BYTE_LENGTH);
			}
		}
	}

}
