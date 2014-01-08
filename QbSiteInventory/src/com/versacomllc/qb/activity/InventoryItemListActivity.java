package com.versacomllc.qb.activity;

import static com.versacomllc.qb.utils.Constants.LOG_TAG;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarScannerActivity;
import com.versacomllc.qb.InventoryQbApp;
import com.versacomllc.qb.R;
import com.versacomllc.qb.adapter.CheckoutListAdapter;
import com.versacomllc.qb.model.CheckedInventoryItem;
import com.versacomllc.qb.model.InventoryAdjustment;
import com.versacomllc.qb.model.InventoryAdjustment.InventoryAdjustmentLineItem;
import com.versacomllc.qb.model.InventoryAdjustment.InventoryAdjustmentResponse;
import com.versacomllc.qb.model.StringResponse;
import com.versacomllc.qb.spice.GenericPostRequest;
import com.versacomllc.qb.spice.RestCall;
import com.versacomllc.qb.spice.RetrySpiceCallback;
import com.versacomllc.qb.utils.Constants;
import com.versacomllc.qb.utils.EndPoints;

public class InventoryItemListActivity extends BaseActivity {

	private static final int ZBAR_SCANNER_REQUEST = 0;
	private CheckoutListAdapter adapter;
	private Boolean checkIn = false;

	private Button btnProcess;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory_items);
		setTitle(getString(R.string.app_name));

		Intent intent = getIntent();
		if (intent != null) {
			checkIn = intent.getBooleanExtra(Constants.EXTRA_TRANSACTION_TYPE,
					false);
		}
		btnProcess = (Button) findViewById(R.id.btn_Process);
		if(checkIn){
			btnProcess.setText(getResources().getString(R.string.checkIn));
		}
		else{
			btnProcess.setText(getResources().getString(R.string.checkout));
		}
	}

	@Override
	protected void onStart() {
		super.onStart();

		List<CheckedInventoryItem> items = InventoryQbApp.getCheckedItems();
		adapter = new CheckoutListAdapter(this, R.layout.checkout_list_item,
				items);

		ListView listViewItems = (ListView) findViewById(R.id.lv_scannedItems);
		listViewItems.setAdapter(adapter);

		listViewItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				CheckedInventoryItem item = adapter.getItem(position);

				if (item != null) {
					InventoryQbApp.setCurrentItem(item);
					processScannedResult(null);
				}

			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ZBAR_SCANNER_REQUEST:
			if (resultCode == RESULT_OK) {
				final String barCode = data
						.getStringExtra(ZBarConstants.SCAN_RESULT);
				processScannedResult(barCode);

			} else if (resultCode == RESULT_CANCELED && data != null) {
				String error = data.getStringExtra(ZBarConstants.ERROR_INFO);
				processCancellation(error);
			}
			break;
		}
	}

	private void processScannedResult(final String barCode) {

		Intent intent = new Intent(getBaseContext(),
				InventoryItemDetailsActivity.class);
		intent.putExtra(Constants.EXTRA_BARCODE, barCode);
		startActivity(intent);
	}

	private void processCancellation(final String error) {
		if (!TextUtils.isEmpty(error)) {
			Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
		}
	}

	public void launchScanner(View v) {
		if (isCameraAvailable()) {
			Intent intent = new Intent(this, ZBarScannerActivity.class);
			startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
		} else {
			Toast.makeText(this, "Rear Facing Camera Unavailable",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void processTransaction(View v) {
		InventoryAdjustment adjustment = getApplicationState()
				.getInventoryAdjustment();

		if (adjustment.getItems() == null) {
			adjustment
					.setItems(new ArrayList<InventoryAdjustment.InventoryAdjustmentLineItem>());
		}
		List<CheckedInventoryItem> lineItems = InventoryQbApp.getCheckedItems();
		for (CheckedInventoryItem item : lineItems) {
			InventoryAdjustmentLineItem lineItem = new InventoryAdjustmentLineItem();
			lineItem.setListID(item.getListID());
			lineItem.setFullName(item.getFullName());
			if (checkIn) {
				lineItem.setQuantityDifference(item.getCount());
			} else {
				lineItem.setQuantityDifference(-item.getCount());
			}
			adjustment.getItems().add(lineItem);
		}

		Log.d(LOG_TAG,
				"Processing checkin/checkout for item: "
						+ adjustment.getInventorySiteRefName());
		if(adjustment.getItems().size() == 0 ){
			Toast.makeText(
					InventoryItemListActivity.this,
					"Please add item(s) to proceed transaction"
							,
					Toast.LENGTH_SHORT).show();
		}
		else{
			processInventoryCheckout(adjustment);
		}
		
	}

	private void processInventoryCheckout(final InventoryAdjustment adjustment) {
		String endPoint = EndPoints.REST_CALL_GET_INVENTORY_ADJUSTMENT
				.getSimpleAddress();

		restHelper.execute(new GenericPostRequest<InventoryAdjustmentResponse>(
				InventoryAdjustmentResponse.class, endPoint, adjustment),
				new RetrySpiceCallback<InventoryAdjustmentResponse>(this) {

					@Override
					public void onSpiceSuccess(
							InventoryAdjustmentResponse response) {

						Log.d(LOG_TAG, response + "");
						Toast.makeText(
								InventoryItemListActivity.this,
								"Txn completed successfully :"
										+ response.getStatus(),
								Toast.LENGTH_SHORT).show();
						InventoryQbApp.getCheckedItems().clear();
						adapter.notifyDataSetChanged();
					}

					@Override
					public void onSpiceError(
							RestCall<InventoryAdjustmentResponse> restCall,
							StringResponse response) {
						Toast.makeText(InventoryItemListActivity.this,
								"Txn completed with error:" + response,
								Toast.LENGTH_SHORT).show();
					}

				}, new com.versacomllc.qb.spice.DefaultProgressIndicatorState(
						getString(R.string.processing)));

	}

	public boolean isCameraAvailable() {
		PackageManager pm = getPackageManager();
		return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
	}
}
