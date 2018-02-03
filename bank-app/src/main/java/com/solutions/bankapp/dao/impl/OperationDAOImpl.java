package com.solutions.bankapp.dao.impl;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.solutions.bankapp.dao.AccountDAO;
import com.solutions.bankapp.dao.OperationDAO;
import com.solutions.bankapp.dao.UserDAO;
import com.solutions.bankapp.dto.request.CreateDepositRequestDto;
import com.solutions.bankapp.dto.request.CreateWithdrawalRequestDto;
import com.solutions.bankapp.dto.request.GetAccountUserOperationsRequestDto;
import com.solutions.bankapp.dto.request.GetOperationsRequestDto;
import com.solutions.bankapp.entity.Account;
import com.solutions.bankapp.entity.Operation;
import com.solutions.bankapp.entity.User;
import com.solutions.bankapp.exception.MissingOperationDetailsException;
import com.solutions.bankapp.exception.WrongAccountException;
import com.solutions.bankapp.exception.WrongUserException;

@Repository
public class OperationDAOImpl implements OperationDAO {

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private AccountDAO accountDAO;

	@Override
	public Operation createDeposit(CreateDepositRequestDto request) {
		if (request == null) {
			throw new MissingOperationDetailsException();
		}
		Operation deposit = buildDeposit(request);
		Session session = sessionFactory.getCurrentSession();
		session.save(deposit);
		return deposit;
	}

	private Operation buildDeposit(CreateDepositRequestDto request) {
		Operation deposit = new Operation();
		deposit.setCreatedBy("Auto");
		deposit.setCreatedDateTime(LocalDateTime.now());
		deposit.setUpdatedBy("Auto");
		deposit.setUpdatedDateTime(LocalDateTime.now());
		deposit.setAmount(request.getAmount());
		User user = userDAO.getUserByPersonalId(request.getPersonalId());
		if (user != null) {
			deposit.setUser(user);
			Account account = accountDAO.getAccountByUserPersonalId(request.getPersonalId());
			if (account != null) {
				deposit.setAccount(account);
			} else {
				throw new WrongAccountException();
			}
		} else {
			throw new WrongUserException();
		}
		deposit.setName(request.getName());
		deposit.setSurname(request.getSurname());
		return deposit;
	}

	@Override
	public Operation createWithdrawal(CreateWithdrawalRequestDto request) {
		if (request == null) {
			throw new MissingOperationDetailsException();
		}
		Operation withdrawal = buildWithdrawal(request);
		Session session = sessionFactory.getCurrentSession();
		session.save(withdrawal);
		return withdrawal;
	}

	private Operation buildWithdrawal(CreateWithdrawalRequestDto request) {
		Operation withdrawal = new Operation();
		withdrawal.setCreatedBy("Auto");
		withdrawal.setCreatedDateTime(LocalDateTime.now());
		withdrawal.setUpdatedBy("Auto");
		withdrawal.setUpdatedDateTime(LocalDateTime.now());
		withdrawal.setAmount(request.getAmount());
		User user = userDAO.getUserByPersonalId(request.getPersonalId());
		if (user != null) {
			withdrawal.setUser(user);
			Account account = accountDAO.getAccountByUserPersonalId(user.getPersonalId());
			if (account != null) {
				withdrawal.setAccount(account);
			} else {
				throw new WrongAccountException();
			}
		} else {
			throw new WrongUserException();
		}
		return withdrawal;
	}

	@Override
	public List<Operation> getAccountOperations(GetOperationsRequestDto request) {
		if (request==null) {
			throw new MissingOperationDetailsException();
		}
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Operation> criteria = builder.createQuery(Operation.class);
		Root<Operation> operationRoot = criteria.from(Operation.class);
		criteria.select(operationRoot);
		Query query = session.createQuery(criteria);		
		List<Operation> operations = (List<Operation>)query.getResultList();
		return operations;
	}

	@Override
	public List<Operation> getAccountUserOperations(GetAccountUserOperationsRequestDto request) {
		if (request==null) {
			throw new MissingOperationDetailsException();
		}
		if (request.getUserId()==null) {
			throw new MissingOperationDetailsException();
		}
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Operation> criteria = builder.createQuery(Operation.class);	
		Root<Operation> operationRoot = criteria.from(Operation.class);
		criteria.select(operationRoot).where(builder.equal(operationRoot.get("user").get("personalId"), request.getUserId()));
		Query query = session.createQuery(criteria);
		List<Operation> operations = (List<Operation>)query.getResultList();
		return operations;
	}
}
