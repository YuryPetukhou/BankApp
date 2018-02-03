package com.solutions.bankapp.test.service;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.solutions.bankapp.dao.AccountDAO;
import com.solutions.bankapp.dto.request.CreateWithdrawalRequestDto;
import com.solutions.bankapp.dto.request.CreateWithdrawalRequestDto;
import com.solutions.bankapp.exception.MissingOperationDetailsException;
import com.solutions.bankapp.exception.NegativeAmountException;
import com.solutions.bankapp.exception.WithdrawalAmountException;
import com.solutions.bankapp.exception.WrongAccountException;
import com.solutions.bankapp.exception.WrongUserException;
import com.solutions.bankapp.service.checker.CreateDepositRequestChecker;
import com.solutions.bankapp.service.checker.CreateWithdrawalRequestChecker;
import com.solutions.bankapp.service.checker.impl.CreateDepositRequestCheckerImpl;
import com.solutions.bankapp.service.checker.impl.CreateWithdrawalRequestCheckerImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = OperationTestConfiguration.class)
public class CreateWithdrawalServiceCheckerTest {

	@Autowired
	private CreateWithdrawalRequestChecker checker;

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
	public void amountNullTest() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		request.setAmount(null);
		Method method = CreateWithdrawalRequestCheckerImpl.class.getDeclaredMethod("argumentsNotNull",
				CreateWithdrawalRequestDto.class);
		method.setAccessible(true);
		Boolean result = true;
		result = (Boolean) method.invoke(checker, request);
		assertFalse(result);
		checker.makeCheck(request);

	}

	@Test(expected = MissingOperationDetailsException.class)
	public void personaIdNullTest() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		request.setPersonalId(null);
		Method method = CreateWithdrawalRequestCheckerImpl.class.getDeclaredMethod("argumentsNotNull",
				CreateWithdrawalRequestDto.class);
		method.setAccessible(true);
		Boolean result = true;
		result = (Boolean) method.invoke(checker, request);
		assertFalse(result);
		checker.makeCheck(request);

	}

	@Test(expected = MissingOperationDetailsException.class)
	public void requestNullTest() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		request = null;
		Method method = CreateWithdrawalRequestCheckerImpl.class.getDeclaredMethod("argumentsNotNull",
				CreateWithdrawalRequestDto.class);
		method.setAccessible(true);
		Boolean result = true;
		result = (Boolean) method.invoke(checker, request);
		assertFalse(result);
		checker.makeCheck(request);

	}

	@Test(expected = NegativeAmountException.class)
	public void negativeAmountTest() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		request.setAmount(new Double(-10));
		Method method = CreateWithdrawalRequestCheckerImpl.class.getDeclaredMethod("amountNotNegative",
				CreateWithdrawalRequestDto.class);
		method.setAccessible(true);
		Boolean result = true;
		result = (Boolean) method.invoke(checker, request);
		assertFalse(result);
		checker.makeCheck(request);
	}

	@Test(expected = WrongAccountException.class)
	public void wrongAccountTest() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		request.setPersonalId("WrongAccountUser");
		when(accountDAO.getAccountBalance(anyString())).thenReturn(request.getAmount() + 10);
		Method method = CreateWithdrawalRequestCheckerImpl.class.getDeclaredMethod("accountCorrect",
				CreateWithdrawalRequestDto.class);
		method.setAccessible(true);
		Boolean result = true;
		result = (Boolean) method.invoke(checker, request);
		assertFalse(result);
		checker.makeCheck(request);
	}

	@Test(expected = WrongUserException.class)
	public void wrongUserTest() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		request.setPersonalId("WrongUser");
		when(accountDAO.getAccountBalance(anyString())).thenReturn(request.getAmount() + 10);
		Method method = CreateWithdrawalRequestCheckerImpl.class.getDeclaredMethod("userCorrect",
				CreateWithdrawalRequestDto.class);
		method.setAccessible(true);
		Boolean result = true;
		result = (Boolean) method.invoke(checker, request);
		assertFalse(result);
		checker.makeCheck(request);
	}

	@Test(expected = WithdrawalAmountException.class)
	public void amountExceedTest() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		request.setAmount(new Double(10));
		when(accountDAO.getAccountBalance(anyString())).thenReturn(request.getAmount() - 10);
		Method method = CreateWithdrawalRequestCheckerImpl.class.getDeclaredMethod("amountNotExceedCurrent",
				CreateWithdrawalRequestDto.class);
		method.setAccessible(true);
		Boolean result = true;
		result = (Boolean) method.invoke(checker, request);
		assertFalse(result);
		checker.makeCheck(request);
	}

	
	@Test
	public void positiveCheckTest() {
		when(accountDAO.getAccountBalance(anyString())).thenReturn(request.getAmount() + 10);
		assertTrue(checker.makeCheck(request));
	}

}
