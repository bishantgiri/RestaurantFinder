package com.bishant.restaurantfinder.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bishant.restaurantfinder.R;
import com.bishant.restaurantfinder.apiservices.MapsApiClient;
import com.bishant.restaurantfinder.fragment.MenuFragment;
import com.bishant.restaurantfinder.fragment.RestaurantDetailFragment;
import com.bishant.restaurantfinder.fragment.ReviewFragment;
import com.bishant.restaurantfinder.helper.Constants;
import com.bishant.restaurantfinder.helper.RestaurantActivity;
import com.bishant.restaurantfinder.model.PlaceDetailsModel;
import com.bishant.restaurantfinder.model.PlacesModel;
import com.bishant.restaurantfinder.presenters.RestaurentPresenter;
import com.bishant.restaurantfinder.utilities.Utilities;
import com.bishant.restaurantfinder.utilities.UtilitiesFunctions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDetailActivity extends RestaurantActivity implements RestaurentPresenter.View {

    private ImageView ivRestaurant;
    private TabLayout tabLayout;
    private ViewPager viewPagerDetail;
    private DetailViewPagerAdapter detailViewPagerAdapter;
    private String placeID;
    private Toolbar toolbar;
    private ProgressBar progressBar;

    private RestaurentPresenter restaurentPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            placeID = extras.getString(Constants.PLACE_ID);
        }

        ActionBar actionBar = getSupportActionBar();
//        actionBar.getNa
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Detail");

        initialiseViews();
        initialiseListeners();

        if (Utilities.getDetailData(placeID) == null) {
            if (UtilitiesFunctions.isNetworkAvailable(this)) {
                restaurentPresenter.getRestaurantDetail(placeID);
            } else {
                Toast.makeText(this, "Network not available!", Toast.LENGTH_SHORT).show();
            }
        } else {
            PlaceDetailsModel placeDetailResponse = Utilities.getDetailData(placeID);
            if (placeDetailResponse.getResult().getPhotos() != null) {
                String photoReference = placeDetailResponse.getResult().getPhotos().get(0).getPhotoReference();
                String photoUrl = MapsApiClient.base_url + "place/photo?maxwidth=500&photoreference=" + photoReference + "&key=" + Constants.MAPS_API_KEY_NEW;

                Log.e("Photo: ", photoUrl);

                RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(getResources().getDrawable(R.drawable.ic_restaurant));

                Glide.with(RestaurantDetailActivity.this)
                        .applyDefaultRequestOptions(requestOptions)
                        .load(photoUrl)
                        .into(ivRestaurant);
            }
            setUpTabLayout();
        }

//        setUpTabLayout();
    }

    @Override
    protected void initialiseViews() {
        ivRestaurant = findViewById(R.id.iv_restaurant);
        tabLayout = findViewById(R.id.tab_layout);
        viewPagerDetail = findViewById(R.id.view_pager_res_detail);
        progressBar = findViewById(R.id.progress_bar);
    }

    @Override
    protected void initialiseListeners() {
        restaurentPresenter = new RestaurentPresenter(this);
    }

    private void setUpTabLayout() {
        Log.e("fragment", "setup called");
        progressBar.setVisibility(View.GONE);
        detailViewPagerAdapter = new DetailViewPagerAdapter(getSupportFragmentManager());
        detailViewPagerAdapter.fragmentList.clear();

        Bundle bundle = new Bundle();
        bundle.putString(Constants.PLACE_ID, placeID);

        RestaurantDetailFragment restaurantDetailFragment = new RestaurantDetailFragment();
        restaurantDetailFragment.setArguments(bundle);

        ReviewFragment reviewFragment = new ReviewFragment();
        reviewFragment.setArguments(bundle);

        detailViewPagerAdapter.add(restaurantDetailFragment, "Details");
        detailViewPagerAdapter.add(new MenuFragment(), "Menu");
        detailViewPagerAdapter.add(reviewFragment, "Review");
//        detailViewPagerAdapter.add(new RestaurantDetailFragment(), "Photos");
        viewPagerDetail.setAdapter(detailViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPagerDetail);
    }

    @Override
    public void onPlacesResponseSuccess(PlacesModel placesResponse) {

    }

    @Override
    public void onPlacesDetailResponseSuccess(PlaceDetailsModel placeDetailResponse) {
        Utilities.saveDetailData(placeDetailResponse, placeID);
        if (placeDetailResponse.getResult().getPhotos() != null) {
            String photoReference = placeDetailResponse.getResult().getPhotos().get(0).getPhotoReference();
            String photoUrl = MapsApiClient.base_url + "place/photo?maxwidth=500&photoreference=" + photoReference + "&key=" + Constants.MAPS_API_KEY;

            Log.e("Photo: ", photoUrl);
            RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(getResources().getDrawable(R.drawable.resimage));

            Glide.with(RestaurantDetailActivity.this)
                    .applyDefaultRequestOptions(requestOptions)
                    .load(photoUrl)
                    .into(ivRestaurant);
        }
        setUpTabLayout();
    }

    @Override
    public void onResponseFailure(String message) {
        if (message != null)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
    }

    private class DetailViewPagerAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> fragmentList = new ArrayList<>();
        private List<String> titleList = new ArrayList<>();

        private DetailViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        void add(Fragment fragment, String title) {
            if (!fragment.isAdded()) {
                fragmentList.add(fragment);
                titleList.add(title);
            }
        }

        void clear() {
            fragmentList.clear();
            titleList.clear();
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
