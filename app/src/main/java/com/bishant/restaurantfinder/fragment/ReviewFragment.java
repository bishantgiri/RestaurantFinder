package com.bishant.restaurantfinder.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bishant.restaurantfinder.R;
import com.bishant.restaurantfinder.helper.Constants;
import com.bishant.restaurantfinder.helper.RestaurantFragment;
import com.bishant.restaurantfinder.helper.RestaurantRecyclerViewAdapter;
import com.bishant.restaurantfinder.model.PlaceDetailsModel;
import com.bishant.restaurantfinder.utilities.Utilities;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import de.hdodenhof.circleimageview.CircleImageView;

/*
 * Created by Bishant on 26-Nov-19.
 * Copyright (c) 2019 Softwarica. All rights reserved.
 *
 */
public class ReviewFragment extends RestaurantFragment {

    private RecyclerView recyclerViewReview;
    private String placeID;
    private ReviewRVAdapter reviewRVAdapter;
    private PlaceDetailsModel placeDetailsModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_review, container, false);

        placeID = getArguments().getString(Constants.PLACE_ID);
        placeDetailsModel = Utilities.getDetailData(placeID);

        initialiseView(rootView);
        initialiseListeners();
        prepareRecyclerView();

        return rootView;
    }

    @Override
    protected void initialiseView(View view) {
        recyclerViewReview = view.findViewById(R.id.review_recycler_view);
    }

    @Override
    protected void initialiseListeners() {

    }

    private void prepareRecyclerView() {
        reviewRVAdapter = new ReviewRVAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewReview.setAdapter(reviewRVAdapter);
        recyclerViewReview.setLayoutManager(layoutManager);
    }

    private class ReviewRVAdapter extends RestaurantRecyclerViewAdapter {

        @Override
        public void add(Object object) {

        }

        @Override
        public void clear() {

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.item_user_review, parent, false);
            return new VHReview(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof VHReview){
                VHReview vhReview = (VHReview) holder;

                PlaceDetailsModel.Result.Review review = placeDetailsModel.getResult().getReviews().get(position);

                RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(getResources().getDrawable(R.drawable.user_dummy));

                Glide.with(getActivity())
                        .applyDefaultRequestOptions(requestOptions)
                        .load(review.getProfilePhotoUrl())
                        .into(vhReview.ivUserImage);

                vhReview.tvUserName.setText(review.getAuthorName());
                vhReview.tvReviewText.setText(review.getText());
                vhReview.ratingBar.setRating(review.getRating());

            }
        }

        @Override
        public int getItemCount() {
            if (placeDetailsModel.getResult().getReviews().size() < 7)
                return placeDetailsModel.getResult().getReviews().size();
            else
                return 7;
        }

        private class VHReview extends RecyclerView.ViewHolder {

            private CircleImageView ivUserImage;
            private TextView tvUserName, tvReviewText;
            private RatingBar ratingBar;

            public VHReview(View itemView) {
                super(itemView);
                ivUserImage = itemView.findViewById(R.id.profile_image_view);
                tvUserName = itemView.findViewById(R.id.user_name);
                tvReviewText = itemView.findViewById(R.id.review_text);
                ratingBar = itemView.findViewById(R.id.rating_bar);
            }
        }
    }

}
