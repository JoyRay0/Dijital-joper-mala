package com.mala.digital_joper_mala;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentSender;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;


import java.util.ArrayList;
import java.util.HashMap;


public class HomeAllMalaActivity extends AppCompatActivity {

    //XML id's----------------------------------------------------------------------


    private static final String appPackageName = "com.mala.digital_joper_mala";
    private static final int REQUEST_CODE_UPDATE = 100;

    private Toolbar toolbar;
    private GridView all_mala_gridview;

    //ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    //HashMap<String, String> hashMap;

    //RadioButton radio_button_home, radio_button_rules, radio_button_info;
    //RadioGroup radio_group;

    private FrameLayout frame_layout;

    private BottomNavigationView bottom_nav;

    private AppUpdateManager appUpdateManager;

    private InstallStateUpdatedListener installStateUpdatedListener;


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

        tooLbar();
        bottom_nav();

        appUpdateManager = AppUpdateManagerFactory.create(HomeAllMalaActivity.this);
        check_update();





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

    //toolbar-------------------------------------------------------------------
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


    }
    //toolbar-------------------------------------------------------------------

    //bottom navigation----------------------------------------------------------------
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
    //bottom navigation----------------------------------------------------------------

    //in app update-----------------------------------------------------------------------

    private void check_update(){        //checking update available or not

        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {

                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)){

                    try {
                        appUpdateManager.startUpdateFlowForResult(appUpdateInfo,AppUpdateType.FLEXIBLE, HomeAllMalaActivity.this, REQUEST_CODE_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });

    }

    private void Register_install_state_update(){

        installStateUpdatedListener = new InstallStateUpdatedListener() {
            @Override
            public void onStateUpdate(@NonNull InstallState installState) {
                if (installState.installStatus() == InstallStatus.DOWNLOADED){

                    show_update_complete_notification();

                } else if (installState.installStatus() == InstallStatus.FAILED) {

                    Toast.makeText(HomeAllMalaActivity.this, "Update Failed", Toast.LENGTH_SHORT).show();

                }
            }
        };
        appUpdateManager.registerListener(installStateUpdatedListener);

    }

    private void show_update_complete_notification() {

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Update completed now restart your app", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Restart", view -> appUpdateManager.completeUpdate());
        snackbar.show();

    }

    @Override
    protected void onResume() {
        super.onResume();

        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {

                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED){
                    show_update_complete_notification();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (appUpdateManager != null && installStateUpdatedListener != null){
            appUpdateManager.unregisterListener(installStateUpdatedListener);
        }
    }

    //in app update-----------------------------------------------------------------------


}//public class=================================