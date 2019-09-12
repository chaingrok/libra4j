package com.chaingrok.libra4j.types;

public class Validator {
	
	private ValidatorId id;
	private Signature signature;
	
	public ValidatorId getValidatorId() {
		return id;
	}
	
	public void setValidatorId(ValidatorId id) {
		this.id = id;
	}
	
	public Signature getSignature() {
		return signature;
	}
	
	public void setSignature(Signature signature) {
		this.signature = signature;
	}

}
