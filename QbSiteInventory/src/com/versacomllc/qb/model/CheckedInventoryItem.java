package com.versacomllc.qb.model;

public class CheckedInventoryItem extends ItemInventory {

	private int count;

	public CheckedInventoryItem(int count) {
		super();
		this.count = count;
	}
	public CheckedInventoryItem(ItemInventory item, int count) {
		this.setAssetAccountFullName(item.getAssetAccountFullName());
		this.setAssetAccountListID(item.getAssetAccountListID());
		this.setBarCodeValue(item.getBarCodeValue());
		this.setClassRefFullName(item.getClassRefFullName());
		this.setClassRefListID(item.getClassRefListID());
		this.setcOGSAccountFullName(item.getcOGSAccountFullName());
		this.setcOGSAccountListID(item.getcOGSAccountListID());
		this.setFullName(item.getFullName());
		this.setId(item.getId());
		this.setIncomeAccountFullName(item.getIncomeAccountFullName());
		this.setIncomeAccountListID(item.getIncomeAccountListID());
		this.setIsActive(item.getIsActive());
		this.setListID(item.getListID());
		this.setManufacturerPartNumber(item.getManufacturerPartNumber());
		this.setMax(item.getMax());
		this.setName(item.getName());
		this.setParentRefFullName(item.getParentRefFullName());
		this.setParentRefListID(item.getParentRefListID());
		this.setPrefVendorFullName(item.getPrefVendorFullName());
		this.setPrefVendorListID(item.getPrefVendorListID());
		this.setQuantityOnHand(item.getQuantityOnHand());
		this.setUnitOfMeasureSetFullName(item.getUnitOfMeasureSetFullName());
		this.setUnitOfMeasureSetListID(item.getUnitOfMeasureSetListID());
		this.count = count;
	}
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
