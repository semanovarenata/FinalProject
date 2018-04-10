package com.renatasemanova.dailymenu.DB;


import com.google.firebase.database.IgnoreExtraProperties;
import com.renatasemanova.dailymenu.API.model.UserRating;

@IgnoreExtraProperties

public class RestaurantDB {


    public String id;
    public String restaurant;
    public String address;
    public String latitude;
    public String longitude;
    public UserRating rating;


    public RestaurantDB(){

    }

    public RestaurantDB(String restaurant,String id, String address, String latitude, String longitude, UserRating rating){
        this.restaurant = restaurant;
        this.id = id;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
    }
}
