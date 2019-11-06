package com.chaingrok.libra4j.test.misc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.lib.Utils;
import com.chaingrok.libra4j.misc.Libra4jConfig;
import com.chaingrok.libra4j.misc.ProtoGenerator;
import com.chaingrok.libra4j.test.TestConfig;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestProtoGenerator {
	
	public static final String PROTOC_DIR = TestConfig.HOME_DIR_PATH + File.separator + "protoc";
	public static final String PROTOC_JAVA_PLUGIN = PROTOC_DIR + File.separator + "protoc-gen-grpc-java-1.21.0-osx-x86_64.exe";
	
	public static final String LIBRA_PROJECT_VERSION = "2019-11-06";
	public static final String LIBRA_PROJECT_DIR = TestConfig.HOME_DIR_PATH + File.separator + "libra-master-"+ LIBRA_PROJECT_VERSION;
	
	public static final String PROTO_COMMAND = "/usr/local/bin/protoc";  //local to installation: change if needed
	public static final String PROTO_FILES_DIR = Libra4jConfig.LIB_DIR_PATH  + File.separator + "proto";
	public static final String PROTO_FILES_DIR_NEW = Libra4jConfig.LIB_DIR_PATH  + File.separator + "proto-new";
	public static final String GRPC_DIR = Libra4jConfig.LIB_DIR_PATH  + File.separator + "src" + File.separator + "grpc" + File.separator + "java";
	
	public static  final String LIBRA_GITHUB_PROJECT = "https://github.com/libra/libra";
	
	public static final String[] PROTO_FILES = {
		"types/src/proto/access_path.proto",
		"types/src/proto/account_state_blob.proto",
		"admission_control/admission-control-proto/src/proto/admission_control.proto",
		"types/src/proto/events.proto",
		"types/src/proto/get_with_proof.proto",
		"types/src/proto/language_storage.proto",
		"types/src/proto/ledger_info.proto",
		"mempool/mempool-shared-proto/src/proto/mempool_status.proto",
		"types/src/proto/proof.proto",
		"types/src/proto/transaction_info.proto",
		"types/src/proto/transaction.proto",
		"types/src/proto/validator_change.proto",
		"types/src/proto/validator_public_keys.proto",
		"types/src/proto/validator_set.proto",
		"types/src/proto/vm_errors.proto"
	};
	
	//@Test
	public void test001GenerateProtoFile() {
		ProtoGenerator protoGenerator = new ProtoGenerator(PROTO_FILES_DIR,GRPC_DIR,PROTOC_JAVA_PLUGIN);
		protoGenerator.generateFile("admission_control.proto");
		assertTrue(protoGenerator.isOk());
	}
	
	//@Test
	public void test002GatherNewProtos() throws IOException {
		File libraProjectDir = new File(LIBRA_PROJECT_DIR);
		assertTrue(libraProjectDir.exists());
		assertTrue(libraProjectDir.isDirectory());
		File newProtoDir = new File(PROTO_FILES_DIR_NEW);
		if (newProtoDir.exists()) {
			Utils.deleteDirectoryWithContent(newProtoDir.getAbsolutePath());	
		}
		assertFalse(newProtoDir.exists());
		assertTrue(newProtoDir.mkdir());
		System.out.println("mkdir:" + newProtoDir.getAbsolutePath());
		for (String protoFilePath : PROTO_FILES) {
			protoFilePath = LIBRA_PROJECT_DIR + File.separator + protoFilePath;
			File protoFile = new File(protoFilePath);
			assertTrue(protoFile.exists());
			Path srcPath = Paths.get(protoFilePath);
			Path destPath = Paths.get(PROTO_FILES_DIR_NEW + File.separator + protoFilePath.substring(protoFilePath.lastIndexOf("/") + 1));
			System.out.println("copying " + srcPath.toString() + " to " + destPath);
			Files.copy(srcPath, destPath);
		}
	}
	
	@Test
	public void test003GenerateJavaFromProtoProto() {
		//following lines must be commented out when not generating : grpc java plugin missing on CI server
		/*
		ProtoGenerator protoGenerator = new ProtoGenerator(PROTO_FILES_DIR,GRPC_DIR,PROTOC_JAVA_PLUGIN);
		assertTrue(protoGenerator.generateDirectory()); //uncomment this line to generate
		*/
		
	}

}
