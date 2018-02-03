package com.solutions.bankapp.service.checker;

import com.solutions.bankapp.dto.request.CreateWithdrawalRequestDto;

public interface CreateWithdrawalRequestChecker {
	boolean makeCheck (CreateWithdrawalRequestDto request);
	
}
