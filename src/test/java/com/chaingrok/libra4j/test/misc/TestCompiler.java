package com.chaingrok.libra4j.test.misc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.misc.Compiler;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestCompiler {
	
	public static final String LIBRA_DIR = ""; // libra project dir
	public static final String MVIR_DIR = ""; //
	public static final String MVBC_DIR = "";
	
	//@Test
	public void test001_compileGetVersion() {
		Compiler compiler = new Compiler(LIBRA_DIR);
		assertTrue(compiler.getVersion().startsWith(Compiler.VERSION_PREFIX));
		assertEquals(0,compiler.getMajorVersion());
		assertEquals(1,compiler.getMinorVersion());
	}
	
	//@Test
	public void test002_compileReturnSample() {
		String mvirFilepath = MVIR_DIR + File.separator + "return.mvir";
		String mvbcFilepath = MVBC_DIR + File.separator + "return.mvbc";
		Compiler compiler = new Compiler(LIBRA_DIR);
		System.out.println("Print commmand: " + compiler.createCompileCommand(mvirFilepath, mvbcFilepath));
		File mvbcFile = compiler.compile(mvirFilepath, mvbcFilepath);
		assertTrue(mvbcFile.exists());
		assertTrue(mvbcFile.getAbsolutePath().endsWith(mvbcFilepath));
		assertTrue(mvbcFile.delete());
	}

}
