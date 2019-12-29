package com.bishant.restaurantfinder.activity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bishant.restaurantfinder.R;
import com.bishant.restaurantfinder.apiservices.MapsApiClient;
import com.bishant.restaurantfinder.helper.Constants;
import com.bishant.restaurantfinder.helper.FetchAddressIntentService;
import com.bishant.restaurantfinder.helper.RestaurantActivity;
import com.bishant.restaurantfinder.helper.RestaurantApplication;
import com.bishant.restaurantfinder.helper.RestaurantRecyclerViewAdapter;
import com.bishant.restaurantfinder.helper.RestaurantType;
import com.bishant.restaurantfinder.model.PlaceDetailsModel;
import com.bishant.restaurantfinder.model.PlacesModel;
import com.bishant.restaurantfinder.presenters.RestaurentPresenter;
import com.bishant.restaurantfinder.utilities.Utilities;
import com.bishant.restaurantfinder.utilities.UtilitiesFunctions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

public class RestaurantListActivity extends RestaurantActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, RestaurentPresenter.View {

    private RecyclerView recyclerViewRestaurants;
    private RestaurantsRVAdapter restaurantsRVAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private RestaurentPresenter restaurentPresenter;
    private GoogleApiClient mGoogleApiClient;
    private PlacesClient placesClient;
    private PlacesModel placesModel;
    protected Location lastLocation;
    private AddressResultReceiver resultReceiver;
    private String addressOutput;
    private Location mLastLocation;

    private String TAG = "RestaurantListActivity ";

    private String currentLatLong;
    private long radius = 3 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentLatLong = extras.getString("latlng");
            lastLocation = (Location) extras.get("mLastLocation");
            mLastLocation = getIntent().getParcelableExtra("mLastLocation");
            Log.e("latlong: ", currentLatLong);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Nearby Restaurants");

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        Places.initialize(RestaurantApplication.getAppContext(), Constants.MAPS_API_KEY);
        placesClient = Places.createClient(this);

        resultReceiver = new AddressResultReceiver(new Handler());

        startIntentService();

        //placesClient.findCurrentPlace();

        initialiseViews();
        initialiseListeners();
        prepareRecyclerView();

        if (Utilities.getCurrentLocationData() == null) {
            if (UtilitiesFunctions.isNetworkAvailable(this)) {
                restaurentPresenter.getRestaurants(currentLatLong, radius);
            } else {
                Toast.makeText(this, "Network not available!", Toast.LENGTH_SHORT).show();
            }
            Utilities.saveCurrentLocationData(currentLatLong);
        }else {
            if(!Utilities.getCurrentLocationData().equals(currentLatLong)){
                if (UtilitiesFunctions.isNetworkAvailable(this)) {
                    restaurentPresenter.getRestaurants(currentLatLong, radius);
                } else {
                    Toast.makeText(this, "Network not available!", Toast.LENGTH_SHORT).show();
                }
                Utilities.saveCurrentLocationData(currentLatLong);
            }
        }

