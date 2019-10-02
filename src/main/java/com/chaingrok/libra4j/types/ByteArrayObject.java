package com.chaingrok.libra4j.types;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.chaingrok.libra4j.misc.LCSInterface;
import com.chaingrok.libra4j.misc.Libra4jError;
import com.chaingrok.libra4j.misc.Libra4jLog.Type;
import com.chaingrok.libra4j.misc.Utils;
import com.google.protobuf.ByteString;

public abstract class ByteArrayObject implements LCSInterface<ByteArrayObject> {
	
	protected byte[] bytes;
	
	public ByteArrayObject(String hex) {
		this(Utils.hexStringToByteArray(hex));
	}
		
	public ByteArrayObject(ByteString byteString) {
		this(byteString.toByteArray());
	}
	
	public ByteArrayObject(byte[] bytes) {
		this.bytes = bytes;
	}
	
	public byte[] getBytes() {
		return bytes;
	}
	
	public ByteString getByteString() {
		return ByteString.copyFrom(bytes);
	}

	@Override
	public final ByteArrayOutputStream serializeToLCS(ByteArrayObject object) {
		return serializeToLCS(object,null);
	}

	@Override
	public final ByteArrayOutputStream serializeToLCS(ByteArrayObject object, ByteArrayOutputStream bos) {
		if (bos == null) {
			bos = new ByteArrayOutputStream();
		}
		if (bytes != null) {
			try {
				bos.write(bytes);
			} catch (IOException e) {
				new Libra4jError(Type.JAVA_ERROR,e);
			}
		}
		return bos;
	}

	@Override
	public final ByteArrayObject deserializeFromLCS(ByteArrayInputStream bis, UInt32 length) {
		if (bis != null) {
			if (length != null) {
				bis.read(bytes,0,(int)(long)length.getAsLong());
			} else {
				new Libra4jError(Type.MISSING_DATA,"length cannot be null");
			}
		} else {
			new Libra4jError(Type.MISSING_DATA,"byte input stream cannot be null");
		}
		return null;
	}
	
	
	@Override
	public boolean equals(Object object) {
		boolean result = false;
		if (object != null) {
			if (this.getClass().equals(object.getClass())) {
				ByteArrayObject byteArrayObject = (ByteArrayObject)object;
				byte[] thisBytes = getBytes();
				byte[] objBytes = byteArrayObject.getBytes();
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
	
	@Override
	public String toString() {
		String result = "";
		result += Utils.byteArrayToHexString(bytes);
		return result;
	}

}
