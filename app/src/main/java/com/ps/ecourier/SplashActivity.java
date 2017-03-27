package com.ps.ecourier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ps.ecourier.session.SessionUserData;
import com.ps.ecourier.webservices.ApiParams;

public class SplashActivity extends Activity {

    private SessionUserData sessionUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sessionUserData = new SessionUserData(getApplicationContext());

        if (sessionUserData.isLoggedIn()) {
            /**
             * Call this function whenever you want to check user login
             * This will redirect user to LoginActivity is he is not
             * logged in
             * */
            sessionUserData.checkLogin();
            finish();
        } else {

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent i = new Intent(getApplicationContext(), WelcomeActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }
            }, ApiParams.SPLASH_TIME_OUT);
        }

    }
}