package com.example.pawsome;

import java.util.ArrayList;

public class SearchModel {

    private ArrayList<Card> dogs;

    public SearchModel(ArrayList<Card> dogs) {
        this.dogs = dogs;
    }

    public ArrayList<Card> getDogs() {
        return dogs;
    }

    public void setDogs(ArrayList<Card> dogs) {
        this.dogs = dogs;
    }
}
