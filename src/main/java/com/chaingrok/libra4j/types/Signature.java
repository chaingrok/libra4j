package com.chaingrok.libra4j.types;

import com.chaingrok.lib.ChaingrokError;
import com.chaingrok.lib.Utils;
import com.chaingrok.lib.ChaingrokLog.Type;

import com.google.protobuf.ByteString;

public class Signature extends ByteArray {
	
	public static final int BYTE_LENGTH = 64;
	
	public Signature(ByteString byteString) {
		this(byteString.toByteArray());
	}
	
	public Signature(byte[] bytes) {
		super(bytes);
		if (bytes == null) {
			new ChaingrokError(Type.NULL_DATA,"input byte array cannot be null for signature");
		} else {
			if (bytes.length != BYTE_LENGTH)  {
				new ChaingrokError(Type.INVALID_LENGTH,"invalid length for signature: " + bytes.length + " <> " + BYTE_LENGTH + " (" + Utils.byteArrayToHexString(bytes) + ")");
			}
		}
	}
	
	public boolean isValid() {
		boolean result = false;
		if (bytes != null) {
			result = (bytes.length == BYTE_LENGTH);
		}
		return result;
	}
}
