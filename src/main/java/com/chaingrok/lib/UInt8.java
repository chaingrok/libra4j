package com.chaingrok.lib;

import java.math.BigInteger;

import com.google.protobuf.ByteString;

public class UInt8 extends UInt {
	
	public static final int BYTE_LENGTH = 1;
	public static final BigInteger MAX_VALUE = new BigInteger("255"); 

	public UInt8(ByteString byteString) {
		super(byteString);
	}
	
	public UInt8(byte[] bytes) {
		super(bytes);
	}
	
	public UInt8(Long value) {
		super(value);
	}
	
	@Override
	protected int getLength() {
		return BYTE_LENGTH ;
	}
	
	@Override
	protected BigInteger getMaxValue() {
		return MAX_VALUE;
	}

}
