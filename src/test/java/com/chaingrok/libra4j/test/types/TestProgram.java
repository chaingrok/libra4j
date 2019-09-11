package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.libra4j.test.TestClass;
import com.chaingrok.libra4j.types.Argument;
import com.chaingrok.libra4j.types.Code;
import com.chaingrok.libra4j.types.Module;
import com.chaingrok.libra4j.types.Program;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestProgram extends TestClass {
	
	@Test
	public void test001_newInstance() {
		Program program = new Program();
		//
		Code code = new Code(Code.MINT.getBytes());
		program.setCode(code);
		assertSame(code,program.getCode());
		//
		Long codeSize = 15L;
		program.setCodeSize(codeSize);
		assertSame(codeSize,program.getCodeSize());
		//
		ArrayList<Module> modules = new ArrayList<Module>();
		program.setModules(modules);
		assertSame(modules,program.getModules());
		//
		ArrayList<Argument> arguments = new ArrayList<Argument>();
		program.setArguments(arguments);
		assertSame(arguments,program.getArguments());
	}

}
