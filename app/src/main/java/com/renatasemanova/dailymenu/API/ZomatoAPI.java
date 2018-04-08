package com.renatasemanova.dailymenu.API;

import com.renatasemanova.dailymenu.API.model.Search;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by renatasemanova on 21.9.17.
 */

public interface ZomatoAPI {

    @GET("search?entity_id=219&entity_type=city")
    Call<Search> searchRestaurant(@Query("q") String restaurant);

    @GET("dailymenu")
    Call<Search> dailyMenu(@Query("res_id") Integer res_id);


}
