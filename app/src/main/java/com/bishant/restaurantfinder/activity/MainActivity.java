package com.bishant.restaurantfinder.activity;

import android.Manifest;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bishant.restaurantfinder.R;
import com.bishant.restaurantfinder.fragment.HomeFragment;
import com.bishant.restaurantfinder.fragment.OrdersFragment;
import com.bishant.restaurantfinder.fragment.ProfileFragment;
import com.bishant.restaurantfinder.helper.RestaurantActivity;
import com.bishant.restaurantfinder.utilities.UtilitiesFunctions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends RestaurantActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    public RelativeLayout relativeLayout;
    private int selectedTab = 4;
    private int type = 0;
    private int count = 0;

    private static final String TAG = "MainActivity";

    private GoogleApiClient mGoogleApiClient;
    //    public ArrayList<PlacesDetailModal> detailModal;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    protected Location mLastLocation;

    private long radius = 3 * 1000;

    private String[] permissions = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getResources().getString(R.string.app_name));

        /*if (!checkPermissions()) {
            ActivityCompat.requestPermissions(this, permissions, 1);
        }*/

        initialiseViews();
        initialiseListeners();

        if (UtilitiesFunctions.isNetworkAvailable(this)) {
            showSnack(true);
        } else {
            showSnack(false);
        }

        bottomNavigationView.setSelectedItemId(R.id.menu_home);
    }

    @Override
    protected void initialiseViews() {
        relativeLayout = findViewById(R.id.container);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    @Override
    protected void initialiseListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_home:
                callHome();
                break;
            case R.id.menu_order:
                callOrder();
                break;
            case R.id.menu_profile:
                callProfile();
                break;
        }
        return true;
    }

    private void callHome() {
        if (selectedTab != 0) {
            FragmentManager mFragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.allActivityContainer, new HomeFragment()).commit();
            selectedTab = 0;
        }
    }

    private void callOrder() {
        if (selectedTab != 1) {
            FragmentManager mFragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.allActivityContainer, new OrdersFragment()).commit();
            selectedTab = 1;
        }
    }

    private void callProfile() {
        if (selectedTab != 2) {
            FragmentManager mFragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.allActivityContainer, new ProfileFragment()).commit();
            selectedTab = 2;
        }
    }

    /*public boolean checkPermissions() {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }*/

    public void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;
//            getUserLocation();
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar
                .make(relativeLayout, message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

}
