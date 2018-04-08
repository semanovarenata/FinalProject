package com.renatasemanova.dailymenu.API.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Example {

    @SerializedName("daily_menu")
    @Expose
    private List<DailyMenu> dailyMenu = null;

    public List<DailyMenu> getDailyMenu() {
        return dailyMenu;
    }

    public void setDailyMenu(List<DailyMenu> dailyMenu) {
        this.dailyMenu = dailyMenu;
    }

}