package com.mala.digital_joper_mala;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


import java.util.ArrayList;
import java.util.HashMap;


public class HomeAllMalaActivity extends AppCompatActivity {

    //XML id's----------------------------------------------------------------------


    private static final String appPackageName = "com.mala.digital_joper_mala";

    Toolbar toolbar;
    GridView all_mala_gridview;

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    HashMap<String, String> hashMap;

    RadioButton radio_button_home, radio_button_rules, radio_button_info;
    RadioGroup radio_group;


    BottomNavigationView bottom_nav;

    FrameLayout frame_layout;

    //XML id's----------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        load_mode();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_all_mala);
        //dark----------------------------------------------------------------

        //dark----------------------------------------------------------------



        //identity period-------------------------------------------------------------------

        toolbar = findViewById(R.id.toolbar);
        all_mala_gridview = findViewById(R.id.all_mala_gridview);
        //radio_button_home = findViewById(R.id.radio_button_home);
        //radio_button_rules = findViewById(R.id.radio_button_rules);
        //radio_button_info = findViewById(R.id.radio_button_info);
        //radio_group = findViewById(R.id.radio_group);
        bottom_nav = findViewById(R.id.bottom_nav);
        frame_layout = findViewById(R.id.frame_layout);

        //identity period-------------------------------------------------------------------




        hashmap();
        Myadapter myadapter = new Myadapter();
        all_mala_gridview.setAdapter(myadapter);

        tooLbar();
        bottom_nav();



       /*
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {


                //radio button default color
                radio_button_home.setTextColor(getResources().getColor(android.R.color.white));
                radio_button_rules.setTextColor(getResources().getColor(android.R.color.white));
                radio_button_info.setTextColor(getResources().getColor(android.R.color.white));



                if (id == R.id.radio_button_home){

                    radio_button_home.setTextColor(getResources().getColor(android.R.color.black));

                } else if (id == R.id.radio_button_rules) {

                    radio_button_rules.setTextColor(getResources().getColor(android.R.color.black));


                } else if (id == R.id.radio_button_info) {

                    radio_button_info.setTextColor(getResources().getColor(android.R.color.black));

                }

            }
        });

        */



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


                } else if (item.getItemId() == R.id.rating) {



                }


                return false;
            }
        });


    }//toolbar

    private void bottom_nav(){


        bottom_nav.setSelectedItemId(R.id.bottom_nav_home);


        bottom_nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.bottom_nav_rule){

                    startActivity(new Intent(HomeAllMalaActivity.this, RulesForJopaActivity.class));
                    finishAffinity();

                }else if (item.getItemId() == R.id.bottom_nav_list){

                    startActivity(new Intent(HomeAllMalaActivity.this, AdvatageForJopaActivity.class));
                    finishAffinity();

                }else if (item.getItemId() == R.id.bottom_nav_setting){

                    startActivity(new Intent(HomeAllMalaActivity.this, SettingActivity.class));
                    finishAffinity();
                }

                return false;
            }
        });
    }

    private void current_theme() {

        String mode_saved = load_mode();

        if (mode_saved.equals("light")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        } else if (mode_saved.equals("dark")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }

    }
    private String load_mode() {

        SharedPreferences sharedPreferences = getSharedPreferences("App_theme", MODE_PRIVATE);
        return sharedPreferences.getString("theme_mode", "system");
    }

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

                       startActivity(new Intent(HomeAllMalaActivity.this, UMalaActivity.class));

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