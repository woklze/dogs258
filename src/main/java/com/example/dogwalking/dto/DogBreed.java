package com.example.dogwalking.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.example.dogwalking.dto.General;
import com.example.dogwalking.dto.Care;
import com.example.dogwalking.dto.Behavior;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DogBreed {
    private General general;
    private Behavior behavior;
    private Care care;

    public Behavior getBehavior() { return behavior; }

    public Care getCare() { return care; }

    public General getGeneral() {
        return general;
    }
}
