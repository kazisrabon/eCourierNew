/*
 * Copyright (c) Prokash Sarkar 2015. Contact, prokashsarkar@outlook.com.
 */

package com.ps.ecourier.views;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ps.ecourier.R;

public class CustomToast {

    public void showDeafultToast(Activity activity, String title, int length) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_layout,
                (ViewGroup) activity.findViewById(R.id.toast_layout_root));

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(title);

        Toast toast = new Toast(activity);
        toast.setDuration(length);
        toast.setView(layout);
        toast.show();
    }

    public void showToast(Activity activity, int layout_color, int text_color,
                          String title, int length) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_layout,
                (ViewGroup) activity.findViewById(R.id.toast_layout_root));
        layout.setBackgroundColor(layout_color);

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setTextColor(text_color);
        text.setText(title);

        Toast toast = new Toast(activity);
        // toast.setGravity(Gravity.CENTER_HORIZONTAL |
        // Gravity.CENTER_VERTICAL,0, 0);

        // 0 = short, 1 = long
        toast.setDuration(length);
        toast.setView(layout);
        toast.show();
    }

}
