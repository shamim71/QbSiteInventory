package com.versacomllc.qb.model;

public class Item {

	private final String code;
	
	private final String description;

	public Item(String code, String description) {
		super();
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

}
