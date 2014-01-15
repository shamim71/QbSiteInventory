package com.versacomllc.qb.model;

public class AuthenticationResponse extends AbstractResponse{

	private AuthenticationResult result;

	public AuthenticationResult getResult() {
		return result;
	}

	public void setResult(AuthenticationResult result) {
		this.result = result;
	}
	
}
