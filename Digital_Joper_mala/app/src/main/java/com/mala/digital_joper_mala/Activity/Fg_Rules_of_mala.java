package com.mala.digital_joper_mala.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mala.digital_joper_mala.R;

import java.util.ArrayList;
import java.util.HashMap;


public class Fg_Rules_of_mala extends Fragment {

    //XML id's-------------------------------------------------------------

    private ListView listview;

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    HashMap<String, String> hashMap;
    //XML id's-------------------------------------------------------------

    
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fg_rules, container, false);

        //identity period--------------------------------------------------------
        listview = view1.findViewById(R.id.listview);
        //identity period--------------------------------------------------------

        hashmap();
        Myadapter myadapter = new Myadapter();
        listview.setAdapter(myadapter);


        return view1;
    }//on create========================================

    //adapter class------------------------------------------------------
    private class Myadapter extends BaseAdapter{

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View view1 = layoutInflater.inflate(R.layout.lay_design_of_listview, viewGroup,false);

            HashMap<String, String> hashMap1 = arrayList.get(position);


            AppCompatImageView imageview = view1.findViewById(R.id.imageview);
            LinearLayout ans_layout = view1.findViewById(R.id.ans_layout);
            LinearLayout clicked = view1.findViewById(R.id.clicked);
            AppCompatTextView ques_text = view1.findViewById(R.id.ques_text);
            AppCompatTextView tvDisplay = view1.findViewById(R.id.tvDisplay);


            String ques1 = hashMap1.get("ques");
            ques_text.setText(ques1);

            clicked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //code here-----

                    String ans1 = hashMap1.get("ans");

                    if (ans_layout.getVisibility() == View.GONE){


                        tvDisplay.setText(ans1);
                        //TransitionManager.beginDelayedTransition(mother_layout, new AutoTransition());
                        ans_layout.setVisibility(View.VISIBLE);
                        imageview.setImageResource(R.drawable.ic_up);

                    }else {


                        //TransitionManager.beginDelayedTransition(mother_layout, new AutoTransition());
                        ans_layout.setVisibility(View.GONE);
                        imageview.setImageResource(R.drawable.ic_down);

                    }

                }
            });


            return view1;
        }
    }
    //adapter class------------------------------------------------------

    //hashmap-----------------------------------------------
    private void hashmap(){

        hashMap = new HashMap<>();
        hashMap.put("ques","১। জপের পূর্বে ইষ্টদেবতার স্মরণ কেন গুরুত্বপূর্ণ বলে বিবেচিত হয়?");
        hashMap.put("ans","মালা জপ শুরু করার পূর্বে, ভক্তিভরে ইষ্টদেবতার নাম স্মরণ করলে জপ আরও ফলদায়ী হয়।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("ques","২। জপ করার ক্ষেত্রে স্নানের পর সময়টিকে কেন সর্বোত্তম বলে বিবেচনা করা হয়?");
        hashMap.put("ans","জপমালা ব্যবহারের আদর্শ সময় হলো স্নানের পর, যখন শরীর ও মন পবিত্র থাকে। এই পবিত্রতা আমাদের ভক্তিভাব জাগিয়ে তোলে এবং জপে গভীর মনোযোগ বজায় রাখতে সহায়তা করে।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("ques","৩। অপবিত্র দেহে জপ করলে তার প্রভাব কী ধরনের হতে পারে?");
        hashMap.put("ans","অপবিত্র শরীরে, বিশেষত শৌচকর্মের পর স্নান না করে জপ করলে—তা পূর্ণভাবে কার্যকর হয় না। জপের সফলতার জন্য দেহ ও মন উভয়ের পবিত্রতা অপরিহার্য।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("ques","৪। জপের প্রথম তিন ধাপ কীভাবে সম্পন্ন করতে হয়?");
        hashMap.put("ans","শুরুতে ১০৮ বার জপ শেষ করুন।\n" +
                "তারপর দ্বিতীয় ধাপে, ১ বার করে মোট ১৬ বার গননা করবেন।\n" +
                "শেষে, ৩য় ধাপে আবার ১ বার করে মোট ৪ বার গননা করুন।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("ques","৫। কোন জায়গায় বসে জপ করলে সবচেয়ে ভালো ফল পাওয়া যায়?");
        hashMap.put("ans","জপের সময় এমন স্থানে বসা উচিত যা পরিচ্ছন্ন, পবিত্র এবং শান্ত। শব্দ থেকে মুক্ত এমন পরিবেশ মনকে স্থির করে এবং ভগবানের স্মৃতিতে গভীর নিমগ্ন হতে সহায়তা করে। জপের জন্য নির্দিষ্ট স্থান থাকা, নিয়মিত সেখানে বসে জপ করা শরীর ও মনের অভ্যাস গড়তে সাহায্য করে।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("ques","৬। নবীনদের জন্য কম বার জপ শুরু করার পরামর্শ কী?");
        hashMap.put("ans","১০৮ বার জপ করার বিশেষ তাৎপর্য আছে। শাস্ত্রে ১০৮ সংখ্যাটি পবিত্র ও পূর্ণতা নির্দেশ করে। যখন আপনি ১০৮ বার জপ করেন, তখন আপনার সাধনা পরিপূর্ণ হয় এবং মন্ত্রের শক্তি পূর্ণরূপে প্রবাহিত হয়। তাই সাধারনত ১০৮ বার থেকে কম জপ করা উচিত নয়। তবে, নবীন বা সময়ের অভাবে কেউ কম বার জপ শুরু করলেও নিয়মিত ও ভক্তিপূর্ণ জপ করাই সবচেয়ে গুরুত্বপূর্ণ। সময় সুযোগ মতো ধীরে ধীরে ১০৮ পূর্ণ করা উত্তম।");
        arrayList.add(hashMap);



    }
    //hashmap-----------------------------------------------


}//public class==========================================

