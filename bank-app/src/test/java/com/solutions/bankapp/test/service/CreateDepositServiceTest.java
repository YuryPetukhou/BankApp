package com.solutions.bankapp.test.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.solutions.bankapp.dto.request.CreateDepositRequestDto;
import com.solutions.bankapp.exception.MissingOperationDetailsException;
import com.solutions.bankapp.exception.NegativeAmountException;
import com.solutions.bankapp.exception.WrongUserException;
import com.solutions.bankapp.service.CreateDepositService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = OperationTestConfiguration.class)
public class CreateDepositServiceTest {
	
	@Autowired
	private CreateDepositService service;

	private CreateDepositRequestDto request;

	@Before
	public void createRequest() {
		request = new CreateDepositRequestDto();
		request.setAmount(new Double(10));
		request.setName("Alex");
		request.setSurname("Perkons");
		request.setPersonalId("123-321");
	}

	@Test(expected = MissingOperationDetailsException.class)
	public void amountNullTest() {
		request.setAmount(null);
		service.createDeposit(request);
	}

	@Test(expected = MissingOperationDetailsException.class)
	public void nameNullTest() {
		request.setName(null);
		service.createDeposit(request);
	}

	@Test(expected = MissingOperationDetailsException.class)
	public void surnameNullTest() {
		request.setSurname(null);
		service.createDeposit(request);
	}

	@Test(expected = MissingOperationDetailsException.class)
	public void personaIdNullTest() {
		request.setPersonalId(null);
		service.createDeposit(request);
	}

	@Test(expected = MissingOperationDetailsException.class)
	public void requestNullTest() {
		request = null;
		service.createDeposit(request);
	}

	@Test(expected = NegativeAmountException.class)
	public void negativeAmountTest() {
		request.setAmount(new Double(-10));
		service.createDeposit(request);
	}

	@Test(expected = WrongUserException.class)
	public void wrongUserTest() {
		request.setPersonalId("WrongUser");
		service.createDeposit(request);
	}

	
	@Test
	public void positiveDepositTest() {
		service.createDeposit(request);
	}

	@Test
	public void positiveZeroAmountTest() {
		request.setAmount(new Double(0));
		service.createDeposit(request);
	}
}
