package com.solutions.bankapp.service;

import com.solutions.bankapp.dto.request.CreateWithdrawalRequestDto;
import com.solutions.bankapp.dto.response.CreateWithdrawalResponseDto;

public interface CreateWithdrawalService {
	CreateWithdrawalResponseDto createWithdrawal (CreateWithdrawalRequestDto request);
}
