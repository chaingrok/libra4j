package com.chaingrok.libra4j.misc;

@SuppressWarnings("serial")
public class Libra4jException extends RuntimeException {

	public Libra4jException(String msg) {
		super(msg);
	}

	public Libra4jException(Throwable e) {
		super(e.getMessage());
		System.out.println("Exception message: " + e.getMessage());
		e.printStackTrace();
	}
}
