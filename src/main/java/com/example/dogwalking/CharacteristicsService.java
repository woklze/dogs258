package com.example.dogwalking;

import com.example.dogwalking.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import com.example.dogwalking.dto.DogBreed;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CharacteristicsService {
    private final RestClient breedsRestClient;
    private final BreedsConfig breedsConfig;
    private final ObjectMapper objectMapper;
    private final BreedsService breedsService;

    public CharacteristicsService(BreedsConfig breedsConfig, ObjectMapper objectMapper, BreedsService breedsService) {
        this.breedsRestClient = RestClient.builder()
                .baseUrl(breedsConfig.getFullUrl())
                .defaultHeader("Accept", "application/json")
                .build();
        this.breedsConfig = breedsConfig;
        this.objectMapper = objectMapper;
        this.breedsService = breedsService;
    }

    public Optional<BreedCharacteristics> getBreedCharacteristics(String breedName) {
        return breedsService.getAllBreeds().stream()
                .filter(breed -> breed.getGeneral() != null &&
                        breed.getGeneral().getName().equalsIgnoreCase(breedName.trim()))
                .findFirst()
                .map(breed -> {
                    Behavior behavior = breed.getBehavior();
                    Care care = breed.getCare();

                    return new BreedCharacteristics(
                            breed.getGeneral().getName(),
                            care != null ? care.getExerciseNeeds() : 0,
                            behavior != null ? behavior.getPlayfulness() : 0,
                            behavior != null ? behavior.getFriendlinessToStrangers() : 0,
                            behavior != null ? behavior.getDogSociability() : 0
                    );
                });
    }

    public Optional<DogBreed> getBreedByName(String breedName) {
        return breedsService.getAllBreeds().stream()
                .filter(breed -> breed.getGeneral() != null &&
                        breed.getGeneral().getName().equalsIgnoreCase(breedName.trim()))
                .findFirst();
    }
}