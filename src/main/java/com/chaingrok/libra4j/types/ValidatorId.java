package com.chaingrok.libra4j.types;

import com.chaingrok.libra4j.misc.Libra4jError;
import com.chaingrok.libra4j.misc.Libra4jLog.Type;
import com.chaingrok.libra4j.misc.Utils;
import com.google.protobuf.ByteString;

public class ValidatorId extends ByteArrayObject {
	
	public static final int BYTE_LENGTH = 32;
	
	public ValidatorId(ByteString byteString) {
		super(byteString);
		if (getBytes().length != BYTE_LENGTH) {
			new Libra4jError(Type.INVALID_LENGTH,"invalid validator id length: " + getBytes().length + " <> " + BYTE_LENGTH + "(" + Utils.byteArrayToHexString(bytes) + ")");
		}
	}
	
}
