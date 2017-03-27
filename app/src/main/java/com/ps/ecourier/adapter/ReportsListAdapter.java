package com.ps.ecourier.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ps.ecourier.ActivityReportsDetails;
import com.ps.ecourier.R;
import com.ps.ecourier.pojo.ReportsListDatum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gakk Apps on 6/14/2015.
 */
public class ReportsListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ReportsListDatum> reportsListDatumList;

    public ReportsListAdapter(Activity activity, List<ReportsListDatum> reportsListDatumList) {
        this.activity = activity;
        this.reportsListDatumList = reportsListDatumList;
    }

    @Override
    public int getCount() {
        return reportsListDatumList.size();
    }

    @Override
    public Object getItem(int location) {
        return reportsListDatumList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_consignment_list, null);

        TextView consignment_position = (TextView) convertView.findViewById(R.id.textPosition);
        TextView consignment_id = (TextView) convertView.findViewById(R.id.textConsignmentId);
        TextView company = (TextView) convertView.findViewById(R.id.textCompany);
        TextView amount = (TextView) convertView.findViewById(R.id.textAmount);
        TextView order_time = (TextView) convertView.findViewById(R.id.textOrderTime);
        TextView parcel_status = (TextView) convertView.findViewById(R.id.textParcelStatus);

        // getting consignment list data for the row
        ReportsListDatum m = reportsListDatumList.get(position);

        // starting the position from 1 default is 0
        consignment_position.setText("" + (position + 1) + ". ");

        // consignment_id
        consignment_id.setText(m.getConsignmentId());
        // company
        company.setText(m.getEso());
        amount.setText("BDT " + m.getProductPrice());
        order_time.setText(m.getOrderTime());
        // parcel_status
        parcel_status.setText(m.getParcelStatus());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // convert the list to array list and pass via intent
                ArrayList<ReportsListDatum> ItemArray = ((ArrayList<ReportsListDatum>) reportsListDatumList);

                Intent i = new Intent(activity, ActivityReportsDetails.class);
                i.putExtra("visible_state", "" + 0);
                i.putExtra("consignment_data_position", "" + position);
                i.putExtra("consignment_data_array", ItemArray);
                activity.startActivity(i);

            }
        });

        return convertView;
    }

}