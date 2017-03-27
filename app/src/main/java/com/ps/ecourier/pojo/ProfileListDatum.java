package com.ps.ecourier.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Prokash Sarkar on 2/11/2017.
 */
public class ProfileListDatum {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("join_date")
    @Expose
    private Object joinDate;
    @SerializedName("delivery_zone")
    @Expose
    private String deliveryZone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Object joinDate) {
        this.joinDate = joinDate;
    }

    public String getDeliveryZone() {
        return deliveryZone;
    }

    public void setDeliveryZone(String deliveryZone) {
        this.deliveryZone = deliveryZone;
    }
}
