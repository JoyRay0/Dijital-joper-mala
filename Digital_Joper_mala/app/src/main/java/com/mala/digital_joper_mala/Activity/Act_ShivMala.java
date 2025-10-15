package com.mala.digital_joper_mala.Activity;

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

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;

import com.mala.digital_joper_mala.Database.History;
import com.mala.digital_joper_mala.R;
import com.mala.digital_joper_mala.Utils.ImageUploadHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Act_ShivMala extends AppCompatActivity {

    //XML id's-----------------------------------------------------

    private TextView tv_count_display1, tv_count_display2,tv_count_display3;

    private AppCompatButton add1, add2, add3, reset1, reset2, reset3;

    private ImageButton back;

    private Vibrator vibrator;

    private CardView cd_save_count1, cd_save_count2, cd_save_count3;

    private AppCompatImageButton iv_upload_button, iv_delete_button;

    private AppCompatImageView iv_upload_image;

    private static final int REQUEST_IMG_PICK = 1;

    private ImageUploadHelper uploadHelper;

    //initial value********************************************

    private int count = 0, i = 0, j = 0;

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

    String firstCount = "";
    String secondCount = "";
    String thirdCount = "";

    //initial value********************************************

    //other
    private History historyDB;

    //XML id's-----------------------------------------------------

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_shiv_mala);

        //identity period---------------------------------------
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

        cd_save_count1 = findViewById(R.id.cd_save_count1);
        cd_save_count2 = findViewById(R.id.cd_save_count2);
        cd_save_count3 = findViewById(R.id.cd_save_count3);

        //identity period---------------------------------------

        historyDB = new History(this);
        uploadHelper = new ImageUploadHelper(Act_ShivMala.this, "shiv_image.png", iv_upload_image , iv_upload_button, iv_delete_button);

        //back button-----------------------------------------------
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Act_ShivMala.this, Act_Home_All_Mala.class));
                finishAffinity();

            }
        });
        //back button-----------------------------------------------

        counter();

        uploadHelper.loadImage();
        uploadHelper.imageButtons();

        //back-----------------------------------------------------
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(Act_ShivMala.this, Act_Home_All_Mala.class));
                finishAffinity();
            }
        });
        //back-----------------------------------------------------

    }//on create===========================================


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

                tv_count_display3.setText(zero[j - 0]);
                thirdCount = tv_count_display3.getText().toString();

                vibrate();

            }
        });
        //3rd step ended++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    }

    //vibrator-------------------------------------------------
    private void vibrate(){                     //vibrate

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(50);
    }
    //vibrator-------------------------------------------------

    //image methods===================================================

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uploadHelper.ActivityResult(requestCode, resultCode, data);

    }
    //image methods=======================================

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

                Toast.makeText(this, "সেভ হয়েছে।", Toast.LENGTH_SHORT).show();

                addDataToDatabase(note, count);

                dialog.dismiss();

            }

        });

        dialog.show();

    }

    //data added to database---------------------------------------------------------
    private void addDataToDatabase(String titles, String counts){

        historyDB.insert(titles, counts);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        historyDB.CloseDB();
    }

}//public class =============================================