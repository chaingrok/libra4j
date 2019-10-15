package com.chaingrok.libra4j.types;

import com.chaingrok.lib.ChaingrokError;
import com.chaingrok.lib.ChaingrokLog.Type;

public class EventKey extends ByteArray {
	
	public static final int BYTE_LENGTH = 32;

	public EventKey(byte[] bytes) {
		super(bytes);
		if (bytes == null) {
			new ChaingrokError(Type.NULL_DATA,"input byte array cannot be null for event key");
		} else {
			if (bytes.length != BYTE_LENGTH) {
				new ChaingrokError(Type.INVALID_LENGTH,"invalid event key size: " + bytes.length +  " <> " + BYTE_LENGTH);
			}
		}
	}

}
