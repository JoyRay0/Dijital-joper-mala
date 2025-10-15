package com.mala.digital_joper_mala.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;


import com.mala.digital_joper_mala.Adapter.Mala;
import com.mala.digital_joper_mala.Utils.BanglaDateUtils;
import com.mala.digital_joper_mala.R;

import java.util.ArrayList;
import java.util.HashMap;


public class Fg_all_mala extends Fragment {

    //XML id's-------------------------------------------------

    private FrameLayout anim_fab;

    private GridView all_mala_gridview;

    private AppCompatImageView iv_mantras;

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    HashMap<String, String> hashMap;

    private static final String appPackageName = "com.mala.digital_joper_mala";

    //XML id's-------------------------------------------------

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fg_all_mala, container, false);

        //Identity period --------------------------------------------

        //anim_fab = view.findViewById(R.id.anim_fab);
        all_mala_gridview = view.findViewById(R.id.all_mala_gridview);
        iv_mantras = view.findViewById(R.id.iv_mantras);

        //Identity period --------------------------------------------

        hashmap();
        //Myadapter myadapter = new Myadapter();
        Mala mala = new Mala(requireActivity(), arrayList);
        all_mala_gridview.setAdapter(mala);
        //fab_button();

        iv_mantras.setOnClickListener(view1 -> {

            startActivity(new Intent(requireContext(), Act_all_mantra.class));

        });



        return view;
    }//on create=================================



   //hashmap----------------------------------------
    private void hashmap(){

        hashMap = new HashMap<>();
        hashMap.put("name_mala","মালা");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name_mala","বৈষ্ণব মালা");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name_mala","শিব মালা");
        arrayList.add(hashMap);



    }

}//public class===========================