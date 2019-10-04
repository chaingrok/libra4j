package com.chaingrok.libra4j.misc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.chaingrok.libra4j.misc.Libra4jLog.Type;
import com.chaingrok.libra4j.types.AccessPath;
import com.chaingrok.libra4j.types.AccountAddress;
import com.chaingrok.libra4j.types.Argument;
import com.chaingrok.libra4j.types.Code;
import com.chaingrok.libra4j.types.Module;
import com.chaingrok.libra4j.types.Program;
import com.chaingrok.libra4j.types.TransactionPayloadType;
import com.chaingrok.libra4j.types.UInt16;
import com.chaingrok.libra4j.types.UInt32;
import com.chaingrok.libra4j.types.UInt64;
import com.chaingrok.libra4j.types.UInt8;

//source: https://github.com/libra/libra/tree/master/common/canonical_serialization

public class LCSProcessor {
	
	private boolean bosWritten = false;
	private ByteArrayOutputStream bos = null;
	private ByteArrayInputStream bis = null;
	
	private LCSProcessor() {
		this.bos = new ByteArrayOutputStream();
	}
	
	private LCSProcessor(byte[] bytes) {
		this.bis = new ByteArrayInputStream(bytes);
	}
	
	public static LCSProcessor buildEncoder() {
		return new LCSProcessor();
	}
	
	public static LCSProcessor buildDecoder(byte[] bytes) {
		return new LCSProcessor(bytes);
	}
	
	public byte[] build() {
		byte[] result = null;
		if ((bos != null)
				&& (bosWritten)) {
			result = bos.toByteArray();
		}
		return result;
	}
	
	public ByteArrayOutputStream getBos() {
		return bos;
	}
	
	public ByteArrayInputStream getBis() {
		return bis;
	}
	
	public LCSProcessor encode(byte[] bytes) {
		if (bytes != null) {
			encode(new UInt32(bytes.length));
			write(bytes);
		}
		return this;
	}
	
	public byte[] decodeByteArray() {
		byte[] result= null;
		if ((bis != null) 
				&& (bis.available() >= 1)){
			UInt32 length = decodeUInt32();
			int intLength = (int)(long)length.getAsLong();
			byte[] bytes = new byte[intLength];
			int count = bis.read(bytes,0,intLength);
			if (count == intLength) {
				result = bytes;
			} else {
				new Libra4jError(Type.INVALID_LENGTH,"byte buffer read did not return proper number of bytes: " + count + " <> " + intLength);
			}
		}
		return result;
	}
	
	public LCSProcessor encode(Boolean b) {
		if (b != null) {
			if (b) {
				byte[] bytes = {0x01};
				write(bytes);
			} else {
				byte[] bytes = {0x00};
				write(bytes);
			}
		}
		return this;
	}
	
	public Boolean decodeBoolean() {
		Boolean result = null;
		if ((bis != null) 
				&& (bis.available() >= 1)){
			int b = bis.read();
			if (b == 0) {
				result = false;
			} else if (b == 1) {
				result = true;
			} else {
				new Libra4jError(Type.UNKNOWN_VALUE,"boolean must be 0 or 1");
			}
		}
		return result;
	}
	
	public  LCSProcessor encode(String string) {
		if (string != null) {
			UInt32 length = new UInt32(string.getBytes().length);
			encode(length);
			write(string.getBytes());
		}
		return this;
	}
	
	public String decodeString() {
		String result = null;
		if (bis != null) {
			UInt32 uint32Length = decodeUInt32();
			int length = (int)(long)uint32Length.getAsLong();
			byte[] bytes = new byte[length];
			int count = bis.read(bytes,0,length);
			if (count == length) {
				try {
					result = new String(bytes,"UTF8");
				} catch (UnsupportedEncodingException e) {
					new Libra4jError(Type.UNKNOWN_VALUE,e);
				}
			} else {
				new Libra4jError(Type.INVALID_LENGTH,"byte buffer read did not return proper number of bytes: " + count + " <> " + length);
			}
		}
		return result;
	}
	