        setupRestaurantData();
    }

    @Override
    protected void initialiseViews() {
        recyclerViewRestaurants = findViewById(R.id.restaurants_recycler_view);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        progressBar = findViewById(R.id.progress_bar);
    }

    @Override
    protected void initialiseListeners() {
        restaurentPresenter = new RestaurentPresenter(this);
    }

    protected void prepareRecyclerView() {
        restaurantsRVAdapter = new RestaurantsRVAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewRestaurants.setAdapter(restaurantsRVAdapter);
        recyclerViewRestaurants.setLayoutManager(layoutManager);
    }

    private void setupRestaurantData() {
        if (Utilities.getRestaurantData() != null) {
            placesModel = Utilities.getRestaurantData();
            if (placesModel != null) {
                restaurantsRVAdapter.clear();
                restaurantsRVAdapter.add(placesModel);
                restaurantsRVAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    private class RestaurantsRVAdapter extends RestaurantRecyclerViewAdapter {

        private PlacesModel placesModel;
        private final int TYPE_HEADER = 0;
        private final int TYPE_FOOTER = 1;

        @Override
        public void add(Object object) {
            this.placesModel = RestaurantType.getType(object, PlacesModel.class);
        }

        @Override
        public void clear() {
            placesModel = null;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == TYPE_HEADER) {
                View itemView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_restaurant_list_header, parent, false);
                return new VHHeader(itemView);
            } else {
                View itemView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_restaurant_footer, parent, false);
                return new VHFooter(itemView);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof VHHeader) {
                VHHeader vhHeader = (VHHeader) holder;
                vhHeader.tvCurrentLocation.setText(addressOutput);
            } else {
                VHFooter vhFooter = (VHFooter) holder;
                vhFooter.restName.setText(placesModel.getResults().get(position - 1).getName());
                vhFooter.restAddress.setText(placesModel.getResults().get(position - 1).getVicinity());
                vhFooter.ratingBar.setRating(Float.valueOf(String.valueOf(placesModel.getResults().get(position - 1).getRating().floatValue())));
                vhFooter.restImage.setBackgroundColor(getResources().getColor(R.color.white));
                Glide.with(RestaurantListActivity.this)
                        .load(getResources().getDrawable(R.drawable.ic_restaurant))
                        .into(vhFooter.restImage);
                if (placesModel.getResults().get(position - 1).getPhotos() != null) {
                    String photoReference = placesModel.getResults().get(position - 1).getPhotos().get(0).getPhotoReference();
                    String photoUrl = MapsApiClient.base_url + "place/photo?maxwidth=100&photoreference=" + photoReference + "&key=" + Constants.MAPS_API_KEY;

                    RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
//                            .placeholder(getResources().getDrawable(R.drawable.ic_restaurant);

                    Glide.with(RestaurantListActivity.this)
                            .applyDefaultRequestOptions(requestOptions)
                            .load(photoUrl)
                            .into(vhFooter.restImage);
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == TYPE_HEADER) {
                return TYPE_HEADER;
            } else {
                return TYPE_FOOTER;
            }
        }

        @Override
        public int getItemCount() {
            if (placesModel != null) {
                if (placesModel.getResults() != null) {
                    if (placesModel.getResults().size() < 15) {
                        Log.e(TAG, "count: " + placesModel.getResults().size());
                        return placesModel.getResults().size() + 1;
                    } else {
                        return 15;
                    }
                }
            }
            return 0;
        }


        private class VHHeader extends RecyclerView.ViewHolder {
            private TextView tvCurrentLocation;

            public VHHeader(View itemView) {
                super(itemView);
                this.tvCurrentLocation = itemView.findViewById(R.id.location_tv);
            }
        }

        private class VHFooter extends RecyclerView.ViewHolder implements View.OnClickListener {
            private CardView cvParent;
            private TextView restName, restRating, restAddress, restPhone, restDistance;
            private RatingBar ratingBar;
            private ImageView restImage;

            public VHFooter(View itemView) {
                super(itemView);
                this.cvParent = itemView.findViewById(R.id.cv_parent);
                this.restName = itemView.findViewById(R.id.name);
                this.ratingBar = itemView.findViewById(R.id.restaurant_rating);
                this.restAddress = itemView.findViewById(R.id.address);
                this.restPhone = itemView.findViewById(R.id.phone);
                this.restDistance = itemView.findViewById(R.id.distance);
                this.restImage = itemView.findViewById(R.id.restaurant_image_view);

                cvParent.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.cv_parent) {
                    Intent intent = new Intent(RestaurantListActivity.this, RestaurantDetailActivity.class);
                    intent.putExtra(Constants.PLACE_ID, placesModel.getResults().get(getAdapterPosition()-1).getPlaceId());
                    startActivity(intent);
                }
            }
        }
    }

    protected void startIntentService() {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLastLocation);
        startService(intent);
    }

    class AddressResultReceiver extends ResultReceiver {
        AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            if (resultData == null) {
                return;
            }

            // Display the address string
            // or an error message sent from the intent service.
            addressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
            if (addressOutput == null) {
                addressOutput = "";
            }
            //displayAddressOutput();

            // Show a toast message if an address was found.
            if (resultCode == Constants.SUCCESS_RESULT) {
                Toast.makeText(RestaurantListActivity.this, getString(R.string.address_found), Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onPlacesResponseSuccess(PlacesModel placesResponse) {
        Utilities.saveRestaurantData(placesResponse);
        if (placesResponse != null) {
            if (placesResponse.getResults() != null) {
                setupRestaurantData();
            }
        }
    }

    @Override
    public void onPlacesDetailResponseSuccess(PlaceDetailsModel placeDetailResponse) {

    }

    @Override
    public void onResponseFailure(String message) {
        if (message != null)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        Log.i("on start", "true");
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        Log.i("on resume", "true");
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            //  getUserLocation();
        }
    }

    @Override
    protected void onPause() {
        Log.i("on pause", "true");

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
