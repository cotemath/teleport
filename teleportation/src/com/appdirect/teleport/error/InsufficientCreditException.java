package com.appdirect.teleport.error;

/**
 * Exception thrown when the user doesn't have enough credits 
 *
 */
public class InsufficientCreditException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6072576441972681866L;
	
	public InsufficientCreditException(String message) {
		super(message);
	}

}
