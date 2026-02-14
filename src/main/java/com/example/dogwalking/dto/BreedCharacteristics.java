package com.example.dogwalking.dto;

public class BreedCharacteristics {
    private String name;
    private int exerciseNeeds;
    private int playfulness;
    private int friendlinessToStrangers;
    private int dogSociability;

    public BreedCharacteristics(String name, int exerciseNeeds, int playfulness,
                                int friendlinessToStrangers, int dogSociability) {
        this.name = name;
        this.exerciseNeeds = exerciseNeeds;
        this.playfulness = playfulness;
        this.friendlinessToStrangers = friendlinessToStrangers;
        this.dogSociability = dogSociability;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getExerciseNeeds() { return exerciseNeeds; }
    public void setExerciseNeeds(int exerciseNeeds) { this.exerciseNeeds = exerciseNeeds; }
    public int getPlayfulness() { return playfulness; }
    public void setPlayfulness(int playfulness) { this.playfulness = playfulness; }
    public int getFriendlinessToStrangers() { return friendlinessToStrangers; }
    public void setFriendlinessToStrangers(int friendlinessToStrangers) { this.friendlinessToStrangers = friendlinessToStrangers; }
    public int getDogSociability() { return dogSociability; }
    public void setDogSociability(int dogSociability) { this.dogSociability = dogSociability; }
}