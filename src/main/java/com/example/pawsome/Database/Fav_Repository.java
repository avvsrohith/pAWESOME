package com.example.pawsome.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.security.PublicKey;
import java.util.List;

import retrofit2.http.PUT;

public class Fav_Repository {
    private Fav_DAO fav_dao;
    private LiveData<List<Fav_Entity>> allFavs;

    public Fav_Repository(Application application){
        Fav_Database database=Fav_Database.getInstance(application);
        fav_dao=database.fav_dao();
        allFavs= fav_dao.get_favs();
    }

    public void addToFavs(Fav_Entity fav_entity){
        new InsertfavAsyncTask(fav_dao).execute(fav_entity);
    }

    public void deleteFromFavs(Fav_Entity fav_entity){
        new DeletefavAsyncTask(fav_dao).execute(fav_entity);
    }

    public void update(Fav_Entity fav_entity){
        new UpdatefavAsyncTask(fav_dao).execute(fav_entity);
    }

    public LiveData<List<Fav_Entity>> getAllFavs(){
        return allFavs;
    }

    private static class InsertfavAsyncTask extends AsyncTask<Fav_Entity,Void,Void>{

        private Fav_DAO fav_dao;

        private InsertfavAsyncTask(Fav_DAO fav_dao){
            this.fav_dao=fav_dao;
        }

        @Override
        protected Void doInBackground(Fav_Entity... fav_entities) {
            fav_dao.addToFavs(fav_entities[0]);
            return null;
        }
    }

    private static class UpdatefavAsyncTask extends AsyncTask<Fav_Entity,Void,Void>{

        private Fav_DAO fav_dao;

        private UpdatefavAsyncTask(Fav_DAO fav_dao){
            this.fav_dao=fav_dao;
        }

        @Override
        protected Void doInBackground(Fav_Entity... fav_entities) {
            fav_dao.update(fav_entities[0]);
            return null;
        }
    }

    private static class DeletefavAsyncTask extends AsyncTask<Fav_Entity,Void,Void>{

        private Fav_DAO fav_dao;

        private DeletefavAsyncTask(Fav_DAO fav_dao){
            this.fav_dao=fav_dao;
        }

        @Override
        protected Void doInBackground(Fav_Entity... fav_entities) {
            fav_dao.deleteFromFavs(fav_entities[0]);
            return null;
        }
    }

}
