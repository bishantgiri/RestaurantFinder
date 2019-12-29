package com.bishant.restaurantfinder.helper;

import androidx.appcompat.app.AppCompatActivity;

/*
 * Created by bishant on 11-Nov-19.
 * Copyright (c) 2019 Softwarica College. All rights reserved.
 *
 */
public abstract class RestaurantActivity extends AppCompatActivity {

    abstract protected void initialiseViews();

    abstract protected void initialiseListeners();

}
