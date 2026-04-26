package com.mala.digital_joper_mala.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import com.mala.digital_joper_mala.Database.UserMala;
import com.mala.digital_joper_mala.R;
import com.mala.digital_joper_mala.Helper.onDataDeleteListener;

public class Act_User_mala extends AppCompatActivity {

    //XML id's---------------------------------------------------------------------

    //toolbar
    private ImageButton back;
    private AppCompatImageView iv_delete;

    //frame layout
    private FrameLayout fl_user_mala;

    //other
    public static String MALA_NAME = "";
    private UserMala userMala;

    //XML id's---------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_user_mala);

        //identity period----------------------------------------------------------

        back = findViewById(R.id.back);
        fl_user_mala = findViewById(R.id.fl_user_mala);
        iv_delete = findViewById(R.id.iv_delete);

        //identity period----------------------------------------------------------

        userMala = new UserMala(this);

        intentData();

        back.setOnClickListener(view -> {

            startActivity(new Intent(Act_User_mala.this, Act_Home_All_Mala.class));
            finishAffinity();

        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                startActivity(new Intent(Act_User_mala.this, Act_Home_All_Mala.class));
                finishAffinity();

            }
        });

        if (iv_delete.getVisibility() == View.VISIBLE){

            iv_delete.setOnClickListener(view -> {

                userMala.deleteAll();
                Toast.makeText(this, "সব ডিলিট হয়েছে।", Toast.LENGTH_SHORT).show();
                Fragment fragmentManager =  getSupportFragmentManager().findFragmentById(R.id.fl_user_mala);

                if (fragmentManager instanceof onDataDeleteListener){

                    ((onDataDeleteListener)fragmentManager).onDataDeleted();
                }

            });

        }


    }// on create=================================================================

    //set fragment with intent data---------------------------------------------------
    private void intentData(){

        String creates = getIntent().getStringExtra("create");
        String shows = getIntent().getStringExtra("show");
        String name = getIntent().getStringExtra("name");

        MALA_NAME = name;

        if (creates != null && creates.contains("Fg_Create_mala")){

            iv_delete.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_user_mala, new Fg_create_mala()).commit();

        }else if (shows != null && shows.contains("Fg_show_user_mala")){

            iv_delete.setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_user_mala, new Fg_show_user_mala()).commit();

        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userMala.closeDB();
    }
}//public class===================================================================