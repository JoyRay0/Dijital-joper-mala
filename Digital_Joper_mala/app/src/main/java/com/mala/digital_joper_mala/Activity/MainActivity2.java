package com.mala.digital_joper_mala.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.mala.digital_joper_mala.R;

public class MainActivity2 extends AppCompatActivity {

    //XML id's------------------------------------------------------------

    ImageButton back;

    Boolean nightMode;
    SharedPreferences sharedPreferences;

    private static final String PREF_NAME = "dark_light_mode";
    private static final String KEY_NAME = "dark_mode";
    private static final String appPackageName = "com.mala.digital_joper_mala";


    //XML id's------------------------------------------------------------



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
        setContentView(R.layout.boisnob_mala_rules);

        //identity period----------------------------------------------------------

        back = findViewById(R.id.back);

        //identity period----------------------------------------------------------




        //---------------------------------------------------------------------


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                Intent myback = new Intent(MainActivity2.this, Boisnob_mala.class);
                startActivity(myback);

            }
        });

        //---------------------------------------------------------------------

        
    }
}