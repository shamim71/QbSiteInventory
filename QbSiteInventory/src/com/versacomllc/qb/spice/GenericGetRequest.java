package com.versacomllc.qb.spice;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;


public class GenericGetRequest<ResultType> extends
    AbstractSpiceRequest<ResultType> {

  /**
   * 
   * @param clazz
   *          The resultType
   * @param url
   *          the formattedUrl
   */
  public GenericGetRequest(Class<ResultType> clazz, String url) {
    super(clazz, url, null);

  }

  /**
   * 
   * @param clazz
   *          The resultType
   * @param requestEntity
   *          The requestEntity
   * @param url
   *          The formatted Url
   */
  public GenericGetRequest(Class<ResultType> clazz,
      HttpEntity<?> requestEntity, String url) {
    super(clazz, url, requestEntity);
  }

  @Override
  protected HttpMethod getHttpMethod() {
    return HttpMethod.GET;
  }
}