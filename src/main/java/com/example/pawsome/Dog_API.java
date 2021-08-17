package com.example.pawsome;

import static com.example.pawsome.API_utilities.api_key;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Dog_API {

    @Headers("x-api-key:"+ api_key)
    @GET("v1/images/search")
    Call<List<Card>> getDogs(
            @Query("page") int page,
            @Query("limit") int limit
    );

    @Headers("x-api-key:"+ api_key)
    @GET("v1/breeds/search")
    Call<List<BreedInfo>> searchdogs(
            @Query("q") String query
    );

    @Headers("x-api-key:"+ api_key)
    @GET("v1/images/search")
    Call <List<Card>> getbyid(
            @Query("breed_ids") int breedid,
            @Query("limit") int limit
    );

    @Headers("x-api-key:"+ api_key)
    @POST("v1/favourites")
    Call<PostFavourite> addToFav(
            @Body PostFavourite postFavourite);

    @Headers("x-api-key:"+ api_key)
    @GET("v1/favourites")
    Call<List<FavModel>> getFavs(
            @Query("sub_id") String sub_id
    );

    @Headers("x-api-key:"+ api_key)
    @GET("v1/images/{image_id}")
    Call<Card> getFavBody(
            @Path("image_id") String image_id
    );

    @Headers("x-api-key:"+api_key)
    @DELETE("v1/favourites/{favourite_id}")
    Call<Void> deleteFromFavs(
            @Path("favourite_id") String favId
    );


}
