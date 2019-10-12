package com.chaingrok.libra4j.types;

import com.chaingrok.lib.ChaingrokError;
import com.chaingrok.lib.ChaingrokLog.Type;
import com.google.gson.Gson;

public class MoveIR {
	
	private boolean checked = false;
	private int[] code;
	private Object[] args;
	
	public int[] getCode() {
		check();
		return code;
	}
	public Object[] getArgs() {
		check();
		return args;
	}
	
	private void check() {
		if (!checked) {
			if (code == null) {
				new ChaingrokError(Type.NULL_DATA,"code array is null");
			} else {
				if (code.length == 0) {
					new ChaingrokError(Type.INVALID_VALUE,"code array cannot be empty");
				}
			}
			if (args == null) {
				new ChaingrokError(Type.NULL_DATA,"args array is null");
			} else {
				if (args.length != 0) {
					new ChaingrokError(Type.INVALID_VALUE,"args array should be empty");
				}
			}
		}
		checked = true;
	}
	
	public byte[] toByteArray() {
		byte[] result = null;
		if (code != null) {
			result = new byte[code.length];
			int i = 0;
			for(int c : code) {
				result[i++] = (byte)c;
			}
		}
		return result;
	}
	
	public static MoveIR fromJson(String string) {
		return new Gson().fromJson(string, MoveIR.class);
	}
}
