package com.chaingrok.libra4j.types;

import java.io.File;

import com.chaingrok.lib.ChaingrokError;
import com.chaingrok.lib.Utils;
import com.chaingrok.lib.ChaingrokLog.Type;
import com.chaingrok.libra4j.misc.LCSInterface;
import com.chaingrok.libra4j.misc.LCSProcessor;

public class Program implements LCSInterface {
	
	public static final String MV_EXT = "mv";
	
	public static final String CREATE_ACCOUNT = "create_account";
	public static final String MINT = "mint";
	public static final String PEER_TO_PEER_TRANSFER = "peer_to_peer_transfer";
	public static final String ROTATE_AUTHENTICATION_KEY = "rotate_authentication_key";
	
	public static final String[] PROGRAMS = {
			CREATE_ACCOUNT,
			MINT,
			PEER_TO_PEER_TRANSFER,
			ROTATE_AUTHENTICATION_KEY,
	};
	
	private Code code = null;
	private Long codeSize = null;
	private Arguments arguments = null;
	private Modules modules = null;
	
	public Code getCode() {
		return code;
	}
	
	public Program setCode(Code code) {
		this.code = code;
		return this;
	}
	
	public Long getCodeSize() {
		return codeSize;
	}
	
	public Program setCodeSize(Long codeSize) {
		this.codeSize = codeSize;
		return this;
	}
	
	public Arguments getArguments() {
		return arguments;
	}
	
	public Program setArguments(Arguments arguments) {
		this.arguments = arguments;
		return this;
	}
	
	public Modules getModules() {
		return modules;
	}
	
	public Program setModules(Modules modules) {
		this.modules = modules;
		return this;
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
			new ChaingrokError(Type.INVALID_VALUE,"file to be deserialized does not exist: " + file.getAbsolutePath());
		}
		return result;
	}

	@Override
	public LCSProcessor encodeToLCS(LCSProcessor lcsProcessor) {
		if (lcsProcessor != null) {
			lcsProcessor
				.encode(code)
				.encode(arguments)
				.encode(modules)
				;
		}
		return lcsProcessor;
	}
	
}
