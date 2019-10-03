package com.chaingrok.libra4j.types;

import com.chaingrok.libra4j.misc.LCSInterface;
import com.chaingrok.libra4j.misc.LCSProcessor;
import com.chaingrok.libra4j.misc.Libra4jError;
import com.chaingrok.libra4j.misc.Libra4jLog.Type;

public enum TransactionPayloadType implements LCSInterface {
	
	PROGRAM(0),
	WRITESET(1),
	SCRIPT(2),
	MODULE(3),
	
	;
	
	private int type = -1;
	
	private TransactionPayloadType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
	
	public static TransactionPayloadType get(int type) {
		TransactionPayloadType result = null;
		for (TransactionPayloadType t:  TransactionPayloadType.values()) {
			if (t.type == type) {
				result = t;
				break;
			}
		}
		if (result == null) {
			new Libra4jError(Type.UNKNOWN_VALUE,"unknown value for payload type: " + type);
		}
		return result;
	}

	@Override
	public LCSProcessor encodeToLCS(LCSProcessor lcsProcessor)  {
		UInt32 uint32 = new UInt32(this.getType());
		lcsProcessor.encode(uint32);
		return lcsProcessor;
	}
	
}
