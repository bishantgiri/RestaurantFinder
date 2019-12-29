package com.bishant.restaurantfinder.presenters;

import android.util.Log;

import com.bishant.restaurantfinder.apiservices.MapsAPIInterface;
import com.bishant.restaurantfinder.apiservices.MapsApiClient;
import com.bishant.restaurantfinder.helper.Constants;
import com.bishant.restaurantfinder.model.PlaceDetailsModel;
import com.bishant.restaurantfinder.model.PlacesModel;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * Created by bishant on 09-Nov-19.
 * Copyright (c) 2019 softwarica. All rights reserved.
 *
 */
public class RestaurentPresenter {

    private WeakReference<View> view;

    public RestaurentPresenter(View view) {
        this.view = new WeakReference<>(view);
    }

    public interface View {
        void onPlacesResponseSuccess(PlacesModel placesResponse);

        void onPlacesDetailResponseSuccess(PlaceDetailsModel placeDetailResponse);

        void onResponseFailure(String message);

        //void onDistanceResponseSuccess(PlacesModel distanceModel);
    }

    public View getView() throws NullPointerException {
        if (view != null) {
            return view.get();
        } else {
            throw new NullPointerException("view is unavailable");
        }
    }

    public void getRestaurants(final String latlong, long radius) {
        MapsAPIInterface apiService = MapsApiClient.getClient().create(MapsAPIInterface.class);
        Call<PlacesModel> call = apiService.doPlaces(latlong, radius, "restaurant", Constants.MAPS_API_KEY);
        call.enqueue(new Callback<PlacesModel>() {
            @Override
            public void onResponse(Call<PlacesModel> call, Response<PlacesModel> response) {
                if (response.isSuccessful()) {
                    //Log.e("response", new GsonBuilder().create().toJson(response.body()));
                    if (response.body().getStatus().equals("OK")) {
                        /*for (int i=0; i<15; i++){
                            String destinationLatLng = response.body().getResults().get(i).getGeometry().getLocation().getLat()+","+response.body().getResults().get(i).getGeometry().getLocation().getLat();
                            getRestaurantDistance(latlong, destinationLatLng);
                        }*/
                        getView().onPlacesResponseSuccess(response.body());
                    }
                } else {
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            getView().onResponseFailure("Something went wrong. Try again!");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PlacesModel> call, Throwable throwable) {
                Log.e("place api failure: ", throwable.getLocalizedMessage());
            }
        });
    }

    public void getRestaurantDetail(final String placeID) {
        MapsAPIInterface apiService = MapsApiClient.getClient().create(MapsAPIInterface.class);
        Call<PlaceDetailsModel> call = apiService.getPlaceDetails(placeID, Constants.MAPS_API_KEY_NEW);
        call.enqueue(new Callback<PlaceDetailsModel>() {
            @Override
            public void onResponse(Call<PlaceDetailsModel> call, Response<PlaceDetailsModel> response) {
                if (response.isSuccessful()) {
                    Log.e("response", new GsonBuilder().create().toJson(response.body()));
                    if (response.body().getStatus().equals("OK")) {
                        getView().onPlacesDetailResponseSuccess(response.body());
                    }
                } else {
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            getView().onResponseFailure("Something went wrong. Try again!");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PlaceDetailsModel> call, Throwable throwable) {
                Log.e("place api failure: ", throwable.getLocalizedMessage());
            }
        });
    }

    public void getRestaurantDistance(String originLatLng, String destinationLatLng) {
        MapsAPIInterface apiService = MapsApiClient.getClient().create(MapsAPIInterface.class);
        Call<PlacesModel> call = apiService.getDistance(originLatLng, destinationLatLng, Constants.MAPS_API_KEY);
        call.enqueue(new Callback<PlacesModel>() {
            @Override
            public void onResponse(Call<PlacesModel> call, Response<PlacesModel> response) {
                if (response.isSuccessful()) {
                    Log.e("response", new GsonBuilder().create().toJson(response.body()));
                    if (response.body().getStatus().equals("OK")) {
                        //getView().onDistanceResponseSuccess(response.body());
                    }
                } else {
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            getView().onResponseFailure("Something went wrong. Try again!");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PlacesModel> call, Throwable throwable) {
                Log.e("place api failure: ", throwable.getLocalizedMessage());
            }
        });
    }
}
