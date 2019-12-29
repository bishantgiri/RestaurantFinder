package com.bishant.restaurantfinder.apiservices;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ApiInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {

        Request originalRequest = chain.request();
        //if (Utilities.getLoginResponse() == null || TextUtils.isEmpty(Utilities.getLoginResponse().getUserDetails().getToken())) {
        return chain.proceed(originalRequest);
//        }
        /*Request request = originalRequest.newBuilder()
                //.addHeader("Authorization", Utilities.getLoginResponse().getUserDetails().getToken())
                .addHeader("Accept", "Accept: application/x.school.v1+json")
                .build();

        return chain.proceed(request);*/
    }
}

