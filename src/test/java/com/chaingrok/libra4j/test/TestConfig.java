package com.chaingrok.libra4j.test;

import java.io.File;

public class TestConfig {
	
	public static final File PROJECT_DIR = new File(System.getProperty("user.dir"));
	public static final String PROJECT_DIR_PATH = PROJECT_DIR.getAbsolutePath();
	public static final File HOME_DIR = PROJECT_DIR.getParentFile().getParentFile();
	public static final String HOME_DIR_PATH = HOME_DIR.getAbsolutePath();

}
