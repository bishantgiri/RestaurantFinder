package com.bishant.restaurantfinder.apiservices;

import com.bishant.restaurantfinder.model.PlaceDetailsModel;
import com.bishant.restaurantfinder.model.PlacesModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapsAPIInterface {

    @GET("place/nearbysearch/json?")
    Call<PlacesModel> doPlaces(@Query(value = "location", encoded = true) String location, @Query(value = "radius", encoded = true) long radius, @Query(value = "type", encoded = true) String type, @Query(value = "key", encoded = true) String key);


    @GET("distancematrix/json?") // origins/destinations:  LatLng as string
    Call<PlacesModel> getDistance(@Query(value = "origins", encoded = true) String origins, @Query(value = "destinations", encoded = true) String destinations, @Query(value = "key", encoded = true) String key);

    @GET("place/details/json?")
    Call<PlaceDetailsModel> getPlaceDetails(@Query(value = "placeid", encoded = true) String placeid, @Query(value = "key", encoded = true) String key);

}
