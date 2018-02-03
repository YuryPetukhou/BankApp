package com.solutions.bankapp.dao;

import com.solutions.bankapp.dto.request.CreateDepositRequestDto;
import com.solutions.bankapp.dto.request.CreateWithdrawalRequestDto;
import com.solutions.bankapp.entity.Account;

public interface AccountDAO {
	boolean checkAccountByUserPersonalId(String userId);
	Account getAccountByUserPersonalId(String personalId);
	Double getAccountBalance(String personalId);
	void decreaseBalance(CreateWithdrawalRequestDto request);
	void increaseBalance(CreateDepositRequestDto request);
}
