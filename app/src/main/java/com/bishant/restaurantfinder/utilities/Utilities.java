package com.bishant.restaurantfinder.utilities;

import android.content.SharedPreferences;

import com.bishant.restaurantfinder.helper.Constants;
import com.bishant.restaurantfinder.model.PlaceDetailsModel;
import com.bishant.restaurantfinder.model.PlacesModel;
import com.google.gson.GsonBuilder;

import static com.bishant.restaurantfinder.helper.RestaurantApplication.getSharedPreference;

/*
 * Created by bishant on 16-Nov-19.
 * Copyright (c) 2019 Softwarica. All rights reserved.
 *
 */
public class Utilities {

    public static boolean isLogin() {
        boolean isLogin = getSharedPreference().getBoolean(Constants.IS_FIRST_LOGIN, false);
        return isLogin;
    }

    public static boolean isFirstRun() {
        boolean isFirstRun = getSharedPreference().getBoolean(Constants.IS_FIRST_RUN, false);
        return isFirstRun;
    }

    public static void setFirstRunCompleted() {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putBoolean(Constants.IS_FIRST_RUN, true);
        editor.apply();
    }

    public static void saveRestaurantData(PlacesModel placesModel) {
        String json = new GsonBuilder().create().toJson(placesModel);
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putString(Constants.PLACE_RESPONSE, json);
        editor.apply();
    }

    public static PlacesModel getRestaurantData() {
        String savedPlaceData = getSharedPreference().getString(Constants.PLACE_RESPONSE, null);
        return new GsonBuilder().create().fromJson(savedPlaceData, PlacesModel.class);
    }

    public static void saveCurrentLocationData(String currentLocationCoordinates) {
//        String json = new GsonBuilder().create().toJson(placesModel);
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putString(Constants.CURRENT_LOCATION, currentLocationCoordinates);
        editor.apply();
    }

    public static String getCurrentLocationData() {
        return getSharedPreference().getString(Constants.CURRENT_LOCATION, null);
    }

    public static void saveDetailData(PlaceDetailsModel placeDetailsModel, String placeID) {
        String json = new GsonBuilder().create().toJson(placeDetailsModel);
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putString(Constants.PLACE_RESPONSE + placeID, json);
        editor.apply();
    }

    public static PlaceDetailsModel getDetailData(String placeID) {
        String savedPlaceData = getSharedPreference().getString(Constants.PLACE_RESPONSE + placeID, null);
        return new GsonBuilder().create().fromJson(savedPlaceData, PlaceDetailsModel.class);
    }
}
