package com.solutions.bankapp.dto.response;

import java.util.List;

import com.solutions.bankapp.entity.Operation;

public class GetAccountUserOperationsResponseDto extends BaseResponseDto{
	
	private List<Operation> operations;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((operations == null) ? 0 : operations.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GetAccountUserOperationsResponseDto other = (GetAccountUserOperationsResponseDto) obj;
		if (operations == null) {
			if (other.operations != null)
				return false;
		} else if (!operations.equals(other.operations))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GetAccountUserOperationsResponseDto [operations=" + operations + "]";
	}

	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}
}
