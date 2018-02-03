package com.solutions.bankapp.test.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.solutions.bankapp.dao.OperationDAO;
import com.solutions.bankapp.dto.request.CreateDepositRequestDto;
import com.solutions.bankapp.dto.request.CreateWithdrawalRequestDto;
import com.solutions.bankapp.dto.request.GetOperationsRequestDto;
import com.solutions.bankapp.dto.request.GetAccountUserOperationsRequestDto;
import com.solutions.bankapp.entity.Account;
import com.solutions.bankapp.entity.Operation;
import com.solutions.bankapp.entity.User;
import com.solutions.bankapp.exception.MissingOperationDetailsException;
import com.solutions.bankapp.exception.WrongAccountException;
import com.solutions.bankapp.exception.WrongUserException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DAOTestConfiguration.class, HibernateConfiguration.class })
@Transactional
public class OperationDAOTest {
	@Autowired
	private OperationDAO dao;

	private CreateDepositRequestDto depositRequest;
	private CreateWithdrawalRequestDto withdrawalRequest;
	private Account account;
	private User user;
	private List<Operation> operations;

	@Autowired
	private SessionFactory sessionFactory;

	@Before
	public void prepareTest() {
		prepareTestTable();
		createDepositDto();
		createWithdrawalDto();
		operations = new ArrayList<Operation>();
	}

	public void prepareTestTable() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "DELETE FROM User";
		Query query = session.createQuery(hql);
		query.executeUpdate();

		user = new User();
		user.setPersonalId("User");
		session.save(user);

		account = new Account();
		account.setUser(user);
		account.setBalance(new Double(100));
		session.save(account);
	}

	public void createDepositDto() {
		depositRequest = new CreateDepositRequestDto();
		depositRequest.setAmount(new Double(10));
		depositRequest.setName("Alex");
		depositRequest.setSurname("Perkons");
		depositRequest.setPersonalId(user.getPersonalId());
	}

	public void createWithdrawalDto() {
		withdrawalRequest = new CreateWithdrawalRequestDto();
		withdrawalRequest.setAmount(new Double(10));
		withdrawalRequest.setPersonalId(user.getPersonalId());
	}

	@Test(expected = MissingOperationDetailsException.class)
	public void nullValueDepositTest() {
		Operation deposit = dao.createDeposit(null);
		assertNull(deposit.getId());
	}

	@Test(expected = MissingOperationDetailsException.class)
	public void nullValueWithdrawalTest() {
		Operation withdrawal = dao.createWithdrawal(null);
		assertNull(withdrawal.getId());
	}

	@Test(expected = WrongUserException.class)
	public void depositWrongUserTest() {
		depositRequest.setPersonalId("WrongUser");
		Operation operation = dao.createDeposit(depositRequest);
	}

	@Test(expected = WrongUserException.class)
	public void withdrawalWrongUserTest() {
		withdrawalRequest.setPersonalId("WrongUser");
		dao.createWithdrawal(withdrawalRequest);
	}

	@Test(expected = WrongAccountException.class)
	public void withdrawalNullUserAccountTest() {
		Session session = sessionFactory.getCurrentSession();
		account.setUser(null);
		session.save(account);
		dao.createWithdrawal(withdrawalRequest);
	}

	@Test
	public void positiveDepositCheckUserTest() {
		Operation deposit = dao.createDeposit(depositRequest);
		assertNotNull(deposit.getId());
	}

	@Test
	public void positiveGetAllOperationsTest() {
		prepareOperationsList();
		GetOperationsRequestDto request = new GetOperationsRequestDto();

		List<Operation> resultList = dao.getAccountOperations(request);
		assertEquals(operations.size(), resultList.size());
	}

	@Test
	public void positiveGetUserOperationsTest() {
		prepareOperationsList();
		GetAccountUserOperationsRequestDto request = new GetAccountUserOperationsRequestDto();
		request.setUserId(user.getPersonalId());
		List<Operation> resultList = dao.getAccountUserOperations(request);
		List<Operation> expectedList = operations.stream().filter(o -> user.equals(o.getUser()))
				.collect(Collectors.toList());
		assertEquals(expectedList.size(), resultList.size());
	}

	private void prepareOperationsList() {
		Session session = sessionFactory.getCurrentSession();
		for (int i = 0; i < 5; ++i) {
			Operation operation = new Operation();
			operation.setUser(user);
			operation.setAmount(new Double(100));
			operation.setType("Deposit");
			operation.setAccount(account);
			operations.add(operation);
			session.save(operation);
		}
		User secondUser = new User();
		session.save(secondUser);
		Account secondAccount = new Account();
		session.save(secondAccount);
		for (int i = 0; i < 8; ++i) {
			Operation operation = new Operation();
			operation.setUser(secondUser);
			operation.setAmount(new Double(100));
			operation.setType("Deposit");
			operation.setAccount(secondAccount);
			operations.add(operation);
			session.save(operation);
		}
	}

	@Test(expected = MissingOperationDetailsException.class)
	public void negativeGetAllOperationsTest() {
		dao.getAccountOperations(null);
	}

	@Test
	public void positiveWithdrawalCheckUserTest() {
		Operation withdrawal = dao.createWithdrawal(withdrawalRequest);
		assertNotNull(withdrawal.getId());
	}

}
