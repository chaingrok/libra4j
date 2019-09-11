package com.chaingrok.libra4j.types;

import com.chaingrok.libra4j.misc.Libra4jException;
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
			throw new Libra4jException("invalid length for signature: " + bytes.length + " <> " + BYTE_LENGTH);
		}
		this.bytes = bytes;
	}

	public byte[] getBytes() {
		return bytes;
	}
	
	@Override
	public String toString() {
		String result = "";
		result += Utils.byteArrayToHexString(bytes);
		return result;
	}

}
