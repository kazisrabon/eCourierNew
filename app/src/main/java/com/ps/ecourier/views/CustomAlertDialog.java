/*
 * Copyright (c) Prokash Sarkar 2015. Contact, prokashsarkar@outlook.com.
 */

package com.ps.ecourier.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Gakk Apps on 5/28/2015.
 */
public class CustomAlertDialog {

    Context context;

    public CustomAlertDialog(Context context) {
        this.context = context.getApplicationContext();
    }

    public void showMessageDialog(String title, String message, String action_message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                context);

        alertDialog.setTitle(title);

        alertDialog
                .setMessage(message);

        alertDialog.setPositiveButton(action_message,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });


        alertDialog.show();
    }
}
