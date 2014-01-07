package com.versacomllc.qb.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarScannerActivity;
import com.versacomllc.qb.InventoryQbApp;
import com.versacomllc.qb.R;
import com.versacomllc.qb.adapter.CheckoutListAdapter;
import com.versacomllc.qb.model.CheckedInventoryItem;
import com.versacomllc.qb.utils.Constants;

public class InventoryItemListActivity extends Activity {

	private static final int ZBAR_SCANNER_REQUEST = 0;
	private CheckoutListAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory_items);
		setTitle(getString(R.string.app_name));

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

	public boolean isCameraAvailable() {
		PackageManager pm = getPackageManager();
		return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
	}
}
