package com.chaingrok.libra4j.types;

import java.util.ArrayList;

public class Program {
	
	private Code code = null;
	private Long codeSize = null;
	private ArrayList<Argument> arguments = null;
	private ArrayList<Module> modules = null;
	
	public Code getCode() {
		return code;
	}
	
	public void setCode(Code code) {
		this.code = code;
	}
	
	public Long getCodeSize() {
		return codeSize;
	}
	
	public void setCodeSize(Long codeSize) {
		this.codeSize = codeSize;
	}
	
	public ArrayList<Argument> getArguments() {
		return arguments;
	}
	
	public void setArguments(ArrayList<Argument> arguments) {
		this.arguments = arguments;
	}
	
	public ArrayList<Module> getModules() {
		return modules;
	}
	
	public void setModules(ArrayList<Module> modules) {
		this.modules = modules;
	}
	
}
