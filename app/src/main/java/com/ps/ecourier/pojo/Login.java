package com.ps.ecourier.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("agent_id")
    @Expose
    private String agentId;
    @SerializedName("group")
    @Expose
    private String group;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("contact_no")
    @Expose
    private String contactNo;
    @SerializedName("profile_pic")
    @Expose
    private String profilePic;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("authentication_key")
    @Expose
    private String authenticationKey;
    @SerializedName("total_picked_agent")
    @Expose
    private String totalPickedAgent;
    @SerializedName("total_delivered_agent")
    @Expose
    private String totalDeliveredAgent;
    @SerializedName("total_returned_agent")
    @Expose
    private String totalReturnedAgent;
    @SerializedName("total_processing")
    @Expose
    private String totalProcessing;
    @SerializedName("total_delivered_product_price")
    @Expose
    private String totalDeliveredProductPrice;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAuthenticationKey() {
        return authenticationKey;
    }

    public void setAuthenticationKey(String authenticationKey) {
        this.authenticationKey = authenticationKey;
    }

    public String getTotalPickedAgent() {
        return totalPickedAgent;
    }

    public void setTotalPickedAgent(String totalPickedAgent) {
        this.totalPickedAgent = totalPickedAgent;
    }

    public String getTotalDeliveredAgent() {
        return totalDeliveredAgent;
    }

    public void setTotalDeliveredAgent(String totalDeliveredAgent) {
        this.totalDeliveredAgent = totalDeliveredAgent;
    }

    public String getTotalReturnedAgent() {
        return totalReturnedAgent;
    }

    public void setTotalReturnedAgent(String totalReturnedAgent) {
        this.totalReturnedAgent = totalReturnedAgent;
    }

    public String getTotalProcessing() {
        return totalProcessing;
    }

    public void setTotalProcessing(String totalProcessing) {
        this.totalProcessing = totalProcessing;
    }

    public String getTotalDeliveredProductPrice() {
        return totalDeliveredProductPrice;
    }

    public void setTotalDeliveredProductPrice(String totalDeliveredProductPrice) {
        this.totalDeliveredProductPrice = totalDeliveredProductPrice;
    }

}