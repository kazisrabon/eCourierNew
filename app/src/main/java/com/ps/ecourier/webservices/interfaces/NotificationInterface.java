package com.ps.ecourier.webservices.interfaces;

import com.ps.ecourier.pojo.Notification;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Prokash Sarkar on 6/27/2015.
 */
public interface NotificationInterface {

    @FormUrlEncoded
    @POST("/api/{operation}")
    public void getResult(
            @Path("operation") String operation,
            @FieldMap Map<String, String> params,
            Callback<Notification> response
    );
}
