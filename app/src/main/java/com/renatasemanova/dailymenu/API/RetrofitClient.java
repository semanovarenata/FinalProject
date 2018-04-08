package com.renatasemanova.dailymenu.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by renatasemanova on 2.9.17.
 */

public class RetrofitClient {

    private static Retrofit retrofitClient = null;

    public static Retrofit getRetrofitClient(String baseUrl) {
        if (retrofitClient == null) {
            retrofitClient = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofitClient;
    }
}
