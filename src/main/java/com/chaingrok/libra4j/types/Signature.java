package com.chaingrok.libra4j.types;

import com.chaingrok.lib.ChaingrokError;
import com.chaingrok.lib.Utils;
import com.chaingrok.lib.Libra4jLog.Type;
import com.google.protobuf.ByteString;

public class Signature {
	
	public static final int BYTE_LENGTH = 64;
	
	private byte[] bytes = null;
	
	public Signature(ByteString byteString) {
		this(byteString.toByteArray());
	}
	
	public Signature(byte[] bytes) {
		if (bytes == null) {
			new ChaingrokError(Type.NULL_DATA,"input byte array cannot be null for signature");
		} else {
			if (bytes.length != BYTE_LENGTH)  {
				new ChaingrokError(Type.INVALID_LENGTH,"invalid length for signature: " + bytes.length + " <> " + BYTE_LENGTH + " (" + Utils.byteArrayToHexString(bytes) + ")");
			}
		}
		this.bytes = bytes;
	}

	public byte[] getBytes() {
		return bytes;
	}
	
	public boolean isValid() {
		boolean result = false;
		if (bytes != null) {
			result = (bytes.length == BYTE_LENGTH);
		}
		return result;
	}
	
	@Override
	public String toString() {
		String result = "";
		result += Utils.byteArrayToHexString(bytes);
		return result;
	}

}
