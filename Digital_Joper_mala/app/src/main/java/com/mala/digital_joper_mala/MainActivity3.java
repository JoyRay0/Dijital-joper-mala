package com.mala.digital_joper_mala;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatImageButton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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





    //XML id's-----------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //identity period------------------------------------------

        mback = findViewById(R.id.back);
        listView = findViewById(R.id.listview);


        sharedPreferences = getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("night",false);



        //identity period------------------------------------------


        //Adapter started-----------------------------------------------------

        myAdapter mylistview = new myAdapter();
        listView.setAdapter(mylistview);

        //Adapter started-----------------------------------------------------




        //hashmap started****************************************

        hashMap = new HashMap<>();  //question 1
        hashMap.put("ques1","১। মালা জপ করা সবার কর্তব্য কেন ?");
        hashMap.put("ans1","শ্রী গৌরাঙ্গ মহাপ্রভু তার ভক্তগণকে তার শ্রীমুখে আজ্ঞা করিলেন, প্রতিদিন ১ লক্ষ নাম জপ করিবে। প্রতিদিন ১ লক্ষ নাম যিনি জপ করেন, তার মনের বাঞ্ছা পূর্ণ করেন ভগবান। নামবিনে কলিকালে আর কোন ধর্ম নাই, শাস্ত্রে বলা হয়েছে শ্রী হরিনাম মহামন্ত্র হলো সব মন্ত্রের সার। হরিনাম জপ করিলে সব পাপ ক্ষয় হয়, এই নামেই জীবের মোক্ষ লাভ হয়। তাই সবার হরিনাম জপ করা কর্তব্য।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>(); //question 2
        hashMap.put("ques1","২। মালা জপ করিলে কেন তার হিসাব রাখতে হয় ?");
        hashMap.put("ans1","কলিযুগে শ্রীমন মহাপ্রভু তার ভক্ত ও বৈষ্ণবদের সংখ্যা রক্ষণ সম্বন্ধে নির্দেশ দিয়েছেন। যদি মালা জপ করে তার সংখ্যা গননা না করা হয় তাহলে সে জপের কোন ফল পাওয়া যায় না ।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>(); //question 3
        hashMap.put("ques1","৩। মানব জন্ম এতো দুর্লব কেন ?");
        hashMap.put("ans1","২০ লক্ষ বার গাছ রুপে। ৯ লক্ষ বার জলের প্রানী রুপে। ১১ লক্ষ বার কৃমি কিট রুপে। ১০ লক্ষ বার পক্ষী রুপে। ৩০ লক্ষ বার পশু রুপে। ৪ লক্ষ বার বানর রুপে। মোট ৮৪ লক্ষ বার জন্মের পর আমরা ১ টি মানব জন্ম পাই । তাই মানব জন্ম খুব দুর্লব । তাই আমাদের এই জন্ম-মৃত্যুর চক্র থেকে বের হতে হলে সর্বদা হরিনাম জপ করতে হবে ।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>(); //question 4
        hashMap.put("ques1", "৪। আমাদের প্রতিদিন কতবার মালা জপ করা উচিত ?");
        hashMap.put("ans1","মালা ১০৮ বার জপ করিলে ১ ফিরান হয়। এইরুপ ৪ ফিরানে ১ গ্রন্থি হয় এবং ১৬ গ্রন্থিতে ১ লক্ষ নাম পূর্ন হয়। এইভাবে ৪ বার x ১৬ বার = ৬৪ বার । ৬৪ বার জপ করিলে ১ লক্ষ নাম পূর্ন হয় । শ্রীমন মহাপ্রভু সমস্ত ভক্তগণকে ১ লক্ষ নাম জপের আদেশ দিয়েছেন ।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>(); //question 5
        hashMap.put("ques1", "৫। কখন আমাদের মালা জপ করা উচিত ?");
        hashMap.put("ans1","মালা জপের সঠিক সময় হচ্ছে স্নান করার পর। স্নান এরপর আমাদের শরীর ও মন পবিএ থাকে। শরীর ও মন পবিএ থাকার ফলে ভগবানের প্রতি আমাদের ভক্তি জাগ্রত হয়।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>(); //question 6
        hashMap.put("ques1", "৬। কিভাবে শ্রী শ্রী হরনিাম জপের মালা শুদ্বি করতে হয় ?");
        hashMap.put("ans1"," শ্রী শ্রী  হরিনাম জপের ১০৮ টি মালা। সর্ব প্রথম পঞ্চগব্য ও মন্ত্র দ্বারা শুদ্ব করিবে। তারপর জপের মালা ভগবান শ্রী রাধাকৃষ্ণের চরনে ছুয়িয়ে মালার আধারীতে / থলিতে রেখে মালা জপ করিবে । পঞ্চগব্য তৈরির বিধি : “ দই , দুধ , ঘি , গরুর গোবর ও চেনা ”।");
        arrayList.add(hashMap);


        //hashmap ended***********************************************



        //starting point----------------------------------------------------------


        if (nightMode){

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }


        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myintent = new Intent(MainActivity3.this, MainActivity.class);
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
            @SuppressLint("ViewHolder") View view1 = layoutInflater.inflate(R.layout.layout, viewGroup, false);

            HashMap<String, String> hashMap1 = arrayList.get(i);


            ImageView imageview = view1.findViewById(R.id.imageview);
            LinearLayout ans_linear_layout = view1.findViewById(R.id.ans_linear_layout);
            LinearLayout mother_layout = view1.findViewById(R.id.mother_layout);
            RelativeLayout clicked = view1.findViewById(R.id.clicked);
            TextView ques_text = view1.findViewById(R.id.ques_text);
            TextView tvDisplay = view1.findViewById(R.id.tvDisplay);



            String string = hashMap1.get("ques1");
            ques_text.setText(""+string);




            clicked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //code here-----

                    String string1 = hashMap1.get("ans1");



                    if (ans_linear_layout.getVisibility() == View.GONE){


                        tvDisplay.setText(""+string1);
                        //TransitionManager.beginDelayedTransition(mother_layout, new AutoTransition());
                        ans_linear_layout.setVisibility(View.VISIBLE);
                        imageview.setImageResource(R.drawable.baseline_keyboard_arrow_up_24);

                    }else {

                        tvDisplay.setText(""+string1);
                        //TransitionManager.beginDelayedTransition(mother_layout, new AutoTransition());
                        ans_linear_layout.setVisibility(View.GONE);
                        imageview.setImageResource(R.drawable.baseline_keyboard_arrow_down_24);

                    }

                }
            });









            return view1;
        }
    }

}//public class ended