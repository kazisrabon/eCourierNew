package com.ps.ecourier.webservices;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Prokash Sarkar on 6/14/2016.
 */
public class ApiParams {

    public static final long SPLASH_TIME_OUT = 3 * 1000;

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
    public static final SimpleDateFormat longDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    public static final DateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
    public static final DateFormat longTimeFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);

    public static final String TAG_BASE_URL = "http://www.ecourier.com.bd";

    public static final String TAG_SUCCESS = "success";

    public static final String USER_TYPE_ADMIN = "1";
    public static final String USER_TYPE_USER = "2";

    // common params
    public static final String PARAM_AUTHENTICATION_KEY = "authentication_key";
    public static final String PARAM_AGENT_ID = "agent_id";
    public static final String PARAM_ADMIN_ID = "admin_id";
    public static final String PARAM_GROUP = "group";

    // user login
    public static final String TAG_LOGIN_KEY = "andLogin.php";
    public static final String PARAM_USER_NAME = "username";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_USER_TYPE = "user_type";

    // product search
    public static final String TAG_CONSIGNMENT_SEARCH_KEY = "andConsignmentSearch.php";
    public static final String TAG_CONSIGNMENT_NO = "consignment_no";

    // consignment list
    public static final String TAG_CONSIGNMENT_LIST_KEY = "andConsignmentList.php";
    public static final String PARAM_TYPE = "type";

    // parcel update
    public static final String TAG_CONSIGNMENT_UPDATE_KEY = "andConsignmentListUpdate.php";
    public static final String PARAM_PARCEL_PRICE = "product_price";
    public static final String PARAM_RECIPIENT_NAME = "recipient_name";
    public static final String PARAM_RECIPIENT_MOBILE = "recipient_mobile";
    public static final String PARAM_RECIPIENT_ADDRESS = "recipient_address";

    // product review
    public static final String TAG_USER_REVIEW_KEY = "userReview.php";
    public static final String TAG_CONSIGNMENT_ID = "consignment_id";
    public static final String PARAM_SERVICE_REVIEW = "service_review";
    public static final String PARAM_PRODUCT_REVIEW = "product_reviw";

    // product status update
    public static final String TAG_PARCEL_UPDATE_KEY = "andParcelStatusUpdate.php";
    public static final String PARAM_STATUS = "status";
    public static final String PARAM_POD = "pod";
    public static final String PARAM_COMMENT = "comment";

    // reports
    public static final String TAG_REPORTS_KEY = "andReports.php";
    public static final String PARAM_FROM_DATE = "from_date";
    public static final String PARAM_TO_DATE = "to_date";
    public static final String PARAM_RECIPIENT_TYPE = "type";

    // profile
    public static final String TAG_PROFILE_KEY = "userProfile.php";
}
