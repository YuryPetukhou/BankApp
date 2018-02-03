package com.solutions.bankapp.dto.request;

public class CreateWithdrawalRequestDto {
	private Double amount;
	private String personalId;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((personalId == null) ? 0 : personalId.hashCode());
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
		CreateWithdrawalRequestDto other = (CreateWithdrawalRequestDto) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (personalId == null) {
			if (other.personalId != null)
				return false;
		} else if (!personalId.equals(other.personalId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CreateWithdrawalDto [amount=" + amount + ", personalId=" + personalId + "]";
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
}
