package com.ps.ecourier.preferences;

/**
 * Created by Prokash Sarkar on 6/14/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

public class TokenManager {

    // Shared Preferences
    private SharedPreferences pref;

    // Editor for Shared preferences
    private Editor editor;

    // Context
    private Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "token_preference";

    // All Shared Preferences Keys
    private static final String IS_TOKEN_AVAILABLE = "IsTokenAvailable";

    public static final String KEY_ACCESS_TOKEN = "access_token";
    public static final String KEY_TOKEN_TYPE = "token_type";
    public static final String KEY_EXPIRES_IN = "expires_in";
    public static final String KEY_REFRESH_TOKEN = "refresh_token";
    public static final String KEY_SCOPE = "scope";

    // Constructor
    public TokenManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create token
     */
    public void createToken(
            String access_token,
            String token_type,
            int expires_in,
            String refresh_token,
            String scope
    ) {

        editor.putBoolean(IS_TOKEN_AVAILABLE, true);
        editor.putString(KEY_ACCESS_TOKEN, access_token);
        editor.putString(KEY_TOKEN_TYPE, token_type);
        editor.putString(KEY_EXPIRES_IN, "" + expires_in);
        editor.putString(KEY_REFRESH_TOKEN, refresh_token);
        editor.putString(KEY_SCOPE, scope);
        // commit changes
        editor.commit();
    }

    /**
     * Get stored session data
     */
    public HashMap<String, String> getToken() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_ACCESS_TOKEN, pref.getString(KEY_ACCESS_TOKEN, null));
        user.put(KEY_TOKEN_TYPE, pref.getString(KEY_TOKEN_TYPE, null));
        user.put(KEY_EXPIRES_IN, pref.getString(KEY_EXPIRES_IN, null));
        user.put(KEY_REFRESH_TOKEN, pref.getString(KEY_REFRESH_TOKEN, null));
        user.put(KEY_SCOPE, pref.getString(KEY_SCOPE, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     */
    public void clearToken() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    /**
     * Get token availability State
     * *
     */
    public boolean isTokenAvailable() {
        return pref.getBoolean(IS_TOKEN_AVAILABLE, false);
    }
}