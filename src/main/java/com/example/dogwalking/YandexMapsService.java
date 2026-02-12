package com.example.dogwalking;

import com.example.dogwalking.dto.GeoResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Service
public class YandexMapsService {
    private final RestClient restClient;
    private final YandexMapsConfig config;
    private final ObjectMapper objectMapper;

    public YandexMapsService(RestClient.Builder restClientBuilder, YandexMapsConfig config) {
        this.restClient = restClientBuilder
                .baseUrl(config.getBaseUrl())
                .defaultHeader("Accept", "application/json")
                .build();
        this.config = config;
        this.objectMapper = new ObjectMapper();
    }

    public GeoResult geocode(String address) {
        try {
            // 1. вызов внешнего API
            String jsonResponse = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("apikey", config.getApiKey())
                            .queryParam("geocode", address)
                            .queryParam("format", "json")
                            .build())
                    .retrieve()
                    .body(String.class);

            // 2. парсинг ответа
            return parseResponse(jsonResponse, address);

        } catch (RestClientException e) {
            return new GeoResult("Ошибка при обращении к Яндекс.Картам: " + e.getMessage());
        } catch (Exception e) {
            return new GeoResult("Внутренняя ошибка сервера: " + e.getMessage());
        }
    }
    
    private GeoResult parseResponse(String json, String originalAddress) {
        try {
            JsonNode root = objectMapper.readTree(json);

            // проверяем, найден ли хотя бы один результат
            JsonNode metaData = root.path("response")
                    .path("GeoObjectCollection")
                    .path("metaDataProperty")
                    .path("GeocoderResponseMetaData");

            int found = Integer.parseInt(metaData.path("found").asText("0"));
            if (found == 0) {
                return new GeoResult("Адрес не найден: " + originalAddress);
            }
            // берём первый объект из списка
            JsonNode featureMember = root.path("response")
                    .path("GeoObjectCollection")
                    .path("featureMember")
                    .get(0);

            JsonNode geoObject = featureMember.path("GeoObject");
            // нормализованный адрес по порядку
            String formattedAddress = geoObject.path("metaDataProperty")
                    .path("GeocoderMetaData")
                    .path("text").asText();

            // координаты (долгота ширина)
            String pos = geoObject.path("Point").path("pos").asText();
            String[] coords = pos.split(" ");
            double longitude = Double.parseDouble(coords[0]);
            double latitude = Double.parseDouble(coords[1]);
            return new GeoResult(formattedAddress, latitude, longitude);

        } catch (Exception e) {
            return new GeoResult("Не удалось обработать ответ от Яндекс.Карт: " + e.getMessage());
        }
    }
}
