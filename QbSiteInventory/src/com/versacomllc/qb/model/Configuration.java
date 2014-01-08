package com.versacomllc.qb.model;



public class Configuration{


	private Long id;

	private String accountRefListId;
	

	private String accountRefFullName;


	private String accountNo;
	
	public String getAccountRefListId() {
		return accountRefListId;
	}

	public void setAccountRefListId(String accountRefListId) {
		this.accountRefListId = accountRefListId;
	}

	public String getAccountRefFullName() {
		return accountRefFullName;
	}

	public void setAccountRefFullName(String accountRefFullName) {
		this.accountRefFullName = accountRefFullName;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
}
