/*
 * Copyright (c) Prokash Sarkar 2015. Contact, prokashsarkar@outlook.com.
 */

package com.ps.ecourier.interfaces;

import com.ps.ecourier.pojo.ConsignmentListDatum;

import java.util.List;

/**
 * Created by Gakk Apps on 6/10/2015.
 */
public interface ConsignmentDetails {

    public void setConsignmentDetails(List<ConsignmentListDatum> consignmentListDatums, int position);
}
