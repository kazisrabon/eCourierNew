/*
 * Copyright (c) Prokash Sarkar 2015. Contact, prokashsarkar@outlook.com.
 */

package com.ps.ecourier.pojo;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class ConsignmentList {

    @Expose
    private String status;
    @Expose
    private String msg;
    @Expose
    private List<ConsignmentListDatum> data = new ArrayList<ConsignmentListDatum>();

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
    public List<ConsignmentListDatum> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(List<ConsignmentListDatum> data) {
        this.data = data;
    }

}