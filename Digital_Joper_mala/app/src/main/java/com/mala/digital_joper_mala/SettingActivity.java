package com.mala.digital_joper_mala;


import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.os.Vibrator;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;



public class SettingActivity extends AppCompatActivity {

    //XML id's---------------------------------------------

    ImageButton back;
    TextView privacy, app_info, feedback, features, share, rating, more_app;



    SwitchCompat sw;
     boolean nightMode,vib_enable;

    SharedPreferences sharedPreferences,sharedPreferences1;
    SharedPreferences.Editor editor,editor1;
    Vibrator vibrator;



    private final String appPackageName = "com.mala.digital_joper_mala";

    //XML id's---------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        //identity-------------------------------------------

        back = findViewById(R.id.back);
        privacy = findViewById(R.id.privacy);
        app_info = findViewById(R.id.app_info);
        feedback = findViewById(R.id.feedback);
        features = findViewById(R.id.features);
        sw = findViewById(R.id.sw);
        share = findViewById(R.id.share);
        rating = findViewById(R.id.rating);
        more_app = findViewById(R.id.more_app);




        //dark----------------------------------------------------------------
        sharedPreferences = getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("night", false);
        //dark----------------------------------------------------------------

        //identity-------------------------------------------



        //night mode-----------------------------------------------------------

        if (nightMode){
            sw.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }



      sw.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              if (nightMode){

                  editor = sharedPreferences.edit();
                  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                  editor.putBoolean("night",false);

              }else {

                  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                  editor = sharedPreferences.edit();
                  editor.putBoolean("night", true);

              }
              editor.apply();


          }
      });

        //night mode-----------------------------------------------------------



        //back button+++++++++++++++++++++++++++++++++++++++++++++++

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        //back button+++++++++++++++++++++++++++++++++++++++++++++++



        //app_ver---------------------------------------------

        app_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //dialog***************************************************

                Dialog dialog = new Dialog(SettingActivity.this);
                dialog.setContentView(R.layout.custom_dialog_box);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                dialog.show();

                //dialog***************************************************

                //Intent intent = new Intent(SettingActivity.this, AppInfoActivity.class);
                //startActivity(intent);

            }
        });

        //app_ver---------------------------------------------



        //privacy------------------------------------------



        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent1 = new Intent(SettingActivity.this, PrivacyPolicyActivity.class);
                startActivity(intent1);

            }
        });



        //privacy------------------------------------------


        //Feedback--------------------------------------------

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_SENDTO);
                String uriText = "mailto:"+ Uri.encode(" r.k.softwares17@gmail.com");
                Uri uri = Uri.parse(uriText);
                intent.setData(uri);
                startActivity(Intent.createChooser(intent," "));

            }
        });

        //Feedback--------------------------------------------


        //Features----------------------------------------------

        features.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SettingActivity.this, NewFeaturesActivity.class);
                startActivity(intent);

            }
        });

        //Features----------------------------------------------




        //Share the app----------------------------------------------

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String Body = "Download this App";
                String sub = "https://play.google.com/store/apps/details?id="+appPackageName;
                intent.putExtra(Intent.EXTRA_TEXT,Body);
                intent.putExtra(Intent.EXTRA_TEXT,sub);
                startActivity(Intent.createChooser(intent,null));

            }
        });


        //Share the app----------------------------------------------




        //Rating the app-------------------------------------------------

        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                }

                //market://details?id=
            }
        });


        // Rating the app-------------------------------------------------


        //more app-------------------------------------------------

        more_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity( new Intent(SettingActivity.this, MoreAppsActivity.class));
            }
        });
        
        //more app-------------------------------------------------

    }
}