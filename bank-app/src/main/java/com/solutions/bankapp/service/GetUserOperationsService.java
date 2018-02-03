package com.solutions.bankapp.service;

import com.solutions.bankapp.dto.request.GetAccountUserOperationsRequestDto;
import com.solutions.bankapp.dto.response.GetAccountUserOperationsResponseDto;

public interface GetUserOperationsService {
	GetAccountUserOperationsResponseDto getOperations(GetAccountUserOperationsRequestDto request);
}
