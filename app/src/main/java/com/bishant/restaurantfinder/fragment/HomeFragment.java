package com.bishant.restaurantfinder.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.bishant.restaurantfinder.R;
import com.bishant.restaurantfinder.activity.RestaurantListActivity;
import com.bishant.restaurantfinder.helper.RestaurantFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.Context.MODE_PRIVATE;

/*
 * Created by bishant on 12-Nov-19.
 * Copyright (c) 2019 Softwarica. All rights reserved.
 *
 */
public class HomeFragment extends RestaurantFragment implements View.OnClickListener,
        OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private MapView mapView;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private Marker mCurrLocationMarker;
    private FragmentActivity activity;
    private Context mContext;
    private Button btnFindRestaurants;
    public String latLngString;
    public double source_lat, source_long;
    public static final String PREFS_FILE_NAME = "sharedPreferences";
    private boolean Permission_is_granted = false;

    private static final int MY_PERMISION_CODE = 10;

    private static final String TAG = "MapFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        initialiseView(rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialiseListeners();
        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    @Override
    protected void initialiseView(View view) {
        btnFindRestaurants = view.findViewById(R.id.btn_find_restaurant);
    }

    @Override
    protected void initialiseListeners() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
        btnFindRestaurants.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_find_restaurant:
                Intent intent = new Intent(mContext, RestaurantListActivity.class);
                intent.putExtra("latlng", latLngString);
                intent.putExtra("mLastLocation", mLastLocation);
                Log.e(TAG, "current address: " + latLngString);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
        mGoogleMap.setMinZoomPreference(4f);
        mGoogleMap.setMaxZoomPreference(17.5f);

        getUserLocation();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (FragmentActivity) context;
        mContext = context;
    }

    private void getUserLocation() {
        if (ActivityCompat.checkSelfPermission(mContext, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        ACCESS_FINE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        ACCESS_COARSE_LOCATION)) {
                    showAlert();
                } else {
                    if (isFirstTimeAskingPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        firstTimeAskingPermission(mContext,
                                Manifest.permission.ACCESS_FINE_LOCATION, false);
                        ActivityCompat.requestPermissions(activity,
                                new String[]{ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION},
                                MY_PERMISION_CODE);
                    } else {
                        Toast.makeText(mContext, "You won't be able to access the features of this App", Toast.LENGTH_LONG).show();
                        //progress.setVisibility(View.GONE);
                        //Permission disable by device policy or user denied permanently. Show proper error message
                    }
                }
            } else Permission_is_granted = true;
        } else {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {

                        mLastLocation = location;
                        source_lat = location.getLatitude();
                        source_long = location.getLongitude();
                        latLngString = location.getLatitude() + "," + location.getLongitude();
                        //fetchCurrentAddress(latLngString);

                        Log.i(TAG, latLngString + "");

                        //Place current location marker
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title("Current Position");
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

                        //move map camera
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                        mGoogleMap.setMyLocationEnabled(true);
                        //fetchStores("restaurant");

                    } else {
                        //progress.setVisibility(View.GONE);
                        Toast.makeText(mContext, "Error in fetching the location", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public static void firstTimeAskingPermission(Context context, String permission, boolean isFirstTime) {
        SharedPreferences sharedPreference = context.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        sharedPreference.edit().putBoolean(permission, isFirstTime).apply();
    }

    public static boolean isFirstTimeAskingPermission(Context context, String permission) {
        return context.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE).getBoolean(permission, true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.e(TAG, "permission success");
        switch (requestCode) {
            case MY_PERMISION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG, "permission if");
                    Permission_is_granted = true;
                    getUserLocation();
                } else {
                    showAlert();
                    Permission_is_granted = false;
                    Toast.makeText(mContext, "Please switch on GPS to access the features", Toast.LENGTH_LONG).show();
                    //progress.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings are OFF \nPlease Enable Location")
                .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        ActivityCompat.requestPermissions(activity,
                                new String[]{ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION},
                                MY_PERMISION_CODE);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }
}
