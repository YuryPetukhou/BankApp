package com.solutions.bankapp.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solutions.bankapp.dao.AccountDAO;
import com.solutions.bankapp.dao.OperationDAO;
import com.solutions.bankapp.dto.request.CreateWithdrawalRequestDto;
import com.solutions.bankapp.dto.response.CreateWithdrawalResponseDto;
import com.solutions.bankapp.service.CreateWithdrawalService;
import com.solutions.bankapp.service.checker.CreateWithdrawalRequestChecker;

@Service
@Transactional
public class CreateWithdrawalServiceImpl implements CreateWithdrawalService {

	@Autowired
	private CreateWithdrawalRequestChecker checker;
	
	@Autowired
	private OperationDAO operationDAO;
	@Autowired
	private AccountDAO accountDAO;
	
	@Override
	public CreateWithdrawalResponseDto createWithdrawal(CreateWithdrawalRequestDto request) {
		checker.makeCheck(request);
		operationDAO.createWithdrawal(request);
		accountDAO.decreaseBalance(request);
		return new CreateWithdrawalResponseDto();
	}

}
