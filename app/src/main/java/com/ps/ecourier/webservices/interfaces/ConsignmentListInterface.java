/*
 * Copyright (c) Prokash Sarkar 2015. Contact, prokashsarkar@outlook.com.
 */

package com.ps.ecourier.webservices.interfaces;

import com.ps.ecourier.pojo.ConsignmentList;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Prokash Sarkar on 6/9/2015.
 */
public interface ConsignmentListInterface {
    @FormUrlEncoded
    @POST("/api/{operation}")
    public void getData(
            @Path("operation") String operation,
            @FieldMap Map<String, String> params,
            Callback<ConsignmentList> response
    );
}
