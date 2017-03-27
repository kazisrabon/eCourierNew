package com.ps.ecourier.base;

/**
 * Created by Prokash Sarkar on 6/26/2015.
 */

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;

import com.ps.ecourier.views.CustomToast;

public class BaseFragment extends Fragment {

    private CustomToast customToast;
    private ProgressDialog progressDialog;

    public void showToast(String message, int duration) {
        customToast = new CustomToast();
        customToast.showDeafultToast(getActivity(), message, duration);
    }

    public void hideDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }
    }

    public void showDialog(boolean show_title, String title, String message) {
        progressDialog = new ProgressDialog(getActivity());
        if (show_title) {
            progressDialog.setTitle(title);
        }
        progressDialog.setMessage(message);
        progressDialog.show();

    }
}
