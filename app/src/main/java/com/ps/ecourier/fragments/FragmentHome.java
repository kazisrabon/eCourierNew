/*
 * Copyright (c) Prokash Sarkar 2015. Contact, prokashsarkar@outlook.com.
 */

package com.ps.ecourier.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ps.ecourier.ActivityConsignmentDetails;
import com.ps.ecourier.R;
import com.ps.ecourier.base.BaseFragment;
import com.ps.ecourier.pojo.ConsignmentList;
import com.ps.ecourier.pojo.ConsignmentListDatum;
import com.ps.ecourier.session.SessionUserData;
import com.ps.ecourier.utils.FormValidator;
import com.ps.ecourier.views.FragmentDialogQRScanner;
import com.ps.ecourier.webservices.ApiParams;
import com.ps.ecourier.webservices.interfaces.ConsignmentListInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Proaksh Sarkar on 6/8/2015.
 */
public class FragmentHome extends BaseFragment implements View.OnClickListener {

    private View rootView;

    private int CAMERA_PERMISSIONS_REQUEST = 8887;

    private EditText mSearchValue;
    private Button mSearch;
    private Button mQRScan;
    private TextView mTotalPicked, mTotalDelivered, mTotalReturned, mTotalProcessing, mTotalPrice;

    private SessionUserData sessionUserData;

    private List<ConsignmentListDatum> consignmentListDatumList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionUserData = new SessionUserData(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        initialize();

        return rootView;
    }

    private void initialize() {
        mTotalPicked = (TextView) rootView.findViewById(R.id.textTotalPicked);
        mTotalDelivered = (TextView) rootView.findViewById(R.id.textTotalDelivered);
        mTotalReturned = (TextView) rootView.findViewById(R.id.textTotalReturned);
        mTotalProcessing = (TextView) rootView.findViewById(R.id.textTotalProcessing);
        mTotalPrice = (TextView) rootView.findViewById(R.id.textTotalPrice);
        mSearchValue = (EditText) rootView.findViewById(R.id.editSearchValue);
        mSearch = (Button) rootView.findViewById(R.id.btnSearch);
        mSearch.setOnClickListener(this);
        mQRScan = (Button) rootView.findViewById(R.id.btnScan);
        mQRScan.setOnClickListener(this);

        // get user data from session
        HashMap<String, String> user = sessionUserData.getSessionDetails();
        String name = user.get(SessionUserData.KEY_USER_NAME);
        String total_picked = user.get(SessionUserData.KEY_USER_TOTAL_PICKED);
        String total_delivered = user.get(SessionUserData.KEY_USER_TOTAL_DELIVERED);
        String total_returned = user.get(SessionUserData.KEY_USER_TOTAL_RETURNED);
        String total_processing = user.get(SessionUserData.KEY_USER_TOTAL_PROCESSING);
        String total_price = user.get(SessionUserData.KEY_USER_TOTAL_DELIVERED_PRODUCT_PRICE);

        mTotalPicked.setText("Total Picked: " + total_picked);
        mTotalDelivered.setText("Total Delivered: " + total_delivered);
        mTotalReturned.setText("Total Returned: " + total_returned);
        mTotalProcessing.setText("Total Processing: " + total_processing);
        mTotalPrice.setText("Total Delivered Product Price: " + total_price);
    }

    private void getRequiredPermission() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "You must provide access to use this app!", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSIONS_REQUEST);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 8887: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }

                } else {
                    // permission denied, boo! Disable the functionality that depends on this permission.
                    Toast.makeText(getActivity(), "You must provide access to use this app!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    public void onClick(View v) {

        FormValidator fv = new FormValidator();
        String search_value = mSearchValue.getText().toString();

        switch (v.getId()) {
            case R.id.btnSearch:
                if (!fv.isValidField(search_value)) {
                    mSearchValue.setError(getResources().getString(R.string.empty_field));
                } else {
                    searchConsignment(search_value);
                }
                break;
            case R.id.btnScan:

                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    FragmentManager fm = getActivity().getFragmentManager();
                    @SuppressLint("ValidFragment")
                    FragmentDialogQRScanner fragmentDialogQRScanner = new FragmentDialogQRScanner() {

                        @Override
                        public void success(String value, String format_type) {
                            searchConsignment(value);
                        }

                        @Override
                        public void error() {

                        }
                    };
                    fragmentDialogQRScanner.setArgs("", "", "");
                    fragmentDialogQRScanner.show(fm, "fragment_qr_scanner");
                } else {
                    getRequiredPermission();
                }
                break;
        }
    }

    private void searchConsignment(String search_value) {

        showDialog(false, "", getResources().getString(R.string.loading));

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ApiParams.TAG_BASE_URL).build();
        ConsignmentListInterface myApiCallback = restAdapter.create(ConsignmentListInterface.class);

        // get user data from session
        HashMap<String, String> user = sessionUserData.getSessionDetails();
        String user_type = user.get(SessionUserData.KEY_USER_TYPE);
        String id = user.get(SessionUserData.KEY_USER_ID);
        String group = user.get(SessionUserData.KEY_USER_GROUP);
        String authentication_key = user.get(SessionUserData.KEY_USER_AUTH_KEY);

        //Lets pass the desired parameters
        HashMap<String, String> map = new HashMap<String, String>();

        if (user_type.equals("1")) {
            map.put(ApiParams.PARAM_ADMIN_ID, "" + id);
        } else if (user_type.equals("2")) {
            map.put(ApiParams.PARAM_AGENT_ID, "" + id);
        }
        map.put(ApiParams.PARAM_GROUP, group);
        map.put(ApiParams.PARAM_AUTHENTICATION_KEY, "" + authentication_key);
        map.put(ApiParams.TAG_CONSIGNMENT_NO, "" + search_value);

        myApiCallback.getData(ApiParams.TAG_CONSIGNMENT_SEARCH_KEY, map, new Callback<ConsignmentList>() {
            @Override
            public void success(ConsignmentList consignment_list, Response response) {
                hideDialog();

                clearData();

                String status = consignment_list.getStatus();
                if (status.equals(ApiParams.TAG_SUCCESS)) {

                    consignmentListDatumList = consignment_list.getData();

                    // convert the list to array list and pass via intent
                    ArrayList<ConsignmentListDatum> ItemArray = ((ArrayList<ConsignmentListDatum>) consignmentListDatumList);

                    Intent i = new Intent(getActivity(), ActivityConsignmentDetails.class);
                    i.putExtra("is_search", true);
                    i.putExtra("visible_state", "0");
                    i.putExtra("consignment_data_position", "0");
                    i.putExtra("consignment_data_array", ItemArray);
                    getActivity().startActivity(i);
                }
                showToast("" + consignment_list.getStatus() + "!", 0);
            }

            @Override
            public void failure(RetrofitError error) {
                hideDialog();
                Log.e("FAILURE", error.toString());
                showToast("" + error.getMessage() + "!", 0);
            }
        });
    }

    private void clearData() {
        mSearchValue.setText("");
    }
}
