package com.solutions.bankapp.exception;

public class NegativeAmountException extends RuntimeException {
	private static final String EXCEPTION_MESSAGE = "Operation cannot be performed with negative amount";
	
	@Override
	public String getMessage () {
		return EXCEPTION_MESSAGE;
	}
}
