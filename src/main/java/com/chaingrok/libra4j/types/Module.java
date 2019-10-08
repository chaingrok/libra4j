package com.chaingrok.libra4j.types;

import com.chaingrok.libra4j.misc.LCSInterface;
import com.chaingrok.libra4j.misc.LCSProcessor;

public class Module extends ByteArray implements LCSInterface {

	public Module(byte[] bytes) {
		super(bytes);
	}

	@Override
	public LCSProcessor encodeToLCS(LCSProcessor lcsProcessor) {
		lcsProcessor.encode(bytes);
		return lcsProcessor;
	}

}
