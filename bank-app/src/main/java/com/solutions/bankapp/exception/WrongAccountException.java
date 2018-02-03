package com.solutions.bankapp.exception;

public class WrongAccountException extends RuntimeException {
	private static final String EXCEPTION_MESSAGE = "Wrong account id";

	@Override
	public String getMessage () {
		return EXCEPTION_MESSAGE;
	}
}
