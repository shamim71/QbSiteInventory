package com.versacomllc.qb.spice;

import com.octo.android.robospice.request.SpiceRequest;

/**
 * Saving states to retry the connection, if required.
 * 
 * @param <ResponseType>
 */
public class RestCall<ResponseType> {

  public RestCall(Object requestCacheKey, long cacheExpiryDuration,
      SpiceRequest<ResponseType> request) {
    mRequestCacheKey = requestCacheKey;
    mCacheExpiryDuration = cacheExpiryDuration;
    mRequest = request;
  }

  private Object mRequestCacheKey;
  private long mCacheExpiryDuration;
  private SpiceRequest<ResponseType> mRequest;

  public Object getRequestCacheKey() {
    return mRequestCacheKey;
  }

  public long getCacheExpiryDuration() {
    return mCacheExpiryDuration;
  }

  public SpiceRequest<ResponseType> getSpiceRequest() {
    return mRequest;
  }
}