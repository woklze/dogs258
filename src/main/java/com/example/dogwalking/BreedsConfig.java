package com.example.dogwalking;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@Configuration
public class BreedsConfig {
    @Value("${dog.breeds.api.base-url}")
    private String baseUrl;
    @Value("${dog.breeds.api.path}")
    private String apiPath;

    public String getBaseUrl() {
        return baseUrl;
    }
    public void setBaseUrl(String baseUrl) {this.baseUrl = baseUrl;}

    public String getApiPath() {
        return apiPath;
    }
    public void setApiPath (String apiPath) {this.apiPath = apiPath;}

    public String getFullUrl(){
        return baseUrl + apiPath;
    }

    @Bean
    public RestClient.Builder restClientBuilder1() {
        return RestClient.builder();
    }
}