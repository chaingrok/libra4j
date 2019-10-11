package com.chaingrok.libra4j.types;

import java.io.File;

import com.chaingrok.libra4j.misc.LCSInterface;
import com.chaingrok.libra4j.misc.LCSProcessor;
import com.chaingrok.libra4j.misc.Libra4jError;
import com.chaingrok.libra4j.misc.Libra4jLog.Type;
import com.chaingrok.libra4j.misc.Utils;

public class Program implements LCSInterface {
	
	public static final String MV_EXT = "mv";
	public static final String KEY_ROTATE = "key_rotate";
	public static final String P2P_TRANSFER = "p2p_transfer";
	
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
	
	public byte[] deserializeFromFile(String filepath) {
		return deserializeFromFile(new File(filepath));
	}
	
	public byte[] deserializeFromFile(File file) {
		byte[] result = null;
		if (file.exists()) {
			String string = new String(Utils.readFile(file.getAbsolutePath()));
			MoveIR moveIR = MoveIR.fromJson(string);
			result = moveIR.toByteArray();
			setCode(new Code(result));
		} else {
			new Libra4jError(Type.INVALID_VALUE,"file to be deserialized does not exist: " + file.getAbsolutePath());
		}
		return result;
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
