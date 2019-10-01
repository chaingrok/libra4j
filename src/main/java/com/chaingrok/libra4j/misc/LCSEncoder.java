package com.chaingrok.libra4j.misc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.chaingrok.libra4j.misc.Libra4jLog.Type;
import com.chaingrok.libra4j.types.UInt16;
import com.chaingrok.libra4j.types.UInt32;
import com.chaingrok.libra4j.types.UInt64;
import com.chaingrok.libra4j.types.UInt8;

//source: https://github.com/libra/libra/tree/master/common/canonical_serialization

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
		if ((bis != null) 
				&& (bis.available() >= 1)){
			int b = bis.read();
			if (b == 0) {
				result = false;
			} else if (b == 1) {
				result = true;
			} else {
				new Libra4jError(Type.UNKNOWN_VALUE,"boolean must be 0 or 1");
			}
		}
		return result;
	}
	
	public static ByteArrayOutputStream encodeString(String string) {
		return encodeString(string,null);
	}
	
	public static ByteArrayOutputStream encodeString(String string,ByteArrayOutputStream bos) {
		if (bos == null) {
			bos = new ByteArrayOutputStream();
		}
		if (string != null) {
			byte[] bytes = string.getBytes();
			try {
				bos.write(bytes);
			} catch (IOException e) {
				new Libra4jError(Type.JAVA_ERROR,e);
			}
		}
		return bos;
	}
	
	public static String decodeString(ByteArrayInputStream bis, UInt32 length) {
		return decodeString(bis,(int)(long)length.getAsLong());
	}
	
	public static String decodeString(ByteArrayInputStream bis, int length) {
		String result = null;
		if (bis != null) {
			byte[] bytes = new byte[length];
			int count = bis.read(bytes,0,length);
			if (count == length) {
				try {
					result = new String(bytes,"UTF8");
				} catch (UnsupportedEncodingException e) {
					new Libra4jError(Type.UNKNOWN_VALUE,e);
				}
			} else {
				new Libra4jError(Type.INVALID_LENGTH,"byte buffer read did not return proper number of bytes: " + count + " <> " + length);
			}
		}
		return result;
	}
	
	public static ByteArrayOutputStream encodeUInt64(UInt64 uint64) {
		return encodeUInt64(uint64,null);
	}
	
	public static ByteArrayOutputStream encodeUInt64(UInt64 uint64,ByteArrayOutputStream bos) {
		if (bos == null) {
			bos = new ByteArrayOutputStream();
		}
		if (uint64 != null) {
			try {
				bos.write(Utils.reverseByteOrder(uint64.getBytes()));
			} catch (IOException e) {
				new Libra4jError(Type.JAVA_ERROR,e);
			}	
		}
		return bos;
	}
	
	public static UInt64 decodeUint64(ByteArrayInputStream bis) {
		UInt64 result = null;
		if (bis !=null) {
			byte[] bytes = new byte[UInt64.BYTE_LENGTH];
			int count = bis.read(bytes, 0,UInt64.BYTE_LENGTH);
			if (count == UInt64.BYTE_LENGTH) {
				result = new UInt64(Utils.reverseByteOrder(bytes));
			} else {
				new Libra4jError(Type.INVALID_LENGTH,"byte buffer read did not return proper number of bytes: " + count + " <> " + UInt64.BYTE_LENGTH);
			}
		}
		return result;
	}
	
	
	
	public static ByteArrayOutputStream encodeUInt32(UInt32 uint32) {
		return encodeUInt32(uint32,null);
	}
	
	public static ByteArrayOutputStream encodeUInt32 (UInt32 uint32,ByteArrayOutputStream bos) {
		if (bos == null) {
			bos = new ByteArrayOutputStream();
		}
		if (uint32 != null) {
			try {
				bos.write(Utils.reverseByteOrder(uint32.getBytes()));
			} catch (IOException e) {
				new Libra4jError(Type.JAVA_ERROR,e);
			}	
		}
		return bos;
	}
	
	public static UInt32 decodeUint32(ByteArrayInputStream bis) {
		UInt32 result = null;
		if (bis !=null) {
			byte[] bytes = new byte[UInt32.BYTE_LENGTH];
			int count = bis.read(bytes, 0,UInt32.BYTE_LENGTH);
			if (count == UInt32.BYTE_LENGTH) {
				result = new UInt32(Utils.reverseByteOrder(bytes));
			} else {
				new Libra4jError(Type.INVALID_LENGTH,"byte buffer read did not return proper number of bytes: " + count + " <> " + UInt32.BYTE_LENGTH);
			}
		}
		return result;
	}
	
	public static ByteArrayOutputStream encodeUInt16(UInt16 uint16) {
		return encodeUInt16(uint16,null);
	}
	
	public static ByteArrayOutputStream encodeUInt16 (UInt16 uint16,ByteArrayOutputStream bos) {
		if (bos == null) {
			bos = new ByteArrayOutputStream();
		}
		if (uint16 != null) {
			try {
				bos.write(Utils.reverseByteOrder(uint16.getBytes()));
			} catch (IOException e) {
				new Libra4jError(Type.JAVA_ERROR,e);
			}	
		}
		return bos;
	}
	
	public static UInt16 decodeUint16(ByteArrayInputStream bis) {
		UInt16 result = null;
		if (bis !=null) {
			byte[] bytes = new byte[UInt16.BYTE_LENGTH];
			int count = bis.read(bytes, 0,UInt16.BYTE_LENGTH);
			if (count == UInt16.BYTE_LENGTH) {
				result = new UInt16(Utils.reverseByteOrder(bytes));
			} else {
				new Libra4jError(Type.INVALID_LENGTH,"byte buffer read did not return proper number of bytes: " + count + " <> " + UInt16.BYTE_LENGTH);
			}
		}
		return result;
	}
	
	public static ByteArrayOutputStream encodeUInt8(UInt8 uint8) {
		return encodeUInt8(uint8,null);
	}
	
	public static ByteArrayOutputStream encodeUInt8 (UInt8 uint8,ByteArrayOutputStream bos) {
		if (bos == null) {
			bos = new ByteArrayOutputStream();
		}
		if (uint8 != null) {
			try {
				bos.write(uint8.getBytes());
			} catch (IOException e) {
				new Libra4jError(Type.JAVA_ERROR,e);
			}	
		}
		return bos;
	}
	
	public static UInt8 decodeUint8(ByteArrayInputStream bis) {
		UInt8 result = null;
		if (bis !=null) {
			byte[] bytes = new byte[UInt8.BYTE_LENGTH];
			int count = bis.read(bytes, 0,UInt8.BYTE_LENGTH);
			if (count == UInt8.BYTE_LENGTH) {
				result = new UInt8(bytes);
			} else {
				new Libra4jError(Type.INVALID_LENGTH,"byte buffer read did not return proper number of bytes: " + count + " <> " + UInt8.BYTE_LENGTH);
			}
		}
		return result;
	}


}
