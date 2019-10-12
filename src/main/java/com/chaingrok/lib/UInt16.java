package com.chaingrok.lib;

import java.math.BigInteger;

import com.google.protobuf.ByteString;

public class UInt16 extends UInt {
	
	public static final int BYTE_LENGTH = 2;
	public static final BigInteger MAX_VALUE = new BigInteger("65535"); //65 535 

	public UInt16(ByteString byteString) {
		super(byteString);
	}
	
	public UInt16(byte[] bytes) {
		super(bytes);
	}
	
	public UInt16(Long value) {
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
