package com.ps.ecourier.pojo;

/**
 * Created by Prokash Sarkar on 6/14/2015.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReportsListDatum implements Serializable {

    @SerializedName("consignment_id")
    @Expose
    private String consignmentId;
    @Expose
    private String eso;
    @SerializedName("recipient_name")
    @Expose
    private String recipientName;
    @SerializedName("recipient_mobile")
    @Expose
    private String recipientMobile;
    @Expose
    private String address;
    @Expose
    private String area;
    @SerializedName("order_time")
    @Expose
    private String orderTime;
    @Expose
    private String agent;
    @SerializedName("parcel_status")
    @Expose
    private String parcelStatus;
    @SerializedName("shipping_price")
    @Expose
    private String shippingPrice;
    @SerializedName("product_price")
    @Expose
    private String productPrice;
    @SerializedName("cod_price")
    @Expose
    private Integer codPrice;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("parcel_id")
    @Expose
    private String parcelId;
    @Expose
    private String comment;
    @SerializedName("payment_clear")
    @Expose
    private String paymentClear;

    /**
     * @return The consignmentId
     */
    public String getConsignmentId() {
        return consignmentId;
    }

    /**
     * @param consignmentId The consignment_id
     */
    public void setConsignmentId(String consignmentId) {
        this.consignmentId = consignmentId;
    }

    /**
     * @return The eso
     */
    public String getEso() {
        return eso;
    }

    /**
     * @param eso The eso
     */
    public void setEso(String eso) {
        this.eso = eso;
    }

    /**
     * @return The recipientName
     */
    public String getRecipientName() {
        return recipientName;
    }

    /**
     * @param recipientName The recipient_name
     */
    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    /**
     * @return The recipientMobile
     */
    public String getRecipientMobile() {
        return recipientMobile;
    }

    /**
     * @param recipientMobile The recipient_mobile
     */
    public void setRecipientMobile(String recipientMobile) {
        this.recipientMobile = recipientMobile;
    }

    /**
     * @return The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return The area
     */
    public String getArea() {
        return area;
    }

    /**
     * @param area The area
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * @return The orderTime
     */
    public String getOrderTime() {
        return orderTime;
    }

    /**
     * @param orderTime The order_time
     */
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    /**
     * @return The agent
     */
    public String getAgent() {
        return agent;
    }

    /**
     * @param agent The agent
     */
    public void setAgent(String agent) {
        this.agent = agent;
    }

    /**
     * @return The parcelStatus
     */
    public String getParcelStatus() {
        return parcelStatus;
    }

    /**
     * @param parcelStatus The parcel_status
     */
    public void setParcelStatus(String parcelStatus) {
        this.parcelStatus = parcelStatus;
    }

    /**
     * @return The shippingPrice
     */
    public String getShippingPrice() {
        return shippingPrice;
    }

    /**
     * @param shippingPrice The shipping_price
     */
    public void setShippingPrice(String shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    /**
     * @return The productPrice
     */
    public String getProductPrice() {
        return productPrice;
    }

    /**
     * @param productPrice The product_price
     */
    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * @return The codPrice
     */
    public Integer getCodPrice() {
        return codPrice;
    }

    /**
     * @param codPrice The cod_price
     */
    public void setCodPrice(Integer codPrice) {
        this.codPrice = codPrice;
    }

    /**
     * @return The paymentMethod
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * @param paymentMethod The payment_method
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * @return The parcelId
     */
    public String getParcelId() {
        return parcelId;
    }

    /**
     * @param parcelId The parcel_id
     */
    public void setParcelId(String parcelId) {
        this.parcelId = parcelId;
    }

    /**
     * @return The comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment The comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return The paymentClear
     */
    public String getPaymentClear() {
        return paymentClear;
    }

    /**
     * @param paymentClear The payment_clear
     */
    public void setPaymentClear(String paymentClear) {
        this.paymentClear = paymentClear;
    }

}