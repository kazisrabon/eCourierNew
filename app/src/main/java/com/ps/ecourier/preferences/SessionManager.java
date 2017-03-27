package com.ps.ecourier.preferences;

/**
 * Created by Prokash Sarkar on 2/18/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.ps.ecourier.WelcomeActivity;

import java.util.HashMap;

public class SessionManager {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "session_prefs";

    // All Shared Preferences Keys
    private static final String IS_logIN = "IsloggedIn";

    public static final String KEY_ACCESS_TOKEN = "token";
    public static final String KEY_SOCIAL_ID = "social_id";
    public static final String KEY_ID = "id";
    public static final String KEY_AVATAR = "profile_url";
    public static final String KEY_NAME = "name";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_AUTH_TYPE = "auth_type";
    public static final String KEY_CURRENT_GROUP_ID = "group_id";
    public static final String KEY_GROUP_WRITE_PERMISSION = "group_write_permission";

    // Configurations Keys
    public static final String KEY_AVAILABILITY = "availability";
    public static final String KEY_CANCELABLE = "cancelable";
    public static final String KEY_TIMEOUT = "timeout";
    public static final String KEY_COUNTDOWN = "countdown";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_URL = "url";
    public static final String KEY_PREVIEW = "preview";

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session with token key
     */
    public void createloginSession(String token) {
        // Storing login value as TRUE
        editor.putBoolean(IS_logIN, true);

        editor.putString(KEY_ACCESS_TOKEN, token);

        // commit changes
        editor.commit();
    }

    /**
     * Create login session
     */
    public void createloginSession(String id, String name, String email, String avatar, String auth_type) {
        // Storing login value as TRUE
        editor.putBoolean(IS_logIN, true);

        editor.putString(KEY_SOCIAL_ID, id);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_AVATAR, avatar);
        editor.putString(KEY_AUTH_TYPE, auth_type);

        // commit changes
        editor.commit();
    }

    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_ID, pref.getString(KEY_ID, null));
        user.put(KEY_SOCIAL_ID, pref.getString(KEY_SOCIAL_ID, null));
        user.put(KEY_ACCESS_TOKEN, pref.getString(KEY_ACCESS_TOKEN, null));
        user.put(KEY_AVATAR, pref.getString(KEY_AVATAR, null));
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_ADDRESS, pref.getString(KEY_ADDRESS, null));
        user.put(KEY_PHONE, pref.getString(KEY_PHONE, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        user.put(KEY_AUTH_TYPE, pref.getString(KEY_AUTH_TYPE, null));
        user.put(KEY_CURRENT_GROUP_ID, pref.getString(KEY_CURRENT_GROUP_ID, null));
        user.put(KEY_GROUP_WRITE_PERMISSION, pref.getString(KEY_GROUP_WRITE_PERMISSION, null));
        return user;
    }


    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, WelcomeActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring login Activity
        _context.startActivity(i);
    }

    public void clearData() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    /**
     * Quick check for login
     **/
    // Get login State
    public boolean isloggedIn() {
        return pref.getBoolean(IS_logIN, false);
    }
}