package com.solutions.bankapp.service.checker.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.solutions.bankapp.dao.AccountDAO;
import com.solutions.bankapp.dao.UserDAO;
import com.solutions.bankapp.dto.request.CreateDepositRequestDto;
import com.solutions.bankapp.dto.request.CreateWithdrawalRequestDto;
import com.solutions.bankapp.exception.MissingOperationDetailsException;
import com.solutions.bankapp.exception.NegativeAmountException;
import com.solutions.bankapp.exception.WrongAccountException;
import com.solutions.bankapp.exception.WrongUserException;
import com.solutions.bankapp.service.checker.CreateDepositRequestChecker;

@Component
public class CreateDepositRequestCheckerImpl implements CreateDepositRequestChecker {
	
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private AccountDAO accountDAO;
	
	@Override
	public boolean makeCheck (CreateDepositRequestDto request) {
		if (!argumentsNotNull(request)) {
			throw new MissingOperationDetailsException ();
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
		return true;
	}
	
	private boolean argumentsNotNull(CreateDepositRequestDto request) {
		if (request==null) {
			return false;
		}
		boolean valid = true;		
		valid&=request.getName()!=null;
		valid&=request.getSurname()!=null;
		valid&=request.getPersonalId()!=null;
		valid&=request.getAmount()!=null;
		
		return valid;
	}

	
	private boolean amountNotNegative(CreateDepositRequestDto request) {
		if (request==null) {
			return false;
		}
		boolean valid = true;
		if (request.getAmount()!=null) {
			valid&= !(request.getAmount()<0);
		}		
		return valid;
	}

	
	
	
	private boolean userCorrect(CreateDepositRequestDto request) {
		if (request == null) {
			return false;
		}
		if (request.getPersonalId()==null) {
			return false;
		}
		return userDAO.checkUserPersonalId(request.getPersonalId());
	}

	
	private boolean accountCorrect(CreateDepositRequestDto request) {
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

}
