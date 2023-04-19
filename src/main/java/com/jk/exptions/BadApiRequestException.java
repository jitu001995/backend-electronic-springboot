package com.jk.exptions;

public class BadApiRequestException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public BadApiRequestException() {
		super("Bad Request !!!");
		// TODO Auto-generated constructor stub
	}

	

	public BadApiRequestException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	
	
}
