package com.solutions.bankapp.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solutions.bankapp.dao.OperationDAO;
import com.solutions.bankapp.dto.request.GetAccountUserOperationsRequestDto;
import com.solutions.bankapp.dto.response.GetAccountUserOperationsResponseDto;
import com.solutions.bankapp.service.GetUserOperationsService;

@Service
@Transactional
public class GetUserOperationsServiceImpl implements GetUserOperationsService {

	@Autowired
	private OperationDAO operationDAO;
	
	@Override
	public GetAccountUserOperationsResponseDto getOperations(GetAccountUserOperationsRequestDto request) {
		GetAccountUserOperationsResponseDto response = new GetAccountUserOperationsResponseDto();
		response.setOperations(operationDAO.getAccountUserOperations(request));
		return response;
	}

}
