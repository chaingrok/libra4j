package com.chaingrok.libra4j.types;

import java.math.BigInteger;

import com.google.protobuf.ByteString;

public class UInt64 extends UInt {
	
	public static final int BYTE_LENGTH = 8;
	public static final BigInteger MAX_VALUE = new BigInteger("18446744073709551615"); // 18 446 744 073 709 551 615
	
	
	public UInt64(ByteString byteString) {
		super(byteString);
	}
	
	public UInt64(byte[] bytes) {
		super(bytes);
	}
	
	public UInt64(Long value) {
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

	
