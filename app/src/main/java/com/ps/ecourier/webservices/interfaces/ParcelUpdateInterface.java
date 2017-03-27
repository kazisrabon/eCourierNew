package com.ps.ecourier.webservices.interfaces;

import com.ps.ecourier.pojo.Status;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Gakk Apps on 6/12/2015.
 */
public interface ParcelUpdateInterface {
    @FormUrlEncoded
    @POST("/api/{operation}")
    public void getResult(
            @Path("operation") String operation,
            @FieldMap Map<String, String> params,
            Callback<Status> response
    );
}
