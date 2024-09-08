package com.mala.digital_joper_mala;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import androidx.appcompat.app.AppCompatActivity;


public class MoreAppsActivity extends AppCompatActivity {

    //XML id's ---------------------------------------------------------

    WebView web_view;

    //XML id's ---------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_apps);

        //identity period---------------------------------------------

        web_view = findViewById(R.id.web_view);

        //identity period---------------------------------------------




        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.setWebViewClient(new WebViewClient());
        web_view.loadUrl("https://sites.google.com/view/rk-softwares-official-site");

    }
}