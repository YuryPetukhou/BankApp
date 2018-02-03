package com.solutions.bankapp.dao;

import com.solutions.bankapp.entity.User;

public interface UserDAO {
	boolean checkUserPersonalId (String personalId);
	User getUserByPersonalId(String personalId);
}
