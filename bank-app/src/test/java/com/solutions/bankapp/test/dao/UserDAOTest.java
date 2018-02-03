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
import com.solutions.bankapp.dao.UserDAO;
import com.solutions.bankapp.entity.User;
import com.solutions.bankapp.exception.WrongUserException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DAOTestConfiguration.class, HibernateConfiguration.class})
@Transactional
public class UserDAOTest {

	@Autowired
	private UserDAO dao;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Before
	public void prepareTestTable() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "DELETE FROM User";
		Query query = session.createQuery(hql);
		query.executeUpdate();
		User validUser=new User();
		validUser.setPersonalId("User");
		session.save(validUser);		
	}
	
	@Test(expected = WrongUserException.class)
	public void negativeCheckUserTest() {
		String id ="WrongUser";
		assertFalse(dao.checkUserPersonalId(id));
	}
	
	@Test(expected = WrongUserException.class)
	public void nullIdUserTest() {	
		assertFalse(dao.checkUserPersonalId(null));
	}
	
	@Test
	public void positiveCheckUserTest() {
		String id ="User";		
		assertTrue(dao.checkUserPersonalId(id));
	}
	
	

}
