package com.example.pawsome.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Fav_Entity.class},version = 1)
public abstract class Fav_Database extends RoomDatabase {

    private static Fav_Database instance;
    public abstract Fav_DAO fav_dao();


    public static synchronized Fav_Database getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    Fav_Database.class,"fav_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback=new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

    private static class PopulateAsyncTask extends AsyncTask<Void,Void,Void>{

        private Fav_DAO fav_dao;

        private PopulateAsyncTask(Fav_Database db){
            fav_dao= db.fav_dao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }


}