	public LCSProcessor encode(UInt64 uint64) {
		if (uint64 != null) {
			if (uint64.getBytes() != null) {
				write(Utils.reverseByteOrder(uint64.getBytes()));
			}
		}
		return this;
	}
	
	public UInt64 decodeUInt64() {
		UInt64 result = null;
		if (bis !=null) {
			byte[] bytes = new byte[UInt64.BYTE_LENGTH];
			int count = bis.read(bytes, 0,UInt64.BYTE_LENGTH);
			if (count == UInt64.BYTE_LENGTH) {
				result = new UInt64(Utils.reverseByteOrder(bytes));
			} else {
				new Libra4jError(Type.INVALID_LENGTH,"byte buffer read did not return proper number of bytes: " + count + " <> " + UInt64.BYTE_LENGTH);
			}
		}
		return result;
	}
	
	public LCSProcessor encode(UInt32 uint32) {
		if (uint32 != null) {
			if (uint32.getBytes() != null) {
				write(Utils.reverseByteOrder(uint32.getBytes()));
			}
		}
		return this;
	}
	
	public UInt32 decodeUInt32() {
		UInt32 result = null;
		if (bis !=null) {
			byte[] bytes = new byte[UInt32.BYTE_LENGTH];
			int count = bis.read(bytes, 0,UInt32.BYTE_LENGTH);
			if (count == UInt32.BYTE_LENGTH) {
				result = new UInt32(Utils.reverseByteOrder(bytes));
			} else {
				new Libra4jError(Type.INVALID_LENGTH,"byte buffer read did not return proper number of bytes: " + count + " <> " + UInt32.BYTE_LENGTH);
			}
		}
		return result;
	}
	
	public LCSProcessor encode(UInt16 uint16) {
		if (uint16 != null) {
			if (uint16.getBytes() != null) {
				write(Utils.reverseByteOrder(uint16.getBytes()));
			}
		}
		return this;
	}
	
	public UInt16 decodeUInt16() {
		UInt16 result = null;
		if (bis !=null) {
			byte[] bytes = new byte[UInt16.BYTE_LENGTH];
			int count = bis.read(bytes, 0,UInt16.BYTE_LENGTH);
			if (count == UInt16.BYTE_LENGTH) {
				result = new UInt16(Utils.reverseByteOrder(bytes));
			} else {
				new Libra4jError(Type.INVALID_LENGTH,"byte buffer read did not return proper number of bytes: " + count + " <> " + UInt16.BYTE_LENGTH);
			}
		}
		return result;
	}

	public LCSProcessor encode(UInt8 uint8) {
		if (uint8 != null) {
			if (uint8.getBytes() != null) {
				write(uint8.getBytes());
			}
		}
		return this;
	}
	
	public UInt8 decodeUInt8() {
		UInt8 result = null;
		if (bis !=null) {
			byte[] bytes = new byte[UInt8.BYTE_LENGTH];
			int count = bis.read(bytes, 0,UInt8.BYTE_LENGTH);
			if (count == UInt8.BYTE_LENGTH) {
				result = new UInt8(bytes);
			} else {
				new Libra4jError(Type.INVALID_LENGTH,"byte buffer read did not return proper number of bytes: " + count + " <> " + UInt8.BYTE_LENGTH);
			}
		}
		return result;
	}
	
	public LCSProcessor encode(AccountAddress accountAddress) {
		if (accountAddress != null) {
			UInt32 length = new UInt32(AccountAddress.BYTE_LENGTH);
			encode(length);
			write(accountAddress.getBytes());
		}
		return this;
	}
	
