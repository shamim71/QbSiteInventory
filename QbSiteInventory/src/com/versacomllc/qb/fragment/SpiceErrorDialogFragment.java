package com.versacomllc.qb.fragment;


import com.versacomllc.qb.R;
import com.versacomllc.qb.model.StringResponse;
import com.versacomllc.qb.spice.DefaultProgressIndicatorState;
import com.versacomllc.qb.spice.GenericSpiceCallback;
import com.versacomllc.qb.spice.RestCall;
import com.versacomllc.qb.spice.SpiceCallbackInterface;
import com.versacomllc.qb.spice.SpiceRestHelper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;


public abstract class SpiceErrorDialogFragment<T> extends DialogFragment {

	public static final String ERROR_MESSAGE_KEY = "ERROR_MESSAGE_KEY";

	private final SpiceCallbackInterface<T> callback;
	private final RestCall<T> restCall;

	private final SpiceRestHelper spiceRestHelper = new SpiceRestHelper();

	public SpiceErrorDialogFragment(SpiceCallbackInterface<T> callback,
			RestCall<T> restCall) {
		this.callback = callback;
		this.restCall = restCall;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		setRetainInstance(true);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		String message = getArguments().getString(ERROR_MESSAGE_KEY);
		return builder
				.setMessage(
						message == null ? getString(R.string.general_service_error)
								: message)
				.setTitle(R.string.error_dialog_title)
				.setPositiveButton(R.string.retry,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								onPositiveButton();
							}
						})
				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								onNegativeButton();
							}
						}).create();
	}

	protected abstract void onNegativeButton();

	protected void onPositiveButton() {
		spiceRestHelper.execute(restCall.getSpiceRequest(),
				new SpiceErrorCallback(), new DefaultProgressIndicatorState(
						getString(R.string.processing)));
	}

	class SpiceErrorCallback extends GenericSpiceCallback<T> {

		SpiceErrorCallback() {
			super(getActivity());
		}

		@Override
		public void onSpiceSuccess(T response) {
			callback.onSpiceSuccess(response);
		}

		@Override
		public void onSpiceError(RestCall<T> restCall, int reason,
				Throwable exception) {
			callback.onSpiceError(restCall, reason, exception);
		}

		@Override
		public void onSpiceError(RestCall<T> restCall, StringResponse response) {
			 callback.onSpiceError(restCall, response);

		}
	}
}
