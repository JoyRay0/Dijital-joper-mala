package com.mala.digital_joper_mala.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.mala.digital_joper_mala.Adapter.UserShowMala;
import com.mala.digital_joper_mala.Database.History;
import com.mala.digital_joper_mala.Database.UserMala;
import com.mala.digital_joper_mala.R;
import com.mala.digital_joper_mala.Utils.CounterHelper;
import com.mala.digital_joper_mala.Utils.ImageUploadHelper;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

public class Fg_show_user_mala extends Fragment {

    //XML id's-------------------------------------------------------------

    //in
    private AppCompatTextView tv_mantra1, tv_mantra2, tv_mantra3, tv_mantra4;
    private AppCompatTextView tv_save_mantras1, tv_save_mantras2, tv_save_mantras3, tv_save_mantras4;
    private AppCompatImageView iv_upload_button, iv_delete_button;
    private AppCompatImageView iv_upload_image;

    private FrameLayout fl_add_image;

    private AppCompatButton add1, reset1, add2, reset2, add3, reset3;
    private AppCompatTextView tv_count_display1, tv_count_display2, tv_count_display3;

    private CardView cd_save_count1, cd_save_count2, cd_save_count3;

    //other
    private UserMala userMala;
    private ImageUploadHelper uploadHelper;
    private CounterHelper counterHelper;
    private History historyDB;

    //XML id's-------------------------------------------------------------

    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fg_show_user_mala, container, false);

        //identity period-------------------------------------------------

        tv_mantra1 = view.findViewById(R.id.tv_mantra1);
        tv_mantra2 = view.findViewById(R.id.tv_mantra2);
        tv_mantra3 = view.findViewById(R.id.tv_mantra3);
        tv_mantra4 = view.findViewById(R.id.tv_mantra4);

        tv_save_mantras1 = view.findViewById(R.id.tv_save_mantras1);
        tv_save_mantras2 = view.findViewById(R.id.tv_save_mantras2);
        tv_save_mantras3 = view.findViewById(R.id.tv_save_mantras3);
        tv_save_mantras4 = view.findViewById(R.id.tv_save_mantras4);

        iv_upload_button = view.findViewById(R.id.iv_upload_button);
        iv_delete_button = view.findViewById(R.id.iv_delete_button);
        iv_upload_image = view.findViewById(R.id.iv_upload_image);
        fl_add_image = view.findViewById(R.id.fl_add_image);

        add1 = view.findViewById(R.id.add1);
        reset1 = view.findViewById(R.id.reset1);
        add2 = view.findViewById(R.id.add2);
        reset2 = view.findViewById(R.id.reset2);
        add3 = view.findViewById(R.id.add3);
        reset3 = view.findViewById(R.id.reset3);

        tv_count_display1 = view.findViewById(R.id.tv_count_display1);
        tv_count_display2 = view.findViewById(R.id.tv_count_display2);
        tv_count_display3 = view.findViewById(R.id.tv_count_display3);

        cd_save_count1 = view.findViewById(R.id.cd_save_count1);
        cd_save_count2 = view.findViewById(R.id.cd_save_count2);
        cd_save_count3 = view.findViewById(R.id.cd_save_count3);

        //identity period-------------------------------------------------

        String name = Act_User_mala.MALA_NAME;
        fl_add_image.setVisibility(View.GONE);

        counterHelper = new CounterHelper(requireActivity(), tv_count_display1, tv_count_display2, tv_count_display3);

        historyDB = new History(requireActivity());

        uploadHelper = new ImageUploadHelper(requireActivity(), "user_mala.png", iv_upload_image, iv_upload_button, iv_delete_button);
        uploadHelper.imageButtons();
        uploadHelper.loadImage();

        userMala = new UserMala(requireActivity());
        List<HashMap<String, String>> user_mala_list = userMala.getSearchItem(name);

        if (user_mala_list == null || user_mala_list.isEmpty()){

            tv_mantra1.setVisibility(View.GONE);
            tv_save_mantras1.setVisibility(View.GONE);
            tv_mantra2.setVisibility(View.GONE);
            tv_save_mantras2.setVisibility(View.GONE);
            tv_mantra3.setVisibility(View.GONE);
            tv_save_mantras3.setVisibility(View.GONE);
            tv_mantra4.setVisibility(View.GONE);
            tv_save_mantras4.setVisibility(View.GONE);

        }else {

            HashMap<String, String> s_map = user_mala_list.get(0);

            String fi_mantra = s_map.get("mantra1");
            String se_mantra = s_map.get("mantra2");
            String th_mantra = s_map.get("mantra3");
            String fo_mantra = s_map.get("mantra4");
            //String image = s_map.get("image");

            showMatra(tv_mantra1, tv_save_mantras1, fi_mantra);
            showMatra(tv_mantra2, tv_save_mantras2, se_mantra);
            showMatra(tv_mantra3, tv_save_mantras3, th_mantra);
            showMatra(tv_mantra4, tv_save_mantras4, fo_mantra);

            Uri uri = Uri.parse("content://media/external/file/20748");
            try {
                InputStream inputStream = requireActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                iv_upload_image.setImageBitmap(bitmap);
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        counter();

        return view;
    }// on create===========================================================

    //show mantra-----------------------------------------------------------
    private void showMatra(AppCompatTextView tv_mantra, AppCompatTextView tv_save_mantras, String mantra){


        if (mantra == null || mantra.trim().isEmpty()){

            tv_mantra.setVisibility(View.GONE);
            tv_save_mantras.setVisibility(View.GONE);

        }else {

            tv_mantra.setVisibility(View.VISIBLE);
            tv_save_mantras.setVisibility(View.VISIBLE);
            tv_save_mantras.setText("“" +mantra+ "”");

        }


    }

    //counter-----------------------------------------------------
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

        Dialog dialog = new Dialog(requireActivity());
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

                Toast.makeText(requireActivity(), "সেভ হয়েছে।", Toast.LENGTH_SHORT).show();

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
    public void onDestroy() {
        super.onDestroy();
        historyDB.CloseDB();
        userMala.closeDB();
    }
}// public class==============================================================