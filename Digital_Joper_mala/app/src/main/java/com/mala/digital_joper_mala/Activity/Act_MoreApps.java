package com.mala.digital_joper_mala.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.mala.digital_joper_mala.R;


public class Act_MoreApps extends AppCompatActivity {

    //XML id's ---------------------------------------------------------

    WebView web_view;

    //XML id's ---------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_more_apps);

        //identity period---------------------------------------------

        web_view = findViewById(R.id.web_view);

        //identity period---------------------------------------------

        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.setWebViewClient(new WebViewClient());
        web_view.loadUrl("https://sites.google.com/view/rk-softwares-official-site");



        //back-----------------------------------------------------
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(Act_MoreApps.this, Act_Setting.class));
                finishAffinity();
            }
        });
        //back-----------------------------------------------------

    }//on create=====================================
}//public class========================================