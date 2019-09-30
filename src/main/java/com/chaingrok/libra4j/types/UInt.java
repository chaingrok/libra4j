package com.chaingrok.libra4j.types;

import java.math.BigInteger;

import com.chaingrok.libra4j.misc.Libra4jError;
import com.chaingrok.libra4j.misc.Libra4jException;
import com.chaingrok.libra4j.misc.Utils;
import com.chaingrok.libra4j.misc.Libra4jLog.Type;
import com.google.protobuf.ByteString;

public abstract class UInt {
	
	protected abstract int getLength();
	protected abstract BigInteger getMaxValue();
	
	private byte[] bytes;
	
	public UInt(ByteString byteString) {
		processByteString(byteString);
	}
	
	public UInt(byte[] bytes) {
		processBytes(bytes);
	}
	
	public UInt(Long value) {
		if (value < 0) {
			throw new Libra4jException("UInt cannot be constructed from negative long value: " + value);
		}
		byte[] longBytes = Utils.longToByteArray(value,getLength());
		if (longBytes.length <= getLength()) {
			bytes = new byte[getLength()];
			System.arraycopy(longBytes, 0, bytes, bytes.length-longBytes.length, longBytes.length);
		} else {
			throw new Libra4jException("byte array is too big: " + longBytes.length + " <> " + getLength() + " - " + Utils.byteArrayToHexString(longBytes));
		}
	}
	
	public final BigInteger getAsBigInt() {
		BigInteger result = Utils.byteArraytoBigInt(bytes);
		if (result.compareTo(getMaxValue()) > 0) {
			new Libra4jError(Type.INVALID_VALUE,"UInt value greater than max value: " + result.toString() + " <> " + getMaxValue().toString());
		}
		return result;
	}
	
	public final long getAsLong() {
		return getAsBigInt().longValueExact();
	}
	
	public final byte[] getBytes() {
		return bytes;
	}
	
	public final ByteString getByteString() {
		return ByteString.copyFrom(bytes);
	}
	
	@Override
	public final String toString() {
		return getAsLong() + "";
	}
	
	private void processByteString(ByteString byteString) {
		if (byteString != null) {
			processBytes(byteString.toByteArray());
		} else {
			new Libra4jError(Type.INVALID_LENGTH,"ByteString cannot be null");
		}
	}
	
	private void processBytes(byte[] bytes) {
		if (bytes != null) {
			if (bytes.length != getLength()) {
				new Libra4jError(Type.INVALID_LENGTH,"byte array length is invalid: " + bytes.length + " <> " + getLength());
			}
		}
		else {
			new Libra4jError(Type.INVALID_LENGTH,"byte array cannot be null");
		}
		this.bytes = bytes;
	}
	
}
