package com.mala.digital_joper_mala;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;


public class AppInfoActivity extends AppCompatActivity {

    //XMl id's------------------------------------

    ImageButton back;

    //XMl id's------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_info);

        //identity period--------------------------------------

        back = findViewById(R.id.back);

        //identity period--------------------------------------




        //starting point ---------------------------------------------


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AppInfoActivity.this, SettingActivity.class);
                startActivity(intent);

            }
        });


    }
}