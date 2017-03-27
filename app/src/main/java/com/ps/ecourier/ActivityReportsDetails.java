package com.ps.ecourier;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.ps.ecourier.base.BaseActivity;
import com.ps.ecourier.interfaces.ReportsDetails;
import com.ps.ecourier.pojo.ReportsListDatum;
import com.ps.ecourier.session.SessionUserData;
import com.ps.ecourier.views.CustomToast;

import java.util.ArrayList;
import java.util.List;

public class ActivityReportsDetails extends BaseActivity implements ReportsDetails {

    private int position;
    private ArrayList<ReportsListDatum> myList;
    private String consignment_no = "";

    private Resources res;
    private TextView mConsignmentId, mRecipientName, mRecipientMobile, mCallRecipient,
            mRecipientAddress, mRecipientArea, mParcelStatus, mShippingPrice, mProductPrice, mCodPrice,
            mPaymentMethod, mParcelId, mPaymentClear, mOrderTime, mComments;

    private CustomToast customToast;
    private SessionUserData sessionUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        customToast = new CustomToast();
        sessionUserData = new SessionUserData(this);

        res = getResources();

        initializeViews();

        // receives data from intent and and serialize it back to a list
        if (getIntent().getExtras() != null) {
            position = Integer.parseInt(getIntent().getStringExtra("consignment_data_position"));
            myList = (ArrayList<ReportsListDatum>) getIntent().getSerializableExtra("consignment_data_array");
            setReportsDetails(myList, position);
        }
    }

    private void initializeViews() {
        mConsignmentId = (TextView) findViewById(R.id.textConsignmentId);
        //mESO = (TextView) findViewById(R.id.textEso);
        mRecipientName = (TextView) findViewById(R.id.textRecipientName);
        mRecipientMobile = (TextView) findViewById(R.id.textRecipientMobile);
        mRecipientMobile.setMovementMethod(LinkMovementMethod.getInstance());
        mCallRecipient = (TextView) findViewById(R.id.txtRecipientMobile);
        mRecipientAddress = (TextView) findViewById(R.id.textRecipientAddress);
        mRecipientArea = (TextView) findViewById(R.id.textRecipientArea);
        mParcelStatus = (TextView) findViewById(R.id.textParcelStatus);
        mShippingPrice = (TextView) findViewById(R.id.textShippingPrice);
        mProductPrice = (TextView) findViewById(R.id.textProductPrice);
        mCodPrice = (TextView) findViewById(R.id.textCodPrice);
        mPaymentMethod = (TextView) findViewById(R.id.textPaymentMethod);
        mParcelId = (TextView) findViewById(R.id.textParcelId);
        mPaymentClear = (TextView) findViewById(R.id.textPaymentClear);
        mOrderTime = (TextView) findViewById(R.id.textOrderTime);
        mComments = (TextView) findViewById(R.id.textComments);

        mCallRecipient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ReportsListDatum data = myList.get(position);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel: " + data.getRecipientMobile()));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void setReportsDetails(List<ReportsListDatum> reportsListDatums, int position) {
        ReportsListDatum data = reportsListDatums.get(position);
        consignment_no = data.getConsignmentId();
        mConsignmentId.setText(String.format(res.getString(R.string.consignment_id), data.getConsignmentId()));
        // mESO.setText(String.format(res.getString(R.string.eso), data.getEso()));
        mRecipientName.setText(String.format(res.getString(R.string.recipient_name), data.getRecipientName()));
        mRecipientMobile.setText(String.format(res.getString(R.string.recipient_mobile), data.getRecipientMobile()));
        mRecipientAddress.setText(String.format(res.getString(R.string.recipient_address), data.getArea()));
        mRecipientArea.setText(String.format(res.getString(R.string.recipient_area), data.getAddress()));
        mParcelStatus.setText(String.format(res.getString(R.string.parcel_status), data.getParcelStatus()));
        mShippingPrice.setText(String.format(res.getString(R.string.shipping_price), data.getShippingPrice()));
        mProductPrice.setText(String.format(res.getString(R.string.product_price), data.getProductPrice()));
        mCodPrice.setText(String.format(res.getString(R.string.cod_price), "" + data.getCodPrice()));
        mPaymentMethod.setText(String.format(res.getString(R.string.payment_method), data.getPaymentMethod()));
        mParcelId.setText(String.format(res.getString(R.string.pacel_id), data.getParcelId()));
        mPaymentClear.setText(String.format(res.getString(R.string.payment_clear), data.getPaymentClear()));
        mOrderTime.setText(String.format(res.getString(R.string.order_time), data.getOrderTime()));
        mComments.setText(String.format(res.getString(R.string.comments), data.getComment()));
    }
}
