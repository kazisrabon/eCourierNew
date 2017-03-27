package com.ps.ecourier.fragments;

/**
 * Created by Prokash Sarkar on 6/14/2015.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;
import com.ps.ecourier.R;
import com.ps.ecourier.adapter.ConsignmentSearchSpinnerAdapter;
import com.ps.ecourier.adapter.ReportsListAdapter;
import com.ps.ecourier.base.BaseFragment;
import com.ps.ecourier.pojo.ReportsList;
import com.ps.ecourier.pojo.ReportsListDatum;
import com.ps.ecourier.session.SessionUserData;
import com.ps.ecourier.webservices.ApiParams;
import com.ps.ecourier.webservices.interfaces.ReportsListInterface;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FragmentReports extends BaseFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private static final String TIME_PATTERN = "HH:mm";
    private int calender_type = 0;
    private String from_date, to_date, report_type;

    private View rootView;
    private LinearLayout layoutTotalStatus;
    private Button mSubmit;
    private Spinner spinnerReportTypes;
    private TextView lblfromDate, lbltoDate, mTotalParcel, mTotalDelivered;
    private ListView consignmentListView;
    private List<ReportsListDatum> reportsListDatumList;
    private ReportsListAdapter adapter;
    private SessionUserData sessionUserData;
    private Calendar calendar;
    private DateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    private Resources res;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = getResources();
        sessionUserData = new SessionUserData(getActivity());
        calendar = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_reports, container, false);

        initialize();

        update();

        return rootView;
    }

    private void initialize() {

        layoutTotalStatus = (LinearLayout) rootView.findViewById(R.id.layoutTotalStatus);

        mTotalParcel = (TextView) rootView.findViewById(R.id.textTotalParcel);
        mTotalDelivered = (TextView) rootView.findViewById(R.id.textTotalDelivered);

        mSubmit = (Button) rootView.findViewById(R.id.btnSubmit);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                from_date = lblfromDate.getText().toString();
                to_date = lbltoDate.getText().toString();

                Log.e("DATE", "" + from_date + "/" + to_date + "/" + report_type);

                loadList();
            }
        });

        spinnerReportTypes = (Spinner) rootView.findViewById(R.id.spinnerReportSearch);
        spinnerReportTypes.setAdapter(new ConsignmentSearchSpinnerAdapter(getActivity(), R.layout.spinner_item, getResources().getStringArray(R.array.consignmentTypeValuesArray)));
        spinnerReportTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                report_type = "" + getResources().getStringArray(R.array.consignmentTypeValuesArray)[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lblfromDate = (TextView) rootView.findViewById(R.id.FromDatePicker);
        lblfromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calender_type = 0;
                DatePickerDialog.newInstance(FragmentReports.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getActivity().getFragmentManager(), "datePicker");

            }
        });
        lbltoDate = (TextView) rootView.findViewById(R.id.ToDatePicker);
        lbltoDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calender_type = 1;
                DatePickerDialog.newInstance(FragmentReports.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getActivity().getFragmentManager(), "datePicker");

            }
        });

        consignmentListView = (ListView) rootView.findViewById(R.id.list);
    }

    private void loadList() {

        showDialog(false, "", getResources().getString(R.string.loading));

        //Retrofit section start from here...
        //create an adapter for retrofit with base url
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ApiParams.TAG_BASE_URL).build();
        //creating a service for adapter with our ApiCallback class
        ReportsListInterface myApiCallback = restAdapter.create(ReportsListInterface.class);

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
        map.put(ApiParams.PARAM_FROM_DATE, "" + from_date);
        map.put(ApiParams.PARAM_TO_DATE, "" + to_date);
        map.put(ApiParams.PARAM_RECIPIENT_TYPE, "" + report_type);

        //Now, we will to call for response
        //Retrofit using gson for JSON-POJO conversion
        myApiCallback.getData(ApiParams.TAG_REPORTS_KEY, map, new Callback<ReportsList>() {
            @Override
            public void success(ReportsList reports_list, Response response) {
                //we get json object from server to our POJO or model class

                hideDialog();

                String status = reports_list.getStatus();
                if (status.equals(ApiParams.TAG_SUCCESS)) {

                    String total_parcel = String.format(res.getString(R.string.total_parcel_text), reports_list.getTotalParcel());
                    String total_delivered = String.format(res.getString(R.string.total_parcel_price_text), reports_list.getTotalParcelPrice().toString());
                    layoutTotalStatus.setVisibility(View.VISIBLE);
                    mTotalParcel.setText(total_parcel);
                    mTotalDelivered.setText(total_delivered);

                    //hide the spinner and button to save space
                    spinnerReportTypes.setAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left));
                    spinnerReportTypes.setVisibility(View.GONE);
                    mSubmit.setAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left));
                    mSubmit.setVisibility(View.GONE);

                    reportsListDatumList = reports_list.getData();
                    adapter = new ReportsListAdapter(getActivity(), reportsListDatumList);
                    consignmentListView.setAdapter(adapter);
                } else {
                    //showToast("" + "" + reports_list.getStatus() + "!", 0);
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

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        update();
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        update();
    }

    private void update() {
        SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm:ss");

        if (calender_type == 0) {
            lblfromDate.setText(sdf_date.format(calendar.getTime()));
        } else
            lbltoDate.setText(sdf_date.format(calendar.getTime()));
    }
}
