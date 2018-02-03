package com.solutions.bankapp.test.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.solutions.bankapp.dao.AccountDAO;
import com.solutions.bankapp.dao.OperationDAO;
import com.solutions.bankapp.dao.UserDAO;
import com.solutions.bankapp.service.CreateDepositService;
import com.solutions.bankapp.service.CreateWithdrawalService;
import com.solutions.bankapp.service.checker.CreateDepositRequestChecker;
import com.solutions.bankapp.service.checker.CreateWithdrawalRequestChecker;
import com.solutions.bankapp.service.checker.impl.CreateDepositRequestCheckerImpl;
import com.solutions.bankapp.service.checker.impl.CreateWithdrawalRequestCheckerImpl;
import com.solutions.bankapp.service.impl.CreateDepositServiceImpl;
import com.solutions.bankapp.service.impl.CreateWithdrawalServiceImpl;

@Configuration
public class OperationTestConfiguration {
	
	@Bean
	public CreateDepositRequestChecker depositChecker() {
		CreateDepositRequestChecker checker = new CreateDepositRequestCheckerImpl();
		return checker;
	}

	@Bean
	public CreateDepositService depositService() {
		CreateDepositService service = new CreateDepositServiceImpl();
		return service;
	}

	@Bean
	public CreateWithdrawalRequestChecker withdrawalChecker() {
		CreateWithdrawalRequestChecker checker = new CreateWithdrawalRequestCheckerImpl();
		return checker;
	}

	@Bean
	public CreateWithdrawalService withdrawalService() {
		CreateWithdrawalService service = new CreateWithdrawalServiceImpl();
		return service;
	}
	
	@Bean
	public OperationDAO operationDAO() {
		OperationDAO dao=mock(OperationDAO.class);
		return dao;
	}

	@Bean
	public UserDAO userDAO() {
		UserDAO dao=mock(UserDAO.class);
		when(dao.checkUserPersonalId(anyString())).thenReturn(true);
		when(dao.checkUserPersonalId("WrongUser")).thenReturn(false);
		return dao;
	}
	
	@Bean
	public AccountDAO accountDAO() {
		AccountDAO dao=mock(AccountDAO.class);
		when(dao.checkAccountByUserPersonalId(anyString())).thenReturn(true);
		when(dao.checkAccountByUserPersonalId("WrongAccountUser")).thenReturn(false);
		return dao;
	}
		
}
