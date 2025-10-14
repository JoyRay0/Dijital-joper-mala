package com.mala.digital_joper_mala.Activity;


import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;

import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mala.digital_joper_mala.Database.History;
import com.mala.digital_joper_mala.Database.Mantra;
import com.mala.digital_joper_mala.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class Act_Boisnob_mala extends AppCompatActivity {


    // XML id's----------------------------------------------------------------



    private TextView tv_count_display1, tv_count_display2,tv_count_display3;

    private AppCompatButton firstpage,secondpage;

    private AppCompatButton add1, add2, add3, reset1, reset2, reset3;

    //private Button less2, less3;

    private ImageButton back;

    private AppCompatImageButton iv_upload_button, iv_delete_button, ib_rules;

    private AppCompatImageView  iv_upload_image;

    private CardView cd_save_count1, cd_save_count2, cd_save_count3;

    private Vibrator vibrator;

    private History historyDB;

    String firstCount = "";
    String secondCount = "";
    String thirdCount = "";

    SharedPreferences sharedPreferences;
    boolean nightMode;
    
    //initial value********************************************
    private int count = 0, i = 0, j = 0;
    //initial value********************************************

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

    // XML id's----------------------------------------------------------------

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_boisnob_mala);

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

        back = findViewById(R.id.back);

        iv_upload_button = findViewById(R.id.iv_upload_button);
        iv_delete_button = findViewById(R.id.iv_delete_button);
        iv_upload_image = findViewById(R.id.iv_upload_image);

        ib_rules = findViewById(R.id.ib_rules);

        cd_save_count1 = findViewById(R.id.cd_save_count1);
        cd_save_count2 = findViewById(R.id.cd_save_count2);
        cd_save_count3 = findViewById(R.id.cd_save_count3);

        //Identity period end+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        historyDB = new History(this);

        //back button-----------------------------------------------
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Act_Boisnob_mala.this, Act_Home_All_Mala.class));

            }
        });
        //back button-----------------------------------------------

        //custom floating button--------------------------------

        ib_rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Act_Boisnob_mala.this, Act_boisnob_mala_helper.class));

            }
        });

        //custom floating button--------------------------------


       counter();

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


        //back-----------------------------------------------------------------
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                startActivity(new Intent(Act_Boisnob_mala.this, Act_Home_All_Mala.class));
                finishAffinity();

            }
        });
        //back-----------------------------------------------------------------

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

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(Act_Boisnob_mala.this.getContentResolver(), img_uri);

                iv_upload_image.setImageBitmap(bitmap);

                save_img_to_internal_storage(bitmap);

            } catch (Exception e) {

                e.printStackTrace();
            }

        }

    }
    //image methods=======================================

    //counter ---------------------------------------------------------
    private void counter(){

        cd_save_count1.setOnClickListener(view -> {

            count_save(firstCount);

        });

        cd_save_count2.setOnClickListener(view -> {

            count_save(secondCount);

        });

        cd_save_count3.setOnClickListener(view -> {

            count_save(thirdCount);

        });

        //1st step started++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count++;


                if (count > 0 && count < 109){

                    tv_count_display1.setText(banglaNumber[count - 1]);

                    firstCount = tv_count_display1.getText().toString();

                }

                vibrate();
            }
        });

        reset1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count = 0;

                tv_count_display1.setText(zero[count - 0]);
                firstCount = tv_count_display1.getText().toString();

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

                    tv_count_display2.setText(banglaNumber[i - 1]);
                    secondCount = tv_count_display2.getText().toString();

                }
                vibrate();

            }
        });

        reset2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i = 0;

                tv_count_display2.setText(zero[i - 0]);
                secondCount = tv_count_display2.getText().toString();

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

                    tv_count_display3.setText(banglaNumber[j - 1]);
                    thirdCount = tv_count_display3.getText().toString();

                }
                vibrate();

            }
        });

        reset3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                j = 0;

                tv_count_display3.setText(zero[j -0]);
                thirdCount = tv_count_display3.getText().toString();

                vibrate();

            }
        });
        //3rd step ended++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    }

    //jopa count save-----------------------------------------------------------------
    private void count_save(String count){

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.lay_japa_history_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        AppCompatEditText ed_note = dialog.findViewById(R.id.ed_note);
        AppCompatEditText ed_count = dialog.findViewById(R.id.ed_count);
        CardView cd_close = dialog.findViewById(R.id.cd_close);
        CardView cd_save = dialog.findViewById(R.id.cd_save);

        ed_note.requestFocus();
        ed_count.setText(count);

        cd_close.setOnClickListener(view -> {

            dialog.dismiss();

        });

        cd_save.setOnClickListener(view -> {

            String note = ed_note.getText().toString().trim();

            if (note == null || note.isEmpty()){

                ed_note.setError("দিতে হবে");

            } else if (count == null || count.isEmpty()) {

                ed_count.setError("দিতে হবে");

            } else {

                historyDB.insert(note, count);

                Toast.makeText(this, "সেভ হয়েছে।", Toast.LENGTH_SHORT).show();

                dialog.dismiss();

            }

        });

        dialog.show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        historyDB.CloseDB();
    }
}//public class ===========================