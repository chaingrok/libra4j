package com.chaingrok.lib;

@SuppressWarnings("serial")
public class ChaingrokException extends RuntimeException {
	
	private Throwable throwable = null;

	public ChaingrokException(String msg) {
		super(msg);
	}

	public ChaingrokException(Throwable throwable) {
		super(throwable.getMessage());
		this.throwable = throwable;
		//System.out.println("Exception message: " + e.getMessage());
		//e.printStackTrace();
	}

	public Throwable getThrowable() {
		return throwable;
	}
	
}
