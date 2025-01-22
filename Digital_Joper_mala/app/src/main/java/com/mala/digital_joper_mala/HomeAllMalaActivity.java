package com.mala.digital_joper_mala;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


import java.util.ArrayList;
import java.util.HashMap;


public class HomeAllMalaActivity extends AppCompatActivity {

    //XML id's----------------------------------------------------------------------


    private static final String appPackageName = "com.mala.digital_joper_mala";

    private Toolbar toolbar;
    private GridView all_mala_gridview;

    //ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    //HashMap<String, String> hashMap;

    //RadioButton radio_button_home, radio_button_rules, radio_button_info;
    //RadioGroup radio_group;

    private FrameLayout frame_layout;

    private BottomNavigationView bottom_nav;


    //XML id's----------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_all_mala);


        //identity period-------------------------------------------------------------------

        toolbar = findViewById(R.id.toolbar);
        //all_mala_gridview = findViewById(R.id.all_mala_gridview);
        //radio_button_home = findViewById(R.id.radio_button_home);
        //radio_button_rules = findViewById(R.id.radio_button_rules);
        //radio_button_info = findViewById(R.id.radio_button_info);
        //radio_group = findViewById(R.id.radio_group);
        bottom_nav = findViewById(R.id.bottom_nav);
        frame_layout = findViewById(R.id.frame_layout);


        //identity period-------------------------------------------------------------------




        //hashmap();
        //Myadapter myadapter = new Myadapter();
        //all_mala_gridview.setAdapter(myadapter);

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

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment_all_mala fragment_all_mala = new Fragment_all_mala();
        fragmentTransaction.replace(R.id.frame_layout, fragment_all_mala);
        fragmentTransaction.commit();




    }//on create=======================

    //menu button
    private void tooLbar(){                     //toolbar


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

                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    }

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

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    Rules_fragment rules_fragment = new Rules_fragment();
                    fragmentTransaction.replace(R.id.frame_layout, rules_fragment);
                    fragmentTransaction.commit();


                }else if (item.getItemId() == R.id.bottom_nav_list){

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    Advantage_of_jopa advantageOfJopa = new Advantage_of_jopa();
                    fragmentTransaction.replace(R.id.frame_layout, advantageOfJopa);
                    fragmentTransaction.commit();

                }else if (item.getItemId() == R.id.bottom_nav_setting){

                    startActivity(new Intent(HomeAllMalaActivity.this, SettingActivity.class));

                } else if (item.getItemId() == R.id.bottom_nav_home) {

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    Fragment_all_mala fragment_all_mala = new Fragment_all_mala();
                    fragmentTransaction.replace(R.id.frame_layout, fragment_all_mala);
                    fragmentTransaction.commit();

                }

                return true;
            }
        });
    }


}//public class=================================