package com.versacomllc.qb.spice;

import static com.versacomllc.qb.utils.Constants.LOG_TAG;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import roboguice.util.temp.Ln;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.SpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;
import com.versacomllc.qb.model.StringResponse;
import com.versacomllc.qb.utils.CustomProgressOverlay;
import com.versacomllc.qb.utils.Utils;

public class SpiceRestHelper {

	public static final int NO_INTERNET = 0;
	public static final int REQUEST_FAILED = 1;
	public static final int WEB_SERVICE_NOT_AVAILABLE = 2;
	public static final int INVALID_SERVER_RESPONSE_MESSAGE = 3;
	public static final int INTERNAL_SERVER_ERROR = 4;
	public static final int BAD_REQUEST = 5;
	public static final int NOT_FOUND = 6;
	public static final int UNKNOWN = -1;
	private final SpiceManager mSpiceManager;
	private ProgressIndicatorState progressIndicator;

	public SpiceRestHelper() {
		mSpiceManager = new SpiceManager(CustomSpiceService.class);
		Ln.getConfig().setLoggingLevel(Log.ERROR);
	}

	protected CustomProgressOverlay mDialog;

	public <ResponseType> void execute(SpiceRequest<ResponseType> request,
			GenericSpiceCallback<ResponseType> callback) {

		progressIndicator = new DefaultProgressIndicatorState();

		execute(request, callback, progressIndicator);
	}

	/**
	 * This method executes Spice-requests
	 * 
	 * @param indicator
	 *            The ProgressIndicator instance
	 * 
	 * @param request
	 *            SpiceRequest you want to execute
	 * @param callback
	 *            Callback to handle the response, Callback Implemented in
	 *            activities. onError can be overridden optionally if the
	 *            standard errorHandling is not sufficient.
	 */
	public <ResponseType> void execute(SpiceRequest<ResponseType> request,
			GenericSpiceCallback<ResponseType> callback,
			ProgressIndicatorState indicator) {

		progressIndicator = indicator;
		// Create a new RestCall-object to provide possibility to retry the Call
		RestCall<ResponseType> restCall = new RestCall<ResponseType>(null, 0,
				request);

		if (Utils.isOnline(callback.getContext())) {
			if (progressIndicator.showProgress()) {
				showDialog(progressIndicator.getProgressMessage(),
						callback.getContext());
			}
			if (mSpiceManager.isStarted() == false) {
				mSpiceManager.start(callback.getContext());
			}
			if (request instanceof GenericGetRequest) {
				mSpiceManager.execute(request,
						((AbstractSpiceRequest<ResponseType>) request)
								.getEndPoint(), DurationInMillis.NEVER,
						new GenericRequestListener<ResponseType>(callback,
								restCall));

			} else {
				mSpiceManager.execute(request,
						new GenericRequestListener<ResponseType>(callback,
								restCall));
			}

		} else {
			callback.onSpiceError(restCall, NO_INTERNET, null);
		}
	}

	/**
	 * 
	 * @param message
	 *            the String that should be shown by the progressDialog
	 * @param context
	 *            the context in which the progressDialog should be shown
	 */
	private void showDialog(String message, Context context) {
		if (mDialog != null) {
			if (progressIndicator.hideProgressOnFinished()) {
				mDialog.dismiss();
			} else {
				mDialog = CustomProgressOverlay.updateProgressMessage(mDialog,
						message);
				return;
			}
		}
		mDialog = CustomProgressOverlay.show(context, message);
	}

	public void onStart(Context context) {
		if (!mSpiceManager.isStarted()) {
			mSpiceManager.start(context);
		}
	}

	public void onPause() {
		if (mDialog != null) {
			mDialog.dismiss();
		}
	}

	public void onStop() {
		mSpiceManager.shouldStop();
	}

	public void onResume() {

	}

	/**
	 * Listener to handle the onSuccess and onFailure of the REST call.
	 * 
	 * 
	 * @param <ResponseType>
	 * 
	 */
	class GenericRequestListener<ResponseType> implements
			RequestListener<ResponseType> {

		private final SpiceCallbackInterface<ResponseType> mCallback;
		private final RestCall<ResponseType> mRestCall;

		public GenericRequestListener(
				SpiceCallbackInterface<ResponseType> callback,
				RestCall<ResponseType> restcall) {
			mCallback = callback;
			mRestCall = restcall;
		}

		@Override
		public void onRequestSuccess(ResponseType response) {
			if (mDialog != null && progressIndicator.hideProgressOnFinished()) {
				mDialog.dismiss();
			}
			mCallback.onSpiceSuccess(response);
		}

		@Override
		public void onRequestFailure(SpiceException spiceException) {

			Log.e(LOG_TAG, "Error: " + spiceException.getMessage(),
					spiceException);

			if (mDialog != null) {
				mDialog.dismiss();
			}

			/** Web resource is not available. Most probably the server is down */
			Throwable exception = spiceException.getCause();

			if (spiceException instanceof RequestCancelledException) {
				Log.w(LOG_TAG, "Request cancelled");
				return;
			}

			int reason;

			if (exception != null)
				Log.e(LOG_TAG, "Error: " + exception.getMessage());

			if (exception instanceof HttpClientErrorException) {
				HttpClientErrorException ex = (HttpClientErrorException) exception;
				String json = ex.getResponseBodyAsString();
				StringResponse result = null;
				try {
					result = new Gson().fromJson(json, StringResponse.class);
				} catch (Exception e) {
					Log.e(LOG_TAG, "Failed to extract error DTO", e);
				}
				StringResponse serverErrorDto = result;
				if (result != null) {
					mCallback.onSpiceError(mRestCall, serverErrorDto);
				} else {
					reason = UNKNOWN;
					if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
						reason = NOT_FOUND;
					}
					mCallback.onSpiceError(mRestCall, reason, exception);
				}
			} else {
				if (exception instanceof ResourceAccessException) {

					reason = WEB_SERVICE_NOT_AVAILABLE;
				}
				/** Internal server error */
				else if (exception instanceof HttpServerErrorException) {

					reason = INTERNAL_SERVER_ERROR;
				}
				/**
				 * Server response invalid message which the rest client fail to
				 * handle (eg: parsing error)
				 */
				else if (exception instanceof RestClientException) {

					reason = INVALID_SERVER_RESPONSE_MESSAGE;
				} else {

					reason = BAD_REQUEST;
				}

				mCallback.onSpiceError(mRestCall, reason, exception);
			}
		}
	}
}
