package com.solutions.bankapp.dao.impl;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.solutions.bankapp.dao.UserDAO;
import com.solutions.bankapp.entity.User;
import com.solutions.bankapp.exception.WrongUserException;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean checkUserPersonalId(String personalId) {
		return getUserByPersonalId(personalId)!=null;
	}

	@Override
	public User getUserByPersonalId(String personalId) {
		if (personalId==null) {
			throw new WrongUserException();
		}
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> userRoot = criteria.from(User.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("personalId"), personalId));
		Query query = session.createQuery(criteria);
		if (query.getResultList().size() > 0) {
			return (User) query.getSingleResult(); 
		} else {
			throw new WrongUserException();
		}
	}
}
