package com.mala.digital_joper_mala.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.mala.digital_joper_mala.R;


public class Act_NewFeatures extends AppCompatActivity {

    //XML id's------------------------------------------

    ImageButton back;


    //XML id's------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_new_features);

        //identity period----------------------------------------------

        back = findViewById(R.id.back);

        //identity period----------------------------------------------


        //back button---------------------------------------

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Act_NewFeatures.this, Act_Setting.class);
                startActivity(intent);

            }
        });

        //back button---------------------------------------

    }
}