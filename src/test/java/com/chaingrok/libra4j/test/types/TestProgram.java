package com.chaingrok.libra4j.test.types;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.chaingrok.lib.Utils;
import com.chaingrok.lib.test.TestClass;
import com.chaingrok.libra4j.misc.LCSProcessor;
import com.chaingrok.libra4j.misc.Libra4jConfig;
import com.chaingrok.libra4j.types.Argument;
import com.chaingrok.libra4j.types.Arguments;
import com.chaingrok.libra4j.types.Code;
import com.chaingrok.libra4j.types.Module;
import com.chaingrok.libra4j.types.Modules;
import com.chaingrok.libra4j.types.Program;
import com.chaingrok.libra4j.types.Transaction;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestProgram extends TestClass {
	
	@Test
	public void test001NewInstance() {
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
		Modules modules = new Modules();
		program.setModules(modules);
		assertSame(modules,program.getModules());
		//
		Arguments arguments = new Arguments();
		program.setArguments(arguments);
		assertSame(arguments,program.getArguments());
	}
	
	@Test
	public void test002ProgramLCSEncodingDecoding() { //as per https://github.com/libra/libra/tree/master/common/canonical_serialization
		String testVectorHex = "040000006D6F766502000000020000000900000043414645204430304402000000090000006361666520643030640300000001000000CA02000000FED0010000000D";
		byte[] bytes = Utils.hexStringToByteArray(testVectorHex);
		LCSProcessor decoder = LCSProcessor.buildDecoder(bytes);
		Program program = decoder.decodeProgram();
		assertNotNull(program);
		assertEquals("move",new String(program.getCode().getBytes()));
		//
		Arguments arguments = program.getArguments();
		assertNotNull(arguments);
		assertEquals(2,arguments.size());
		Argument argument0 = arguments.get(0);
		assertEquals("CAFE D00D",argument0.getString());
		Argument argument1 = arguments.get(1);
		assertEquals("cafe d00d",argument1.getString());
		//
		Modules modules = program.getModules();
		assertNotNull(modules);
		assertEquals(3,modules.size());
		Module module0 = modules.get(0);
		byte[] testVectorModule0 = {(byte)0xca};
		assertArrayEquals(testVectorModule0,module0.getBytes());
		Module module1 = modules.get(1);
		byte[] testVectorModule1 = {(byte)0xfe,(byte)0xd0};
		assertArrayEquals(testVectorModule1,module1.getBytes());
		Module module2 = modules.get(2);
		byte[] testVectorModule2 = {(byte)0x0d};
		assertArrayEquals(testVectorModule2,module2.getBytes());
	}
	
	@Test
	public void test003ProgramCodeDeserializeFromFile() { 
		for (Transaction.Type type : Transaction.Type.values()) {
			if (!Transaction.Type.UNKNOWN.equals(type)) {
				Program program = new Program();
				String filePath = Libra4jConfig.MVIR_DIR + File.separator + type.getMvirSourceCode() + "." + Program.MV_EXT;
				File mvFile = new File(filePath);
				assertTrue("file does not exist: " + mvFile.getAbsolutePath(),mvFile.exists());
				assertNull(program.getCode());
				byte[] bytes = program.deserializeFromFile(filePath);
				assertTrue(bytes.length > 100);
				assertArrayEquals(bytes,program.getCode().getBytes());
				System.out.println("program: " + type.getMvirSourceCode() + " - bytes: " + Utils.byteArrayToHexString(bytes));
				assertArrayEquals(type.getCode().getBytes(),bytes);
			}
		}
	}
		

}
