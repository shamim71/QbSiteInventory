package com.versacomllc.qb.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Utils {

	private static final int TOAST_POSITION_X_OFFSET = 0;
	private static final int TOAST_POSITION_Y_OFFSET = 0;

	public static void disableEnableControls(boolean enable, View view) {
		if (view instanceof ViewGroup) {
			ViewGroup vg = (ViewGroup) view;
			for (int i = 0; i < vg.getChildCount(); i++) {
				View child = vg.getChildAt(i);
				disableEnableControls(enable, child);
			}
		} else {
			view.setEnabled(enable);
		}
	}

	/**
	 * Display toast message in the center of the screen.
	 * 
	 * @param context
	 *            the Context
	 * @param message
	 *            the message to display
	 */
	public static void showCenterAlignedToastMessage(Context context,
			String message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER_HORIZONTAL, TOAST_POSITION_X_OFFSET,
				TOAST_POSITION_Y_OFFSET);
		toast.show();
	}

	/**
	 * Checks whether device has an internet connection or not.
	 * 
	 * @param context
	 *            The context of the app
	 * @return boolean indicating whether device is online or not
	 */
	public static boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the state of Airplane Mode.
	 * 
	 * @param context
	 * @return true if enabled.
	 */
	public static boolean isAirplaneModeOn(Context context) {
		return Settings.System.getInt(context.getContentResolver(),
				android.provider.Settings.System.AIRPLANE_MODE_ON, 0) != 0;

	}

	public static HttpHeaders constructDefaultHeaders() {
		MediaType mediaType = new MediaType("application", "json");
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(mediaType);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(acceptableMediaTypes);
		// headers.setAcceptEncoding(ContentCodingType.GZIP);
		headers.setCacheControl("no-cache");

		return headers;
	}
}
