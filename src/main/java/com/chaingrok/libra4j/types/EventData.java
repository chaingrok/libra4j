package com.chaingrok.libra4j.types;

import java.math.BigInteger;

import com.chaingrok.lib.ChaingrokError;
import com.chaingrok.lib.UInt64;
import com.chaingrok.lib.Utils;
import com.chaingrok.lib.ChaingrokLog.Type;
import com.google.protobuf.ByteString;

public class EventData extends ByteArray {
	
	public static final int ADDRESS_LENGTH_LENGTH = 4;

	public EventData(ByteString byteString) {
		super(byteString);
	}
	
	@Override
	public String toString() {
		return toString(null);
	}
	
	
	public String toString(Path.Type type) {
		String result = "";
		byte[] bytes = super.getBytes();
		if (type == null) {
			result = Utils.byteArrayToHexString(getBytes());
		} else {
			switch(type) {
				case SENT_EVENTS_COUNT:
				case RECEIVED_EVENTS_COUNT:
					int expectedLength = UInt64.BYTE_LENGTH + ADDRESS_LENGTH_LENGTH + AccountAddress.BYTE_LENGTH;
					if (bytes.length == expectedLength) {
						byte[] amount = new byte[UInt64.BYTE_LENGTH];
						System.arraycopy(bytes, 0,amount, 0,amount.length);
						byte[] addrLengthBytes = new byte[ADDRESS_LENGTH_LENGTH]; //seems to be length of following address
						System.arraycopy(bytes, amount.length, addrLengthBytes, 0,addrLengthBytes.length);
						BigInteger addrLength = Utils.byteArraytoBigInt(Utils.reverseByteOrder(addrLengthBytes));
						if (addrLength.longValue() != AccountAddress.BYTE_LENGTH) {
							new ChaingrokError(Type.INVALID_LENGTH, addrLength.longValue()  + " <> " + AccountAddress.BYTE_LENGTH);
						}
						byte[] address = new byte[AccountAddress.BYTE_LENGTH];
						System.arraycopy(bytes, amount.length + addrLengthBytes.length, address,0,address.length);
						result += " type: " + type  +  " - amount: " + new UInt64(Utils.reverseByteOrder(amount)) + " - address: " + new AccountAddress(address);
					}  else {
						result += Utils.byteArrayToHexString(bytes);
						new ChaingrokError(ChaingrokError.Type.INVALID_LENGTH, "type: " + type + " - " + expectedLength + " <> " + bytes.length + " - (" + Utils.byteArrayToHexString(bytes) + ")");
					}
					break; 
				case UNKNOWN:
					result += Utils.byteArrayToHexString(bytes);
					break;
				default:
					result += Utils.byteArrayToHexString(bytes);
					new ChaingrokError(ChaingrokError.Type.UNKNOWN_VALUE,"Should not happen");
					break;
			}
		}
		return result;
	}

}
