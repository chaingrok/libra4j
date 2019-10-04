package com.chaingrok.libra4j.types;

import com.chaingrok.libra4j.misc.LCSInterface;
import com.chaingrok.libra4j.misc.LCSProcessor;
import com.chaingrok.libra4j.misc.Libra4jError;

public class WriteOp extends ByteArray implements LCSInterface {
	
	private Boolean isValue = null;
	private Type type = null;
	
	public WriteOp(byte[] bytes) {
		super(bytes);
	}
	
	public Boolean getIsValue() {
		return isValue;
	}
	
	public void setIsValue(Boolean isValue) {
		this.isValue = isValue;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}
	
	public enum Type implements LCSInterface {
		
		WRITE(0),
		DELETE(1),
		UNRECOGNIZED(-1)
		
		;
		
		int opType = -1;
		
		private Type(int opType) {
			this.opType = opType;
		}
		
		public int getOpType() {
			return opType;
		}
		
		public static Type get(int opType) {
			Type result = null;
			for (Type type : Type.values()) {
				if (type.opType == opType) {
					result = type;
					break;
				}
			}
			if (result == Type.UNRECOGNIZED) {
				new Libra4jError(Libra4jError.Type.UNKNOWN_VALUE, "type: " + Type.UNRECOGNIZED + "found");
			}
			if (result == null) {
				new Libra4jError(Libra4jError.Type.UNKNOWN_VALUE, "unrecognized opType: " + opType);
				result = Type.UNRECOGNIZED;
			}
			return result;
		}
		
		
		@Override
		public LCSProcessor encodeToLCS(LCSProcessor lcsProcessor) {
			UInt32 uint32 = null;
			uint32 = new UInt32(this.getOpType());
			lcsProcessor.encode(uint32);
			return lcsProcessor;
		}

	}

	@Override
	public LCSProcessor encodeToLCS(LCSProcessor lcsProcessor) {
		UInt32 uint32 = null;
		uint32 = new UInt32(getType().getOpType());
		lcsProcessor.encode(uint32);
		lcsProcessor.encode(getBytes());
		return lcsProcessor;
	}
	
}
