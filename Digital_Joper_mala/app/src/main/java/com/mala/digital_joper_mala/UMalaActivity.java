package com.mala.digital_joper_mala;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;


public class UMalaActivity extends AppCompatActivity {

    //XML id's--------------------------------------------------

    AppCompatAutoCompleteTextView ed_autocomplete_textview1, ed_autocomplete_textview2;

    AppCompatTextView tv_mantras1, tv_mantras2;

    TextView text1,text2,text3;

    AppCompatButton add1, add2, add3;

    AppCompatButton reset1, reset2, reset3;

    AppCompatImageView save1, save2, iv_eye1, iv_eye2;

    Toolbar toolbar;

    ImageButton back;

    Vibrator vibrator;

    SharedPreferences sharedPreferences, save_text1, save_text2;
    boolean nightMode;


    int count = 0, i = 0, j = 0;

    private static final String PREF_NAME = "dark_light_mode";
    private static final String KEY_NAME = "dark_mode";
    private static final String appPackageName = "com.mala.digital_joper_mala";

    //XML id's--------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //dark----------------------------------------------------------------

        sharedPreferences = getSharedPreferences(PREF_NAME,MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean(KEY_NAME, false);

        AppCompatDelegate.setDefaultNightMode(nightMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        //dark----------------------------------------------------------------
        setContentView(R.layout.u_mala);

        //identity period -----------------------------------------------------------

        ed_autocomplete_textview1 = findViewById(R.id.ed_autocomplete_textview1);
        ed_autocomplete_textview2 = findViewById(R.id.ed_autocomplete_textview2);
        tv_mantras1 = findViewById(R.id.tv_mantras1);
        tv_mantras2 = findViewById(R.id.tv_mantras2);
        toolbar = findViewById(R.id.toolbar);
        add1 = findViewById(R.id.add1);
        add2 = findViewById(R.id.add2);
        add3 = findViewById(R.id.add3);
        reset1 = findViewById(R.id.reset1);
        reset2 = findViewById(R.id.reset2);
        reset3 = findViewById(R.id.reset3);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        save1 = findViewById(R.id.save1);
        save2 = findViewById(R.id.save2);
        iv_eye1 = findViewById(R.id.iv_eye1);
        iv_eye2 = findViewById(R.id.iv_eye2);
        back = findViewById(R.id.back);


        //identity period -----------------------------------------------------------

        sharedPreferences = getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("night",false);

        save_text1 = getSharedPreferences("text_save1",MODE_PRIVATE);
        String saved_text1 = save_text1.getString("ed_saved1","");


        save_text2 = getSharedPreferences("text_save2",MODE_PRIVATE);
        String saved_text2 = save_text2.getString("ed_saved2","");



        tv_mantras1.setText("‘‘ "+saved_text1+" ’’");
        tv_mantras2.setText("‘‘ "+saved_text2+" ’’");

        save();




        if (nightMode){

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }

        ed_autocomplete_textview1.setVisibility(View.VISIBLE);
        ed_autocomplete_textview2.setVisibility(View.VISIBLE);
        save1.setVisibility(View.VISIBLE);
        save2.setVisibility(View.VISIBLE);

        iv_eye1.setImageResource(R.drawable.eye_svgrepo_com);
        iv_eye1.setTag("open_eye");

        iv_eye1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String current_tag = (String) iv_eye1.getTag();

                if ("open_eye".equals(current_tag)){

                    iv_eye1.setImageResource(R.drawable.eye_closed_svgrepo_com);
                    iv_eye1.setTag("close_eye");
                    ed_autocomplete_textview1.setVisibility(View.GONE);
                    save1.setVisibility(View.GONE);



                } else if ("close_eye".equals(current_tag)) {

                    iv_eye1.setImageResource(R.drawable.eye_svgrepo_com);
                    iv_eye1.setTag("open_eye");
                    ed_autocomplete_textview1.setVisibility(View.VISIBLE);
                    save1.setVisibility(View.VISIBLE);

                }

            }
        });

        iv_eye2.setImageResource(R.drawable.eye_svgrepo_com);
        iv_eye2.setTag("open_eye");

        iv_eye2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String current_tag = (String) iv_eye2.getTag();

                if ("open_eye".equals(current_tag)){

                    iv_eye2.setImageResource(R.drawable.eye_closed_svgrepo_com);
                    iv_eye2.setTag("close_eye");
                    ed_autocomplete_textview2.setVisibility(View.GONE);
                    save2.setVisibility(View.GONE);



                } else if ("close_eye".equals(current_tag)) {

                    iv_eye2.setImageResource(R.drawable.eye_svgrepo_com);
                    iv_eye2.setTag("open_eye");
                    ed_autocomplete_textview2.setVisibility(View.VISIBLE);
                    save2.setVisibility(View.VISIBLE);

                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(UMalaActivity.this, HomeAllMalaActivity.class));

            }
        });





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




    }//on create====================================

    private void vibrate(){                     //vibrate

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        vibrator.vibrate(50);



    }


    private void save(){

        save1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ed_mantra1 = ed_autocomplete_textview1.getText().toString();

                if (ed_mantra1.length() > 0){

                    SharedPreferences.Editor editor1 = save_text1.edit();
                    editor1.putString("ed_saved1",ed_mantra1);
                    editor1.apply();

                    tv_mantras1.setText("‘‘"+ed_mantra1+"’’");

                }else {

                    tv_mantras1.setText("জপ মন্ত্র");


                }





            }
        });


        save2.setOnClickListener(new View.OnClickListener() {   //save button2
            @Override
            public void onClick(View view) {

                String ed_mantra2 = ed_autocomplete_textview2.getText().toString();

                if (ed_mantra2.length() > 0){

                    SharedPreferences.Editor editor2 = save_text2.edit();
                    editor2.putString("ed_saved2",ed_mantra2);
                    editor2.apply();

                    tv_mantras2.setText("‘‘"+ed_mantra2+"’’");


                }else {

                    tv_mantras2.setText("জপ মন্ত্র");


                }

            }
        });



    }
}//public class ===============================