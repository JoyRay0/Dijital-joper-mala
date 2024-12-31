package com.mala.digital_joper_mala;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdvatageForJopaActivity extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    boolean nightMode;

    private static final String PREF_NAME = "dark_light_mode";
    private static final String KEY_NAME = "dark_mode";
    private static final String appPackageName = "com.mala.digital_joper_mala";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //dark----------------------------------------------------------------
        sharedPreferences = getSharedPreferences(PREF_NAME,MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean(KEY_NAME, false);


        /*
        if (nightMode){
            sw.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
         */
        AppCompatDelegate.setDefaultNightMode(nightMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        //dark----------------------------------------------------------------

        setContentView(R.layout.advatage_for_jopa);

    }
}