	public AccountAddress decodeAccountAddress() {
		AccountAddress result = null;
		if (bis !=null) {
			UInt32 length = decodeUInt32();
			if (length.getAsLong() != AccountAddress.BYTE_LENGTH) {
				new Libra4jError(Type.INVALID_LENGTH,"encoded length of AccountAddress is invalid: " + length.getAsLong() + " <> " + AccountAddress.BYTE_LENGTH);
			}
			byte[] bytes = new byte[AccountAddress.BYTE_LENGTH];
			int count = bis.read(bytes, 0,AccountAddress.BYTE_LENGTH);
			if (count == AccountAddress.BYTE_LENGTH) {
				result = new AccountAddress(bytes);
			} else {
				new Libra4jError(Type.INVALID_LENGTH,"byte buffer read did not return proper number of bytes: " + count + " <> " + AccountAddress.BYTE_LENGTH);
			}
		}
		return result;
	}
	
	public LCSProcessor encode(AccessPath accessPath) {
		if (accessPath != null) {
			UInt32 length = new UInt32(AccountAddress.BYTE_LENGTH);
			encode(length);
			//write(accessPath.getBytes());
		}
		return this;
	}
	
	public AccessPath decodeAccessPath() {
		AccessPath result = null;
		if (bis !=null) {
			UInt32 length = decodeUInt32();
			if (length.getAsLong() != AccountAddress.BYTE_LENGTH) {
				new Libra4jError(Type.INVALID_LENGTH,"encoded length of AccountAddress is invalid: " + length.getAsLong() + " <> " + AccountAddress.BYTE_LENGTH);
			}
			byte[] bytes = new byte[AccountAddress.BYTE_LENGTH];
			int count = bis.read(bytes, 0,AccountAddress.BYTE_LENGTH);
			if (count == AccountAddress.BYTE_LENGTH) {
				result = new AccessPath(bytes);
			} else {
				new Libra4jError(Type.INVALID_LENGTH,"byte buffer read did not return proper number of bytes: " + count + " <> " + AccountAddress.BYTE_LENGTH);
			}
		}
		return result;
	}
	
	public LCSProcessor encode(TransactionPayloadType transactionPayloadType) {
		if (transactionPayloadType != null) {
			transactionPayloadType.encodeToLCS(this);
		}
		return this;
	}
	
	public TransactionPayloadType decodeTransactionPayloadType() {
		TransactionPayloadType result = null;
		if (bis !=null) {
			UInt32 uint32 = decodeUInt32();
			if (uint32 != null) {
				result = TransactionPayloadType.get((int)(long)uint32.getAsLong());
			} else {
				new Libra4jError(Type.INVALID_VALUE,"paylod type is null");
			}
		}
		return result;
	}
	
	public LCSProcessor encode(Program program) {
		if (program != null) {
		}
		return this;
	}
	
	public LCSProcessor encodeCode(Code code) {
		if (code != null) {
		}
		return this;
	}
	
	public LCSProcessor encode(Argument argument) {
		if (argument != null) {
			argument.encodeToLCS(this);
		}
		return this;
	}
	
	public Argument decodeArgument() {
		return Argument.decode(this);
	}
	
	public LCSProcessor encodeArguments(ArrayList<Argument> arguments) {
		if (arguments != null) {
		}
		return this;
	}
	
	public LCSProcessor encode(Argument.Type argumentType) {
		if (argumentType != null) {
			argumentType.encodeToLCS(this);
		}
		return this;
	}
	
	public Argument.Type decodeArgumentType() {
		Argument.Type result = null;
		if (bis !=null) {
			UInt32 uint32 = decodeUInt32();
			if (uint32 != null) {
				result = Argument.Type.getFromInt((int)(long)uint32.getAsLong());
			} else {
				new Libra4jError(Type.INVALID_VALUE,"paylod type is null");
			}
		}
		return result;
	}
	
	public LCSProcessor encodeModule(Module module) {
		if (module != null) {
		}
		return this;
	}
	
	public LCSProcessor encodeModules(ArrayList<Module> modules) {
		if (modules != null) {
		}
		return this;
	}
	
	private void write(byte[] bytes) {
		try {
			bos.write(bytes);
		} catch (IOException e) {
			new Libra4jError(Type.JAVA_ERROR,e);
		}
		bosWritten = true;
	}
	
}
