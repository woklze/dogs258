package com.example.dogwalking;
import com.example.dogwalking.dto.DogBreed;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BreedsService {
    private final RestClient breedsRestClient;
    private final BreedsConfig breedsConfig;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public BreedsService(BreedsConfig breedsConfig) {
        this.breedsRestClient = RestClient.builder()
                .baseUrl(breedsConfig.getFullUrl())
                .defaultHeader("Accept", "application/json")
                .build();
        this.breedsConfig = breedsConfig;
    }


    public List<DogBreed> getAllBreeds() {
        try {
            String jsonResponse = breedsRestClient.get()
                    .retrieve()
                    .body(String.class);

            // парсим JSON
            DogBreed response = objectMapper.readValue(jsonResponse, DogBreed.class);
            return response.getData();

        } catch (Exception e) {
            System.err.println("Ошибка при получении пород: " + e.getMessage());
            return List.of();
        }
    }

    public List<String> getAllBreedNames() {
        return getAllBreeds().stream()
                .map(breed -> breed.getGeneral().getName())
                .collect(Collectors.toList());
    }


}