package com.solutions.bankapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solutions.bankapp.dto.request.CreateDepositRequestDto;
import com.solutions.bankapp.dto.request.CreateWithdrawalRequestDto;
import com.solutions.bankapp.dto.request.GetAccountUserOperationsRequestDto;
import com.solutions.bankapp.dto.request.GetOperationsRequestDto;
import com.solutions.bankapp.dto.response.BaseResponseDto;
import com.solutions.bankapp.dto.response.CreateDepositResonseDto;
import com.solutions.bankapp.dto.response.CreateWithdrawalResponseDto;
import com.solutions.bankapp.dto.response.GetOperationsResponseDto;
import com.solutions.bankapp.service.CreateDepositService;
import com.solutions.bankapp.service.CreateWithdrawalService;
import com.solutions.bankapp.service.GetUserOperationsService;
import com.solutions.bankapp.service.GetOpertationsService;
import com.solutions.bankapp.dto.response.GetAccountUserOperationsResponseDto;

@RestController
@RequestMapping("/api")
public class OperationController {

	@Autowired
	private CreateDepositService depositService;
	@Autowired
	private CreateWithdrawalService withdrawalService;
	@Autowired
	private GetOpertationsService getOperationsService;
	@Autowired
	private GetUserOperationsService getUserOperationsService;

	@RequestMapping("/deposit")
	public CreateDepositResonseDto createDeposit(CreateDepositRequestDto request) {
		CreateDepositResonseDto response = new CreateDepositResonseDto();
		response = depositService.createDeposit(request);
		return response;
	}

	@RequestMapping("/withdrawal")
	public CreateWithdrawalResponseDto createWithdrawal(CreateWithdrawalRequestDto request) {
		CreateWithdrawalResponseDto response = new CreateWithdrawalResponseDto();
		response = withdrawalService.createWithdrawal(request);
		return response;
	}

	@RequestMapping("/operationlist")
	public GetOperationsResponseDto getAllOperations(GetOperationsRequestDto request) {
		GetOperationsResponseDto response = new GetOperationsResponseDto();
		response = getOperationsService.getOperations(request);
		return response;
	}

	@RequestMapping("/operationuserlist")
	public GetAccountUserOperationsResponseDto getUserOperations(GetAccountUserOperationsRequestDto request) {
		GetAccountUserOperationsResponseDto response = new GetAccountUserOperationsResponseDto();
		response = getUserOperationsService.getOperations(request);
		return response;
	}

	@ExceptionHandler
	public BaseResponseDto handleException(Exception e) {
		BaseResponseDto errorResponse = new BaseResponseDto();
		errorResponse.setErrorMessage(e.getMessage());
		return errorResponse;
	}
}
