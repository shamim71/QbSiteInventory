package com.versacomllc.qb.model;

import java.math.BigDecimal;

public class ItemInventory {

	private Long id;

	private String listID;

	private String name;

	private String fullName;

	private String barCodeValue;

	private String isActive;

	private String classRefListID;

	private String classRefFullName;

	private String parentRefListID;

	private String parentRefFullName;

	protected String manufacturerPartNumber;

	private String unitOfMeasureSetListID;

	private String unitOfMeasureSetFullName;

	private String incomeAccountListID;

	private String incomeAccountFullName;

	private String cOGSAccountListID;

	private String cOGSAccountFullName;

	private String prefVendorListID;

	private String prefVendorFullName;

	private String assetAccountListID;

	private String assetAccountFullName;

	private String max;


	private BigDecimal quantityOnHand;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getListID() {
		return listID;
	}

	public void setListID(String listID) {
		this.listID = listID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getBarCodeValue() {
		return barCodeValue;
	}

	public void setBarCodeValue(String barCodeValue) {
		this.barCodeValue = barCodeValue;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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

	public String getParentRefListID() {
		return parentRefListID;
	}

	public void setParentRefListID(String parentRefListID) {
		this.parentRefListID = parentRefListID;
	}

	public String getParentRefFullName() {
		return parentRefFullName;
	}

	public void setParentRefFullName(String parentRefFullName) {
		this.parentRefFullName = parentRefFullName;
	}

	public String getManufacturerPartNumber() {
		return manufacturerPartNumber;
	}

	public void setManufacturerPartNumber(String manufacturerPartNumber) {
		this.manufacturerPartNumber = manufacturerPartNumber;
	}

	public String getUnitOfMeasureSetListID() {
		return unitOfMeasureSetListID;
	}

	public void setUnitOfMeasureSetListID(String unitOfMeasureSetListID) {
		this.unitOfMeasureSetListID = unitOfMeasureSetListID;
	}

	public String getUnitOfMeasureSetFullName() {
		return unitOfMeasureSetFullName;
	}

	public void setUnitOfMeasureSetFullName(String unitOfMeasureSetFullName) {
		this.unitOfMeasureSetFullName = unitOfMeasureSetFullName;
	}

	public String getIncomeAccountListID() {
		return incomeAccountListID;
	}

	public void setIncomeAccountListID(String incomeAccountListID) {
		this.incomeAccountListID = incomeAccountListID;
	}

	public String getIncomeAccountFullName() {
		return incomeAccountFullName;
	}

	public void setIncomeAccountFullName(String incomeAccountFullName) {
		this.incomeAccountFullName = incomeAccountFullName;
	}

	public String getcOGSAccountListID() {
		return cOGSAccountListID;
	}

	public void setcOGSAccountListID(String cOGSAccountListID) {
		this.cOGSAccountListID = cOGSAccountListID;
	}

	public String getcOGSAccountFullName() {
		return cOGSAccountFullName;
	}

	public void setcOGSAccountFullName(String cOGSAccountFullName) {
		this.cOGSAccountFullName = cOGSAccountFullName;
	}

	public String getPrefVendorListID() {
		return prefVendorListID;
	}

	public void setPrefVendorListID(String prefVendorListID) {
		this.prefVendorListID = prefVendorListID;
	}

	public String getPrefVendorFullName() {
		return prefVendorFullName;
	}

	public void setPrefVendorFullName(String prefVendorFullName) {
		this.prefVendorFullName = prefVendorFullName;
	}

	public String getAssetAccountListID() {
		return assetAccountListID;
	}

	public void setAssetAccountListID(String assetAccountListID) {
		this.assetAccountListID = assetAccountListID;
	}

	public String getAssetAccountFullName() {
		return assetAccountFullName;
	}

	public void setAssetAccountFullName(String assetAccountFullName) {
		this.assetAccountFullName = assetAccountFullName;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public BigDecimal getQuantityOnHand() {
		return quantityOnHand;
	}

	public void setQuantityOnHand(BigDecimal quantityOnHand) {
		this.quantityOnHand = quantityOnHand;
	}


}
