package com.bishant.restaurantfinder.apiservices;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class ApiTokenAuthenticator implements Authenticator {


    @Nullable
    @Override
    public Request authenticate(@NonNull Route route, @NonNull Response response) throws IOException {
        /*TokenRefreshApiService tokenRefreshService = ApiClient.getClient().create(TokenRefreshApiService.class);
        Call<RefreshTokenModel> call = tokenRefreshService.refreshToken();
        retrofit2.Response<RefreshTokenModel> tokenResponse = call.execute();


        assert tokenResponse.body() != null;
        if (tokenResponse.isSuccessful() && tokenResponse.body().getStatus()) {
            RefreshTokenModel tokenRefreshModel = tokenResponse.body();
            assert tokenRefreshModel != null;
            Utilities.saveUserTokenLogin(tokenRefreshModel.getToken());*/

        return response.request().newBuilder()
                .addHeader("Content-Type", "application/x.school.v1+json")
                .build();
//        }
        //return null;

    }


}

