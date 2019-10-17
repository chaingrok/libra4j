package com.chaingrok.libra4j.types;

import org.libra.grpc.types.TransactionOuterClass.TransactionArgument.ArgType;

import com.chaingrok.lib.ChaingrokError;
import com.chaingrok.lib.ChaingrokLog;
import com.chaingrok.lib.UInt32;
import com.chaingrok.lib.UInt64;
import com.chaingrok.lib.Utils;
import com.chaingrok.libra4j.misc.LCSInterface;
import com.chaingrok.libra4j.misc.LCSProcessor;

public class Argument extends ByteArray implements LCSInterface {
	
	private Type type;
	
	public Argument() {
		super((byte[])null);
	}
	
	public Argument(byte[] bytes) {
		super(bytes);
	}
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	
	public void setString(String string) {
		if (string != null) {
			bytes = string.getBytes();
		}
	}
	
	public String getString() {
		String result = null;
		if (bytes != null) {
			result = new String(bytes);
		}
		return result;
	}
	
	public void setUInt64(UInt64 uint64) {
		if (uint64 != null) {
			bytes = uint64.getBytes();
		}
	}
	
	public UInt64 getUInt64() {
		UInt64 result = null;
		if (bytes != null) {
			result = new UInt64(bytes);
		}
		return result;
	}
	
	public void setAccountAddress(AccountAddress accountAddress) {
		if (accountAddress != null) {
			bytes = accountAddress.getBytes();
		}
	}
	
	public AccountAddress getAccountAddress() {
		AccountAddress result = null;
		if (bytes != null) {
			result = new AccountAddress(bytes);
		}
		return result;
	}

	@Override
	public String toString() {
		String result = "";
		String dataStr = "";
		switch (type) {
			case U64:
				dataStr = (new UInt64(bytes)).getAsLong() +" (" + Utils.byteArrayToHexString(bytes) + ")";
				break;
			case STRING:
				dataStr = new String(bytes) + " (" + Utils.byteArrayToHexString(bytes) + ")";
				break;
			default:
				dataStr = Utils.byteArrayToHexString(bytes);
				break;
		}
		result += "argument: type = " +type + " - data = " + dataStr;
		return result;
	}
	
	@Override
	public LCSProcessor encodeToLCS(LCSProcessor encoder) {
		if (encoder != null) {
			encoder.encode(type);
			switch(type) {
			case U64:
				encoder.encode(getUInt64());
				break;
			case ADDRESS:
				encoder.encode(getAccountAddress());
				break;
			case STRING:
				encoder.encode(getString());
				break;
			case BYTE_ARRAY:
				encoder.encode(bytes);
				break;
			default:
				new ChaingrokError(ChaingrokLog.Type.UNKNOWN_VALUE,"unknown argument type to encode: " + getType() + " - " + Utils.byteArrayToHexString(bytes));
				encoder.encode(bytes); //best effort...
				break;
			}
		}
		return encoder;
	}
	
	public static Argument decode(LCSProcessor decoder) {
		Argument result = null;
		if (decoder != null) {
			result  = new Argument();
			Type argumentType = decoder.decodeArgumentType();
			result.setType(argumentType);
			switch(argumentType) {
				case U64:
					UInt64 uint64 = decoder.decodeUInt64();
					result.setUInt64(uint64);
					break;
				case ADDRESS:
					AccountAddress accountAddress = decoder.decodeAccountAddress();
					result.setAccountAddress(accountAddress);
					break;
				case STRING:
					String string = decoder.decodeString();
					result.setString(string);
					break;
				case BYTE_ARRAY:
					byte[] bytes = decoder.decodeByteArray();
					result.setBytes(bytes);
					break;
				default:
					break;
			}
		}
		return result;
	}
	
	public enum Type implements LCSInterface {
		
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
		
		public ArgType getArgType() {
			return argType;
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
				new ChaingrokError(ChaingrokError.Type.UNKNOWN_VALUE, "type: " + ArgType.UNRECOGNIZED + "found");
			}
			if (result == null) {
				new ChaingrokError(ChaingrokError.Type.UNKNOWN_VALUE, "unrecognized ArgType: " + argType);
				result = Type.UNRECOGNIZED;
			}
			return result;
		}
		
		public static Type getFromInt(int argTypeInt) {
			Type result = null;
			for (Type type : Type.values()) {
				if (type.argType.getNumber() == argTypeInt) {
					result = type;
					break;
				}
			}
			if (result == Type.UNRECOGNIZED) {
				new ChaingrokError(ChaingrokError.Type.UNKNOWN_VALUE, "type: " + ArgType.UNRECOGNIZED + "found");
			}
			if (result == null) {
				new ChaingrokError(ChaingrokError.Type.UNKNOWN_VALUE, "unrecognized ArgTypeInt: " + argTypeInt);
				result = Type.UNRECOGNIZED;
			}
			return result;
		}
		
		@Override
		public LCSProcessor encodeToLCS(LCSProcessor lcsProcessor) {
			UInt32 uint32 = null;
			uint32 = new UInt32(this.getArgType().getNumber());
			lcsProcessor.encode(uint32);
			return lcsProcessor;
		}

	}

}
