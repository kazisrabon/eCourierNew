package com.ps.ecourier.base;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

import com.ps.ecourier.views.CustomToast;

/**
 * Created by Prokash Sarkar on 6/27/2015.
 */
public class BaseActivity extends AppCompatActivity {

    private CustomToast customToast;
    private ProgressDialog progressDialog;

    public void showToast(String message, int duration) {
        customToast = new CustomToast();
        customToast.showDeafultToast(this, message, duration);
    }

    public void hideProgressDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }
    }

    public void showProgressDialog(boolean show_title, String title, String message) {
        progressDialog = new ProgressDialog(this);
        if (show_title) {
            progressDialog.setTitle(title);
        }
        progressDialog.setMessage(message);
        progressDialog.show();

    }
}
