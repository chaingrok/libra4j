package com.chaingrok.libra4j.misc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.chaingrok.libra4j.misc.Libra4jLog.Type;
import com.chaingrok.libra4j.types.UInt64;

//source: common/canonical_serialization/README.md

public class LCSEncoder {
	
	public static ByteArrayOutputStream encodeBoolean(Boolean b) {
		return encodeBoolean(b,null);
	}
	
	public static ByteArrayOutputStream encodeBoolean(Boolean b,ByteArrayOutputStream bos) {
		if (bos == null) {
			bos = new ByteArrayOutputStream();
		}
		if (b != null) {
			if (b == true) {
				bos.write((byte)0x01);
			} else {
				bos.write((byte)0x00);
			}
		}
		return bos;
	}
	
	public static Boolean decodeBoolean(ByteArrayInputStream bis) {
		Boolean result = null;
		int b = bis.read();
		if (b == 0) {
			result = false;
		} else if (b == 1) {
			result = true;
		} else {
			new Libra4jError(Type.UNKNOWN_VALUE,"boolean must be 0 or 1");
		}
		return result;
	}
	
	public static byte[] encodeString (String l) {
		byte[] result = null;
		return result;
	}
	
	public static String decodeString(ByteArrayInputStream bis) {
		String result = null;
		return result;
	}
	
	public static byte[] encodeUInt64 (UInt64 uint64) {
		byte[] result = null;
		return result;
	}
	
	public static UInt64 decodeLong(ByteArrayInputStream bis) {
		UInt64 result = null;
		return result;
	}

}
