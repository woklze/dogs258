package com.example.dogwalking;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@Configuration
public class YandexMapsConfig {
    @Value("${yandex.maps.api.key}")
    private String apiKey;
    @Value("${yandex.maps.api.base-url}")
    private String baseUrl;

    public String getApiKey() {
        return apiKey;
    }
    public String getBaseUrl() {
        return baseUrl;
    }

    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }
}