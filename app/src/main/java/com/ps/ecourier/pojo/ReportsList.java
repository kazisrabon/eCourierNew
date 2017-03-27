package com.ps.ecourier.pojo;

/**
 * Created by Prokash Sarkar on 6/14/2015.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ReportsList {

    @Expose
    private String status;
    @Expose
    private String msg;
    @Expose
    private List<ReportsListDatum> data = new ArrayList<ReportsListDatum>();
    @SerializedName("total_parcel")
    @Expose
    private String totalParcel;
    @SerializedName("total_parcel_price")
    @Expose
    private Integer totalParcelPrice;

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return The msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg The msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return The data
     */
    public List<ReportsListDatum> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(List<ReportsListDatum> data) {
        this.data = data;
    }

    public String getTotalParcel() {
        return totalParcel;
    }

    public void setTotalParcel(String totalParcel) {
        this.totalParcel = totalParcel;
    }

    public Integer getTotalParcelPrice() {
        return totalParcelPrice;
    }

    public void setTotalParcelPrice(Integer totalParcelPrice) {
        this.totalParcelPrice = totalParcelPrice;
    }

}