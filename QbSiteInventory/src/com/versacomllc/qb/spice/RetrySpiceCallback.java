package com.versacomllc.qb.spice;

import com.versacomllc.qb.fragment.SpiceErrorDialogFragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;

public abstract class RetrySpiceCallback<T> extends GenericSpiceCallback<T> {

	private static final String DIALOG_TAG = "ERROR_DIALOG";

	public RetrySpiceCallback(Context context) {
		super(context);
		/*
		 * if (!(context instanceof FragmentActivity)) { throw new
		 * RuntimeException(
		 * "Invalid context class! Needs to be a SherlockFragmentActivity"); }
		 */
	}

	@Override
	public void onSpiceError(RestCall<T> restCall, int reason,
			Throwable exception) {
		FragmentManager fragmentManager = ((Activity) mContext).getFragmentManager();
				
		DialogFragment fragment = (DialogFragment) fragmentManager
				.findFragmentByTag(DIALOG_TAG);
		if (fragment != null) {
			fragmentManager.beginTransaction().remove(fragment).commit();
		}

		fragment = new SpiceErrorDialogFragment<T>(this, restCall) {
			@Override
			protected void onNegativeButton() {
				dismiss();
				onErrorMessageDismissed();
			}
		};

		Bundle args = new Bundle();
		args.putString(SpiceErrorDialogFragment.ERROR_MESSAGE_KEY,
				extractErrorMessage(reason, exception));
		fragment.setArguments(args);

		fragment.show(fragmentManager, DIALOG_TAG);

	}

	protected void onErrorMessageDismissed() {
	}
}
