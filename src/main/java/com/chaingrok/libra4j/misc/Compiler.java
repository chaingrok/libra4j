package com.chaingrok.libra4j.misc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;

public class Compiler {
	
	
	public static final String COMPILER_REL_FILEPATH = "target" + File.separator + "debug" + File.separator + "compiler";
	public static final String VERSION_PREFIX = "IR Compiler ";
	private String compilerFilepath ;
	private int majorVersion;
	private int minorVersion;
	
	public Compiler(String libraPath) {
		compilerFilepath = libraPath + File.separator + COMPILER_REL_FILEPATH;
		File compilerFile = new File(compilerFilepath);
		if (!compilerFile.exists()) {
			throw new Libra4jException("compiler executable does not exist: " + compilerFile.getAbsolutePath());
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public String getVersion() {
		String command = compilerFilepath + " --version";
		Runtime rt = Runtime.getRuntime();
		Process pr;
		try {
			pr = rt.exec(command);
		} catch (IOException e) {
			throw new Libra4jException(e);
		}
		InputStream outputStream = pr.getInputStream();
		StringWriter writer = new StringWriter();
		try {
			IOUtils.copy(outputStream, writer);
		} catch (IOException e) {
			throw new	Libra4jException(e);
		}
		String versionStr = writer.toString();
		if (!versionStr.startsWith(VERSION_PREFIX)) {
			throw new Libra4jException("Unknown compiler version string: " + versionStr);
		} else {
			String subVersionStr = versionStr.substring(VERSION_PREFIX.length());
			System.out.print("Version string:" + subVersionStr);
			majorVersion = new Integer(subVersionStr.substring(0,1));
			minorVersion = new Integer(subVersionStr.substring(2,3));
		}
		return versionStr;
	}
	
	public String createCompileCommand(String srcFilepath,String binFilepath) {
		String command = "";
		command += compilerFilepath;
		command += " --output ";
		command += " " + binFilepath;
		command += " " + srcFilepath;
		return command;
	}
	
	@SuppressWarnings("deprecation")
	public File compile(String srcFilepath,String binFilepath) {
		File srcFile = new File(srcFilepath);
		if (!srcFile.exists()) {
			throw  new Libra4jException("move src file does not exist: " + srcFile.getAbsolutePath());
		}
		File binFile = new File(binFilepath);
		File binDir = binFile.getParentFile();
		if (!binDir.exists()) {
			throw  new Libra4jException("directory for binaries does not exist: " + binDir.getAbsolutePath());
		}
		if (binFile.exists()) {
			binFile.delete();
		}
		//
		String command = createCompileCommand(srcFilepath,binFilepath);
		Runtime rt = Runtime.getRuntime();
		Process pr;
		try {
			pr = rt.exec(command);
		} catch (IOException e) {
			throw new Libra4jException(e);
		}
		InputStream error = pr.getErrorStream();
		StringWriter writer = new StringWriter();
		try {
			IOUtils.copy(error, writer);
		} catch (IOException e) {
			throw new Libra4jException(e);
		}
		String s = writer.toString();
		if (s != null && s.length() > 0) {
			String msg = "Error during compilation:" + s;
			System.out.println(msg);
			throw new Libra4jException(msg);
		}
		if (!binFile.exists()) {
			throw new Libra4jException("Bin contract file not created: " + binFile.getAbsolutePath());
		}
		return binFile;
	}

	public String getCompilerFilepath() {
		return compilerFilepath;
	}

	public int getMajorVersion() {
		return majorVersion;
	}

	public int getMinorVersion() {
		return minorVersion;
	}
	
	

}
