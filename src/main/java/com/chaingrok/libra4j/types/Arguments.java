package com.chaingrok.libra4j.types;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Arguments extends ArrayList<Argument>{
	
	public Arguments set(Argument argument) {
		super.add(argument);
		return this;
	}
}
