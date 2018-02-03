package com.solutions.bankapp.test.dao;

import static org.junit.Assert.*;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.solutions.bankapp.config.HibernateConfiguration;
import com.solutions.bankapp.dao.AccountDAO;
import com.solutions.bankapp.dto.request.CreateDepositRequestDto;
import com.solutions.bankapp.dto.request.CreateWithdrawalRequestDto;
import com.solutions.bankapp.entity.Account;
import com.solutions.bankapp.entity.User;
import com.solutions.bankapp.exception.WrongAccountException;
import com.solutions.bankapp.exception.WrongUserException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DAOTestConfiguration.class, HibernateConfiguration.class})
@Transactional
public class AccountDAOTest {
	
	@Autowired
	private AccountDAO dao;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Account account;
	private User user;
	
	@Before
	public void prepareTestTable() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "DELETE FROM User";
		Query query = session.createQuery(hql);
		query.executeUpdate();
		
		user=new User();
		user.setPersonalId("User");
		session.save(user);
		
		account = new Account();
		account.setUser(user);
		account.setBalance(new Double(100));
		session.save(account);
	}
	
	@Test(expected = WrongAccountException.class)
	public void nullCheckUserTest() {
		dao.checkAccountByUserPersonalId(null);
	}
	
	@Test(expected = WrongAccountException.class)
	public void negativeCheckUserTest() {
		String id ="WrongUser";
		dao.checkAccountByUserPersonalId(id);
	}
	
	@Test(expected = WrongAccountException.class)
	public void wrongBalanceTest() {
		String id ="WrongUser";
		dao.getAccountBalance(id);
	}
	
	@Test 
	public void positiveBalanceTest() {
		String id ="User";
		assertNotNull(dao.getAccountBalance(id));
	}
	
	@Test
	public void positiveCheckUserTest() {
		String id ="User";		
		assertTrue(dao.checkAccountByUserPersonalId(id));
	}

	@Test
	public void positiveIncreaseAmountTest() {
		Double initialAmount=account.getBalance();
		Double deposit = new Double(10);
		CreateDepositRequestDto request = new CreateDepositRequestDto();		
		request.setAmount(deposit);
		request.setPersonalId(user.getPersonalId());
		dao.increaseBalance(request);
		Account refreshedAccount = dao.getAccountByUserPersonalId(user.getPersonalId());
		Double difference=refreshedAccount.getBalance()-initialAmount;
		assertEquals(deposit, difference);
	}

	@Test
	public void positiveDecreaseAmountTest() {
		Double initialAmount=account.getBalance();
		Double withdrawal = new Double(10);
		CreateWithdrawalRequestDto request = new CreateWithdrawalRequestDto();		
		request.setAmount(withdrawal);
		request.setPersonalId(user.getPersonalId());
		dao.decreaseBalance(request);
		Account refreshedAccount = dao.getAccountByUserPersonalId(user.getPersonalId());
		Double difference=initialAmount-refreshedAccount.getBalance();
		assertEquals(withdrawal, difference);
	}
	
	@Test(expected = WrongAccountException.class)
	public void negativeIncreaseAmountTest() {
		Double deposit = new Double(10);
		CreateDepositRequestDto request = new CreateDepositRequestDto();		
		request.setAmount(deposit);
		request.setPersonalId("WrongUser!");
		dao.increaseBalance(request);

	}

	@Test(expected = WrongAccountException.class)
	public void negativeDecreaseAmountTest() {
		Double withdrawal = new Double(10);
		CreateWithdrawalRequestDto request = new CreateWithdrawalRequestDto();		
		request.setAmount(withdrawal);
		request.setPersonalId("WrongUser!");
		dao.decreaseBalance(request);
	}

}
