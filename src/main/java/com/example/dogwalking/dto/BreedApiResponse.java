package com.example.dogwalking.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import com.example.dogwalking.dto.DogBreed;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BreedApiResponse {
    private List<DogBreed> data;

    public List<DogBreed> getData() { return data; }
}