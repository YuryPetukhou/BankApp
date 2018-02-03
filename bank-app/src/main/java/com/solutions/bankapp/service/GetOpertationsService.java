package com.solutions.bankapp.service;

import com.solutions.bankapp.dto.request.GetOperationsRequestDto;
import com.solutions.bankapp.dto.response.GetOperationsResponseDto;

public interface GetOpertationsService {
	GetOperationsResponseDto getOperations(GetOperationsRequestDto request);
}
