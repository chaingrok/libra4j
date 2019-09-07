package com.chaingrok.libra4j.misc;

public class Libra4jWarning extends Libra4jLog {

	public Libra4jWarning(Type type) {
		super(type);
	}
	
	public Libra4jWarning(Type type,Long version) {
		super(type,version);
	}
	
    public Libra4jWarning(Type type, Object object) {
    	super(type,object);
	}
    
    public Libra4jWarning(Type type, Long version, Object object) {
    	super(type,version,object);
	}
}
