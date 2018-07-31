package com.onready.springresttemplateloggerautoconfigure;

import com.onready.springresttemplatelogger.RequestLoggerInterceptor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConditionalOnBean(RestTemplate.class)
public class RequestLoggerInterceptorAutoconfiguration {

  @Bean
  public CommandLineRunner addRequestLoggerInterceptor(final RestTemplate restTemplate) {
    return new CommandLineRunner() {
      public void run(String... strings) {
        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
          interceptors = new ArrayList<ClientHttpRequestInterceptor>(1);
        }
        interceptors.add(new RequestLoggerInterceptor());
        restTemplate.setInterceptors(interceptors);
      }
    };
  }

}
