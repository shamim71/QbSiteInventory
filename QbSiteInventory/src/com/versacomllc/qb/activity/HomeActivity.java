package com.versacomllc.qb.activity;

import com.versacomllc.qb.R;
import com.versacomllc.qb.utils.Constants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends Activity {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

	public void launchCheckout(View v) {
		Intent intent = new Intent(this, InventorySiteActivity.class);
		intent.putExtra(Constants.EXTRA_TRANSACTION_TYPE, false);
		startActivity(intent);
	}
	
	public void launchCheckIn(View v) {
		Intent intent = new Intent(this, InventorySiteActivity.class);
		intent.putExtra(Constants.EXTRA_TRANSACTION_TYPE, true);
		startActivity(intent);
	}
}
