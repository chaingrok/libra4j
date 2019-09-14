package com.chaingrok.libra4j.types;

import org.libra.grpc.types.Transaction.TransactionArgument.ArgType;

import com.chaingrok.libra4j.misc.Libra4jError;
import com.chaingrok.libra4j.misc.Utils;

public class Argument {
	
	private Type type;
	private byte[] data;
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	@Override
	public String toString() {
		String result = "";
		String dataStr = "";
		switch (type) {
			case U64:
				dataStr = (new UInt64(data)).getAsLong() +" (" + Utils.byteArrayToHexString(data) + ")";
				break;
			case STRING:
				dataStr = new String(data) + " (" + Utils.byteArrayToHexString(data) + ")";
				break;
			default:
				dataStr = Utils.byteArrayToHexString(data);
				break;
		}
		result += "argument: type = " +type + " - data = " + dataStr;
		return result;
	}
	
	public enum Type {
		
		U64(ArgType.U64),
		ADDRESS(ArgType.ADDRESS),
		STRING(ArgType.STRING),
		BYTE_ARRAY(ArgType.BYTEARRAY),
		UNRECOGNIZED(ArgType.UNRECOGNIZED)
		
		;
		
		ArgType argType = null;
		
		private Type(ArgType argType) {
			this.argType = argType;
		}
		
		public static Type get(ArgType argType) {
			Type result = null;
			for (Type type : Type.values()) {
				if (type.argType.equals(argType)) {
					result = type;
					break;
				}
			}
			if (result == Type.UNRECOGNIZED) {
				new Libra4jError(Libra4jError.Type.UNKNOWN_VALUE, "type: " + ArgType.UNRECOGNIZED + "found");
			}
			if (result == null) {
				new Libra4jError(Libra4jError.Type.UNKNOWN_VALUE, "unrecognized ArgType: " + argType);
				result = Type.UNRECOGNIZED;
			}
			return result;
		}

	}

}
