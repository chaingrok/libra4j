package com.chaingrok.libra4j.types;

import com.chaingrok.libra4j.misc.Libra4jException;
import com.chaingrok.libra4j.misc.Utils;

//Source : types/src/access_path.rs

public class Path {
	
	public static final String SEPARATOR = "/";
	
	private byte[] path;
	
	public Path(byte[] path) {
		String str = new String(path);
		if (!str.matches("[a-zA-Z0-9_\\/]+")) {
			throw new Libra4jException("path contains invalid chars: " + Utils.byteArrayToHexString(path));
		}
		if (!str.startsWith(SEPARATOR)) {
			throw new Libra4jException("path does not start with proper separator: " + Utils.byteArrayToHexString(path));
		}
		this.path = path;
	}
	
	public byte[] get() {
		return path;
	}
	
	public Type getType() {
		return Type.get(new String(path));
	}
	
	@Override
	public String toString() {
		String result = "";
		result += getType().toString();
		if (getType() == Type.UNKNOWN) {
			result += " (" + Utils.byteArrayToHexString(path) + ")";
		}
		return result;
	}
	
	public enum Type {
		
		SENT_EVENTS_COUNT("/sent_events_count/"),
		RECEIVED_EVENTS_COUNT("/received_events_count/"),
		UNKNOWN,
		
		;
		
		private String type;
		
		private Type() {
		}
		
		private Type(String type) {
			this.type = type;
		}
		
		public String get() {
			return type;
		}
		
		public static Type get(String t) {
			Type result = null;
			for (Type type : Type.values()) {
				if (!type.equals(UNKNOWN)) {
					if (type.type.equals(t)) {
						result = type;
						break;
					}
				}
			}
			if (result == null) {
				result = UNKNOWN;
			}
			return result;
		}
	}

}
