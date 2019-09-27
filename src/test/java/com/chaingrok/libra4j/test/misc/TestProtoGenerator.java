package com.chaingrok.libra4j.test.misc;

import java.io.File;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.misc.ProtoGenerator;

@SuppressWarnings("unused")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestProtoGenerator {
	public static final File PROJECT_DIR = new File(System.getProperty("user.dir"));
	public static final File HOME_DIR = PROJECT_DIR.getParentFile().getParentFile();
	public static final String HOME_DIR_PATH = HOME_DIR.getAbsolutePath();
	public static final String PROTOC_DIR = HOME_DIR_PATH + File.separator + "protoc";
	public static final String PROTOC_JAVA_PLUGIN = PROTOC_DIR + File.separator + "protoc-gen-grpc-java-1.21.0-osx-x86_64.exe";
	
	public static final String PROTO_COMMAND = "/usr/local/bin/protoc";
	public static final String PROTO_FILES_DIR = System.getProperty("user.dir")  + File.separator + "proto";
	public static final String GRPC_DIR = System.getProperty("user.dir")  + File.separator + "gRPC";
	
	//@Test
	public void test001_generateProtoFile() {
		ProtoGenerator protoGenerator = new ProtoGenerator(PROTO_FILES_DIR,GRPC_DIR,PROTOC_JAVA_PLUGIN);
		protoGenerator.generateFile("admission_control.proto");
	}
	
	//@Test
	public void test002_generateProtoDirectory() {
		ProtoGenerator protoGenerator = new ProtoGenerator(PROTO_FILES_DIR,GRPC_DIR,PROTOC_JAVA_PLUGIN);
		protoGenerator.generateDirectory();
	}

}
