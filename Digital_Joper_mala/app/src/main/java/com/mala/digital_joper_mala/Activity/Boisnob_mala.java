package com.mala.digital_joper_mala.Activity;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.view.View;

import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mala.digital_joper_mala.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class Boisnob_mala extends AppCompatActivity {


    // XML id's----------------------------------------------------------------



    private TextView tv_count_display1, tv_count_display2,tv_count_display3;

    private AppCompatButton firstpage,secondpage;

    private AppCompatButton add1, add2, add3, reset1, reset2, reset3;

    //private Button less2, less3;

    private ImageButton back;

    private AppCompatImageButton iv_upload_button, iv_delete_button, ib_rules;

    private AppCompatImageView  iv_upload_image;

    private Vibrator vibrator;

    SharedPreferences sharedPreferences;
    boolean nightMode;
    
    //initial value********************************************
    private int count = 0, i = 0, j = 0;
    //initial value********************************************

    private static final int REQUEST_IMG_PICK = 1;
    // XML id's----------------------------------------------------------------




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boisnob_mala);

        //dark----------------------------------------------------------------



        /*
        if (nightMode){
            sw.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
         */


        //dark----------------------------------------------------------------



        //Identity period start+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        tv_count_display1 = findViewById(R.id.tv_count_display1);
        tv_count_display2 = findViewById(R.id.tv_count_display2);
        tv_count_display3 = findViewById(R.id.tv_count_display3);
        add1 = findViewById(R.id.add1);
        add2 = findViewById(R.id.add2);
        add3 = findViewById(R.id.add3);
        reset1 = findViewById(R.id.reset1);
        reset2 = findViewById(R.id.reset2);
        reset3 = findViewById(R.id.reset3);
        //firstpage = findViewById(R.id.firstpage);
        //secondpage = findViewById(R.id.secondpage);
        back = findViewById(R.id.back);
        iv_upload_button = findViewById(R.id.iv_upload_button);
        iv_delete_button = findViewById(R.id.iv_delete_button);
        iv_upload_image = findViewById(R.id.iv_upload_image);
        ib_rules = findViewById(R.id.ib_rules);



        //Identity period end+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++




        /* Display and Button started */

        //back button-----------------------------------------------
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Boisnob_mala.this, HomeAllMalaActivity.class));

            }
        });
        //back button-----------------------------------------------

        //custom floating button--------------------------------

        ib_rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Boisnob_mala.this, MainActivity2.class));

            }
        });

        //custom floating button--------------------------------


        //1st step started++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    count++;


                    if (count > 0 && count < 109){

                        tv_count_display1.setText(" "+count);

                    }

                vibrate();
            }
        });

        reset1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               count = 0;

                tv_count_display1.setText(" "+count);

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

                    tv_count_display2.setText(" " + i);

                }
                vibrate();

            }
        });

        reset2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i = 0;

                tv_count_display2.setText(" "+i);

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

                    tv_count_display3.setText(" " + j);

                }
                vibrate();

            }
        });

        reset3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                j = 0;

                tv_count_display3.setText(" "+j);

                vibrate();

            }
        });
        //3rd step ended++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        /*

        //Other pages-------------------------------------------------------------------------------
        firstpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mypage = new Intent(Boisnob_mala.this,MainActivity2.class);
                startActivity(mypage);

            }
        });

        secondpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Boisnob_mala.this, MainActivity3.class);
                startActivity(intent);

            }
        });
        //Other pages-------------------------------------------------------------------------------


         */
        //image added -------------------------------------------------------------------
        iv_upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_IMG_PICK);

            }
        });

        iv_delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                delete_img();

            }
        });

        load_img_form_storage();
        //image added -------------------------------------------------------------------


    }//on create==================================

    //vibrator-------------------------------------------------
    private void vibrate(){                     //vibrate

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(50);
    }
    //vibrator-------------------------------------------------

    //image methods===================================================
    private void load_img_form_storage(){

        try {

            FileInputStream fileInputStream = openFileInput("saved_image2.png");

            Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
            iv_upload_image.setImageBitmap(bitmap);
            fileInputStream.close();

        }catch (Exception e){

            e.printStackTrace();

        }

    }

    private void save_img_to_internal_storage(Bitmap bitmap){

        try {

            File file = new File(getFilesDir(),"saved_image2.png");
            if (file.exists()){
                file.delete();

            }

            //file created-----
            FileOutputStream fos = openFileOutput("saved_image2.png", MODE_PRIVATE);

            //image compressed----
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

        }catch (Exception e){

            e.printStackTrace();
            Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();

        }
    }

    private void delete_img(){

        File file = new File(getFilesDir(),"saved_image2.png");

        if (file.exists()){

            if (file.delete()){

                iv_upload_image.setImageDrawable(null);
                iv_upload_image.setImageResource(R.drawable.gallery);

            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMG_PICK && resultCode == RESULT_OK && data != null){

            try {

                Uri img_uri = data.getData();

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(Boisnob_mala.this.getContentResolver(), img_uri);

                iv_upload_image.setImageBitmap(bitmap);

                save_img_to_internal_storage(bitmap);

            } catch (Exception e) {

                e.printStackTrace();
            }

        }

    }
    //image methods=======================================


}//public class ===========================