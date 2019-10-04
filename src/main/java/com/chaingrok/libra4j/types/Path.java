package com.chaingrok.libra4j.types;

import com.chaingrok.libra4j.misc.Libra4jException;
import com.chaingrok.libra4j.misc.Utils;

//Source : types/src/access_path.rs

public class Path extends ByteArray{
	
	public static final String SEPARATOR = "/";
	
	
	public Path(String path) {
		this(path.getBytes(),false);
	}
	
	public Path(byte[] path) {
		this(path,true);
	}
	
	public Path(byte[] path,boolean loose) {
		super(path);
		if (!loose) {
			String str = new String(path);
			if (!str.matches("[a-zA-Z0-9_\\/]+")) {
				throw new Libra4jException("path contains invalid chars: " + Utils.byteArrayToHexString(path));
			}
			if (!str.startsWith(SEPARATOR)) {
				throw new Libra4jException("path does not start with proper separator: " + Utils.byteArrayToHexString(path));
			}
		}
	}
	
	public Type getPathType() {
		return Type.get(new String(getBytes()));
	}
	
	@Override
	public String toString() {
		String result = "";
		result += getPathType().toString();
		if (getPathType() == Type.UNKNOWN) {
			result += " (" + new String(getBytes()) + " - " + Utils.byteArrayToHexString(getBytes()) + ")";
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
				if ((!type.equals(UNKNOWN))
					&& (type.type.equals(t))) {
					result = type;
					break;
				}
			}
			if (result == null) {
				result = UNKNOWN;
			}
			return result;
		}
	}

}
