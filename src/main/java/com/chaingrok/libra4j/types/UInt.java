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
		BigInteger result = null;
		if (bytes != null) {
			result = Utils.byteArraytoBigInt(bytes);
			if (result.compareTo(getMaxValue()) > 0) {
				new Libra4jError(Type.INVALID_VALUE,"UInt value greater than max value: " + result.toString() + " <> " + getMaxValue().toString());
			}
		}
		return result;
	}
	
	public final Long getAsLong() {
		Long result = null;
		BigInteger bigInt = getAsBigInt();
		if (bigInt != null) {
			result = getAsBigInt().longValueExact();
		}
		return result;
	}
	
	public final byte[] getBytes() {
		return bytes;
	}
	
	public final ByteString getByteString() {
		return ByteString.copyFrom(bytes);
	}
	
	@Override
	public final String toString() {
		return getAsBigInt() + "";
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
		} else {
			new Libra4jError(Type.INVALID_LENGTH,"byte array cannot be null");
		}
		this.bytes = bytes;
	}
	
	@Override
	public boolean equals(Object object) {
		boolean result = false;
		if (object != null) {
			if (this.getClass().equals(object.getClass())) {
				UInt uint = (UInt)object;
				byte[] thisBytes = getBytes();
				byte[] objBytes = uint.getBytes();
				if (thisBytes.length == objBytes.length) {
					int length = thisBytes.length;
					boolean delta = false;
					for (int i=0 ; i< length ; ++i) {
						if (thisBytes[i] != objBytes[i]) {
							delta = true;
							break;
						}
					}
					if (!delta) {
						result = true;
					}
				}
			} else {
				new Libra4jError(Type.INVALID_CLASS,"cannot compare objects of different classes: " + this.getClass().getCanonicalName() +  " <> " + object.getClass().getCanonicalName());
			}
		}
		return result;
	}
	
	@Override
	public int hashCode() {
		int result = 0;
		//hoping for randomness in bytes...
		byte[] intBytes = new byte[Integer.BYTES];
		if (bytes.length >=  Integer.BYTES) {
			System.arraycopy(bytes, 0, intBytes, 0,Integer.BYTES);
		} else {
			System.arraycopy(bytes, 0, intBytes, 0,bytes.length);
		}
		result = Utils.byteArrayToInt(intBytes);
		return result;
	}
	
}
