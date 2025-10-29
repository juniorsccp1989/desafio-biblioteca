package com.desafio.biblioteca.dto;

import java.util.List;

public class LoanBatchRequestDto {
    private String requestId;
    private List<LoanRequestDto> loans;
    
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public List<LoanRequestDto> getLoans() {
		return loans;
	}
	public void setLoans(List<LoanRequestDto> loans) {
		this.loans = loans;
	}
}
