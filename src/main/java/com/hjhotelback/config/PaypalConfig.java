package com.hjhotelback.config;

import java.util.function.Consumer;

import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.paypal.sdk.Environment;
import com.paypal.sdk.PaypalServerSDKClient;
import com.paypal.sdk.authentication.ClientCredentialsAuthModel;
import com.paypal.sdk.http.client.HttpClientConfiguration;
import com.paypal.sdk.logging.configuration.ApiLoggingConfiguration;
import com.paypal.sdk.logging.configuration.ApiRequestLoggingConfiguration;
import com.paypal.sdk.logging.configuration.ApiResponseLoggingConfiguration;


@SpringBootApplication
public class PaypalConfig {

	@Value("${PAYPAL_CLIENT_ID}")
	private String PAYPAL_CLIENT_ID;
	
	@Value("${PAYPAL_CLIENT_SECRET}")
	private String PAYPAL_CLIENT_SECRET;
	  
	@Bean
	  public PaypalServerSDKClient paypalClient() {
	    PaypalServerSDKClient.Builder clientBuilder = new PaypalServerSDKClient.Builder();
	    // Logging configuration
	    clientBuilder.loggingConfig(new Consumer < ApiLoggingConfiguration.Builder > () {
	        @Override
	        public void accept(ApiLoggingConfiguration.Builder builder) {
	          builder.level(Level.DEBUG).requestConfig(new Consumer < ApiRequestLoggingConfiguration.Builder > () {
	            @Override
	            public void accept(ApiRequestLoggingConfiguration.Builder builder) {
	              builder.body(true);
	            }
	          }).responseConfig(new Consumer < ApiResponseLoggingConfiguration.Builder > () {
	            @Override
	            public void accept(ApiResponseLoggingConfiguration.Builder builder) {
	              builder.headers(true);
	            }
	          });
	        }
	      })
	      .httpClientConfig(new Consumer < HttpClientConfiguration.Builder > () {
	        @Override
	        public void accept(HttpClientConfiguration.Builder builder) {
	          builder.timeout(0);
	        }
	      })
	      .environment(Environment.SANDBOX)
	      .clientCredentialsAuth(new ClientCredentialsAuthModel.Builder(
	        PAYPAL_CLIENT_ID,
	        PAYPAL_CLIENT_SECRET).build())
	      .build();

	    return clientBuilder.build();
	  }
}
