package com.versacomllc.qb.spice;

import com.versacomllc.qb.model.StringResponse;



/**
 * Interface to be implemented in activities.
 * 
 * 
 * @param <T>
 */
public interface SpiceCallbackInterface<T> {

  void onSpiceSuccess(T response);

  void onSpiceError(RestCall<T> restCall, int reason, Throwable exception);

  void onSpiceError(RestCall<T> restCall, StringResponse response);

}
