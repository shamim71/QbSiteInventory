package com.versacomllc.qb.spice;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;


public class GenericPostRequest<ResultType> extends
    AbstractSpiceRequest<ResultType> {

  /**
   * 
   * @param clazz
   *          The resultType
   * @param url
   *          The formatted Url
   * @param request
   *          the requestObject
   */
  public GenericPostRequest(Class<ResultType> clazz, String url, Object request) {
    super(clazz, url, null, request);
  }

  public GenericPostRequest(Class<ResultType> clazz, String endPoint,
      HttpEntity<?> entity) {
    super(clazz, endPoint, entity, null);
  }

  @Override
  protected HttpMethod getHttpMethod() {
    return HttpMethod.POST;
  }
}
