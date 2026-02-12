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
    private final ObjectMapper objectMapper = new ObjectMapper();

    public YandexMapsService(YandexMapsConfig config) {
        this.restClient = RestClient.builder()
                .baseUrl(config.getBaseUrl())
                .defaultHeader("Accept", "application/json")
                .build();
        this.config = config;
    }

    public GeoResult geocode(String address) {
        try {
            String jsonResponse = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("apikey", config.getApiKey())
                            .queryParam("geocode", address)
                            .queryParam("format", "json")
                            .build())
                    .retrieve()
                    .body(String.class);

            // парсим JSON
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode metaData = root.path("response")
                    .path("GeoObjectCollection")
                    .path("metaDataProperty")
                    .path("GeocoderResponseMetaData");

            int found = Integer.parseInt(metaData.path("found").asText("0"));
            if (found == 0) {
                return new GeoResult("Адрес не найден: " + address);
            }

            // извлекаем первый результат
            JsonNode featureMember = root.path("response")
                    .path("GeoObjectCollection")
                    .path("featureMember")
                    .get(0);

            JsonNode geoObject = featureMember.path("GeoObject");

            // адрес
            String formattedAddress = geoObject.path("metaDataProperty")
                    .path("GeocoderMetaData")
                    .path("text").asText();

            // координаты: строка "долгота широта"
            String pos = geoObject.path("Point").path("pos").asText();
            String[] coords = pos.split(" ");
            double longitude = Double.parseDouble(coords[0]);
            double latitude = Double.parseDouble(coords[1]);

            return new GeoResult(formattedAddress, latitude, longitude);

        } catch (Exception e) {
            return new GeoResult("Ошибка при обработке запроса: " + e.getMessage());
        }
    }
}