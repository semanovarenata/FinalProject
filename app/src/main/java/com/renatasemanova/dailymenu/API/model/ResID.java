package com.renatasemanova.dailymenu.API.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResID {

    @SerializedName("res_id")
    @Expose
    private Integer resId;

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

}