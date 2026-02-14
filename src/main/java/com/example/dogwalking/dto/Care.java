package com.example.dogwalking.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Care {
    @JsonProperty("exerciseNeeds")
    private int exerciseNeeds;

    public int getExerciseNeeds() { return exerciseNeeds; }
}