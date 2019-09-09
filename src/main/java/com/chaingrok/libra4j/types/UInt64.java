package com.chaingrok.libra4j.types;

import java.math.BigInteger;

import com.chaingrok.libra4j.misc.Libra4jException;
import com.chaingrok.libra4j.misc.Utils;

import com.google.protobuf.ByteString;

public class UInt64 {
	
	public static final int BYTE_LENGTH = 8;
	
	public static final BigInteger MAX_VALUE= new BigInteger("18446744073709551615"); // 18 446 744 073 709 551 615
	
	byte[] bytes = null;
	
	public UInt64(ByteString byteString) {
		this(byteString.toByteArray());
	}
	
	public UInt64(byte[] bytes) {
		if (bytes.length != BYTE_LENGTH) {
			throw new Libra4jException("UInt64 length is invalid: " + bytes.length + " <> " + BYTE_LENGTH);
		}
		this.bytes = bytes;
	}
	
	public UInt64(Long value) {
		if (value < 0) {
			throw new Libra4jException("UInt64 cannot be constructed from negative long value: " + value);
		}
		byte[] longBytes = Utils.longToByteArray(value);
		if (longBytes.length <= BYTE_LENGTH) {
			bytes = new byte[BYTE_LENGTH];
			System.arraycopy(longBytes, 0, bytes, bytes.length-longBytes.length, longBytes.length);
		} else {
			throw new Libra4jException("exception (supposidely) unreachable...");
		}
	}
	
	public BigInteger getAsBigInt() {
		return Utils.byteArraytoBigInt(bytes);
	}
	
	public long getAsLong() {
		return getAsBigInt().longValueExact();
	}
	
	public byte[] getBytes() {
		return bytes;
	}
	
	public ByteString getByteString() {
		return ByteString.copyFrom(bytes);
	}
	
	@Override
	public String toString() {
		return getAsLong() + "";
	}
	
}
