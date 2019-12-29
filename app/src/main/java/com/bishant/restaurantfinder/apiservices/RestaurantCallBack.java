package com.bishant.restaurantfinder.apiservices;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantCallBack<T> implements Callback<T> {

    private String cause;

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable throwable) {

        /*if (throwable instanceof UnknownHostException) {
            cause = UpasargaApplication.getAppContext().getString(R.string.no_internet_connection);
        } else if (throwable instanceof InterruptedIOException) {
            cause = UpasargaApplication.getAppContext().getString(R.string.slow_internet_connection);
        } else if (throwable instanceof ConnectException) {
            cause = UpasargaApplication.getAppContext().getString(R.string.cannot_connect_to_server);
        } else {
            cause = UpasargaApplication.getAppContext().getString(R.string.unknown_error);
        }*/
    }

    public String getCause() {
        return cause;
    }
}
