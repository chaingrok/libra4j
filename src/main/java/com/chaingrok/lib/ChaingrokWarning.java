package com.chaingrok.lib;

public class ChaingrokWarning extends ChaingrokLog {

	public ChaingrokWarning(Type type) {
		super(type);
	}
	
	public ChaingrokWarning(Type type,Long version) {
		super(type,version);
	}
	
    public ChaingrokWarning(Type type, Object object) {
    	super(type,object);
	}
    
    public ChaingrokWarning(Type type, Long version, Object object) {
    	super(type,version,object);
	}
}
