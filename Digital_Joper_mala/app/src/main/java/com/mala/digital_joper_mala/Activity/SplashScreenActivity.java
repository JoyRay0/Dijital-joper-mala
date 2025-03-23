package com.mala.digital_joper_mala.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.mala.digital_joper_mala.R;

public class SplashScreenActivity extends AppCompatActivity {

    //XML id's--------------------------------------------------------



    //XML id's--------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);



        //identity period----------------------------------------------------



        //identity period----------------------------------------------------


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){

            other_page();

        }else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {



                    other_page();
                }
            },2000);

        }


    }//on create=====================================
    private void other_page(){

        startActivity(new Intent(SplashScreenActivity.this, HomeAllMalaActivity.class));
        finishAffinity();

    }
}//public class ===========================================