package com.solutions.bankapp.dao;

import java.util.List;

import com.solutions.bankapp.dto.request.CreateDepositRequestDto;
import com.solutions.bankapp.dto.request.CreateWithdrawalRequestDto;
import com.solutions.bankapp.dto.request.GetOperationsRequestDto;
import com.solutions.bankapp.dto.request.GetAccountUserOperationsRequestDto;
import com.solutions.bankapp.entity.Operation;

public interface OperationDAO {
	Operation createDeposit (CreateDepositRequestDto request);
	Operation createWithdrawal (CreateWithdrawalRequestDto request);	
	
	List<Operation> getAccountOperations(GetOperationsRequestDto request);
	List<Operation> getAccountUserOperations(GetAccountUserOperationsRequestDto request);
}
