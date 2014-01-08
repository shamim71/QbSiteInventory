package com.versacomllc.qb.model;

import java.util.List;

import android.text.TextUtils;

public class InventorySite {

	private Long id;

	private String listID;

	private String name;

	private String isActive;

	private String parentSiteRef;

	private String parentSiteRefName;

	private String siteDesc;

	List<InventorySite> childreen;
	
	public List<InventorySite> getChildreen() {
		return childreen;
	}

	public void setChildreen(List<InventorySite> childreen) {
		this.childreen = childreen;
	}

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

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getParentSiteRef() {
		return parentSiteRef;
	}

	public void setParentSiteRef(String parentSiteRef) {
		this.parentSiteRef = parentSiteRef;
	}

	public String getParentSiteRefName() {
		return parentSiteRefName;
	}

	public void setParentSiteRefName(String parentSiteRefName) {
		this.parentSiteRefName = parentSiteRefName;
	}

	public String getSiteDesc() {
		return siteDesc;
	}

	public void setSiteDesc(String siteDesc) {
		this.siteDesc = siteDesc;
	}

	@Override
	public String toString() {
		String desc = null;
		if(!TextUtils.isEmpty(parentSiteRefName)){
			desc = parentSiteRefName + ">";
		}
		desc = desc + name;
		return name;
	}

}
