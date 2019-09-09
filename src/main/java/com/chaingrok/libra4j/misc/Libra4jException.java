package com.chaingrok.libra4j.misc;

@SuppressWarnings("serial")
public class Libra4jException extends RuntimeException {
	
	private Throwable throwable = null;

	public Libra4jException(String msg) {
		super(msg);
	}

	public Libra4jException(Throwable throwable) {
		super(throwable.getMessage());
		this.throwable = throwable;
		//System.out.println("Exception message: " + e.getMessage());
		//e.printStackTrace();
	}

	public Throwable getThrowable() {
		return throwable;
	}
	
}
