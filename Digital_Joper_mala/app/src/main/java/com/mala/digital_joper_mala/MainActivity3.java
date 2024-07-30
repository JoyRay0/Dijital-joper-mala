package com.mala.digital_joper_mala;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageButton;


public class MainActivity3 extends AppCompatActivity {

    //XML id's-----------------------------------------------------

    ImageButton mback;





    //XML id's-----------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //identity period------------------------------------------

        mback = findViewById(R.id.back);







        //identity period------------------------------------------



        //starting point----------------------------------------------------------

        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myintent = new Intent(MainActivity3.this, MainActivity.class);
                startActivity(myintent);

            }
        });




    }
}