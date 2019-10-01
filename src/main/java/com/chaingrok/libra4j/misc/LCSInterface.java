package com.chaingrok.libra4j.misc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.chaingrok.libra4j.types.UInt32;

public interface LCSInterface<T> {
	
	public ByteArrayOutputStream serializeToLCS(T object); 
	
	public ByteArrayOutputStream serializeToLCS(T object,ByteArrayOutputStream bos); 
	
	public T deserializeFromLCS(ByteArrayInputStream bis, UInt32 length);
	
}
