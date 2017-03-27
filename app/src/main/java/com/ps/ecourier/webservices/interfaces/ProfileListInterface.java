package com.ps.ecourier.webservices.interfaces;

import com.ps.ecourier.pojo.ProfileList;
import com.ps.ecourier.pojo.ProfileListDatum;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Gakk Apps on 6/14/2015.
 */
public interface ProfileListInterface {
    @FormUrlEncoded
    @POST("/api/{operation}")
    public void getData(
            @Path("operation") String operation,
            @FieldMap Map<String, String> params,
            Callback<ProfileList> response
    );
}
