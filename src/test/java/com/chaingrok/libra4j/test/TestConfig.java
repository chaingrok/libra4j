package com.chaingrok.libra4j.test;

import java.io.File;

import com.chaingrok.libra4j.misc.Libra4jConfig;

public class TestConfig {
	
	//dependent on local config
	public static final File HOME_DIR = Libra4jConfig.LIB_DIR.getParentFile().getParentFile();
	public static final String HOME_DIR_PATH = HOME_DIR.getAbsolutePath();

}
