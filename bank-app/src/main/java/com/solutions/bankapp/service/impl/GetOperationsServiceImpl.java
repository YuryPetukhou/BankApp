package com.solutions.bankapp.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solutions.bankapp.dao.OperationDAO;
import com.solutions.bankapp.dto.request.GetOperationsRequestDto;
import com.solutions.bankapp.dto.response.GetOperationsResponseDto;
import com.solutions.bankapp.service.GetOpertationsService;

@Service
@Transactional
public class GetOperationsServiceImpl implements GetOpertationsService {
	@Autowired
	private OperationDAO operationDAO;
	
	@Override
	public GetOperationsResponseDto getOperations(GetOperationsRequestDto request) {
		GetOperationsResponseDto response = new GetOperationsResponseDto();
		response.setOperations(operationDAO.getAccountOperations(request));
		return response;
	}

}
