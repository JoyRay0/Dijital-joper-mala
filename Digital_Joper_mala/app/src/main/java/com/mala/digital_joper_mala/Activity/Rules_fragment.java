package com.mala.digital_joper_mala.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

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


public class Rules_fragment extends Fragment {

    //XML id's-------------------------------------------------------------

    private ListView listview;

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    HashMap<String, String> hashMap;
    //XML id's-------------------------------------------------------------

    
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_rules_fragment, container, false);

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
            View view1 = layoutInflater.inflate(R.layout.design_of_listview, viewGroup,false);

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
                        imageview.setImageResource(R.drawable.baseline_keyboard_arrow_up_24);

                    }else {


                        //TransitionManager.beginDelayedTransition(mother_layout, new AutoTransition());
                        ans_layout.setVisibility(View.GONE);
                        imageview.setImageResource(R.drawable.baseline_keyboard_arrow_down_24);

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
        hashMap.put("ques","১। মালা জপের নিয়ম ?");
        hashMap.put("ans","মালা জপ করার পূর্বে ইষ্ট দেবতাকে স্মরন করে জপ শুরু করতে হয়।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("ques","২। কখন আমাদের মালা জপ করা উচিত ?");
        hashMap.put("ans","মালা জপের সঠিক সময় হচ্ছে স্নান করার পর। স্নান এরপর আমাদের শরীর ও মন পবিএ থাকে। শরীর ও মন পবিএ থাকার ফলে ভগবানের প্রতি আমাদের ভক্তি জাগ্রত হয়।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("ques","৩। কখন মালা জপ করা উচিত নয়?");
        hashMap.put("ans","শৌচ কাজ করার পর, স্নানের আগে এবং অপবিএ শরীরে জপ করলে জপের কোন ফল পাওয়া যায় না।");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("ques","৪। কোন ধাপে কতবার জপ করিবেন ?");
        hashMap.put("ans","১ম ধাপে ১০৮ বার জপ করিবেন। ১ম ধাপে ১০৮ বার জপ শেষ হলে ২য় ধাপে ১ বার গননা করিবেন। এই ভাবে ২য় ধাপে ১৬ বার গননা করিবেন। " +
                "২য় ধাপে ১৬ বার গননা শেষ হলে, ৩য় ধাপে ১ বার গননা করিবেন। এই ভাবে ৩য় ধাপে ৪ বার গননা করিবেন।");
        arrayList.add(hashMap);



    }
    //hashmap-----------------------------------------------


}//public class==========================================

