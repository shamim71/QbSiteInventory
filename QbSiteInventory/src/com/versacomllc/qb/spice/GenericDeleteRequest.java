package com.versacomllc.qb.spice;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;


public class GenericDeleteRequest<ResultType> extends
    AbstractSpiceRequest<ResultType> {

  public GenericDeleteRequest(Class<ResultType> resultType, String url) {
    super(resultType, url, null);

  }

  public GenericDeleteRequest(Class<ResultType> resultType, String url,
      HttpEntity<?> entity) {
    super(resultType, url, entity);
  }

  @Override
  protected HttpMethod getHttpMethod() {
    return HttpMethod.DELETE;
  }
}
