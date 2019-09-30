package com.chaingrok.libra4j.types;

import java.math.BigInteger;

import com.google.protobuf.ByteString;

public class UInt32 extends UInt  {
	
	public static final int BYTE_LENGTH = 4;
	public static final BigInteger MAX_VALUE = new BigInteger("4294967295"); // 4 294 967 295

	public UInt32(ByteString byteString) {
		super(byteString);
	}
	
	public UInt32(byte[] bytes) {
		super(bytes);
	}
	
	public UInt32(Long value) {
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
