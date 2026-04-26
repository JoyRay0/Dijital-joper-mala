package com.mala.digital_joper_mala.View;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.mala.digital_joper_mala.Database.History;
import com.mala.digital_joper_mala.R;
import com.mala.digital_joper_mala.Helper.CounterHelper;
import com.mala.digital_joper_mala.Helper.ImageUploadHelper;


public class Act_Custom_Mala extends AppCompatActivity {

    //XML id's--------------------------------------------------

    private AppCompatAutoCompleteTextView ed_autocomplete_textview1, ed_autocomplete_textview2, ed_autocomplete_textview3, ed_autocomplete_textview4;

    private AppCompatTextView tv_mantras1, tv_mantras2, tv_mantras3, tv_mantras4;

    private TextView text1,text2,text3;

    private AppCompatButton add1, add2, add3, reset1, reset2, reset3;

    private AppCompatImageView iv_upload_button, iv_delete_button;

    private AppCompatImageView save1, save2, save3, save4 ,iv_eye1, iv_eye2, iv_eye3, iv_eye4, iv_upload_image, iv_delete1, iv_delete2, iv_delete3, iv_delete4;

    Toolbar toolbar;

    private ImageButton back;

    private CardView cd_save_count1, cd_save_count2, cd_save_count3;

    //other
    private History historyDB;
    private ImageUploadHelper uploadHelper;
    private CounterHelper counterHelper;

    SharedPreferences sharedPreferences, save_text1, save_text2, save_text3, save_text4;

    //XML id's--------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_custom_mala);

        init();

        savedMantra();

        save();

        eye();

        deleteMantra();

