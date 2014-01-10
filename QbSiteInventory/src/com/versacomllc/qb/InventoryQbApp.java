package com.versacomllc.qb;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.app.Application;

import com.google.gson.Gson;
import com.versacomllc.qb.model.CheckedInventoryItem;
import com.versacomllc.qb.model.InventoryAdjustment;
import com.versacomllc.qb.model.InventorySite;
import com.versacomllc.qb.model.ItemInventory;
import com.versacomllc.qb.utils.FileDataStorageManager;
import com.versacomllc.qb.utils.FileDataStorageManager.StorageFile;

/**
 * In-memory state cache
 */
public class InventoryQbApp extends Application {

	private static List<CheckedInventoryItem> checkedItems = new ArrayList<CheckedInventoryItem>();

	private static CheckedInventoryItem currentItem;

	private Gson jsonHelper = new Gson();

	@Override
	public void onCreate() {
		super.onCreate();
	}

	public static List<CheckedInventoryItem> getCheckedItems() {
		return checkedItems;
	}

	public static void setCheckedItems(List<CheckedInventoryItem> checkedItems) {
		InventoryQbApp.checkedItems = checkedItems;
	}

	public static void addCheckedItem(CheckedInventoryItem checkedItem) {

		boolean exist = false;

		for (CheckedInventoryItem item : checkedItems) {
			if (item.getListID().equals(checkedItem.getListID())) {
				item.setCount(item.getCount() + checkedItem.getCount());
				exist = true;
				break;
			}
		}
		if (!exist) {
			InventoryQbApp.checkedItems.add(checkedItem);
		}
	}

	public static void updateCheckedItem(CheckedInventoryItem checkedItem) {

		for (CheckedInventoryItem item : checkedItems) {
			if (item.getListID().equals(checkedItem.getListID())) {
				item.setCount(checkedItem.getCount());
				break;
			}
		}

	}

	public static CheckedInventoryItem getCurrentItem() {
		return currentItem;
	}

	public static void setCurrentItem(CheckedInventoryItem currentItem) {
		InventoryQbApp.currentItem = currentItem;
	}

	public void saveInventoryAdjustment(InventoryAdjustment adjustment) {
		final String jsonContent = jsonHelper.toJson(adjustment);
		FileDataStorageManager.saveContentToFile(getBaseContext(),
				StorageFile.INVENTORY_ADJUSTMENT, jsonContent);
	}

	public void saveInventorySites(InventorySite[] sites){
		final String jsonContent = jsonHelper.toJson(sites);
		FileDataStorageManager.saveContentToFile(getBaseContext(),
				StorageFile.INVENTORY_SITES, jsonContent);
	}
	public InventorySite[] getInventorySites(){
		return loadObject(InventorySite[].class,
				StorageFile.INVENTORY_SITES);
	}
	
	public void saveInventoryItems(ItemInventory[] items){
		final String jsonContent = jsonHelper.toJson(items);
		FileDataStorageManager.saveContentToFile(getBaseContext(),
				StorageFile.INVENTORY_ITEMS, jsonContent);
	}
	public ItemInventory[] getInventoryItems(){
		return loadObject(ItemInventory[].class,
				StorageFile.INVENTORY_ITEMS);
	}
	
	public InventorySite getInventorySite(final String listID){
		InventorySite[] sites = getInventorySites();
		if(sites == null) return null;
		InventorySite mSite = null;
		for(InventorySite site: sites){
			if(site.getListID().equals(listID)){
				mSite = site;
				break;
			}
		}
		return mSite;
	}
	private <T> T loadObject(Class<T> type, StorageFile name) {
		String jsonContent = FileDataStorageManager.getContentFromFile(
				getBaseContext(), name);

		if (StringUtils.isEmpty(jsonContent))
			return null;

		T dtos = jsonHelper.fromJson(jsonContent, type);

		return dtos;
	}

	public InventoryAdjustment getInventoryAdjustment() {

		return loadObject(InventoryAdjustment.class,
				StorageFile.INVENTORY_ADJUSTMENT);

	}
}
