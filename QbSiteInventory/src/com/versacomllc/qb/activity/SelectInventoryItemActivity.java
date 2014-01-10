package com.versacomllc.qb.activity;

import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.versacomllc.qb.InventoryQbApp;
import com.versacomllc.qb.R;
import com.versacomllc.qb.adapter.ItemAutocompleteListAdapter;
import com.versacomllc.qb.model.InventoryAdjustment;
import com.versacomllc.qb.model.ItemInventory;
import com.versacomllc.qb.model.StringResponse;
import com.versacomllc.qb.spice.GenericGetRequest;
import com.versacomllc.qb.spice.RestCall;
import com.versacomllc.qb.spice.RetrySpiceCallback;
import com.versacomllc.qb.utils.Constants;
import com.versacomllc.qb.utils.EndPoints;

public class SelectInventoryItemActivity extends BaseActivity implements
		OnItemClickListener {

	ArrayAdapter<ItemInventory> adapter = null;
	InventoryAdjustment adjustment = new InventoryAdjustment();
	InventoryQbApp state = null;
	boolean checkIn = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_inventory_item);
		setTitle(getString(R.string.app_name));

		this.initComponents();

		this.initServiceData();

	}

	private void populateSiteList(ItemInventory[] objects) {

		AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.sp_siteList);

		List<ItemInventory> list = Arrays.asList(objects);

		adapter = new ItemAutocompleteListAdapter(this,
				android.R.layout.simple_dropdown_item_1line, list);

		autoCompleteTextView.setThreshold(1);
		autoCompleteTextView.setAdapter(adapter);

		autoCompleteTextView.setOnItemClickListener(this);
	}

	private void initServiceData() {
		ItemInventory[] items = getApplicationState().getInventoryItems();
		if (items != null && items.length > 0) {
			populateSiteList(items);
			return;
		}

		String endPoint = EndPoints.REST_CALL_GET_ALL_INVENTORY_ITEMS
				.getSimpleAddress();

		restHelper.execute(new GenericGetRequest<ItemInventory[]>(
				ItemInventory[].class, endPoint),
				new RetrySpiceCallback<ItemInventory[]>(this) {

					@Override
					public void onSpiceSuccess(ItemInventory[] response) {

						if (response != null) {
							populateSiteList(response);
							/** Save items */
							getApplicationState().saveInventoryItems(response);
						}

					}

					@Override
					public void onSpiceError(
							RestCall<ItemInventory[]> restCall,
							StringResponse response) {

					}
				}, new com.versacomllc.qb.spice.DefaultProgressIndicatorState(
						getString(R.string.processing)));

	}

	private void initComponents() {

	}

	public void navigateToList(View v) {

		Intent intent = new Intent(this, InventoryItemListActivity.class);
		intent.putExtra(Constants.EXTRA_TRANSACTION_TYPE, checkIn);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Log.d("AutocompleteContacts", "Position:" + position + " Item :"
				+ parent.getItemAtPosition(position));
		ItemInventory item = (ItemInventory) parent.getItemAtPosition(position);
		final String barCode = item.getBarCodeValue();
		processResult(barCode);
	}

	private void processResult(final String barCode) {

		Intent intent = new Intent(getBaseContext(),
				InventoryItemDetailsActivity.class);
		intent.putExtra(Constants.EXTRA_BARCODE, barCode);
		startActivity(intent);
	}
}
