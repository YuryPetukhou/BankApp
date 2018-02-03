package com.solutions.bankapp.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solutions.bankapp.dao.AccountDAO;
import com.solutions.bankapp.dao.OperationDAO;
import com.solutions.bankapp.dto.request.CreateDepositRequestDto;
import com.solutions.bankapp.dto.response.CreateDepositResonseDto;
import com.solutions.bankapp.service.CreateDepositService;
import com.solutions.bankapp.service.checker.CreateDepositRequestChecker;

@Service
@Transactional
public class CreateDepositServiceImpl implements CreateDepositService{

	@Autowired
	private CreateDepositRequestChecker checker;
	
	@Autowired
	private OperationDAO operationDAO;
	@Autowired
	private AccountDAO accountDAO;
	
	@Override
	public CreateDepositResonseDto createDeposit(CreateDepositRequestDto request) {
		checker.makeCheck(request);
		operationDAO.createDeposit(request);
		accountDAO.increaseBalance(request);
		return new CreateDepositResonseDto();
	}

}
