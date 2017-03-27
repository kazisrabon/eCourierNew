/*
 * Copyright (c) Prokash Sarkar 2015. Contact, prokashsarkar@outlook.com.
 */

package com.ps.ecourier.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ps.ecourier.R;

/**
 * Created by Gakk Apps on 6/12/2015.
 */
public class ConsignmentSearchSpinnerAdapter extends ArrayAdapter<String> {

    private Context context;
    private LayoutInflater inflater;
    private String[] searchTypeArray;

    public ConsignmentSearchSpinnerAdapter(Context context, int textViewResourceId, String[] searchTypeArray) {
        super(context, textViewResourceId, searchTypeArray);
        this.context = context;
        this.searchTypeArray = searchTypeArray;
    }

    public int getCount() {
        return searchTypeArray.length;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.spinner_item, null);

        TextView search_key = (TextView) convertView.findViewById(R.id.textSearchValue);
        search_key.setText(searchTypeArray[position]);

        return convertView;
    }
}