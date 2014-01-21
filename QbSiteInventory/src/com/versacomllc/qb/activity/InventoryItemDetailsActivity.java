package com.versacomllc.qb.activity;

import static com.versacomllc.qb.utils.Constants.LOG_TAG;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.versacomllc.qb.InventoryQbApp;
import com.versacomllc.qb.R;
import com.versacomllc.qb.model.BarCodeRequest;
import com.versacomllc.qb.model.CheckedInventoryItem;
import com.versacomllc.qb.model.ItemInventory;
import com.versacomllc.qb.model.StringResponse;
import com.versacomllc.qb.spice.GenericPostRequest;
import com.versacomllc.qb.spice.RestCall;
import com.versacomllc.qb.spice.RetrySpiceCallback;
import com.versacomllc.qb.spice.SpiceRestHelper;
import com.versacomllc.qb.utils.Constants;
import com.versacomllc.qb.utils.EndPoints;

public class InventoryItemDetailsActivity extends Activity {

	protected SpiceRestHelper restHelper = new SpiceRestHelper();

	private TextView tvBarCode;
	private TextView tvName;
	private TextView tvFullName;
	private TextView tvQuantity;
	private TextView tvUnit;
	private EditText etQuantity;

	private CheckedInventoryItem currentItem = null;
	private boolean add = true;

	private String barCode;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory_item_detail);
		setTitle(getString(R.string.app_name));


		Intent intent = getIntent();
		if (intent != null) {
			barCode = intent.getStringExtra(Constants.EXTRA_BARCODE);
		}

		this.initComponents();

		/** From list view */
		if (barCode == null && InventoryQbApp.getCurrentItem() != null) {
			add = false;
			currentItem = InventoryQbApp.getCurrentItem();
			populateModel(currentItem);
		}
		/** From bar code scanner */
		else if (!TextUtils.isEmpty(barCode)) {
/*			Toast.makeText(this, "Scan Result = " + barCode, Toast.LENGTH_SHORT)
					.show();*/

			initServiceData(barCode);
		}

	}

	private void initServiceData(final String barCode) {
		String endPoint = EndPoints.REST_CALL_POST_ITEM_BY_BARCODE
				.getSimpleAddress();
		BarCodeRequest request = new BarCodeRequest();
		request.setBarCode(barCode);

		restHelper.execute(new GenericPostRequest<ItemInventory>(
				ItemInventory.class, endPoint, request),
				new RetrySpiceCallback<ItemInventory>(this) {

					@Override
					public void onSpiceSuccess(ItemInventory response) {

						Log.d(LOG_TAG, response + "");
						if (response != null) {
							currentItem = new CheckedInventoryItem(response, 1);
							populateModel(currentItem);
						}
						else{
							final String barCodeText = getString(R.string.barcode_label)
									+" "+ barCode;
							tvBarCode.setText(barCodeText);
							
							Toast.makeText(InventoryItemDetailsActivity.this, "Item not found with bar code: " + barCode, Toast.LENGTH_LONG)
							.show();
						}
					}

					@Override
					public void onSpiceError(RestCall<ItemInventory> restCall,
							StringResponse response) {
						Toast.makeText(InventoryItemDetailsActivity.this,
								"Error = " + response.getMessage(),
								Toast.LENGTH_SHORT).show();
					}

				}, new com.versacomllc.qb.spice.DefaultProgressIndicatorState(
						getString(R.string.processing)));

	}

	private void initComponents() {
		tvBarCode = (TextView) findViewById(R.id.tv_barCode);
		tvName = (TextView) findViewById(R.id.tv_name);
		tvFullName = (TextView) findViewById(R.id.tv_fullName);
		tvQuantity = (TextView) findViewById(R.id.tv_itemQuantity);
		tvUnit = (TextView) findViewById(R.id.tv_itemUnit);
		etQuantity = (EditText) findViewById(R.id.et_Quantity);
	}

	private void populateModel(CheckedInventoryItem item) {
		final String barCodeText = getString(R.string.barcode_label)
				+" "+ item.getBarCodeValue();
		final String itemNameText = getString(R.string.item_name_label)
				+" "+ item.getName();
		final String itemFullNameText = getString(R.string.item_fullname_label)
				+" "+ item.getFullName();
		final String quantityText = getString(R.string.quantity_Label);
		final String unitM = (item.getUnitOfMeasureSetFullName() == null) ? "": item.getUnitOfMeasureSetFullName();
		final String unitText = getString(R.string.item_unit_label)
				+" "+ unitM;

		tvBarCode.setText(barCodeText);
		tvName.setText(itemNameText);
		tvFullName.setText(itemFullNameText);
		tvUnit.setText(unitText);
		tvQuantity.setText(quantityText);
		etQuantity.setText(String.valueOf(item.getCount()));
	}

	private void updateModel() {
		String value = etQuantity.getText().toString();
		int count = Integer.parseInt(value);
		if (add) {
			if (currentItem != null && currentItem.getCount() > 0) {
				currentItem.setCount(count);
				InventoryQbApp.addCheckedItem(currentItem);
			}
		} else {
			currentItem.setCount(count);
			InventoryQbApp.updateCheckedItem(currentItem);
		}

	}

	public void backToList(View v) {

		updateModel();

		Intent intent = new Intent(getBaseContext(),
				InventoryItemListActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
	}
}
