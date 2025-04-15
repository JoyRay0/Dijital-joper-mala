package com.mala.digital_joper_mala.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.mala.digital_joper_mala.R;

public class Act_SplashScreen extends AppCompatActivity {

    //XML id's--------------------------------------------------------



    //XML id's--------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences theme = getSharedPreferences("my_theme", MODE_PRIVATE);
        int defaultTheme = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;


        int currentTheme = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentTheme == Configuration.UI_MODE_NIGHT_YES){

            defaultTheme = AppCompatDelegate.MODE_NIGHT_YES;

        }

        if (!theme.contains("app_theme")){

            theme.edit()
                    .putInt("app_theme", defaultTheme)
                    .apply();

        }


        int savedTheme = theme.getInt("app_theme", defaultTheme);

        if (AppCompatDelegate.getDefaultNightMode() != savedTheme){

            AppCompatDelegate.setDefaultNightMode(savedTheme);

            recreate();

            return;
        }


        setContentView(R.layout.act_splash_screen);

        //identity period----------------------------------------------------



        //identity period----------------------------------------------------


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){

            other_page();

        }else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {



                    other_page();
                }
            },2000);

        }


    }//on create=====================================
    private void other_page(){

        startActivity(new Intent(Act_SplashScreen.this, Act_Home_All_Mala.class));
        finishAffinity();

    }
}//public class ===========================================