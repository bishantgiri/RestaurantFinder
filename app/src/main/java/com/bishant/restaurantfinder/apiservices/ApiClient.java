package com.bishant.restaurantfinder.apiservices;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    //    private static final String BASE_URL = "https://elibrary.smartgov.app/api/";
    private static final String BASE_URL = "http://192.168.1.76:5000/auth/";
    //    private static final String BASE_URL = "http://192.168.1.73/library/elibrary_api/public/api/";
    public static final String IMAGE_BASE_URL = "";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.authenticator(new ApiTokenAuthenticator());
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient httpClient = builder.readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new ApiInterceptor())
                .connectTimeout(60, TimeUnit.SECONDS).build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).client(httpClient).build();
        }
        return retrofit;
    }
}
