/*
 * Copyright (c) Prokash Sarkar 2015. Contact, prokashsarkar@outlook.com.
 */

package com.ps.ecourier.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ConsignmentListDatum implements Serializable {

    @SerializedName("consignment_id")
    @Expose
    private String consignmentId;
    @SerializedName("sender_group")
    @Expose
    private String senderGroup;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("recipient_name")
    @Expose
    private String recipientName;
    @SerializedName("recipient_mobile")
    @Expose
    private String recipientMobile;
    @SerializedName("recipient_address")
    @Expose
    private String recipientAddress;
    @SerializedName("consignment_no")
    @Expose
    private String consignmentNo;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("eso")
    @Expose
    private String eso;
    @SerializedName("eso_mobile")
    @Expose
    private String esoMobile;
    @SerializedName("order_time")
    @Expose
    private String orderTime;
    @SerializedName("product_price")
    @Expose
    private String productPrice;
    @SerializedName("shipping_price")
    @Expose
    private String shippingPrice;
    @SerializedName("recipient_postal_code")
    @Expose
    private String recipientPostalCode;
    @SerializedName("parcel_status")
    @Expose
    private String parcelStatus;

    public String getConsignmentId() {
        return consignmentId;
    }

    public void setConsignmentId(String consignmentId) {
        this.consignmentId = consignmentId;
    }

    public String getSenderGroup() {
        return senderGroup;
    }

    public void setSenderGroup(String senderGroup) {
        this.senderGroup = senderGroup;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientMobile() {
        return recipientMobile;
    }

    public void setRecipientMobile(String recipientMobile) {
        this.recipientMobile = recipientMobile;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public String getConsignmentNo() {
        return consignmentNo;
    }

    public void setConsignmentNo(String consignmentNo) {
        this.consignmentNo = consignmentNo;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getEso() {
        return eso;
    }

    public void setEso(String eso) {
        this.eso = eso;
    }

    public String getEsoMobile() {
        return esoMobile;
    }

    public void setEsoMobile(String esoMobile) {
        this.esoMobile = esoMobile;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(String shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public String getRecipientPostalCode() {
        return recipientPostalCode;
    }

    public void setRecipientPostalCode(String recipientPostalCode) {
        this.recipientPostalCode = recipientPostalCode;
    }

    public String getParcelStatus() {
        return parcelStatus;
    }

    public void setParcelStatus(String parcelStatus) {
        this.parcelStatus = parcelStatus;
    }

}