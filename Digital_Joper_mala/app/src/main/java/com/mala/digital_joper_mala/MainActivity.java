package com.mala.digital_joper_mala;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatImageButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {


    // XML id's----------------------------------------------------------------

    TextView text1,text2,text3,firstpage,secondpage;
    Button add1, add2, add3;
    Button less2, less3;
    Button reset1, reset2, reset3;

    AppCompatImageButton setting;

    Vibrator vibrator;
    SharedPreferences sharedPreferences;
    boolean nightMode;
    
    //initial value********************************************

    int count = 0, i = 0, j = 0;

    //initial value********************************************


    // XML id's----------------------------------------------------------------




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Identity period start+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        add1 = findViewById(R.id.add1);
        add2 = findViewById(R.id.add2);
        add3 = findViewById(R.id.add3);
        less2 = findViewById(R.id.less2);
        less3 = findViewById(R.id.less3);
        reset1 = findViewById(R.id.reset1);
        reset2 = findViewById(R.id.reset2);
        reset3 = findViewById(R.id.reset3);
        firstpage = findViewById(R.id.firstpage);
        secondpage = findViewById(R.id.secondpage);
        setting = findViewById(R.id.setting);


        sharedPreferences = getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("night",false);



        //Identity period end+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++









        if (nightMode){

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }




        /* Display and Button started */


        //1st step started++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    count++;


                    if (count > 0 && count < 109){

                        text1.setText(" "+count);

                    }

                vibrate();
            }
        });

        less2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    count--;

                    if (count > -1 && count < 17){

                        text1.setText (" "+count);


                    }
                vibrate();


            }
        });

        reset1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               count = 0;

                text1.setText(" "+count);

                vibrate();

            }

        });
        //1st step ended++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++






        //2nd step started++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i++;

                if (i > 0 && i < 17) {

                    text2.setText(" " + i);

                }
                vibrate();

            }
        });

        less3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i--;

                if (i > -1 && i < 17){

                    text2.setText(" " + i);
                }

                vibrate();



            }
        });

        reset2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i = 0;

                text2.setText(" "+i);

                vibrate();

            }
        });
        //2nd step ended++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++






        //3rd step started++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        add3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                j++;

                if (j > 0 && j < 5) {

                    text3.setText(" " + j);

                }
                vibrate();

            }
        });

        reset3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                j = 0;

                text3.setText(" "+j);

                vibrate();

            }
        });
        //3rd step ended++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++





        //Other pages-------------------------------------------------------------------------------
        firstpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mypage = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(mypage);

            }
        });

        secondpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                startActivity(intent);

            }
        });
        //Other pages-------------------------------------------------------------------------------


        //setting button-------------------------------------

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);



            }
        });


        //setting button-------------------------------------


    }//on create==================================

    private void vibrate(){

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(50);

    }



}//public class ===========================