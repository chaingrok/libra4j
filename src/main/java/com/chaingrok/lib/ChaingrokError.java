package com.chaingrok.lib;

public class ChaingrokError extends Libra4jLog {

	public ChaingrokError(Type type) {
		super(type);
	}
	
	public ChaingrokError(Type type,Long version) {
		super(type,version);
	}
	
    public ChaingrokError(Type type, Object object) {
    	super(type,object);
	}
    
    public ChaingrokError(Type type, Long version, Object object) {
    	super(type,version,object);
	}
}
