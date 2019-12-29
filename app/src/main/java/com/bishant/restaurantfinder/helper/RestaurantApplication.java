package com.bishant.restaurantfinder.helper;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

/*
 * Created by bishant on 16-Nov-19.
 * Copyright (c) 2019 Softwarica. All rights reserved.
 *
 */
public class RestaurantApplication extends Application implements Application.ActivityLifecycleCallbacks {

    private static RestaurantApplication restaurantApplication;
    private static SharedPreferences sharedPreferences;

    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
        restaurantApplication = this;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getAppContext());
    }

    public static Context getAppContext() {
        return restaurantApplication.getApplicationContext();
    }

    public static SharedPreferences getSharedPreference() {
        return sharedPreferences;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
