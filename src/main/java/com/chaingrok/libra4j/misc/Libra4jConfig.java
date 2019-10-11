package com.chaingrok.libra4j.misc;

import java.io.File;

public class Libra4jConfig {
	
	public static final File LIB_DIR = new File(System.getProperty("user.dir"));
	public static final String LIB_DIR_PATH = LIB_DIR.getAbsolutePath();
	
	public static final String MVIR_DIR = LIB_DIR_PATH 
			+ File.separator + "src" 
			+ File.separator + "main"  
			+ File.separator + "resources" 
			+ File.separator + "move"
			+ File.separator + "mvir"
			;
	
	

}
