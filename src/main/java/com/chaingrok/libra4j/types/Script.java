package com.chaingrok.libra4j.types;

import com.chaingrok.libra4j.misc.LCSInterface;
import com.chaingrok.libra4j.misc.LCSProcessor;

public class Script implements LCSInterface {
	
	private Code code = null;
	private Arguments arguments = null;
	
	public Code getCode() {
		return code;
	}

	public void setCode(Code code) {
		this.code = code;
	}

	public Arguments getArguments() {
		return arguments;
	}

	public void setArguments(Arguments arguments) {
		this.arguments = arguments;
	}

	@Override
	public LCSProcessor encodeToLCS(LCSProcessor lcsProcessor) {
		if (lcsProcessor != null) {
			lcsProcessor
				.encode(code)
				.encode(arguments)
				;
		}
		return lcsProcessor;
	}

}
