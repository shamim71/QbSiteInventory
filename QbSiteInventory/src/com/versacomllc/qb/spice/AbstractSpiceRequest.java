package com.versacomllc.qb.spice;

import java.util.List;
import java.util.Map;

import android.util.Log;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.versacomllc.qb.utils.Constants;
import com.versacomllc.qb.utils.Utils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public abstract class AbstractSpiceRequest<ResultType> extends
    SpringAndroidSpiceRequest<ResultType> {

  protected String endPoint;
  private HttpEntity<?> entity = null;
  protected Object request;

  public AbstractSpiceRequest(Class<ResultType> clazz, String endPoint,
      HttpEntity<?> entity, Object request) {
    super(clazz);
    this.endPoint = endPoint;
    this.entity = entity;
    this.request = request;
  }

  public AbstractSpiceRequest(Class<ResultType> clazz, String endPoint,
      HttpEntity<?> entity) {
    super(clazz);
    this.endPoint = endPoint;
    this.entity = entity;

  }

  public HttpEntity<?> getEntity() {
    if (entity == null) {
      entity = new HttpEntity<Object>(this.request,
          Utils.constructDefaultHeaders());
    }
    return entity;
  }

  public String getEndPoint() {
    return endPoint;
  }

  public void setEndPoint(String endPoint) {
    this.endPoint = endPoint;
  }

  protected abstract HttpMethod getHttpMethod();

  @Override
  public ResultType loadDataFromNetwork() throws Exception {
    Log.d(Constants.REST_LOG_TAG, "Executing request: " + endPoint);
    Log.d(Constants.REST_LOG_TAG, "Request headers:\n"
        + getHttpHeaders(getEntity().getHeaders()));
    ResponseEntity<ResultType> result = getRestTemplate().exchange(endPoint,
        getHttpMethod(), entity, getResultType());

    Log.d(Constants.REST_LOG_TAG,
        "Response headers:\n" + getHttpHeaders(result.getHeaders()));
    return result.getBody();
  }

  private static String getHttpHeaders(HttpHeaders headers) {
    StringBuilder builder = new StringBuilder();
    for (Map.Entry<String, List<String>> headerEntry : headers.entrySet()) {
      builder.append("\n").append(headerEntry.getKey()).append(": ")
          .append(headerEntry.getValue().toString());
    }
    return builder.toString();
  }

}
