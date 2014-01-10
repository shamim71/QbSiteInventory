package com.versacomllc.qb.activity;

import static com.versacomllc.qb.utils.Constants.ACTION_FINISH;
import static com.versacomllc.qb.utils.Constants.LOG_TAG;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.versacomllc.qb.R;
import com.versacomllc.qb.model.AuthenticationRequest;
import com.versacomllc.qb.model.AuthenticationResponse;
import com.versacomllc.qb.model.CheckedInventoryItem;
import com.versacomllc.qb.model.ItemInventory;
import com.versacomllc.qb.model.StringResponse;
import com.versacomllc.qb.spice.GenericGetRequest;
import com.versacomllc.qb.spice.GenericPostRequest;
import com.versacomllc.qb.spice.RestCall;
import com.versacomllc.qb.spice.RetrySpiceCallback;
import com.versacomllc.qb.utils.Constants;
import com.versacomllc.qb.utils.EndPoints;

public class LoginActivity extends BaseActivity {



    
	private EditText etEmailAddress;
	private EditText etPassword;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);

		initComponents();
		
		registerActivityFinishSignal();
       
        verifyExistingLogin();
        
	}
	
	private void verifyExistingLogin(){
		AuthenticationResponse response =  getApplicationState().getAuthentication();
		if(response != null){
			processResult();
		}
	}
	private void initComponents() {

		etEmailAddress = (EditText) findViewById(R.id.et_emailAddress);
		etPassword = (EditText) findViewById(R.id.et_Password);
	}
	
	public void authenticateUser(View v){
		final String email = etEmailAddress.getText().toString();
		final String password = etPassword.getText().toString();
		
		if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
			final String emptyMessage = getString(R.string.login_enter_authentication);
			Toast.makeText(this, emptyMessage,
					Toast.LENGTH_LONG).show();
			return;
		}
		
		AuthenticationRequest request = new AuthenticationRequest(email, password);
		
		authenticateWithServer(request );
	}
	private void authenticateWithServer(AuthenticationRequest request) {

		String endPoint = EndPoints.REST_CALL_POST_AUTHENTICATE
				.getSimpleAddress();

		restHelper.execute(new GenericPostRequest<AuthenticationResponse>(
				AuthenticationResponse.class, endPoint, request),
				new RetrySpiceCallback<AuthenticationResponse>(this) {

					@Override
					public void onSpiceSuccess(AuthenticationResponse response) {

						Log.d(LOG_TAG, response + "");
						if (response != null) {
							
							getApplicationState().saveAuthentication(response);
							
							processResult();
						}
					}

					@Override
					public void onSpiceError(RestCall<AuthenticationResponse> restCall,
							StringResponse response) {
						Toast.makeText(LoginActivity.this,
								"Error = " + response.getMessage(),
								Toast.LENGTH_LONG).show();
					}

				}, new com.versacomllc.qb.spice.DefaultProgressIndicatorState(
						getString(R.string.processing)));

	}


	private void processResult() {

		Intent intent = new Intent(getBaseContext(),
				HomeActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				);
		startActivity(intent);
	}
	

}
