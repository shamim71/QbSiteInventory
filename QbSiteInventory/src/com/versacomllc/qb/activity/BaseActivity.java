package com.versacomllc.qb.activity;

import static com.versacomllc.qb.utils.Constants.ACTION_FINISH;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.versacomllc.qb.InventoryQbApp;
import com.versacomllc.qb.spice.SpiceRestHelper;

public class BaseActivity extends Activity {

	protected SpiceRestHelper restHelper = new SpiceRestHelper();
	
	private FinishReceiver finishReceiver;
	
	protected InventoryQbApp getApplicationState() {
		return (InventoryQbApp) getApplication();
	}

	protected void registerActivityFinishSignal(){
        finishReceiver= new FinishReceiver();
        registerReceiver(finishReceiver, new IntentFilter(ACTION_FINISH));
	}
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(finishReceiver != null){
        	unregisterReceiver(finishReceiver);
        }
    }

    private final class FinishReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_FINISH)) 
                finish();
        }
    }
}
