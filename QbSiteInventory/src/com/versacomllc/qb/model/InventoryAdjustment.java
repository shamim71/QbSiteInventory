package com.versacomllc.qb.model;

import java.util.List;

public class InventoryAdjustment {

	private String adjustmentType;
	
	private String accountRefListID;

	private String accountRefFullName;

	private String referenceNo;

	private String inventorySiteRefListID;

	private String inventorySiteRefName;

	private String customerRefListID;

	private String customerRefFullName;

	private String classRefListID;

	private String classRefFullName;

	private String memo;
	
	private List<InventoryAdjustmentLineItem> items;
	

	public List<InventoryAdjustmentLineItem> getItems() {
		return items;
	}

	public void setItems(List<InventoryAdjustmentLineItem> items) {
		this.items = items;
	}

	public String getAdjustmentType() {
		return adjustmentType;
	}

	public void setAdjustmentType(String adjustmentType) {
		this.adjustmentType = adjustmentType;
	}

	public String getAccountRefListID() {
		return accountRefListID;
	}

	public void setAccountRefListID(String accountRefListID) {
		this.accountRefListID = accountRefListID;
	}

	public String getAccountRefFullName() {
		return accountRefFullName;
	}

	public void setAccountRefFullName(String accountRefFullName) {
		this.accountRefFullName = accountRefFullName;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getInventorySiteRefListID() {
		return inventorySiteRefListID;
	}

	public void setInventorySiteRefListID(String inventorySiteRefListID) {
		this.inventorySiteRefListID = inventorySiteRefListID;
	}

	public String getInventorySiteRefName() {
		return inventorySiteRefName;
	}

	public void setInventorySiteRefName(String inventorySiteRefName) {
		this.inventorySiteRefName = inventorySiteRefName;
	}

	public String getCustomerRefListID() {
		return customerRefListID;
	}

	public void setCustomerRefListID(String customerRefListID) {
		this.customerRefListID = customerRefListID;
	}

	public String getCustomerRefFullName() {
		return customerRefFullName;
	}

	public void setCustomerRefFullName(String customerRefFullName) {
		this.customerRefFullName = customerRefFullName;
	}

	public String getClassRefListID() {
		return classRefListID;
	}

	public void setClassRefListID(String classRefListID) {
		this.classRefListID = classRefListID;
	}

	public String getClassRefFullName() {
		return classRefFullName;
	}

	public void setClassRefFullName(String classRefFullName) {
		this.classRefFullName = classRefFullName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	

    public static  class InventoryAdjustmentLineItem
    {
        public String listID;

        public String fullName;

        public int newQuantity;

        public int quantityDifference;

        public String serialNumber;

        public String lotNumber;

        public String inventorySiteLocationRefListID;

        public String inventorySiteLocationRefFullName;
    }

    public static class InventoryAdjustmentResponse
    {
        public String txnID;

        public int txnNumber;
    }
    
}
