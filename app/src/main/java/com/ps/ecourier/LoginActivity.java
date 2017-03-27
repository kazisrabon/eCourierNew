/*
 * Copyright (c) Prokash Sarkar 2015. Contact, prokashsarkar@outlook.com.
 */

package com.ps.ecourier;

/**
 * Created by Prokash Sarkar on 6/2/2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ps.ecourier.base.BaseActivity;
import com.ps.ecourier.pojo.Login;
import com.ps.ecourier.session.SessionUserData;
import com.ps.ecourier.utils.FormValidator;
import com.ps.ecourier.webservices.ApiParams;
import com.ps.ecourier.webservices.interfaces.LoginInterface;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends BaseActivity {

    private String name, password;

    private Button mSubmit;
    private EditText mName, mPassword;

    private SessionUserData sessionUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionUserData = new SessionUserData(this);

        mName = (EditText) findViewById(R.id.editUserName);
        mPassword = (EditText) findViewById(R.id.editPassword);

        mSubmit = (Button) findViewById(R.id.btnSubmit);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = mName.getText().toString();
                password = mPassword.getText().toString();

                if (!FormValidator.isValidField(name)) {
                    mName.setError(getResources().getString(R.string.empty_field));
                } else if (!FormValidator.isValidField(password)) {
                    mPassword.setError(getResources().getString(R.string.empty_field));
                } else {
                    verifyUser();
                }
            }
        });
    }

    private void verifyUser() {

        showProgressDialog(false, "", getResources().getString(R.string.loading));

        //Retrofit section start from here...
        //create an adapter for retrofit with base url
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ApiParams.TAG_BASE_URL).build();
        //creating a service for adapter with our ApiCallback class
        LoginInterface myLoginInterface = restAdapter.create(LoginInterface.class);

        //Lets pass the desired parameters
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(ApiParams.PARAM_USER_NAME, name);
        map.put(ApiParams.PARAM_PASSWORD, password);
        map.put(ApiParams.PARAM_USER_TYPE, ApiParams.USER_TYPE_USER);

        //Now, we will to call for response
        //Retrofit using gson for JSON-POJO conversion
        myLoginInterface.getResult(ApiParams.TAG_LOGIN_KEY, map, new Callback<Login>() {
            @Override
            public void success(Login loginModel, Response response) {
                //we get json object from server to our POJO or model class

                hideProgressDialog();

                String status = loginModel.getStatus();
                if (status.equals(ApiParams.TAG_SUCCESS)) {

                    sessionUserData.createUserInfo(ApiParams.USER_TYPE_USER, loginModel.getAgentId(),
                            loginModel.getName(), password, loginModel.getGroup(), loginModel.getAuthenticationKey(),
                            loginModel.getTotalPickedAgent(), loginModel.getTotalDeliveredAgent(),
                            loginModel.getTotalReturnedAgent(), loginModel.getTotalProcessing(), loginModel.getTotalDeliveredProductPrice());

                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }

                showToast("" + loginModel.getStatus() + "!", 0);
            }

            @Override
            public void failure(RetrofitError error) {
                hideProgressDialog();
                showToast("" + error.getMessage() + "!", 0);
            }
        });
    }
}
