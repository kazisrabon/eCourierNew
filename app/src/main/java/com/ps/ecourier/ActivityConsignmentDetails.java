/*
 * Copyright (c) Prokash Sarkar 2015. Contact, prokashsarkar@outlook.com.
 */

package com.ps.ecourier;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.ps.ecourier.adapter.ConsignmentSearchSpinnerAdapter;
import com.ps.ecourier.base.BaseActivity;
import com.ps.ecourier.interfaces.ConsignmentDetails;
import com.ps.ecourier.pojo.ConsignmentListDatum;
import com.ps.ecourier.pojo.Status;
import com.ps.ecourier.pojo.StatusAlternative;
import com.ps.ecourier.session.SessionUserData;
import com.ps.ecourier.utils.FormValidator;
import com.ps.ecourier.views.CustomToast;
import com.ps.ecourier.webservices.ApiParams;
import com.ps.ecourier.webservices.interfaces.ParcelUpdateInterface;
import com.ps.ecourier.webservices.interfaces.ReviewInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ActivityConsignmentDetails extends BaseActivity implements ConsignmentDetails {

    private Resources res;
    private Button mEditProductInfo, mEditRecipientInfo, mSaveProductInfo, mSaveRecipientInfo, mUpdate;

    private LinearLayout mLayoutConsignmentInformation, mLayoutParcelInformation;
    private Spinner statusSpinner, commentSpinner;

    private CustomToast customToast;
    private SessionUserData sessionUserData;

    private TextView mConsignmentId, mSenderGroup, mCompany, mCompanyPhone, mCallCompany, mProductId, mOrderTime, mParcelStatus, mCallRecipient;
    private EditText editProductPrice, editComment, editRecipientName, editRecipientMobile, editRecipientAddress;

    private List<String> parcelStatusValuesArray;

    private ArrayList<ConsignmentListDatum> myList;

    private int position;
    private int spinner_position = 0;
    private String consignment_no = "";
    private String statusParcel = "";
    private String commentParcel = "";
    private String current_parcel_status = "";

    private Bitmap signature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consignment_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        customToast = new CustomToast();
        sessionUserData = new SessionUserData(this);

        res = getResources();

        initializeViews();

        parcelStatusValuesArray = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.parcelStatusValuesArray1)));

        // receives data from intent and and serialize it back to a list
        if (getIntent().getExtras() != null) {
            position = Integer.parseInt(getIntent().getStringExtra("consignment_data_position"));
            myList = (ArrayList<ConsignmentListDatum>) getIntent().getSerializableExtra("consignment_data_array");
            setConsignmentDetails(myList, position);
        }

        if (Integer.parseInt(getIntent().getStringExtra("visible_state")) == 0) {
            mLayoutParcelInformation.setVisibility(View.GONE);
        }

        // agent can change status to 1. Picked Up 2. Delivered 3. Partial Delivered 4. HOLD at ECOURIER HUB only
        // check the status sequence and verify if the next status is changeable
        if (current_parcel_status.equals("Initiated")
                || current_parcel_status.equals("On the way to Delivery")
                || current_parcel_status.equals("Delivered")
                || current_parcel_status.equals("Partial Delivered")
                || current_parcel_status.equals("HOLD at ECOURIER HUB")) {

            if (current_parcel_status.equals("Initiated")) {
                parcelStatusValuesArray = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.parcelStatusValuesArray1)));
            } else if (current_parcel_status.equals("On the way to Delivery")) {
                parcelStatusValuesArray = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.parcelStatusValuesArray2)));
            } else if (current_parcel_status.equals("Delivered")) {
                parcelStatusValuesArray = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.parcelStatusValuesArray3)));
            } else if (current_parcel_status.equals("Partial Delivered")) {
                parcelStatusValuesArray = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.parcelStatusValuesArray4)));
            } else if (current_parcel_status.equals("HOLD at ECOURIER HUB")) {
                parcelStatusValuesArray = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.parcelStatusValuesArray5)));
            }

            statusSpinner.setEnabled(true);

            // allow changing comment for this status only
            if (current_parcel_status.equals("HOLD at ECOURIER HUB")) {
                commentSpinner.setEnabled(true);
            } else {
                commentSpinner.setEnabled(false);
            }
            mUpdate.setClickable(true);

        } else {
            statusSpinner.setEnabled(false);
            commentSpinner.setEnabled(false);
            mUpdate.setClickable(false);
            customToast.showDeafultToast(this, "You aren't allowed to change the status right now!", 0);
        }

        // convert list back to array
        String parcelStatusValues[] = new String[parcelStatusValuesArray.size()];
        parcelStatusValues = parcelStatusValuesArray.toArray(parcelStatusValues);

        statusSpinner.setAdapter(new ConsignmentSearchSpinnerAdapter(this, R.layout.spinner_item, parcelStatusValues));
        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                spinner_position = position;
                Log.e("Spinner position", spinner_position+"");

                if (position != 0) {
                    statusParcel = "" + getResources().getStringArray(R.array.parcelStatusKeysArray)[position];
                } else {
                    statusParcel = "" + getResources().getStringArray(R.array.parcelStatusKeysArray)[0];
                }
                Log.e("Status Parcel", statusParcel);

                if (position == 3) {
                    commentSpinner.setEnabled(true);
                } else {
                    commentSpinner.setEnabled(false);
                    editComment.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        commentSpinner.setAdapter(new ConsignmentSearchSpinnerAdapter(this, R.layout.spinner_item, getResources().getStringArray(R.array.parcelCommentKeysArray)));
        commentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                commentParcel = "" + getResources().getStringArray(R.array.parcelCommentKeysArray)[position];

                if (commentParcel.equals("Other")) {
                    editComment.setVisibility(View.VISIBLE);
                } else {
                    editComment.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                commentParcel = "" + getResources().getStringArray(R.array.parcelCommentKeysArray)[0];

                if (commentParcel.equals("Other")) {
                    editComment.setVisibility(View.VISIBLE);
                } else {
                    editComment.setVisibility(View.GONE);
                }
            }
        });

        mEditProductInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mEditProductInfo.getTag().equals("true")) {
                    editProductPrice.setFocusableInTouchMode(true);

                    mEditProductInfo.setText("Cancel");
                    mEditProductInfo.setTag("false");
                    mSaveProductInfo.setVisibility(View.VISIBLE);
                } else {
                    editProductPrice.setFocusable(false);

                    mEditProductInfo.setText("Edit");
                    mEditProductInfo.setTag("true");
                    mSaveProductInfo.setVisibility(View.GONE);
                }
            }
        });

        mSaveProductInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FormValidator.isValidField(editProductPrice.getText().toString())) {
                    updateContent();
                } else {
                    editProductPrice.setError("Please choose a price!");
                }
            }
        });

        mCallCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ConsignmentListDatum data = myList.get(position);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel: " + data.getEsoMobile()));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mCallRecipient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ConsignmentListDatum data = myList.get(position);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel: " + data.getRecipientMobile()));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mEditRecipientInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEditRecipientInfo.getTag().equals("true")) {
                    editRecipientName.setFocusableInTouchMode(true);
                    editRecipientMobile.setFocusableInTouchMode(true);
                    editRecipientAddress.setFocusableInTouchMode(true);

                    mEditRecipientInfo.setText("Cancel");
                    mEditRecipientInfo.setTag("false");
                    mSaveRecipientInfo.setVisibility(View.VISIBLE);
                } else {
                    editRecipientName.setFocusable(false);
                    editRecipientMobile.setFocusable(false);
                    editRecipientAddress.setFocusable(false);

                    mEditRecipientInfo.setTag("true");
                    mEditRecipientInfo.setText("Edit");
                    mSaveRecipientInfo.setVisibility(View.GONE);
                }
            }
        });

        mSaveRecipientInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!FormValidator.isValidField(editRecipientName.getText().toString())) {
                    editRecipientName.setError("Empty field!");
                } else if (!FormValidator.isValidField(editRecipientMobile.getText().toString())) {
                    editRecipientMobile.setError("Empty field!");
                } else if (!FormValidator.isValidField(editRecipientAddress.getText().toString())) {
                    editRecipientAddress.setError("Empty field!");
                } else {
                    updateContent();
                }
            }
        });

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // agent can change status to 1. Picked Up 2. Delivered 3. Partial Delivered 4. HOLD at ECOURIER HUB only
                // check the status sequence and verify if the next status is changeable
                if (current_parcel_status.equals("Initiated")
                        || current_parcel_status.equals("On the way to Delivery")
                        || current_parcel_status.equals("Delivered")
                        || current_parcel_status.equals("Partial Delivered")
                        || current_parcel_status.equals("HOLD at ECOURIER HUB")) {

                    if (commentSpinner.getSelectedItem().toString().equals("Other")) {
                        commentParcel = editComment.getText().toString();
                    } else {
                        commentParcel = "";
                    }

                    updateParcel();
                } else {
                    statusSpinner.setEnabled(false);
                    commentSpinner.setClickable(false);
                    mUpdate.setClickable(false);
                    customToast.showDeafultToast(ActivityConsignmentDetails.this, "You aren't allowed to change the status right now!", 0);
                }
            }
        });
    }

    private void initializeViews() {
        editComment = (EditText) findViewById(R.id.editComment);
        mEditProductInfo = (Button) findViewById(R.id.btnEditProductInfo);
        mEditRecipientInfo = (Button) findViewById(R.id.btnEditRecipientInfo);
        mSaveProductInfo = (Button) findViewById(R.id.btnSaveProductInfo);
        mSaveRecipientInfo = (Button) findViewById(R.id.btnSaveRecipientInfo);
        mUpdate = (Button) findViewById(R.id.btnUpdate);
        mLayoutConsignmentInformation = (LinearLayout) findViewById(R.id.layoutConsignmentInformation);
        mLayoutParcelInformation = (LinearLayout) findViewById(R.id.layoutParcelInformation);
        mConsignmentId = (TextView) findViewById(R.id.textConsignmentId);
        mSenderGroup = (TextView) findViewById(R.id.textSenderGroup);
        mCompany = (TextView) findViewById(R.id.textCompany);
        mCompanyPhone = (TextView) findViewById(R.id.textCompanyMobile);
        mCallCompany = (TextView) findViewById(R.id.txtCompanyMobile);
        mProductId = (TextView) findViewById(R.id.textProductId);
        mCallRecipient = (TextView) findViewById(R.id.txtRecipientMobile);
        editProductPrice = (EditText) findViewById(R.id.editProductPrice);
        editRecipientName = (EditText) findViewById(R.id.editRecipientName);
        editRecipientMobile = (EditText) findViewById(R.id.editRecipientMobile);
        editRecipientAddress = (EditText) findViewById(R.id.editRecipientAddress);
        mOrderTime = (TextView) findViewById(R.id.textOrderTime);
        mParcelStatus = (TextView) findViewById(R.id.textParcelStatus);
        statusSpinner = (Spinner) findViewById(R.id.spinnerStatusKey);
        commentSpinner = (Spinner) findViewById(R.id.spinnerCommentKey);
    }

    @Override
    public void setConsignmentDetails(List<ConsignmentListDatum> consignmentListDatums, int position) {
        ConsignmentListDatum data = consignmentListDatums.get(position);

        //TODO: ADD THE REQUIRED FIELDS
        // need to check weather its coming from search result or consignment list
        // for search result consignment_no is usually located in getConsignmentNo()
        if (getIntent().getBooleanExtra("is_search", false)) {
            consignment_no = data.getConsignmentNo();
            mCompany.setText(String.format(res.getString(R.string.company), data.getEso()));
            mCompanyPhone.setText(String.format(res.getString(R.string.contact), data.getEsoMobile()));
        } else {
            consignment_no = data.getConsignmentId();
            mCompany.setText(String.format(res.getString(R.string.company), data.getCompany()));
            mCompanyPhone.setVisibility(View.GONE);
            mCallCompany.setVisibility(View.GONE);
        }
        mConsignmentId.setText(String.format(res.getString(R.string.consignment_id), consignment_no));
        mSenderGroup.setText(String.format(res.getString(R.string.sender_group), data.getSenderGroup()));

        mProductId.setText(String.format(res.getString(R.string.product_id), data.getProductId()));
        //editProductPrice.setText(String.format(res.getString(R.string.product_price), data.getProductPrice()));
        editProductPrice.setText("" + data.getProductPrice());
        //editRecipientName.setText(String.format(res.getString(R.string.recipient_name), data.getRecipientName()));
        editRecipientName.setText("" + data.getRecipientName());
        //editRecipientMobile.setText(String.format(res.getString(R.string.recipient_mobile), data.getRecipientMobile()));
        editRecipientMobile.setText("" + data.getRecipientMobile());
        //editRecipientAddress.setText(String.format(res.getString(R.string.recipient_address), data.getRecipientAddress()));
        editRecipientAddress.setText("" + data.getRecipientAddress());
        mOrderTime.setText(String.format(res.getString(R.string.order_time), data.getOrderTime()));
        mParcelStatus.setText(String.format(res.getString(R.string.parcel_status), data.getParcelStatus()));

        current_parcel_status = data.getParcelStatus();
    }

    private void updateParcel() {
        showProgressDialog(false, "", getResources().getString(R.string.loading));

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ApiParams.TAG_BASE_URL).build();
        ParcelUpdateInterface myParcelUpdateInterface = restAdapter.create(ParcelUpdateInterface.class);

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
        map.put(ApiParams.PARAM_AUTHENTICATION_KEY, authentication_key);
        map.put(ApiParams.TAG_CONSIGNMENT_NO, consignment_no);
        map.put(ApiParams.PARAM_STATUS, statusParcel);

        // TODO: CONFIRM THE STATUS CHANGE FROM CLIENT
        if (statusParcel.equals("S4")) {
            map.put(ApiParams.PARAM_POD, "Delivered");
        }
        //TODO: CHANGE THE POD
        map.put(ApiParams.PARAM_POD, "");
        map.put(ApiParams.PARAM_COMMENT, commentParcel);

        myParcelUpdateInterface.getResult(ApiParams.TAG_PARCEL_UPDATE_KEY, map, new Callback<Status>() {
            @Override
            public void success(Status model, Response response) {

                hideProgressDialog();

                String status = model.getStatus();

                if (status.equals(ApiParams.TAG_SUCCESS)) {
                    mParcelStatus.setText("" + getResources().getStringArray(R.array.parcelStatusValuesArray)[spinner_position]);
                }
                //if (current_parcel_status.equals("Delivered")) {
                if (statusSpinner.getSelectedItem().toString().equals("Delivered") ||
                        statusSpinner.getSelectedItem().toString().equals("Partial Delivered")) {
                    promptReview();
                }

                customToast.showDeafultToast(ActivityConsignmentDetails.this, "" + model.getStatus() + "!", 0);

            }

            @Override
            public void failure(RetrofitError error) {
                hideProgressDialog();
                customToast.showDeafultToast(ActivityConsignmentDetails.this, "" + error.getMessage() + "!", 0);
            }
        });
    }

    private void updateContent() {
        showProgressDialog(false, "", getResources().getString(R.string.loading));

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ApiParams.TAG_BASE_URL).build();
        ParcelUpdateInterface myParcelUpdateInterface = restAdapter.create(ParcelUpdateInterface.class);

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
        map.put(ApiParams.PARAM_AUTHENTICATION_KEY, authentication_key);
        map.put(ApiParams.TAG_CONSIGNMENT_NO, consignment_no);

        map.put(ApiParams.PARAM_PARCEL_PRICE, editProductPrice.getText().toString());
        map.put(ApiParams.PARAM_RECIPIENT_NAME, editRecipientName.getText().toString());
        map.put(ApiParams.PARAM_RECIPIENT_MOBILE, editRecipientMobile.getText().toString());
        map.put(ApiParams.PARAM_RECIPIENT_ADDRESS, editRecipientAddress.getText().toString());

        myParcelUpdateInterface.getResult(ApiParams.TAG_CONSIGNMENT_UPDATE_KEY, map, new Callback<Status>() {
            @Override
            public void success(Status model, Response response) {

                hideProgressDialog();

                String status = model.getStatus();

                if (status.equals(ApiParams.TAG_SUCCESS)) {
                    mParcelStatus.setText("" + getResources().getStringArray(R.array.parcelStatusValuesArray)[spinner_position]);
                }

                editProductPrice.setFocusable(false);
                mEditProductInfo.setText("Edit");
                mEditProductInfo.setTag("true");
                mSaveProductInfo.setVisibility(View.GONE);

                editRecipientName.setFocusable(false);
                editRecipientMobile.setFocusable(false);
                editRecipientAddress.setFocusable(false);
                mEditRecipientInfo.setTag("true");
                mEditRecipientInfo.setText("Edit");
                mSaveRecipientInfo.setVisibility(View.GONE);

                //if (current_parcel_status.equals("Delivered")) {
                if (statusSpinner.getSelectedItem().toString().equals("Delivered")) {
                    promptReview();
                }

                customToast.showDeafultToast(ActivityConsignmentDetails.this, "" + model.getStatus() + "!", 0);

            }

            @Override
            public void failure(RetrofitError error) {
                hideProgressDialog();
                customToast.showDeafultToast(ActivityConsignmentDetails.this, "" + error.getMessage() + "!", 0);
            }
        });
    }

    private void promptReview() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_review, null);
        dialogBuilder.setView(dialogView);

        final AlertDialog b = dialogBuilder.create();
        b.setCancelable(false);
        b.show();

        final RatingBar ratingBarServiceReview = (RatingBar) dialogView.findViewById(R.id.ratingBarServiceReview);
        final RatingBar ratingBarProductReview = (RatingBar) dialogView.findViewById(R.id.ratingBarProductReview);

        final SignaturePad mSignaturePad = (SignaturePad) dialogView.findViewById(R.id.signature_pad);
        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
            }

            @Override
            public void onSigned() {
                //Event triggered when the pad is signed
                signature = mSignaturePad.getSignatureBitmap();
            }

            @Override
            public void onClear() {
                //Event triggered when the pad is cleared
            }
        });

        final Button btnRate = (Button) dialogView.findViewById(R.id.btnRate);
        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
                addReview(ratingBarServiceReview.getRating(), ratingBarProductReview.getRating(), signature);
            }
        });

        dialogBuilder.setTitle("Rate our service:");

    }

    private void addReview(float ratingBarServiceReview, float ratingBarProductReview, Bitmap signature) {
        hideProgressDialog();

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ApiParams.TAG_BASE_URL).build();
        ReviewInterface reviewInterface = restAdapter.create(ReviewInterface.class);

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
        map.put(ApiParams.PARAM_AUTHENTICATION_KEY, authentication_key);
        map.put(ApiParams.TAG_CONSIGNMENT_ID, consignment_no);

        map.put(ApiParams.PARAM_SERVICE_REVIEW, "" + ratingBarServiceReview);
        map.put(ApiParams.PARAM_PRODUCT_REVIEW, "" + ratingBarProductReview);

        reviewInterface.getResult(ApiParams.TAG_USER_REVIEW_KEY, map, new Callback<StatusAlternative>() {
            @Override
            public void success(StatusAlternative model, Response response) {

                hideProgressDialog();

                String status = "" + model.getStatus();

                Log.e("Status", status);
                customToast.showToast(ActivityConsignmentDetails.this, Color.RED, Color.BLACK, status, 1);
                if (status.equals(ApiParams.TAG_SUCCESS)) {
//                    customToast.showDeafultToast(ActivityConsignmentDetails.this, "" + model.getStatus() + "!", 0);
                } else {
//                    customToast.showDeafultToast(ActivityConsignmentDetails.this, "" + model.getMsg() + "!", 0);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                hideProgressDialog();
                customToast.showDeafultToast(ActivityConsignmentDetails.this, "" + error.getMessage() + "!", 0);
            }
        });
    }
}
