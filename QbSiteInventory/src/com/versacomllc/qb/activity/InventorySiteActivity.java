package com.versacomllc.qb.activity;

import static com.versacomllc.qb.utils.Constants.LOG_TAG;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.versacomllc.qb.InventoryQbApp;
import com.versacomllc.qb.R;
import com.versacomllc.qb.adapter.SimpleDropDownListAdapter;
import com.versacomllc.qb.model.AuthenticationResponse;
import com.versacomllc.qb.model.Configuration;
import com.versacomllc.qb.model.InventoryAdjustment;
import com.versacomllc.qb.model.InventorySite;
import com.versacomllc.qb.model.StringResponse;
import com.versacomllc.qb.spice.GenericGetRequest;
import com.versacomllc.qb.spice.RestCall;
import com.versacomllc.qb.spice.RetrySpiceCallback;
import com.versacomllc.qb.utils.Constants;
import com.versacomllc.qb.utils.EndPoints;

public class InventorySiteActivity extends BaseActivity implements OnItemSelectedListener {


	ArrayAdapter<InventorySite> adapter = null;
	InventoryAdjustment adjustment = new InventoryAdjustment();
	InventoryQbApp state = null;
	boolean checkIn = false;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory_site);
		setTitle(getString(R.string.app_name));

		Intent intent = getIntent();
		if (intent != null) {
			checkIn = intent.getBooleanExtra(Constants.EXTRA_TRANSACTION_TYPE,
					false);
		}
		
		state = getApplicationState();
		
		this.initComponents();

		this.initServiceData();
		
	}

	private void populateSiteList(InventorySite[] objects){
		Spinner spinner = (Spinner) findViewById(R.id.sp_siteList);
		// Create an ArrayAdapter using the string array and a default spinner layout
		
		//List<InventorySite> list = Arrays.asList(objects);
		List<InventorySite> list = getParentSites(objects);
		
		adapter = new SimpleDropDownListAdapter(this, android.R.layout.simple_spinner_item, list); 
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
	}
	private void initServiceData() {
		String endPoint = EndPoints.REST_CALL_GET_INVENTORY_SITES
				.getSimpleAddress();

		restHelper.execute(new GenericGetRequest<InventorySite[]>(InventorySite[].class, endPoint), new RetrySpiceCallback<InventorySite[]>(this) {

			@Override
			public void onSpiceSuccess(InventorySite[] response) {
				
				
				if(response != null){
					getApplicationState().saveInventorySites(response);
				}
				
				initConfigurationData();
				
				populateSiteList(response);
				
			}

			@Override
			public void onSpiceError(RestCall<InventorySite[]> restCall,
					StringResponse response) {
			
				
			}
		}, new com.versacomllc.qb.spice.DefaultProgressIndicatorState(
				getString(R.string.processing),false));

	}

	private void initConfigurationData() {
		String endPoint = EndPoints.REST_CALL_GET_ADJUSTMENT_CONF
				.getSimpleAddress();

		restHelper.execute(new GenericGetRequest<Configuration>(Configuration.class, endPoint), new RetrySpiceCallback<Configuration>(this) {

			@Override
			public void onSpiceSuccess(Configuration response) {
				adjustment.setAccountRefListID(response.getAccountRefListId());
				adjustment.setAccountRefFullName(response.getAccountRefFullName());
	
			}

			@Override
			public void onSpiceError(RestCall<Configuration> restCall,
					StringResponse response) {
				
			}
		}, new com.versacomllc.qb.spice.DefaultProgressIndicatorState());

	}
	private List<InventorySite> getParentSites(InventorySite[] sites){
		List<InventorySite> parentSites = new ArrayList<InventorySite>();
		for(InventorySite site: sites){
			if(site.getParentSiteRef() == null){
				parentSites.add(site);
			}
		}
		return parentSites;
	}
	private void initComponents() {

	}

	public void navigateToList(View v){
		
		Intent intent = new Intent(this, InventoryItemListActivity.class);
		intent.putExtra(Constants.EXTRA_TRANSACTION_TYPE, checkIn);
		startActivity(intent);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, 
            int position, long id) {
	
		InventorySite site =  adapter.getItem(position);
		Log.d(LOG_TAG, site.getListID());
		Log.d(LOG_TAG, site.getName());
		
		AuthenticationResponse authentication = getApplicationState().getAuthentication();
		if(authentication != null){
			adjustment.setUserId(authentication.getId());
		}
		adjustment.setInventorySiteRefListID(site.getListID());
		adjustment.setInventorySiteRefName(site.getName());
		
		state.saveInventoryAdjustment(adjustment);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		Log.d(LOG_TAG, "Nothing selected...");
		
	}

}
