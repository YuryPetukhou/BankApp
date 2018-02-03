package com.solutions.bankapp.service;

import com.solutions.bankapp.dto.request.CreateDepositRequestDto;
import com.solutions.bankapp.dto.response.CreateDepositResonseDto;

public interface CreateDepositService {
	CreateDepositResonseDto createDeposit (CreateDepositRequestDto request);
}
