package com.mala.digital_joper_mala.Activity;


import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.mala.digital_joper_mala.R;


public class SettingActivity extends AppCompatActivity {

    //XML id's---------------------------------------------

    private ImageButton back;
    private TextView privacy, app_info, feedback, features, share, rating, more_app;


    private RelativeLayout main;

    private AppCompatTextView mode;
    private RadioGroup day_night_radio_group;
    private AppCompatRadioButton day_radio_button, night_radio_button, system_default_radio_button;
    private AppCompatButton mode_ok;

    boolean nightMode;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;



    private static final String appPackageName = "com.mala.digital_joper_mala";

    //XML id's---------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);


       /*
       //dark----------------------------------------------------------------
        sharedPreferences = getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE);
        editor = sharedPreferences.edit();
        nightMode = sharedPreferences.getBoolean("nightmode", false);



        if (nightMode){
            sw.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

         */


        //dark----------------------------------------------------------------

        /*
        sharedPreferences = getSharedPreferences("App_theme",MODE_PRIVATE);
        sharedPreferences.getString("theme_mode",);

         */


        //identity-------------------------------------------

        back = findViewById(R.id.back);
        privacy = findViewById(R.id.privacy);
        app_info = findViewById(R.id.app_info);
        feedback = findViewById(R.id.feedback);
        features = findViewById(R.id.features);
        //mode = findViewById(R.id.mode);
        share = findViewById(R.id.share);
        rating = findViewById(R.id.rating);
        more_app = findViewById(R.id.more_app);
        main = findViewById(R.id.main);

        //identity-------------------------------------------


        //night mode-----------------------------------------------------------

        /*
        mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mytheme();

            }
        });

         */

        /*

         if (nightMode){

                  editor = sharedPreferences.edit();
                  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                  editor.putBoolean("night",false);
                  restart_activity_with_animation();

              }else {

                  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                  editor = sharedPreferences.edit();
                  editor.putBoolean("night", true);
                  restart_activity_with_animation();
              }
              editor.apply();

        */


        //night mode-----------------------------------------------------------



        //back button+++++++++++++++++++++++++++++++++++++++++++++++

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SettingActivity.this, HomeAllMalaActivity.class));
                finishAffinity();

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

                startActivity( new Intent(SettingActivity.this, PrivacyPolicyActivity.class));

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

                startActivity(new Intent(SettingActivity.this, NewFeaturesActivity.class));
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


    //selected theme---------------------------------------------------
    private void mytheme() {

        Dialog dialog = new Dialog(SettingActivity.this);
        dialog.setContentView(R.layout.mode);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.show();

        day_night_radio_group = dialog.findViewById(R.id.day_night_radio_group);
        day_radio_button = dialog.findViewById(R.id.day_radio_button);
        night_radio_button = dialog.findViewById(R.id.night_radio_button);
        system_default_radio_button = dialog.findViewById(R.id.system_default_radio_button);
        mode_ok = dialog.findViewById(R.id.mode_ok);



        mode_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              int checked_radioButton = day_night_radio_group.getCheckedRadioButtonId();

              if (checked_radioButton == R.id.day_radio_button){

                  day_radio_button.setChecked(true);
                  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                  mode_theme("light");



              } else if (checked_radioButton == R.id.night_radio_button) {

                  night_radio_button.setChecked(true);
                  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                  mode_theme("dark");




              } else if (checked_radioButton == R.id.system_default_radio_button) {

                  system_default_radio_button.setChecked(true);
                  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                  mode_theme("system");


              }


              dialog.dismiss();

            }
        });

        /*
        if (nightMode){

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            editor.putBoolean("nightmode",false);

        }else {

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            editor.putBoolean("nightmode",true);

        }
        editor.apply();

         */

    }


    private void mode_theme(String mode) {


       sharedPreferences = getSharedPreferences("App_theme",MODE_PRIVATE);
        editor.putString("theme_mode",mode);
        editor = sharedPreferences.edit();
        editor.commit();


    }


    private String load_mode(){

       SharedPreferences sharedPreferences = getSharedPreferences("App_theme",MODE_PRIVATE);

        return  sharedPreferences.getString("theme_mode","system");
    }
    //selected theme---------------------------------------------------


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(SettingActivity.this, HomeAllMalaActivity.class));
        finishAffinity();

    }
}//public class =========================================