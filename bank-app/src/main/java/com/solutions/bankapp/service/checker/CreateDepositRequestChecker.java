package com.solutions.bankapp.service.checker;

import com.solutions.bankapp.dto.request.CreateDepositRequestDto;

public interface CreateDepositRequestChecker {
	boolean makeCheck (CreateDepositRequestDto request);
	
}
