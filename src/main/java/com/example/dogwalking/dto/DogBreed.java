package com.example.dogwalking.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DogBreed {
    private String status;
    private List<DogBreed> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DogBreed> getData() {
        return data;
    }

    public void setData(List<DogBreed> data) {
        this.data = data;
    }

    private String id;
    private GeneralInfo general;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GeneralInfo getGeneral() {
        return general;
    }

    public void setGeneral(GeneralInfo general) {
        this.general = general;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GeneralInfo {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

}
