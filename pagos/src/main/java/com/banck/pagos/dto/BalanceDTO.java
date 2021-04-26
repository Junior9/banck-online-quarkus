package com.banck.pagos.dto;

public class BalanceDTO {
	
	private String userId;
	private Boolean error;
	private Integer balance;

	public BalanceDTO() {}
	
	public BalanceDTO(String userId, Boolean error, Integer balance) {
		super();
		this.userId = userId;
		this.error = error;
		this.balance = balance;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	

	
}
