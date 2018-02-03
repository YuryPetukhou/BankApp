package com.solutions.bankapp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

public class BaseResponseDto {
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
