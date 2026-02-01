package com.mala.digital_joper_mala.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

import com.mala.digital_joper_mala.Activity.Act_Boisnob_mala;
import com.mala.digital_joper_mala.Activity.Act_Custom_Mala;
import com.mala.digital_joper_mala.Activity.Act_ShivMala;
import com.mala.digital_joper_mala.Activity.Act_easy_mala;
import com.mala.digital_joper_mala.R;

import java.util.HashMap;
import java.util.List;

public class Mala extends BaseAdapter {

    private List<HashMap<String, String>> list;
    private Context context;

    public Mala(Context context, List<HashMap<String, String>> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
    public View getView(int position, View view, ViewGroup parent) {

         View view1 = LayoutInflater.from(context).inflate(R.layout.lay_desgin_for_all_mala_home,parent, false );


        CardView cardview = view1.findViewById(R.id.cardview);
        AppCompatTextView all_mala_name_textview = view1.findViewById(R.id.tv_all_mala_name);
        AppCompatImageView all_mala_imageview = view1.findViewById(R.id.iv_all_mala_image);


        HashMap<String, String> hashMap1 = list.get(position);

        //hashmap to string
        String text_item = hashMap1.get("name_mala");

        all_mala_name_textview.setText(text_item);

        //mala button
        cardview.setOnClickListener(view2 -> {

            if (position == 0){

                context.startActivity(new Intent(context, Act_easy_mala.class));

            } else if (position == 1) {

                context.startActivity(new Intent(context, Act_Custom_Mala.class));

            } else if (position == 2) {

                context.startActivity(new Intent(context, Act_Boisnob_mala.class));

            } else if (position == 3) {

                context.startActivity(new Intent(context, Act_ShivMala.class));

            }

        });

        return view1;
    }
}
