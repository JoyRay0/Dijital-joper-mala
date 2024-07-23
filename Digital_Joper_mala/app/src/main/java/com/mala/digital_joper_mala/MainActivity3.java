package com.mala.digital_joper_mala;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;


public class MainActivity3 extends AppCompatActivity {

    //XML id's-----------------------------------------------------

    AppCompatImageButton mback;

   // TextToSpeech textToSpeech;

    //XML id's-----------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //identity period------------------------------------------

        mback = findViewById(R.id.back);
        //button = findViewById(R.id.button);

        //identity period------------------------------------------



        //starting point

        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myintent = new Intent(MainActivity3.this, MainActivity.class);
                startActivity(myintent);

            }
        });


        /*
        //text to speech------------------------------------------------------------

         textToSpeech = new TextToSpeech(MainActivity3.this, new TextToSpeech.OnInitListener() {
             @Override
             public void onInit(int i) {

             }
         });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                textToSpeech.speak(, TextToSpeech.QUEUE_FLUSH, null, null);

            }
        });

        //text to speech------------------------------------------------------------


         */
    }
}