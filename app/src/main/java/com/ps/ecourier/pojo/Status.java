package com.ps.ecourier.pojo;

/**
 * Created by Gakk Apps on 6/12/2015.
 */

import com.google.gson.annotations.Expose;

public class Status {

    @Expose
    private String status;
    @Expose
    private String msg;

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

}