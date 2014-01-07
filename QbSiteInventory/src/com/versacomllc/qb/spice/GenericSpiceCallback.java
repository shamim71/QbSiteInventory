package com.versacomllc.qb.spice;

import static com.versacomllc.qb.spice.SpiceRestHelper.INTERNAL_SERVER_ERROR;
import static com.versacomllc.qb.spice.SpiceRestHelper.INVALID_SERVER_RESPONSE_MESSAGE;
import static com.versacomllc.qb.spice.SpiceRestHelper.NO_INTERNET;
import static com.versacomllc.qb.spice.SpiceRestHelper.REQUEST_FAILED;
import static com.versacomllc.qb.spice.SpiceRestHelper.WEB_SERVICE_NOT_AVAILABLE;
import android.content.Context;

import com.versacomllc.qb.R;
import com.versacomllc.qb.utils.Utils;


public abstract class GenericSpiceCallback<T> implements
    SpiceCallbackInterface<T> {

  protected final Context mContext;

  protected GenericSpiceCallback(Context context) {
    this.mContext = context;
  }

  public Context getContext() {
    return mContext;
  }

  @Override
  public void onSpiceError(RestCall<T> restCall, int reason, Throwable exception) {
    Utils.showCenterAlignedToastMessage(mContext,
        extractErrorMessage(reason, exception));
  }



  protected String extractErrorMessage(int reason, Throwable exception) {

    String message = null;

    switch (reason) {
      case NO_INTERNET:
        message = mContext.getString(R.string.no_internet);
        break;

      /** General request fail */
      case REQUEST_FAILED:
        message = exception.getMessage();
        break;

      /** Web service not available */
      case WEB_SERVICE_NOT_AVAILABLE:
        message = mContext.getString(R.string.service_not_avilable);
        break;

      /** Invalid server response */
      case INVALID_SERVER_RESPONSE_MESSAGE:
        message = mContext.getString(R.string.invalid_server_response);
        break;

      /** Internal server error */
      case INTERNAL_SERVER_ERROR:
        message = mContext.getString(R.string.internal_server_error);
        break;
    }
    // generic reasons
    if (Utils.isAirplaneModeOn(mContext)) {
      message = mContext.getString(R.string.turn_off_airplane_mode);
    }
    else if (!Utils.isOnline(mContext)) {
      message = mContext.getString(R.string.no_internet);
    }
    else if (message == null) {
      message = mContext.getString(R.string.general_service_error);
    }

    return message;
  }

}
