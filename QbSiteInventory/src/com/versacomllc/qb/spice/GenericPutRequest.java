package com.versacomllc.qb.spice;

import org.springframework.http.HttpMethod;


public class GenericPutRequest<ResultType, RequestType> extends
    AbstractSpiceRequest<ResultType> {

  /**
   * 
   * @param clazz
   * @param url
   *          Request url of the server.
   * @param requestObject
   *          Request object to be passed to the server.
   */
  public GenericPutRequest(Class<ResultType> clazz, RequestType requestObject,
      String url) {
    super(clazz, url, null, requestObject);

  }

  @Override
  protected HttpMethod getHttpMethod() {
    return HttpMethod.PUT;
  }
}
