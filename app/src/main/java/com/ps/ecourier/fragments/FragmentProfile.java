package com.ps.ecourier.fragments;

/**
 * Created by Prokash Sarkar on 6/14/2015.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ps.ecourier.R;
import com.ps.ecourier.base.BaseFragment;
import com.ps.ecourier.pojo.ProfileList;
import com.ps.ecourier.session.SessionUserData;
import com.ps.ecourier.webservices.ApiParams;
import com.ps.ecourier.webservices.interfaces.ProfileListInterface;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FragmentProfile extends BaseFragment {

    private View rootView;
    private TextView txtName, txtJoinDate, txtDeliveryZone, txtPhone;
    private SessionUserData sessionUserData;
    private Resources res;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = getResources();
        sessionUserData = new SessionUserData(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        initialize();

        loadProfile();

        return rootView;
    }

    private void initialize() {

        txtName = (TextView) rootView.findViewById(R.id.txtName);
        txtJoinDate = (TextView) rootView.findViewById(R.id.txtJoinDate);
        txtDeliveryZone = (TextView) rootView.findViewById(R.id.txtDeliveryZone);
        txtPhone = (TextView) rootView.findViewById(R.id.txtPhone);
    }

    private void loadProfile() {

        showDialog(false, "", getResources().getString(R.string.loading));

        //Retrofit section start from here...
        //create an adapter for retrofit with base url
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ApiParams.TAG_BASE_URL).build();
        //creating a service for adapter with our ApiCallback class
        ProfileListInterface myApiCallback = restAdapter.create(ProfileListInterface.class);

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
        map.put(ApiParams.PARAM_GROUP, "" + group);
        map.put(ApiParams.PARAM_AUTHENTICATION_KEY, "" + authentication_key);

        //Now, we will to call for response
        //Retrofit using gson for JSON-POJO conversion
        myApiCallback.getData(ApiParams.TAG_PROFILE_KEY, map, new Callback<ProfileList>() {
            @Override
            public void success(ProfileList profileList, Response response) {
                //we get json object from server to our POJO or model class

                hideDialog();

                boolean status = profileList.getStatus();
                if (status) {
                    txtName.setText("Name: " + profileList.getData().getName());
                    txtJoinDate.setText("Joining Date: " + (profileList.getData().getJoinDate() == null ? "" : profileList.getData().getJoinDate()));
                    txtDeliveryZone.setText("Delivery Zone: " + profileList.getData().getDeliveryZone());
                    //txtPhone.setText(profileList.getData().getPhone);
                    txtPhone.setVisibility(View.GONE);
                } else {
                    //showToast("" + "" + profileList.getStatus() + "!", 0);
                    showDialog("Oops!", "No result found!");
                }
            }

            @Override
            public void failure(RetrofitError error) {
                hideDialog();
                showToast("" + error.getMessage() + "!", 0);
            }
        });
    }

    private void showDialog(String title, String message) {

        AlertDialog.Builder alert_box = new AlertDialog.Builder(getActivity());
        alert_box.setTitle(title);
        alert_box.setMessage(message);

        alert_box.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });

        alert_box.show();
    }
}
