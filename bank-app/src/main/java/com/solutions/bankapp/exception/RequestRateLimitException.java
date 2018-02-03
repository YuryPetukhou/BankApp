package com.solutions.bankapp.exception;

public class RequestRateLimitException extends RuntimeException {
	private static final String EXCEPTION_MESSAGE = "Rate limit exceeded for country";

	private String countryName;
	
	public RequestRateLimitException() {
		super();
	}
	
	public RequestRateLimitException(String countryName) {
		super();
		this.countryName=countryName;
	}
	
	@Override
	public String getMessage () {
		return countryName==null ? EXCEPTION_MESSAGE : EXCEPTION_MESSAGE+" "+countryName;
	}

}