        countMantra();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        uploadHelper.imageButtons();
        uploadHelper.loadImage();


    }//on create====================================================================

    private void init(){

        ed_autocomplete_textview1 = findViewById(R.id.ed_autocomplete_textview1);
        ed_autocomplete_textview2 = findViewById(R.id.ed_autocomplete_textview2);
        ed_autocomplete_textview3 = findViewById(R.id.ed_autocomplete_textview3);
        ed_autocomplete_textview4 = findViewById(R.id.ed_autocomplete_textview4);

        tv_mantras1 = findViewById(R.id.tv_mantras1);
        tv_mantras2 = findViewById(R.id.tv_mantras2);
        tv_mantras3 = findViewById(R.id.tv_mantras3);
        tv_mantras4 = findViewById(R.id.tv_mantras4);

        iv_eye1 = findViewById(R.id.iv_eye1);
        iv_eye2 = findViewById(R.id.iv_eye2);
        iv_eye3 = findViewById(R.id.iv_eye3);
        iv_eye4 = findViewById(R.id.iv_eye4);

        save1 = findViewById(R.id.save1);
        save2 = findViewById(R.id.save2);
        save3 = findViewById(R.id.save3);
        save4 = findViewById(R.id.save4);

        iv_delete1 = findViewById(R.id.iv_delete1);
        iv_delete2 = findViewById(R.id.iv_delete2);
        iv_delete3 = findViewById(R.id.iv_delete3);
        iv_delete4 = findViewById(R.id.iv_delete4);

        toolbar = findViewById(R.id.toolbar);
        back = findViewById(R.id.back);

        add1 = findViewById(R.id.add1);
        add2 = findViewById(R.id.add2);
        add3 = findViewById(R.id.add3);
        reset1 = findViewById(R.id.reset1);
        reset2 = findViewById(R.id.reset2);
        reset3 = findViewById(R.id.reset3);
        text1 = findViewById(R.id.tv_count_display1);
        text2 = findViewById(R.id.tv_count_display2);
        text3 = findViewById(R.id.tv_count_display3);

        iv_upload_image = findViewById(R.id.iv_upload_image);
        iv_upload_button = findViewById(R.id.iv_upload_button);
        iv_delete_button = findViewById(R.id.iv_delete_button);

        cd_save_count1 = findViewById(R.id.cd_save_count1);
        cd_save_count2 = findViewById(R.id.cd_save_count2);
        cd_save_count3 = findViewById(R.id.cd_save_count3);

        historyDB = new History(this);
        uploadHelper = new ImageUploadHelper(Act_Custom_Mala.this, "custom.png", iv_upload_image, iv_upload_button, iv_delete_button);

        counterHelper = new CounterHelper(this, text1, text2, text3);

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

        save3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ed_mantra3 = ed_autocomplete_textview3.getText().toString();

                if (ed_mantra3.length() > 0){

                    SharedPreferences.Editor editor3 = save_text3.edit();
                    editor3.putString("ed_saved3",ed_mantra3);
                    editor3.apply();

                    tv_mantras3.setText("‘‘"+ed_mantra3+"’’");
                    ed_autocomplete_textview3.setText("");

                }else {

                    tv_mantras3.setText("জপ মন্ত্র");

                }

            }
        });


        save4.setOnClickListener(new View.OnClickListener() {   //save button2
            @Override
            public void onClick(View view) {

                String ed_mantra4 = ed_autocomplete_textview4.getText().toString();

                if (ed_mantra4.length() > 0){

                    SharedPreferences.Editor editor4 = save_text4.edit();
                    editor4.putString("ed_saved4",ed_mantra4);
                    editor4.apply();

                    tv_mantras4.setText("‘‘"+ed_mantra4+"’’");
                    ed_autocomplete_textview4.setText("");

                }else {

                    tv_mantras4.setText("জপ মন্ত্র");


                }

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uploadHelper.ActivityResult(requestCode, resultCode, data);

    }

    //eye button-----------------------------------------
    private void eye(){

        ed_autocomplete_textview1.setVisibility(View.VISIBLE);
        ed_autocomplete_textview2.setVisibility(View.VISIBLE);
        ed_autocomplete_textview3.setVisibility(View.VISIBLE);
        ed_autocomplete_textview4.setVisibility(View.VISIBLE);

        save1.setVisibility(View.VISIBLE);
        save2.setVisibility(View.VISIBLE);
        save3.setVisibility(View.VISIBLE);
        save4.setVisibility(View.VISIBLE);

        // tags---------------------------------
        iv_eye1.setImageResource(R.drawable.ic_open_eye);
        iv_eye1.setTag("open_eye");

        iv_eye2.setImageResource(R.drawable.ic_open_eye);
        iv_eye2.setTag("open_eye");

        iv_eye3.setImageResource(R.drawable.ic_open_eye);
        iv_eye3.setTag("open_eye");

        iv_eye4.setImageResource(R.drawable.ic_open_eye);
        iv_eye4.setTag("open_eye");

        // tags---------------------------------

        //eye button--------------------------------
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

        iv_eye3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String current_tag = (String) iv_eye3.getTag();

                if ("open_eye".equals(current_tag)){

                    iv_eye3.setImageResource(R.drawable.ic_eye_close);
                    iv_eye3.setTag("close_eye");
                    ed_autocomplete_textview3.setVisibility(View.GONE);
                    save3.setVisibility(View.GONE);
                    iv_delete3.setVisibility(View.GONE);

                } else if ("close_eye".equals(current_tag)) {

                    iv_eye3.setImageResource(R.drawable.ic_open_eye);
                    iv_eye3.setTag("open_eye");
                    ed_autocomplete_textview3.setVisibility(View.VISIBLE);
                    save3.setVisibility(View.VISIBLE);
                    iv_delete3.setVisibility(View.VISIBLE);

                }

            }
        });

        iv_eye4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String current_tag = (String) iv_eye4.getTag();

                if ("open_eye".equals(current_tag)){

                    iv_eye4.setImageResource(R.drawable.ic_eye_close);
                    iv_eye4.setTag("close_eye");
                    ed_autocomplete_textview4.setVisibility(View.GONE);
                    save4.setVisibility(View.GONE);
                    iv_delete4.setVisibility(View.GONE);

                } else if ("close_eye".equals(current_tag)) {

                    iv_eye4.setImageResource(R.drawable.ic_open_eye);
                    iv_eye4.setTag("open_eye");
                    ed_autocomplete_textview4.setVisibility(View.VISIBLE);
                    save4.setVisibility(View.VISIBLE);
                    iv_delete4.setVisibility(View.VISIBLE);

                }

            }
        });
        //eye button--------------------------------
    }

    //delete mantra----------------------------------------------
    private void deleteMantra(){

        //delete_button----------------------
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

        iv_delete3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                save_text3 = getSharedPreferences("text_save3", MODE_PRIVATE);
                SharedPreferences.Editor editor = save_text3.edit();
                editor.clear();
                editor.apply();

                tv_mantras3.setText("জপ মন্ত্র");

            }
        });

        iv_delete4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                save_text4 = getSharedPreferences("text_save4", MODE_PRIVATE);
                SharedPreferences.Editor editor = save_text4.edit();
                editor.clear();
                editor.apply();

                tv_mantras4.setText("জপ মন্ত্র");

            }
        });
        //delete_button----------------------

    }

    //mantra counter-----------------------------------------------------------------
    private void countMantra(){

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

    //show saved mantra---------------------------------------------------------------
    private void savedMantra(){

        save_text1 = getSharedPreferences("text_save1",MODE_PRIVATE);
        String saved_text1 = save_text1.getString("ed_saved1","");

        save_text2 = getSharedPreferences("text_save2",MODE_PRIVATE);
        String saved_text2 = save_text2.getString("ed_saved2","");

        save_text3 = getSharedPreferences("text_save3",MODE_PRIVATE);
        String saved_text3 = save_text3.getString("ed_saved3","");

        save_text4 = getSharedPreferences("text_save4",MODE_PRIVATE);
        String saved_text4 = save_text4.getString("ed_saved4","");

        tv_mantras1.setText("‘‘ "+saved_text1+" ’’");
        tv_mantras2.setText("‘‘ "+saved_text2+" ’’");

        tv_mantras3.setText("‘‘ "+saved_text3+" ’’");
        tv_mantras4.setText("‘‘ "+saved_text4+" ’’");

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
}//public class ===============================