package com.solutions.bankapp.test.service;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.solutions.bankapp.dto.request.CreateDepositRequestDto;
import com.solutions.bankapp.exception.MissingOperationDetailsException;
import com.solutions.bankapp.exception.NegativeAmountException;
import com.solutions.bankapp.exception.WrongAccountException;
import com.solutions.bankapp.exception.WrongUserException;
import com.solutions.bankapp.service.checker.CreateDepositRequestChecker;
import com.solutions.bankapp.service.checker.impl.CreateDepositRequestCheckerImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = OperationTestConfiguration.class)
public class CreateDepositServiceRequestCheckerTest {

	@Autowired
	private CreateDepositRequestChecker checker;
	
	private CreateDepositRequestDto request;
	
	@Before
	public void createRequest () {
		request = new CreateDepositRequestDto();
		request.setAmount(new Double(10));
		request.setName("Alex");
		request.setSurname("Perkons");
		request.setPersonalId("123-321");
	} 
	
	@Test (expected = MissingOperationDetailsException.class)
	public void amountNullTest() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		request.setAmount(null);
		Method method = CreateDepositRequestCheckerImpl.class.getDeclaredMethod("argumentsNotNull",CreateDepositRequestDto.class);
		method.setAccessible(true);
		Boolean result=true;
		result=(Boolean)method.invoke(checker,request);
		assertFalse(result);
		checker.makeCheck(request);
	}

	@Test (expected = MissingOperationDetailsException.class)
	public void nameNullTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		request.setName(null);		
		Method method = CreateDepositRequestCheckerImpl.class.getDeclaredMethod("argumentsNotNull",CreateDepositRequestDto.class);
		method.setAccessible(true);
		Boolean result=true;
		result=(Boolean)method.invoke(checker,request);
		assertFalse(result);		
		checker.makeCheck(request);
	}

	@Test (expected = MissingOperationDetailsException.class)
	public void surnameNullTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		request.setSurname(null);		
		Method method = CreateDepositRequestCheckerImpl.class.getDeclaredMethod("argumentsNotNull",CreateDepositRequestDto.class);
		method.setAccessible(true);
		Boolean result=true;
		result=(Boolean)method.invoke(checker,request);
		assertFalse(result);
		checker.makeCheck(request);
	}
	
	@Test (expected = MissingOperationDetailsException.class)
	public void personaIdNullTest() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		request.setPersonalId(null);
		Method method = CreateDepositRequestCheckerImpl.class.getDeclaredMethod("argumentsNotNull",CreateDepositRequestDto.class);
		method.setAccessible(true);
		Boolean result=true;
		result=(Boolean)method.invoke(checker,request);
		assertFalse(result);
		checker.makeCheck(request);
	}
	
	@Test (expected = MissingOperationDetailsException.class)
	public void requestNullTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		request = null;
		Method method = CreateDepositRequestCheckerImpl.class.getDeclaredMethod("argumentsNotNull",CreateDepositRequestDto.class);
		method.setAccessible(true);
		Boolean result=true;
		result=(Boolean)method.invoke(checker,request);
		assertFalse(result);
		checker.makeCheck(request);
	}
	
	@Test (expected = NegativeAmountException.class)
	public void negativeAmountTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		request.setAmount(new Double(-10));
		Method method = CreateDepositRequestCheckerImpl.class.getDeclaredMethod("amountNotNegative",CreateDepositRequestDto.class);
		method.setAccessible(true);
		Boolean result=true;
		result=(Boolean)method.invoke(checker,request);
		assertFalse(result);
		checker.makeCheck(request);
	}

	@Test(expected = WrongUserException.class)
	public void wrongUserTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		request.setPersonalId("WrongUser");
		Method method = CreateDepositRequestCheckerImpl.class.getDeclaredMethod("userCorrect",CreateDepositRequestDto.class);
		method.setAccessible(true);
		Boolean result=true;
		result=(Boolean)method.invoke(checker,request);
		assertFalse(result);
		checker.makeCheck(request);
	}
	
	@Test(expected = WrongAccountException.class)
	public void wrongAccountTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		request.setPersonalId("WrongAccountUser");
		
		Method method = CreateDepositRequestCheckerImpl.class.getDeclaredMethod("accountCorrect",CreateDepositRequestDto.class);
		method.setAccessible(true);
		Boolean result=true;
		result=(Boolean)method.invoke(checker,request);
		assertFalse(result);
		checker.makeCheck(request);
	}
	
	@Test 
	public void positiveCheckTest() {
		assertTrue(checker.makeCheck(request));
	}
}
