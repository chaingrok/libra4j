package com.chaingrok.libra4j.misc;

public class Libra4jInfo extends Libra4jLog {

	public Libra4jInfo(Type type) {
		super(type);
	}
	
	public Libra4jInfo(Type type,Long version) {
		super(type,version);
	}
	
    public Libra4jInfo(Type type, Object object) {
    	super(type,object);
	}
    
    public Libra4jInfo(Type type, Long version, Object object) {
    	super(type,version,object);
	}
}
