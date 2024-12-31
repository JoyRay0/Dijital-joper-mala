package com.mala.digital_joper_mala;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;




public class MainActivity extends AppCompatActivity {


    // XML id's----------------------------------------------------------------


    Toolbar toolbar;

    TextView text1,text2,text3;

    AppCompatButton firstpage,secondpage;

    AppCompatButton add1, add2, add3;
    Button less2, less3;
    AppCompatButton reset1, reset2, reset3;





    Vibrator vibrator;
    SharedPreferences sharedPreferences;
    boolean nightMode;
    
    //initial value********************************************

    int count = 0, i = 0, j = 0;



    //initial value********************************************


    private static final String PREF_NAME = "dark_light_mode";
    private static final String KEY_NAME = "dark_mode";
    private static final String appPackageName = "com.mala.digital_joper_mala";
    // XML id's----------------------------------------------------------------




    @SuppressLint("MissingInflatedId")
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
        setContentView(R.layout.activity_main);


        //Identity period start+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        add1 = findViewById(R.id.add1);
        add2 = findViewById(R.id.add2);
        add3 = findViewById(R.id.add3);
        //less2 = findViewById(R.id.less2);
        //less3 = findViewById(R.id.less3);
        reset1 = findViewById(R.id.reset1);
        reset2 = findViewById(R.id.reset2);
        reset3 = findViewById(R.id.reset3);
        firstpage = findViewById(R.id.firstpage);
        secondpage = findViewById(R.id.secondpage);

       toolbar = findViewById(R.id.toolbar);





        //Identity period end+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


        tooLbar();


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
        /*

        less2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    count--;

                if (count > -1 && count <= 16){

                    text1.setText (" "+count);


                }
                vibrate();


            }
        });

         */

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

        /*
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

         */

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




    }//on create==================================

    private void vibrate(){                     //vibrate

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        vibrator.vibrate(50);



    }

    private void tooLbar(){                     //toolbar

        toolbar.inflateMenu(R.menu.toolbar_menu);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.share){
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    String Body = "Download this App";
                    String sub = "https://play.google.com/store/apps/details?id="+appPackageName;
                    intent.putExtra(Intent.EXTRA_TEXT,Body);
                    intent.putExtra(Intent.EXTRA_TEXT,sub);
                    startActivity(Intent.createChooser(intent,null));


                }


                return false;
            }
        });


    }



}//public class ===========================