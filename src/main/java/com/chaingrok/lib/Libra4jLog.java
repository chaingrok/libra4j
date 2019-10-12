package com.chaingrok.lib;

import java.util.ArrayList;


public abstract class Libra4jLog {  
	
	private static ArrayList<Libra4jLog> errorLog = new ArrayList<Libra4jLog>();
	
	private Type type;
	private Long version;
	private Object object;
	private StackTraceElement stackTrace[] = null;
	
	public Libra4jLog(Type type) {
		this.type = type;
		log(this);
	}
	
	public Libra4jLog(Type type,Long version) {
		this.type = type;
		this.version = version;
		log(this);
	}
	
    public Libra4jLog(Type type, Object object) {
    	this.type = type;
		this.object = object;
		log(this);
	}
    
    public Libra4jLog(Type type, Long version, Object object) {
    	this.type = type;
    	this.version = version;
		this.object = object;
		log(this);
	}

	public Type getType() {
		return type;
	}

	public Long getVersion() {
		return version;
	}

	public Object getObject() {
		return object;
	}
	
	public StackTraceElement[] getStackTrace() {
		return stackTrace;
	}

	
	private void log(Libra4jLog log) {
		try
		{
			throw new Throwable(); //to obtain the call stack
		} catch(Throwable throwable) {
			this.stackTrace = throwable.getStackTrace();
		}
		errorLog.add(log);
	}

	public static ArrayList<Libra4jLog> getLogs() {
		return errorLog;
	}
	
	public static boolean hasLogs() {
		boolean result = false;
		if ((getLogs() != null)
			&& (getLogs().size() > 0)) {
			result = true;
		}
		return result;
	}
	
	public static String dumpLogs() {
		String result = "";
		int count = 0;
		for (Libra4jLog log : Libra4jLog.getLogs()) {
			result += log.getClass().getSimpleName() + " #" + ++count + ": " + log.getType().toString();
			if (log.getObject() != null) {
				result += " -- " + log.getObject().toString();
			}
			result += "\n";
			StackTraceElement[] stackTrace = log.getStackTrace();
			if (stackTrace != null) {
				int i = 0;
				for (StackTraceElement stackTraceElement : stackTrace) {
					if (++i > 2) { //remove locator itself
						String className = stackTraceElement.getClassName();
						String methodName = stackTraceElement.getMethodName();
						String fileName = stackTraceElement.getFileName();
						int lineNumber = stackTraceElement.getLineNumber();
						result += "   at " + className + "." + methodName + "(" + fileName + ":" + lineNumber + ")\n";
					}
				}
			}
			result += "\n";
		}
		return result;
	}
	
	public static void purgeLogs() {
		errorLog = new ArrayList<Libra4jLog>();
	}

	public enum Type {
		LIST_TOO_LONG,
		NULL_DATA,
		INIT_ERROR,
		TOO_MANY_ITEMS,
		INVALID_VERSION,
		INVALID_CLASS,
		INVALID_TIMESTAMP,
		INVALID_LENGTH,
		INVALID_COUNT,
		INVALID_VALUE,
		UNKNOWN_VALUE,
		MISSING_DATA,
		NODE_ERROR,
		JAVA_ERROR,
		HTTP_ERROR,
		NOT_IMPLEMENTED,
	}

}
