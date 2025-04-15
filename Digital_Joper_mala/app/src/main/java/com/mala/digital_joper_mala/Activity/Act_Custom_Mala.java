package com.mala.digital_joper_mala.Activity;

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


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.mala.digital_joper_mala.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class Act_Custom_Mala extends AppCompatActivity {

    //XML id's--------------------------------------------------

    private AppCompatAutoCompleteTextView ed_autocomplete_textview1, ed_autocomplete_textview2;

    private AppCompatTextView tv_mantras1, tv_mantras2;

    private TextView text1,text2,text3;

    private AppCompatButton add1, add2, add3, reset1, reset2, reset3;

    private AppCompatImageButton iv_upload_button, iv_delete_button;

    private AppCompatImageView save1, save2, iv_eye1, iv_eye2, iv_upload_image, iv_delete1, iv_delete2;

    Toolbar toolbar;

    private ImageButton back;

    private Vibrator vibrator;

    SharedPreferences sharedPreferences, save_text1, save_text2;
    boolean nightMode;


    int count = 0, i = 0, j = 0;

    private static final String PREF_NAME = "dark_light_mode";
    private static final String KEY_NAME = "dark_mode";
    private static final String appPackageName = "com.mala.digital_joper_mala";
    private static final int REQUEST_IMG_PICK = 1;

    String[] banglaNumber = {"১", "২", "৩", "৪", "৫", "৬", "৭", "৮", "৯", "১০",

            "১১", "১২", "১৩", "১৪", "১৫", "১৬", "১৭", "১৮", "১৯", "২০",
            "২১", "২২", "২৩", "২৪", "২৫", "২৬", "২৭", "২৮", "২৯", "৩০",
            "৩১", "৩২", "৩৩", "৩৪", "৩৫", "৩৬", "৩৭", "৩৮", "৩৯", "৪০",
            "৪১", "৪২", "৪৩", "৪৪", "৪৫", "৪৬", "৪৭", "৪৮", "৪৯", "৫০",
            "৫১", "৫২", "৫৩", "৫৪", "৫৫", "৫৬", "৫৭", "৫৮", "৫৯", "৬০",
            "৬১", "৬২", "৬৩", "৬৪", "৬৫", "৬৬", "৬৭", "৬৮", "৬৯", "৭০",
            "৭১", "৭২", "৭৩", "৭৪", "৭৫", "৭৬", "৭৭", "৭৮", "৭৯", "৮০",
            "৮১", "৮২", "৮৩", "৮৪", "৮৫", "৮৬", "৮৭", "৮৮", "৮৯", "৯০",
            "৯১", "৯২", "৯৩", "৯৪", "৯৫", "৯৬", "৯৭", "৯৮", "৯৯", "১০০",
            "১০১", "১০২", "১০৩", "১০৪", "১০৫", "১০৬", "১০৭", "১০৮"};

    String[] zero = {"০"};

    //XML id's--------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_custom_mala);



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
        text1 = findViewById(R.id.tv_count_display1);
        text2 = findViewById(R.id.tv_count_display2);
        text3 = findViewById(R.id.tv_count_display3);
        save1 = findViewById(R.id.save1);
        save2 = findViewById(R.id.save2);
        iv_eye1 = findViewById(R.id.iv_eye1);
        iv_eye2 = findViewById(R.id.iv_eye2);
        back = findViewById(R.id.back);
        iv_upload_image = findViewById(R.id.iv_upload_image);
        iv_upload_button = findViewById(R.id.iv_upload_button);
        iv_delete_button = findViewById(R.id.iv_delete_button);
        iv_delete1 = findViewById(R.id.iv_delete1);
        iv_delete2 = findViewById(R.id.iv_delete2);


        //identity period -----------------------------------------------------------

        save_text1 = getSharedPreferences("text_save1",MODE_PRIVATE);
        String saved_text1 = save_text1.getString("ed_saved1","");

        save_text2 = getSharedPreferences("text_save2",MODE_PRIVATE);
        String saved_text2 = save_text2.getString("ed_saved2","");

        tv_mantras1.setText("‘‘ "+saved_text1+" ’’");
        tv_mantras2.setText("‘‘ "+saved_text2+" ’’");

        save();


        ed_autocomplete_textview1.setVisibility(View.VISIBLE);
        ed_autocomplete_textview2.setVisibility(View.VISIBLE);
        save1.setVisibility(View.VISIBLE);
        save2.setVisibility(View.VISIBLE);

        iv_eye1.setImageResource(R.drawable.ic_open_eye);
        iv_eye1.setTag("open_eye");

        iv_eye1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String current_tag = (String) iv_eye1.getTag();

                if ("open_eye".equals(current_tag)){

                    iv_eye1.setImageResource(R.drawable.ic_eye_close);
                    iv_eye1.setTag("close_eye");
                    ed_autocomplete_textview1.setVisibility(View.GONE);
                    save1.setVisibility(View.GONE);
                    iv_delete1.setVisibility(View.GONE);



                } else if ("close_eye".equals(current_tag)) {

                    iv_eye1.setImageResource(R.drawable.ic_open_eye);
                    iv_eye1.setTag("open_eye");
                    ed_autocomplete_textview1.setVisibility(View.VISIBLE);
                    save1.setVisibility(View.VISIBLE);
                    iv_delete1.setVisibility(View.VISIBLE);

                }

            }
        });

        iv_eye2.setImageResource(R.drawable.ic_open_eye);
        iv_eye2.setTag("open_eye");

        iv_eye2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String current_tag = (String) iv_eye2.getTag();

                if ("open_eye".equals(current_tag)){

                    iv_eye2.setImageResource(R.drawable.ic_eye_close);
                    iv_eye2.setTag("close_eye");
                    ed_autocomplete_textview2.setVisibility(View.GONE);
                    save2.setVisibility(View.GONE);
                    iv_delete2.setVisibility(View.GONE);



                } else if ("close_eye".equals(current_tag)) {

                    iv_eye2.setImageResource(R.drawable.ic_open_eye);
                    iv_eye2.setTag("open_eye");
                    ed_autocomplete_textview2.setVisibility(View.VISIBLE);
                    save2.setVisibility(View.VISIBLE);
                    iv_delete2.setVisibility(View.VISIBLE);

                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Act_Custom_Mala.this, Act_Home_All_Mala.class));

            }
        });





        //1st step started++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count++;

                if (count > 0 && count < 109){

                    text1.setText(banglaNumber[count - 1]);

                }

                vibrate();
            }
        });


        reset1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count = 0;

                text1.setText(zero[count - 0]);

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

                    text2.setText(banglaNumber[i - 1]);

                }
                vibrate();

            }
        });



        reset2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i = 0;

                text2.setText(zero[i - 0]);

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

                    text3.setText(banglaNumber[j - 1]);

                }
                vibrate();

            }
        });

        reset3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                j = 0;

                text3.setText(zero[j - 0]);

                vibrate();

            }
        });
        //3rd step ended++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


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

        //delete_button1----------------------
        iv_delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                save_text1 = getSharedPreferences("text_save1", MODE_PRIVATE);
                SharedPreferences.Editor editor = save_text1.edit();
                editor.clear();
                editor.apply();

                tv_mantras1.setText("জপ মন্ত্র");

            }
        });

        //delete_button1----------------------
        iv_delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                save_text2 = getSharedPreferences("text_save2", MODE_PRIVATE);
                SharedPreferences.Editor editor = save_text2.edit();
                editor.clear();
                editor.apply();

                tv_mantras2.setText("জপ মন্ত্র");

            }
        });






    }//on create====================================


    //vibration--------------------------
    private void vibrate(){                     //vibrate

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        vibrator.vibrate(50);



    }


    //save--------------------------
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
                    ed_autocomplete_textview1.setText("");

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
                    ed_autocomplete_textview2.setText("");

                }else {

                    tv_mantras2.setText("জপ মন্ত্র");


                }

            }
        });

    }

    private void load_img_form_storage(){


        try {

            FileInputStream fileInputStream = openFileInput("saved_image.png");

           Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
           iv_upload_image.setImageBitmap(bitmap);
           fileInputStream.close();

        }catch (Exception e){

            e.printStackTrace();


        }

    }


    private void save_img_to_internal_storage(Bitmap bitmap){

        try {

            File file = new File(getFilesDir(),"saved_image.png");
            if (file.exists()){
                file.delete();

            }


            //file created-----
            FileOutputStream fos = openFileOutput("saved_image.png", MODE_PRIVATE);

            //image compressed----
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();




        }catch (Exception e){

            e.printStackTrace();
            Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();

        }

    }



    private void delete_img(){

        File file = new File(getFilesDir(),"saved_image.png");

        if (file.exists()){

            if (file.delete()){

                iv_upload_image.setImageDrawable(null);
                iv_upload_image.setImageResource(R.drawable.img_gallery);

            }
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMG_PICK && resultCode == RESULT_OK && data != null){

            try {

                Uri img_uri = data.getData();

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(Act_Custom_Mala.this.getContentResolver(), img_uri);

                iv_upload_image.setImageBitmap(bitmap);

                save_img_to_internal_storage(bitmap);

            } catch (Exception e) {

                e.printStackTrace();
            }

        }

    }


}//public class ===============================