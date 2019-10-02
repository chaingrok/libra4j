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

public class LCSProcessor {
	
	private boolean bosWritten = false;
	private ByteArrayOutputStream bos = null;
	private ByteArrayInputStream bis = null;
	
	private LCSProcessor() {
		this.bos = new ByteArrayOutputStream();
	}
	
	private LCSProcessor(byte[] bytes) {
		this.bis = new ByteArrayInputStream(bytes);
	}
	
	public static LCSProcessor buildEncoder() {
		return new LCSProcessor();
	}
	
	public static LCSProcessor buildDecoder(byte[] bytes) {
		return new LCSProcessor(bytes);
	}
	
	public byte[] build() {
		byte[] result = null;
		if ((bos != null)
				&& (bosWritten == true)) {
			result = bos.toByteArray();
		}
		return result;
	}
	
	public ByteArrayOutputStream getBos() {
		return bos;
	}
	
	public ByteArrayInputStream getBis() {
		return bis;
	}
	
	public LCSProcessor encode(Boolean b) {
		if (b != null) {
			if (b == true) {
				byte[] bytes = {0x01};
				write(bytes);
			} else {
				byte[] bytes = {0x00};
				write(bytes);
			}
		}
		return this;
	}
	
	public Boolean decodeBoolean() {
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
	
	public  LCSProcessor encode(String string) {
		if (string != null) {
			write(string.getBytes());
		}
		return this;
	}
	
	public String decodeString(UInt32 length) {
		return decodeString((int)(long)length.getAsLong());
	}
	
	public String decodeString(int length) {
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
	
	public LCSProcessor encode(UInt64 uint64) {
		if (uint64 != null) {
			write(Utils.reverseByteOrder(uint64.getBytes()));
		}
		return this;
	}
	
	public UInt64 decodeUint64() {
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
	
	public LCSProcessor encode (UInt32 uint32) {
		if (uint32 != null) {
			write(Utils.reverseByteOrder(uint32.getBytes()));
		}
		return this;
	}
	
	public UInt32 decodeUint32() {
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
	
	public LCSProcessor encode(UInt16 uint16) {
		if (uint16 != null) {
			write(Utils.reverseByteOrder(uint16.getBytes()));
		}
		return this;
	}
	
	public UInt16 decodeUint16() {
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

	public LCSProcessor encode(UInt8 uint8) {
		if (uint8 != null) {
			write(uint8.getBytes());
		}
		return this;
	}
	
	public UInt8 decodeUint8() {
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
	
	private void write(byte[] bytes) {
		try {
			bos.write(bytes);
		} catch (IOException e) {
			new Libra4jError(Type.JAVA_ERROR,e);
		}
		bosWritten = true;
	}
	
}
