package com.bishant.restaurantfinder.helper;

import androidx.recyclerview.widget.RecyclerView;

/*
 * Created by bishant on 13-Nov-19.
 * Copyright (c) 2019 Softwarica. All rights reserved.
 *
 */
public abstract class RestaurantRecyclerViewAdapter extends RecyclerView.Adapter {
    public abstract void add(Object object);

    public abstract void clear();
}
