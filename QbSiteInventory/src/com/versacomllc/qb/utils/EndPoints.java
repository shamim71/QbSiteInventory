package com.versacomllc.qb.utils;

import java.text.MessageFormat;

/**
 * @author SAhmmed
 *
 */
public enum EndPoints {

	
	REST_CALL_GET_INVENTORY_ADJUSTMENT("/qb/inventory/adjustment"),
	/**
	 * Load all inventory sites.
	 */
	REST_CALL_GET_INVENTORY_SITES("/inventory/sites"),
	/**
	 * Reset all greetings
	 */
	REST_CALL_POST_ITEM_BY_BARCODE("/inventory/items/barcode"),

	REST_CALL_GET_ALL_INVENTORY_ITEMS("/inventory/items"),
	
	REST_CALL_GET_ADJUSTMENT_CONF("/configurations");

	private final String address;

	private EndPoints(String address) {
		this.address = address;
	}

	public String getSimpleAddress() {
		return "http://d1mnzch1.versacomllc.com:9080/quickbooks-gateway-server"
				+ address;
	}

	/**
	 * Return the formatted URL variables
	 * 
	 * @param args
	 *            URL arguments
	 * 
	 * @return the formatted string
	 */
	public String getAddress(Object... args) {
		return MessageFormat.format(getSimpleAddress(), args);
	}
}
