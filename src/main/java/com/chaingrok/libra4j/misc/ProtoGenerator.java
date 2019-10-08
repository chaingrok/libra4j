package com.chaingrok.libra4j.misc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;


public class ProtoGenerator {
	
	public static final String PROTO_COMMAND = "/usr/local/bin/protoc"; //assuming running on a Mac
	
	private File protoDir;
	private File grpcDir;
	private File javaPlugin;
	private boolean isOk = false;
	
	public ProtoGenerator(String protoDirpath,String grpcDirpath,String javaPluginFilepath) {
		protoDir = new File(protoDirpath);
		if (!protoDir.exists()) {
			throw new Libra4jException("proto dir does not exist: " + protoDir.getAbsolutePath());
		}
		if (!protoDir.isDirectory()) {
			throw new Libra4jException("proto dir is not a directory: " + protoDir.getAbsolutePath());
		}
		grpcDir = new File(grpcDirpath);
		if (!grpcDir.exists()) {
			throw new Libra4jException("grpc dir does not exist: " + grpcDir.getAbsolutePath());
		}
		if (!grpcDir.isDirectory()) {
			throw new Libra4jException("grpc dir is not a directory: " + grpcDir.getAbsolutePath());
		} 
		javaPlugin = new File(javaPluginFilepath);
		if (!javaPlugin.exists()) {
			throw new Libra4jException("java plugin does not exist: " + javaPlugin.exists());
		}
	}
	
	public boolean generateDirectory() {
		boolean result = true;
		File[] protoFiles = protoDir.listFiles();
		for (File protoFile : protoFiles) {
			generateFile(protoFile.getName());
			if (!isOk()) {
				result = false;
			}
		}
		return result;
	}
	
	@SuppressWarnings("deprecation")
	public File generateFile(String protoFilename) {
		File result = null;
		isOk = true;
		File protoFile = new File(protoDir.getAbsolutePath() + File.separator + protoFilename);
		if (!protoFile.exists()) {
			throw new Libra4jException("Proto file does not exist: " + protoFile.getAbsolutePath());
		}
		String command = PROTO_COMMAND 
				+ " --proto_path=" + protoDir.getAbsolutePath() 
				+ " --java_out=" + grpcDir.getAbsolutePath() 
				//https://stackoverflow.com/questions/31029675/protoc-not-generating-service-stub-files
				//do not forget to make plugin.exe executable on your machine
				+ " --plugin=protoc-gen-grpc-java=" + javaPlugin.getAbsolutePath()
				+ " --grpc-java_out=" + grpcDir.getAbsolutePath() 
				+ " " + protoFile.getName();
		System.out.println("protoc command:" + command);
		Runtime rt = Runtime.getRuntime();
		Process pr;
		try {
			pr = rt.exec(command);
		} catch (IOException e) {
			throw new Libra4jException(e);
		}
		InputStream errorStream = pr.getErrorStream();
		StringWriter writer = new StringWriter();
		try {
			IOUtils.copy(errorStream, writer);
		} catch (IOException e) {
			throw new Libra4jException(e);
		}
		String string  = writer.toString();
		if ((string != null) 
				&& (string.length()> 0)){
				isOk = false;
				System.out.println("ERROR - error stream from protoc (" + string.length() + "): " + string);
			}
		InputStream outputStream = pr.getInputStream();
		writer = new StringWriter();
		try {
			IOUtils.copy(outputStream, writer);
		} catch (IOException e) {
			throw new Libra4jException(e);
		}
		string = writer.toString();
		if ((string != null) 
			&& (string.length()> 0)){
			isOk = false;
			System.out.println("ERROR - out stream from protoc (" + string.length() + "): " + string);
		}
		return result;
	}
	
	public boolean isOk() {
		return isOk;
	}

}
