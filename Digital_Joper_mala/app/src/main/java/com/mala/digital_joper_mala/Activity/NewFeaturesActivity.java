package com.mala.digital_joper_mala.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.mala.digital_joper_mala.R;


public class NewFeaturesActivity extends AppCompatActivity {

    //XML id's------------------------------------------

    ImageButton back;

    Boolean nightMode;
    SharedPreferences sharedPreferences;

    private static final String PREF_NAME = "dark_light_mode";
    private static final String KEY_NAME = "dark_mode";

    //XML id's------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //dark----------------------------------------------------------------
        sharedPreferences = getSharedPreferences(PREF_NAME,MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean(KEY_NAME, false);


        AppCompatDelegate.setDefaultNightMode(nightMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        //dark----------------------------------------------------------------

        setContentView(R.layout.new_features);

        //identity period----------------------------------------------

        back = findViewById(R.id.back);



        //identity period----------------------------------------------


        //back button---------------------------------------

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(NewFeaturesActivity.this, SettingActivity.class);
                startActivity(intent);

            }
        });

        //back button---------------------------------------

    }
}