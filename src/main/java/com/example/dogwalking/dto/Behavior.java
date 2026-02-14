package com.example.dogwalking.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Behavior {
    @JsonProperty("dogSociability")
    private int dogSociability;

    @JsonProperty("friendlinessToStrangers")
    private int friendlinessToStrangers;

    @JsonProperty("playfulness")
    private int playfulness;

    public int getDogSociability() { return dogSociability; }
    public int getFriendlinessToStrangers() { return friendlinessToStrangers; }
    public int getPlayfulness() { return playfulness; }
}