package com.chaingrok.libra4j.types;

import java.util.ArrayList;

public class WriteOp {
	
	private Boolean isValue = null;
	private ArrayList<UInt8> value = null;
	
	public Boolean getIsValue() {
		return isValue;
	}
	
	public void setIsValue(Boolean isValue) {
		this.isValue = isValue;
	}
	
	public ArrayList<UInt8> getValue() {
		return value;
	}
	
	public void setValue(ArrayList<UInt8> value) {
		this.value = value;
	}
	
}
