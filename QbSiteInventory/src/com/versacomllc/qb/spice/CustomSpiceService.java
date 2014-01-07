package com.versacomllc.qb.spice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Application;

import com.octo.android.robospice.SpringAndroidSpiceService;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.springandroid.json.gson.GsonObjectPersisterFactory;

public class CustomSpiceService extends SpringAndroidSpiceService {

  private static final int SERVICE_CONNECT_TIMEOUT = 30000;
  private static final int SERVICE_READ_TIMEOUT = 60000;

  private static List<MediaType> types;

  @Override
  public RestTemplate createRestTemplate() {

    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    requestFactory.setConnectTimeout(SERVICE_CONNECT_TIMEOUT);
    requestFactory.setReadTimeout(SERVICE_READ_TIMEOUT);

    RestTemplate restTemplate = new RestTemplate();

    final List<HttpMessageConverter<?>> listHttpMessageConverters = restTemplate
        .getMessageConverters();

    GsonHttpMessageConverter jsonConverter = new GsonHttpMessageConverter();
    //StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();

   // listHttpMessageConverters.add(stringConverter);
    listHttpMessageConverters.add(jsonConverter);

    jsonConverter.setSupportedMediaTypes(getSupportedMediaTypes());

    //stringConverter.setSupportedMediaTypes(getSupportedMediaTypes());
    restTemplate.setRequestFactory(requestFactory);
    restTemplate.setMessageConverters(listHttpMessageConverters);

    return restTemplate;
  }

  @Override
  public CacheManager createCacheManager(Application application) {
    CacheManager cacheManager = new CacheManager();
    cacheManager.addPersister(new GsonObjectPersisterFactory(application));
    return cacheManager;
  }

  public static List<MediaType> getSupportedMediaTypes() {
    if (types == null) {
      types = new ArrayList<MediaType>();
      
      final Map<String, String> parameterMap = new HashMap<String, String>(4);
      parameterMap.put("charset", "UTF-8");
      
      types.add(new MediaType("application", "json",parameterMap));
     // types.add(new MediaType("text", "html"));

    }
    return types;
  }

}