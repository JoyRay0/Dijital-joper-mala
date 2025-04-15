package com.mala.digital_joper_mala.Activity;


import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.transition.Fade;

import com.mala.digital_joper_mala.R;


public class Act_Setting extends AppCompatActivity {

    //XML id's---------------------------------------------

    private ImageButton back;
    private TextView privacy, app_info, feedback, features, share, rating, more_app;


    private RelativeLayout main;

    private AppCompatTextView mode_day_night;
    private RadioGroup day_night_radio_group;
    private AppCompatRadioButton day_radio_button, night_radio_button, system_default_radio_button;
    private AppCompatButton mode_ok;

    private static final String appPackageName = "com.mala.digital_joper_mala";

    //XML id's---------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences theme = getSharedPreferences("my_theme", MODE_PRIVATE);
        int savedTheme = theme.getInt("app_theme", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        AppCompatDelegate.setDefaultNightMode(savedTheme);

        setContentView(R.layout.act_setting);

        //identity-------------------------------------------

        back = findViewById(R.id.back);
        privacy = findViewById(R.id.privacy);
        app_info = findViewById(R.id.app_info);
        feedback = findViewById(R.id.feedback);
        features = findViewById(R.id.features);
        mode_day_night = findViewById(R.id.mode_day_night);
        share = findViewById(R.id.share);
        rating = findViewById(R.id.rating);
        more_app = findViewById(R.id.more_app);
        main = findViewById(R.id.main);

        //identity-------------------------------------------

        mode_day_night.setOnClickListener(view -> {

            mytheme();

        });

        //back button+++++++++++++++++++++++++++++++++++++++++++++++

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Act_Setting.this, Act_Home_All_Mala.class));
                finishAffinity();

            }
        });

        //back button+++++++++++++++++++++++++++++++++++++++++++++++


        //app_ver---------------------------------------------

        app_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //dialog***************************************************

                Dialog dialog = new Dialog(Act_Setting.this);
                dialog.setContentView(R.layout.lay_info_dialog);
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

                startActivity( new Intent(Act_Setting.this, Act_PrivacyPolicy.class));

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

                finishAffinity();

            }
        });

        //Feedback--------------------------------------------


        //Features----------------------------------------------
        features.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Act_Setting.this, Act_NewFeatures.class));
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
                    finishAffinity();
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    finishAffinity();
                }

                //market://details?id=
            }
        });

        // Rating the app-------------------------------------------------


        //more app-------------------------------------------------

        more_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity( new Intent(Act_Setting.this, Act_MoreApps.class));
            }
        });
        
        //more app-------------------------------------------------

    }


    //selected theme---------------------------------------------------
    private void mytheme() {

        Dialog dialog = new Dialog(Act_Setting.this);
        dialog.setContentView(R.layout.lay_day_night);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        day_night_radio_group = dialog.findViewById(R.id.day_night_radio_group);
        day_radio_button = dialog.findViewById(R.id.day_radio_button);
        night_radio_button = dialog.findViewById(R.id.night_radio_button);
        system_default_radio_button = dialog.findViewById(R.id.system_default_radio_button);
        mode_ok = dialog.findViewById(R.id.mode_ok);


        SharedPreferences theme = getSharedPreferences("my_theme", MODE_PRIVATE);


        int current_mode = theme.getInt("app_theme",AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

        switch (current_mode){

            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
                system_default_radio_button.setChecked(true);
                break;

            case AppCompatDelegate.MODE_NIGHT_YES:
                night_radio_button.setChecked(true);
                break;

            case AppCompatDelegate.MODE_NIGHT_NO:
                day_radio_button.setChecked(true);
                break;

        }


        mode_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedID = day_night_radio_group.getCheckedRadioButtonId();
                int selectedTheme;

                if (selectedID == R.id.system_default_radio_button){

                    selectedTheme = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;

                } else if (selectedID == R.id.night_radio_button) {

                    selectedTheme = AppCompatDelegate.MODE_NIGHT_YES;

                }else {

                    selectedTheme = AppCompatDelegate.MODE_NIGHT_NO;
                }

                theme.edit()
                        .putInt("app_theme", selectedTheme)
                        .apply();

                getDelegate().setLocalNightMode(selectedTheme);
                Intent intent = getIntent();
                finishAffinity();
                startActivity(intent);
                dialog.dismiss();



            }
        });

        dialog.show();
    }
    //selected theme---------------------------------------------------


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(Act_Setting.this, Act_Home_All_Mala.class));
        finishAffinity();

    }

}//public class =========================================