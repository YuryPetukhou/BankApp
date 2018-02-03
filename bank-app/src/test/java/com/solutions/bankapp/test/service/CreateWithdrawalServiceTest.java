package com.solutions.bankapp.test.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.solutions.bankapp.dao.AccountDAO;
import com.solutions.bankapp.dto.request.CreateWithdrawalRequestDto;
import com.solutions.bankapp.exception.MissingOperationDetailsException;
import com.solutions.bankapp.exception.NegativeAmountException;
import com.solutions.bankapp.exception.WithdrawalAmountException;
import com.solutions.bankapp.exception.WrongAccountException;
import com.solutions.bankapp.exception.WrongUserException;
import com.solutions.bankapp.service.CreateWithdrawalService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = OperationTestConfiguration.class)
public class CreateWithdrawalServiceTest {

	@Autowired
	private CreateWithdrawalService service;
	@Autowired
	private AccountDAO accountDAO;

	private CreateWithdrawalRequestDto request;

	@Before
	public void createRequest() {
		request = new CreateWithdrawalRequestDto();
		request.setAmount(new Double(10));
		request.setPersonalId("123-321");
	}

	@Test(expected = MissingOperationDetailsException.class)
	public void nullAmountTest() {
		request.setAmount(null);
		service.createWithdrawal(request);
	}

	@Test(expected = MissingOperationDetailsException.class)
	public void nullPersonalIdTest() {
		request.setPersonalId(null);
		service.createWithdrawal(request);
	}

	@Test(expected = NegativeAmountException.class)
	public void negativeAmountTest() {
		request.setAmount(new Double(-10));
		service.createWithdrawal(request);
	}

	@Test(expected = WrongUserException.class)
	public void wrongUserTest() {
		request.setPersonalId("WrongUser");
		service.createWithdrawal(request);
	}

	@Test(expected = WrongAccountException.class)
	public void wrongAccountTest() {
		request.setPersonalId("WrongAccountUser");
		service.createWithdrawal(request);
	}
	
	@Test(expected = WithdrawalAmountException.class)
	public void amountExceedTest() {
		when(accountDAO.getAccountBalance(anyString())).thenReturn(request.getAmount() - 10);
		service.createWithdrawal(request);
	}

	@Test
	public void positiveTest() {
		when(accountDAO.getAccountBalance(anyString())).thenReturn(request.getAmount() + 10);
		service.createWithdrawal(request);
	}

	@Test
	public void positiveZeroAmountTest() {
		request.setAmount(new Double(0));
		when(accountDAO.getAccountBalance(anyString())).thenReturn(request.getAmount() + 10);
		service.createWithdrawal(request);
	}

}
