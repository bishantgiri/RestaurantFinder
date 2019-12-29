package com.bishant.restaurantfinder.presenters;

import com.google.gson.JsonObject;

import java.lang.ref.WeakReference;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * Created by bishant on 09-Nov-19.
 * Copyright (c) 2019 softwarica. All rights reserved.
 *
 */
public class HomePresenter {

    private WeakReference<View> view;

    public HomePresenter(View view) {
        this.view = new WeakReference<>(view);
    }

    public interface View {
//        void onResponseSuccess(ResponseModel responseModel);
    }

    public View getView() throws NullPointerException {
        if (view != null) {
            return view.get();
        } else {
            throw new NullPointerException("view is unavailable");
        }
    }

    public void getResponse() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.76:5000/auth/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", "superadmin");
        jsonObject.addProperty("password", "password");

//        RestaurantApiService restaurantApiService = retrofit.create(RestaurantApiService.class);

        /*restaurantApiService.getResponse(jsonObject).enqueue(new RestaurantCallBack<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                Log.e("response ", response.code() + "");
                if (response.isSuccessful()) {

                    Log.e("response ", response.code() + "");
                    Log.e("response ", response.headers().size() + "");
                } else {
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            //getView().onResponseFailure("Something went wrong. Try again!");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable throwable) {
                //getView().onResponseFailure(super.getCause());
                Log.e("failure: ", throwable.getLocalizedMessage());
                throwable.printStackTrace();
            }
        });*/
    }
}
