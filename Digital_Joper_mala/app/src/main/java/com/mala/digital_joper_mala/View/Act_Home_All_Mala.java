package com.mala.digital_joper_mala.View;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.gson.Gson;
import com.mala.digital_joper_mala.Api.GET_api;
import com.mala.digital_joper_mala.Api.Request_link;
import com.mala.digital_joper_mala.Model.Api_links;
import com.mala.digital_joper_mala.Model.Main_response;
import com.mala.digital_joper_mala.R;
import com.mala.digital_joper_mala.Helper.ApiResponseListener;
import com.mala.digital_joper_mala.Helper.Security_utils;

import com.squareup.picasso.Picasso;


import java.io.IOException;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;




public class Act_Home_All_Mala extends AppCompatActivity {

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

    private DrawerLayout drawer;
    private NavigationView nv_drawer;

    private String notificationRedDot = "";
    private String notificationTitle = "";
    private String notificationDescription = "";
    private String notificationButton = "";
    private String notificationImage = "";

    //XML id's----------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home_all_mala);

        //identity period-------------------------------------------------------------------

        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer);
        nv_drawer = findViewById(R.id.nv_drawer);
        bottom_nav = findViewById(R.id.bottom_nav);
        frame_layout = findViewById(R.id.frame_layout);


        //identity period-------------------------------------------------------------------

        SharedPreferences preferences = getSharedPreferences("firstTime", MODE_PRIVATE);
        boolean isFirstTime = preferences.getBoolean("firstTime", true);

        if (isFirstTime){

            intro();

        }

        toolBar(); //Toolbar
        bottom_nav();//Bottom navigation view

        //in ap update------------------------------------------------------
        appUpdateManager = AppUpdateManagerFactory.create(Act_Home_All_Mala.this);
        check_update();
        //in ap update------------------------------------------------------

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(Act_Home_All_Mala.this, drawer, toolbar,R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        drawer_navigation();
        //feedback_dialog();


        //checking device security----------------------------------------------------------
        if (Security_utils.isDeviceRooted()) {
            Toast.makeText(this, "This Device is Rooted", Toast.LENGTH_SHORT).show();
            finishAffinity();
            System.exit(0);
        }
        //checking device security----------------------------------------------------------

        //Home fragment -------------------------------------------------------
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_layout, new Fg_all_mala()).commit();
        //Home fragment -------------------------------------------------------

        try {

            Request_link link = new Request_link(new ApiResponseListener() {
                @Override
                public void onApiResponse(Api_links apiLinks) {

                    String url = apiLinks.getInAppnotification();

                    Notification_from_server(url);

                }

                @Override
                public void onApiFailed(String error) {

                }
            });
            link.Apis();

        }catch (Exception e){
            e.printStackTrace();
        }


    }//on create============================================================

    //toolbar-------------------------------------------------------------------
    private void toolBar(){                     //toolbar

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                if (item.getItemId() ==R.id.add){

                    startActivity(new Intent(Act_Home_All_Mala.this, Act_add_mantra.class));

                } else if (item.getItemId() == R.id.notification) {

                    notification_dialog(Act_Home_All_Mala.this);

                } else if (item.getItemId() == R.id.setting) {

                    startActivity(new Intent(Act_Home_All_Mala.this, Act_setting.class));
                    finishAffinity();

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
                    fragmentManager.beginTransaction().replace(R.id.frame_layout, new Fg_Rules_of_mala()).commit();

                }else if (item.getItemId() == R.id.bottom_nav_list){

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_layout, new Fg_Advantage_of_jopa()).commit();

                } else if (item.getItemId() == R.id.bottom_nav_home) {

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_layout, new Fg_all_mala()).commit();

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
                        appUpdateManager.startUpdateFlowForResult(appUpdateInfo,AppUpdateType.FLEXIBLE, Act_Home_All_Mala.this, REQUEST_CODE_UPDATE);
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

                    Toast.makeText(Act_Home_All_Mala.this, "Update Failed", Toast.LENGTH_SHORT).show();

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

    private void intro(){

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.lay_introduction_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        AppCompatTextView tv_start = dialog.findViewById(R.id.tv_start);

        SharedPreferences preferences = getSharedPreferences("firstTime", MODE_PRIVATE);

        tv_start.setOnClickListener(view -> {

            preferences.edit()
                    .putBoolean("firstTime", false)
                    .apply();

            dialog.dismiss();

        });

        dialog.show();

    }

    //notification dialog
    private void Notification_from_server(String url){

        Gson gson = new Gson();

        GET_api getApi = new GET_api(url);

        getApi.call_Api(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.isSuccessful() && response.body() != null){

                    String data = response.body().string();

                    Main_response mainResponse = gson.fromJson(data, Main_response.class);

                    new Handler(Looper.getMainLooper()).post(() -> {

                       try {

                           if (mainResponse.getMsize().contains("long")){

                              // notificationRedDot = mainResponse.getMsize();

                               notificationTitle = mainResponse.getTitle();
                               notificationDescription = mainResponse.getDescription();

                               //Log.i("noti", mainResponse.getTitle());

                               if (!mainResponse.getImg_link().isEmpty()){

                                   notificationImage = mainResponse.getImg_link();

                               }

                               if (!mainResponse.getWeb_link().isEmpty()) {

                                   notificationButton = mainResponse.getWeb_link();

                               }

                           }

                       } catch (Exception e) {
                          e.printStackTrace();
                       }

                    });


                }

            }
        });

    }

    //notification dialog------------------------------------------------------------
    private void notification_dialog(Context context){

        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.lay_notification);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Window window = dialog.getWindow();
        window.setGravity(Gravity.TOP);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        AppCompatImageView iv_img = dialog.findViewById(R.id.iv_img);
        AppCompatTextView tv_notification_title = dialog.findViewById(R.id.tv_notification_title);
        AppCompatTextView tv_notification_description = dialog.findViewById(R.id.tv_notification_description);
        AppCompatTextView tv_no_notification = dialog.findViewById(R.id.tv_no_notification);
        MaterialButton mb_send = dialog.findViewById(R.id.mb_send);
        MaterialButton mb_cancel = dialog.findViewById(R.id.mb_cancel);
        CardView cv_dialog = dialog.findViewById(R.id.cv_dialog);
        LinearLayout ll_image = dialog.findViewById(R.id.ll_image);

        if (notificationTitle.isEmpty() && notificationDescription.isEmpty()){

            cv_dialog.setVisibility(View.VISIBLE);
            tv_no_notification.setVisibility(View.VISIBLE);

        }else {

            cv_dialog.setVisibility(View.VISIBLE);
            tv_notification_title.setText(notificationTitle);
            tv_notification_description.setText(notificationDescription);
            tv_notification_title.setVisibility(View.VISIBLE);
            tv_notification_description.setVisibility(View.VISIBLE);
            mb_cancel.setVisibility(View.VISIBLE);
            tv_no_notification.setVisibility(View.GONE);

            mb_cancel.setOnClickListener(view -> {

                dialog.dismiss();

            });

            if (notificationImage.isEmpty()){

                iv_img.setVisibility(View.GONE);

            } else {

                Picasso.get().load(notificationImage).into(iv_img);
                ll_image.setVisibility(View.VISIBLE);
            }

            if (notificationButton.isEmpty()){

                mb_send.setVisibility(View.GONE);

            } else {
                //mb_send.setText(notificationButton);
                mb_send.setVisibility(View.VISIBLE);

                mb_send.setOnClickListener(view -> {

                    startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(notificationButton)));
                    dialog.dismiss();

                });
            }



        }

        dialog.show();
    }

    //drawer navigation--------------------------------------------------
    private void drawer_navigation(){

        nv_drawer.setNavigationItemSelectedListener(menuItem -> {


            int id = menuItem.getItemId();
            
            if (id == R.id.share){

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String Body = "Download this App";
                String sub = "https://play.google.com/store/apps/details?id="+appPackageName;
                intent.putExtra(Intent.EXTRA_TEXT,Body);
                intent.putExtra(Intent.EXTRA_TEXT,sub);
                startActivity(Intent.createChooser(intent,null));
                
            } else if (id == R.id.star) {

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    finishAffinity();
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    finishAffinity();
                }
                
            } else if (id == R.id.feature) {

                startActivity(new Intent(Act_Home_All_Mala.this, Act_new_feature.class));

            } else if (id == R.id.history) {

                startActivity(new Intent(Act_Home_All_Mala.this, Act_jopa_history.class));

            } else if (id == R.id.create) {

                Intent intent = new Intent(this, Act_User_mala.class);
                intent.putExtra("create", "Fg_Create_mala");
                startActivity(intent);

            } else if (id == R.id.all_mantra) {

                startActivity(new Intent(Act_Home_All_Mala.this, Act_all_mantra.class));


            } else if (id == R.id.chart) {

                startActivity(new Intent(Act_Home_All_Mala.this, Act_chart.class));

            }

            return true;
        });

    }

}//public class=================================