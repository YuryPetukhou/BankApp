package com.solutions.bankapp.service.checker.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.solutions.bankapp.dao.AccountDAO;
import com.solutions.bankapp.dao.UserDAO;
import com.solutions.bankapp.dto.request.CreateWithdrawalRequestDto;
import com.solutions.bankapp.exception.MissingOperationDetailsException;
import com.solutions.bankapp.exception.NegativeAmountException;
import com.solutions.bankapp.exception.WithdrawalAmountException;
import com.solutions.bankapp.exception.WrongAccountException;
import com.solutions.bankapp.exception.WrongUserException;
import com.solutions.bankapp.service.checker.CreateWithdrawalRequestChecker;

@Component
public class CreateWithdrawalRequestCheckerImpl implements CreateWithdrawalRequestChecker {

	@Autowired
	private UserDAO userDAO;
	@Autowired
	private AccountDAO accountDAO;

	@Override
	public boolean makeCheck(CreateWithdrawalRequestDto request) {
		if (!argumentsNotNull(request)) {
			throw new MissingOperationDetailsException();
		}
		if (!amountNotNegative(request)) {
			throw new NegativeAmountException();
		}
		if (!userCorrect(request)) {
			throw new WrongUserException();
		}
		if (!accountCorrect(request)) {
			throw new WrongAccountException();
		}
		if (!amountNotExceedCurrent(request)) {
			throw new WithdrawalAmountException();
		}
		return true;
	}


	private boolean argumentsNotNull(CreateWithdrawalRequestDto request) {
		if (request == null) {
			return false;
		}
		boolean valid = true;
		valid &= request.getPersonalId() != null;
		valid &= request.getAmount() != null;
		return valid;
	}

	private boolean amountNotNegative(CreateWithdrawalRequestDto request) {
		if (request == null) {
			return false;
		}
		boolean valid = false;
		if (request.getAmount() != null) {
			valid = !(request.getAmount() < 0);
		} else {
			valid = false;
		}
		return valid;
	}

	private boolean userCorrect(CreateWithdrawalRequestDto request) {
		if (request == null) {
			return false;
		}
		if (request.getPersonalId()==null) {
			return false;
		}
		return userDAO.checkUserPersonalId(request.getPersonalId());
	}

	private boolean accountCorrect(CreateWithdrawalRequestDto request) {
		if (request == null) {
			return false;
		}
		boolean valid=false;
		if (request.getPersonalId() != null) {
			valid = accountDAO.checkAccountByUserPersonalId(request.getPersonalId());
		} else {
			valid = false;
		}
		return valid;
	}

	private boolean amountNotExceedCurrent(CreateWithdrawalRequestDto request) {
		if (request == null) {
			return false;
		}
		if (request.getPersonalId() == null) {
			return false;
		}
		boolean valid = false;
		Double currentBalance = accountDAO.getAccountBalance(request.getPersonalId());
		if (request.getAmount() != null) {
			valid = currentBalance > request.getAmount();
		} else {
			valid = false;
		}
		return valid;
	}
}
