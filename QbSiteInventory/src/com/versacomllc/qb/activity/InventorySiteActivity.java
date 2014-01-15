package com.versacomllc.qb.activity;

import static android.view.View.GONE;
import static com.versacomllc.qb.utils.Constants.LOG_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.versacomllc.qb.InventoryQbApp;
import com.versacomllc.qb.R;
import com.versacomllc.qb.adapter.SimpleDropDownListAdapter;
import com.versacomllc.qb.adapter.SiteAccessDropDownListAdapter;
import com.versacomllc.qb.gateway.domain.CustomerSiteAccess;
import com.versacomllc.qb.model.AuthenticationResponse;
import com.versacomllc.qb.model.Configuration;
import com.versacomllc.qb.model.InventoryAdjustment;
import com.versacomllc.qb.model.InventorySite;
import com.versacomllc.qb.model.StringResponse;
import com.versacomllc.qb.service.LocationFinderService;
import com.versacomllc.qb.spice.DefaultProgressIndicatorState;
import com.versacomllc.qb.spice.GenericGetRequest;
import com.versacomllc.qb.spice.RestCall;
import com.versacomllc.qb.spice.RetrySpiceCallback;
import com.versacomllc.qb.utils.Constants;
import com.versacomllc.qb.utils.EndPoints;

public class InventorySiteActivity extends BaseActivity implements OnItemSelectedListener {


	ArrayAdapter<InventorySite> adapter = null;
	ArrayAdapter<CustomerSiteAccess> customerSiteAdapter = null;
	InventoryAdjustment adjustment = new InventoryAdjustment();
	InventoryQbApp state = null;
	boolean checkIn = false;
    double latitude = 0.0f;
    double longitude = 0.0f;
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
		
		locationFinderService = new LocationFinderService(this);
		this.checkLocation();
		
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
	
	private void populateCustomerSiteAccessList(CustomerSiteAccess[] objects){
		Spinner spinner = (Spinner) findViewById(R.id.sp_customerSiteAccess);
		TextView tvSiteAccess = (TextView) findViewById(R.id.tv_customerSiteAccess);
		if(objects == null || objects.length == 0){
			spinner.setVisibility(GONE);
			tvSiteAccess.setVisibility(GONE);
		}

		List<CustomerSiteAccess> list = Arrays.asList(objects);
		customerSiteAdapter = new SiteAccessDropDownListAdapter(this, android.R.layout.simple_spinner_item, list); 
		// Specify the layout to use when the list of choices appears
		customerSiteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(customerSiteAdapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
			CustomerSiteAccess siteAccess =	customerSiteAdapter.getItem(position);
				if(siteAccess != null){
					Log.d(LOG_TAG, " log: "+ siteAccess.siteId);
					adjustment.setCustomerSiteAccessId(siteAccess.getSiteId());
					state.saveInventoryAdjustment(adjustment);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			
				
			}
		});
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
				
				findCustomerSiteAccess(latitude, longitude, 1f);
				
				populateSiteList(response);
				
			}

			@Override
			public void onSpiceError(RestCall<InventorySite[]> restCall,
					StringResponse response) {
			
				
			}
		}, new DefaultProgressIndicatorState(
				getString(R.string.processing),false));

	}
	private void checkLocation(){
		 // check if GPS enabled    
       if(locationFinderService.canGetLocation()){
            
           latitude = locationFinderService.getLatitude();
           longitude = locationFinderService.getLongitude();
            
           // \n is for new line
          // Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();   
       }else{
           // can't get location
           // GPS or Network is not enabled
           // Ask user to enable GPS/network in settings
       	locationFinderService.showSettingsAlert();
       }
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
		}, new DefaultProgressIndicatorState());

	}
	
	private void findCustomerSiteAccess(double latitude, double longitude, double radius) {
		
		String endPoint = EndPoints.REST_CALL_GET_CUSTOMER_SITE_ACCESS_LIST.getAddress(latitude, longitude, radius);
			

		restHelper.execute(new GenericGetRequest<CustomerSiteAccess[]>(CustomerSiteAccess[].class, endPoint), new RetrySpiceCallback<CustomerSiteAccess[]>(this) {

			@Override
			public void onSpiceSuccess(CustomerSiteAccess[] response) {
				
				populateCustomerSiteAccessList(response);
			}

			@Override
			public void onSpiceError(RestCall<CustomerSiteAccess[]> restCall,
					StringResponse response) {
				
			}
		}, new DefaultProgressIndicatorState());

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
		if(authentication != null && authentication.getResult() != null){
			adjustment.setUserId(authentication.getResult().getId());
		}
		adjustment.setInventorySiteRefListID(site.getListID());
		adjustment.setInventorySiteRefName(site.getName());
		adjustment.setAdjustmentType("IN");
		if(!checkIn){
			adjustment.setAdjustmentType("OUT");
		}
		
		state.saveInventoryAdjustment(adjustment);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		Log.d(LOG_TAG, "Nothing selected...");
		
	}

}
