package com.chaingrok.libra4j.test.misc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.misc.Compiler;
import com.chaingrok.libra4j.test.TestConfig;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestCompiler {
	
	public static final String LIBRA_DIR = TestConfig.HOME_DIR_PATH + File.separator + "libra-master-2019-09-27"; // libra project dir
	public static final String MVIR_DIR = TestConfig.PROJECT_DIR_PATH 
											+ File.separator + "src" 
											+ File.separator + "main"  
											+ File.separator + "resources" 
											+ File.separator + "move"
											+ File.separator + "mvir"
											;
	
	@Test
	public void test001CompileGetVersion() {
		Compiler compiler = new Compiler(LIBRA_DIR);
		assertTrue(compiler.getVersion().startsWith(Compiler.VERSION_PREFIX));
		assertEquals(0,compiler.getMajorVersion());
		assertEquals(1,compiler.getMinorVersion());
	}
	
	@Test
	public void test002CompileReturnSample() {
		String mvirFilepath = MVIR_DIR + File.separator + "p2p_transfer.mvir";
		String mvbcFilepath = "";
		Compiler compiler = new Compiler(LIBRA_DIR);
		System.out.println("Print commmand: " + compiler.createCompileCommand(mvirFilepath));
		File mvbcFile = compiler.compile(mvirFilepath);
		assertTrue(mvbcFile.exists());
		assertTrue(mvbcFile.getAbsolutePath().endsWith(mvbcFilepath));
		//assertTrue(mvbcFile.delete());
	}

}
