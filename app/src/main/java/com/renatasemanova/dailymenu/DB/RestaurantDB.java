package com.renatasemanova.dailymenu.DB;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties

public class RestaurantDB {

    public String id;
    public String restaurant;

    public RestaurantDB(){

    }

    public RestaurantDB(String restaurant,String id){
        this.restaurant = restaurant;
        this.id = id;
    }
}
