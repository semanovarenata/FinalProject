package com.renatasemanova.dailymenu.API;

/**
 * Created by renatasemanova on 2.9.17.
 */

public class ApiUtils {

    public static final String BASE_URL = "https://developers.zomato.com/api/v2.1/";

    public static ZomatoAPI getZomatoApi() {
        return RetrofitClient.getRetrofitClient(BASE_URL).create(ZomatoAPI.class);
    }
}