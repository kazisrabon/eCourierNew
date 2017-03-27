package com.ps.ecourier.fragments;

import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.ps.ecourier.R;
import com.ps.ecourier.base.BaseFragment;

/**
 * Created by Prokash Sarkar on 8/10/2015.
 */
public class FragmentCheckVersion extends BaseFragment {

    private static final String url = "";
    private View rootView;
    private WebView web;
    private ProgressBar progressBar;
    private TextView title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_check_version, container, false);

        web = (WebView) rootView.findViewById(R.id.webView);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        title = (TextView) rootView.findViewById(R.id.textTitle);

        return rootView;
    }

    private void verifyVersion(double version) {
        int current_version = 0;
        try {
            current_version = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (version <= current_version) {
            title.setText(getResources().getString(R.string.no_update_available));
            title.setTypeface(null, Typeface.BOLD);
            progressBar.setVisibility(View.GONE);
        } else {
            web.loadUrl(url);
        }
    }
}
