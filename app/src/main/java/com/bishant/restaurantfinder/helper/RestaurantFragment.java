package com.bishant.restaurantfinder.helper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public abstract class RestaurantFragment extends Fragment {

    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    abstract protected void initialiseView(View view);

    abstract protected void initialiseListeners();
}