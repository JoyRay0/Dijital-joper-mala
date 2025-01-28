package com.mala.digital_joper_mala;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;


import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity3 extends AppCompatActivity {

    //XML id's-----------------------------------------------------

    ImageButton mback;

    Boolean nightMode;
    SharedPreferences sharedPreferences;

    ListView listView;



    ArrayList <HashMap<String, String>> arrayList = new ArrayList<>();

    HashMap<String, String> hashMap;

    private static final String PREF_NAME = "dark_light_mode";
    private static final String KEY_NAME = "dark_mode";






    //XML id's-----------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //dark----------------------------------------------------------------
        sharedPreferences = getSharedPreferences(PREF_NAME,MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean(KEY_NAME, false);


        AppCompatDelegate.setDefaultNightMode(nightMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        //dark----------------------------------------------------------------
        setContentView(R.layout.activity_main3);

        //identity period------------------------------------------

        mback = findViewById(R.id.back);
        listView = findViewById(R.id.listview);


        hashmap();

        //identity period------------------------------------------


        //Adapter started-----------------------------------------------------

        myAdapter mylistview = new myAdapter();
        listView.setAdapter(mylistview);

        //Adapter started-----------------------------------------------------







        //starting point----------------------------------------------------------


        if (nightMode){

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }


        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myintent = new Intent(MainActivity3.this, Boisnob_mala.class);
                startActivity(myintent);

            }
        });

        //ending point-----------------------------------------------------------



    }//on create bundle ended

    private class myAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ViewHolder") View view1 = layoutInflater.inflate(R.layout.design_of_listview, viewGroup, false);

            HashMap<String, String> hashMap1 = arrayList.get(i);


            AppCompatImageView imageview = view1.findViewById(R.id.imageview);
            CardView ans_cardview = view1.findViewById(R.id.ans_cardview);
            CardView clicked = view1.findViewById(R.id.clicked);
            AppCompatTextView ques_text = view1.findViewById(R.id.ques_text);
            AppCompatTextView tvDisplay = view1.findViewById(R.id.tvDisplay);



            String string = hashMap1.get("ques1");
            ques_text.setText(string);




            clicked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //code here-----

                    String string1 = hashMap1.get("ans1");



                    if (ans_cardview.getVisibility() == View.GONE){


                        tvDisplay.setText(string1);
                        //TransitionManager.beginDelayedTransition(mother_layout, new AutoTransition());
                        ans_cardview.setVisibility(View.VISIBLE);
                        imageview.setImageResource(R.drawable.baseline_keyboard_arrow_up_24);

                    }else {

                        tvDisplay.setText(string1);
                        //TransitionManager.beginDelayedTransition(mother_layout, new AutoTransition());
                        ans_cardview.setVisibility(View.GONE);
                        imageview.setImageResource(R.drawable.baseline_keyboard_arrow_down_24);

                    }

                }
            });



            return view1;
        }
    }


    private void hashmap(){


        String ques1 = getString(R.string.question1);
        String ques2 = getString(R.string.question2);

        String ques4 = getString(R.string.question4);
        String ques5 = getString(R.string.question5);
        String ques6 = getString(R.string.question6);

        String ans1 = getString(R.string.Answer1);
        String ans2 = getString(R.string.Answer2);

        String ans4 = getString(R.string.Answer4);
        String ans5 = getString(R.string.Answer5);
        String ans6 = getString(R.string.Answer6);


        hashMap = new HashMap<>();
        hashMap.put("ques1", ques1);
        hashMap.put("ans1",ans1);
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("ques1",ques2);
        hashMap.put("ans1",ans2);
        arrayList.add(hashMap);



        hashMap = new HashMap<>(); //question 4
        hashMap.put("ques1", ques4);
        hashMap.put("ans1",ans4);
        arrayList.add(hashMap);

        hashMap = new HashMap<>(); //question 5
        hashMap.put("ques1", ques5);
        hashMap.put("ans1",ans5);
        arrayList.add(hashMap);

        hashMap = new HashMap<>(); //question 6
        hashMap.put("ques1", ques6);
        hashMap.put("ans1",ans6);
        arrayList.add(hashMap);
        


    }//hashmap ended


}//public class ended