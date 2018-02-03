package com.solutions.bankapp.exception;

public class WrongUserException extends RuntimeException {
	private static final String EXCEPTION_MESSAGE = "Wrong user id";

	@Override
	public String getMessage () {
		return EXCEPTION_MESSAGE;
	}

}
