package com.ps.ecourier.pojo;

/**
 * Created by Prokash Sarkar on 6/27/2015.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification {

    @Expose
    private String status;
    @Expose
    private String msg;
    @SerializedName("notification_count")
    @Expose
    private String notificationCount;
    @SerializedName("notification_message")
    @Expose
    private String notificationMessage;
    @SerializedName("notification_id")
    @Expose
    private String notificationId;

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
     * @return The notificationCount
     */
    public String getNotificationCount() {
        return notificationCount;
    }

    /**
     * @param notificationCount The notification_count
     */
    public void setNotificationCount(String notificationCount) {
        this.notificationCount = notificationCount;
    }

    /**
     * @return The notificationMessage
     */
    public String getNotificationMessage() {
        return notificationMessage;
    }

    /**
     * @param notificationMessage The notification_message
     */
    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    /**
     * @return The notificationId
     */
    public String getNotificationId() {
        return notificationId;
    }

    /**
     * @param notificationId The notification_id
     */
    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

}