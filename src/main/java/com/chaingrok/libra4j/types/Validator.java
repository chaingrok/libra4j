package com.chaingrok.libra4j.types;

public class Validator {
	
	private ValidatorId id;
	private Signature signature;
	
	public ValidatorId getValidatorId() {
		return id;
	}
	
	public Validator setValidatorId(ValidatorId id) {
		this.id = id;
		return this;
	}
	
	public Signature getSignature() {
		return signature;
	}
	
	public Validator setSignature(Signature signature) {
		this.signature = signature;
		return this;
	}

}
