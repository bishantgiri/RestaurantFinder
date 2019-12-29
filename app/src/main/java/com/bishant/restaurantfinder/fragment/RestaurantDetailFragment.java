package com.bishant.restaurantfinder.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bishant.restaurantfinder.R;
import com.bishant.restaurantfinder.helper.Constants;
import com.bishant.restaurantfinder.helper.RestaurantFragment;
import com.bishant.restaurantfinder.model.PlaceDetailsModel;
import com.bishant.restaurantfinder.utilities.Utilities;

/*
 * Created by bishant on 12-Nov-19.
 * Copyright (c) 2019 Softwarica. All rights reserved.
 *
 */
public class RestaurantDetailFragment extends RestaurantFragment implements View.OnClickListener {

    private TextView tvRestName, tvRestAddress, tvRestPhone, tvRestWebsite, tvOpeningHours, tvOpenNow;
    private ImageView ivOpenNow;
    private RatingBar ratingBar;
    private String placeID;

    private PlaceDetailsModel placeDetailsModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_resturant_detail, container, false);

        placeID = getArguments().getString(Constants.PLACE_ID);

        initialiseView(rootView);
        initialiseListeners();

        placeDetailsModel = Utilities.getDetailData(placeID);

        setUpDetailData();

        return rootView;
    }

    private void setUpDetailData() {
        tvRestName.setText(placeDetailsModel.getResult().getName());
        ratingBar.setRating(placeDetailsModel.getResult().getRating());
        tvRestAddress.setText(placeDetailsModel.getResult().getVicinity());
        tvRestPhone.setText(placeDetailsModel.getResult().getFormattedPhoneNumber());
        tvRestWebsite.setText(placeDetailsModel.getResult().getWebsite());

        StringBuilder openingHours = new StringBuilder();

        if (placeDetailsModel.getResult().getOpeningHours() != null) {
            if (placeDetailsModel.getResult().getOpeningHours().getOpenNow() != null) {
                if (placeDetailsModel.getResult().getOpeningHours().getOpenNow()) {
                    tvOpenNow.setText("Open Today");
                    ivOpenNow.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_opened));
                } else {
                    tvOpenNow.setText("Close Today");
                    ivOpenNow.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_closed));
                }
            }

            if (placeDetailsModel.getResult().getOpeningHours().getWeekdayText() != null) {
                for (int i = 0; i < placeDetailsModel.getResult().getOpeningHours().getWeekdayText().size(); i++) {
                    openingHours.append(placeDetailsModel.getResult().getOpeningHours().getWeekdayText().get(i)).append("\n");
                }
            }
        }
        tvOpeningHours.setText(openingHours);
    }

    @Override
    protected void initialiseView(View view) {
        ratingBar = view.findViewById(R.id.rating_bar);
        tvRestName = view.findViewById(R.id.tv_rest_name);
        tvRestAddress = view.findViewById(R.id.tv_rest_address);
        tvRestPhone = view.findViewById(R.id.tv_rest_phone);
        tvRestWebsite = view.findViewById(R.id.tv_rest_website);
        tvOpeningHours = view.findViewById(R.id.tv_rest_opening_hours);
        tvOpenNow = view.findViewById(R.id.tv_rest_open_now);
        ivOpenNow = view.findViewById(R.id.iv_open_now);
    }

    @Override
    protected void initialiseListeners() {

    }

    @Override
    public void onClick(View v) {

    }
}