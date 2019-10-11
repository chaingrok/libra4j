package com.chaingrok.libra4j.types;

import com.chaingrok.libra4j.crypto.KeyPair;
import com.chaingrok.libra4j.misc.Libra4jError;
import com.chaingrok.libra4j.misc.Libra4jLog.Type;

public class PubKey extends ByteArray {
	
	public static final int BYTE_LENGTH = KeyPair.PUBLIC_KEY_STRIPPED_LENGTH;

	public PubKey(byte[] bytes) {
		super(bytes);
		if (bytes == null) {
			new Libra4jError(Type.NULL_DATA,"input byte array cannot be null for piblic key");
		} else {
			if (bytes.length != BYTE_LENGTH) {
				new Libra4jError(Type.INVALID_LENGTH,"invalid public key size: " + bytes.length +  " <> " + BYTE_LENGTH);
			}
		}
	}

}
