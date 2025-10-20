package com.mala.digital_joper_mala.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;


import com.mala.digital_joper_mala.Adapter.Mala;
import com.mala.digital_joper_mala.Adapter.UserShowMala;
import com.mala.digital_joper_mala.Database.UserMala;
import com.mala.digital_joper_mala.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Fg_all_mala extends Fragment {

    //XML id's-------------------------------------------------

    private FrameLayout anim_fab;

    private GridView all_mala_gridview;

    private RecyclerView rv_user_mala;

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    HashMap<String, String> hashMap;

    private List<HashMap<String, String>> s_list = new ArrayList<>();

    private static final String appPackageName = "com.mala.digital_joper_mala";


    //other
    private UserShowMala showMala;
    private UserMala userMala;

    //XML id's-------------------------------------------------

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fg_all_mala, container, false);

        //Identity period --------------------------------------------

        //anim_fab = view.findViewById(R.id.anim_fab);
        all_mala_gridview = view.findViewById(R.id.all_mala_gridview);
        rv_user_mala = view.findViewById(R.id.rv_user_mala);

        //Identity period --------------------------------------------

        hashmap();

        Mala mala = new Mala(requireActivity(), arrayList);
        all_mala_gridview.setAdapter(mala);
        //fab_button();

        showMala = new UserShowMala(requireActivity(), s_list);
        rv_user_mala.setAdapter(showMala);

        userMala = new UserMala(requireActivity());

        s_list.addAll(userMala.getAll());


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