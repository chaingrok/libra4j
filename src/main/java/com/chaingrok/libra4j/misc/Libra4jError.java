package com.chaingrok.libra4j.misc;

public class Libra4jError extends Libra4jLog {

	public Libra4jError(Type type) {
		super(type);
	}
	
	public Libra4jError(Type type,Long version) {
		super(type,version);
	}
	
    public Libra4jError(Type type, Object object) {
    	super(type,object);
	}
    
    public Libra4jError(Type type, Long version, Object object) {
    	super(type,version,object);
	}
}
