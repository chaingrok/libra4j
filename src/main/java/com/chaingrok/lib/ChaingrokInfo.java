package com.chaingrok.lib;

public class ChaingrokInfo extends ChaingrokLog {

	public ChaingrokInfo(Type type) {
		super(type);
	}
	
	public ChaingrokInfo(Type type,Long version) {
		super(type,version);
	}
	
    public ChaingrokInfo(Type type, Object object) {
    	super(type,object);
	}
    
    public ChaingrokInfo(Type type, Long version, Object object) {
    	super(type,version,object);
	}
}
