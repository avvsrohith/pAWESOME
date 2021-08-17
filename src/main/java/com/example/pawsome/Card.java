package com.example.pawsome;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Card  {

    private String url;
    @SerializedName("id")
    private transient String imageId;
    @SerializedName("breeds")
    ArrayList<BreedInfo> breedInfos;

    public Card(String imageid, String url) {
        this.url = url;
        this.imageId=imageid;
    }

    public Card() {

    }

    public String getUrl() {
        return url;
    }

    public ArrayList<BreedInfo> getBreedInfos() {
        return breedInfos;
    }

    public String getImageId() {
        return imageId;
    }

    public void setBreeds(ArrayList<BreedInfo> breedInfos) {
        this.breedInfos = breedInfos;
    }
}


