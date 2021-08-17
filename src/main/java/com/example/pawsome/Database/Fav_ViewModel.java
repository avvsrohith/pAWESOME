package com.example.pawsome.Database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


public class Fav_ViewModel extends AndroidViewModel {

    private Fav_Repository repository;
    private LiveData<List<Fav_Entity>> allFavs;
    public Fav_ViewModel(@NonNull Application application) {
        super(application);
        repository=new Fav_Repository(application);
        allFavs=repository.getAllFavs();
    }

    public void addToFavs(Fav_Entity fav_entity){
        repository.addToFavs(fav_entity);
    }

    public void update(Fav_Entity fav_entity){
        repository.update(fav_entity);
    }

    public void deleteFromFavs(Fav_Entity fav_entity){
        repository.deleteFromFavs(fav_entity);
    }

    public LiveData<List<Fav_Entity>> getAllFavs(){
        return allFavs;
    }
}
