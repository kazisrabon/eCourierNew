package com.ps.ecourier.webservices.interfaces;

/**
 * Created by Prokash Sarkar on 1/25/2017.
 */

import com.ps.ecourier.pojo.StatusAlternative;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Path;

public interface ReviewInterface {
    //string operation is for passing values while calling the interface
    @FormUrlEncoded
    @POST("/api/{operation}")
    /*
    operation is for specifying the api action
    FiledMap is used for passing the request parameters
    response is the response from the server which is now in the POJO
    */
    public void getResult(
            @Path("operation") String operation,
            @FieldMap Map<String, String> params,
            Callback<StatusAlternative> response
    );
}
