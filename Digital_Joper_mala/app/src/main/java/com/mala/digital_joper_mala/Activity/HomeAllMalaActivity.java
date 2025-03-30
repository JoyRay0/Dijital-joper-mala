package com.mala.digital_joper_mala.Activity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
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
import com.mala.digital_joper_mala.Utils.Notification_service;
import com.mala.digital_joper_mala.R;
import com.mala.digital_joper_mala.Utils.Security_utils;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.mikepenz.actionitembadge.library.utils.BadgeStyle;
import com.mikepenz.actionitembadge.library.utils.BadgeStyle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HomeAllMalaActivity extends AppCompatActivity {

    //XML id's----------------------------------------------------------------------


    private static final String appPackageName = "com.mala.digital_joper_mala";
    private static final int REQUEST_CODE_UPDATE = 100;

    private Toolbar toolbar;
    private GridView all_mala_gridview;

    private FrameLayout frame_layout;

    private BottomNavigationView bottom_nav;

    private AppUpdateManager appUpdateManager;

    private InstallStateUpdatedListener installStateUpdatedListener;

    private AppCompatTextView tv_notification_title;

    private AppCompatTextView tv_notification_description;

    private AppCompatTextView tv_no_notification;




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

        tooLbar(); //Toolbar
        bottom_nav();//Bottom navigation view

        //in ap update------------------------------------------------------
        appUpdateManager = AppUpdateManagerFactory.create(HomeAllMalaActivity.this);
        check_update();
        //in ap update------------------------------------------------------


        //Notification service-----------------------------------------------
        startService(new Intent(this, Notification_service.class));
        //Notification service-----------------------------------------------


        //checking device security----------------------------------------------------------
        if (Security_utils.isDeviceRooted()) {
            Toast.makeText(this, "This Device is Rooted", Toast.LENGTH_SHORT).show();
            finishAffinity();
            System.exit(0);
        }
        //checking device security----------------------------------------------------------







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

        //Home fragment -------------------------------------------------------
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_layout, new Fragment_all_mala()).commit();
        //Home fragment -------------------------------------------------------




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

                } else if (item.getItemId() == R.id.notification) {



                    Dialog dialog = new Dialog(HomeAllMalaActivity.this);
                    dialog.setContentView(R.layout.notification_dialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    dialog.show();

                    tv_notification_title = dialog.findViewById(R.id.tv_notification_title);
                    tv_notification_description= dialog.findViewById(R.id.tv_notification_description);
                    tv_no_notification = dialog.findViewById(R.id.tv_no_notification);

                    String url = "https://rksoftwares.xyz/All_app/jopa_mala/InAppNotification";

                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder().url(url).build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {

                            new Handler(Looper.getMainLooper()).post(() -> {

                                tv_no_notification.setVisibility(View.VISIBLE);
                                tv_notification_title.setVisibility(View.GONE);
                                tv_no_notification.setVisibility(View.GONE);

                            });

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                            if (response.isSuccessful() && response.body() != null){

                                String message = response.body().string();

                                try {
                                    JSONObject object = new JSONObject(message);

                                    String title = object.getString("title");
                                    String description = object.getString("description");
                                    String msize = object.getString("msize");

                                    new Handler(Looper.getMainLooper()).post(() -> {

                                        if (msize.equals("long")){

                                            tv_no_notification.setVisibility(View.GONE);
                                            tv_notification_title.setVisibility(View.VISIBLE);
                                            tv_notification_description.setVisibility(View.VISIBLE);

                                            //int notify_num = Integer.parseInt(notification_num);

                                            tv_notification_title.setText(title);
                                            tv_notification_description.setText(description);
                                            
                                        } else if (msize.equals("sort")) {

                                            tv_no_notification.setVisibility(View.VISIBLE);
                                            tv_notification_title.setVisibility(View.GONE);
                                            tv_notification_description.setVisibility(View.GONE);

                                        }

                                    });

                                } catch (Exception e) {
                                   new Handler(Looper.getMainLooper()).post(() -> {

                                       Toast.makeText(HomeAllMalaActivity.this, ""+e, Toast.LENGTH_SHORT).show();

                                   });
                                }

                            }

                        }
                    });


                }

                return true;
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
                    fragmentManager.beginTransaction().replace(R.id.frame_layout, new Rules_fragment()).commit();

                }else if (item.getItemId() == R.id.bottom_nav_list){

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_layout, new Advantage_of_jopa()).commit();

                }else if (item.getItemId() == R.id.bottom_nav_setting){

                    startActivity(new Intent(HomeAllMalaActivity.this, SettingActivity.class));

                } else if (item.getItemId() == R.id.bottom_nav_home) {

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_layout, new Fragment_all_mala()).commit();

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

        stopService(new Intent(this, Notification_service.class));
    }
    //in app update-----------------------------------------------------------------------

}//public class=================================