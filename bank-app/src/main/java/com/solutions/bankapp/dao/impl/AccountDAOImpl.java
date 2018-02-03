package com.solutions.bankapp.dao.impl;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.solutions.bankapp.dao.AccountDAO;
import com.solutions.bankapp.dto.request.CreateDepositRequestDto;
import com.solutions.bankapp.dto.request.CreateWithdrawalRequestDto;
import com.solutions.bankapp.entity.Account;
import com.solutions.bankapp.exception.WrongAccountException;

@Repository
public class AccountDAOImpl implements AccountDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean checkAccountByUserPersonalId(String personalId) {
		return getAccountByUserPersonalId(personalId) != null;
	}

	@Override
	public Account getAccountByUserPersonalId(String personalId) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Account> criteria = builder.createQuery(Account.class);
		Root<Account> accountRoot = criteria.from(Account.class);
		criteria.select(accountRoot).where(builder.equal(accountRoot.get("user").get("personalId"), personalId));
		Query query = session.createQuery(criteria);
		if (query.getResultList().size() > 0) {
			return (Account) query.getSingleResult();
		} else {
			throw new WrongAccountException();
		}

	}

	@Override
	public Double getAccountBalance(String personalId) {
		Account account = getAccountByUserPersonalId(personalId);
		return account.getBalance();
	}

	@Override
	public void decreaseBalance(CreateWithdrawalRequestDto request) {
		Account account = getAccountByUserPersonalId(request.getPersonalId());
		Session session = sessionFactory.getCurrentSession();
		account.setBalance(account.getBalance() - request.getAmount());
		session.saveOrUpdate(account);
	}

	@Override
	public void increaseBalance(CreateDepositRequestDto request) {
		Account account = getAccountByUserPersonalId(request.getPersonalId());
		Session session = sessionFactory.getCurrentSession();
		account.setBalance(account.getBalance() + request.getAmount());
		session.saveOrUpdate(account);
	}

}
