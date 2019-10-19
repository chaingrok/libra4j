package com.chaingrok.libra4j.types;

import com.chaingrok.lib.ChaingrokError;
import com.chaingrok.lib.Utils;
import com.chaingrok.lib.ChaingrokLog.Type;
import com.google.protobuf.ByteString;

public class ValidatorId extends ByteArray {
	
	public static final int BYTE_LENGTH = 32;
	
	public ValidatorId(ByteString byteString) {
		super(byteString);
		if (getBytes().length != BYTE_LENGTH) {
			new ChaingrokError(Type.INVALID_LENGTH,"invalid validator id length: " + getBytes().length + " <> " + BYTE_LENGTH + " (" + Utils.byteArrayToHexString(bytes) + ")");
		}
	}
	
}
