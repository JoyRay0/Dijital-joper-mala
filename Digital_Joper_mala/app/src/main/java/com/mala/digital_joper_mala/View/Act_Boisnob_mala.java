package com.mala.digital_joper_mala.View;


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
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mala.digital_joper_mala.Database.History;
import com.mala.digital_joper_mala.R;
import com.mala.digital_joper_mala.Helper.CounterHelper;
import com.mala.digital_joper_mala.Helper.ImageUploadHelper;


public class Act_Boisnob_mala extends AppCompatActivity {


    // XML id's----------------------------------------------------------------



    private TextView tv_count_display1, tv_count_display2,tv_count_display3;

    private AppCompatButton add1, add2, add3, reset1, reset2, reset3;

    //private Button less2, less3;

    private ImageButton back;

    private AppCompatImageButton ib_rules;

    private AppCompatImageView iv_upload_button, iv_delete_button;

    private AppCompatImageView  iv_upload_image;

    private CardView cd_save_count1, cd_save_count2, cd_save_count3;

    private History historyDB;

    private ImageUploadHelper uploadHelper;

    private CounterHelper counterHelper;

    // XML id's----------------------------------------------------------------

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_boisnob_mala);

        init();

        //back button-----------------------------------------------
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

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

       uploadHelper.imageButtons();
       uploadHelper.loadImage();


    }//on create===================================================================

    private void init(){

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

        historyDB = new History(this);
        uploadHelper = new ImageUploadHelper(Act_Boisnob_mala.this, "rk.png", iv_upload_image, iv_upload_button, iv_delete_button);

        counterHelper = new CounterHelper(this, tv_count_display1, tv_count_display2, tv_count_display3);


    }

    //image methods===================================================

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uploadHelper.ActivityResult(requestCode, resultCode, data);

    }
    //image methods=======================================

    //counter ---------------------------------------------------------
    private void counter(){

        counterHelper.setFirstCount(add1, reset1);
        counterHelper.setSecondCount(add2, reset2);
        counterHelper.setThirdCount(add3, reset3);

        cd_save_count1.setOnClickListener(view -> {

            count_save(counterHelper.getFirstCount());

        });

        cd_save_count2.setOnClickListener(view -> {

            count_save(counterHelper.getSecondCount());

        });

        cd_save_count3.setOnClickListener(view -> {

            count_save(counterHelper.getThirdCount());

        });

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