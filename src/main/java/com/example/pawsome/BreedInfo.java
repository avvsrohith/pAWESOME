package com.example.pawsome;

import com.google.gson.annotations.SerializedName;

public class BreedInfo extends Card{
    private String name;
    private String life_span,temperament;

    @SerializedName("id")
    private int breedId;

    public BreedInfo(String name, String life_span, String temperament, int breedid){
        super();
        this.life_span=life_span;
        this.name=name;
        this.temperament=temperament;
        this.breedId=breedid;
    }

    public String getName() {
        return name;
    }

    public String getLife_span() {
        return life_span;
    }

    public String getTemperament() {
        return temperament;
    }

    public int getBreedId() {
        return breedId;
    }
}
