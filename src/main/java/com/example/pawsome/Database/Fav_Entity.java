package com.example.pawsome.Database;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pawsome.BreedInfo;

import java.util.ArrayList;

@Entity(tableName = "Fav_table")
public class Fav_Entity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String url;
    private String imageId;
    private String name;
    private String life_span,temperament;
    private int breedId;


    public Fav_Entity(String url, String imageId, String name, String life_span, String temperament, int breedId) {
        this.url = url;
        this.imageId = imageId;
        this.name = name;
        this.life_span = life_span;
        this.temperament = temperament;
        this.breedId = breedId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getImageId() {
        return imageId;
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

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLife_span(String life_span) {
        this.life_span = life_span;
    }

    public void setTemperament(String temperament) {
        this.temperament = temperament;
    }

    public void setBreedId(int breedId) {
        this.breedId = breedId;
    }
}
