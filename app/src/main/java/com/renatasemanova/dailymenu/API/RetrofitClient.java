package com.renatasemanova.dailymenu.API;

import com.renatasemanova.dailymenu.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by renatasemanova on 2.9.17.
 */

public class RetrofitClient {

    private static Retrofit retrofitClient = null;

    public static Retrofit getRetrofitClient(String baseUrl) {

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(5, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }

        builder.addInterceptor(chain -> {
            Request request = chain.request().newBuilder().addHeader("Accept", "application/json").addHeader("user-key","ee27f4b709da2a9cd5568fb65e6af91a").build();
            return chain.proceed(request);
        });

        OkHttpClient client = builder.build();

        if (retrofitClient == null) {
            retrofitClient = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofitClient;
    }
}
