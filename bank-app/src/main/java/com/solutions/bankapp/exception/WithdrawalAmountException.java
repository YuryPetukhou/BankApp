package com.solutions.bankapp.exception;

public class WithdrawalAmountException extends RuntimeException {
	private static final String EXCEPTION_MESSAGE = "Withdrawal amout exceeds available user amount";

	@Override
	public String getMessage () {
		return EXCEPTION_MESSAGE;
	}
}
