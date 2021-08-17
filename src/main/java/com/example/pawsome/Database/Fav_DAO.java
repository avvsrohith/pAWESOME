package com.example.pawsome.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pawsome.BreedInfo;

import java.util.List;

@Dao
public interface Fav_DAO {

    @Insert
    long addToFavs(Fav_Entity Favourite);

    @Update
    void update(Fav_Entity Favourite);

    @Delete
    void deleteFromFavs(Fav_Entity Favourite);

    @Query("SELECT * FROM fav_table")
    LiveData<List<Fav_Entity>> get_favs();



}
