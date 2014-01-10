package com.versacomllc.qb.activity;

import static com.versacomllc.qb.utils.Constants.ACTION_FINISH;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.versacomllc.qb.R;
import com.versacomllc.qb.utils.Constants;

public class HomeActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		setTitle(R.string.app_name);
		sendBroadcast(new Intent(ACTION_FINISH));

		registerActivityFinishSignal();
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

	public void signOut(View v) {

	

		getApplicationState().saveAuthentication(null);
		
		sendBroadcast(new Intent(ACTION_FINISH));
		
		Intent intent = new Intent(this, LoginActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
	}

}
