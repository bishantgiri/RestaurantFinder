package com.bishant.restaurantfinder.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bishant.restaurantfinder.R;
import com.bishant.restaurantfinder.helper.RestaurantFragment;

/*
 * Created by Bishant on 26-Nov-19.
 * Copyright (c) 2019 Softwarica. All rights reserved.
 *
 */
public class OrdersFragment extends RestaurantFragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order, container, false);


        return rootView;
    }

    @Override
    protected void initialiseView(View view) {

    }

    @Override
    protected void initialiseListeners() {

    }
}
