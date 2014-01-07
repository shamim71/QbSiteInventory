package com.versacomllc.qb.activity;

import android.app.Activity;

import com.versacomllc.qb.InventoryQbApp;
import com.versacomllc.qb.spice.SpiceRestHelper;

public class BaseActivity extends Activity {

	protected SpiceRestHelper restHelper = new SpiceRestHelper();
	
	protected InventoryQbApp getApplicationState() {
		return (InventoryQbApp) getApplication();
	}

}
