/*
 * Copyright (c) Prokash Sarkar 2015. Contact, prokashsarkar@outlook.com.
 */

package com.ps.ecourier.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ps.ecourier.ActivityConsignmentDetails;
import com.ps.ecourier.R;
import com.ps.ecourier.pojo.ConsignmentListDatum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prokash Sarkar on 6/9/2015.
 */
public class ConsignmentListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ConsignmentListDatum> consignmentListDatumList;

    public ConsignmentListAdapter(Activity activity, List<ConsignmentListDatum> consignmentListDatumList) {
        this.activity = activity;
        this.consignmentListDatumList = consignmentListDatumList;
    }

    @Override
    public int getCount() {
        return consignmentListDatumList.size();
    }

    @Override
    public Object getItem(int location) {
        return consignmentListDatumList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_consignment_list, null);

        TextView consignment_position = (TextView) convertView.findViewById(R.id.textPosition);
        TextView consignment_id = (TextView) convertView.findViewById(R.id.textConsignmentId);
        consignment_id.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
        TextView company = (TextView) convertView.findViewById(R.id.textCompany);
        TextView name = (TextView) convertView.findViewById(R.id.textName);
        TextView amount = (TextView) convertView.findViewById(R.id.textAmount);
        TextView mobile_no = (TextView) convertView.findViewById(R.id.textMobileNo);
        TextView order_time = (TextView) convertView.findViewById(R.id.textOrderTime);
        TextView parcel_status = (TextView) convertView.findViewById(R.id.textParcelStatus);

        // getting consignment list data for the row
        ConsignmentListDatum m = consignmentListDatumList.get(position);

        // starting the position from 1 default is 0
        consignment_position.setText("" + (position + 1) + ". ");

        // consignment_id
        //consignment_id.setText(m.getConsignmentId());
        // company
        //company.setText(m.getCompany());

        name.setText(m.getRecipientName());
        amount.setText("BDT " + m.getProductPrice());
        mobile_no.setText(m.getRecipientMobile());

        // order_time
        order_time.setText(m.getOrderTime());
        // parcel_status
        parcel_status.setText(m.getParcelStatus());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // convert the list to array list and pass via intent
                ArrayList<ConsignmentListDatum> ItemArray = ((ArrayList<ConsignmentListDatum>) consignmentListDatumList);

                Intent i = new Intent(activity, ActivityConsignmentDetails.class);
                i.putExtra("visible_state", "" + 1);
                i.putExtra("consignment_data_position", "" + position);
                i.putExtra("consignment_data_array", ItemArray);
                activity.startActivity(i);
            }
        });

        return convertView;
    }

}