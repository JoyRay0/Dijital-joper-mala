package com.mala.digital_joper_mala.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.mala.digital_joper_mala.R;


public class PrivacyPolicyActivity extends AppCompatActivity {

    //XML---------------------------------------------

    WebView web;

    //XML---------------------------------------------

    @SuppressLint({"MissingInflatedId", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_policy);

        //identity period-----------------------------------------------

        web = findViewById(R.id.web);

        //identity period-----------------------------------------------


        web.getSettings().setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient());
        web.loadUrl("https://sites.google.com/view/jopermala/home");




    }
}