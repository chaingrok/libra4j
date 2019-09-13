package com.chaingrok.libra4j.types;

import com.chaingrok.libra4j.misc.Libra4jError;
import com.chaingrok.libra4j.misc.Libra4jException;
import com.chaingrok.libra4j.misc.Libra4jLog.Type;
import com.chaingrok.libra4j.misc.Utils;
import com.google.protobuf.ByteString;

public class Signature {
	
	public static final int BYTE_LENGTH = 64;
	
	private byte[] bytes = null;
	
	public Signature(ByteString byteString) {
		this(byteString.toByteArray());
	}
	
	public Signature(byte[] bytes) {
		if (bytes.length != BYTE_LENGTH)  {
			new Libra4jError(Type.INVALID_LENGTH,"invalid length for signature: " + bytes.length + " <> " + BYTE_LENGTH + " (" + Utils.byteArrayToHexString(bytes) + ")");
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
