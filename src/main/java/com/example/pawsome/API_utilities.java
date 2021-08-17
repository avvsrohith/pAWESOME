package com.example.pawsome;

import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API_utilities {

    public static final String Base_URL="https://api.thedogapi.com/";
    public static final String api_key="cbae1cbf-8e4c-41ba-b294-cc308d855b03";

    public static Retrofit retrofit=null;

    public static Dog_API getAPIinterface(){
        if(retrofit==null){
            retrofit=new Retrofit.Builder().
                    baseUrl(Base_URL).
                    addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit.create(Dog_API.class);
    }
}
