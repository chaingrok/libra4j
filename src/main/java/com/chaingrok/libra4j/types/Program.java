package com.chaingrok.libra4j.types;

import com.chaingrok.libra4j.misc.LCSInterface;
import com.chaingrok.libra4j.misc.LCSProcessor;

public class Program implements LCSInterface {
	
	private Code code = null;
	private Long codeSize = null;
	private Arguments arguments = null;
	private Modules modules = null;
	
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
	
	public Arguments getArguments() {
		return arguments;
	}
	
	public void setArguments(Arguments arguments) {
		this.arguments = arguments;
	}
	
	public Modules getModules() {
		return modules;
	}
	
	public void setModules(Modules modules) {
		this.modules = modules;
	}

	@Override
	public LCSProcessor encodeToLCS(LCSProcessor lcsProcessor) {
		if (lcsProcessor != null) {
			lcsProcessor
				.encode(code)
				.encode(arguments)
				.encode(modules);
		}
		return lcsProcessor;
	}
	
}
