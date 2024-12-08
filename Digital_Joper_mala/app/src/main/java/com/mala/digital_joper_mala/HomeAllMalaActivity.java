package com.mala.digital_joper_mala;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.HashMap;


public class HomeAllMalaActivity extends AppCompatActivity {

    //XML id's----------------------------------------------------------------------

    String appPackageName = "com.mala.digital_joper_mala";
    Toolbar toolbar;
    GridView all_mala_gridview;

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    HashMap<String, String> hashMap;

    RadioButton radio_button_home, radio_button_rules, radio_button_info;

    //XML id's----------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_all_mala);

        //identity period-------------------------------------------------------------------

        toolbar = findViewById(R.id.toolbar);
        all_mala_gridview = findViewById(R.id.all_mala_gridview);
        radio_button_home = findViewById(R.id.radio_button_home);
        radio_button_rules = findViewById(R.id.radio_button_rules);
        radio_button_info = findViewById(R.id.radio_button_info);
        //identity period-------------------------------------------------------------------

        hashmap();
        Myadapter myadapter = new Myadapter();
        all_mala_gridview.setAdapter(myadapter);

        tooLbar();

        if (radio_button_home.isActivated() || radio_button_rules.isChecked() || radio_button_info.isChecked()){

            radio_button_home.setTextColor(R.color.color2);
            radio_button_rules.setTextColor(R.color.color2);
            radio_button_info.setTextColor(R.color.color2);

        }else {

            radio_button_home.setTextColor(R.color.color3);
            radio_button_rules.setTextColor(R.color.color3);
            radio_button_info.setTextColor(R.color.color3);

        }





    }//on create=======================

    //menu button
    private void tooLbar(){                     //toolbar

        toolbar.inflateMenu(R.menu.toolbar_menu);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.share){
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    String Body = "Download this App";
                    String sub = "https://play.google.com/store/apps/details?id="+appPackageName;
                    intent.putExtra(Intent.EXTRA_TEXT,Body);
                    intent.putExtra(Intent.EXTRA_TEXT,sub);
                    startActivity(Intent.createChooser(intent,null));


                } else if (item.getItemId() == R.id.setting) {

                    startActivity(new Intent(HomeAllMalaActivity.this, SettingActivity.class));

                }


                return false;
            }
        });


    }//toolbar

    private void hashmap(){

        hashMap = new HashMap<>();
        hashMap.put("name_mala","");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name_mala","");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name_mala","");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name_mala","");
        arrayList.add(hashMap);

    }

    public class Myadapter extends BaseAdapter{


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

           HashMap<String, String> hashMap1 = arrayList.get(position);


           cardview.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                   if (position == 0){

                       Toast.makeText(HomeAllMalaActivity.this, "Sending", Toast.LENGTH_SHORT).show();

                   } else if (position == 1) {

                       startActivity(new Intent(HomeAllMalaActivity.this, MainActivity.class));

                   } else if (position == 2) {

                       Toast.makeText(HomeAllMalaActivity.this, "Sending", Toast.LENGTH_SHORT).show();

                   }

               }
           });


            return view1;
        }
    }


}//public class=================================