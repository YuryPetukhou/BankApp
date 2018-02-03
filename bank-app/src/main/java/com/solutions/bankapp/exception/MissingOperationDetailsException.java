package com.solutions.bankapp.exception;

public class MissingOperationDetailsException extends RuntimeException{
	private static final String EXCEPTION_MESSAGE = "Missing operation details in request";

	@Override
	public String getMessage () {
		return EXCEPTION_MESSAGE;
	}
}
