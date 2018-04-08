package com.renatasemanova.dailymenu.API.model;

import com.renatasemanova.dailymenu.API.ZomatoAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by renatasemanova on 2.9.17.
 */

public class RetrofitClient {

    private static final String URL = "https://d17h27t6h515a5.cloudfront.net/";

    private static Retrofit retrofitClient = null;

    private static Retrofit getRetrofitClient(){
        if (retrofitClient == null) {
            retrofitClient = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofitClient;
    }

    public static ZomatoAPI getApiService() {
        return getRetrofitClient().create(ZomatoAPI.class);
    }



}
