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
	
	private String userId;
	
	private String customerSiteAccessId;
	
	public String getCustomerSiteAccessId() {
		return customerSiteAccessId;
	}

	public void setCustomerSiteAccessId(String customerSiteAccessId) {
		this.customerSiteAccessId = customerSiteAccessId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

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

		public String getListID() {
			return listID;
		}

		public void setListID(String listID) {
			this.listID = listID;
		}

		public String getFullName() {
			return fullName;
		}

		public void setFullName(String fullName) {
			this.fullName = fullName;
		}

		public int getNewQuantity() {
			return newQuantity;
		}

		public void setNewQuantity(int newQuantity) {
			this.newQuantity = newQuantity;
		}

		public int getQuantityDifference() {
			return quantityDifference;
		}

		public void setQuantityDifference(int quantityDifference) {
			this.quantityDifference = quantityDifference;
		}

		public String getSerialNumber() {
			return serialNumber;
		}

		public void setSerialNumber(String serialNumber) {
			this.serialNumber = serialNumber;
		}

		public String getLotNumber() {
			return lotNumber;
		}

		public void setLotNumber(String lotNumber) {
			this.lotNumber = lotNumber;
		}

		public String getInventorySiteLocationRefListID() {
			return inventorySiteLocationRefListID;
		}

		public void setInventorySiteLocationRefListID(
				String inventorySiteLocationRefListID) {
			this.inventorySiteLocationRefListID = inventorySiteLocationRefListID;
		}

		public String getInventorySiteLocationRefFullName() {
			return inventorySiteLocationRefFullName;
		}

		public void setInventorySiteLocationRefFullName(
				String inventorySiteLocationRefFullName) {
			this.inventorySiteLocationRefFullName = inventorySiteLocationRefFullName;
		}
        
    }

    public static class InventoryAdjustmentResponse
    {
        public String txnID;

        public int txnNumber;
        
        private String status;
        
        private long id;

		public String getTxnID() {
			return txnID;
		}

		public void setTxnID(String txnID) {
			this.txnID = txnID;
		}

		public int getTxnNumber() {
			return txnNumber;
		}

		public void setTxnNumber(int txnNumber) {
			this.txnNumber = txnNumber;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}
        
    }
    
}
