package com.mala.digital_joper_mala;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

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


import java.util.ArrayList;
import java.util.HashMap;


public class Fragment_all_mala extends Fragment {

    //XML id's-------------------------------------------------

    private FrameLayout anim_fab;

    private GridView all_mala_gridview;

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    HashMap<String, String> hashMap;

    private static final String appPackageName = "com.mala.digital_joper_mala";

    //XML id's-------------------------------------------------

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragement_all_mala, container, false);

        //Identity period --------------------------------------------


        anim_fab = view.findViewById(R.id.anim_fab);
        all_mala_gridview = view.findViewById(R.id.all_mala_gridview);


        //Identity period --------------------------------------------

        hashmap();
        Myadapter myadapter = new Myadapter();
        all_mala_gridview.setAdapter(myadapter);
        fab_button();



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

        /*
        hashMap = new HashMap<>();
        hashMap.put("name_mala","শিব মালা");
        arrayList.add(hashMap);

         */

    }
    //hashmap----------------------------------------

    //adapter class -----------------------------------------------------------
    private class Myadapter extends BaseAdapter {


        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = getLayoutInflater();
            @SuppressLint("ViewHolder") View view1 =  layoutInflater.inflate(R.layout.desgin_for_all_mala_home, viewGroup,false);

            CardView cardview = view1.findViewById(R.id.cardview);
            AppCompatTextView all_mala_name_textview = view1.findViewById(R.id.all_mala_name_textview);
            AppCompatImageView all_mala_imageview = view1.findViewById(R.id.all_mala_imageview);


            HashMap<String, String> hashMap1 = arrayList.get(position);

            //hashmap to string
            String text_item = hashMap1.get("name_mala");

            all_mala_name_textview.setText(text_item);



            cardview.setOnClickListener(new View.OnClickListener() { //mala button
                @Override
                public void onClick(View view) {

                    if (position == 0){

                        startActivity(new Intent(getActivity(), Custom_MalaActivity.class));

                    } else if (position == 1) {

                        startActivity(new Intent(getActivity(), Boisnob_mala.class));

                    } else if (position == 2) {

                        startActivity(new Intent(getActivity(), ShivMala.class));

                    }

                }
            });


            return view1;
        }
    }
    //adapter class -----------------------------------------------------------

    //fab button----------------------------------------------

    private void fab_button(){

        anim_fab.setOnClickListener(view -> {

            Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.bangla_date);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.show();

            AppCompatTextView banglaDate_tv = dialog.findViewById(R.id.banglaDate_tv);

            String bangla_full_date = BanglaDateUtils.getBanglafullDate();
            String week = BanglaDateUtils.getWeekDay();
            String day = BanglaDateUtils.getBanglaDay();
            String month = BanglaDateUtils.getBanglaMonth();
            String season = BanglaDateUtils.getBanglaSeason();

            banglaDate_tv.setText("তারিখ: "+bangla_full_date+"\nবার: "+week);



        });

    }

    //fab button----------------------------------------------

}//public class===========================