package com.chaingrok.libra4j.types;

import com.chaingrok.lib.ChaingrokError;
import com.chaingrok.lib.UInt32;
import com.chaingrok.lib.Utils;
import com.chaingrok.libra4j.misc.LCSInterface;
import com.chaingrok.libra4j.misc.LCSProcessor;

public class WriteOp extends ByteArray implements LCSInterface {
	
	private Type opType = null;
	
	public WriteOp(byte[] bytes) {
		super(bytes);
	}
	
	public void setOpType(Type opType) {
		this.opType = opType;
	}
	
	public Type getOpType() {
		return opType;
	}
	
	public enum Type implements LCSInterface {
		
		DELETE(0),
		WRITE(1),
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
				new ChaingrokError(ChaingrokError.Type.UNKNOWN_VALUE, "type: " + Type.UNRECOGNIZED + "found");
			}
			if (result == null) {
				new ChaingrokError(ChaingrokError.Type.UNKNOWN_VALUE, "unrecognized opType: " + opType);
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
		uint32 = new UInt32(getOpType().getOpType());
		lcsProcessor.encode(uint32);
		lcsProcessor.encode(getBytes());
		return lcsProcessor;
	}
	
	@Override
	public String toString() {
		String result = "";
		result += "op type: " + getOpType();
		String byteHex = "";
		if (getBytes() != null) {
			byteHex = Utils.byteArrayToHexString(getBytes());
		}
		result += " - value: " + byteHex;
		return result; 
	}
	
}
