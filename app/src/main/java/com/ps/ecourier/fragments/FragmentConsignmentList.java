/*
 * Copyright (c) Prokash Sarkar 2015. Contact, prokashsarkar@outlook.com.
 */

package com.ps.ecourier.fragments;

/**
 * Created by Prokash Sarkar on 6/9/2015.
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.ps.ecourier.R;
import com.ps.ecourier.adapter.ConsignmentListAdapter;
import com.ps.ecourier.adapter.ConsignmentSearchSpinnerAdapter;
import com.ps.ecourier.base.BaseFragment;
import com.ps.ecourier.pojo.ConsignmentList;
import com.ps.ecourier.pojo.ConsignmentListDatum;
import com.ps.ecourier.session.SessionUserData;
import com.ps.ecourier.webservices.ApiParams;
import com.ps.ecourier.webservices.interfaces.ConsignmentListInterface;
import com.squareup.okhttp.OkHttpClient;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class FragmentConsignmentList extends BaseFragment {

    private View rootView;

    private SessionUserData sessionUserData;

    private Spinner statusSpinner;

    private ListView consignmentListView;
    private List<ConsignmentListDatum> consignmentListDatumList;

    private ConsignmentListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionUserData = new SessionUserData(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_list, container, false);

        statusSpinner = (Spinner) rootView.findViewById(R.id.spinnerStatusKey);
        statusSpinner.setAdapter(new ConsignmentSearchSpinnerAdapter(getActivity(), R.layout.spinner_item, getResources().getStringArray(R.array.consignmentTypeValuesArray)));
        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadList(statusSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                loadList(statusSpinner.getSelectedItem().toString());
            }
        });

        consignmentListView = (ListView) rootView.findViewById(R.id.list);

        return rootView;
    }

    private void loadList(String type) {

        showDialog();

        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(120, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(120, TimeUnit.SECONDS);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setClient(new OkClient(okHttpClient))
                .setEndpoint(ApiParams.TAG_BASE_URL).build();
        ConsignmentListInterface myApiCallback = restAdapter.create(ConsignmentListInterface.class);

        // get user data from session
        HashMap<String, String> user = sessionUserData.getSessionDetails();
        String user_type = user.get(SessionUserData.KEY_USER_TYPE);
        String id = user.get(SessionUserData.KEY_USER_ID);
        String group = user.get(SessionUserData.KEY_USER_GROUP);
        String authentication_key = user.get(SessionUserData.KEY_USER_AUTH_KEY);

        //Lets pass the desired parameters
        HashMap<String, String> map = new HashMap<String, String>();

        if (user_type.contains("1")) {
            map.put(ApiParams.PARAM_ADMIN_ID, "" + id);
        } else if (user_type.contains("2")) {
            map.put(ApiParams.PARAM_AGENT_ID, "" + id);
        }
        map.put(ApiParams.PARAM_GROUP, group);
        map.put(ApiParams.PARAM_AUTHENTICATION_KEY, "" + authentication_key);
        map.put(ApiParams.PARAM_TYPE, type);

        myApiCallback.getData(ApiParams.TAG_CONSIGNMENT_LIST_KEY, map, new Callback<ConsignmentList>() {
            @Override
            public void success(ConsignmentList consignment_list, Response response) {
                //we get json object from server to our POJO or model class

                hideDialog();

                String status = consignment_list.getStatus();
                if (status.contains(ApiParams.TAG_SUCCESS)) {
                    consignmentListDatumList = consignment_list.getData();
                    adapter = new ConsignmentListAdapter(getActivity(), consignmentListDatumList);
                    consignmentListView.setAdapter(adapter);
                }
                showToast("" + "" + consignment_list.getStatus() + "!", 0);
            }

            @Override
            public void failure(RetrofitError error) {
                hideDialog();
                showToast("" + error.getMessage() + "!", 0);
            }
        });
    }

    public void showToast(String message, int duration) {
        super.showToast(message, duration);
    }

    public void showDialog() {
        super.showDialog(false, "", getResources().getString(R.string.loading));
    }

    public void hideDialog() {
        super.hideDialog();
    }
}
